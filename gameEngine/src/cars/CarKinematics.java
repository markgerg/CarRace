package cars;

import org.lwjgl.util.vector.Vector3f;

public class CarKinematics {
	Vector3f 	position;
	float 		velocity;
	float		acceleration;
	float		turning;
	
	public CarKinematics(Vector3f position) {
		super();
		this.position = position;
		this.acceleration = 0;
		this.velocity = 0;
		this.turning = 0;
	}

	public void increaseSpeed()
	{
		this.velocity += 0.1f;
		this.position.z += this.velocity*0.01f;
	}
	
	public void decreaseSpeed()
	{
		if ( 0.0f < this.velocity )
		{
			this.velocity -= 0.05f;
			this.position.z += this.velocity*0.01f;
		}
	}
	
	public void breakingCar()
	{
		if ( 0.0f < this.velocity )
		{
			this.velocity -= 0.2f;
			this.position.z += this.velocity*0.01f;
		}

	}
}
