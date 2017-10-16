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
		label_main = new JLabel("加载中...");
		label_main.setBounds(38, 45, 112, 34);
		//控件插入容器
		MPanel.add(label_main);;
	}
	
	private void LOGIN(){
		new Thread(new Runnable() {
			public void run() {
				String xx = "↑-w\r\n↓-S\r\n←-A\r\n→-D\r\n普通炮-Num1\r\nAOE炮-Num2\r\n追踪炮-Num3";
				JOptionPane.showMessageDialog(null, xx);
				
				playerClient = new PlayerClient();						//加载游戏面板
				
				loginDlg = new LoginDlg(playerClient);					//加载登录界面
				playerClient.GetloginDlg(loginDlg);						//获取登录指针
				loginDlg.setVisible(true);                              //显示登录界面
				
				mFrame.dispose();										//关闭这个窗口
			}
		}).start();		
	}
}
