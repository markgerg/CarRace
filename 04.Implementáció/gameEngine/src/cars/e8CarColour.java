package cars;

public enum e8CarColour {
	RED((byte)30),
	BLUE((byte)45),
	GREEN((byte)60);
	
    private final byte id;
	e8CarColour(byte id) { this.id = id; }
    public byte getValue() { return id; }
}
