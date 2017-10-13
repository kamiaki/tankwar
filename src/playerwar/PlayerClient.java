package playerwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;
/**
 * 大管家类
 *
 */
public class PlayerClient extends JFrame implements InitValue{	
	//固定参数
	public static final int setLift = 3;				//初始玩家生命数量
	//窗口面板
	public MainWindows mainWindows;					//主窗口指针
	public JPanel MainPanel;							//窗口主面板
	public mainPanel GamePanel;						//游戏面板
	public boolean StartGame = true;					//开始游戏
	//画游戏内容
	public Background background;						//背景图案
	public List<Item> Items;							//物品链表
	public int ItemLength = 5;							//总物品数量
	public int ItemTime = 3000;							//物品刷新时间
	public boolean CreateItemPD = true;					//是否生成物品
	public Player myPlayer;								//玩家人物
	public List<Player> enemyPlayers;					//敌人链表
	public int EnemyLength = 5;							//总物品数量
	public int EnemyTime = 2000;						//物品刷新时间
	public boolean CreateEnemyPlayersPD = true;			//是否生成敌人
	public List<Missile> missiles;						//子弹链表
	public List<Explode> explodes;						//爆炸链表
	public List<Wall> walls;							//墙链表
	public List<Door> doors;							//门链表
	public int killPlayerNumber = 0;					//杀死敌人数
	public int rePlayerNumber = 0;						//玩家重生次数
	//随机器
	public static Random random = new Random();			//随机方法器
	//多线程
	public Thread SXSjThread = null;					//刷新数据线程			
	
