import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class Application extends JFrame implements ActionListener
{
	private final static String TITLE= "Swimmy Fish";
	private Board board = new Board();

    public Application()
    {
        initUI();
    }

    private void initUI()
    {
        add(board);

        setSize(Config.DIMENSION);

        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes program when exit is clicked
        setLocationRelativeTo(null); //centers window
    }    
    
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Application ex = new Application();
                ex.setVisible(true);
            }
        });
    }

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}
}