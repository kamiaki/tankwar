package tankwar;

import java.awt.Color;
import java.awt.Graphics;

public class Missile {
	
	int X, Y, xspeed = 3, yspeed = 3;
	public static final int missileX = 10, missileY = 10;
	Direction MissileFangXiang;
	
	public Missile(int x, int y, Direction missileFangXiang) {
		super();
		this.X = x;
		this.Y = y;
		MissileFangXiang = missileFangXiang;
		MissileQD();
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(X, Y, 10, 10);
		g.setColor(c);
	}

	private void MissileQD(){
		new Thread(new Runnable() {
			public void run() {	
				while(true){
					move();
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}	
			}
		}).start();
	}
	
	private void move() {
		switch (MissileFangXiang) {
		case d4:
			X = X - xspeed;
			break;
		case d7:
			X = X - xspeed;
			Y = Y - yspeed;
			break;
		case d8:
			Y = Y - yspeed;
			break;
		case d9:
			X = X + xspeed;
			Y = Y - yspeed;
			break;
		case d6:
			X = X + xspeed;
			break;
		case d3:
			X = X + xspeed;
			Y = Y + yspeed;
			break;
		case d2:
			Y = Y + yspeed;
			break;
		case d1:
			X = X - xspeed;
			Y = Y + yspeed;
			break;
		case d5:
			break;
		default:
			break;
		}	
	}
}
