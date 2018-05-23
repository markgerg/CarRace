package cars;

public enum e8Steering {
	NONE((byte)50),
	LEFT((byte)60),
	RIGHT((byte)70);
	
    private final byte id;
	e8Steering(byte id) { this.id = id; }
    public byte getValue() { return id; }
    
    public static e8Steering fromValue(byte id)
    {
    	for (e8Steering my: e8Steering.values()) {
            if (my.id == id) {
                return my;
            }
        }
		return NONE;

    }
}
