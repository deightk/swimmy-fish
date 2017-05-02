package game;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class Fish extends GameObject
{
	protected static final String path = Config.RESOURCE_PATH + "fish.png";
	protected int score;
	protected boolean dead;

	public Fish()
	{
		this(Config.FISH_START_XLOC, Config.FISH_START_YLOC);
	}

	public Fish(int x, int y)
	{
		super(x, y);
		dy = 3;
		score = 0;
		dead = false;
		loadImage(path);
	}

	public void swim()
	{
		dy = Config.FISH_SWIM_RATE;
	}

	public void neutral()
	{
		dy = Config.FISH_NEUT_RATE;
	}

	public void dive()
	{
		dy = Config.FISH_DIVE_RATE;
	}

	public void kill()
	{
		dead = true;
		//used to weed out especially poor scorers
		if (score < -750)
		{
			score = -10000;
		}
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
		score += 20;
	}
	
	public void penalize()
	{
		score -= 10;
	}

	//for external invoking from an ActionListener
	
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
