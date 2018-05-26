package cars;

import network.eMsgType;

public enum e8Accelerating {
	NONE((byte)10),
	FORWARD((byte)20),
	BACKWARD((byte)30);
	
    private final byte id;
	e8Accelerating(byte id) { this.id = id; }
    public byte getValue() { return id; }
    
    public static e8Accelerating fromValue(byte id)
    {
    	for (e8Accelerating my: e8Accelerating.values()) {
            if (my.id == id) {
                return my;
            }
        }
		return NONE;

    }
}

