import java.awt.Graphics;

public class Fish extends GameObject
{
	private static final String path = Config.RESOURCE_PATH + "fish.png";
	private int dx;
	private int dy;
	private int score;
	private boolean dead;
	
	public Fish()
	{
		new Fish(0, 0);
	}

	public Fish(int x, int y)
	{
		super(x, y);
		score = 0;
		dead = false;
		loadImage(path);
	}
	
	public void move()
	{
		x += dx;
		y += dy;
	}
	
	public void draw(Graphics g)
	{
		if (!dead)
		{
			super.draw(g);
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
    
    public void kill()
    {
    	dead = true;
    }
    
    public boolean isDead()
    {
    	return dead;
    }
    
    public void setScore(int score)
    {
    	this.score = score;
    }
    
    public int getScore()
    {
    	return score;
    }
    
    public void score()
    {
    	score++;
    }
}
