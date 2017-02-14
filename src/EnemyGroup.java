import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnemyGroup {
	private List<Enemy> group;
	private List<Enemy> movingGroup;
	private int[] startVar = new int[] { 1, 100 };
	private int indexSent = -1;
	private int origSize = 0;
	private int[] currentStats = new int[3];
	private int startDelay = 500;

	public EnemyGroup() {
		group = new ArrayList<Enemy>();
		movingGroup = new ArrayList<Enemy>();
		add(new Enemy(10000, 10000, 0));
	}

	public EnemyGroup(int size,int rank) {
		group = new ArrayList<Enemy>();
		movingGroup = new ArrayList<Enemy>();
		group.add(new Enemy(10000, 10000, 0));
		for (int i = 0; i < size; i++) {
			add(new Enemy(-500, -500, (int)(rank*.05)+1,rank*10+200));
		}
		currentStats[0] = (int)(rank*.05)+1;
		currentStats[1] = rank*10+200;
		currentStats[2] =  group.get(1).getValue()+(int)(1 + (1 * rank-1));
	}
	
	public int ended()
	{
		int num = 0;
		for(int i = 0; i < group.size();i++)
		{
			num += group.get(i).reachedEnd();
		}
		return num;
	}
	
	public void add(Enemy e) {
		group.add(e);
		origSize++;
	}
	public void setStartDelay(int s)
	{
		startDelay = s;
	}
	public int getStartDelay()
	{
		return startDelay;
	}
	public void drawCountDown(Graphics window)
	{
		Font oldFont = window.getFont();
		if(startDelay > 0){
			window.setFont(new Font("Thing",1,30));
			window.setColor(Color.yellow);
			window.drawString("TIME TO NEXT WAVE "+startDelay, 150, 250);
		}
		window.setFont(oldFont);
	}
	public void enemyStats(Graphics window,int x, int y)
	{
		window.setColor(Color.yellow);
		window.drawString("Enemy Stats", x, y);
		window.drawLine(x, y+1, x+60, y+1);
		window.drawString("HP: "+currentStats[1], x, y+12);
		window.drawString("Speed: "+currentStats[0],x,y+24);
		window.drawString("Value: "+currentStats[2], x, y+36);
	}

	public int cleanUp() {
		int income = 0;
		for (int i = group.size() - 1; i > 0; i--) {
			income += group.get(i).killed();
		}
		return income;
	}
	public boolean allDead()
	{
		for(int i = 1;i < group.size();i++){
			if(!group.get(i).isDead())
				return false;
		}
		return true;
	}
	public void drawGroup(Graphics window) {
		for (int i = 0; i < group.size(); i++) {
			group.get(i).draw(window);
		}
	}

	public void sender() {
		if (startVar[0] < group.size()) {
			if (startVar[1] == 100) {
				movingGroup.add(group.get(startVar[0]));
				movingGroup.get(movingGroup.size() - 1).setPos(10, 0);
				startVar[0]++;
				startVar[1] = 0;
			} else {
				startVar[1]++;
			}
		}
	}

	public void moveGroup(List<int[]> points) {
		sender();
		for (int i = 0; i < movingGroup.size(); i++) {
			movingGroup.get(i).direction(points);
			movingGroup.get(i).move(movingGroup.get(i).getDirection());
		}
	}

	public List<Enemy> getList() {
		return group;
	}
}
