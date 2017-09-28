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
import javax.swing.*;

public class TankClient extends JFrame implements InitValue{	
	private mainPanel mPanel;
	
	Tank myTank = new Tank(50, 50 ,this);
	List<Missile> missiles = new ArrayList<Missile>();
	
	/**
	 * 构造函数
	 */
	public TankClient(){
		launchFrame();
	}
	/**
	 * 初始化窗口
	 */
	public void launchFrame(){
		//窗口生成
		this.setTitle("Tank");
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
			
			myTank.draw(goffScreenImage);											//画tank	
			for(int i = 0; i < missiles.size(); i++){								//画炮弹
				Missile m = missiles.get(i);
				m.draw(goffScreenImage);		
			}
			goffScreenImage.drawString("子弹数量:" + missiles.size(), 10, 50);
			
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
