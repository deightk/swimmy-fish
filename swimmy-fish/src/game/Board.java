package game;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import game.Board.GameLoop;
import game.Board.PauseAction;

public abstract class Board extends JPanel
{
	protected static final int BOARD_X = 0;
	protected static final int BOARD_Y = 0;

	protected ArrayList<Fish> fish;
	protected TopPipe[] topPipe = new TopPipe[3];
	protected BottomPipe[] bottomPipe = new BottomPipe[3];

	protected Image background;
	
	protected Random rando;
	
	protected Timer timer;
	protected int gameSpeed = Config.TIMER_INT;

	protected boolean paused;

	protected int numPipes;
	protected int pipeSpeed;
	protected int closestPipeIndex;

	public Board()
	{
		initialize();
	}
	
	public BottomPipe getClosestBottomPipe()
	{
		return bottomPipe[closestPipeIndex];
	}

	public TopPipe getClosestTopPipe()
	{
		return topPipe[closestPipeIndex];
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		drawComponents((Graphics2D) g);
		Toolkit.getDefaultToolkit().sync();
	}

	protected void drawComponents(Graphics2D g)
	{
		g.drawImage(background, BOARD_X, BOARD_Y, null); 

		for (int i = 0; i < bottomPipe.length; i++)
		{
			bottomPipe[i].draw(g);
			topPipe[i].draw(g);
		}

		for (int i = 0; i < fish.size(); i++)
		{
			if (!fish.get(i).isDead())
			{
				fish.get(i).draw(g);
			}
		}
	}

	protected void initialize()
	{
		ImageIcon ii = new ImageIcon(Config.BOARD_PATH);
		background = ii.getImage();

		rando = new Random();
		
		initializeControls();
		newGame();
	}
	
	protected void initializeControls()
	{
		//reset
		getInputMap().put(KeyStroke.getKeyStroke("R"), "resetButton");
		getActionMap().put("resetButton", new ResetAction());
		//pause
		getInputMap().put(KeyStroke.getKeyStroke("P"), "pauseButton");
		getActionMap().put("pauseButton", new PauseAction());
	}

	protected void newGame()
	{
		initializePipes();

		pipeSpeed = 5;
		closestPipeIndex = 0;
		numPipes = 3;

		paused = false;

		populate();

		initializeTimer();
	}

	protected void initializePipes()
	{
		int adj = pipeHeightAdjustment();
		
		topPipe[0] = new TopPipe(Config.FIRST_PIPE_XLOC, adj);
		bottomPipe[0] = new BottomPipe(Config.FIRST_PIPE_XLOC, adj);

		adj = pipeHeightAdjustment();

		topPipe[1] = new TopPipe(Config.SECOND_PIPE_XLOC, adj);
		bottomPipe[1] = new BottomPipe(Config.SECOND_PIPE_XLOC, adj);

		adj = pipeHeightAdjustment();

		topPipe[2] = new TopPipe(Config.THIRD_PIPE_XLOC, adj);
		bottomPipe[2] = new BottomPipe(Config.THIRD_PIPE_XLOC, adj);
	}

	protected int pipeHeightAdjustment()
	{
		return (rando.nextInt(20) * 15) - 150;
	}

	protected void populate()
	{
		fish = new ArrayList<Fish>();

		for (int i = 0; i < 10; i++)
		{
			fish.add(new Fish());
		}
	}

	protected void initializeTimer()
	{
		timer = new Timer();
		timer.schedule(new GameLoop(), gameSpeed, gameSpeed);
	}

	protected void gameLogic()
	{
		fishLogic();
		pipeLogic();
		additionalLogic();

		if (fish.size() == 0)
		{
			timer.cancel();
		}
	}

	protected void additionalLogic()
	{
	}

	private void fishLogic()
	{
		for (Fish f : fish)
		{
			
			if (f.isDead()) //skip this iteration if the fish is dead
			{
				continue;
			}
			
			if 	(   //kill the fish if it goes out of bounds, touches a pipe
					f.collides(getClosestTopPipe())
					|| f.collides(getClosestBottomPipe())
					|| f.getY() > Config.VERTICAL_RES
					|| f.getY() < -25
				)
			{
				f.kill();
			}
			
			if (f.isDead()) //skip if the fish was just killed
			{
				continue;
			}

			f.move(); //move the fish according to its current action

			if (f.getX() > getClosestTopPipe().getX()) 
			{
				f.score();	//grant the fish points for passing through a pipe
			}

			if (	f.getX() > getClosestTopPipe().getX() - 225
					&& (f.getY() < getClosestTopPipe().getBottomY()
					|| (f.getBottomY()) > getClosestBottomPipe().getY())) //
			{
				f.penalize();	//penalize the fish for being on a collision course
			}
		}
	}

	private void pipeLogic()
	{
		for (int i = 0; i < bottomPipe.length; i++)
		{
			if (numPipes > Config.MAX_PIPES)
			{
				for (Fish f : fish)
				{
					f.kill();
				}
			}
			
			if (getClosestTopPipe().getX() < -25)
			{
				//change the "closest" pipe to the next pipe in the sequence
				closestPipeIndex = (closestPipeIndex + 1) % 3;
				numPipes++;
			}
				// if the pipe has traveled far enough off-screen to the left,
				// reposition it at the right of the screen to begin anew
			
			if (bottomPipe[i].getX() < -400)
			{
				int adj = pipeHeightAdjustment();

				bottomPipe[i].setX(800);
				bottomPipe[i].setY(adj);

				topPipe[i].setX(800);
				topPipe[i].setY(adj);
			}
			else
			{
				for (int j = 0; j < pipeSpeed; j++)
				{
					bottomPipe[i].moveLeft();
					topPipe[i].moveLeft();
				}
			}
		}
	}

	protected class GameLoop extends TimerTask
	{
		public void run()
		{
			gameLogic();
			repaint();
		}
	}
	
	protected class PauseAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			if (!paused)
			{
				timer.cancel();
			}
			else
			{
				timer = new Timer();
				timer.schedule(new GameLoop(), gameSpeed, gameSpeed);
			}
			paused = !paused;
		}
	}
	
	protected class ResetAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			timer.cancel();
			newGame();
		}
	}
	
}