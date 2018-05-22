package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;

import javax.swing.JOptionPane;

import cars.e8CarColour;
import cars.e8CarType;
import engineTester.MainGameLoop;
import network.DeLoreanServerStateMachine.State;
import track.e8TrackID;



public class DeLoreanClientStateMachine extends Network {

	public enum State {
		DISCONNECTED,
		CONNECTION_IN_PROGRESS,
		CONNECTED
	}
	
	private State state;
	Timer timer = new Timer();
	int DeLoreanGeneration = 1;
	
	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	
	
	
	public DeLoreanClientStateMachine() {
		super();
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
			// Disconnect TODO
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
			System.out.println("A DeLorean szerver kapcsolódott!\n A játék kezdődik: "+(int)msg.u8Countdown+" másodpercen belül!");
			break;
		case DISCONNECTED:
			break;
		default:
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
						// Disconnect TODO
						// Fejezzük be a ciklust
					}

					eMsgType msgType = eMsgType.fromValue(received.sHeader.u8MessageType);
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
						break;
					case MSG_RACE_START:
						DeLoreanServerMsg_Race_Start((sMsgRaceStart)received.sRecord);
						break;
					case MSG_REQ_CONTROL:
						break;
					default:
						break;
		
					}
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				System.err.println("A Szerver lebomlott!");
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

}
