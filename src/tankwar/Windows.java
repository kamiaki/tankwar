package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class Windows extends JFrame{

	private class mainPanel extends JPanel{
		public void paint(Graphics g) {
			super.paint(g);
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.fillOval(50, 50,30, 30);
			g.setColor(c);
		}		
	}
	
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
		
		mainPanel mPanel = new mainPanel(); 
		this.setContentPane(mPanel);
		mPanel.setLayout(null);
		mPanel.setBackground(Color.GREEN);
		
		this.setVisible(true);
	}
}
