
public class BottomPipe extends GameObject
{
	public BottomPipe()
	{
		super();
		loadImage(Config.BOTTOM_PIPE_PATH);
	}
	
	public BottomPipe(int x, int y)
	{
		super(x, Config.BOTTOM_PIPE_BASE_HEIGHT + y);
		loadImage(Config.BOTTOM_PIPE_PATH);
	}
	
	public void setY(int y)
	{
		super.setY(Config.BOTTOM_PIPE_BASE_HEIGHT + y);
	}
}
