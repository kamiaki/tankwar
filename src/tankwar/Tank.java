package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Tank {
	private int x, y;
	
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g){		
		g.setColor(Color.RED);
		g.fillOval(x, y,30, 30);	
	}
		
	public void KEY(KeyEvent e){
		int Key = e.getKeyCode();
		switch (Key) {
		case KeyEvent.VK_W:
			y = y - 5;
			break;
		case KeyEvent.VK_S:
			y = y + 5;
			break;
		case KeyEvent.VK_A:
			x = x - 5;
			break;
		case KeyEvent.VK_D:
			x = x + 5;
			break;
		default:
			break;
		}
	}
}
