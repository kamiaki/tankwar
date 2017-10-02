package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

public class TankClient extends JFrame implements InitValue{	
	
	public static final int PanelX = -5,PanelY = -5;
	public static Random random = new Random();	
	private mainPanel mPanel;
	private JPanel Mmpanel;
	public Background background;
	public Tank myTank;
	public List<Tank> enemyTanks;
	public boolean enemytanksPD = true;
	public List<Missile> missiles;
	public List<Explode> explodes;
	public int killTankNumber = 0;
	public int reTankNumber = 0;
	/**
	 * 构造函数
	 */
	public TankClient(){
		launchFrame();
		//启动绘图线程
		new Thread(new PaintThread()).start();	
		initObject();
		this.setVisible(true);
	}	
	/**
	 * 随机范围数
	 * @param min
	 * @param max
	 * @return
	 */
	public int random(int min, int max){
		int jieguo = random.nextInt(max)%(max-min+1) + min;	
		return jieguo;
	}	
	/**
	 * 创建坦克线程
	 * @author Administrator
	 *
	 */
	private class CreatTank implements Runnable{
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			while(enemytanksPD){
				if(enemyTanks.size() < 5){
					Tank enemyTank = new Tank(random(50, 750), random(50, 400), type_enemy, Color.GRAY, TankClient.this);
					enemyTanks.add(enemyTank);
				}
				try {Thread.sleep(1000);} catch (Exception e) {}	//刷新间隔
			}
		}
	}
	/**
	 * 初始化坦克子弹等参数
	 */
	public void initObject(){	
		reTankNumber = 3;
		enemyTanks = new ArrayList<Tank>();
		missiles = new ArrayList<Missile>();
		explodes = new ArrayList<Explode>();
		background = new Background(0, 0, WindowsXlength + PanelX * (-2),  WindowsYlength + PanelY * (-2), this);
		myTank = new Tank(random(50, 750), random(50, 400), type_player, Color.RED, this);
		new Thread(new CreatTank()).start();	
	}
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
				System.exit(0);
			}		
		});		
		//键盘监听
		this.addKeyListener(new Keylistener());	
		//游戏面板
		mPanel = new mainPanel(); 
		mPanel.setLocation(PanelX, PanelY);
		mPanel.setSize(WindowsXlength + PanelX * (-2), WindowsYlength + PanelY * (-2));
		mPanel.setLayout(null);			
		//窗口面板（游戏面板 在其中）
		Mmpanel = new JPanel();
		Mmpanel.setLayout(null);
		Mmpanel.add(mPanel);		
		//窗口面板添加进主窗口
		this.setContentPane(Mmpanel);			
	}	
	/**
	 * 震动
	 */
	public void ZhenDong(){
		new Thread(new Runnable() {
			public void run() {
				int windowsXz = 0;
				int windowsYz = 0;
				int ZFX = 1;
				int ZFPDX =0;
				int ZFY = 1;
				int ZFPDY =0;
				
				for(int i = 0; i < 10; i++){
					
					ZFPDX = random(1, 10);
					ZFPDY = random(1, 10);
					windowsXz = random(1, PanelX * (-1));		//震动量
					windowsYz = random(1, PanelY * (-1));		//震动量
					if(ZFPDX <= 5){ZFX = -1;}else{ZFX = 1;}		//震动 + -
					if(ZFPDY <= 5){ZFY = -1;}else{ZFY = 1;}		//震动 + -
					
					mPanel.setLocation(PanelX + ZFX * windowsXz, PanelY + ZFY * windowsYz);
					try {Thread.sleep(20);} catch (Exception e) {}	
				}
				mPanel.setLocation(PanelX,PanelY);	
			}
		}).start();
		
	}	
	/**
	 * 重绘线程
	 * @author Administrator
	 *
	 */
	private class PaintThread implements Runnable{
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			while(true){
				mPanel.repaint();
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		}
	}
	/*
	 * 主窗口面板
	 */
	private class mainPanel extends JPanel{
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);			
			Image offScreenImage = Doublebuffer();
			g.drawImage(offScreenImage, 0, 0 , null);		
		}
		
		/**
		 * 双缓存 方法
		 * @return
		 */
		private Image Doublebuffer(){
			Image image = mainPanel.this.createImage(WindowsXlength + PanelX * (-2),  WindowsYlength + PanelY * (-2));
			Graphics goffScreenImage = image.getGraphics();
			
			//背景图案
			background.draw(goffScreenImage);
			
			//对象信息
			if( myTank.isTankLive() )myTank.draw(goffScreenImage);					//画自己的 tank	
			for(int i = 0; i < enemyTanks.size(); i++){								//画敌人的坦克
				Tank tank = enemyTanks.get(i);
				tank.draw(goffScreenImage);		
			}											
			for(int i = 0; i < missiles.size(); i++){								//画炮弹
				Missile m = missiles.get(i);		
				m.draw(goffScreenImage);
				m.hitTanks(enemyTanks);
				m.hitTank(myTank);
			}
			for(int i = 0; i < explodes.size(); i++){
				Explode e = explodes.get(i);
				e.draw(goffScreenImage);
			}
			
			//数据信息
			goffScreenImage.drawString("玩家坦克位置: X." + myTank.getX() + " Y." + myTank.getY(), 10, 20);
			goffScreenImage.drawString("子弹数量:" + missiles.size(), 10, 40);	
			goffScreenImage.drawString("爆炸数量:" + explodes.size(), 10, 60);
			goffScreenImage.drawString("坦克数量:" + enemyTanks.size(), 10, 80);
			goffScreenImage.drawString("击杀坦克数量:" + killTankNumber, 200, 20);
			goffScreenImage.drawString("生命值数量:" + reTankNumber, 200, 40);
			if(reTankNumber < 0){
				goffScreenImage.drawString("游戏结束，击杀坦克总数:" + killTankNumber, 320, 225);
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
				if(!myTank.isTankLive() && reTankNumber >= 0){
					reTankNumber -= 1;
					myTank = new Tank(random(50, 750), random(50, 400), type_player, Color.RED, TankClient.this);
				}
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
}
