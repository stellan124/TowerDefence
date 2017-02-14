import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Player {
	private int money = 500;
	private int lives = 20;
	private int level = 1;
	private EnemyGroup enemies;
	private List<Tower> towers = new ArrayList<Tower>();

	public Player(int m, int l, EnemyGroup e) {
		enemies = e;
		money = m;
		lives = l;
	}
	public void setEnemyGroup(EnemyGroup e)
	{
		enemies = e;
	}
	public int getMoney() {
		return money;
	}
	public int getLevel(){
		return level;
	}
	public void setLevel(int s){
		level = s;
	}
	public void setMoney(int money) {
		this.money = money;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public EnemyGroup getEnemy()
	{
		return enemies;
	}
	//Display
	public void drawStatus(Graphics window,int x,int y)
	{
		window.setColor(Color.yellow);
		window.drawString("Player Stats", x, y);
		window.drawLine(x, y+1, x+57, y+1);
		window.drawString("Level "+level, x, y+12);
		window.drawString("Lives: "+lives, x, y+24);
		window.drawString("$"+money, x, y+36);
	}
	// Tower control
	//int x, int y, int attackRange, int attackDamage, int attackSpeed, Color col
	public boolean intersectsOther(int x, int y){
		Rectangle tempRect = new Rectangle(x-10,y-10,20,20);
		if(towers.size() < 1)
			return false;
		for(int i = 0;i < towers.size();i++)
		{
			if(tempRect.intersects(towers.get(i).getRect()))
				return true;
		}
		return false;
	}
	public void addTower(String s, int x, int y) {
		if (money - (towers.size()*10+100) >= 0 && !intersectsOther(x,y)) {
			money -= towers.size()*10+100;
			if (s == "speed")
				towers.add(new Tower(-50, -50, 50, 20, 200, Color.GREEN));
			else if (s == "range")
				towers.add(new Tower(-50, -50, 150, 35, 75, Color.ORANGE));
			else if (s == "damage")
				towers.add(new Tower(-50, -50, 75, 50, 50, Color.MAGENTA));
			towers.get(towers.size() - 1).setPos(x, y);
		}
	}
	public void upgradeTower(Rectangle r)
	{
		for(int i = 0; i < towers.size();i++)
		{
			if(towers.get(i).getRect().intersects(r)){
				if(money - towers.get(i).getUpgradePrice(towers.get(i).getRank())>= 0){
					money -= towers.get(i).upgrade();
				}
			}
		}
	}
	public int getTowerCount()
	{
		return towers.size();
	}
	public void buyTower(Tower t) {
		if (money - t.getPrice() >= 0) {
			towers.add(t);
			money -= t.getPrice();
		}
	}

	public void fireTowers() {
		if (towers.size() > 0) {
			for (int i = 0; i < towers.size(); i++) {
				towers.get(i).shoot(enemies.getList());
			}
		}
	}

	public void drawTowers(Graphics window,Rectangle r) {
		if (towers.size() > 0) {
			for (int i = 0; i < towers.size(); i++) {
				towers.get(i).draw(window,r);
			}
		}
	}

}
