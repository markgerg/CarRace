package network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import cars.Challenger;
import cars.e8Accelerating;
import cars.e8CarColour;
import cars.e8CarType;
import cars.e8Steering;
import common.e8State;
import engineTester.MainGameLoop;

import track.e8TrackID;



public class DeLoreanServerStateMachine extends Network {
	
	public enum State {
		DISCONNECTED,
		CONNECTION_IN_PROGRESS,
		CONNECTED
	}
	
	Challenger servercar;
	Challenger clientcar;
	
	private State state;
	Timer timer = new Timer();
	Timer timerReconn = new Timer();
	private e8ConnectionType connType;
	int DeLoreanGeneration = 1;
	
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	
	TimerTask queryFirsttime = new TimerTask() {
		  @Override
		  public void run() {
			  State innerState = state;
			  switch (innerState)
			  {
			case DISCONNECTED:
				sMsgConnReq rec = new sMsgConnReq("Chandler", e8CarType.CHALLENGER, e8CarColour.RED, e8TrackID.TRACK1, e8ConnectionType.FIRSTCONNECTION);
				System.out.println("Kapcsolódni akar..");
				send(new sMsg(eMsgType.MSG_CONN_REQ, rec));
				break;
			default:
				Disconnect();
				break;
			  }
		  }
	};
	
	TimerTask queryReconn = new TimerTask() {
		  @Override
		  public void run() {
			  State innerState = state;
			  switch (innerState)
			  {
			case DISCONNECTED:
				sMsgConnReq rec = new sMsgConnReq("Chandler", e8CarType.CHALLENGER, e8CarColour.RED, e8TrackID.TRACK1, e8ConnectionType.RECONNECTION);
				System.out.println("Újracsatlakozás..");
				send(new sMsg(eMsgType.MSG_CONN_REQ, rec));
				break;
			default:
				Disconnect();
				break;
			  }
		  }
	};
	
	public DeLoreanServerStateMachine(State state) {
		super();
		this.state = State.DISCONNECTED;
		
		this.connType = e8ConnectionType.FIRSTCONNECTION;
	
		// Kapcsolatfelvétel újraküldése
		timer.scheduleAtFixedRate(queryFirsttime, (long)0, (long)20);
	}
	
	public void Disconnect()
	{
		send(new sMsg(eMsgType.MSG_DISCONN_REQ,	 null));
		state = State.DISCONNECTED;
		timer.schedule(queryReconn, (long)0, (long)5);
	}
	
	private void DeLoreanServerMsg_Conn_Ack()
	{
		switch(state)
		{
		case DISCONNECTED:
			state = State.CONNECTION_IN_PROGRESS;
			timer.cancel();
			System.out.println("DeLorean kapcsolódás folyamatban...");
			break;
		default:
			Disconnect();
			break;
		
		}
	}
	
	private void DeLoreanServerMsg_Control(sMsgControl msg)
	{
//		System.out.println(e8Accelerating.fromValue(msg.u8Accelerate));
//		System.out.println(e8Steering.fromValue(msg.u8Steer));
		// Feldolgozás
		sRecord rec = new sMsgKinematicsData(servercar.kinematics.getPosition(), servercar.kinematics.getHeading(), servercar.kinematics.getVelocity(), servercar.kinematics.getFrontWheelHeading(), clientcar.kinematics.getPosition(), clientcar.kinematics.getHeading(), clientcar.kinematics.getVelocity(), clientcar.kinematics.getFrontWheelHeading(), 0);
		sMsg sendMsg = new sMsg(eMsgType.MSG_KINEMATICS_DATA, rec);
		send(sendMsg);
	}
	

	
	private void DeLoreanServerMsg_Conn_Setup(sMsgConnSetup msg)
	{
		switch(state)
		{
		case CONNECTION_IN_PROGRESS:
			state = State.CONNECTED;
			this.connType = e8ConnectionType.RECONNECTION;
			System.out.println("A kliens kapcsolódott!");
			System.out.println("A kliens autójának típusa:"+msg.u8carType);
			send(new sMsg(eMsgType.MSG_RACE_START, new sMsgRaceStart(5)));
			break;
		default:
			Disconnect();
			break;

		}
	}
	
	private void DeLoreanServerMsg_Disconn_Req()
	{
		state = State.DISCONNECTED;
		timer.schedule(queryReconn, (long)0, (long)5);
	}
	
	private class ReceiverThread implements Runnable {

