package playerwar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

public class MainWindows {
	//大管家指针
	private PlayerClient PlayerClient;	
	//主窗口
	public JFrame mFrame;					
	private JPanel MPanel;		
	private buttonlistener btr;
	//控件
	private JButton button_StartGame;	
	private JButton button_CZ;			
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
		button_StartGame = new JButton("开始游戏");
		button_StartGame.setBounds(30, 22, 128, 31);
		button_StartGame.setActionCommand("开始游戏");
		button_StartGame.addActionListener(btr);

		button_CZ = new JButton("操作说明");
		button_CZ.setBounds(30, 63, 128, 31);
		button_CZ.setActionCommand("操作说明");
		button_CZ.addActionListener(btr);	
		//控件插入容器
		MPanel.add(button_StartGame);
		MPanel.add(button_CZ);
	}
	
	//按钮响应类
	private class buttonlistener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "开始游戏":
				PlayerClient = new PlayerClient(MainWindows.this);
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
