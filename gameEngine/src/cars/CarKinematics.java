package cars;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.*;
import org.lwjgl.util.*;
import java.math.*;
import toolbox.Maths;

public class CarKinematics {
	
	// Az autó világ szerinti relatív adatai
	Vector3f 	position;
	float		heading;
	
	// Az autó lokális tulajdonságai
	float 		velocity;
	float		maxSpeed;
	float 		steerAngle;
	float		wheelBase;
	float		axleBase;
	
	// Az autó kerekeinek helyzete
	float		frontWheelHeading = 0;
	Vector3f	frontWheelLeft = new Vector3f(0.0f, 0.0f, 0.0f);
	Vector3f 	rearWheelLeft = new Vector3f(0.0f, 0.0f, 0.0f);
	Vector3f	frontWheelRight = new Vector3f(0.0f, 0.0f, 0.0f);
	Vector3f 	rearWheelRight = new Vector3f(0.0f, 0.0f, 0.0f);
	
	float 		dt = 0.005f;			// Két frissítés között eltelt idő

	public CarKinematics(Vector3f position, float wheelBase, float maxSpeed) {
		super();
		// A kasztni pozíciójának meghatározása (pozíció + szög)
		this.position = position;
		this.heading = 45;
		this.steerAngle = 0;
		// Tengelytávolság meghatározása
		this.wheelBase = wheelBase;
		this.axleBase = 0.72f;
		this.maxSpeed = maxSpeed;
		
		calculateFromLocation();
		

		

		

		this.velocity = 0;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getHeading() {
		return heading;
	}

	public float getVelocity() {
		return velocity;
	}

	public float getFrontWheelHeading() {
		return frontWheelHeading;
	}

	public void calculateFromLocation()
	{
		//Első kerekek pozíciójának meghatározása (pozíció + szög)
		this.frontWheelLeft.x = position.x - wheelBase/2 * Maths.cos(heading) + axleBase * Maths.cos(heading+90);
		this.frontWheelLeft.y = 0.33f;
		this.frontWheelLeft.z = position.z - wheelBase/2 * Maths.sin(heading) + axleBase * Maths.sin(heading+90);
		
		this.frontWheelRight.x = position.x - wheelBase/2 * Maths.cos(heading) + axleBase * Maths.cos(heading-90);
		this.frontWheelRight.y = 0.33f;
		this.frontWheelRight.z = position.z - wheelBase/2 * Maths.sin(heading) + axleBase * Maths.sin(heading-90);
		
		frontWheelHeading = heading + steerAngle;
		
		//Hátsó kerekek pozíciójának meghatározása (csak pozíció)
		this.rearWheelLeft.x = position.x + wheelBase/2 * Maths.cos(heading) - axleBase * Maths.cos(heading+90);
		this.rearWheelLeft.y = 0.33f;
		this.rearWheelLeft.z = position.z + wheelBase/2 * Maths.sin(heading) - axleBase * Maths.sin(heading+90);
		
		this.rearWheelRight.x = position.x + wheelBase/2 * Maths.cos(heading) - axleBase * Maths.cos(heading-90);
		this.rearWheelRight.y = 0.33f;
		this.rearWheelRight.z = position.z + wheelBase/2 * Maths.sin(heading) - axleBase * Maths.sin(heading-90);
	}
	
	public void renderPreProcess()
	{
		position.x = ((frontWheelRight.x + frontWheelLeft.x)/2 + (rearWheelRight.x + rearWheelLeft.x)/2)/2;
		position.z = ((frontWheelRight.z + frontWheelLeft.z)/2 + (rearWheelRight.z + rearWheelLeft.z)/2)/2;
		
//		heading = Maths.atan((frontWheelRight.z - rearWheelRight.z), (frontWheelRight.x - rearWheelRight.x));
		heading = Maths.atan(((frontWheelRight.z + frontWheelLeft.z)/2 - (rearWheelRight.z + rearWheelLeft.z)/2), ((frontWheelRight.x + frontWheelLeft.x)/2 - (rearWheelRight.x + rearWheelLeft.x)/2))-180;
	}
	
	public void updateCarLocation(e8Accelerating accelerate, e8Steering steer)
	{

		
		// Gyorsítási/lassítási adatok feldolgozása
		switch( accelerate )
		{
			case FORWARD:{							// Gázadás
				if (velocity < maxSpeed)
				{
					this.velocity += 0.1f;
				}
				
				System.out.println(velocity);
				break;
			}
			case BACKWARD:{							// Fékezés
				if ( 0.0f < this.velocity )
				{
					this.velocity -= 0.2f;
				}
				System.out.println("Lassul");
				break;
			}
			case NONE:{								// Gurulás/lassulás
				
			}
			default:{								// Hiba esetén is lassít kicsi
				if ( 0.0f < this.velocity )
				{
					this.velocity -= 0.05f;
				}
				else
				{
					this.velocity = 0;
				}
			}
		}
		
		// A kanyarodási adatok feldolgozása
		switch( steer )
		{
			case LEFT:{								// Balra kanyarodás
				if( steerAngle > -15f )
				{
					this.steerAngle -= 0.5f;
					System.out.println("Balra");
				}
				break;
			}
			case RIGHT:{							// Jobbra kanyarodás
				if ( steerAngle < +15f )
				{
					this.steerAngle += 0.5f;
					System.out.println("Jobbra");
				}
				break;
			}
			case NONE:{								// Gurulás/lassulás
				
			}
			default:{								// Hiba esetén is lassít kicsi
				if ( steerAngle < 0 )
				{
					this.steerAngle += 0.2f;
				}
				else if( steerAngle > 0 )
				{
					this.steerAngle -= 0.2f;
				}
				else
				{
					this.steerAngle = 0;
				}
			}
		}
		
		// Kerekek új pozíciójának és szögének kiszámítása
		frontWheelRight.x -= velocity * dt * Maths.cos(heading+steerAngle);
		frontWheelRight.z -= velocity * dt * Maths.sin(heading+steerAngle);
		
		frontWheelLeft.x -= velocity * dt * Maths.cos(heading+steerAngle);
		frontWheelLeft.z -= velocity * dt * Maths.sin(heading+steerAngle);
//		
		rearWheelLeft.x -= velocity * dt * Maths.cos(heading);
		rearWheelLeft.z -= velocity * dt * Maths.sin(heading);
		
		rearWheelRight.x -= velocity * dt * Maths.cos(heading);
		rearWheelRight.z -= velocity * dt * Maths.sin(heading);
//		

	}
	

}
