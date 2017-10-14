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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

import org.apache.commons.httpclient.HttpClient;

import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class LoginDlg extends JFrame {
	
	private Container container;			//设置一个容器
	private JPanel jPanelLogin;
	
	private GridBagLayout gridBagLayout;
	
	private JTextField textField_useName;
	private JPasswordField textField_password;
	private JLabel Label_useName;
	private JLabel Label_password;
	private JButton Button_Login;
	private JLabel Label_readCode;
	private JCheckBox CheckBox_password;
	private JCheckBox CheckBox_login;
	private JButton Button_join;
	private JTextField textField_Code;
	private JMenuItem mntmNewMenuItem;
	private JMenu mnNewMenu;
	private JMenuBar menuBar;
	
	private buttonlistener btl;
	
	playerwar.PlayerClient CD;
	
	String initname = "";
	String initpassword = "";
	String initloginPD = "";
	
	String StrCode = "";
	String INStrCode = "";
	String userName = "";
	String password = "";

	/**
	 * 创建登录主窗口
	 */
	public LoginDlg(playerwar.PlayerClient cd) {
		btl = new buttonlistener();
		this.CD = cd;
		//********************************************主窗口
		setTitle("生产单上传：登录");
		setSize(437, 246);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		container = getContentPane();	
		container.setLayout(null);
		
		//********************************************控件	
		gridBagLayout = new GridBagLayout();										//创件gridbag布局
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};			//行是否扩展
		gridBagLayout.rowHeights = new int[]{30, 30, 30, 30, 30};					//行权重,行之间的比例
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};						//列是否扩展
		gridBagLayout.columnWidths = new int[]{120, 120};							//列权重,列之间的比例
		jPanelLogin = new JPanel();													//创件面板
		jPanelLogin.setLayout(gridBagLayout);										//面板设置布局为gridbag
		jPanelLogin.setBounds(83, 12, 265, 173);									//设置面板位置，大小
		container.add(jPanelLogin);
		
		Label_useName = new JLabel("用 户 名");
		Label_useName.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_Label_useName = new GridBagConstraints();
		gbc_Label_useName.fill = GridBagConstraints.BOTH;
		gbc_Label_useName.insets = new Insets(0, 0, 5, 5);
		gbc_Label_useName.gridx = 0;
		gbc_Label_useName.gridy = 0;
		jPanelLogin.add(Label_useName, gbc_Label_useName);
		
		textField_useName = new JTextField();
		textField_useName.setForeground(Color.GRAY);
		textField_useName.setText("手机号/邮箱");
		textField_useName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_useName.getText().equals("手机号/邮箱")){
					textField_useName.setForeground(Color.BLACK);
					textField_useName.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_useName.getText().equals("")){
					textField_useName.setForeground(Color.GRAY);
					textField_useName.setText("手机号/邮箱");
				}
			}
		});
		textField_useName.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField_useName = new GridBagConstraints();
		gbc_textField_useName.fill = GridBagConstraints.BOTH;
		gbc_textField_useName.insets = new Insets(0, 0, 5, 0);
		gbc_textField_useName.gridx = 1;
		gbc_textField_useName.gridy = 0;
		jPanelLogin.add(textField_useName, gbc_textField_useName);
		
		Label_password = new JLabel("密    码");
		Label_password.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_Label_password = new GridBagConstraints();
		gbc_Label_password.fill = GridBagConstraints.BOTH;
		gbc_Label_password.insets = new Insets(0, 0, 5, 5);
		gbc_Label_password.gridx = 0;
		gbc_Label_password.gridy = 1;
		jPanelLogin.add(Label_password, gbc_Label_password);
		
		textField_password = new JPasswordField();
		textField_password.setForeground(Color.GRAY);
		textField_password.setText("登录密码");
		textField_password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_password.getText().equals("登录密码")){
					textField_password.setForeground(Color.BLACK);
					textField_password.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_password.getText().equals("")){
					textField_password.setForeground(Color.GRAY);
					textField_password.setText("登录密码");
				}
			}
		});
		textField_password.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField_password = new GridBagConstraints();
		gbc_textField_password.fill = GridBagConstraints.BOTH;
		gbc_textField_password.insets = new Insets(0, 0, 5, 0);
		gbc_textField_password.gridx = 1;
		gbc_textField_password.gridy = 1;
		jPanelLogin.add(textField_password, gbc_textField_password);
		
		Label_readCode = new JLabel("验证码");
		Label_readCode.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		jPanelLogin.add(Label_readCode, gbc_lblNewLabel_1);
		Label_readCode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				readCode();
			}
		});
		
		textField_Code = new JTextField();
		textField_Code.setForeground(Color.GRAY);
		textField_Code.setText("验证码");
		textField_Code.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_Code.getText().equals("验证码")){
					textField_Code.setForeground(Color.BLACK);
					textField_Code.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_Code.getText().equals("")){
					textField_Code.setForeground(Color.GRAY);
					textField_Code.setText("验证码");
				}
			}
		});
		textField_Code.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		jPanelLogin.add(textField_Code, gbc_textField);
		textField_Code.setColumns(10);
		
		CheckBox_password = new JCheckBox("记住密码");
		CheckBox_password.addActionListener(btl);
		CheckBox_password.setActionCommand("记住密码");
		CheckBox_password.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.SOUTHEAST;
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox.gridx = 0;
		gbc_chckbxNewCheckBox.gridy = 3;
		jPanelLogin.add(CheckBox_password, gbc_chckbxNewCheckBox);
		
		CheckBox_login = new JCheckBox("自动登录");
		CheckBox_login.addActionListener(btl);
		CheckBox_login.setActionCommand("自动登录");
		CheckBox_login.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_chckbxNewCheckBox_1 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_1.anchor = GridBagConstraints.SOUTHWEST;
		gbc_chckbxNewCheckBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox_1.gridx = 1;
		gbc_chckbxNewCheckBox_1.gridy = 3;
		jPanelLogin.add(CheckBox_login, gbc_chckbxNewCheckBox_1);
		

		Button_join = new JButton("注册");
		Button_join.addActionListener(btl);
		Button_join.setActionCommand("注册");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 4;
		jPanelLogin.add(Button_join, gbc_btnNewButton);
		
		Button_Login = new JButton("登录");
		Button_Login.addActionListener(btl);
		Button_Login.setActionCommand("登录");
		GridBagConstraints gbc_Button_Login = new GridBagConstraints();
		gbc_Button_Login.anchor = GridBagConstraints.SOUTHWEST;
		gbc_Button_Login.gridx = 1;
		gbc_Button_Login.gridy = 4;
		jPanelLogin.add(Button_Login, gbc_Button_Login);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnNewMenu = new JMenu("设置");
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem("修改密码");
		mntmNewMenuItem.addActionListener(btl);
		mntmNewMenuItem.setActionCommand("修改密码");
		mnNewMenu.add(mntmNewMenuItem);
				
		//********************************************初始化参数
		 init();
	}
	
	/**
	 * 控件响应事件
	 * @author KamiAki
	 *
	 */
	public class buttonlistener implements ActionListener{	
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "修改密码":
				LoginDlg.this.setEnabled(false);
				UpDataDlg updatadlg = new UpDataDlg(LoginDlg.this);
				updatadlg.setVisible(true);
				break;
			case "登录":				
				INStrCode = textField_Code.getText();
				userName = textField_useName.getText();
				password = textField_password.getText();
				
				if( INStrCode.equals(StrCode) ){
					if(!userName.equals("")){
						if(!password.equals("")){
							
							//**************************************************************开始登录验证
							HTTPclient httPclient = new HTTPclient("123","123");
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
							JOptionPane.showMessageDialog(null, "密码错误！","提示",JOptionPane.ERROR_MESSAGE);
						}			
					}else{
						JOptionPane.showMessageDialog(null, "用户名错误！","提示",JOptionPane.ERROR_MESSAGE);
					}	
				}else{
					JOptionPane.showMessageDialog(null, "验证码不对哦！","提示",JOptionPane.ERROR_MESSAGE);
				}
				break;
			case "自动登录":

				if(CheckBox_password.isSelected() == false && CheckBox_login.isSelected() == true){
					CheckBox_password.setSelected(true);
				}else if(CheckBox_password.isSelected() == true && CheckBox_login.isSelected() == false){
					CheckBox_password.setSelected(false);
				}
				break;
			case "记住密码":
				if(CheckBox_password.isSelected() == false && CheckBox_login.isSelected() == true){
					CheckBox_login.setSelected(false);
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
	 * 初始化函数
	 */
	public void init(){
		readCode();															//生成验证码
		LoginDlg.this.setVisible(true);
		initname = FileRelevant.filepathread("logindata.jw",1);
		initpassword = FileRelevant.filepathread("logindata.jw",2);
		initloginPD = FileRelevant.filepathread("logindata.jw",3);	
		textField_useName.setForeground(Color.BLACK);
		textField_useName.setText(initname);								//设置用户名
		
		if(!initpassword.equals("") && initloginPD.equals("不登录")){
			textField_password.setForeground(Color.BLACK);
			textField_password.setText(initpassword);						//如果保存密码了就读取
			CheckBox_password.setSelected(true);							//选中保存密码
		}else if(!initpassword.equals("") && initloginPD.equals("自动登录")){
			textField_password.setForeground(Color.BLACK);
			textField_password.setText(initpassword);						//如果保存密码了就读取
			CheckBox_password.setSelected(true);							//选中保存密码
			CheckBox_login.setSelected(true);								//选中自动登录
			LOGIN();
		}
	}
	
	/**
	 * 登录函数
	 */
	public void LOGIN(){
		readCode();																				//刷新验证码
		String SAVEneirong;
		if( CheckBox_password.isSelected() == true && CheckBox_login.isSelected() == false){
			SAVEneirong = textField_useName.getText() + "\r\n" + textField_password.getText() + "\r\n不登录";
		}else if( CheckBox_login.isSelected() == true ){
			SAVEneirong = textField_useName.getText() + "\r\n" + textField_password.getText() + "\r\n自动登录";
		}else {
			SAVEneirong = textField_useName.getText() + "\r\n" + "" + "\r\n不登录";
		}	
		FileRelevant.filepathsave("logindata.jw", SAVEneirong);
		initname = FileRelevant.filepathread("logindata.jw",1);
		
		CD.setVisible(true);
		LoginDlg.this.dispose();
	}
	
	/**
	 * 加载验证码
	 */
	public void readCode(){
		StrCode = String.valueOf(  (int)((Math.random()*9+1)*1000)  );
		createImage(StrCode,new Font("宋体", Font.BOLD, 14),Label_readCode);		
	}
	/**
	 * 文字转图片
	 */
	public void createImage(String str,Font font,JLabel codelabel) {
		//获取font的样式应用在str上的整个矩形
		Rectangle2D r=font.getStringBounds(str, new FontRenderContext(AffineTransform.getScaleInstance(1, 1),false,false));
		int width=(int)Math.round(r.getWidth())+1;		//获取整个str用了font样式的宽度这里用四舍五入后+1保证宽度绝对能容纳这个字符串作为图片的宽度
		int height=(int)Math.floor(r.getHeight())+3;		//把单个字符的高度+3保证高度绝对能容纳字符串作为图片的高度
		//创建图片
		BufferedImage CreateImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
		Graphics g = CreateImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);//先用白色填充整张图片,也就是背景
		g.setColor(Color.black);//在换成黑色
		g.setFont(font);//设置画笔字体
		g.drawString(str, 0, font.getSize());//画出字符串
		g.dispose();
		//输出图片
		ImageIcon imageicon = new ImageIcon(CreateImage);
		//设置标签
		Label_readCode.setText("");
		Label_readCode.setIcon(imageicon);
	}
}
