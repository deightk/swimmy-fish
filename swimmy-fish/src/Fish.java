public class Fish extends GameObject
{
	private static final String path = Config.RESOURCE_PATH + "fish.png";
	private int dx;
	private int dy;
	private boolean dead;
	
	public Fish()
	{
		super();
		dead = false;
		loadImage(path);
	}
	
	public Fish(int x, int y)
	{
		super(x, y);
		dead = false;
		loadImage(path);
	}
	
	public void move()
	{
		x += dx;
		
		if (((y + dy) > 0) && ((y + dy) < (Config.VERTICAL_RES - height)))
		{
			y += dy;
		}
	}

	public void swim()
	{
		dy = -9;
	}

    public void neutral()
    {
        dy = 0;
    }
    
    public void dive()
    {
    	dy = 6;
    }
    
    public boolean isDead()
    {
    	return dead;
    }
    
    public void kill()
    {
    	dead = true;
    }
}
