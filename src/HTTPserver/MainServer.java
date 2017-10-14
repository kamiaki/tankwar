package HTTPserver;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class MainServer {
	//控件
	private JFrame frame;
	private JLabel Label_IpPort;
	private JLabel Label_FanKui;
	private JScrollPane scrollPane;
	private JTextArea textArea_XinXi;
	private JLabel label_ID;
	//菜单
	private JMenuBar menuBar;
	private JMenu Menu_CaoZuo;
	private JMenuItem MenuItem_Start;
	private JMenuItem MenuItem_Stop;
	private JMenuItem MenuItem_Exit;
	private JMenu Menu_Set;
	private JMenuItem MenuItem_Set;
	//其他参数
	private HTTPserver httPserver;
	private int PORT;
	private int LINKNUMBER;
	private ButtonListener btl;
	/**
	 * 主函数
	 * @param args //参数数组
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainServer window = new MainServer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * 构造函数
	 */
	public MainServer() {
		initialize();
	}
	/**
	 * 初始化
	 */
	private void initialize() {
		btl = new ButtonListener();
		//**************************************************************主窗口
		frame = new JFrame();
		frame.setTitle("接收文件服务端");
		frame.setSize(419, 262);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);									//居中
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		//**************************************************************控件
		Label_IpPort = new JLabel("IpPort");
		Label_IpPort.setHorizontalAlignment(SwingConstants.RIGHT);
		Label_IpPort.setBounds(10, 10, 389, 15);
		
		Label_FanKui = new JLabel("运行反馈");
		Label_FanKui.setBounds(10, 46, 54, 15);
		
		label_ID = new JLabel("服务器状态：关闭");
		label_ID.setHorizontalAlignment(SwingConstants.RIGHT);
		label_ID.setBounds(177, 46, 222, 15);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 71, 389, 132);	
		textArea_XinXi = new JTextArea();
		scrollPane.setViewportView(textArea_XinXi);
		textArea_XinXi.setText("反馈信息..");	
		
		menuBar = new JMenuBar();	
		Menu_CaoZuo = new JMenu("操作");	
		Menu_Set = new JMenu("设置");	
		
		MenuItem_Start = new JMenuItem("启动服务器");
		MenuItem_Start.addActionListener(btl);
		MenuItem_Start.setActionCommand("启动服务器");	
		
		MenuItem_Stop = new JMenuItem("关闭服务器");
		MenuItem_Stop.addActionListener(btl);
		MenuItem_Stop.setActionCommand("关闭服务器");	
		MenuItem_Stop.setEnabled(false);
		
		MenuItem_Exit = new JMenuItem("退出");
		MenuItem_Exit.addActionListener(btl);
		MenuItem_Exit.setActionCommand("退出");	
			
		MenuItem_Set = new JMenuItem("设置..");	
		MenuItem_Set.addActionListener(btl);
		MenuItem_Set.setActionCommand("设置");	

		//**************************************************************控件顺序
		//菜单栏
		frame.setJMenuBar(menuBar);
		menuBar.add(Menu_CaoZuo);
		Menu_CaoZuo.add(MenuItem_Start);
		Menu_CaoZuo.add(MenuItem_Stop);
		Menu_CaoZuo.add(MenuItem_Exit);
		menuBar.add(Menu_Set);
		Menu_Set.add(MenuItem_Set);	
		//主控件
		frame.getContentPane().add(Label_IpPort);
		frame.getContentPane().add(Label_FanKui);
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().add(label_ID);
		
		//**************************************************************更新控件
		updataServer();
	}
	//**************************************************************按钮响应事件
	public class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switch (arg0.getActionCommand()) {
			case "启动服务器":
				String portstr = JOptionPane.showInputDialog(null,"端口号：","参数输入",JOptionPane.PLAIN_MESSAGE);  
				String linkStr = JOptionPane.showInputDialog(null,"连接数：","参数输入",JOptionPane.PLAIN_MESSAGE);
				PORT = Integer.parseInt(portstr);
				LINKNUMBER = Integer.parseInt(linkStr);
				
				httPserver = new HTTPserver(PORT, LINKNUMBER);
				if(httPserver.HTTPserverStrat()){
					MenuItem_Start.setEnabled(false);	
					MenuItem_Stop.setEnabled(true);
					label_ID.setText("服务器状态：启动");
				}else{
					JOptionPane.showMessageDialog(null, "服务器端启动失败！");	
				}
				updataServer();
				break;
			case "关闭服务器":
				if(httPserver.HTTPserverOff()){
					label_ID.setText("服务器状态：关闭");
					MenuItem_Start.setEnabled(true);	
					MenuItem_Stop.setEnabled(false);
				}else{
					JOptionPane.showMessageDialog(null, "服务器端关闭失败！");	
				}		
				updataServer();
				break;
			case "设置":
				JOptionPane.showMessageDialog(null, "无设置内容..");	
				break;	
			case "退出":
				System.exit(0); //正常退出0，非正常退出1（用在try..catch）			
				break;		
			default:
				break;
			}
			
		}
	}
	/**
	 * 更新参数
	 */
	public void updataServer(){
		String ip = "";
		try {
			ip = ( (InetAddress)InetAddress.getLocalHost() ).getHostAddress().toString();  
		} catch (Exception e) {
			e.printStackTrace();
		}	
		String port = String.valueOf(PORT);
		String HeBing1 = String.format("IP地址 : %s   端口号 : %s", ip,port);
		Label_IpPort.setText(HeBing1);
	}
}
