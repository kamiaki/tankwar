package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Missile implements InitValue{
	TankClient TC = null;
	int X, Y, xspeed = 3, yspeed = 3;
	public static final int missileX = 10, missileY = 10;
	private boolean live = true;
	Direction MissileFangXiang;
	/**
	 * 设置子弹存活状态
	 * @param live
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * 判断子弹是否存活
	 * @return
	 */
	public boolean isLive() {
		return live;
	}
	/**
	 * 构造函数
	 * @param x
	 * @param y
	 * @param missileFangXiang
	 * @param tc
	 */
	public Missile(int x, int y, Direction missileFangXiang,TankClient tc) {
		this.X = x;
		this.Y = y;
		MissileFangXiang = missileFangXiang;
		this.TC = tc;
		this.live = true;
		MissileQD();
	}
	/**
	 * 画子弹
	 * @param g
	 */
	public void draw(Graphics g) {
		MissilePicture(g);
	}
	/**
	 * 子弹贴图
	 * @param g
	 */
	private void MissilePicture(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(X, Y, 10, 10);
		g.setColor(c);
	}
	/**
	 * 启动子弹移动线程
	 */
	private void MissileQD(){
		new Thread(new Runnable() {
			public void run() {	
				while(live){
					move();
					try {Thread.sleep(10);} catch (Exception e) {}
				}	
			}
		}).start();
	}
	/**
	 * 移动子弹
	 */
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
		
		if(TC != null){
			if(X < 0 || Y < 0 || X > WindowsXlength || Y > WindowsYlength){
				this.live = false;			//子弹生命判断为死		
				TC.missiles.remove(this);	//在队列中移除子弹
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
			this.live = false;									//子弹生命判断为死
			TC.missiles.remove(this);							//在队列中移除子弹
			
			TC.ZhenDong();										//大管家震动			
			t.setTankLive(false);								//坦克生命判断为死
			
			Explode e = new Explode(this.X, this.Y, this.TC);	//添加一个爆炸
			TC.explodes.add(e);
			
			return true;
		}
		return false;
	}
	/**
	 * 击中坦克链表
	 * @param enemyTanks
	 */
	public boolean hitTanks(List<Tank> enemyTanks) {
		for(int i = 0; i < enemyTanks.size(); i++){
			if(hitTank(enemyTanks.get(i))){
				enemyTanks.remove(i);
				return true;
			}			
		}
		return false;
	}
}
