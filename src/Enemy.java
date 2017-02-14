import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

public class Enemy extends MovingThing {
	private int value = 10;
	private int hp = 100;
	private int speed = 3;
	private int distanceMoved;
	private Color color = Color.red;
	private String direction = "DOWN";
	private int indexPassed = 0;
	private boolean end = false;

	public Enemy() {
		super();
	}

	public Enemy(int s) {
		super();
		speed = s;
	}

	public Enemy(int s, Color c) {
		super();
		speed = s;
		color = c;
	}

	public Enemy(int s, Color c, int h) {
		super();
		speed = s;
		color = c;
		hp = h;
	}

	public Enemy(int x, int y, int s) {
		super(x, y);
		speed = s;
	}
	public Enemy(int x, int y, int s, int h) {
		super(x, y);
		speed = s;
		hp = h;
	}

	public Enemy(int x, int y) {
		super(x, y);
	}

	public void setMoveDistance(int d) {
		distanceMoved = d;
	}

	public int getMoveDistance() {
		return distanceMoved;
	}

	public void hit(int damage) {
		hp -= damage;
	}

	public int killed() {
		if (hp <= 0 && hp > Integer.MIN_VALUE) {
			hp = Integer.MIN_VALUE;
			return value;
		}
		return 0;
	}

	public void setDirection(String s) {
		direction = s;
	}

	public void direction(List<int[]> spots) {
		if (distanceMoved - speed <= 0 && !(indexPassed >= spots.size())) {
			if (direction == "RIGHT" || direction == "LEFT") {
				if (spots.get(indexPassed)[1] > 0) {
					direction = "DOWN";
					distanceMoved = Math.abs(spots.get(indexPassed)[1]);
				} else {
					direction = "UP";
					distanceMoved = Math.abs(spots.get(indexPassed)[1]);
				}
			} else {
				if (spots.get(indexPassed)[1] > 0) {
					direction = "RIGHT";
					distanceMoved = Math.abs(spots.get(indexPassed)[1]);
				} else {
					direction = "LEFT";
					distanceMoved = Math.abs(spots.get(indexPassed)[1]);
				}
			}
			indexPassed++;
		}
		if(indexPassed == spots.size() && distanceMoved <= 0){
			end = true;
		}
	}
	public int reachedEnd()
	{
		
		if(end && hp > 0){
			hp = 0;
			value = 0;
			return 1;
		}
		return 0;
	}
	public void move(String s) {
		if (!isDead()) {
			if (direction == "DOWN")
				setY(getY() + speed);
			else if (direction == "UP")
				setY(getY() - speed);
			else if (direction == "RIGHT")
				setX(getX() + speed);
			else
				setX(getX() - speed);
			distanceMoved -= speed;
		}
	}

	public boolean isDead() {
		if (hp < 1)
			return true;
		return false;
	}

	public int getHp() {
		return hp;
	}

	public int getValue() {
		return value;
	}

	public String getDirection() {
		return direction;
	}

	public void draw(Graphics window) {
		if (!isDead()) {
			window.setColor(color);
			window.fillRect(getX(), getY(), getWidth(), getHeight());
			window.setFont(new Font("test", 5, 10));
			window.setColor(Color.YELLOW);
			window.drawString("" + hp, getX(), getY() + getHeight());
		}
	}
}
