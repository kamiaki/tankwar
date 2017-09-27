package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class Windows extends JFrame implements InitValue{	
	private int x = 50,xFangXiang = 1,xSpeed = 1,y = 50;	
	
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
		this.setTitle("Tank");
		this.setSize(WindowsXlength, WindowsYlength);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}		
		});
		
		mPanel = new mainPanel(); 
		mPanel.setSize(WindowsXlength, WindowsYlength);
		this.setContentPane(mPanel);
		mPanel.setLayout(null);
		
		new Thread(new PaintThread()).start();
		
		this.setVisible(true);
	}
	
	private class PaintThread implements Runnable{
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			while(true){
				mPanel.repaint();
				try {
					Thread.sleep(20);
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
			
			xSpeed = x / 10;
			if(xSpeed <= 0){
				xSpeed = 1;
			}
			if(x > 600){
				xFangXiang = -1;
			}else if(x < 50){
				xFangXiang = 1;
			}
			x = x + ( xFangXiang * xSpeed);	
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
	
	
}
