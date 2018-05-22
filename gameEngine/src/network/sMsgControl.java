package network;

import cars.e8Accelerating;
import cars.e8Steering;

public class sMsgControl {
	byte u8Accelerate;
	byte u8Steer;
	
	
	public sMsgControl(e8Accelerating accelerate, e8Steering steer) {
		super();
		this.u8Accelerate = accelerate.getValue();
		this.u8Steer = steer.getValue();
	}
	
	
}
