package gameMaster;

import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import cars.Challenger;
import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import network.DeLoreanClientStateMachine;
import network.DeLoreanServerStateMachine;
import network.Network;
import network.DeLoreanServerStateMachine.State;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class GameMain {


	private static void SinglePlayer()
	{
		DisplayManager.createDisplay(120);
		
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
        Light light = new Light(new Vector3f(10, 50, 20), new Vector3f(1, 1, 1));
        Camera camera = new Camera();
		
		Challenger singleCar = new Challenger(loader);
		
		RawModel car_tire_left_head_raw = OBJLoader.loadOBJModel("track1",	 loader);
		TexturedModel car_tire_left_head = new TexturedModel(car_tire_left_head_raw, new ModelTexture(loader.loadTexture("asphalt3")));
		ModelTexture texture_tire = car_tire_left_head.getTexture();
		texture_tire.setShineDamper(5);
		texture_tire.setReflectivity(1);
		Entity trackE = new Entity(car_tire_left_head, new Vector3f(0, 0, 0), 0, 0, 0, 1);
		
		Timer frameUpdateSeq = new Timer();
		TimerTask frameUpdate = new TimerTask() {

			@Override
			public void run() {
//	            System.out.println("Eltelt 20ms-um.");
	            singleCar.moveChallenger();
			}
			
		};
		
		frameUpdateSeq.scheduleAtFixedRate(frameUpdate, (long)3, (long)20);
		
		while(!Display.isCloseRequested())
		{
	        camera.setPosition(singleCar.kinematics.getPosition());									// Az autó mozgatja
//			camera.moveWithKeyboardcontrol();
			renderer.prepare();
			shader.start();
			shader.loadLight(light);
			shader.loadViewMatrix(camera);
			
			
			singleCar.RenderCar(renderer, shader);
			renderer.render(trackE, shader);
		
			DisplayManager.updateDisplay();
			
			shader.stop();


		}
		
		// A vége, felszabadítások
		shader.cleanUp();
		loader.cleanUp();
		
		DisplayManager.deleteDisplay();
	}
	
	private static void MultiPlayer()
	{
		
		
		
		DisplayManager.createDisplay(120);
		
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
        Light light = new Light(new Vector3f(10, 50, 20), new Vector3f(1, 1, 1));
        Camera camera = new Camera();
        
        Network net = null; 
		
		Challenger serverCar = new Challenger(loader);
		Challenger clientCar = new Challenger(loader);
		
//		if (net != null)
//			net.disconnect();
//		net = new DeLoreanServerStateMachine(State.DISCONNECTED);
//		net.connect("192.168.1.104");
//		net.setCar(serverCar, clientCar);
		
		if (net != null)
			net.disconnect();
		net = new DeLoreanClientStateMachine("192.168.1.104");
		net.connect("192.168.1.104");
		net.setCar(serverCar, clientCar);
		
		Timer frameUpdateSeq = new Timer();
		TimerTask frameUpdate = new TimerTask() {

			@Override
			public void run() {
//	            System.out.println("Eltelt 20ms-um.");
	            
			}
			
		};
		
		frameUpdateSeq.scheduleAtFixedRate(frameUpdate, (long)3, (long)20);
		
		while(!Display.isCloseRequested())
		{
	        camera.setPosition(new Vector3f(0, 0, -10));									// Az autó mozgatja
			renderer.prepare();
			shader.start();
			shader.loadLight(light);
			shader.loadViewMatrix(camera);
			
			
			serverCar.RenderCar(renderer, shader);
			
		
			DisplayManager.updateDisplay();
			
			shader.stop();


		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Általános tulajdonságok
		e8GameType gameType = e8GameType.MULTIPLAYER;
		
		// Választás single player vagy multiplayer között
		
		switch ( gameType )
		{
		case SINGLEPLAYER:
			SinglePlayer();
			break;
		case MULTIPLAYER:
			MultiPlayer();
			break;
		default:
			break;
		}
	}
}
