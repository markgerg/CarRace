package network;

public class sMsgGameOver {
	float u32msRaceTimeServer;
	float u32msRaceTimeClient;
	byte  u8Winner;
	
	
	public sMsgGameOver(float u32msRaceTimeServer, float u32msRaceTimeClient, eRole winner) {
		super();
		this.u32msRaceTimeServer = u32msRaceTimeServer;
		this.u32msRaceTimeClient = u32msRaceTimeClient;
		this.u8Winner = winner.getValue();
	}
	
	
}
