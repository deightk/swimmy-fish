import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Board extends JPanel implements ActionListener
{
	private static final int BOARD_X = 0;
	private static final int BOARD_Y = 0;

	private Fish fish;
	private TopPipe[] topPipe = new TopPipe[3];
	private BottomPipe[] bottomPipe = new BottomPipe[3];
	private Image background;
	private Random rando;
	private Timer timer;

	private boolean pressed;
	private boolean paused;

	private int score;

	public Board()
	{
		initialize();
	}

	private void initialize()
	{
		ImageIcon ii = new ImageIcon(Config.BOARD_PATH);
		background = ii.getImage();
		setPreferredSize(Config.DIMENSION);
		setVisible(true);
		initializeControls();
		
		rando = new Random();
		score = 0;
		pressed = false;
		paused = false;

		fish = new Fish(100, 0);
		
		topPipe[0] = new TopPipe(650, 0);
		bottomPipe[0] = new BottomPipe(650, 0);
		
		int adj = pipeHeightAdjustment();
		
		topPipe[1] = new TopPipe(1000, adj);
		bottomPipe[1] = new BottomPipe(1000, adj);
		
		adj = pipeHeightAdjustment();
		
		topPipe[2] = new TopPipe(1350, adj);
		bottomPipe[2] = new BottomPipe(1350, adj);
		
		timer = new Timer(10, this);
		timer.start();
	}

	private int pipeHeightAdjustment()
	{
		return (rando.nextInt(6) * 50) - 150;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(background, BOARD_X, BOARD_Y, null);
		doDrawing(g);
		//updateGame();
		Toolkit.getDefaultToolkit().sync();
	}

	private void doDrawing(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		if (fish.isAlive())
		{
			g2d.drawImage(fish.getSprite(), fish.getX(), fish.getY(), this);  
		}
		
		for (int i = 0; i < bottomPipe.length; i++)
		{
			g2d.drawImage(bottomPipe[i].getSprite(), bottomPipe[i].getX(), bottomPipe[i].getY(), this);
			g2d.drawImage(topPipe[i].getSprite(), topPipe[i].getX(), topPipe[i].getY(), this);
		}
	}

	private void moveFish()
	{
		if (fish.getY() != 533)
		{
			fish.moveDown();
			fish.moveDown();
		}
	}

	private void pipeLogic()
	{
		for (int i = 0; i < bottomPipe.length; i++)
		{
			if (bottomPipe[i].getX() <= -250)
			{
				bottomPipe[i].setX(800);
				topPipe[i].setX(800);

				int adj = pipeHeightAdjustment();
				bottomPipe[i].setY(Config.BOTTOM_PIPE_BASE_HEIGHT + adj);
				topPipe[i].setY(Config.TOP_PIPE_BASE_HEIGHT + adj);
				
				score++;
			}
			else
			{
				bottomPipe[i].moveLeft();
				topPipe[i].moveLeft();
			}

			if (fish.getBounds().intersects(bottomPipe[i].getBounds())
					|| fish.getBounds().intersects(topPipe[i].getBounds()))
			{
				fish.kill();
			}
		}
	}
	
	private void updateGame()
	{
		fish.move();
		moveFish();
		pipeLogic();
	}

	public void actionPerformed(ActionEvent e)
	{
		//fish.move();
		updateGame();
		repaint();  
	}

	public void initializeControls()
	{
		getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "swimButton");
		getActionMap().put("swimButton", new SwimAction());
		getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "fallRelease");
		getActionMap().put("fallRelease", new FallAction());
		getInputMap().put(KeyStroke.getKeyStroke("R"), "resetButton");
		getActionMap().put("resetButton", new ResetAction());
		getInputMap().put(KeyStroke.getKeyStroke("P"), "pauseButton");
		getActionMap().put("pauseButton", new PauseAction());
	}

	private class SwimAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			if (!pressed)
			{
				fish.swim();
				pressed = true;
			}
		}
	}

	private class FallAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			fish.fall();
			pressed = false;
		}
	} 

	private class ResetAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			timer.stop();
			initialize();
		}
	}
	
	private class PauseAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			if (!paused)
			{
				timer.stop();
				paused = true;
			}
			else
			{
				timer.start();
				paused = false;
			}
		}
	}
}