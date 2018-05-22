package network;

public enum eMsgType {
    MSG_CONN_REQ((byte)99), 
    MSG_CONN_ACK((byte)100),
	MSG_CONN_SETUP((byte)101),
	MSG_DISCONN_REQ((byte)102),
	MSG_RACE_START((byte)103),
	MSG_KINEMATICS_DATA((byte)104),
	MSG_CONTROL((byte)105),
	MSG_REQ_DATA((byte)106),
	MSG_REQ_CONTROL((byte)107),
	MSG_GAME_OVER((byte)108);

    private byte id;
    eMsgType(byte id) { this.id = id; }
    public byte getValue() { return id; }
    public void setValue(byte id) { this.id = id; }
}
