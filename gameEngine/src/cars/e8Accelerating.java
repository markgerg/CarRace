package cars;

public enum e8Accelerating {
	NONE((byte)10),
	FORWARD((byte)20),
	BACKWARD((byte)30);
	
    private final byte id;
	e8Accelerating(byte id) { this.id = id; }
    public byte getValue() { return id; }
}

