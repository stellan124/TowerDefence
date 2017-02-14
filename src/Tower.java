import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Tower extends MovingThing {
	private int range, damage, speed, recharge;
	private int rank = 1;
	private Color color;
	private int shotX, shotY;
	private int price = 100;
	private int shooterInc = 0;
	private Enemy target;
	private boolean advanceDisplay = false;

	public Tower(int x, int y, int attackRange, int attackDamage, int attackSpeed, Color col) {
		super(x, y, 20, 20);
		range = attackRange;
		damage = attackDamage;
		if (attackSpeed <= 0)
			attackSpeed = 1;
		speed = 10000 / attackSpeed;
		color = col;
		recharge = speed;
	}

	public void shoot(List<Enemy> list) {
		target = findFirstEnemy(list);
		if (shooterInc == 0 && target != null && recharge >= speed) {
			shotX = target.getX() + target.getWidth() / 2;
			shotY = target.getY() + target.getHeight() / 2;
			target.hit(damage);
			shooterInc = 10;
			recharge = 0;
		} else
			recharge++;
	}

	public Enemy findClosestEnemy(List<Enemy> list) {
		List<Enemy> temp = enemiesInRange(list);
		if (temp.size() > 0) {
			Enemy closest = temp.get(0);
			for (int i = 1; i < temp.size(); i++) {
				if (distance(temp.get(0)) > distance(list.get(i)))
					closest = temp.get(i);
			}
			return closest;
		}
		return null;
	}

	public Enemy findFirstEnemy(List<Enemy> list) {
		List<Enemy> temp = enemiesInRange(list);
		if (temp.size() > 0) {
			return temp.get(0);
		}
		return null;
	}

	public List<Enemy> enemiesInRange(List<Enemy> list) {
		List<Enemy> temp = new ArrayList<Enemy>();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (distance(list.get(i)) <= (double) range && !list.get(i).isDead())
					temp.add(list.get(i));
			}
		}
		return temp;
	}

	public Enemy findFirstLiving(List<Enemy> list) {
		for (int i = 0; i < list.size(); i++) {
			if (distance(list.get(i)) <= (double) range && !list.get(i).isDead())
				return list.get(i);
		}
		return null;
	}

	public void draw(Graphics window,Rectangle r) {
		window.setColor(color);
		window.fillRect(getX(), getY(), getWidth(), getHeight());
		window.setFont(new Font("test", 5, 10));
		if(getRect().intersects(r)){
			window.drawOval(getX() - range + (getWidth() / 2), getY() - range + (getHeight() / 2), range * 2, range * 2);
			window.drawString("$"+getUpgradePrice(rank),getX(),getY()-10);
		}
		window.setColor(Color.WHITE);
		window.drawString("D:" + damage, getX(), getY() + 10);
		window.drawString("R:" + range, getX(), getY() + 20);
		if (shooterInc > 0) {
			window.setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
			window.drawLine(getX() + getWidth() / 2, getY() + getHeight() / 2, shotX, shotY);
			shooterInc--;
		}
	}

	public int getRank() {
		return rank;
	}

	public int upgrade() {
		this.setDamage((int) (this.getDamage() * 1.25));
		this.setRange((int) (this.getRange() * 1.1));
		rank++;
		return this.getUpgradePrice(rank - 1);
	}

	public int getPrice() {
		return price;
	}

	public int getUpgradePrice(int r) {
		return r * 75;
	}

	public void setDamage(int d) {
		damage = d;
	}

	public void setRange(int r) {
		range = r;
	}

	public void setSpeed(int s) {
		speed = s;
	}

	public int getSpeed() {
		return speed;
	}

	public int getDamage() {
		return damage;
	}

	public int getRange() {
		return range;
	}
}
