package HTTPclient;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Color;

public class JoinDlg extends JFrame {
	
	private JPanel contentPane;
	
	private LoginDlg pLoginDlg;
	private JTextField textField_userName;
	private JTextField textField_name;
	private JPasswordField textField_password;
	private JPasswordField textField_password2;
	private JButton btnNewButton;
	
	private buttonlistener btl;
	
	String userName = "";
	String name = ""; 
	String password = "";
	String password2 = "";
	private JTextField textField_YaoQing;
	
	/**
	 * Create the frame.
	 */
	public JoinDlg(LoginDlg ld) {
		this.pLoginDlg = ld;
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				pLoginDlg.setEnabled(true);
			}
		});
		btl = new buttonlistener();
		//**************************************************主窗口
		setTitle("生产单上传：注册");
		setSize(462, 260);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(98, 18, 260, 195);
		contentPane.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{120, 120};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panel.setLayout(gbl_panel);
		
		//**************************************************控件
		JLabel lblNewLabel = new JLabel("用户名");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		textField_userName = new JTextField();
		textField_userName.setForeground(Color.GRAY);
		textField_userName.setText("手机号/邮箱");
		textField_userName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_userName.getText().equals("手机号/邮箱")){
					textField_userName.setForeground(Color.BLACK);
					textField_userName.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_userName.getText().equals("")){
					textField_userName.setForeground(Color.GRAY);
					textField_userName.setText("手机号/邮箱");
				}
			}
		});
		textField_userName.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField_userName = new GridBagConstraints();
		gbc_textField_userName.fill = GridBagConstraints.BOTH;
		gbc_textField_userName.insets = new Insets(0, 0, 5, 0);
		gbc_textField_userName.gridx = 1;
		gbc_textField_userName.gridy = 0;
		panel.add(textField_userName, gbc_textField_userName);
		
		JLabel lblNewLabel_1 = new JLabel("姓名");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textField_name = new JTextField();
		textField_name.setForeground(Color.GRAY);
		textField_name.setText("真实姓名");
		textField_name.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_name.getText().equals("真实姓名")){
					textField_name.setForeground(Color.BLACK);
					textField_name.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_name.getText().equals("")){
					textField_name.setForeground(Color.GRAY);
					textField_name.setText("真实姓名");
				}
			}
		});
		textField_name.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField_name = new GridBagConstraints();
		gbc_textField_name.fill = GridBagConstraints.BOTH;
		gbc_textField_name.insets = new Insets(0, 0, 5, 0);
		gbc_textField_name.gridx = 1;
		gbc_textField_name.gridy = 1;
		panel.add(textField_name, gbc_textField_name);
		
		JLabel lblNewLabel_2 = new JLabel("密码");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textField_password = new JPasswordField();
		textField_password.setForeground(Color.GRAY);
		textField_password.setText("两次输入一致");
		textField_password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_password.getText().equals("两次输入一致")){
					textField_password.setForeground(Color.BLACK);
					textField_password.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_password.getText().equals("")){
					textField_password.setForeground(Color.GRAY);
					textField_password.setText("两次输入一致");
				}
			}
		});
		textField_password.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField_password = new GridBagConstraints();
		gbc_textField_password.fill = GridBagConstraints.BOTH;
		gbc_textField_password.insets = new Insets(0, 0, 5, 0);
		gbc_textField_password.gridx = 1;
		gbc_textField_password.gridy = 2;
		panel.add(textField_password, gbc_textField_password);
		textField_password.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("确认密码");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		textField_password2 = new JPasswordField();
		textField_password2.setForeground(Color.GRAY);
		textField_password2.setText("请您输入密码");
		textField_password2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_password2.getText().equals("请您输入密码")){
					textField_password2.setForeground(Color.BLACK);
					textField_password2.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_password2.getText().equals("")){
					textField_password2.setForeground(Color.GRAY);
					textField_password2.setText("请您输入密码");
				}
			}
		});
		textField_password2.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField_password2 = new GridBagConstraints();
		gbc_textField_password2.fill = GridBagConstraints.BOTH;
		gbc_textField_password2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_password2.gridx = 1;
		gbc_textField_password2.gridy = 3;
		panel.add(textField_password2, gbc_textField_password2);
		textField_password2.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("邀请码");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 4;
		panel.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		textField_YaoQing = new JTextField();
		textField_YaoQing.setForeground(Color.GRAY);
		textField_YaoQing.setText("公司座机号");
		textField_YaoQing.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_YaoQing.getText().equals("公司座机号")){
				textField_YaoQing.setForeground(Color.BLACK);
				textField_YaoQing.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_YaoQing.getText().equals("")){
				textField_YaoQing.setForeground(Color.GRAY);
				textField_YaoQing.setText("公司座机号");
				}
			}
		});
		textField_YaoQing.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField_YaoQing = new GridBagConstraints();
		gbc_textField_YaoQing.fill = GridBagConstraints.BOTH;
		gbc_textField_YaoQing.insets = new Insets(0, 0, 5, 0);
		gbc_textField_YaoQing.gridx = 1;
		gbc_textField_YaoQing.gridy = 4;
		panel.add(textField_YaoQing, gbc_textField_YaoQing);
		textField_YaoQing.setColumns(10);
		
		btnNewButton = new JButton("取消");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 5;
		panel.add(btnNewButton, gbc_btnNewButton);
		btnNewButton.addActionListener(btl);
		btnNewButton.setActionCommand("取消");
		
		JButton btnNewButton_1 = new JButton("注册");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton_1.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 5;
		panel.add(btnNewButton_1, gbc_btnNewButton_1);
		btnNewButton_1.addActionListener(btl);
		btnNewButton_1.setActionCommand("注册");
	}	
	
	/**
	 * 按钮响应事件
	 * @author Administrator
	 *
	 */
	public class buttonlistener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			switch (e.getActionCommand()) {
			case "注册":			
				userName = textField_userName.getText();
				name = textField_name.getText(); 
				password = textField_password.getText();
				password2 = textField_password2.getText();
				
				if(!userName.equals("")){
					if(!name.equals("")){
						if(!password.equals("")){
							if(!password2.equals("")){
								if(password2.equals(password)){
									if(textField_YaoQing.getText().equals("123")){
										
										//*************************************************************开始注册
										HTTPclient httPclient = new HTTPclient("123.123.123.123","1234");
										String returnSTR = httPclient.HTTPclientSendJoin(userName, name, password);	
										if(returnSTR.equals("注册成功")){
											JOptionPane.showMessageDialog(null, "注册成功！","提示",JOptionPane.INFORMATION_MESSAGE);					
											pLoginDlg.setEnabled(true);
											JoinDlg.this.dispose();	
										}else if(returnSTR.equals("服务端异常")){
											JOptionPane.showMessageDialog(null, "注册服务端异常！","提示",JOptionPane.INFORMATION_MESSAGE);	
										}else{
											JOptionPane.showMessageDialog(null, "注册失败，用户名重复 或 格式有误！","提示",JOptionPane.INFORMATION_MESSAGE);					
										}
										
									}else{
										JOptionPane.showMessageDialog(null, "邀请码错误！","提示",JOptionPane.INFORMATION_MESSAGE);	
									}							
								}else{
									JOptionPane.showMessageDialog(null, "两次密码不一致!","提示",JOptionPane.ERROR_MESSAGE);
								}			
							}else{
								JOptionPane.showMessageDialog(null, "请填写正确的确认密码!","提示",JOptionPane.ERROR_MESSAGE);
							}
						}else{
							JOptionPane.showMessageDialog(null, "请填写正确的密码!","提示",JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(null, "请填写正确的姓名!","提示",JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "请填写正确的用户名!","提示",JOptionPane.ERROR_MESSAGE);	
				}
				
				break;
			case "取消":
				pLoginDlg.setEnabled(true);
				JoinDlg.this.dispose();
				break;
			default:
				break;
			}
		}	
	}
}
