package game;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public abstract class GameObject
{
	protected int x;
	protected int y;
	protected int dx;
	protected int dy;
	protected int height;
	protected int width;
	protected Image sprite;

	public GameObject()
	{
	}

	public GameObject(int x, int y)
	{
		this.x = x;
		this.y = y;
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
	
	public void draw(Graphics g)
	{
		g.drawImage(sprite, x, y, null);
	}

	//used for collision detection
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}
	
	public boolean collides(GameObject go)
	{
		return this.getBounds().intersects(go.getBounds());
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

	public void move()
	{
		x += dx;
		y += dy;
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
