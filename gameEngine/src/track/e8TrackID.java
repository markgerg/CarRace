package track;

public enum e8TrackID {
	TRACK1((byte)10);
	
    private final byte id;
	e8TrackID(byte id) { this.id = id; }
    public byte getValue() { return id; }
}
