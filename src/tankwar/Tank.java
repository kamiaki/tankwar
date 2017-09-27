package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Tank{
	private int X, Y, xspeed = 1, yspeed = 1;
	public static final int tankX = 30, tankY = 30;
	private boolean Up = false, Down = false, Left = false, Right = false;
	private Direction FangXiang = Direction.d5;
	private Windows windows = null;
	
	public Tank(int x, int y) {
		this.X = x;
		this.Y = y;
		TankQD();
	}
	
	public Tank(int x, int y, Windows w){
		this(x,y);
		this.windows = w;
	}
	
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(X, Y,tankX, tankY);
		g.setColor(c);
	}
	
	private void TankQD(){
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
		switch (FangXiang) {
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

	public void KEY(KeyEvent e){
		int Key = e.getKeyCode();
		switch (Key) {
		case KeyEvent.VK_NUMPAD0:
			windows.missile = fire();
			break;
		case KeyEvent.VK_W:
			Up = true;
			break;
		case KeyEvent.VK_S:
			Down = true;
			break;
		case KeyEvent.VK_A:
			Left = true;
			break;
		case KeyEvent.VK_D:
			Right = true;
			break;
		case KeyEvent.VK_O:
			xspeed++;
			yspeed++;
			break;
		case KeyEvent.VK_P:
			xspeed--;
			yspeed++;
			break;
		default:
			break;
		}
		PDFangXiang();
	}
	
	public void noKEY(KeyEvent e){
		int Key = e.getKeyCode();
		switch (Key) {
		case KeyEvent.VK_W:
			Up = false;
			break;
		case KeyEvent.VK_S:
			Down = false;
			break;
		case KeyEvent.VK_A:
			Left = false;
			break;
		case KeyEvent.VK_D:
			Right = false;
			break;
		default:
			break;
		}
		PDFangXiang();
	}
	
	private void PDFangXiang(){
		if(!Up && !Down && Left && !Right)FangXiang = Direction.d4;
		else if(Up && !Down && Left && !Right)FangXiang = Direction.d7;
		else if(Up && !Down && !Left && !Right)FangXiang = Direction.d8;
		else if(Up && !Down && !Left && Right)FangXiang = Direction.d9;
		else if(!Up && !Down && !Left && Right)FangXiang = Direction.d6;
		else if(!Up && Down && !Left && Right)FangXiang = Direction.d3;
		else if(!Up && Down && !Left && !Right)FangXiang = Direction.d2;
		else if(!Up && Down && Left && !Right)FangXiang = Direction.d1;
		else if(!Up && !Down && Left && Right)FangXiang = Direction.d5;
		else if(Up && Down && !Left && !Right)FangXiang = Direction.d5;
		else if(!Up && !Down && !Left && !Right)FangXiang = Direction.d5;
		else if(Up && Down && Left && Right)FangXiang = Direction.d5;
	}
	
	/**
	 * 生成一个子弹
	 * @return
	 */
	public Missile fire(){
		int x = this.X + Tank.tankX/2 - Missile.missileX/2;
		int y = this.Y + Tank.tankY/2 - Missile.missileY/2;
		Missile missile = new Missile(x, y, FangXiang);
		return missile;
		
	}
}
