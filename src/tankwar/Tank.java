package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
/**
 * 坦克类
 *
 */
public class Tank implements InitValue{	
	//大客户指针
	private TankClient tankClient;						//大管家
	//坦克属性
	private boolean live = true;						//坦克是否活着
	private int TankType = type_player;					//坦克种类
	private int blood = 100;							//生命值
	private int bloodZong = 100;						//生命值总数
	private BloodBar bloodBar = new BloodBar();			//血条类
	private int X, Y, xspeed, yspeed, oldX, oldY;		//坦克位置 速度
	private boolean Up = false, Down = false,Left = false, Right = false;//坦克按键方向
	private Direction FangXiang = Direction.d5;			//坦克移动方向
	private Direction ptDir = Direction.d4;				//子弹 和 炮筒方向
	private int TrackingDistance = 1000;					//坦克追击半径
	//随机器
	public static Random random = new Random();			//随机器
	//各种子弹数量
	public int BaFangNumber = 0;						//八方炮数量
	public int ZhuiZongNumber = 0;						//追踪弹数量
	//贴图
	private int step;														//动画步骤
	private int stepfireX;													//动画步骤
	private int stepfireY;													//动画步骤
	private boolean AtkKey;													//按下射击键
	
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();			//工具包
	private static Image Player1Picture;									//人物图片1
	public static final int Player1X = 79;									//人物大小1
	public static final int Player1Y = 108;									//人物大小1
	private static Image Player2Picture;									//人物图片2
	public static final int Player2X = 79;									//人物大小2
	public static final int Player2Y = 108;									//人物大小2
	private static Image FirePicture;										//fire
	public static final int FireX = 120;									//fire
	public static final int FireY = 120;									//fire
	static{	
			Player1Picture = toolkit.getImage(Tank.class.getClassLoader().getResource("images/KG1.png"));	//人物1图片
			Player1Picture = Player1Picture.getScaledInstance(Player1X * 8, Player1Y * 8, Image.SCALE_DEFAULT);
		
			Player2Picture = toolkit.getImage(Tank.class.getClassLoader().getResource("images/KG2.png"));	//人物2图片
			Player2Picture = Player2Picture.getScaledInstance(Player2X * 8, Player2Y * 8, Image.SCALE_DEFAULT);
	
			FirePicture = toolkit.getImage(Tank.class.getClassLoader().getResource("images/使用技能.png"));	//发射图片
			FirePicture = FirePicture.getScaledInstance(FireX * 5, FireY * 4, Image.SCALE_DEFAULT);
	}

