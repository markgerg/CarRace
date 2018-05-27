package network;

import cars.Challenger;

public abstract class Network {
	
//	protected MainGameLoop main;

	Network() {
		
	}

	public abstract void connect(String ip);

	public abstract void disconnect();

	public abstract void send(sMsg send);
	
	public abstract void setCar(Challenger car1, Challenger car2);
}
