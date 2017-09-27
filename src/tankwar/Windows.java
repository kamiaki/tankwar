package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class Windows extends JFrame implements InitValue{	
	private int x = 50,y = 50;	
	
	private mainPanel mPanel;
	
	/**
	 * 构造函数
	 */
	public Windows(){
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
		private Image Doublebuffer(){
			Image image = mainPanel.this.createImage(WindowsXlength, WindowsYlength);
			Graphics goffScreenImage = image.getGraphics();
			goffScreenImage.setColor(Color.GREEN);
			goffScreenImage.fillRect(0, 0, WindowsXlength, WindowsYlength);
			goffScreenImage.setColor(Color.RED);
			goffScreenImage.fillOval(x, y,30, 30);			
			return image;
		}
	}
	/**
	 * 键盘监听类
	 * @author Administrator
	 *
	 */
	private class Keylistener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			int Key = e.getKeyCode();
			switch (Key) {
			case KeyEvent.VK_W:
				y = y - 5;
				break;
			case KeyEvent.VK_S:
				y = y + 5;
				break;
			case KeyEvent.VK_A:
				x = x - 5;
				break;
			case KeyEvent.VK_D:
				x = x + 5;
				break;
			default:
				break;
			}
		}
				
		
	}
}
