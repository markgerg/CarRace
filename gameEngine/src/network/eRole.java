package network;

public enum eRole {
	SERVER((byte)20),
	CLIENT((byte)30);
	
    private final byte id;
    eRole(byte id) { this.id = id; }
    public byte getValue() { return id; }
}
