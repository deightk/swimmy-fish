import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public abstract class GameObject
{
	protected int x;
	protected int y;
	protected int height;
	protected int width;
	protected boolean visible;
	protected Image sprite;

	public GameObject()
	{
		visible = true;
	}

	public GameObject(int x, int y)
	{
		this.x = x;
		this.y = y;
		visible = true;
	}

	public void loadImage(String filename)
	{
		//load the resource
		ImageIcon ii = new ImageIcon(filename);
		sprite = ii.getImage();
		//set the object's height and width appropriately
		height = sprite.getHeight(null);
		width = sprite.getWidth(null);
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}

	public Image getSprite()
	{
		return sprite;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public void moveLeft()
	{
		x--;
	}

	public void moveRight()
	{
		x++;
	}

	public void moveUp()
	{
		y--;
	}
	
	public void moveDown()
	{
		y++;
	}

}

