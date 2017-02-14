//© A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class - 
//Lab  -

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static java.lang.Character.*;

import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class mainLoop extends Canvas implements KeyListener, Runnable,
		MouseListener, MouseMotionListener {
	private EnemyGroup Enemies;
	private Map field;
	private Player user;
	private boolean[] keys;
	private boolean[] mouseDown;
	private BufferedImage back;
	private int[] mousePos = new int[3];
	private Menu menu;
	private String towerType = "damage";
	private Rectangle mouseRect;
	private String sceneSelector = "menu";

	public mainLoop() {
		setBackground(Color.black);
		keys = new boolean[5];
		mouseDown = new boolean[3];
		// objects here
		field = new Map(new int[][] { { 0, -5 }, { 1, 50 }, { 0, 400 },
				{ 1, 150 }, { 0, 50 }, { 1, -150 }, { 0, 50 }, { 1, 200 },
				{ 0, -500 }, { 1, 50 }, { 0, 600 }, { 1, 100 }, { 0, -630 } });

		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		new Thread(this).start();

		setVisible(true);
	}

	public void update(Graphics window) {
		paint(window);
	}

	public void paint(Graphics window) {
		mouseRect = new Rectangle(mousePos[0], mousePos[1], 1, 1);
		// set up the double buffering to make the game animation nice and
		// smooth
		Graphics2D twoDGraph = (Graphics2D) window;

		// take a snap shop of the current screen and same it as an image
		// that is the exact same width and height as the current screen
		if (back == null)
			back = (BufferedImage) (createImage(getWidth(), getHeight()));

		// create a graphics reference to the back ground image
		// we will draw all changes on the background image
		Graphics graphToBack = back.createGraphics();

		graphToBack.setColor(Color.BLACK);
		graphToBack.fillRect(0, 0, 800, 600);

		if (sceneSelector == "menu") {
			if (mouseDown[0]) {
				if (mouseRect.intersects(new Rectangle(250, 100, 300, 50))) {
					sceneSelector = "game";
					Enemies = new EnemyGroup(10, 1);
					user = new Player(500, 20, Enemies);
					menu = new Menu(user);
				}
			}
			drawStartScreen(window);
		} else if (sceneSelector == "game") {
			if (mouseDown[0]) {
				if (mouseRect.intersects(menu.getOrangeRect())) {
					towerType = "range";
				} else if (mouseRect.intersects(menu.getGreenRect())) {
					towerType = "speed";
				} else if (mouseRect.intersects(menu.getMagentaRect())) {
					towerType = "damage";
				}
			}
			if (mouseDown[1]) {
				user.addTower(towerType, mousePos[0] - 10, mousePos[1] - 10);
			}
			if (mouseDown[2] || keys[0]) {
				user.upgradeTower(mouseRect);
			}
			if (Enemies.allDead()) {
				user.setLevel(user.getLevel() + 1);
				Enemies = new EnemyGroup(10, user.getLevel());
				user.setEnemyGroup(Enemies);
			}
			if (Enemies.getStartDelay() <= 0)
				Enemies.moveGroup(field.getList());
			else
				Enemies.setStartDelay(Enemies.getStartDelay() - 1);
			user.setLives(user.getLives() - Enemies.ended());
			user.setMoney((int) (Enemies.cleanUp() * (1 + (.1 * user.getLevel())) + user.getMoney()));
			user.fireTowers();
			if (user.getLives() <= 0) {
				sceneSelector = "gameOver";
			}
			drawGame(window);
		} else if (sceneSelector == "gameOver") {
			drawGameOver(window);
			if (mouseDown[0]) {
				if (mouseRect.intersects(new Rectangle(200, 50, 400, 50))) {
					sceneSelector = "menu";
				}
			}
		}
		window.setFont(new Font("the", 1, 10));
		window.setColor(Color.white);
		window.drawString("Created by Stellan Muller-Cohn", 600, 550);
		mouseDown[0] = false;
		mouseDown[1] = false;
		mouseDown[2] = false;
	}

	// Draw Objects Here
	public void drawGame(Graphics window) {
		window.setColor(Color.BLACK);
		window.fillRect(0, 0, getWidth(), getHeight());
		field.draw(window);
		Enemies.drawGroup(window);
		user.drawTowers(window, mouseRect);
		menu.draw(window, towerType);
		Enemies.drawCountDown(window);
	}

	public void drawStartScreen(Graphics window) {
		Font oldFont = window.getFont();
		window.setColor(Color.BLACK);
		window.fillRect(0, 0, 800, 600);
		window.setColor(Color.BLUE);
		window.fillRect(250, 100, 300, 50);
		window.fillRect(150, 200, 502, 90);
		window.setColor(Color.BLACK);
		window.setFont(new Font("ui", 1, 35));
		window.drawString("START", 340, 140);
		window.setFont(oldFont);
		window.drawString("To select a tower to buy left click on one of the icon on the side menu then right click", 170, 220);
		window.drawString("anywhere on the map to buy and place the tower. To upgrade a tower click the middle ", 170, 240);
		window.drawString("mouse button on one of the towers, if you do not hava a middle mouse button then hover ", 170, 260);
		window.drawString("over the desired tower and press enter to upgrade.", 170, 280);
	}

	public void drawGameOver(Graphics window) {
		window.setColor(Color.BLACK);
		window.fillRect(0, 0, 800, 600);
		window.setColor(Color.BLUE);
		window.fillRect(200, 50, 400, 50);
		window.fillRect(150, 150, 500, 300);
		window.setColor(Color.BLACK);
		window.setFont(new Font("the", 1, 40));
		window.drawString("GAME OVER", 300, 200);
		window.setFont(new Font("g", 1, 30));
		window.drawString("Back to Main Menu", 290, 80);
		window.setFont(new Font("if", 1, 20));
		window.drawString("Reached level " + user.getLevel(), 340, 300);
		window.drawString("Built " + user.getTowerCount() + " towers", 350, 340);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			keys[0] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keys[1] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keys[2] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keys[3] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[4] = true;
		}
		repaint();
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keys[0] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keys[1] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keys[2] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keys[3] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[4] = false;
		}
		repaint();
	}

	public void keyTyped(KeyEvent e) {
		// no code needed here
	}

	public void run() {
		try {
			while (true) {
				Thread.currentThread().sleep(5);
				repaint();
			}
		} catch (Exception e) {
		}
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		mousePos[0] = e.getX();
		mousePos[1] = e.getY();
		if (e.getButton() == 1) {
			mouseDown[0] = true;
		}
		if (e.getButton() == 2) {
			mouseDown[2] = true;
		}
		if (e.getButton() == 3) {
			mouseDown[1] = true;
		}
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		mousePos[0] = e.getX();
		mousePos[1] = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mousePos[0] = e.getX();
		mousePos[1] = e.getY();
	}
}
