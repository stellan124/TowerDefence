import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Map {
	int[] start = new int[] { 0, 0 };
	int width = 20;
	List<int[]> coordinates = new ArrayList<int[]>();
	Color color = Color.DARK_GRAY;

	public Map() {
	}

	// int[][] = [0 or 1(0 = x, 1 = y),distance]
	public Map(int[][] matrix) {
		for (int[] i : matrix) {
			coordinates.add(i);
		}
	}

	public void add(int[] spot) {
		coordinates.add(spot);
	}

	public void draw(Graphics window) {
		int currentX = start[0];
		int currentY = start[1];
		window.setColor(color);
		for (int i = 1; i < coordinates.size(); i++) {
			if (coordinates.get(i)[0] == 0) {
				if (coordinates.get(i)[1] > 0) {
					window.fillRect(currentX, currentY, coordinates.get(i)[1], width);
				} else {
					window.fillRect(currentX + width, currentY, coordinates.get(i)[1] - width, width);
				}
				currentX += coordinates.get(i)[1];
			} else {
				if (coordinates.get(i)[1] > 0) {
					window.fillRect(currentX, currentY, width, coordinates.get(i)[1]);
				} else {
					window.fillRect(currentX, currentY + width, width, coordinates.get(i)[1] - width);
				}
				currentY += coordinates.get(i)[1];
			}
		}
	}
	
	public void setWidth(int w) {
		width = w;
	}

	public void setColor(Color c) {
		color = c;
	}
	public int[] getStart()
	{
		return start;
	}

	public List<int[]> getList() {
		return coordinates;
	}
}
