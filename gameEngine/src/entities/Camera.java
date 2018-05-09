package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
 
public class Camera {
     
    private Vector3f position = new Vector3f(0,0,0);
    private float pitch;
    private float yaw;
    private float roll;
     
    public Camera(){}
     
    public void moveWithKeyboardcontrol(){
        final float MAX_LOOK_UP = 90;
        final float MAX_LOOK_DOWN = -90;
    	
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z-=0.1f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.z+=0.1f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x+=0.1f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x-=0.1f;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
        		position.y+=0.1f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
    			position.y-=0.1f;
        }
        float mouseDX = Mouse.getDX() * 0.16f;
        float mouseDY = Mouse.getDY() * 0.16f;
        
        if (yaw + mouseDX >= 360) {
            yaw = yaw + mouseDX - 360;
        } else if (yaw + mouseDX < 0) {
            yaw = 360 - yaw + mouseDX;
        } else {
            yaw += mouseDX;
        }
        
        if (pitch - mouseDY >= MAX_LOOK_DOWN && pitch - mouseDY <= MAX_LOOK_UP) {
            pitch += -mouseDY;
        } else if (pitch - mouseDY < MAX_LOOK_DOWN) {
            pitch = MAX_LOOK_DOWN;
        } else if (pitch - mouseDY > MAX_LOOK_UP) {
            pitch = MAX_LOOK_UP;
        }
    }
    
    public void setPosition(Vector3f entityPosition)
    {
    	position.x = entityPosition.x;
    	position.y = entityPosition.y+4;
    	position.z = entityPosition.z-9;
    	yaw = 180;
    	pitch = 20;
    }
 
    public Vector3f getPosition() {
        return position;
    }
 
    public float getPitch() {
        return pitch;
    }
 
    public float getYaw() {
        return yaw;
    }
 
    public float getRoll() {
        return roll;
    }
     
     
 
}