	/**
	 * 构造函数
	 * @param mainWindows //传入窗口指针
	 */
	public PlayerClient(MainWindows mainWindows){
		this.mainWindows = mainWindows;							//主窗口指针赋值
		launchFrame();											//画主窗口
		initObject();											//初始化一些参数
		launchGamePanel();										//游戏面板加载
		new Thread(new PaintThread()).start();					//启动绘图线程
		this.setVisible(true);									//显示窗口
	}	
	//**********************************************************构造窗口 初始化
	/**
	 * 初始化窗口
	 */
	public void launchFrame(){
		//窗口生成
		this.setTitle("KamiAki's First JavaGame");
		this.setSize(WindowsXlength, WindowsYlength);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				dispose();
				mainWindows.mFrame.setVisible(true);
			}
		});	
		//键盘监听
		this.addKeyListener(new Keylistener());	
		//窗口面板（游戏面板 在其中）
		MainPanel = new JPanel();
		MainPanel.setLayout(null);
		this.setContentPane(MainPanel);		
	}
	/**
	 * 游戏面板加载
	 */
	public void launchGamePanel() {
		GamePanel = new mainPanel(); 
		GamePanel.setLayout(null);
		GamePanel.setLocation(PanelX, PanelY);
		GamePanel.setSize(WindowsXlength + PanelX * (-2), WindowsYlength + PanelY * (-2));			
		MainPanel.add(GamePanel);	
	}
	/**
	 * 初始化坦克子弹等参数
	 */
	public void initObject(){
		rePlayerNumber = setLift;			//生命数
		//加载背景
		if(background == null)background = new Background(0, 0, 2,PlayerClient.this);
		//加载玩家坦克
		myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, this);
		//加载子弹
		if(missiles == null)missiles = new ArrayList<Missile>();
		//加载爆炸
		if(explodes == null)explodes = new ArrayList<Explode>();
		//加门
		if(doors == null)doors = new ArrayList<Door>();
		doors.add(new Door(0, 450, Door_woods, PlayerClient.this));
		//************************************启动数据刷新 线程
		SXSjThread = new Thread(new ShuJuShuaXin());
		SXSjThread.start();
	}
	//**********************************************************各种线程
	/**
	 * 创建坦克线程
	 * @author Administrator
	 *
	 */
	public class CreateEnemyPlayer implements Runnable{
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			while(CreateEnemyPlayersPD){
				if(CreateEnemyPlayersPD && enemyPlayers.size() < EnemyLength){
					Player enemyPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_enemy, 1, 1, PlayerClient.this);
					while(enemyPlayer.ZhuangWalls(walls) || enemyPlayer.ZhuangTanks(enemyPlayers) || enemyPlayer.ZhuangTank(myPlayer)) {
						enemyPlayer.setPlayerLive(false);
						enemyPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_enemy, 1, 1, PlayerClient.this);
					}
					enemyPlayers.add(enemyPlayer);
				}
				try {Thread.sleep(EnemyTime);} catch (Exception e) {}	//刷新间隔
			}
		}
	}	
	/**
	 * 创建物品线程
	 * @author Administrator
	 *
	 */
	public class CreateItem implements Runnable{
		@Override
		public void run() {	
			Item item = null;
			ItemsType[] itemsTypes = ItemsType.values();
			while(CreateItemPD){
				ItemsType itemsType = itemsTypes[random(0, itemsTypes.length)];
				if(Items.size() >= ItemLength && CreateItemPD == true) {
					//抓越界异常
					try{
						if(Items.get(0) != null) {
							Items.remove(Items.get(0));
						}
					} catch (IndexOutOfBoundsException e) {
						e.printStackTrace();
					}		
				}
				if(Items.size() < ItemLength && CreateItemPD == true){
					switch (itemsType) {
					case Blood:
						//添加血
						item = new Item(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), 10, 10, ItemsType.Blood, PlayerClient.this);
						while(item.ZhuangWalls(walls)) {
							item.setLive(false);
							item = new Item(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), 10, 10, ItemsType.Blood, PlayerClient.this);
						}
						Items.add(item);
						break;
					case WeaponBaFang:
						//添加枪八方炮
						item = new Item(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), 10, 10, ItemsType.WeaponBaFang, PlayerClient.this);
						while(item.ZhuangWalls(walls)) {
							item.setLive(false);
							item = new Item(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), 10, 10, ItemsType.WeaponBaFang, PlayerClient.this);
						}
						Items.add(item);
						break;
					case WeaponZhuiZong:
						//添加枪追踪炮
						item = new Item(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), 10, 10, ItemsType.WeaponZhuiZong, PlayerClient.this);
						while(item.ZhuangWalls(walls)) {
							item.setLive(false);
							item = new Item(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), 10, 10, ItemsType.WeaponZhuiZong, PlayerClient.this);
						}
						Items.add(item);
						break;
					default:
						break;
					}
				}
				try {Thread.sleep(ItemTime);} catch (Exception e) {}	//刷新间隔
			}
		}
	}
	/**
	 * 重绘线程
	 */
	public class PaintThread implements Runnable{
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			while(StartGame){
				GamePanel.repaint();
				try {Thread.sleep(10);} catch (Exception e) {}	
			}
		}
	}
	/**
	 * 数据刷新
	 */
	public class ShuJuShuaXin implements Runnable{
		@Override
		public void run() {
			ItemsType itemsType = ItemsType.NoItem;								//吃到了何种物品
			while(StartGame){
				//刷新子弹击中坦克事件		
				Missile missile = null;
				if(missiles != null){
					for(int i = 0; i < missiles.size(); i++){					//炮弹 触碰检测	
						//抓越界异常
						try {
							missile = missiles.get(i);	
							if(missile != null) {
								if(enemyPlayers != null)missile.hitTanks(enemyPlayers);
								if(myPlayer != null)missile.hitTank(myPlayer);
							}
						} catch (IndexOutOfBoundsException e) {
							e.printStackTrace();
						}			
					}	
				}
				//刷新玩家吃到物品事件
				itemsType = myPlayer.eats(Items);
				switch (itemsType) {
				case Blood:
					myPlayer.setBlood(myPlayer.getBlood() + 40);
					if(myPlayer.getBlood() >200){
						myPlayer.setBlood(200);
					}
					break;
				case WeaponBaFang:
					myPlayer.setMana(myPlayer.getMana() + 20);
					if(myPlayer.getMana() >200){
						myPlayer.setMana(200);
					}
					break;	
				case WeaponZhuiZong:
					myPlayer.setMana(myPlayer.getMana() + 40);
					if(myPlayer.getMana() >200){
						myPlayer.setMana(200);
					}
					break;
				default:
					break;
				}
				//循环刷新间隔
				try {Thread.sleep(10);} catch (Exception e) {}	
			}
		}
	}
	//**********************************************************画游戏面板 按键操作
	/*
	 * 主窗口面板
	 */
	public class mainPanel extends JPanel{		
		@Override
		public void paint(Graphics g) {
			super.paint(g);			
			Image offScreenImage = Doublebuffer();			//先将内容绘制在图片上
			g.drawImage(offScreenImage, 0, 0 , null);		//双缓冲
		}		
		/**
		 * 双缓存 方法
		 * @return
		 */
		private Image Doublebuffer(){

			Image image = mainPanel.this.createImage(WindowsXlength + PanelX * (-2),  WindowsYlength + PanelY * (-2));
			Graphics ImageG = image.getGraphics();
			//抓越界异常
			try {
			//背景图案 和 墙 和 门
				if(background != null){
					background.draw(ImageG);	
				}
				if(walls != null){
					for(int i = 0; i < walls.size(); i++) {
						Wall wall = walls.get(i);
						if(wall != null)wall.draw(ImageG);
					}
				}
				if(doors != null){
					for(int i = 0; i < doors.size(); i++) {
						Door door = doors.get(i);
						if(door != null)door.draw(ImageG);
					}
				}
			//对象信息
				if(enemyPlayers != null){
					for(int i = 0; i < enemyPlayers.size(); i++){					//画敌人的坦克
						Player tank = enemyPlayers.get(i);
						if(tank != null)tank.draw(ImageG);	
					}
				}	
				if(myPlayer != null){
					if( myPlayer.isPlayerLive() )myPlayer.draw(ImageG);				//画自己的 tank		
				}
				if(missiles != null){
					for(int i = 0; i < missiles.size(); i++){					//画炮弹
						Missile missile = missiles.get(i);		
						if(missile != null)missile.draw(ImageG);
					}
				}
				if(explodes != null){
					for(int i = 0; i < explodes.size(); i++){						//画爆炸
						Explode e = explodes.get(i);
						try {
							if(e != null)e.draw(ImageG);
						} catch (NullPointerException e2) {
							e2.printStackTrace();
						}
					}
				}
				if(Items != null){
					for(int i = 0; i < Items.size(); i++){							//画爆炸
						Item item = Items.get(i);
						if(item != null)item.draw(ImageG);
					}
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}		
			//*************数据信息
			//击杀坦克数
			ImageG.drawString("击杀坦克数量:" + killPlayerNumber, 10, 20);
			//显示生命数
			if(rePlayerNumber < 0){
				ImageG.drawString("生命值数量:0", 10, 40);
			}else{
				ImageG.drawString("生命值数量:" + rePlayerNumber, 10, 40);
			}
			//坦克血量
			ImageG.drawString("坦克血量:" + myPlayer.getBlood(), 10, 60);
			//******游戏结束 显示总共击杀数
			if(rePlayerNumber < 0){
				background.draw(ImageG);	
				ImageG.drawString("游戏结束，击杀坦克总数:" + killPlayerNumber, 10, 120);		
			}
			return image;
		}
	}
	/**
	 * 键盘监听类
	 * @author Administrator
	 *
	 */
	public class Keylistener extends KeyAdapter {
		//按下
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			myPlayer.KEY(e);
			switch (e.getKeyCode()) {
			case KeyEvent.VK_R:	
				//单轮游戏重生坦克
				if(!myPlayer.isPlayerLive() && rePlayerNumber > 0){
					rePlayerNumber--;
					myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, PlayerClient.this);
					while(myPlayer.ZhuangWalls(walls) || myPlayer.ZhuangTanks(enemyPlayers) ) {
						myPlayer.setPlayerLive(false);
						myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, PlayerClient.this);
					}
				}
				//重新开始新的一局
				if(rePlayerNumber < 0){
					myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, PlayerClient.this);
					while(myPlayer.ZhuangWalls(walls) || myPlayer.ZhuangTanks(enemyPlayers) ) {
						myPlayer.setPlayerLive(false);
						myPlayer = new Player(random(WindowsSide, WindowsXlength - WindowsSide), random(WindowsSide, WindowsYlength - WindowsSide), type_player, 3, 3, PlayerClient.this);
					}
					rePlayerNumber = setLift;
					killPlayerNumber = 0;
				}
				break;
			case KeyEvent.VK_Q:				
				break;
			case KeyEvent.VK_E:	
				break;
			default:
				break;
			}
		}
		//抬起
		public void keyReleased(KeyEvent e) {
			super.keyReleased(e);
			myPlayer.noKEY(e);
		}
	}
	//**********************************************************一些其他方法
	/**
	 * 随机范围数 只能是正数
	 * @param min
	 * @param max
	 * @return
	 */
	public int random(int min, int max){
		int jieguo = random.nextInt(max)%(max-min+1) + min;	
		return jieguo;
	}
	/**
	 * 震动
	 */
	public void ZhenDong(){
		new Thread(new Runnable() {
			public void run() {
				int windowsXz = 0;								//震动量X
				int windowsYz = 0;								//震动量Y
				int ZFX = 1;									//x的震动正负+1-1
				int ZFPDX =0;									//计算是正是负PD
				int ZFY = 1;									//y的震动正负+1-1
				int ZFPDY =0;									//计算是正是负PD
				
				for(int i = 0; i < 10; i++){					
					ZFPDX = random(1, 10);						//计算是正是负
					ZFPDY = random(1, 10);						//计算是正是负
					windowsXz = random(1, PanelX * (-1));		//震动量
					windowsYz = random(1, PanelY * (-1));		//震动量
					if(ZFPDX <= 5){ZFX = -1;}else{ZFX = 1;}		//震动 +1 or -1
					if(ZFPDY <= 5){ZFY = -1;}else{ZFY = 1;}		//震动 +1 or -1				
					GamePanel.setLocation(PanelX + ZFX * windowsXz, PanelY + ZFY * windowsYz);
					try {Thread.sleep(20);} catch (Exception e) {}	
				}
				GamePanel.setLocation(PanelX,PanelY);	
			}
		}).start();	
	}	
}
