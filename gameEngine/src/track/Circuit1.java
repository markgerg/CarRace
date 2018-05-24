package track;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class Circuit1 {

	Loader loader;
	StaticShader shader;
	Renderer renderer;
	
	Entity circuitE;
	
	public Circuit1(Loader loader, StaticShader shader, Renderer renderer) {
		super();
		this.loader = loader;
		this.shader = shader;
		this.renderer = renderer;
		
		RawModel car_tire_left_head_raw = OBJLoader.loadOBJModel("track1",	 loader);
		TexturedModel circuit = new TexturedModel(car_tire_left_head_raw, new ModelTexture(loader.loadTexture("asphalt3")));
		ModelTexture texture_tire = circuit.getTexture();
		texture_tire.setShineDamper(5);
		texture_tire.setReflectivity(1);
		circuitE = new Entity(circuit, new Vector3f(0, -0.2f, 0), 0, 0, 0, 1);
	}
	
	public void RenderCircuit()
	{
		renderer.render(circuitE, shader);
	}
}
