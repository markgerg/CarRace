package network;



public abstract class Network {
	
//	protected MainGameLoop main;

	Network() {
		
	}

	public abstract void connect(String ip);

	public abstract void disconnect();

	public abstract void send(sMsg send);
}
