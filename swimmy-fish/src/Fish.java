import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class Fish extends GameObject
{
	private static final String path = Config.RESOURCE_PATH + "fish.png";
	private int dx;
	private int dy = 3;
	private int score;
	private boolean dead;
	
	public Fish()
	{
		new Fish(Config.FISH_START_XLOC, Config.FISH_START_YLOC);
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
		dy = -6;
	}

    public void neutral()
    {
        dy = 3;
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
    
	private class SwimAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			{
				swim();
			}
		}
	}

	private class NeutralAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			{
				neutral();
			}
		}
	}

	private class DiveAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			dive();
		}
	} 
}
