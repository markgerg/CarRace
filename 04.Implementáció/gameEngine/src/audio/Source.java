package audio;

import org.lwjgl.openal.AL10;

public class Source {
	
	private int sourceId;
	
	public Source() {
		sourceId = AL10.alGenSources();
		
		AL10.alSourcei(sourceId, AL10.AL_LOOPING, 1);
		

	}

	public void play(int buffer) {
		AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
		AL10.alSourcePlay(sourceId);
	}
	
	public void setVolume(float volume) {
		AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
	}
	
	public void SetPosition(float x, float y, float z) {
		AL10.alSource3f(sourceId, AL10.AL_POSITION, x, y, z);
	}	
		
	public void SetListener(float x, float y, float z) {
		AL10.alListener3f(AL10.AL_POSITION, x, y, z);
	}
	
	public void delete() {
		AL10.alDeleteSources(sourceId);
	}	
	
	public void EnginePitch(float pitch) {		//max speed: 
		AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
	}
	
	
}
