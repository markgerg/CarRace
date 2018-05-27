package audio;

import org.lwjgl.util.vector.Vector3f;

public class AudioCar {

	
	float volume;
	String audioFile;
	Source source = new Source();	//létrehoz egy lejátszót
	
	public AudioCar(float volume, String audioFile) {
		super();
		this.volume = volume;
		this.audioFile = audioFile;
		
		/*AudioMaster.init();		//main függvényből legyen majd meghívva
		AudioMaster.setListenerData();*/
		
		int buffer = AudioMaster.loadSound(audioFile);		// betölti a hangot a bufferbe    "audio/loop_0.wav"

		source.setVolume(volume);  //beállítja a lejátszó hangerősségét   0-1
		
		source.play(buffer);		//indítja a lejátszást
		
	}
	
	
	public void SetCarParameters(float velocity, Vector3f carPosition, Vector3f cameraPosition ) 
	{
		source.EnginePitch(velocity); //beállítja a hangmagasságot 
		source.SetPosition(carPosition.x, carPosition.y, carPosition.z);  //beállítja a hangforrás helyzetét
		source.SetListener(cameraPosition.x, cameraPosition.y, cameraPosition.z);  //beállítja a hallgató helyzetét
		
	}
	
	
	public void deleteAudioCar() {
		source.delete();
		AudioMaster.cleanUp();
	}
	
	
	
}
