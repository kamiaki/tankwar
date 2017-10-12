package playerwar;

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
 * 角色类
 *
 */
public class Player implements InitValue{	
	//大客户指针
	private PlayerClient playerClient;										//大管家
	//角色属性
	private boolean live = true;											//角色是否活着
	private int playerType = type_player;									//角色身份
	private int blood = 100;												//生命值
	private int bloodZong = 100;											//生命值总数
	private int Mana = 100;													//生命值
	private int ManaZong = 100;												//生命值总数
	private Bar bar = new Bar();								//血条类
	private int X, Y, oldX, oldY, xspeed, yspeed;							//角色位置 速度
	private boolean Up = false, Down = false,Left = false, Right = false;	//玩家角色 按键方向
	private Direction MoveFangXiang = Direction.d5;							//角色 移动方向
	private Direction DrawFangXiang = Direction.d4;							//子弹 和 炮筒方向
	private int FollowDistance = 1000;										//角色追击距离
	private int XuLi = 0;													//角色蓄力
	private int XuLiZhi = 30;												//角色蓄力值
	//随机器
	public static Random random = new Random();								//随机器
	//贴图
	private int step;														//移动动画步数
	private int stepfireX;													//出招动画步数 图片x坐标
	private int stepfireY;													//出招动画步数 图片y坐标
	private boolean AtkKey;													//按下射击键 判断
	
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();			//工具包
	private static Image Player1Picture;									//人物图片1
	public static final int Player1X = 79;									//人物X大小1
	public static final int Player1Y = 108;									//人物Y大小1
	private static Image Player2Picture;									//人物图片2
	public static final int Player2X = 79;									//人物X大小2
	public static final int Player2Y = 108;									//人物Y大小2
	private static Image FirePicture;										//出招贴图
	public static final int FireX = 120;									//出招大小X
	public static final int FireY = 120;									//出招大小Y
	static{	
		//人物1图片
		Player1Picture = toolkit.getImage(Player.class.getClassLoader().getResource("images/KG1.png"));	
		Player1Picture = Player1Picture.getScaledInstance(Player1X * 8, Player1Y * 8, Image.SCALE_DEFAULT);
		//人物2图片
		Player2Picture = toolkit.getImage(Player.class.getClassLoader().getResource("images/KG2.png"));	
		Player2Picture = Player2Picture.getScaledInstance(Player2X * 8, Player2Y * 8, Image.SCALE_DEFAULT);
		FirePicture = toolkit.getImage(Player.class.getClassLoader().getResource("images/使用技能.png"));	
		//发射图片
		FirePicture = FirePicture.getScaledInstance(FireX * 5, FireY * 4, Image.SCALE_DEFAULT);
	}

