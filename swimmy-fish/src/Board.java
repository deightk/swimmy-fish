import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Board extends JPanel
{
	private static final int BOARD_X = 0;
	private static final int BOARD_Y = 0;

	private Fish fish;
	private TopPipe[] topPipe = new TopPipe[3];
	private BottomPipe[] bottomPipe = new BottomPipe[3];
	private Image background;
	private Random rando;
	private Timer timer;

	private boolean paused;

	private int pipeSpeed;
	private int whirlpoolSpeed;

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

	private void drawComponents(Graphics2D g)
	{
		g.drawImage(background, BOARD_X, BOARD_Y, null); 

		for (int i = 0; i < bottomPipe.length; i++)
		{
			bottomPipe[i].draw(g);
			topPipe[i].draw(g);
		}

		fish.draw(g);
	}

	private void initialize()
	{
		ImageIcon ii = new ImageIcon(Config.BOARD_PATH);
		background = ii.getImage();

		rando = new Random();

		initializeControls();
		newGame();
	}

	private void initializeControls()
	{
		//swimming
		getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "swimButton");
		getActionMap().put("swimButton", new SwimAction());
		getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "swimRelease");
		getActionMap().put("swimRelease", new NeutralAction());
		//diving
		getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "diveButton");
		getActionMap().put("diveButton", new DiveAction());
		getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "diveRelease");
		getActionMap().put("diveRelease", new NeutralAction());
		//reset
		getInputMap().put(KeyStroke.getKeyStroke("R"), "resetButton");
		getActionMap().put("resetButton", new ResetAction());
		//pause
		getInputMap().put(KeyStroke.getKeyStroke("P"), "pauseButton");
		getActionMap().put("pauseButton", new PauseAction());
	}

	private void newGame()
	{
		initializePipes();
		pipeSpeed = 3;
		whirlpoolSpeed = 3;
		paused = false;
		fish = new Fish(100, 0);
		timer = new Timer();
		timer.schedule(new GameLoop(), 12, 12);
	}

	private void initializePipes()
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

	private int pipeHeightAdjustment()
	{
		return (rando.nextInt(6) * 50) - 150;
	}

	private void gameLogic()
	{
		fish.move();

		for (int i = 0; i < whirlpoolSpeed; i++)
		{
			fish.moveDown();
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

			if (fish.getBounds().intersects(topPipe[i].getBounds())
					|| fish.getBounds().intersects(bottomPipe[i].getBounds())
					|| fish.getY() > Config.VERTICAL_RES)
			{
				fish.kill();
				timer.cancel();
			}
			else
			{
				fish.score();
			}
		}
	}

	private class SwimAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			{
				fish.swim();
			}
		}
	}

	private class NeutralAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			{
				fish.neutral();
			}
		}
	}

	private class DiveAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			fish.dive();
		}
	} 

	private class ResetAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			timer.cancel();
			newGame();
		}
	}

	private class PauseAction extends AbstractAction
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
				timer.schedule(new GameLoop(), 12, 12);
			}
			paused = !paused;
		}
	}

	private class GameLoop extends TimerTask
	{
		public void run()
		{
			gameLogic();
			repaint();
		}
	}
}