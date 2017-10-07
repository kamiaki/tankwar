package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
/**
 * 子弹类
 *
 */
public class Missile implements InitValue{
	private TankClient tankClient = null;								//大管家指针
	public static final int missileXlength = 10, missileYlength = 10;	//子弹的大小
	private int X, Y, xspeed, yspeed, oldX, oldY;						//子弹位置 和 速度
	private int MissileType = type_player;								//子弹种类
	Direction MissileFangXiang;											//子弹方向
	private boolean live = true;										//子弹是否活着
	//各种子弹
	private int MissileZhongLei = Misslie_putong;						//炮弹种类
	public boolean ZhuiZongPD = false;									//追踪弹是否启动
	
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
	public Missile(int x, int y,int MissileZhongLei, Direction missileFangXiang, int tankType, int xspeed,int yspeed, TankClient tc) {
		this.X = x;
		this.Y = y;
		this.MissileZhongLei = MissileZhongLei;
		MissileFangXiang = missileFangXiang;
		this.MissileType = tankType;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.oldX = x;
		this.oldY = y;
		this.tankClient = tc;
		this.live = true;
		MissileQD();
	}
	/**
	 * 画子弹
	 * @param g
	 */
	public void draw(Graphics g) {
		if(live) {
			MissilePicture(g);
		}
	}
	/**
	 * 子弹贴图
	 * @param g
	 */
	private void MissilePicture(Graphics g){
		Color c = g.getColor();
		if(MissileType == type_player) {
			switch (MissileZhongLei) {
			case Misslie_putong:
				g.setColor(Color.BLACK);
				break;
			case Misslie_bafang:
				g.setColor(Color.BLUE);
				break;
			case Misslie_zhuizong:
				g.setColor(Color.MAGENTA);
				break;
			default:
				break;
			}
		}else {
			g.setColor(Color.GRAY);
		}
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
	 * 启动子弹数据更新程序
	 */
	private void MissileQD(){
		if(MissileType == type_player)Follow();  	//启动追踪弹线程
		MissileMove();						  		//子弹移动线程
		TimeMissileDead();								//子弹消亡线程
	}
	/**
	 * 子弹移动线程
	 */
	private void MissileMove(){
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
	 * 追踪弹线程
	 */
	private void Follow(){
		new Thread(new Runnable() {
			public void run() {
				int tankx = 0;
				int tanky = 0;
				double Distance = 0;
				List<Tank> enemytanks = tankClient.enemyTanks;
				//如果玩家坦克活着再追
				while(live){
					//吃到追踪弹再追踪
					while(ZhuiZongPD) {
						for(int i = 0; i < enemytanks.size(); i++) {
							Tank enemytank = enemytanks.get(i);
							Distance = Math.sqrt(Math.pow(Math.abs(Missile.this.X - enemytank.getX()), 2) + Math.pow(Math.abs(Missile.this.X - enemytank.getX()), 2));
							if(Distance < 100) {
								tankx = enemytank.getX();
								tanky = enemytank.getY();	
								if(Missile.this.X < tankx && Missile.this.Y == tanky){
									MissileFangXiang = Direction.d6;
								}else if(Missile.this.X < tankx && Missile.this.Y < tanky){
									MissileFangXiang = Direction.d3;
								}else if(Missile.this.X == tankx && Missile.this.Y < tanky){
									MissileFangXiang = Direction.d2;
								}else if(Missile.this.X > tankx && Missile.this.Y < tanky){
									MissileFangXiang = Direction.d1;
								}else if(Missile.this.X > tankx && Missile.this.Y == tanky){
									MissileFangXiang = Direction.d4;
								}else if(Missile.this.X > tankx && Missile.this.Y > tanky){
									MissileFangXiang = Direction.d7;
								}else if(Missile.this.X == tankx && Missile.this.Y > tanky){
									MissileFangXiang = Direction.d8;
								}else if(Missile.this.X < tankx && Missile.this.Y > tanky){
									MissileFangXiang = Direction.d9;
								}							
							}					
						}
						try {Thread.sleep(10);} catch (Exception e) {}	
					}
					try {Thread.sleep(10);} catch (Exception e) {}	
				}
			}
		}).start();
	}
	/**
	 * 子弹随时间消亡线程
	 */
	private void TimeMissileDead(){
		new Thread(new Runnable() {
			public void run() {	
				try {Thread.sleep(5000);} catch (Exception e) {}
				if(Missile.this.live){
					Missile.this.live = false;											//子弹生命判断为死
					if(Missile.this != null) {
						tankClient.missiles.remove(Missile.this);						//在队列中移除子弹
					}
				}	
			}
		}).start();
	}
	/**
	 * 移动子弹
	 */
	private void move() {	
		if( Missile.this.hitWalls(tankClient.walls) ) {
			this.missileDead();
		}else {
			//上一次移动的坐标
			this.oldX = X;
			this.oldY = Y;	
			//开始移动
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
					this.live = false;						//子弹生命判断为死
					if(this != null) {
						tankClient.missiles.remove(this);	//在队列中移除子弹		
					}
					
				}
			}
		}
	}
	/**
	 * 让子弹直接死亡
	 */
	private void missileDead() {
		this.live = false;
		if(this != null) {
			tankClient.missiles.remove(this);	
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
			
			this.live = false;											//子弹生命判断为死
			if(this != null) {
				tankClient.missiles.remove(this);						//在队列中移除子弹
			}
				
			if(t.getTankType() == type_player) {
				t.setBlood(t.getBlood() - 20);
				if(t.getBlood() <= 0) {
					t.setTankLive(false);								//玩家坦克生命判断为死	
					if(tankClient.reTankNumber == 0){
						tankClient.reTankNumber -= 1;
					}
				}
			}else if(t.getTankType() == type_enemy){	
				//什么样的子弹掉什么样的血
				if( this.MissileZhongLei == Misslie_bafang ) {
					t.setBlood(t.getBlood() - Misslie_bafangX);
				}else if( this.MissileZhongLei == Misslie_zhuizong ) {
					t.setBlood(t.getBlood() - Misslie_zhuizongX);
				}else {
					t.setBlood(t.getBlood() - Misslie_putongX);
				}	
				//杀死敌人后 数量 + 1
				if(t.getBlood() <= 0) {
					t.setTankLive(false);								//敌人坦克生命判断为死
					tankClient.killTankNumber += 1;
					return true;
				}else {
					return false;
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
				if(enemyTanks.get(i) != null) {
					enemyTanks.remove(enemyTanks.get(i));
				}
				return true;
			}			
		}
		return false;
	}
	/**
	 * 击中了墙
	 * @param w
	 * @return
	 */
	public boolean hitWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			return true;
		}
		return false;
	}
	/**
	 * 击中了墙链表
	 * @param w
	 * @return
	 */
	public boolean hitWalls(List<Wall> walls) {
		for(int i = 0; i < walls.size(); i++) {
			if(hitWall(walls.get(i))) {
				return true;
			}
		}
		return false;
	}
}
