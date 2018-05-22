package network;

import java.io.Serializable;

public class sMsgRaceStart extends sRecord implements Serializable {
	byte u8Countdown;

	public sMsgRaceStart(int countdown) {
		super();
		this.u8Countdown = (byte)countdown;
	}
}
