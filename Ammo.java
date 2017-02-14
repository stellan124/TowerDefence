import java.awt.Color;
import java.awt.Graphics;
import java.awt.Window;


public class Ammo extends MovingThing{
	int speed;
	int destX,destY;
	double unresolvedX, unresolvedY;
	Color color = Color.yellow;
	
	public Ammo()
	{
		super(-100,-100);
		speed = 1;
		destX = getX();
		destY = getY();
	}
	
	public Ammo(int s)
	{
		super(-100,-100);
		speed = s;
		destX = getX();
		destY = getY();
	}
	public Ammo(int s, Color col)
	{
		super(140,300);
		speed = s;
		color = col;
		destX = getX();
		destY = getY();
	}
	public void move()
	{
		int lenX = getX()-destX;
		int lenY = getY()-destY;
		if(lenX != 0 && lenY != 0){
		double angle = Math.atan(Math.toRadians(lenX/lenY));
		unresolvedX += Math.cos(angle)*speed;
		unresolvedY += Math.sin(angle)*speed;
		System.out.println(unresolvedX+"|"+unresolvedY);
		setPos(resolve(unresolvedX),resolve(unresolvedY));		
		}else if(lenX == 0 && lenY < 0){
			setY(getY() + speed);
		}else if(lenX == 0 && lenY > 0){
			setY(getY() + speed);
		}
	}
	private int resolve(double num)
	{
		if(num%1.0 >= .5)
			return (int)num;
		else
			return (int)(num-1); 
	}
	public void setDest(int x, int y)
	{
		destX = x;
		destY = y;
	}
	public void draw(Graphics window)
	{
		window.setColor(color);
		window.fillRect(getX(), getY(), getWidth(), getHeight());
	}
}
