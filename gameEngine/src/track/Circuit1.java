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
	Entity treeE[] = new Entity[9];
	
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
		
		RawModel treeDry = OBJLoader.loadOBJModel("tree10",	 loader);
		TexturedModel tree = new TexturedModel(treeDry, new ModelTexture(loader.loadTexture("treeText")));
		ModelTexture texture_tree = tree.getTexture();
		texture_tree.setShineDamper(5);
		texture_tree.setReflectivity(1);
		treeE[0] = new Entity(tree, new Vector3f(0, -0.2f, 0), 0, 0, 0, 1);
		treeE[1] = new Entity(tree, new Vector3f(3, -0.2f, 2), 0, 0, 0, 1);
		treeE[2] = new Entity(tree, new Vector3f(7, -0.2f, 2), 0, 0, 0, 1);
		treeE[3] = new Entity(tree, new Vector3f(3, -0.2f, 6), 0, 0, 0, 1);
		treeE[4] = new Entity(tree, new Vector3f(-61, -0.2f, 7), 0, 0, 0, 1);
		treeE[5] = new Entity(tree, new Vector3f(4, -0.2f, 2), 0, 0, 0, 1);
		treeE[6] = new Entity(tree, new Vector3f(3, -0.2f, 9), 0, 0, 0, 1);
		treeE[7] = new Entity(tree, new Vector3f(12, -0.2f, 2), 0, 0, 0, 1);
		treeE[8] = new Entity(tree, new Vector3f(15, -0.2f, 15), 0, 0, 0, 1);
	}
	
	public void RenderCircuit()
	{
		renderer.render(circuitE, shader);
		for (int i=0; i<9; i++)
		{
			renderer.render(treeE[i], shader);
		}

	}
}
