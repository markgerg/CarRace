package network;

import java.io.Serializable;

import cars.e8Accelerating;
import cars.e8Steering;

public class sMsgControl extends sRecord implements Serializable  {
	byte u8Accelerate;
	byte u8Steer;
	
	
	public sMsgControl(e8Accelerating accelerate, e8Steering steer) {
		super();
		this.u8Accelerate = accelerate.getValue();
		this.u8Steer = steer.getValue();
	}
	
	
}
