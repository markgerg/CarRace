package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;
import javax.swing.Renderer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import cars.Challenger;
import cars.e8Accelerating;
import cars.e8CarColour;
import cars.e8CarType;
import cars.e8Steering;
import engineTester.MainGameLoop;
import entities.Camera;
import network.DeLoreanServerStateMachine.State;
import shaders.StaticShader;
import track.e8TrackID;



public class DeLoreanClientStateMachine extends Network {

	public enum State {
		DISCONNECTED,
		CONNECTION_IN_PROGRESS,
		CONNECTED
	}
	

	
	Challenger servercar;
	Challenger clientcar;
	
	Camera camera;
	
	private State state;
	private String ip;
	Timer timer = new Timer();
	Timer frameTime = new Timer();
	int DeLoreanGeneration = 1;
	
	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	
	static Semaphore mutex = new Semaphore(1);
	
	TimerTask frameUpdate = new TimerTask() {
		  @Override
		  public void run() {
			//System.out.println("Eltelt 40ms idő...");
			// Billentyűk olvasása
			e8Accelerating accelerate;
			e8Steering steer;
			
	        if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
	    		//kinematics.increaseSpeed();
	        	accelerate = e8Accelerating.FORWARD;
	        }
	        else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				//kinematics.breakingCar();
	        	accelerate = e8Accelerating.BACKWARD;
	        }
	        else {
	        	accelerate = e8Accelerating.NONE;
	        }
	        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				//kinematics.turning(1);
	        	steer = e8Steering.LEFT;
	        }
	        else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				//kinematics.turning(2);
	        	steer = e8Steering.RIGHT;
	        }
	        else
	        {
	        	//kinematics.decreaseSpeed();
	        	steer = e8Steering.NONE;
	        }
	        
	        //Üzenet elküldése
	        send(new sMsg(eMsgType.MSG_CONTROL, new sMsgControl(accelerate, steer)));
