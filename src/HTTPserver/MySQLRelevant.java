package HTTPserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Date;
import java.sql.Timestamp;

public class MySQLRelevant {
	
	public String addr = "";
	public String port = "";
	public String SQLname = "";
	public String name = "";
	public String password = "";	
	public Connection con = null;
	public PreparedStatement pst = null;

	//*************************************************************************** 用户注册 修改密码等
	//连接用户ID
	public void connectionPlayerGameID(){
		String url = null;
		url = String.format("jdbc:mysql://rm-2ze354j79ce6ky541o.mysql.rds.aliyuncs.com:3306/aki");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,"beijingjiuwei","jiuwei123-"); 		 
		}catch ( Exception e ) {    
	        e.printStackTrace();  
	    }   		
	}
	//关闭数据库
	public void deconnectionSQL(){
		try {
			if(con != null){
				con.close();
			}else{
//				JOptionPane.showMessageDialog(null,"数据库未连接！");
			}
		} catch (Exception e) {
			// TODO: handle exception
//			JOptionPane.showMessageDialog(null,"数据库关闭失败！" + e);
			e.printStackTrace();
		}
	}
	//写入新用户信息
	public boolean writePlayerGameID(Map<String, String> map){
		String insert = null;
		String userName = null,password = null;
		try {
			if(con != null){											
				userName = map.get("用户名");
				password = map.get("密码");
				insert = String.format("insert into playerwarid (用户名,密码)"
									+ " values(?,?)");
				pst = con.prepareStatement(insert);	 
				pst.setString(1, userName); 
				pst.setString(2, password);		
				pst.executeUpdate(); //执行更新	
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {  
            e.printStackTrace();  
            return false;
        } 
	}
	//修改用户信息
	public boolean updataPlayerGameID(Map<String, String> map){	
		String update = "";
		String userName = "",newPassWord = "";	
		userName = map.get("用户名");
		newPassWord = map.get("新密码");
		try {
			if(con != null){		
				update = String.format("update %s set 密码 = '%s' where 用户名  = '%s'", 
						"userid", newPassWord, userName);
				pst = con.prepareStatement(update);		
				pst.executeUpdate();
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {  
            e.printStackTrace(); 
            return false;
		}
	}
	//查询用户名是否存在
	public boolean selectPlayerGameUserNameID(Map<String, String> map){	
		String select = "";
		String userName = "";
		
		ResultSet rs = null;  	
		
		userName = map.get("用户名");
		try {
			if(con != null){
				select = String.format("select * from %s where 用户名 = '%s'", 
						"playerwarid", userName);
				//单个查询
				pst = con.prepareStatement(select);
				rs = pst.executeQuery(select);  
				if(rs.next()){
					rs.close();
					return true;
				}else{
					rs.close();
					return false;
				}
			}else{
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	//查询用户名,密码是否正确
	public boolean selectPlayerGameUserNamePassWordID(Map<String, String> map){	
		String select = "";
		String userName = "";
		String passWord = "";
		String SQLpassword = "";
		
		ResultSet rs = null;  	
		
		userName = map.get("用户名");
		passWord = map.get("密码");
		try {
			if(con != null){
				select = String.format("select * from %s where 用户名 = '%s'", 
						"playerwarid", userName);
				//单个查询
				pst = con.prepareStatement(select);
				rs = pst.executeQuery(select);  
				if(rs.next()){
					SQLpassword = rs.getString("密码");
					if( passWord.equals( SQLpassword ) ){
						rs.close();
						return true;
					}else{
						rs.close();
						return false;
					}	
				}else{
					rs.close();
					return false;
				}
			}else{
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
