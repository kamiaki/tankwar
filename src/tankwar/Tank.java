package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;
/**
 * 坦克类
 *
 */
public class Tank implements InitValue{	
	private TankClient tankClient;						//大管家
	public static final int tankX = 30, tankY = 30;		//坦克大小
	private int X, Y, xspeed, yspeed, oldX, oldY;		//坦克位置 速度
	private boolean Up = false, Down = false,			//坦克按键方向
					Left = false, Right = false;
	private Direction FangXiang = Direction.d5;			//坦克移动方向
	private Direction ptDir = Direction.d4;				//子弹 和 炮筒方向
	private int TankType = type_player;					//坦克种类
	private Color tankColor;							//坦克颜色
	private int TrackingDistance = 200;					//坦克追击半径
	private boolean live = true;						//坦克是否活着
	private int blood = 100;							//生命值
	private int bloodZong = 100;						//生命值总数
	private BloodBar bloodBar = new BloodBar();			//血条类
	//各种子弹数量
	public int BaFangNumber = 0;						//八方炮数量
	public int ZhuiZongNumber = 0;						//追踪弹数量
	
	/**
	 * 坦克构造函数
	 * @param x
	 * @param y
	 * @param good
	 * @param Co
	 * @param w
	 */
	public Tank(int x, int y, int type, Color Co , int xspeed, int yspeed, TankClient w){
		this.X = x;
		this.Y = y;
		this.TankType = type;
		this.tankColor = Co;
		this.xspeed = xspeed;
		this.yspeed = xspeed;
		this.oldX = x;
		this.oldY = y;
		this.tankClient = w;
		this.live = true;
		switch (TankType) {
		case type_player:
			blood = 100;
			bloodZong = blood;
			break;
		case type_enemy:
			blood = 60;
			bloodZong = blood;
			break;
		default:
			break;
		}
		TankQD();
	}
	/**
	 * 获取血量
	 * @return
	 */
	public int getBlood() {
		return blood;
	}
	/**
	 * 改变血量
	 * @param blood
	 */
	public void setBlood(int blood) {
		this.blood = blood;
	}
	public static Random random = new Random();			//随机器
	/**
	 * 判断是什么坦克
	 * @return
	 */
	public int getTankType() {
		return TankType;
	}
	/**
	 * 设置是什么坦克
	 * @param good
	 */
	public void setTankType(int type) {
		TankType = type;
	}
	/**
	 * 判断死活
	 * @return
	 */
	public boolean isTankLive() {
		return live;
	}
	/**
	 * 设置死活
	 * @param tankLive
	 */
	public void setTankLive(boolean tankLive) {
		this.live = tankLive;
	}	
	/**
	 * 坦克x位置
	 * @return
	 */
	public int getY() {
		return Y;
	}
	/**
	 * 坦克Y位置
	 * @return
	 */
	public int getX() {
		return X;
	}
	/**
	 * 画坦克
	 * @param g
	 */
	public void draw(Graphics g){	
		if(live) {
			TankPicture(g);	
		}	
	}
	/**
	 * 坦克贴图
	 * @param g
	 */
	private void TankPicture(Graphics g){
		Color c = g.getColor();
		g.setColor(tankColor);
		g.fillOval(X, Y,tankX, tankY);
		g.setColor(c);
		paotong(g);
		bloodBar.draw(g);
	}
	/**
	 * 获取坦克的矩形
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(X, Y, tankX, tankY);
	}
	/**
	 * 画炮筒
	 * @param g
	 */
	private void paotong(Graphics g){
		Color c = g.getColor();
		int shenchu = 3;					//伸出的长度
		g.setColor(Color.BLACK);
		switch (ptDir) {
		case d4:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X - shenchu, Y + Tank.tankY/2);
			break;
		case d7:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X, Y);
			break;
		case d8:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X + Tank.tankX/2, Y - shenchu);
			break;
		case d9:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X + Tank.tankX, Y);
			break;
		case d6:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X + Tank.tankX + shenchu, Y + Tank.tankY/2);
			break;
		case d3:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X + Tank.tankX, Y + Tank.tankY);
			break;
		case d2:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X + Tank.tankX/2, Y + Tank.tankY + shenchu);
			break;
		case d1:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X, Y + Tank.tankY);
			break;
		default:
			break;
		}
		g.setColor(c);
	}
	/**
	 * 启动坦克数据更新程序
	 */
	private void TankQD(){
		if(this.TankType == type_player){
			MoveThread();
		}else{
			autoMove();
			ZhuiMove();
			autofire();
			MoveThread();
		}
	}
	/**
	 * 移动线程
	 */
	private void MoveThread(){
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
	 * 追踪移动线程
	 */
	private void ZhuiMove(){
		new Thread(new Runnable() {
			public void run() {
				int tankx = 0;
				int tanky = 0;
				while(live){
					//如果玩家坦克活着再追
					if( tankClient.myTank.isTankLive() ){
						tankx = tankClient.myTank.X;
						tanky = tankClient.myTank.Y;	
						if( Math.sqrt(Math.pow(Math.abs(Tank.this.X - tankx), 2) + Math.pow(Math.abs(Tank.this.Y - tanky), 2)) < TrackingDistance ){
							if(Tank.this.X < tankx && Tank.this.Y == tanky){
								FangXiang = Direction.d6;
							}else if(Tank.this.X < tankx && Tank.this.Y < tanky){
								FangXiang = Direction.d3;
							}else if(Tank.this.X == tankx && Tank.this.Y < tanky){
								FangXiang = Direction.d2;
							}else if(Tank.this.X > tankx && Tank.this.Y < tanky){
								FangXiang = Direction.d1;
							}else if(Tank.this.X > tankx && Tank.this.Y == tanky){
								FangXiang = Direction.d4;
							}else if(Tank.this.X > tankx && Tank.this.Y > tanky){
								FangXiang = Direction.d7;
							}else if(Tank.this.X == tankx && Tank.this.Y > tanky){
								FangXiang = Direction.d8;
							}else if(Tank.this.X < tankx && Tank.this.Y > tanky){
								FangXiang = Direction.d9;
							}		
						}
					}
					try {Thread.sleep(10);} catch (Exception e) {}	
				}
			}
		}).start();
	}
	/**
	 * 自动移动线程
	 */
	private void autoMove(){
		new Thread(new Runnable() {
			public void run() {
				int tankx = 0;
				int tanky = 0;
				int time = 0;
				while(live){
					time = random.nextInt(2000) + 500;
					tankx = tankClient.myTank.X;
					tanky = tankClient.myTank.Y;
					if(Math.sqrt(Math.pow(Math.abs(Tank.this.X - tankx), 2) + Math.pow(Math.abs(Tank.this.Y - tanky), 2))  >= TrackingDistance){
						Direction[] dirs = Direction.values();
						FangXiang = dirs[ random.nextInt(dirs.length) ];
					}
					try {Thread.sleep(time);} catch (Exception e) {}	
				}
			}
		}).start();
	}
	/**
	 * 坦克移动
	 */
	private void move() {
		if( Tank.this.ZhuangWalls(tankClient.walls) || Tank.this.ZhuangTank(tankClient.myTank) || Tank.this.ZhuangTanks(tankClient.enemyTanks) ) {
			this.stay();
		}else {
			//上一次移动的坐标
			this.oldX = X;
			this.oldY = Y;		
			//开始移动
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
			//子弹初始方向 炮筒初始方向
			if( FangXiang != Direction.d5 ) this.ptDir = this.FangXiang;
			//坦克不能出界
			if(X < 0) this.stay();
			if(Y < 0) this.stay();
			if(X + Tank.tankX > WindowsXlength) this.stay();
			if(Y + Tank.tankY + 30 > WindowsYlength) this.stay();
		}
	}
	/**
	 * 返回上一次移动的位置 （就是停止）
	 */
	private void stay() {
		this.X = oldX;
		this.Y = oldY;
	}
	/**
	 * 自动开火
	 */
	public void autofire(){
		new Thread(new Runnable() {
			public void run() {
				int sleepInt = 0;
				int x = 0,y = 0;
				while(live){
					sleepInt = random.nextInt(2000) + 500;
					if(tankClient != null){
						x = Tank.this.X + Tank.tankX/2 - Missile.missileXlength/2;
						y = Tank.this.Y + Tank.tankY/2 - Missile.missileYlength/2;
						if(ptDir == Direction.d5)ptDir = Direction.d6;//炮弹不能不动
						Missile missile = new Missile(x, y, Misslie_putong, ptDir, type_enemy, 2, 2, tankClient);
						tankClient.missiles.add(missile);	
					}
					try {Thread.sleep(sleepInt);} catch (Exception e) {}
				}			
			}
		}).start();	
	}
	/**
	 * 玩家开火 生成一个子弹
	 * @return
	 */
	public void fire(){
		if(tankClient != null && Tank.this.isTankLive()){
			int x = this.X + Tank.tankX/2 - Missile.missileXlength/2;
			int y = this.Y + Tank.tankY/2 - Missile.missileYlength/2;
			if(ptDir == Direction.d5)ptDir = Direction.d6;//炮弹不能不动
			Missile missile = new Missile(x, y, Misslie_putong, ptDir, type_player, 5, 5, tankClient);
			tankClient.missiles.add(missile);	
		}
	}		
	/**
	 * 玩家开火 四面八方炮
	 * @return
	 */
	public void BaFangfire(){
		if(tankClient != null && Tank.this.isTankLive()){
			int x = this.X + Tank.tankX/2 - Missile.missileXlength/2;
			int y = this.Y + Tank.tankY/2 - Missile.missileYlength/2;
			Direction[] directions = Direction.values();
			Direction direction = Direction.d1;
			for(int i = 0; i < 8; i++) {
				direction = directions[i];
				Missile missile = new Missile(x, y, Misslie_bafang, direction, type_player, 4, 4, tankClient);
				tankClient.missiles.add(missile);	
			}			
		}
	}
	/**
	 * 玩家开火 生成一个追踪子弹
	 * @return
	 */
	public void ZhuiZongfire(){
		if(tankClient != null && Tank.this.isTankLive()){
			int x = this.X + Tank.tankX/2 - Missile.missileXlength/2;
			int y = this.Y + Tank.tankY/2 - Missile.missileYlength/2;
			if(ptDir == Direction.d5)ptDir = Direction.d6;//炮弹不能不动
			Missile missile = new Missile(x, y, Misslie_zhuizong, ptDir, type_player, 3, 3, tankClient);
			missile.ZhuiZongPD = true;
			tankClient.missiles.add(missile);	
		}
	}	
	/**
	 * 按下按键
	 * @param e
	 */
	public void KEY(KeyEvent e){
		int Key = e.getKeyCode();
		switch (Key) {
		case KeyEvent.VK_NUMPAD0:		
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
		default:
			break;
		}
		PDFangXiang();
	}
	/**
	 * 抬起按键
	 * @param e
	 */
	public void noKEY(KeyEvent e){
		int Key = e.getKeyCode();
		switch (Key) {
		case KeyEvent.VK_NUMPAD1:
			fire();
			break;
		case KeyEvent.VK_NUMPAD2:
			 
//			if(BaFangNumber > 7) {
				BaFangNumber -= 8;
				BaFangfire();
//			}
			break;
		case KeyEvent.VK_NUMPAD3:
//			if(ZhuiZongNumber > 0) {
				ZhuiZongNumber -= 1;
				ZhuiZongfire();
//			}
			break;
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
	/**
	 * 判断方向
	 */
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
	 * 坦克撞墙
	 * @param w
	 * @return
	 */
	public boolean ZhuangWall(Wall w) {
		if(this.live && Tank.this.getRect().intersects(w.getRect())) {
			return true;
		}
		return false;
	}
	/**
	 * 坦克撞链表墙
	 * @param w
	 * @return
	 */
	public boolean ZhuangWalls(List<Wall> walls) {
		for(int i = 0; i < walls.size(); i++) {
			if(ZhuangWall(walls.get(i))){
				return true;
			}
		}
		return false;
	}
	/**
	 * 坦克与坦克相撞
	 * @param t
	 * @return
	 */
	public boolean ZhuangTank(Tank t) {
		if(this.live == true && t.live == true && this.getRect().intersects(t.getRect()) && this != t   ) {
			return true;
		}
		return false;
	}
	/**
	 * 坦克与坦克相撞 读取链表
	 * @param t
	 * @return
	 */
	public boolean ZhuangTanks(List<Tank> tanks) {
		for(int i = 0; i < tanks.size(); i++) {
			if( ZhuangTank(tanks.get(i)) ) {
				return true;
			}	
		}
		return false;
	}
	/**
	 * 吃物品
	 * @param b
	 * @return
	 */
	private ItemsType eat(Item item) {
		ItemsType itemsType = ItemsType.NoItem;
		if(this.live == true && this.getRect().intersects(item.getRect())) {
			switch (item.getItemsType()) {
			case Blood:
				itemsType = ItemsType.Blood;
				break;
			case WeaponBaFang:
				itemsType = ItemsType.WeaponBaFang;
				break;
			case WeaponZhuiZong:
				itemsType = ItemsType.WeaponZhuiZong;
				break;
			default:
				itemsType = ItemsType.NoItem;
				break;
			}
			item.setLive(false);
			return itemsType;
		}
		return ItemsType.NoItem;
	}
	/**
	 * 吃物品链表
	 * @param tanks
	 * @return
	 */
	public ItemsType eats(List<Item> items) {
		ItemsType itemsType = ItemsType.NoItem;
		for(int i = 0; i < items.size(); i++) {
			itemsType = eat(items.get(i));
			if(itemsType != ItemsType.NoItem) {
				items.remove(items.get(i));
				return itemsType;
			}
		}
		return ItemsType.NoItem;
	}
	/**
	 * 血条类
	 *
	 */
	private class BloodBar {
		//画血条
		public void draw(Graphics g) {
			BloodBarPicture(g);
		}
		//画血条图
		private void BloodBarPicture(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(X, Y-12, tankX, 5);
			int w = tankX * blood/bloodZong;
			g.fillRect(X, Y-12, w, 5);
			g.setColor(c);
		}
	}
}
