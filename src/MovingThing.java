//© A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class - 
//Lab  -

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class MovingThing {
	private int xPos;
	private int yPos;
	private int width;
	private int height;

	public MovingThing() {
		xPos = 10;
		yPos = 10;
		width = 10;
		height = 10;
	}

	public MovingThing(int x, int y) {
		xPos = x;
		yPos = y;
		width = 10;
		height = 10;
	}

	public MovingThing(int x, int y, int w, int h) {
		// add code here
		xPos = x;
		yPos = y;
		height = h;
		width = w;
	}
	public double distance(MovingThing e)
	{
		return Math.sqrt(Math.pow(getX()-e.getX(), 2)+Math.pow(getY()-e.getY(), 2));
	}
	public void setPos(int x, int y) {
		// add code here
		xPos = x;
		yPos = y;
	}

	public void setX(int x) {
		// add code here
		xPos = x;
	}

	public void setY(int y) {
		// add code here
		yPos = y;
	}

	public int getX() {
		return xPos; // finish this method
	}

	public int getY() {
		return yPos; // finish this method
	}

	public void setWidth(int w) {
		// add code here
		width = w;
	}

	public void setHeight(int h) {
		// add code here
		height = h;
	}

	public int getWidth() {
		return width; // finish this method
	}

	public int getHeight() {
		return height; // finish this method
	}

	public boolean collide(MovingThing obj) {
		return this.getRect().intersects(obj.getRect());
	}
	
	public Rectangle getRect(){
		return new Rectangle(xPos,yPos,width,height);
	}

	//public abstract void move(String direction);

//	public abstract void draw(Graphics window);

	public String toString() {
		return getX() + " " + getY() + " " + getWidth() + " " + getHeight();
	}
}