//			long timeNow = System.currentTimeMillis();
//			System.out.println(timeNow);
		  }
	};
	
	
	public DeLoreanClientStateMachine(String ip, Camera camera) {
		super();
		state = State.DISCONNECTED;
		this.ip = ip;
		this.camera = camera;

	}
	
	public void Disconnect()
	{
		send(new sMsg(eMsgType.MSG_DISCONN_REQ,	 null));
		state = State.DISCONNECTED;
	}

	private void DeLoreanServerMsg_Conn_Req(sMsgConnReq msg)
	{
		switch(state)
		{
		case DISCONNECTED:
			state = State.CONNECTION_IN_PROGRESS;
			System.out.println("DeLorean kapcsolódás folyamatban...");
			//TODO ack elküldése
			System.out.println("A kapcsolódási kérelem megjött!");
			send(new sMsg(eMsgType.MSG_CONN_ACK, null));
			sMsgConnSetup rec = new sMsgConnSetup("Monica", e8CarType.CHALLENGER, e8CarColour.BLUE, e8ConnectionType.FIRSTCONNECTION);
			send(new sMsg(eMsgType.MSG_CONN_SETUP, rec));
			break;
		default:
			
			break;
		
		}
	}
	
	private void DeLoreanServerMsg_Race_Start(sMsgRaceStart msg)
	{
		switch(state)
		{
		case CONNECTED:
			break;
		case CONNECTION_IN_PROGRESS:
			state = State.CONNECTED;
			frameTime.scheduleAtFixedRate(frameUpdate, (long)0, (long)40);
			System.out.println("A DeLorean szerver kapcsolódott!\n A játék kezdődik: "+(int)msg.u8Countdown+" másodpercen belül!");
			break;
		case DISCONNECTED:
			break;
		default:
			Disconnect();
			break;

		}
	}
	
	private void DeLoreanServerMsg_Kinematics_Data(sMsgKinematicsData msg)
	{
		switch(state)
		{
		case CONNECTED:

			
			try {
				mutex.acquire();
				try {
					
					clientcar.kinematics.setPosition(new Vector3f(msg.si32PositionClientX, msg.si32PositionClientY, msg.si32PositionClientZ));
					clientcar.kinematics.setHeading(msg.si32HeadingDegClient);
					clientcar.kinematics.setFrontWheelHeading(msg.si32FrontWheelHeadingSClient);
					clientcar.kinematics.setVelocity(msg.si32VelocityClient);
					servercar.kinematics.setPosition(new Vector3f(msg.si32PositionServerX, msg.si32PositionServerY, msg.si32PositionServerZ));
					servercar.kinematics.setHeading(msg.si32HeadingDegServer);
					servercar.kinematics.setFrontWheelHeading(msg.si32FrontWheelHeadingServer);
					servercar.kinematics.setVelocity(msg.si32VelocityServer);
					
//					clientcar.kinematics.renderPreProcess();

					
//					Challenger.MoveCar(clientcar.eChallenger, clientcar.kinematics);
//					Challenger.MoveCar(servercar.eChallenger, servercar.kinematics);
					servercar.kinematics.calculateFromLocation();
					clientcar.kinematics.calculateFromLocation();
					
					servercar.kinematics.renderPreProcess();
					clientcar.kinematics.renderPreProcess();
					
					servercar.moveChallenger(camera.getPosition());
					clientcar.moveChallenger(camera.getPosition());
					
//					clientcar.kinematics.calculateFromLocation();

				} finally {
					mutex.release();
				}
			}
			catch(InterruptedException ie) {
				
			}
			

			
	
		
			break;
		case CONNECTION_IN_PROGRESS:
			break;
		case DISCONNECTED:
			break;
		default:
			Disconnect();
			break;

		}
	}
	
	private class ReceiverThread implements Runnable {

		public void run() {
			System.out.println("Szerver DeLorean csatlakozását várjuk...");
			try {
				while (true) {
					sMsg received = (sMsg) in.readObject();
					System.out.println("Üzenet érkezett  kliens oldalán.");
					//ctrl.clickReceived(received);
					//long timeNow = System.currentTimeMillis();
					boolean msgIsOK = MsgCheck(received);
					if (!msgIsOK)
					{
						Disconnect();
					}

					eMsgType msgType = eMsgType.fromValue(received.sHeader.u8MessageType);
					System.out.println("Az üzenet típusa:"+msgType);
					switch (msgType)
					{
					case MSG_CONN_REQ:
						DeLoreanServerMsg_Conn_Req((sMsgConnReq)received.sRecord);
						break;
					case MSG_DISCONN_REQ:
						break;
					case MSG_GAME_OVER:
						break;
					case MSG_KINEMATICS_DATA:
						DeLoreanServerMsg_Kinematics_Data((sMsgKinematicsData)received.sRecord);
						break;
					case MSG_RACE_START:
						DeLoreanServerMsg_Race_Start((sMsgRaceStart)received.sRecord);
						break;
					case MSG_REQ_CONTROL:
						break;
					default:
						Disconnect();
						break;
		
					}
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				System.err.println("A Szerver lebomlott!");
				disconnect();
				connect(ip);
			} finally {
				disconnect();
			}
		}
	}
	
	private boolean MsgCheck(sMsg msg)
	{
		boolean msgLengthIsOK = false;
		boolean msgTypeIsOK = false;
		boolean msgRecLengthIsOK = false;
		boolean msgGenIsOK = false;
		int msgLength = (int)msg.sHeader.u8Length;
		int msgGeneration = (int)msg.sHeader.u8Generation;
		int msgRecordLength = (int)msg.sHeader.u8RecordLenght;
		
		// Generáció ellenőrzés
		if (msgGeneration == DeLoreanGeneration)
		{
			msgGenIsOK = true;
		}
		
		// Üzenettípus és rekord ellenőrzés
		eMsgType msgType = eMsgType.fromValue(msg.sHeader.u8MessageType);
		switch (msgType)
		{
		case MSG_CONN_ACK:
			msgTypeIsOK = false;
			if (msgRecordLength == 0)
			{
				msgRecLengthIsOK = true;
			}
			break;
		case MSG_CONN_REQ:
			msgTypeIsOK = true;
			sMsgConnReq record = (sMsgConnReq)msg.sRecord;
			
			if (msgRecordLength == record.u8NameLength+5)
			{
				msgRecLengthIsOK = true;
			}
			break;
		case MSG_CONN_SETUP:
			msgTypeIsOK = false;
			break;
		case MSG_CONTROL:
			msgTypeIsOK = false;
			break;
		case MSG_DISCONN_REQ:
			msgTypeIsOK = true;
			if (msgRecordLength == 0)
			{
				msgRecLengthIsOK = true;
			}
			break;
		case MSG_GAME_OVER:
			msgTypeIsOK = true;
			if (msgRecordLength == 9)
			{
				msgRecLengthIsOK = true;
			}
			break;
		case MSG_KINEMATICS_DATA:
			msgTypeIsOK = true;
			if (msgRecordLength == 52)
			{
				msgRecLengthIsOK = true;
			}
			break;
		case MSG_RACE_START:
			msgTypeIsOK = true;
			if (msgRecordLength == 1)
			{
				msgRecLengthIsOK = true;
			}
			break;
		case MSG_REQ_CONTROL:
			msgTypeIsOK = true;
			if (msgRecordLength == 0)
			{
				msgRecLengthIsOK = true;
			}
			break;
		case MSG_REQ_DATA:
			msgTypeIsOK = false;
			break;
		default:
			msgTypeIsOK = false;
			break;
		}

		// Teljes hossz ellenőrzés
		if (msgLength == (msgRecordLength+4))
		{
			msgLengthIsOK = true;
		}
		
		if (!(msgTypeIsOK && msgLengthIsOK && msgRecLengthIsOK && msgGenIsOK))
		{
			System.out.println("Hibás üzenet");
		}
		
		return (msgTypeIsOK && msgLengthIsOK && msgRecLengthIsOK && msgGenIsOK);
	}
	
	@Override
	public void connect(String ip) {
		disconnect();
		try {
			socket = new Socket(ip, 9000);

			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.flush();

			Thread rec = new Thread(new ReceiverThread());
			rec.start();
		} catch (UnknownHostException e) {
			System.err.println("Nincs információm a hostról");
		} catch (IOException e) {
			System.err.println("Nem találom a portot!");
			JOptionPane.showMessageDialog(null, "A kliens nem tudott a szerverhez csatlakozni!");
		}
	}

	@Override
	public void disconnect() {
		state = State.DISCONNECTED;
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (socket != null)
				socket.close();
		} catch (IOException ex) {
			System.err.println("Hiba történt a kapcsolat lezárása közben!");
		}
	}

	@Override
	public void send(sMsg send) {
		// TODO Auto-generated method stub

		if (out == null)
			return;
		
		try {
			out.writeObject(send);
			out.flush();
//			long timeNow = System.currentTimeMillis();
//			System.out.println(timeNow);
		} catch (IOException ex) {
			System.err.println("Send error.");
		}
	}



	@Override
	public void setCar(Challenger car1, Challenger car2) {
		// TODO Auto-generated method stub
		this.servercar = car1;
		this.clientcar = car2;
	}

}
