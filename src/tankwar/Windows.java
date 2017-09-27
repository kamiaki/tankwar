package tankwar;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class Windows extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2277845644615413307L;

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
		this.setTitle("tank");
		this.setSize(800, 450);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
			
		});
		
		
		this.setVisible(true);
	}
}
