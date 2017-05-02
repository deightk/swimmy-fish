package agent;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import jneat.*;

import game.Board;
import game.Config;
import game.Fish;

public class NeuralBoard extends Board
{
	protected int generation;
	protected Population neatPop;
	protected Vector orgs;

	public NeuralBoard()
	{
		Neat.initbase();

		neatPop = new Population
				(
						50, 		/* population size */ 
						3, 			/* network inputs */ 
						1, 			/* network outputs */
						10, 		/* max index of nodes */
						true, 		/* recurrent */
						0.5 		/* probability of connecting two nodes */
				);
		
		orgs = neatPop.getOrganisms();
		generation = 0;
		gameSpeed = 5;
		
		loadFromFile();
		initialize();
	}
	
	protected void loadFromFile()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Would you like to load from a file? Y/N");
		
		if (scan.nextLine().toUpperCase().equals("Y"))
		{
			System.out.print("Enter file name: ");
			neatPop = new Population(scan.nextLine());
			
			System.out.println();
			
			System.out.print("Enter generation number: ");
			generation = scan.nextInt();
		}
		
		Neat.readParam("parametri");
	}

	protected void populate()
	{
		fish = new ArrayList<Fish>(25);

		for (int i = 0; i < 50; i++)
		{
			fish.add(new Fish());
		}
	}

	private double normalizeY(int y)
	{
		return ((double) y / (double) Config.VERTICAL_RES);
	}

	private double normalizeX(int x)
	{
		return ((double) x / (double) Config.HORIZONTAL_RES);
	}

	public void simulate()
	{
		orgs = neatPop.getOrganisms();

		for (int i = 0; i < orgs.size(); i++)
		{
			if (fish.get(i).isDead())
			{
				continue;
			}
			
			Network brain = ((Organism)orgs.get(i)).getNet();

			double[] inputs = 
				{
						normalizeX(getClosestBottomPipe().getX()),
						normalizeY(fish.get((i)).getY()), 
						normalizeY(getClosestBottomPipe().getY())
						//normalizeY(getClosestTopPipe().getY()),
				};

			brain.load_sensors(inputs);

			int net_depth = brain.max_depth();

			brain.activate();

			for (int relax = 0; relax <= net_depth; relax++)
			{
				brain.activate();
			}

			double output = ((NNode) brain.getOutputs().elementAt(0)).getActivation();

			if (output > 0.67)
			{
				fish.get(i).swim();
			}
			else if (output > 0.33)
			{
				fish.get(i).neutral();
			}
			else
			{
				fish.get(i).dive();
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected void evaluate()
	{
		//Vector<Organism> 
		orgs = neatPop.getOrganisms(); 

		for (int i = 0; i < orgs.size(); i++)
		{
			double score = (double) fish.get(i).getScore();
			
			((Organism)orgs.get(i)).setFitness(score);
			
			if (score > 2500)
			{
				((Organism)orgs.get(i)).setWinner(true);
			}
		}
		
		Vector species = neatPop.getSpecies();
		
		for (int i = 0; i < species.size(); i++)
		{
			Species s = (Species) species.elementAt(i);
			
			int index = 0;
			double max = 0;
			
			for (int j = 0; j < s.getOrganisms().size(); j++)
			{
				Organism o = (Organism)s.getOrganisms().elementAt(j);
				
				if (o.getFitness() > max)
				{
					max = o.getFitness();
					index = j;
				}
				
			}
			
			((Organism)s.getOrganisms().elementAt(index)).setChampion(true);
			
			s.compute_average_fitness();
			s.compute_max_fitness();
			
			if (s.getMax_fitness() > s.getMax_fitness_ever())
			{
				s.setMax_fitness_ever(s.getMax_fitness());
			}
		}

		generation++; //increase generation number
		
		neatPop.viewtext(); //print diagnostics
		
		neatPop.print_to_file_by_species("generation" + generation); //print to file
		
		neatPop.epoch(generation); //begin new generation
	}

	protected void additionalLogic()
	{
		simulate();
		
		boolean eval = true; 
		
		for (int i = 0; i < fish.size(); i++)
		{
			if (!fish.get(i).isDead())
			{
				eval = false;
			}
		}
		
		if (eval)
		{
			System.out.println("GENERATION: " + generation);
			timer.cancel();
			evaluate();
			populate();
			newGame();
		}
	}
}
