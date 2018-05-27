package gameMaster;

import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.SlickException;

import audio.AudioMaster;
import automenu.frame1;
import cars.Challenger;
import cars.e8CarColour;
import common.e8SocketType;
import common.e8State;
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
import track.Circuit1;

public class GameMain {

	public static boolean gameRunning;
	public static e8State state;
	
	private static void SinglePlayer()
	{
		DisplayManager.createDisplay(60);
		
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
        Light light = new Light(new Vector3f(10, 50, 20), new Vector3f(1, 1, 1));
        Camera camera = new Camera();

		AudioMaster.init();		//main függvényből legyen majd meghívva
		AudioMaster.setListenerData();
		

		
		// Az autó létrehozása
		Challenger singleCar = new Challenger(loader, e8CarColour.BLUE, new Vector3f(0, 0, 0));
		
		// A pálya létrehozása
		Circuit1 circuit = new Circuit1(loader, shader, renderer);

		// A főciklus
		while(!Display.isCloseRequested())
		{
	        camera.setPosition(singleCar.kinematics.getPosition(), singleCar.kinematics.getHeading(), singleCar.kinematics.getFrontWheelHeading());									// Az autó mozgatja
//			camera.moveWithKeyboardcontrol();
			renderer.prepare();
			shader.start();
			shader.loadLight(light);
			shader.loadViewMatrix(camera);
			
            singleCar.moveChallenger(camera.getPosition());
			
			singleCar.RenderCar(renderer, shader);
			circuit.RenderCircuit();
		
			DisplayManager.updateDisplay();
			
			shader.stop();


		}
		
		// A vége, felszabadítások
		singleCar.delete();
		shader.cleanUp();
		loader.cleanUp();
		AudioMaster.cleanUp();
		DisplayManager.deleteDisplay();
	}
	
	private static void MultiPlayer(e8SocketType socketType, String ip)
	{
		
		
		
		DisplayManager.createDisplay(60);
		
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
        Light light = new Light(new Vector3f(10, 50, 20), new Vector3f(1, 1, 1));
        Camera camera = new Camera();
        
		AudioMaster.init();		//main függvényből legyen majd meghívva
		AudioMaster.setListenerData();
        
        Network net = null; 
		
		Challenger serverCar = new Challenger(loader, e8CarColour.RED, new Vector3f(-3, 0, 0));
		Challenger clientCar = new Challenger(loader, e8CarColour.BLUE, new Vector3f(3, 0, 0));
		
		Circuit1 circuit = new Circuit1(loader, shader, renderer);
		

		
		switch (socketType)
		{
		case CLIENT:
			if (net != null)
			net.disconnect();
		net = new DeLoreanClientStateMachine(ip, camera);
		net.connect(ip);
		net.setCar(serverCar, clientCar);
			break;
		case SERVER:
			if (net != null)
				net.disconnect();
			net = new DeLoreanServerStateMachine(State.DISCONNECTED, camera);
			net.connect(ip);
			net.setCar(serverCar, clientCar);
			break;
		default:
			break;
		
		}
		

		

//		
		Timer frameUpdateSeq = new Timer();
		TimerTask frameUpdate = new TimerTask() {

			@Override
			public void run() {
//	            System.out.println("Eltelt 20ms-um.");
				serverCar.moveChallenger(camera.getPosition());
				
			}
			
		};
		
		//frameUpdateSeq.scheduleAtFixedRate(frameUpdate, (long)3, (long)20);
		
		while(!Display.isCloseRequested())
		{
			switch (socketType)
			{
			case CLIENT:
				camera.setPosition(clientCar.kinematics.position, clientCar.kinematics.getHeading(), clientCar.kinematics.getFrontWheelHeading());
				break;
			case SERVER:
				camera.setPosition(serverCar.kinematics.position, serverCar.kinematics.getHeading(), serverCar.kinematics.getFrontWheelHeading());
				break;
			default:
				break;
			
			}
	        								// Az autó mozgatja
			renderer.prepare();
			shader.start();
			shader.loadLight(light);
			shader.loadViewMatrix(camera);
			
			clientCar.RenderCar(renderer, shader);
			serverCar.RenderCar(renderer, shader);
			
			circuit.RenderCircuit();
			
		
			DisplayManager.updateDisplay();
			
			shader.stop();


		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		// A játékot mindig a menüvel kezdjük
		state = e8State.MENU;
		e8GameType gameType = e8GameType.SINGLEPLAYER;
		e8SocketType socketType = e8SocketType.SERVER;
		String ip = "localhost";

		
		while(true)
		{
			frame1.guiMain();
			Thread.sleep(1);
			while (state == e8State.MENU)
			{

				if (frame1.startGame == true)
				{
					state = e8State.GAME;

				}
				Thread.sleep(1);
				
			}
			frame1.startGame = false;
			try 
			{
				gameType = frame1.propr.getGameType();
				socketType = frame1.propr.getSocketType();
				ip = frame1.propr.getIp();
			} 
			catch (NullPointerException e)
			{
				gameType = e8GameType.SINGLEPLAYER;
			}
			

			// Általános tulajdonságok
			
		
			
			// Választás single player vagy multiplayer között
			
			switch ( gameType )
			{
			case SINGLEPLAYER:
				System.out.println("Single player indult");
				SinglePlayer();
				
				break;
			case MULTIPLAYER:
				System.out.println("Multiplayer player indult, ip:"+ip);
				MultiPlayer(socketType, ip);
				break;
			default:
				break;
			}
			state = e8State.MENU;
		}
	}
}
