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


	
	public Entity[] eChallenger = new Entity[6];
	public CarKinematics kinematics = new CarKinematics(new Vector3f(0.0f, 0.0f, 0.0f), 2.9f, 50f);
	public AudioCar sound = new AudioCar(1, "audio/loop_0.wav");
	

	
	public enum Color
	{
		RED,
		BLUE,
		GREEN
	}
	
	
    
    public Challenger( Loader loader ) {
		super();
    	RawModel challengerDry = OBJLoader.loadOBJModel("challenger",	 loader);
    	TexturedModel challenger = new TexturedModel(challengerDry, new ModelTexture(loader.loadTexture("red")));
    	ModelTexture texture = challenger.getTexture();
    	texture.setShineDamper(10);
    	texture.setReflectivity(1);
    	
    	RawModel car_glass_raw = OBJLoader.loadOBJModel("car_glass",	 loader);
    	TexturedModel car_glass = new TexturedModel(car_glass_raw, new ModelTexture(loader.loadTexture("glass")));
    	ModelTexture texture_glass = car_glass.getTexture();
    	texture_glass.setShineDamper(10);
    	texture_glass.setReflectivity(1);
    	
    	RawModel car_tire_left_head_raw = OBJLoader.loadOBJModel("car_tire",	 loader);
    	TexturedModel car_tire_left_head = new TexturedModel(car_tire_left_head_raw, new ModelTexture(loader.loadTexture("glass")));
    	ModelTexture texture_tire = car_tire_left_head.getTexture();
    	texture_tire.setShineDamper(5);
    	texture_tire.setReflectivity(1);
    	
        //Entity entity = new Entity(texturedModel, new Vector3f(0,0,-5),0,0,0,10);
        eChallenger[0] = new Entity(challenger, new Vector3f(0, 0, 0), 0, 0, 0, 1);
        eChallenger[1] = new Entity(car_glass, new Vector3f(0, 0, 0), 0, 0, 0, 1);
        eChallenger[2] = new Entity(car_tire_left_head, new Vector3f(0.72f, 0.33f, 1.5f), 0, 0, 0, 1);
        eChallenger[3] = new Entity(car_tire_left_head, new Vector3f(-0.72f, 0.33f, 1.5f), 0, 180, 0, 1);
        eChallenger[4] = new Entity(car_tire_left_head, new Vector3f(0.72f, 0.33f, -1.4f), 0, 0, 0, 1);
        eChallenger[5] = new Entity(car_tire_left_head, new Vector3f(-0.72f, 0.33f, -1.4f), 0, 180, 0, 1);
        
	}

	public void loadChallenger( Loader loader, Color color )
    {

    }
	
	public static void MoveCar(Entity[] eChallenger, CarKinematics kinemtaics)
	{
		// Itt még nem jó a dolog, mert a pozíció nem relatív
		
		eChallenger[0].setPosition(kinemtaics.position);
		eChallenger[0].setRotY(-kinemtaics.heading-90);
		eChallenger[1].setPosition(kinemtaics.position);
		eChallenger[1].setRotY(-kinemtaics.heading-90);
		Vector3f tirePosition = new Vector3f((kinemtaics.frontWheelLeft.x), (kinemtaics.position.y+0.33f), (kinemtaics.frontWheelLeft.z) );
		eChallenger[2].setPosition(kinemtaics.frontWheelLeft);
		eChallenger[2].setRotY(-kinemtaics.frontWheelHeading-90);
		//tirePosition = new Vector3f((kinemtaics.frontWheel.x-0.72f), (kinemtaics.position.y+0.33f), (kinemtaics.frontWheel.z) );
		eChallenger[3].setPosition(kinemtaics.frontWheelRight);
		eChallenger[3].setRotY(-kinemtaics.frontWheelHeading-90);
		//tirePosition = new Vector3f((kinemtaics.rearWheel.x+0.72f), (kinemtaics.position.y+0.33f), (kinemtaics.rearWheel.z) );
		eChallenger[4].setPosition(kinemtaics.rearWheelLeft);
		eChallenger[4].setRotY(-kinemtaics.heading-90);
		//tirePosition = new Vector3f((kinemtaics.rearWheel.x-0.72f), (kinemtaics.position.y+0.33f), (kinemtaics.rearWheel.z) );
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
	

    
    public void moveChallenger(  )
    {
		for(int i=2; i<6; i++)
		{
			eChallenger[i].setSpinningSpeed(0.15f*kinematics.velocity);				// forgatjuk az objektumot
		}
    	

    	
		e8Accelerating accelerate;
		e8Steering steer;
		
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

        
        
        kinematics.updateCarLocation(accelerate, steer);
        
        kinematics.renderPreProcess();
        
        sound.SetCarParameters(kinematics.velocity/7, kinematics.position, new Vector3f(0, 0, -10));
        
        MoveCarSelf();
        
        kinematics.calculateFromLocation();
    }
    
	public void RenderCar(Renderer renderer, StaticShader shader )
	{
		//renderer.render(eChallenger[2], shader);
		for(int i=0; i<6; i++)
		{
			renderer.render(eChallenger[i], shader);
		}
	}
	
}
