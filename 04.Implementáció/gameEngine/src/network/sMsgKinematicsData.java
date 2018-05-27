package network;

import java.io.Serializable;

import org.lwjgl.util.vector.Vector3f;

public class sMsgKinematicsData extends sRecord implements Serializable  {
//	Vector3f si32PositionServer;
	
	float si32PositionServerX;
	float si32PositionServerY;
	float si32PositionServerZ;
	
	float si32HeadingDegServer;
	float si32VelocityServer;
	float si32FrontWheelHeadingServer;
	
//	Vector3f si32PositionClient;
	
	float si32PositionClientX;
	float si32PositionClientY;
	float si32PositionClientZ;
	
	float si32HeadingDegClient;
	float si32VelocityClient;
	float si32FrontWheelHeadingSClient;
	
	float u32msRaceTime;

	public sMsgKinematicsData(Vector3f si32PositionServer, float si32HeadingDegServer, float si32VelocityServer,
			float si32FrontWheelHeadingServer, Vector3f si32PositionClient, float si32HeadingDegClient,
			float si32VelocityClient, float si32FrontWheelHeadingSClient, float u32msRaceTime) {
		super();
//		this.si32PositionServer = si32PositionServer;
		
		this.si32PositionServerX = si32PositionServer.x;
		this.si32PositionServerY = si32PositionServer.y;
		this.si32PositionServerZ = si32PositionServer.z;
		
		this.si32HeadingDegServer = si32HeadingDegServer;
		this.si32VelocityServer = si32VelocityServer;
		this.si32FrontWheelHeadingServer = si32FrontWheelHeadingServer;
//		this.si32PositionClient = si32PositionClient;
		
		this.si32PositionClientX = si32PositionClient.x;
		this.si32PositionClientY = si32PositionClient.y;
		this.si32PositionClientZ = si32PositionClient.z;
		
		this.si32HeadingDegClient = si32HeadingDegClient;
		this.si32VelocityClient = si32VelocityClient;
		this.si32FrontWheelHeadingSClient = si32FrontWheelHeadingSClient;
		this.u32msRaceTime = u32msRaceTime;
	}
}
