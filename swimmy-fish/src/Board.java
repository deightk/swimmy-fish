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

public class Board extends JPanel
{
	protected static final int BOARD_X = 0;
	protected static final int BOARD_Y = 0;

	protected ArrayList<Fish> fish;
	protected TopPipe[] topPipe = new TopPipe[3];
	protected BottomPipe[] bottomPipe = new BottomPipe[3];
	protected Image background;
	protected Random rando;
	protected Timer timer;

	protected boolean paused;

	protected int pipeSpeed;
	protected int whirlpoolSpeed;

	public Board()
	{
		initialize();
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
			if (fish.get(i).isDead())
			{
				fish.remove(i);
			}
			else
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

		newGame();
	}

	protected void newGame()
	{
		initializePipes();
		pipeSpeed = 3;
		whirlpoolSpeed = 3;
		paused = false;
		fish = new ArrayList<Fish>();
		populate();

		timer = new Timer();
		timer.schedule(new GameLoop(), 12, 12);
	}

	protected void populate()
	{
		for (int i = 0; i < 100; i++)
		{
			fish.add(new Fish());
		}
	}

	protected void initializePipes()
	{
		topPipe[0] = new TopPipe(Config.FIRST_PIPE_XLOC, 0);
		bottomPipe[0] = new BottomPipe(Config.FIRST_PIPE_XLOC, 0);

		int adj = pipeHeightAdjustment();

		topPipe[1] = new TopPipe(Config.SECOND_PIPE_XLOC, adj);
		bottomPipe[1] = new BottomPipe(Config.SECOND_PIPE_XLOC, adj);

		adj = pipeHeightAdjustment();

		topPipe[2] = new TopPipe(Config.THIRD_PIPE_XLOC, adj);
		bottomPipe[2] = new BottomPipe(Config.THIRD_PIPE_XLOC, adj);
	}

	protected int pipeHeightAdjustment()
	{
		return (rando.nextInt(6) * 50) - 150;
	}

	protected void gameLogic()
	{
		for (Fish f : fish)
		{
			f.move();
		}

		for (int i = 0; i < bottomPipe.length; i++)
		{
			if (bottomPipe[i].getX() < -249)
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

			for (Fish f : fish)
			{
				if (f.getBounds().intersects(topPipe[i].getBounds())
						|| f.getBounds().intersects(bottomPipe[i].getBounds())
						|| f.getY() > Config.VERTICAL_RES)
				{
					f.kill();
					timer.cancel();
				}
				else
				{
					f.score();
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
}