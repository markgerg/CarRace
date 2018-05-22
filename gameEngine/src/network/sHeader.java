package network;

import java.io.Serializable;

public class sHeader implements Serializable {
	// A struktúra tartozékai
	byte u8Length;
	byte u8Generation;
	byte u8MessageType;
	byte u8RecordLenght;
	
	//Konstruktor a struktúra kitöltésére
	sHeader(int Length, int Generation, eMsgType msg)
	{
		this.u8Generation = (byte)Generation;
		this.u8MessageType = (byte)msg.getValue();
		switch (msg)
		{
		case MSG_CONN_REQ:
			this.u8RecordLenght = (byte)39;
			this.u8Length = (byte)43;
			break;
		case MSG_CONN_ACK:
			this.u8RecordLenght = (byte)0;
			this.u8Length = (byte)4;
		case MSG_CONN_SETUP:
			this.u8RecordLenght = (byte)38;
			this.u8Length = (byte)42;
			break;
		case MSG_CONTROL:
			this.u8RecordLenght = (byte)2;
			this.u8Length = (byte)6;
			break;
		case MSG_DISCONN_REQ:
			this.u8RecordLenght = (byte)0;
			this.u8Length = (byte)4;
			break;
		case MSG_GAME_OVER:
			this.u8RecordLenght = (byte)9;
			this.u8Length = (byte)13;
			break;
		case MSG_KINEMATICS_DATA:
			this.u8RecordLenght = (byte)52;
			this.u8Length = (byte)56;
			break;
		case MSG_RACE_START:
			this.u8RecordLenght = (byte)5;
			this.u8Length = (byte)1;
			break;
		case MSG_REQ_CONTROL:
			this.u8RecordLenght = (byte)0;
			this.u8Length = (byte)4;
			break;
		case MSG_REQ_DATA:
			this.u8RecordLenght = (byte)0;
			this.u8Length = (byte)4;
			break;
		default:
			// TCP kapcsolat bontása
			break;
		}
	}
}
