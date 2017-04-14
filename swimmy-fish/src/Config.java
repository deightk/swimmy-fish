import java.awt.Dimension;

public class Config
{
	public final static int HORIZONTAL_RES = 800;
	public final static int VERTICAL_RES = 600;
	public final static Dimension DIMENSION = new Dimension(HORIZONTAL_RES, VERTICAL_RES);
	
	public final static int TOP_PIPE_BASE_HEIGHT = -375;
	public final static int BOTTOM_PIPE_BASE_HEIGHT = 375;
	
	public final static String RESOURCE_PATH = "src/images/";
	public final static String FISH_PATH = RESOURCE_PATH + "fish.png";
	public final static String BOARD_PATH = RESOURCE_PATH + "ocean.png";
	public final static String TOP_PIPE_PATH = RESOURCE_PATH + "topPipe.png";
	public final static String BOTTOM_PIPE_PATH = RESOURCE_PATH + "bottomPipe.png";
}
