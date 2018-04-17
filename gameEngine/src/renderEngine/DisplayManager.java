package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;


public class DisplayManager {
	
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS = 120;

	public static void createDisplay()
	{
		System.out.println("Az OpenGL betöltése...");
		ContextAttribs contextAtrributes = new ContextAttribs(3, 2).withProfileCore(true).withForwardCompatible(true);
		//attributes.withForwardCompatible(true);
		//attributes.withProfileCore(true);
		

		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), contextAtrributes);
			Display.setTitle("Car Race");
			System.out.println("Az OpenGL verzió száma: " + GL11.glGetString(GL11.GL_VERSION));
			
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}
	
	public static void updateDisplay()
	{
		Display.sync(FPS);
		Display.update();
	}
	
	public static void deleteDisplay()
	{
		Display.destroy();
	}
	
}
