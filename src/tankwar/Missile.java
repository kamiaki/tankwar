package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Missile implements InitValue{
	private TankClient tankClient = null;								//大管家指针
	public static final int missileXlength = 10, missileYlength = 10;	//子弹的大小
	private int X, Y, xspeed, yspeed;									//子弹位置 和 速度
	private int MissileType = type_player;								//子弹种类
	Direction MissileFangXiang;											//子弹方向
	private boolean live = true;										//子弹是否活着
	
	/**
	 * 获取子弹种类
	 * @return
	 */
	public int getMissileType() {
		return MissileType;
	}
	/**
	 * 设置子弹种类
	 * @param tankType
	 */
	public void setMissileType(int tankType) {
		MissileType = tankType;
	}
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
	public Missile(int x, int y, Direction missileFangXiang, int tankType, int xspeed,int yspeed, TankClient tc) {
		this.X = x;
		this.Y = y;
		MissileFangXiang = missileFangXiang;
		this.MissileType = tankType;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.tankClient = tc;
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
		g.fillOval(X, Y, missileXlength, missileYlength);
		g.setColor(c);
	}
	/**
	 * 获取子弹的矩形
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(X, Y, missileXlength, missileYlength);
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
		
		if(tankClient != null){
			if(X < 0 || Y < 0 || X > WindowsXlength || Y > WindowsYlength){
				tankClient.missiles.remove(this);	//在队列中移除子弹
				this.live = false;					//子弹生命判断为死		
			}
		}
	}	
	/**
	 * 击中坦克
	 * @param t
	 * @return
	 */
	public boolean hitTank(Tank t){
		if(this.getRect().intersects( t.getRect()) && t.isTankLive() && Missile.this.getMissileType() != t.getTankType() ){			
			Explode e = new Explode(this.X, this.Y, this.tankClient);	//添加一个爆炸
			tankClient.explodes.add(e);
			
			tankClient.ZhenDong();										//大管家 震动方法			
			
			tankClient.missiles.remove(this);							//在队列中移除子弹
			this.live = false;											//子弹生命判断为死
			t.setTankLive(false);										//坦克生命判断为死		
			
			if(t.getTankType() == type_enemy){
				tankClient.killTankNumber += 1;
			}
			if(t.getTankType() == type_player){
				if(tankClient.reTankNumber == 0){
					tankClient.reTankNumber -= 1;
				}
			}			
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
