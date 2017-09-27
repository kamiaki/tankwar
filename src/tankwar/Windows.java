package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class Windows extends JFrame{	
	private mainPanel mPanel;
	
	private int x = 50, y = 50;

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
		this.setSize(800, 450);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}		
		});
		
		mPanel = new mainPanel(); 
		this.setContentPane(mPanel);
		mPanel.setLayout(null);
		mPanel.setBackground(Color.GREEN);
		
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
		public void paint(Graphics g) {
			super.paint(g);
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.fillOval(x, y,30, 30);
			g.setColor(c);	
			x += 1;
		}		
	}
}
