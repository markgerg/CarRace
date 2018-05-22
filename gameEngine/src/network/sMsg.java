package network;

import java.io.Serializable;
import java.lang.Object;

import cars.e8CarType;
import track.e8TrackID;
import cars.e8CarColour;




public class sMsg implements Serializable {
	sHeader sHeader;
	sRecord sRecord;
	
	public sMsg(eMsgType msgType, sRecord record) {
		super();
		this.sHeader = new sHeader(0, 1, msgType);
		switch (msgType)
		{
		case MSG_CONN_ACK:
			this.sRecord = null;
			break;
		case MSG_CONN_REQ:
			this.sRecord = (sMsgConnReq)record;
			this.sHeader.u8RecordLenght = (byte) (((sMsgConnReq)record).u8NameLength+5);
			this.sHeader.u8Length = (byte) (((sMsgConnReq)record).u8NameLength+9);
			break;
		case MSG_CONN_SETUP:
			this.sRecord = (sMsgConnSetup)record;
			this.sHeader.u8RecordLenght = (byte) (((sMsgConnSetup)record).u8NameLength+5);
			this.sHeader.u8Length = (byte) (((sMsgConnSetup)record).u8NameLength+9);
			break;
		case MSG_CONTROL:
			break;
		case MSG_DISCONN_REQ:
			this.sRecord = null;
			break;
		case MSG_GAME_OVER:
			break;
		case MSG_KINEMATICS_DATA:
			break;
		case MSG_RACE_START:
			this.sRecord = (sMsgRaceStart)record;
			break;
		case MSG_REQ_CONTROL:
			break;
		case MSG_REQ_DATA:
			break;
		default:
			break;
		
		}
	}
	
	
}