	/**
	 * 角色构造函数
	 * @param x
	 * @param y
	 * @param good
	 * @param Co
	 * @param w
	 */
 	public Player(int x, int y, int type, int xspeed, int yspeed, PlayerClient w){
		this.X = x;
		this.Y = y;
		this.playerType = type;
		this.xspeed = xspeed;
		this.yspeed = xspeed;
		this.oldX = x;
		this.oldY = y;
		this.playerClient = w;
		this.live = true;
		SetTypeBar(this.playerType);	//设置血量 魔法值
		PlayerMoveDongHua();			//启动角色移动动画
		PlayerFireDongHua();			//启动角色出招动画
		PlayerQD();						//启动角色线程程序
	}
 	/**
	 * 根据角色种类设置血量
	 */
	public void SetTypeBar(int Type){
		switch (Type) {
		case type_player:
			blood = 200;
			bloodZong = blood;
			Mana = 200;													
			ManaZong = Mana;
			break;
		case type_enemy:
			blood = 60;
			bloodZong = blood;
			break;
		case type_boss:
			blood = 2000;
			bloodZong = blood;
			break;
		default:
			break;
		}
	}
	//******************************************************************角色参数设置
	/**
	 *  * 获取魔法值
	 * @return
	 */
	public int getMana() {
		return Mana;
	}
	/**
	 *  * 改变魔法值
	 * @return
	 */
	public void setMana(int mana) {
		Mana = mana;
	}
	/**
	 *  * 获取血量
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
	 * 判断是什么角色
	 * @return
	 */
	public int getPlayerType() {
		return playerType;
	}
	/**
	 * 设置是什么角色
	 * @param good
	 */
	public void setPlayerType(int type) {
		playerType = type;
	}
	/**
	 * 判断死活
	 * @return
	 */
	public boolean isPlayerLive() {
		return live;
	}
	/**
	 * 设置死活
	 * @param tankLive
	 */
	public void setPlayerLive(boolean tankLive) {
		this.live = tankLive;
	}	
	/**
	 * 角色x位置
	 * @return
	 */
	public int getY() {
		return Y;
	}
	/**
	 * 角色Y位置
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
					if(Up || Down || Left || Right || playerType != type_player){
						if(step > 7)step = 0;
					}else{
						step = 0;
					}
					try {Thread.sleep(50);} catch (Exception e) {}
					step += 1;
				}			
			}
		}).start();
	}	
	/**
	 * 玩家动画 发射招式帧数 蓄力
	 */
	private void PlayerFireDongHua(){
		new Thread(new Runnable() {
			public void run() {
				while(live){
					if(AtkKey){
						if(XuLi < XuLiZhi)XuLi++;
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
	 * 画角色
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
		switch (playerType) {
		case type_player:
			HuaPlayer(g,Player1Picture,Player1X,Player1Y);
			Huafire(g,FirePicture,FireX,FireY);
			bar.draw(g);
			break;
		default:
			HuaPlayer(g,Player2Picture,Player2X,Player2Y);
			bar.draw(g);
			break;
		}
	}
	/**
	 * 画玩家1
	 * @param g
	 */
	private void HuaPlayer(Graphics g , Image player , int playerX, int playerY){
		switch (DrawFangXiang) {
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
	 * 获取角色的矩形
	 * @return
	 */
	public Rectangle getRect(){
		Rectangle rectangle = null;
		switch (playerType) {
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
	 * 启动角色数据更新程序
	 */
	private void PlayerQD(){
		if(this.playerType == type_player){
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
					//如果玩家角色活着再追
					if( playerClient.myPlayer.isPlayerLive() ){
						tankx = playerClient.myPlayer.X;
						tanky = playerClient.myPlayer.Y;	
						if( Math.sqrt(Math.pow(Math.abs(Player.this.X - tankx), 2) + Math.pow(Math.abs(Player.this.Y - tanky), 2)) < FollowDistance ){
							if(Player.this.X < tankx && Player.this.Y == tanky){
								MoveFangXiang = Direction.d6;
							}else if(Player.this.X < tankx && Player.this.Y < tanky){
								MoveFangXiang = Direction.d3;
							}else if(Player.this.X == tankx && Player.this.Y < tanky){
								MoveFangXiang = Direction.d2;
							}else if(Player.this.X > tankx && Player.this.Y < tanky){
								MoveFangXiang = Direction.d1;
							}else if(Player.this.X > tankx && Player.this.Y == tanky){
								MoveFangXiang = Direction.d4;
							}else if(Player.this.X > tankx && Player.this.Y > tanky){
								MoveFangXiang = Direction.d7;
							}else if(Player.this.X == tankx && Player.this.Y > tanky){
								MoveFangXiang = Direction.d8;
							}else if(Player.this.X < tankx && Player.this.Y > tanky){
								MoveFangXiang = Direction.d9;
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
					tankx = playerClient.myPlayer.X;
					tanky = playerClient.myPlayer.Y;
					if(Math.sqrt(Math.pow(Math.abs(Player.this.X - tankx), 2) + Math.pow(Math.abs(Player.this.Y - tanky), 2))  >= FollowDistance){
						Direction[] dirs = Direction.values();
						MoveFangXiang = dirs[ random.nextInt(dirs.length) ];
					}
					try {Thread.sleep(time);} catch (Exception e) {}	
				}
			}
		}).start();
	}
	/**
	 * 角色移动
	 */
	private void move() {
		if( Player.this.ZhuangWalls(playerClient.walls) || Player.this.ZhuangTank(playerClient.myPlayer) || Player.this.ZhuangTanks(playerClient.enemyPlayers) ) {
			this.stay();
		}else {
			//上一次移动的坐标
			this.oldX = X;
			this.oldY = Y;		
			//开始移动
			switch (MoveFangXiang) {
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
			if( MoveFangXiang != Direction.d5 ) this.DrawFangXiang = this.MoveFangXiang; 
			//角色不能出界
			if(X < 0 || Y < 0 || X + Player.Player1X > WindowsXlength || Y + Player.Player1Y + 30 > WindowsYlength) {
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
					if(playerClient != null){
						x = Player.this.X + Player.Player1X/2 - Missile.missileXlength/2;	//从对象中心发射子弹
						y = Player.this.Y + Player.Player1Y/2 - Missile.missileYlength/2;	//从对象中心发射子弹
						if(DrawFangXiang == Direction.d5)DrawFangXiang = Direction.d6;				//炮弹不能不动
						//new 出一发子弹
						Missile missile = new Missile(x, y, Misslie_putong, DrawFangXiang, type_enemy, 2, 2, playerClient);
						playerClient.missiles.add(missile);	
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
		if(playerClient != null && Player.this.isPlayerLive()){
			int x = this.X + Player.Player1X/2 - Missile.missileXlength/2;				//从对象中心发射子弹
			int y = this.Y + Player.Player1Y/2 - Missile.missileYlength/2;				//从对象中心发射子弹
			if(DrawFangXiang == Direction.d5)DrawFangXiang = Direction.d6;				//炮弹不能不动
			//new 出一发子弹
			Missile missile = new Missile(x, y, Misslie_putong, DrawFangXiang, type_player, 5, 5, playerClient);
			missile.ZhuiZongPD = false;
			playerClient.missiles.add(missile);	
		}
	}	
	/**
	 * 玩家开火 生成一个超级普通子弹
	 * @return
	 */
	public void Superfire(){
		if(playerClient != null && Player.this.isPlayerLive()){
			int x = this.X + Player.Player1X/2 - Missile.missileXlength/2;				//从对象中心发射子弹
			int y = this.Y + Player.Player1Y/2 - Missile.missileYlength/2;				//从对象中心发射子弹
			if(DrawFangXiang == Direction.d5)DrawFangXiang = Direction.d6;				//炮弹不能不动
			//new 出一发子弹
			Missile missile = new Missile(x, y, Misslie_bafang, DrawFangXiang, type_player, 5, 5, playerClient);
			missile.ZhuiZongPD = false;
			playerClient.missiles.add(missile);	
		}
	}		
	/**
	 * 玩家开火 四面八方炮
	 * @return
	 */
	public void BaFangfire(){
		if(playerClient != null && Player.this.isPlayerLive()){
			new Thread(new Runnable() {
				public void run() {		
					for(int i = 0; i < 8; i++) {
						int x = Player.this.X + Player.Player1X/2 - Missile.missileXlength/2;	//从对象中心发射子弹
						int y = Player.this.Y + Player.Player1Y/2 - Missile.missileYlength/2;	//从对象中心发射子弹
						Direction[] directions = Direction.values();
						Direction direction = Direction.d1;
						direction = directions[i];
						Missile missile = new Missile(x, y, Misslie_bafang, direction, type_player, 4, 4, playerClient);
						missile.ZhuiZongPD = true;
						playerClient.missiles.add(missile);	
						try {Thread.sleep(50);} catch (Exception e) {}	
					}				
				}
			}).start();
					
		}
	}
	/**
	 * 玩家开火 生成一个追踪子弹
	 * @return
	 */
	public void ZhuiZongfire(){
		if(playerClient != null && Player.this.isPlayerLive()){
			int x = this.X + Player.Player1X/2 - Missile.missileXlength/2;	//从对象中心发射子弹
			int y = this.Y + Player.Player1Y/2 - Missile.missileYlength/2;	//从对象中心发射子弹
			if(DrawFangXiang == Direction.d5)DrawFangXiang = Direction.d6;				//炮弹不能不动
			Missile missile = new Missile(x, y, Misslie_zhuizong, DrawFangXiang, type_player, 3, 3, playerClient);
			missile.ZhuiZongPD = true;
			playerClient.missiles.add(missile);	
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
			if(XuLi >= XuLiZhi){
				Superfire();
			}else{
				fire();	
			}
			XuLi = 0;
			AtkKey = false;
			break;
		case KeyEvent.VK_NUMPAD2:
			if(XuLi >= XuLiZhi && Mana >= 20){
				BaFangfire();
				Mana -= 20;
			}		
			XuLi = 0;
			AtkKey = false;
			break;
		case KeyEvent.VK_NUMPAD3:
			if(XuLi >= XuLiZhi && Mana >= 10){
				ZhuiZongfire();
				Mana -= 10;
			}	
			XuLi = 0;
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
		if(!Up && !Down && Left && !Right)MoveFangXiang = Direction.d4;
		else if(Up && !Down && Left && !Right)MoveFangXiang = Direction.d7;
		else if(Up && !Down && !Left && !Right)MoveFangXiang = Direction.d8;
		else if(Up && !Down && !Left && Right)MoveFangXiang = Direction.d9;
		else if(!Up && !Down && !Left && Right)MoveFangXiang = Direction.d6;
		else if(!Up && Down && !Left && Right)MoveFangXiang = Direction.d3;
		else if(!Up && Down && !Left && !Right)MoveFangXiang = Direction.d2;
		else if(!Up && Down && Left && !Right)MoveFangXiang = Direction.d1;
		else if(!Up && !Down && Left && Right)MoveFangXiang = Direction.d5;
		else if(Up && Down && !Left && !Right)MoveFangXiang = Direction.d5;
		else if(!Up && !Down && !Left && !Right)MoveFangXiang = Direction.d5;
		else if(Up && Down && Left && Right)MoveFangXiang = Direction.d5;
	}
	/**
	 * 条类
	 *
	 */
	private class Bar {
		//画条
		public void draw(Graphics g) {
			BarPicture(g);
		}
		//画条图
		private void BarPicture(Graphics g) {
			int BarLength = 0;
			int BarHeight = 5;
			int offset = -120;
			int offsetXuLi = -10;
			//保存画笔颜色
			Color c = g.getColor();
			//血条
			g.setColor(Color.BLACK);
			g.drawRect(X, Y-(offset + BarHeight * 2), Player1X, BarHeight);
			BarLength = Player1X * blood / bloodZong;
			if( blood > bloodZong / 5 ){g.setColor(Color.GREEN);}
			else{g.setColor(Color.RED);}
			g.fillRect(X + 1, Y-(offset + BarHeight * 2) + 1, BarLength - 1, BarHeight - 1);
			//如果是玩家
			if(playerType == type_player){
				//魔法条
				g.setColor(Color.BLACK);
				g.drawRect(X, Y-(offset + BarHeight * 1), Player1X, BarHeight);
				BarLength = Player1X * Mana / ManaZong;
				if( Mana > ManaZong / 5 ){g.setColor(Color.BLUE);}
				else{g.setColor(Color.RED);}
				g.fillRect(X + 1, Y-(offset + BarHeight * 1) + 1, BarLength - 1, BarHeight - 1);
				//蓄力条
				BarLength = Player1X * XuLi / XuLiZhi;
				g.setColor(Color.YELLOW);
				g.fillRect(X + 1, Y-(offsetXuLi + BarHeight * 0) + 1, BarLength - 1, BarHeight - 1);
			}
			//还原画笔颜色
			g.setColor(c);
		}
	}	
	//******************************************************************角色与其他对象互动
	/**
	 * 角色撞墙
	 * @param w
	 * @return
	 */
	public boolean ZhuangWall(Wall w) {
		if(this.live && Player.this.getRect().intersects(w.getRect())) {
			return true;
		}
		return false;
	}
	/**
	 * 角色撞链表墙
	 * @param w
	 * @return
	 */
	public boolean ZhuangWalls(List<Wall> walls) {
		for(int i = 0; i < walls.size(); i++) {
			try {
				if(ZhuangWall(walls.get(i))){
					return true;
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}		
		}
		return false;
	}
	/**
	 * 角色与角色相撞
	 * @param t
	 * @return
	 */
	public boolean ZhuangTank(Player t) {
		if(this.live == true && t.live == true && this.getRect().intersects(t.getRect()) && this != t   ) {
			return true;
		}
		return false;
	}
	/**
	 * 角色与角色相撞 读取链表
	 * @param t
	 * @return
	 */
	public boolean ZhuangTanks(List<Player> Players) {
		if(Players != null){
			for(int i = 0; i < Players.size(); i++) {
				try {
					if( ZhuangTank(Players.get(i)) ) {
						return true;
					}	
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}			
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
			try {
				itemsType = eat(items.get(i));
				if(itemsType != ItemsType.NoItem) {
					if(items.get(i) != null)items.remove(items.get(i));
					return itemsType;
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}		
		}
		return ItemsType.NoItem;
	}
	
}
