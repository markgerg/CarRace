package cars;

public enum e8CarType {
	CHALLENGER((byte)30),
	DELOREAN((byte)50);
	
    private final byte id;
	e8CarType(byte id) { this.id = id; }
    public byte getValue() { return id; }
}
