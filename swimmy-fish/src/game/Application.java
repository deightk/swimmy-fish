package game;

import java.util.Scanner;

import javax.swing.JFrame;

import agent.NeuralBoard;

public class Application extends JFrame
{
	public Board board;
	public boolean manual;

	public Application(boolean manual)
	{
		if (manual)
		{
			board = new ManualBoard();
		}
		else
		{
			board = new NeuralBoard();
		}
		
		initialize();
	}

	private void initialize()
	{
		add(board);
		setSize(Config.DIMENSION);
		setTitle(Config.GAME_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes program when exit is clicked
		setLocationRelativeTo(null); //centers window
	}    

	public static void main(String[] args)
	{
		Application ex;
		Scanner scan = new Scanner(System.in);
				
		System.out.println("Would you like to play Swimmy Fish or Neural Fish? Type 1 or 2");
		System.out.println("1. Swimmy Fish");
		System.out.println("2. Neural Fish");
		
		if (scan.nextLine().equals("1"))
		{
			ex = new Application(true);
		}
		else
		{
			ex = new Application(false);
		}

		ex.setVisible(true);
		ex.board.gameLogic();
	}
}