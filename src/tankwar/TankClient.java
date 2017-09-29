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
	
	private mainPanel mPanel;
	
	Tank myTank;
	List<Missile> missiles;
	List<Tank> enemyTanks; 
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
		myTank = new Tank(random(50, 750), random(50, 400), Tank.Tanktype_man, this);
		missiles = new ArrayList<Missile>();
		enemyTanks = new ArrayList<Tank>();
		CreateEnemyTank();
		new Thread(new Runnable() {		
			public void run() {	
				PDHitTank();
			}
		}).start();
	}
	
	/**
	 * 随机生成敌人坦克
	 */
	public void CreateEnemyTank(){
		for(int i = 0; i < 5; i++){
		enemyTanks.add( new Tank(random(50, 750), random(50, 400), Tank.Tanktype_robot , this) );		
		}
	}
	
	/**
	 * 判断是否击中坦克
	 */
	public void PDHitTank(){
		int tanknumber = 0;
		int missileN = 0;
		int tankN = 0;
		while(true){
			for(int i = 0; i < missiles.size(); i++){
				for(int j = 0; j < enemyTanks.size(); j++){
					if( missiles.get(i).hitTank(enemyTanks.get(j)) ){
							missiles.remove(i);
							enemyTanks.remove(j);
							enemyTanks.add( new Tank(random(50, 750), random(50, 400), Tank.Tanktype_robot , this) );	
							i = i - 1;
							j = j - 1;
							break;
					}
				}			
			}
			try {Thread.sleep(10);} catch (Exception e) {}
		}
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
		//面板
		mPanel = new mainPanel(); 
		mPanel.setSize(WindowsXlength, WindowsYlength);
		this.setContentPane(mPanel);
		mPanel.setLayout(null);	
		new Thread(new PaintThread()).start();
				
		this.setVisible(true);
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
					Thread.sleep(1);
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
			for(int j = 0; j < enemyTanks.size(); j++){								//敌人的坦克
				enemyTanks.get(j).draw(goffScreenImage);
			}								
			for(int i = 0; i < missiles.size(); i++){								//画炮弹
				missiles.get(i).draw(goffScreenImage);		
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