		@Override
		public void run() {
			try {
				System.out.println("Várakzás a kliensre...");
				clientSocket = serverSocket.accept();
				System.out.println("A TCP kapcsolat felépült.\nA kliens csatlakozott.");
				if (connType == e8ConnectionType.RECONNECTION)
				{
					timerReconn.scheduleAtFixedRate(queryReconn, (long)0, (long)5);
				}
			} catch (IOException e) {
				System.err.println("A TCP kapcsolat felépítése sikertelen volt.\nA TCP kapcsolatot bontom!");
				disconnect();
				connect("localhost");
				return;
			}

			try {
				out = new ObjectOutputStream(clientSocket.getOutputStream());
				in = new ObjectInputStream(clientSocket.getInputStream());
				out.flush();
			} catch (IOException e) {
				System.err.println("Hiba történt az adatfolyam érkezése során.\nA TCP kapcsolatot bontom!");
				disconnect();
				connect("localhost");
				return;
			}

			try {
				while (true) {
					sMsg received = (sMsg) in.readObject();
					//ctrl.clickReceived(received);
					//long timeNow = System.currentTimeMillis();
					boolean msgIsOK = MsgCheck(received);
					if (!msgIsOK)
					{
						send(new sMsg(eMsgType.MSG_DISCONN_REQ,	 null));		
						//System.out.println("Az üzenet típusa:"+received.sHeader.u8MessageType);
						//continue;
					}
					eMsgType msgType = eMsgType.fromValue(received.sHeader.u8MessageType);
					switch (msgType)
					{
					case MSG_CONN_ACK:
						DeLoreanServerMsg_Conn_Ack();
						break;
					case MSG_CONN_SETUP:
						DeLoreanServerMsg_Conn_Setup((sMsgConnSetup)received.sRecord);
						break;
					case MSG_CONTROL:
						DeLoreanServerMsg_Control((sMsgControl)received.sRecord);
						break;
					case MSG_DISCONN_REQ:
						DeLoreanServerMsg_Disconn_Req();
						break;
					case MSG_REQ_DATA:
						//TODO
						break;
					default:
						send(new sMsg(eMsgType.MSG_DISCONN_REQ,	 null));
						break;
								
					}
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				System.err.println("A kliens lecsatlakozott!\nA TCP kapcsolatot bontom!");
				disconnect();
				connect("localhost");
			} finally {
				disconnect();
			}
		}
		
	}
	
	// Üzenetek hosszának ellenőrzése
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
			msgTypeIsOK = true;
			if (msgRecordLength == 0)
			{
				msgRecLengthIsOK = true;
			}
			break;
		case MSG_CONN_REQ:
			msgTypeIsOK = false;
			break;
		case MSG_CONN_SETUP:
			msgTypeIsOK = true;
			sMsgConnSetup record = (sMsgConnSetup)msg.sRecord;
			
			if (msgRecordLength == record.u8NameLength+5)
			{
				msgRecLengthIsOK = true;
			}
			break;
		case MSG_CONTROL:
			msgTypeIsOK = true;
			if (msgRecordLength == 2)
			{
				msgRecLengthIsOK = true;
			}
			break;
		case MSG_DISCONN_REQ:
			msgTypeIsOK = true;
			if (msgRecordLength == 0)
			{
				msgRecLengthIsOK = true;
			}
			break;
		case MSG_GAME_OVER:
			msgTypeIsOK = false;
			break;
		case MSG_KINEMATICS_DATA:
			msgTypeIsOK = false;
			break;
		case MSG_RACE_START:
			msgTypeIsOK = false;
			break;
		case MSG_REQ_CONTROL:
			msgTypeIsOK = false;
			break;
		case MSG_REQ_DATA:
			msgTypeIsOK = true;
			if (msgRecordLength == 0)
			{
				msgRecLengthIsOK = true;
			}
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
			System.out.println("Hiba van az üzenetben");
		}
		
		return (msgTypeIsOK && msgLengthIsOK && msgRecLengthIsOK && msgGenIsOK);
	}
	


	@Override
	public
	void connect(String ip) {
		disconnect();
		try {
			serverSocket = new ServerSocket(9000);


			Thread rec = new Thread(new ReceiverThread());
			rec.start();
		} catch (IOException e) {
			System.err.println("Nem lehet kapcsolódni a következő porton: 9000.");
		}
	}

	@Override
	public
	void disconnect() {
		state = State.DISCONNECTED;
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (clientSocket != null)
				clientSocket.close();
			if (serverSocket != null)
				serverSocket.close();
		} catch (IOException ex) {
			Logger.getLogger(DeLoreanServerStateMachine.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	@Override
	public
	void send(sMsg send) {
		// TODO Auto-generated method stub
		if (out == null)
			return;


		try {
			
			out.writeObject(send);
			out.flush();

//			long timeNow = System.currentTimeMillis();
//			System.out.println(timeNow);
		} catch (IOException ex) {
			System.err.println("A szerver nem tudta elküldeni az üzenetet.");
		}
	}

	@Override
	public void setCar(Challenger car1, Challenger car2) {
		this.servercar = car1;
		this.clientcar = car2;
	}
	
}
