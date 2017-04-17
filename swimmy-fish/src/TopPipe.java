public class TopPipe extends GameObject
{
	public TopPipe()
	{
		super();
		loadImage(Config.TOP_PIPE_PATH);
	}
	
	public TopPipe(int x, int y)
	{
		super(x, Config.TOP_PIPE_BASE_HEIGHT + y);
		loadImage(Config.TOP_PIPE_PATH);
	}
	
	public void setY(int y)
	{
		super.setY(Config.TOP_PIPE_BASE_HEIGHT + y);
	}
}
