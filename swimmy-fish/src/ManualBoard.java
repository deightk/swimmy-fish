import java.awt.event.ActionEvent;
import java.util.Timer;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class ManualBoard extends Board
{

	public ManualBoard()
	{
		super();
		initializeControls();
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
	
	protected void populate()
	{
		fish.add(new Fish());
	}
	
	private class SwimAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			{
				for (Fish f : fish)
				{
					f.swim();
				}
			}
		}
	}

	private class NeutralAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			{
				for (Fish f : fish)
				{
					f.neutral();
				}
			}
		}
	}

	private class DiveAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			for (Fish f : fish)
			{
				f.dive();
			}
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
				timer.schedule(new GameLoop(), 12, 12);
			}
			paused = !paused;
		}
	}
}