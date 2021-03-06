package cars;

import org.lwjgl.util.vector.Vector3f;

import audio.AudioCar;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import cars.e8Accelerating;
import cars.e8Steering;



public class Challenger {

	// Az autü fő tulajdonságai (kasztni, fizikai tulajdonságok, hang)
	
	public Entity[] eChallenger = new Entity[6];
	public CarKinematics kinematics;
	public AudioCar sound = new AudioCar(1, "audio/loop_0.wav");
 
    public Challenger(Loader loader, e8CarColour colour, Vector3f startPosition) {
		super();
		
		kinematics = new CarKinematics(startPosition, 2.9f, 90f);
	
    	RawModel challengerDry = OBJLoader.loadOBJModel("challenger",	 loader);
    	TexturedModel challenger = new TexturedModel(challengerDry, new ModelTexture(loader.loadTexture("sapphire")));

    	switch ( colour )
    	{
		case BLUE:
			challenger = new TexturedModel(challengerDry, new ModelTexture(loader.loadTexture("sapphire")));
			break;
		case GREEN:
			break;
		case RED:
			challenger = new TexturedModel(challengerDry, new ModelTexture(loader.loadTexture("red")));
			break;
		default:
			challenger = new TexturedModel(challengerDry, new ModelTexture(loader.loadTexture("red")));
			break;
    	
    	}
    	
    	ModelTexture texture = challenger.getTexture();
    	texture.setShineDamper(10);
    	texture.setReflectivity(1);
    	
    	RawModel car_glass_raw = OBJLoader.loadOBJModel("car_glass",	 loader);
    	TexturedModel car_glass = new TexturedModel(car_glass_raw, new ModelTexture(loader.loadTexture("glass")));
    	ModelTexture texture_glass = car_glass.getTexture();
    	texture_glass.setShineDamper(10);
    	texture_glass.setReflectivity(3);
    	
    	RawModel car_tire_left_head_raw = OBJLoader.loadOBJModel("car_tire",	 loader);
    	TexturedModel car_tire_left_head = new TexturedModel(car_tire_left_head_raw, new ModelTexture(loader.loadTexture("glass")));
    	ModelTexture texture_tire = car_tire_left_head.getTexture();
    	texture_tire.setShineDamper(5);
    	texture_tire.setReflectivity(1);
    	
    	
    	switch ( colour )
    	{
		case BLUE:
			kinematics.position = new Vector3f(3, 0, 0);
			break;
		case GREEN:
			break;
		case RED:
			kinematics.position = new Vector3f(-3, 0, 0);
			break;
		default:
			
			break;
    	
    	}
        //Entity entity = new Entity(texturedModel, new Vector3f(0,0,-5),0,0,0,10);
        eChallenger[0] = new Entity(challenger, new Vector3f(0, 0, 0), 0, 0, 0, 1);
        eChallenger[1] = new Entity(car_glass, new Vector3f(0, 0, 0), 0, 0, 0, 1);
        eChallenger[2] = new Entity(car_tire_left_head, new Vector3f(0.72f, 0.33f, 1.5f), 0, 0, 0, 1);
        eChallenger[3] = new Entity(car_tire_left_head, new Vector3f(-0.72f, 0.33f, 1.5f), 0, 180, 0, 1);
        eChallenger[4] = new Entity(car_tire_left_head, new Vector3f(0.72f, 0.33f, -1.4f), 0, 0, 0, 1);
        eChallenger[5] = new Entity(car_tire_left_head, new Vector3f(-0.72f, 0.33f, -1.4f), 0, 180, 0, 1);
        
	}
	
    // Lejárt függvény, nem használjuk. A MoveCarSelf kiindulása
	public static void MoveCar(Entity[] eChallenger, CarKinematics kinemtaics)
	{

		eChallenger[0].setPosition(kinemtaics.position);
		eChallenger[0].setRotY(-kinemtaics.heading-90);
		eChallenger[1].setPosition(kinemtaics.position);
		eChallenger[1].setRotY(-kinemtaics.heading-90);
		eChallenger[2].setPosition(kinemtaics.frontWheelLeft);
		eChallenger[2].setRotY(-kinemtaics.frontWheelHeading-90);
		eChallenger[3].setPosition(kinemtaics.frontWheelRight);
		eChallenger[3].setRotY(-kinemtaics.frontWheelHeading-90);
		eChallenger[4].setPosition(kinemtaics.rearWheelLeft);
		eChallenger[4].setRotY(-kinemtaics.heading-90);
		eChallenger[5].setPosition(kinemtaics.rearWheelRight);
		eChallenger[5].setRotY(-kinemtaics.heading-90);
		
	}
	
	public void MoveCarSelf()
	{
		this.eChallenger[0].setPosition(this.kinematics.position);
		this.eChallenger[0].setRotY(-(this.kinematics.heading)-90);
		this.eChallenger[1].setPosition(this.kinematics.position);
		this.eChallenger[1].setRotY(-(this.kinematics.heading)-90);
		this.eChallenger[2].setPosition(this.kinematics.frontWheelLeft);
		this.eChallenger[2].setRotY(-(this.kinematics.frontWheelHeading)-90);
		this.eChallenger[3].setPosition(this.kinematics.frontWheelRight);
		this.eChallenger[3].setRotY(-(this.kinematics.frontWheelHeading)-90);
		this.eChallenger[4].setPosition(this.kinematics.rearWheelLeft);
		this.eChallenger[4].setRotY(-(this.kinematics.heading)-90);
		this.eChallenger[5].setPosition(this.kinematics.rearWheelRight);
		this.eChallenger[5].setRotY(-(this.kinematics.heading)-90);
	}
	
	public e8Accelerating getAccelerating()
	{
		e8Accelerating accelerate;
		
        if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
    		//kinematics.increaseSpeed();
        	accelerate = e8Accelerating.FORWARD;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			//kinematics.breakingCar();
        	accelerate = e8Accelerating.BACKWARD;
        }
        else {
        	accelerate = e8Accelerating.NONE;
        }
        return accelerate;
	}
	
	public e8Steering getSteering()
	{
		e8Steering steer;
		
        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			//kinematics.turning(1);
        	steer = e8Steering.LEFT;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			//kinematics.turning(2);
        	steer = e8Steering.RIGHT;
        }
        else
        {
        	//kinematics.decreaseSpeed();
        	steer = e8Steering.NONE;
        }
        return steer;
	}

    public void moveChallenger( Vector3f cameraPosition  )
    {
		for(int i=2; i<6; i++)
		{
			eChallenger[i].setSpinningSpeed(0.15f*kinematics.velocity);				// forgatjuk az objektumot
		}
    	

        
        
        kinematics.updateCarLocation(getAccelerating(), getSteering());
        
        kinematics.renderPreProcess();
        
        sound.SetCarParameters(kinematics.velocity/7+3, kinematics.position, cameraPosition);
        
        MoveCarSelf();
        
        kinematics.calculateFromLocation();
    }
    
	public void RenderCar(Renderer renderer, StaticShader shader )
	{
		for(int i=0; i<6; i++)
		{
			renderer.render(eChallenger[i], shader);
		}
	}
	
	public void delete()
	{
		sound.deleteAudioCar();
	}
	
}
