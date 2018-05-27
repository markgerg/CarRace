package network;

import java.io.Serializable;

import cars.e8CarColour;
import cars.e8CarType;
import track.e8TrackID;

public class sMsgConnReq extends sRecord implements Serializable {

	String name;
	byte u8NameLength;
	byte u8ConnectionType;
	byte u8carType;
	byte u8CarColour;
	byte u8RaceTrack;

	
	sMsgConnReq(String name, e8CarType carType, e8CarColour colour, e8TrackID track, e8ConnectionType type)
	{
		super();
		this.name = name;
		this.u8NameLength = (byte)this.name.getBytes().length;
		this.u8carType = carType.getValue();
		this.u8CarColour = colour.getValue();
		this.u8RaceTrack = track.getValue();
		this.u8ConnectionType = type.getValue();
	}
}
