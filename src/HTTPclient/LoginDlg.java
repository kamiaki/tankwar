package HTTPclient;


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

import org.apache.commons.httpclient.HttpClient;

import playerwar.Door;
import playerwar.Explode;
import playerwar.Item;
import playerwar.Missile;
import playerwar.Player;
import playerwar.Wall;
import playerwar.PlayerClient.mainPanel;

import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class LoginDlg extends JFrame {
	//***************************************主窗口
	private static int WindowsWidth = 800, WindowsHeigth = 450, side = 50;
	
	private Container container;			//主窗口容器		
	//***************************************控件
	private Login jPanelLogin;				//登录面板
	private JLabel jLabel_beijing;			//登录背景
	//***************************************其他参数
	private buttonlistener btl;				//按钮响应事件
	private playerwar.PlayerClient CD;		//大管家指针
	private String initname = "";			//初始化用户名
	private String initpassword = "";		//初始化密码
	private String initloginPD = "";		//初始化判断是否自动登录
	private String userName = "";			//用户名
	private String password = "";			//密码
	//退出图片
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image imageExit = tk.getImage(LoginDlg.class.getClassLoader().getResource("images/询问退出.gif"));
	private static Image imageBeijing = tk.getImage(LoginDlg.class.getClassLoader().getResource("images/登录背景.jpeg"));
	static{
		imageExit = imageExit.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		imageBeijing = imageBeijing.getScaledInstance(WindowsWidth + side , WindowsHeigth + side , Image.SCALE_DEFAULT);
	}
	private static Icon iconExit = new ImageIcon(imageExit);
	private static Icon iconBeijing = new ImageIcon(imageBeijing);
	
	/**
	 * 创建登录主窗口
	 */
	public LoginDlg(playerwar.PlayerClient cd) {
		btl = new buttonlistener();
		this.CD = cd;
		//********************************************主窗口
		setTitle("欢迎来到东高地！");
		setSize(WindowsWidth, WindowsHeigth);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {		
				int i = JOptionPane.showConfirmDialog(null, "是否要退出？","退出",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,iconExit);
				if(i == JOptionPane.OK_OPTION){
					System.exit(0);
				}
			}
		});		
		//********************************************容器
		container = getContentPane();	
		container.setLayout(null);
		//********************************************控件
		jPanelLogin = new Login();																	//登录面板
		jLabel_beijing = new JLabel(iconBeijing);													//画背景
		jLabel_beijing.setBounds(-side/2, -side/2, WindowsWidth + side, WindowsHeigth + side);		//设置背景位置大小
		//*********************************************控件顺序
		container.add(jPanelLogin);
		container.add(jLabel_beijing);
		//********************************************初始化参数
		 init();
	}
	
	/**
	 * 控件响应事件
	 * @author KamiAki
	 *
	 */
	private class buttonlistener implements ActionListener{	
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "修改密码":
				LoginDlg.this.setEnabled(false);
				UpDataDlg updatadlg = new UpDataDlg(LoginDlg.this);
				updatadlg.setVisible(true);
				break;
			case "登录":				
				userName = jPanelLogin.textField_useName.getText();
				password = jPanelLogin.textField_password.getText();			
					if(!userName.equals("")){
						if(!password.equals("")){				
							//**************************************************************开始登录验证
							HTTPclient httPclient = new HTTPclient("192.168.199.148","1234");
							String returnSTR = httPclient.HTTPclientSendLogin(userName, password);	
							if(returnSTR.equals("登录成功")){
								JOptionPane.showMessageDialog(null, "登录成功！","提示",JOptionPane.INFORMATION_MESSAGE);					
								LOGIN();
							}else if(returnSTR.equals("服务端异常")){
								JOptionPane.showMessageDialog(null, "登录服务端异常！","提示",JOptionPane.INFORMATION_MESSAGE);	
							}else{
								JOptionPane.showMessageDialog(null, "登录失败，用户名 或 密码错误！","提示",JOptionPane.INFORMATION_MESSAGE);					
							}	
						}else{
							JOptionPane.showMessageDialog(null, "密码未填写！","提示",JOptionPane.ERROR_MESSAGE);
						}			
					}else{
						JOptionPane.showMessageDialog(null, "用户名未填写！","提示",JOptionPane.ERROR_MESSAGE);
					}				
				break;
			case "自动登录":

				if(jPanelLogin.CheckBox_password.isSelected() == false && jPanelLogin.CheckBox_login.isSelected() == true){
					jPanelLogin.CheckBox_password.setSelected(true);
				}else if(jPanelLogin.CheckBox_password.isSelected() == true && jPanelLogin.CheckBox_login.isSelected() == false){
					jPanelLogin.CheckBox_password.setSelected(false);
				}
				break;
			case "记住密码":
				if(jPanelLogin.CheckBox_password.isSelected() == false && jPanelLogin.CheckBox_login.isSelected() == true){
					jPanelLogin.CheckBox_login.setSelected(false);
				}
				break;
			case "注册":
				LoginDlg.this.setEnabled(false);
				JoinDlg frame = new JoinDlg(LoginDlg.this);
				frame.setVisible(true);
				break;
			default:
				break;
			}
		}
	}
	/**
	 * 登录面板
	 * @author Administrator
	 *
	 */
	private class Login extends JPanel{
		//成员变量
		public GridBagLayout gridBagLayout;					//网格包布局
		public JTextField textField_useName;				//用户名
		public JPasswordField textField_password;			//密码	
		public JLabel Label_name;							//用户名图标
		public JLabel Label_pass;							//密码图标		
		public JButton Button_Login;						//登录按钮
		public JCheckBox CheckBox_password;					//保存密码
		public JCheckBox CheckBox_login;					//自动登录
		public JButton Button_join;							//注册按钮
		public JButton Button_updata;						//修改密码按钮
		/**
		 * 构造函数
		 */
		public Login(){
			gridBagLayout = new GridBagLayout();									//创件gridbag布局
			gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};		//行是否扩展
			gridBagLayout.rowHeights = new int[]{30, 30, 30, 30, 0};				//行权重,行之间的比例
			gridBagLayout.columnWeights = new double[]{0.0,0.0, 0.0};				//列是否扩展
			gridBagLayout.columnWidths = new int[]{100,100, 100};					//列权重,列之间的比例
			this.setLayout(gridBagLayout);											//面板设置布局为gridbag
			this.setBounds(40, 180, 350, 190);										//设置面板位置，大小
			this.setOpaque(false); 													//设置透明度
			
			//********************************************************用户名
			textField_useName = new JTextField();
			textField_useName.setForeground(Color.GRAY);
			textField_useName.setText("用户名");
			textField_useName.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					if(textField_useName.getText().equals("用户名")){
						textField_useName.setForeground(Color.BLACK);
						textField_useName.setText("");
					}
				}
				public void focusLost(FocusEvent e) {
					if(textField_useName.getText().equals("")){
						textField_useName.setForeground(Color.GRAY);
						textField_useName.setText("用户名");
					}
				}
			});
			textField_useName.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_textField_useName = new GridBagConstraints();
			gbc_textField_useName.gridwidth = 2;
			gbc_textField_useName.fill = GridBagConstraints.BOTH;
			gbc_textField_useName.insets = new Insets(0, 0, 5, 0);
			gbc_textField_useName.gridx = 0;
			gbc_textField_useName.gridy = 0;
			this.add(textField_useName, gbc_textField_useName);
			//********************************************************密码
			textField_password = new JPasswordField();
			textField_password.setForeground(Color.GRAY);
			textField_password.setText("登录密码");
			textField_password.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					if(textField_password.getText().equals("登录密码")){
						textField_password.setForeground(Color.BLACK);
						textField_password.setText("");
					}
				}
				public void focusLost(FocusEvent e) {
					if(textField_password.getText().equals("")){
						textField_password.setForeground(Color.GRAY);
						textField_password.setText("登录密码");
					}
				}
			});
			textField_password.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_textField_password = new GridBagConstraints();
			gbc_textField_password.gridwidth = 2;
			gbc_textField_password.fill = GridBagConstraints.BOTH;
			gbc_textField_password.insets = new Insets(0, 0, 5, 0);
			gbc_textField_password.gridx = 0;
			gbc_textField_password.gridy = 1;
			this.add(textField_password, gbc_textField_password);	
			//********************************************************记住密码
			CheckBox_password = new JCheckBox("记住密码");
			CheckBox_password.setOpaque(false);
			CheckBox_password.addActionListener(btl);
			CheckBox_password.setActionCommand("记住密码");
			CheckBox_password.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
			gbc_chckbxNewCheckBox.fill = GridBagConstraints.BOTH;
			gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxNewCheckBox.gridx = 0;
			gbc_chckbxNewCheckBox.gridy = 2;
			this.add(CheckBox_password, gbc_chckbxNewCheckBox);
			//********************************************************自动登录
			CheckBox_login = new JCheckBox("自动登录");
			CheckBox_login.setOpaque(false);
			CheckBox_login.addActionListener(btl);
			CheckBox_login.setActionCommand("自动登录");
			CheckBox_login.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_chckbxNewCheckBox_1 = new GridBagConstraints();
			gbc_chckbxNewCheckBox_1.fill = GridBagConstraints.BOTH;
			gbc_chckbxNewCheckBox_1.insets = new Insets(0, 0, 5, 0);
			gbc_chckbxNewCheckBox_1.gridx = 1;
			gbc_chckbxNewCheckBox_1.gridy = 2;
			this.add(CheckBox_login, gbc_chckbxNewCheckBox_1);
			//********************************************************登录
			Button_Login = new JButton("登录");
			Button_Login.addActionListener(btl);
			Button_Login.setActionCommand("登录");
			GridBagConstraints gbc_Button_Login = new GridBagConstraints();
			gbc_Button_Login.fill = GridBagConstraints.BOTH;
			gbc_Button_Login.gridwidth = 2;
			gbc_Button_Login.insets = new Insets(0, 0, 5, 0);
			gbc_Button_Login.gridx = 0;
			gbc_Button_Login.gridy = 3;
			this.add(Button_Login, gbc_Button_Login);
			//********************************************************注册
			Button_join = new JButton("注册");
			Button_join.addActionListener(btl);
			Button_join.setActionCommand("注册");
			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
			gbc_btnNewButton.fill = GridBagConstraints.BOTH;
			gbc_btnNewButton.gridx = 0;
			gbc_btnNewButton.gridy = 4;
			this.add(Button_join, gbc_btnNewButton);
			//********************************************************修改密码
			Button_updata = new JButton("修改密码");
			Button_updata.addActionListener(btl);
			Button_updata.setActionCommand("修改密码");
			GridBagConstraints gbc_Button_updata = new GridBagConstraints();
			gbc_Button_updata.fill = GridBagConstraints.BOTH;
			gbc_Button_updata.gridx = 1;
			gbc_Button_updata.gridy = 4;
			this.add(Button_updata, gbc_Button_updata);
		}	
	}
	/**
	 * 初始化函数
	 */
	public void init(){
		LoginDlg.this.setVisible(true);
		initname = FileRelevant.filepathread("logindata.jw",1);
		initpassword = FileRelevant.filepathread("logindata.jw",2);
		initloginPD = FileRelevant.filepathread("logindata.jw",3);	
		jPanelLogin.textField_useName.setForeground(Color.BLACK);
		jPanelLogin.textField_useName.setText(initname);								//设置用户名
		
		if(!initpassword.equals("") && initloginPD.equals("不登录")){
			jPanelLogin.textField_password.setForeground(Color.BLACK);
			jPanelLogin.textField_password.setText(initpassword);						//如果保存密码了就读取
			jPanelLogin.CheckBox_password.setSelected(true);							//选中保存密码
		}else if(!initpassword.equals("") && initloginPD.equals("自动登录")){
			jPanelLogin.textField_password.setForeground(Color.BLACK);
			jPanelLogin.textField_password.setText(initpassword);						//如果保存密码了就读取
			jPanelLogin.CheckBox_password.setSelected(true);							//选中保存密码
			jPanelLogin.CheckBox_login.setSelected(true);								//选中自动登录
			LOGIN();
		}
	}
	
	/**
	 * 登录函数
	 */
	public void LOGIN(){																			//刷新验证码
		String SAVEneirong;
		if( jPanelLogin.CheckBox_password.isSelected() == true && jPanelLogin.CheckBox_login.isSelected() == false){
			//选保存密码
			SAVEneirong = jPanelLogin.textField_useName.getText() + "\r\n" + jPanelLogin.textField_password.getText() + "\r\n不登录";
		}else if( jPanelLogin.CheckBox_login.isSelected() == true ){
			//选自动登录
			SAVEneirong = jPanelLogin.textField_useName.getText() + "\r\n" + jPanelLogin.textField_password.getText() + "\r\n自动登录";
		}else {
			//什么都没选
			SAVEneirong = jPanelLogin.textField_useName.getText() + "\r\n" + "" + "\r\n不登录";
		}	
		FileRelevant.filepathsave("logindata.jw", SAVEneirong); //输出文本
		initname = FileRelevant.filepathread("logindata.jw",1);	//读取用户名
		
		CD.gamestart();											//游戏启动
		LoginDlg.this.dispose();								//关闭登录窗口
	}
}
