package gameMaster;

import network.eMsgType;

public enum e8GameType {
	SINGLEPLAYER((byte)1),
	MULTIPLAYER((byte)12);
	
    private final byte id;
    e8GameType(byte id) { this.id = id; }
    public byte getValue() { return id; }
    
    static e8GameType fromValue(byte id)
    {
    	for (e8GameType my: e8GameType.values()) {
            if (my.id == id) {
                return my;
            }
        }
		return SINGLEPLAYER;

    }
}
