package tankwar;

import java.awt.Color;
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

public class TankClient extends JFrame implements InitValue{	
	
	public static final int PanelX = -5,PanelY = -5;
	
	private mainPanel mPanel;
	private JPanel Mmpanel;
	Tank myTank;
	Tank enemyTank;
	List<Missile> missiles;
	
	/**
	 * 构造函数
	 */
	public TankClient(){
		initTank();
		launchFrame();
	}
	
	/**
	 * 随机范围数
	 * @param min
	 * @param max
	 * @return
	 */
	public int random(int min, int max){
		Random random = new Random();	
		int jieguo = random.nextInt(max)%(max-min+1) + min;	
		return jieguo;
	}
	
	/**
	 * 初始化坦克子弹等参数
	 */
	public void initTank(){	    
		myTank = new Tank(random(50, 750), random(50, 400), true, Color.RED, this);
		enemyTank = new Tank(100, 100, false, Color.GRAY, this);
		missiles = new ArrayList<Missile>();
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

		new Thread(new PaintThread()).start();
				
		this.setVisible(true);
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
			Image image = mainPanel.this.createImage(WindowsXlength, WindowsYlength);
			Graphics goffScreenImage = image.getGraphics();
			Color c = goffScreenImage.getColor();		
			goffScreenImage.setColor(Color.GREEN);									//画背景
			goffScreenImage.fillRect(0, 0, WindowsXlength, WindowsYlength);
			goffScreenImage.setColor(c);
			
			myTank.draw(goffScreenImage);											//画自己的 tank	
			if( enemyTank.isTankLive() ) enemyTank.draw(goffScreenImage);			//敌人的坦克												
			for(int i = 0; i < missiles.size(); i++){								//画炮弹
				Missile m = missiles.get(i);
				m.hitTank(enemyTank);
				m.draw(goffScreenImage);		
			}
			goffScreenImage.drawString("子弹数量:" + missiles.size(), 10, 20);
			goffScreenImage.drawString("坦克位置: X." + myTank.getX() + " Y." + myTank.getY(), 10, 40);
			
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
		}
		//抬起
		public void keyReleased(KeyEvent e) {
			super.keyReleased(e);
			myTank.noKEY(e);
		}
	}
}
