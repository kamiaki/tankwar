package playerwar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import HTTPclient.LoginDlg;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

public class MainWindows {
	//其他窗口引用
	LoginDlg loginDlg;
	PlayerClient playerClient;
	//主窗口
	public JFrame mFrame;					
	private JPanel MPanel;	
	private JLabel label_main;
	//其他参数
	private buttonlistener btr;
		
	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		MainWindows window = new MainWindows();
		window.mFrame.setVisible(true);
	}
	/**
	 * 构造函数
	 */
	public MainWindows() {
		initialize();
		LOGIN();
	}
	/**
	 * 初始化主窗体
	 */
	private void initialize() {
		btr = new buttonlistener();
		//主窗体
		mFrame = new JFrame();
		mFrame.setSize(194, 153);
		mFrame.setLocationRelativeTo(null);
		mFrame.setResizable(false);
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//主容器
		MPanel = new JPanel();
		MPanel.setLayout(null);
		mFrame.setContentPane(MPanel);
		//控件		
		label_main = new JLabel("加载中..");
		label_main.setBounds(38, 45, 112, 34);
		//控件插入容器
		MPanel.add(label_main);;
	}
	
	private void LOGIN(){
		new Thread(new Runnable() {
			public void run() {
				for(int i = 2; i >= 0; i--){
					label_main.setText("加载中.." + i);
					try { Thread.sleep(1000); } catch (InterruptedException e) {}
				}	
				playerClient = new PlayerClient(MainWindows.this);
				loginDlg = new LoginDlg(playerClient);	
				loginDlg.setVisible(true);
			}
		}).start();
		
	}
	
	
	
	//按钮响应类
	private class buttonlistener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "开始游戏":
				PlayerClient PlayerClient = new PlayerClient(MainWindows.this);
				mFrame.dispose();
				break;
			case "操作说明":
				String xx = "↑-w\r\n↓-S\r\n←-A\r\n→-D\r\n普通炮-Num1\r\nAOE炮-Num2\r\n追踪炮-Num3";
				JOptionPane.showMessageDialog(null, xx);
				break;
			default:
				break;
			}
			
		}
	}
}
