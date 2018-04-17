package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		

		
		
		RawModel model = OBJLoader.loadOBJModel("route", loader);	
		//ModelTexture texture = new ModelTexture(loader.loadTexture("textureB"));
		TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("asphalt")));
		
		RawModel treeDry = OBJLoader.loadOBJModel("tree",	 loader);
		TexturedModel tree = new TexturedModel(treeDry, new ModelTexture(loader.loadTexture("bark_0021")));
		
        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-5),0,0,0,10);
        Entity treeE = new Entity(tree, new Vector3f(90, 0, 0), 90, 90, 90, 1);
        
        Camera camera = new Camera();
		
		while(!Display.isCloseRequested())
		{
			//entity.increaseRotation(0.1f, 0.5f, 0.5f);			// forgatjuk az objektumot
            camera.move();							// kamera mozgást bevesszük a billentyűzetről
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
            renderer.render(entity,shader);
            renderer.render(treeE, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		shader.cleanUp();
		loader.cleanUp();
		
		DisplayManager.deleteDisplay();
	}

}
