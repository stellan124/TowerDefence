import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;


public class Menu {
	private List<Tower> displayTowers = new ArrayList<Tower>();
	private Rectangle orange, green, magenta;
	private Player menudisplay;
	public Menu(Player p)
	{
		orange = new Rectangle(720, 30, 30, 30);
		green = new Rectangle(720, 80, 30, 30);
		magenta = new Rectangle(720, 130, 30, 30);
		menudisplay = p;
	}
	public void draw(Graphics window,String s)
	{
		window.setColor(Color.DARK_GRAY);
		window.fillRect(690, 0, 110, 600);
		window.setColor(Color.yellow);
		window.drawString("Menu", 720, 20);
		window.drawLine(720, 22, 749, 22);
		window.setColor(Color.orange);
		window.fillRect(720, 30, 30, 30);
		window.drawString("LongRange", 710, 70);
		window.setColor(Color.WHITE);
		window.drawString("D: "+35, 720, 40);
		window.drawString("R: "+200, 720, 50);
		window.drawString("S: "+75, 720, 60);
		window.setColor(Color.GREEN);
		window.fillRect(720, 80, 30, 30);
		window.drawString("FastAttack", 710, 120);
		window.setColor(Color.WHITE);
		window.drawString("D: "+20, 720, 90);
		window.drawString("R: "+50, 720, 100);
		window.drawString("S: "+200, 720, 110);
		window.setColor(Color.magenta);
		window.fillRect(720, 130, 30, 30);
		window.drawString("HeavyDamage", 710, 170);
		window.setColor(Color.WHITE);
		window.drawString("D: "+50, 720, 140);
		window.drawString("R: "+75, 720, 150);
		window.drawString("S: "+50, 720, 160);
		menudisplay.drawStatus(window, 700, 190);
		menudisplay.getEnemy().enemyStats(window, 700, 250);
		selected(window,s);
		
	}
	public void selected(Graphics window,String s)
	{
		window.setColor(Color.YELLOW);
		window.drawString("Right click to place", 690, 300);
		window.drawString("tower number "+(menudisplay.getTowerCount()+1), 690, 310);
		window.drawString("for $"+(menudisplay.getTowerCount()*10+100), 690, 320);
		if(s == "range"){
			window.setColor(Color.ORANGE);
			window.drawString("Currently Placing", 695, 335);
			window.fillRect(720, 340, 30, 30);
			window.drawString("LongRange", 710, 380);
		}else if(s == "speed"){
			window.setColor(Color.GREEN);
			window.drawString("Currently Placing", 695, 335);
			window.fillRect(720, 340, 30, 30);
			window.drawString("FastAttack", 710, 380);
		}else{
			window.setColor(Color.MAGENTA);
			window.drawString("Currently Placing", 695, 335);
			window.fillRect(720, 340, 30, 30);
			window.drawString("HeavyDamage", 705, 380);
		}
	}
	public Rectangle getOrangeRect()
	{
		return orange;
	}
	public Rectangle getGreenRect()
	{
		return green;
	}
	public Rectangle getMagentaRect()
	{
		return magenta;
	}
}
