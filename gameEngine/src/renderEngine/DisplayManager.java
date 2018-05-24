package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;


public class DisplayManager {
	
	private static final int WIDTH = 1920;
	private static final int HEIGHT = 1080;
	private static int FPS = 120;

	public static void createDisplay(int fps)
	{
		FPS = fps;
		System.out.println("Az OpenGL betöltése...");
		ContextAttribs contextAtrributes = new ContextAttribs(3, 2).withProfileCore(true).withForwardCompatible(true);
		//attributes.withForwardCompatible(true);
		//attributes.withProfileCore(true);
		

		
		try {
			DisplayMode displayMode = null;
	        DisplayMode[] modes = Display.getAvailableDisplayModes();

	         for (int i = 0; i < modes.length; i++)
	         {
	             if (modes[i].getWidth() == WIDTH
	             && modes[i].getHeight() == HEIGHT
	             && modes[i].isFullscreenCapable())
	               {
	                    displayMode = modes[i];
	               }
	         }
			
			Display.setDisplayMode(displayMode);
			Display.setFullscreen(false);
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
