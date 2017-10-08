package tankwar;

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
public class TankClient extends JFrame implements InitValue{	
	//窗口面板
	private MainWindows mainWindows;					//主窗口指针
	private JPanel MainPanel;							//窗口主面板
	private mainPanel GamePanel;						//游戏面板
	public boolean StartGame = true;					//开始游戏
	//化游戏内容
	public Background background;						//背景图案
	public List<Item> Items;							//敌人坦克链表
	public boolean CreateItemPD = true;					//是否生成敌人坦克
	public Tank myTank;									//玩家坦克
	public static final int setLift = 3;				//游戏面板位置
	public List<Tank> enemyTanks;						//敌人坦克链表
	public boolean CreateEnemyTanksPD = true;			//是否生成敌人坦克
	public List<Missile> missiles;						//子弹链表
	public List<Explode> explodes;						//爆炸链表
	public List<Wall> walls;							//墙链表
	public int killTankNumber = 0;						//杀死坦克数
	public int reTankNumber = 0;						//玩家重生次数
	//随机方法
	public static Random random = new Random();			//随机方法
	
	/**
	 * 构造函数1
	 */
	public TankClient(){
		launchFrame();											//画主窗口
		initObject();											//初始化一些参数
		launchGamePanel();										//游戏面板加载
		new Thread(new PaintThread()).start();					//启动绘图线程
		this.setVisible(true);									//显示窗口
	}	
	/**
	 * 构造函数2
	 */
	public TankClient(MainWindows mainWindows){
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
				mainWindows.frame.setVisible(true);
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
		//生命数
		reTankNumber = setLift;
		//加载背景 障碍
		background = new Background(0, 0, "images/背景2.png", this);
		walls = new ArrayList<Wall>();
		walls.add(new Wall(200, 100, 100, 50, "images/墙.png", TankClient.this));
		walls.add(new Wall(400, 300, 50, 100, "images/墙.png", TankClient.this));
		//加载物品
		Items = new ArrayList<Item>();
		new Thread(new CreatItem()).start();
		//加载玩家坦克
		myTank = new Tank(random(100, 650), random(100, 300), type_player, 2, 2, this);
		while(myTank.ZhuangWalls(walls)) {
			myTank.setTankLive(false);
			myTank = new Tank(random(100, 650), random(100, 300), type_player, 2, 2, this);
		}
		//加载敌人坦克
		enemyTanks = new ArrayList<Tank>();
		new Thread(new CreatEnemyTank()).start();
		//加载子弹
		missiles = new ArrayList<Missile>();
		//加载爆炸
		explodes = new ArrayList<Explode>();
		//************************************启动数据刷新 线程
		new Thread(new ShuJuShuaXin()).start();			
	}
	//**********************************************************各种线程
	/**
	 * 创建坦克线程
	 * @author Administrator
	 *
	 */
	private class CreatEnemyTank implements Runnable{
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			while(CreateEnemyTanksPD){
				if(enemyTanks.size() < 5){
					Tank enemyTank = new Tank(random(100, 650), random(100, 300), type_enemy, 1, 1, TankClient.this);
					while(enemyTank.ZhuangWalls(walls) || enemyTank.ZhuangTanks(enemyTanks) || enemyTank.ZhuangTank(myTank)) {
						enemyTank.setTankLive(false);
						enemyTank = new Tank(random(100, 650), random(100, 300), type_enemy, 1, 1, TankClient.this);
					}
					enemyTanks.add(enemyTank);
				}
				try {Thread.sleep(2000);} catch (Exception e) {}	//刷新间隔
			}
		}
	}	
	/**
	 * 创建物品线程
	 * @author Administrator
	 *
	 */
	private class CreatItem implements Runnable{
		@Override
		public void run() {	
			Item item = null;
			ItemsType[] itemsTypes = ItemsType.values();
			while(CreateItemPD){
				ItemsType itemsType = itemsTypes[random(0, itemsTypes.length)];
				if(Items.size() >= 3) {
					if(Items.get(0) != null) {
						Items.remove(Items.get(0));
					}				
				}
				if(Items.size() < 3){
					switch (itemsType) {
					case Blood:
						//添加血
						item = new Item(random(100, 650), random(100, 300), 10, 10, ItemsType.Blood, TankClient.this);
						while(item.ZhuangWalls(walls)) {
							item.setLive(false);
							item = new Item(random(100, 650), random(100, 300), 10, 10, ItemsType.Blood, TankClient.this);
						}
						Items.add(item);
						break;
					case WeaponBaFang:
						//添加枪八方炮
						item = new Item(random(100, 650), random(100, 300), 10, 10, ItemsType.WeaponBaFang, TankClient.this);
						while(item.ZhuangWalls(walls)) {
							item.setLive(false);
							item = new Item(random(100, 650), random(100, 300), 10, 10, ItemsType.WeaponBaFang, TankClient.this);
						}
						Items.add(item);
						break;
					case WeaponZhuiZong:
						//添加枪追踪炮
						item = new Item(random(100, 650), random(100, 300), 10, 10, ItemsType.WeaponZhuiZong, TankClient.this);
						while(item.ZhuangWalls(walls)) {
							item.setLive(false);
							item = new Item(random(100, 650), random(100, 300), 10, 10, ItemsType.WeaponZhuiZong, TankClient.this);
						}
						Items.add(item);
						break;
					default:
						break;
					}
				}
				try {Thread.sleep(10000);} catch (Exception e) {}	//刷新间隔
			}
		}
	}
	/**
	 * 重绘线程
	 */
	private class PaintThread implements Runnable{
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
			ItemsType itemsType = ItemsType.NoItem;				//吃到了何种物品
			while(StartGame){
				//刷新子弹击中坦克事件											
				for(int i = 0; i < missiles.size(); i++){						//炮弹 触碰检测
					Missile missile = missiles.get(i);	
					if(missile != null) {
						missile.hitTanks(enemyTanks);
						missile.hitTank(myTank);
					}
				}
				//刷新玩家吃到物品事件
				itemsType = myTank.eats(Items);
				switch (itemsType) {
				case Blood:
					myTank.setBlood(100);
					break;
				case WeaponBaFang:
					myTank.BaFangNumber += 20;
					break;	
				case WeaponZhuiZong:
					myTank.ZhuiZongNumber += 5;
					break;
				default:
					break;
				}
				try {Thread.sleep(10);} catch (Exception e) {}	
			}
		}
	}
	//**********************************************************画游戏面板 按键操作
	/*
	 * 主窗口面板
	 */
	private class mainPanel extends JPanel{		
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
			//背景图案 和 墙
			background.draw(ImageG);	
			for(int i = 0; i < walls.size(); i++) {
				Wall wall = walls.get(i);
				wall.draw(ImageG);
			}
			//对象信息
			if( myTank.isTankLive() )myTank.draw(ImageG);					//画自己的 tank	
			for(int i = 0; i < enemyTanks.size(); i++){						//画敌人的坦克
				Tank tank = enemyTanks.get(i);
				tank.draw(ImageG);	
			}											
			for(int i = 0; i < missiles.size(); i++){						//画炮弹
				Missile missile = missiles.get(i);		
				missile.draw(ImageG);
			}
			for(int i = 0; i < explodes.size(); i++){						//画爆炸
				Explode e = explodes.get(i);
				e.draw(ImageG);
			}			
			for(int i = 0; i < Items.size(); i++){							//画爆炸
				Item item = Items.get(i);
				item.draw(ImageG);
			}
			//*************数据信息
			//******调试信息
//			ImageG.drawString("玩家坦克位置: X." + myTank.getX() + " Y." + myTank.getY(), 300, 20);
//			ImageG.drawString("子弹数量:" + missiles.size(), 300, 40);	
//			ImageG.drawString("爆炸数量:" + explodes.size(), 300, 60);
//			ImageG.drawString("坦克数量:" + enemyTanks.size(), 300, 80);
			//******玩家信息
			//击杀坦克数
			ImageG.drawString("击杀坦克数量:" + killTankNumber, 10, 20);
			//显示生命数
			if(reTankNumber < 0){
				ImageG.drawString("生命值数量:0", 10, 40);
			}else{
				ImageG.drawString("生命值数量:" + reTankNumber, 10, 40);
			}
			//坦克血量
			ImageG.drawString("坦克血量:" + myTank.getBlood(), 10, 60);
			//和子弹数
			ImageG.drawString("AOE弹数量:" + myTank.BaFangNumber, 710, 20);
			ImageG.drawString("追踪弹数量:" + myTank.ZhuiZongNumber, 710, 40);
			//******游戏结束 显示总共击杀数
			if(reTankNumber < 0){
				background.draw(ImageG);	
				ImageG.drawString("游戏结束，击杀坦克总数:" + killTankNumber, 320, 225);		
			}
			return image;
		}
	}
	/**
	 * 键盘监听类
	 * @author Administrator
	 *
	 */
	private class Keylistener extends KeyAdapter {
		//按下
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			myTank.KEY(e);
			switch (e.getKeyCode()) {
			case KeyEvent.VK_R:	
				//单轮游戏重生坦克
				if(!myTank.isTankLive() && reTankNumber > 0){
					reTankNumber--;
					myTank = new Tank(random(100, 650), random(100, 300), type_player, 2, 2, TankClient.this);
					while(myTank.ZhuangWalls(walls) || myTank.ZhuangTanks(enemyTanks) ) {
						myTank.setTankLive(false);
						myTank = new Tank(random(100, 650), random(100, 300), type_player, 2, 2, TankClient.this);
					}
				}
				//重新开始新的一局
				if(reTankNumber < 0){
					myTank = new Tank(random(100, 650), random(100, 300), type_player, 2, 2, TankClient.this);
					while(myTank.ZhuangWalls(walls) || myTank.ZhuangTanks(enemyTanks) ) {
						myTank.setTankLive(false);
						myTank = new Tank(random(100, 650), random(100, 300), type_player, 2, 2, TankClient.this);
					}
					reTankNumber = setLift;
					killTankNumber = 0;
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
			myTank.noKEY(e);
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
