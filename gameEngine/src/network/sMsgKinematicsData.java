package network;

import java.io.Serializable;

import org.lwjgl.util.vector.Vector3f;

public class sMsgKinematicsData extends sRecord implements Serializable  {
	Vector3f si32PositionServer;
	float si32HeadingDegServer;
	float si32VelocityServer;
	float si32FrontWheelHeadingServer;
	
	Vector3f si32PositionClient;
	float si32HeadingDegClient;
	float si32VelocityClient;
	float si32FrontWheelHeadingSClient;
	
	float u32msRaceTime;

	public sMsgKinematicsData(Vector3f si32PositionServer, float si32HeadingDegServer, float si32VelocityServer,
			float si32FrontWheelHeadingServer, Vector3f si32PositionClient, float si32HeadingDegClient,
			float si32VelocityClient, float si32FrontWheelHeadingSClient, float u32msRaceTime) {
		super();
		this.si32PositionServer = si32PositionServer;
		this.si32HeadingDegServer = si32HeadingDegServer;
		this.si32VelocityServer = si32VelocityServer;
		this.si32FrontWheelHeadingServer = si32FrontWheelHeadingServer;
		this.si32PositionClient = si32PositionClient;
		this.si32HeadingDegClient = si32HeadingDegClient;
		this.si32VelocityClient = si32VelocityClient;
		this.si32FrontWheelHeadingSClient = si32FrontWheelHeadingSClient;
		this.u32msRaceTime = u32msRaceTime;
	}
}