	/**
	 * 坦克构造函数
	 * @param x
	 * @param y
	 * @param good
	 * @param Co
	 * @param w
	 */
 	public Tank(int x, int y, int type, int xspeed, int yspeed, TankClient w){
		this.X = x;
		this.Y = y;
		this.TankType = type;
		this.xspeed = xspeed;
		this.yspeed = xspeed;
		this.oldX = x;
		this.oldY = y;
		this.tankClient = w;
		this.live = true;
		SetTypeBlood(this.TankType);
		PlayerMoveDongHua();
		PlayerFireDongHua();
		TankQD();
	}
 	/**
	 * 根据坦克种类设置血量
	 */
	public void SetTypeBlood(int Type){
		switch (Type) {
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
	}
	//******************************************************************坦克参数设置
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
	//******************************************************************画出人物
	/**
	 * 玩家动画 运动帧数
	 */
	private void PlayerMoveDongHua(){
		new Thread(new Runnable() {
			public void run() {
				while(live){
					if(Up || Down || Left || Right || TankType != type_player){
						if(step > 7)step = 0;
					}else{
						step = 0;
					}
					if(AtkKey){
						if(stepfireY > 3) stepfireY = 0;
						if(stepfireX > 4) stepfireX = 0;
					}else{
						stepfireX = 0;
						stepfireY = 0;
					};
					try {Thread.sleep(50);} catch (Exception e) {}
					step += 1;
					stepfireX += 1;
					if(stepfireX > 4){
						stepfireY += 1;
					}
				}			
			}
		}).start();
	}	
	/**
	 * 玩家动画 发射招式帧数
	 */
	private void PlayerFireDongHua(){
		new Thread(new Runnable() {
			public void run() {
				while(live){
					if(AtkKey){
						if(stepfireY > 3) stepfireY = 0;
						if(stepfireX > 4) stepfireX = 0;
					}else{
						stepfireX = 0;
						stepfireY = 0;
					};
					try {Thread.sleep(20);} catch (Exception e) {}
					stepfireX += 1;
					if(stepfireX > 4){
						stepfireY += 1;
					}
				}			
			}
		}).start();
	}	
	/**
	 * 画坦克
	 * @param g
	 */
	public void draw(Graphics g){	
		if(live) {
			PlayerPicture(g);	
		}	
	}
	/**
	 * 对象贴图贴图
	 * @param g
	 */
	private void PlayerPicture(Graphics g){
		switch (TankType) {
		case type_player:
			HuaPlayer(g,Player1Picture,Player1X,Player1Y);
			Huafire(g,FirePicture,FireX,FireY);
			bloodBar.draw(g);
			break;
		default:
			HuaPlayer(g,Player2Picture,Player2X,Player2Y);
			bloodBar.draw(g);
			break;
		}
	}
	/**
	 * 画玩家1
	 * @param g
	 */
	private void HuaPlayer(Graphics g , Image player , int playerX, int playerY){
		switch (ptDir) {
		case d4:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 1, (step+1) * Player1X, Player1Y * 2, null);
			break;
		case d7:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 6, (step+1) * Player1X, Player1Y * 7, null);
			break;
		case d8:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 3, (step+1) * Player1X, Player1Y * 4, null);
			break;
		case d9:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 7, (step+1) * Player1X, Player1Y * 8, null);
			break;
		case d6:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 2, (step+1) * Player1X, Player1Y * 3, null);
			break;
		case d3:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 5, (step+1) * Player1X, Player1Y * 6, null);
			break;
		case d2:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 0, (step+1) * Player1X, Player1Y * 1, null);
			break;
		case d1:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 4, (step+1) * Player1X, Player1Y * 5, null);
			break;
		default:
			g.drawImage(player, X, Y, X + playerX, Y + playerY, step * playerX, playerY * 0, (step+1) * Player1X, Player1Y * 1, null);
			break;
		}
	}
	/**
	 * 画发射
	 * @param g
	 */
	private void Huafire(Graphics g , Image fire , int fireX, int fireY){
		if(AtkKey){
			g.drawImage(fire  , X - 35, Y - 15, X + fireX, Y + fireY, stepfireX * fireX, stepfireY * fireY, (stepfireX+1) * fireX, (stepfireY+1) * fireY, null);
		}
	}
	/**
	 * 获取坦克的矩形
	 * @return
	 */
	public Rectangle getRect(){
		Rectangle rectangle = null;
		switch (TankType) {
		case type_player:
			rectangle = new Rectangle(X + 25, Y + 25, Player1X - 50, Player1Y - 30);
			break;
		default:
			rectangle = new Rectangle(X + 25, Y + 25, Player2X - 50, Player2Y - 30);
			break;
		}
		return rectangle;
	}
	//******************************************************************人物线程更新
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
			//子弹初始方向 炮筒初始方向  //如果没有动就不改变方向了
			if( FangXiang != Direction.d5 ) this.ptDir = this.FangXiang; 
			//坦克不能出界
			if(X < 0 || Y < 0 || X + Tank.Player1X > WindowsXlength || Y + Tank.Player1Y + 30 > WindowsYlength) {
				this.stay();
			}
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
	 * 自动开火线程
	 */
	public void autofire(){
		new Thread(new Runnable() {
			public void run() {
				int sleepInt = 0;
				int x = 0,y = 0;
				while(live){
					sleepInt = random.nextInt(2000) + 500;
					if(tankClient != null){
						x = Tank.this.X + Tank.Player1X/2 - Missile.missileXlength/2;	//从对象中心发射子弹
						y = Tank.this.Y + Tank.Player1Y/2 - Missile.missileYlength/2;	//从对象中心发射子弹
						if(ptDir == Direction.d5)ptDir = Direction.d6;				//炮弹不能不动
						//new 出一发子弹
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
			int x = this.X + Tank.Player1X/2 - Missile.missileXlength/2;	//从对象中心发射子弹
			int y = this.Y + Tank.Player1Y/2 - Missile.missileYlength/2;	//从对象中心发射子弹
			if(ptDir == Direction.d5)ptDir = Direction.d6;				//炮弹不能不动
			//new 出一发子弹
			Missile missile = new Missile(x, y, Misslie_putong, ptDir, type_player, 5, 5, tankClient);
			missile.ZhuiZongPD = false;
			tankClient.missiles.add(missile);	
		}
	}		
	/**
	 * 玩家开火 四面八方炮
	 * @return
	 */
	public void BaFangfire(){
		if(tankClient != null && Tank.this.isTankLive()){
			int x = this.X + Tank.Player1X/2 - Missile.missileXlength/2;	//从对象中心发射子弹
			int y = this.Y + Tank.Player1Y/2 - Missile.missileYlength/2;	//从对象中心发射子弹
			Direction[] directions = Direction.values();
			Direction direction = Direction.d1;
			for(int i = 0; i < 8; i++) {
				direction = directions[i];
				Missile missile = new Missile(x, y, Misslie_bafang, direction, type_player, 4, 4, tankClient);
				missile.ZhuiZongPD = false;
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
			int x = this.X + Tank.Player1X/2 - Missile.missileXlength/2;	//从对象中心发射子弹
			int y = this.Y + Tank.Player1Y/2 - Missile.missileYlength/2;	//从对象中心发射子弹
			if(ptDir == Direction.d5)ptDir = Direction.d6;				//炮弹不能不动
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
		case KeyEvent.VK_NUMPAD1:
			AtkKey = true;
			break;
		case KeyEvent.VK_NUMPAD2:
			AtkKey = true;
			break;
		case KeyEvent.VK_NUMPAD3:
			AtkKey = true;
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
			AtkKey = false;
			break;
		case KeyEvent.VK_NUMPAD2:		 
			if(BaFangNumber > 7) {
				BaFangNumber -= 8;
				BaFangfire();
			}
			AtkKey = false;
			break;
		case KeyEvent.VK_NUMPAD3:
			if(ZhuiZongNumber > 0) {
				ZhuiZongNumber -= 1;
				ZhuiZongfire();
			}
			AtkKey = false;
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
			g.setColor(Color.BLUE);
			g.drawRect(X, Y-12, Player1X, 5);
			int w = Player1X * blood/bloodZong;
			if(blood>50){
				g.setColor(Color.GREEN);
			}else{
				g.setColor(Color.RED);
			}
			g.fillRect(X, Y-11, w, 4);
			g.setColor(c);
		}
	}	
	//******************************************************************坦克与其他对象互动
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
				if(items.get(i) != null)items.remove(items.get(i));
				return itemsType;
			}
		}
		return ItemsType.NoItem;
	}
	
}
