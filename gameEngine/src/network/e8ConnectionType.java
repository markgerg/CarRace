package network;

public enum e8ConnectionType {

	FIRSTCONNECTION((byte)10),
	RECONNECTION((byte)20);
	
    private final byte id;
	e8ConnectionType(byte id) { this.id = id; }
    public byte getValue() { return id; }
}
