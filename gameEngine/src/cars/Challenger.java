package cars;

import org.lwjgl.util.vector.Vector3f;

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

public class Challenger {


	
	Entity[] eChallenger = new Entity[6];
	CarKinematics kinematics = new CarKinematics(new Vector3f(0.0f, 0.0f, 0.0f));
	
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
		eChallenger[1].setPosition(kinemtaics.position);
		Vector3f tirePosition = new Vector3f((kinemtaics.position.x+0.72f), (kinemtaics.position.y+0.33f), (kinemtaics.position.z+1.5f) );
		eChallenger[2].setPosition(tirePosition);
		tirePosition = new Vector3f((kinemtaics.position.x-0.72f), (kinemtaics.position.y+0.33f), (kinemtaics.position.z+1.5f) );
		eChallenger[3].setPosition(tirePosition);
		eChallenger[3].setRotY(180);
		tirePosition = new Vector3f((kinemtaics.position.x+0.72f), (kinemtaics.position.y+0.33f), (kinemtaics.position.z-1.4f) );
		eChallenger[4].setPosition(tirePosition);
		tirePosition = new Vector3f((kinemtaics.position.x-0.72f), (kinemtaics.position.y+0.33f), (kinemtaics.position.z-1.4f) );
		eChallenger[5].setPosition(tirePosition);
		eChallenger[5].setRotY(180);
		
	}
    
    public void moveChallenger(  )
    {
		eChallenger[2].increaseRotation(8f, 0f, 0f);			// forgatjuk az objektumot
		eChallenger[3].increaseRotation(8f, 0f, 0f);			// forgatjuk az objektumot
		eChallenger[4].increaseRotation(8f, 0f, 0f);			// forgatjuk az objektumot
		eChallenger[5].increaseRotation(8f, 0f, 0f);			// forgatjuk az objektumot
    	
        if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
    		kinematics.increaseSpeed();
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			kinematics.breakingCar();
        }
        else
        {
        	kinematics.decreaseSpeed();
        }
        
        MoveCar(eChallenger, kinematics);
    }
    
	public void RenderCar(Renderer renderer, StaticShader shader )
	{
		for(int i=0; i<6; i++)
		{
			renderer.render(eChallenger[i], shader);
		}
	}
	
}
