package engineTester;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import cars.Challenger;
import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import network.DeLoreanClientStateMachine;
import network.DeLoreanServerStateMachine;
import network.DeLoreanServerStateMachine.State;
import network.Network;
import textures.ModelTexture;




public class MainGameLoop {

	private static Network net = null;
	
	public static void RenderCar(Renderer renderer, StaticShader shader, Entity[] eChallenger )
	{
		for(int i=0; i<6; i++)
		{
			renderer.render(eChallenger[i], shader);
		}
	}
	
	public static void MoveCar(Entity[] eChallenger)
	{
		for(int i=0; i<6; i++)
		{
			eChallenger[i].increasePosition(0, 0, 0.02f);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		Challenger redcar = new Challenger(loader);
		Challenger netcar = new Challenger(loader);

		String pr = "Próba string jávában";
		System.out.println("A string mérete:" + pr.getBytes().length);
		
//		if (net != null)
//			net.disconnect();
//		net = new DeLoreanServerStateMachine(State.DISCONNECTED);
//		net.connect("192.168.1.104");
//		net.setCar(redcar, netcar);
		
		if (net != null)
			net.disconnect();
		net = new DeLoreanClientStateMachine("192.168.1.105");
		net.connect("192.168.1.105");
		net.setCar(redcar, netcar);

		/*
		
		//RawModel model = OBJLoader.loadOBJModel("route", loader);	
		//ModelTexture texture = new ModelTexture(loader.loadTexture("textureB"));
		//TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("asphalt")));
		
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
		
		Entity[] eChallenger = new Entity[6];
		
        //Entity entity = new Entity(texturedModel, new Vector3f(0,0,-5),0,0,0,10);
        eChallenger[0] = new Entity(challenger, new Vector3f(0, 0, 0), 0, 0, 0, 1);
        eChallenger[1] = new Entity(car_glass, new Vector3f(0, 0, 0), 0, 0, 0, 1);
        eChallenger[2] = new Entity(car_tire_left_head, new Vector3f(0.72f, 0.33f, 1.5f), 0, 0, 0, 1);
        eChallenger[3] = new Entity(car_tire_left_head, new Vector3f(-0.72f, 0.33f, 1.5f), 0, 180, 0, 1);
        eChallenger[4] = new Entity(car_tire_left_head, new Vector3f(0.72f, 0.33f, -1.4f), 0, 0, 0, 1);
        eChallenger[5] = new Entity(car_tire_left_head, new Vector3f(-0.72f, 0.33f, -1.4f), 0, 180, 0, 1);
        */
        Light light = new Light(new Vector3f(10, 10, 20), new Vector3f(1, 1, 1));
        
        Camera camera = new Camera();
		
		while(!Display.isCloseRequested())
		{
			/*eChallenger[2].increaseRotation(8f, 0f, 0f);			// forgatjuk az objektumot
			eChallenger[3].increaseRotation(8f, 0f, 0f);			// forgatjuk az objektumot
			eChallenger[4].increaseRotation(8f, 0f, 0f);			// forgatjuk az objektumot
			eChallenger[5].increaseRotation(8f, 0f, 0f);			// forgatjuk az objektumot
			MoveCar(eChallenger);*/
            //camera.moveWithKeyboardcontrol();							// kamera mozgást bevesszük a billentyűzetről
            camera.setPosition(new Vector3f(0, 0, -10));									// Az autó mozgatja
			renderer.prepare();
			shader.start();
			shader.loadLight(light);
			shader.loadViewMatrix(camera);
			redcar.moveChallenger();
			redcar.RenderCar(renderer, shader);
			//RenderCar(renderer, shader, eChallenger);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		shader.cleanUp();
		loader.cleanUp();
		
		DisplayManager.deleteDisplay();
	}

}
