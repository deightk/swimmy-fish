package game;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class ManualBoard extends Board
{

	public ManualBoard()
	{
		super();
		initializeManualControls();
	}
	
	private void initializeManualControls()
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
	}
	
	protected void populate()
	{
		fish = new ArrayList<Fish>();
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
}