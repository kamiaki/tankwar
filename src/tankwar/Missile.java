package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Missile implements InitValue{
	TankClient TC = null;
	int X, Y, xspeed = 3, yspeed = 3;
	public static final int missileX = 10, missileY = 10;
	private boolean live = true;
	
	Direction MissileFangXiang;
		
	public boolean isLive() {
		return live;
	}
	
	public Missile(int x, int y, Direction missileFangXiang) {
		this.X = x;
		this.Y = y;
		MissileFangXiang = missileFangXiang;
		MissileQD();
	}
	
	public Missile(int x, int y, Direction missileFangXiang,TankClient tc) {
		this(x,y,missileFangXiang);
		this.TC = tc;
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
		if(live == false){
			TC.missiles.remove(this);
			return;
		}
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
		
		if(TC != null){
			if(X < 0 || Y < 0 || X > WindowsXlength || Y > WindowsYlength){
				live = false;
				TC.missiles.remove(this);
			}
		}
	}
	
	/**
	 * 获取子弹的矩形
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(X, Y, missileX, missileY);
	}
	
	/**
	 * 击中坦克
	 * @param t
	 * @return
	 */
	public boolean hitTank(Tank t){
		if(  this.getRect().intersects( t.getRect() )  && t.isTankLive()){
			t.setTankLive(false);
			this.live = false;
			return true;
		}
		return false;
	}
}
