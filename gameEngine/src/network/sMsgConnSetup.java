package network;

import java.io.Serializable;

import cars.e8CarColour;
import cars.e8CarType;
import track.e8TrackID;

public class sMsgConnSetup extends sRecord implements Serializable {
	String name;
	byte u8NameLength;
	byte u8ConnectionType;
	byte u8carType;
	byte u8CarColour;

	
	sMsgConnSetup(String name, e8CarType carType, e8CarColour colour, e8ConnectionType type)
	{
		super();
		this.name = name;
		this.u8NameLength = (byte)this.name.getBytes().length;
		this.u8carType = carType.getValue();
		this.u8CarColour = colour.getValue();
		this.u8ConnectionType = type.getValue();
	}
}
