package game;
import java.awt.Dimension;

public class Config
{
	public final static String GAME_TITLE = "Swimmy Fish";
	
	public final static int HORIZONTAL_RES = 800;
	public final static int VERTICAL_RES = 600;
	public final static Dimension DIMENSION = new Dimension(HORIZONTAL_RES, VERTICAL_RES);
	
	public final static int TIMER_INT = 10;
	
	public final static int FISH_START_XLOC = 100;
	public final static int FISH_START_YLOC = 0;
	public final static int FISH_SWIM_RATE = -6;
	public final static int FISH_DIVE_RATE = 6;
	public final static int FISH_NEUT_RATE = 3;
	
	public final static int TOP_PIPE_BASE_HEIGHT = -400;
	public final static int BOTTOM_PIPE_BASE_HEIGHT = 400;
	
	public final static int FIRST_PIPE_XLOC = 650;
	public final static int SECOND_PIPE_XLOC = 1050;
	public final static int THIRD_PIPE_XLOC = 1450;
	
	public final static String RESOURCE_PATH = "src/images/";
	public final static String FISH_PATH = RESOURCE_PATH + "fish.png";
	public final static String BOARD_PATH = RESOURCE_PATH + "ocean.png";
	public final static String TOP_PIPE_PATH = RESOURCE_PATH + "topPipe.png";
	public final static String BOTTOM_PIPE_PATH = RESOURCE_PATH + "bottomPipe.png";
}
