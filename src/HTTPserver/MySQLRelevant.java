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
	//***************************************************************************mysql基础示例
	//构造函数
	public MySQLRelevant(){
	}
	//连接数据库
	public void connectionSQL(String addr, String port, String SQLname, String name, String password){
		this.addr = addr;
		this.port = port;
		this.SQLname = SQLname;
		this.name = name;
		this.password = password;
		
		String url = null;
//		url = "jdbc:mysql://localhost:3306/hello?characterEncoding=UTF-8"; 
		url = String.format("jdbc:mysql://%s:%s/%s", addr,port,SQLname);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,name,password);
			JOptionPane.showMessageDialog(null,"连接数据库" + SQLname + "成功！");
		}catch ( ClassNotFoundException cnfex ) {    
	        JOptionPane.showMessageDialog(null,"//捕获加载驱动程序异常  装载 JDBC/ODBC 驱动程序失败。" + cnfex);
	        cnfex.printStackTrace();  
	    }   
	    catch ( SQLException sqlex ) {  
	        JOptionPane.showMessageDialog(null,"//捕获连接数据库异常  无法连接数据库" + sqlex);
	        sqlex.printStackTrace(); 
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
	//写入数据库
	public void writeSQL(String table, List<Map<String, String>> maplist){
		try {
			if(con != null){
				String insert = "insert into qandrtable(问题,A,B,数字) values('wenti','aaaa','bbbb',123)";  
				pst = con.prepareStatement(insert);
				pst.executeUpdate();
			}else{
				JOptionPane.showMessageDialog(null,"数据库未连接！");
			}
		} catch (SQLException e) {  
            JOptionPane.showMessageDialog(null,"插入数据库时出错：" + e);
            e.printStackTrace();  
        } catch (Exception e) {  
            JOptionPane.showMessageDialog(null,"插入时出错：" + e);
            e.printStackTrace();  
        }  
	}
	//更新数据库
	public void updataSQL(String table, List<Map<String, String>> maplist){
		try {
			if(con != null){
				String update = "update qandrtable set C = '123' where 问题 = 'wenti'";
				pst = con.prepareStatement(update);
				pst.executeUpdate();
			}else{
				JOptionPane.showMessageDialog(null,"数据库未连接！");
			}
		} catch (SQLException e) {  
            JOptionPane.showMessageDialog(null,"更新数据库时出错：" + e);
            e.printStackTrace();  
        } catch (Exception e) {  
            JOptionPane.showMessageDialog(null,"更新时出错：" + e);
            e.printStackTrace();  
        }  
	}
	//删除数据库
	public void deleteSQL(String table, List<Map<String, String>> maplist){
		try {
			if(con != null){
				String delete = "delete from qandrtable where 问题 = 'wenti'";  
				pst = con.prepareStatement(delete);
				pst.executeUpdate();
			}else{
				JOptionPane.showMessageDialog(null,"数据库未连接！");
			}
		} catch (SQLException e) {  
            JOptionPane.showMessageDialog(null,"删除数据库时出错：" + e);
            e.printStackTrace();  
        } catch (Exception e) {  
            JOptionPane.showMessageDialog(null,"删除时出错：" + e);
            e.printStackTrace();  
        }  
	}
	//查询数据库
	public void selectSQL(String table, List<Map<String, String>> maplist){
		ResultSet rs = null;  
		try {
			if(con != null){
				String select = "select * from qandrtable";
				String str = null;
				int zonging = 0;
				//单个查询
				pst = con.prepareStatement(select);
				rs = pst.executeQuery(select);  
				while (rs.next()) {  
					str = " 数字:" + rs.getInt("数字") + " A:" + rs.getString("A") + " B:" + rs.getString("B"); 
					JOptionPane.showMessageDialog(null,"查询内容：" + str);
				} 
				//总数
				select = "select count(*) totalCount from qandrtable";
				rs = pst.executeQuery(select);//ResultSet类，用来存放获取的结果集！！
				while(rs.next()) {   
	       		 zonging = rs.getInt("totalCount");
				}  	   
				JOptionPane.showMessageDialog(null,"查询总数：" + zonging);
				rs.close();
			}else{
				JOptionPane.showMessageDialog(null,"数据库未连接！");
			}
		} catch (SQLException e) {  
            JOptionPane.showMessageDialog(null,"查询数据库时出错：" + e);
            e.printStackTrace();  
        } catch (Exception e) {  
            JOptionPane.showMessageDialog(null,"查询时出错：" + e);
            e.printStackTrace();  
        }  
	}
		
	//*************************************************************************** 用户注册 修改密码等
	//连接用户ID
	public void connectionPlayerGameID(){
		String url = null;
		url = String.format("jdbc:mysql://rm-2ze354j79ce6ky541o.mysql.rds.aliyuncs.com:3306/playerwarid");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,"beijingjiuwei","jiuwei123-"); 		 
		}catch ( Exception e ) {    
	        e.printStackTrace();  
	    }   		
	}
	//写入新用户信息
	public boolean writePlayerGameID(Map<String, String> map){
		String insert = null;
		String userName = null,name = null,password = null,joinDate = null,IPLocation = null;
		try {
			if(con != null){											
				userName = map.get("用户名");
				name = map.get("姓名");
				password = map.get("密码");
				joinDate = map.get("注册日期");
				IPLocation = map.get("IP地址");
				insert = String.format("insert into userid (用户名,密码,姓名,注册日期,IP地址)"
									+ " values(?,?,?,?,?)");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
				pst = con.prepareStatement(insert);	 
				pst.setString(1, userName); 
				pst.setString(2, password);
				pst.setString(3, name);
				Date dateXD = new Date();  
				dateXD = sdf.parse(joinDate); 
				pst.setDate(4,new java.sql.Date(dateXD.getTime())); 				
				pst.setString(5, IPLocation);			
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
						"userid", userName);
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
						"userid", userName);
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
	//用户操作记录
	public boolean PlayerGameuserRecord(Map<String, String> map){
		String insert = "";
		String userName = "";
		String IP = "";
		String NeiRong = "";
		String Date = "";		
		ResultSet rs = null;  			
		userName = map.get("用户名");
		IP = map.get("IP地址");
		NeiRong = map.get("内容");
		Date = map.get("日期");	
		try {
			if(con != null){											

				insert = String.format("insert into userrecord (用户名,IP地址,内容,日期)"
									+ " values(?,?,?,?)");
				pst = con.prepareStatement(insert);	 
				pst.setString(1, userName); 
				pst.setString(2, IP);
				pst.setString(3, NeiRong);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
				Date dateXD = new Date();  
				dateXD = sdf.parse(Date); 
				Timestamp tt = new Timestamp(dateXD.getTime());			
				pst.setTimestamp(4,tt); 
				
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
	//用户读取历史记录
	public boolean PlayerGameuserHistory(Map<String, String> map,JSONObject object){
		int xuhao = 0;
		String xuhaoSTR = "";
		String select = "";
		String userName = "";	
		String NeiRong = "";
		ResultSet rs = null;  	
		
		userName = map.get("用户名");
		
		try {
			if(con != null){
				select = String.format("select * from %s where 用户名  = '%s'", 
						"userrecord", userName);
				//单个查询
				pst = con.prepareStatement(select);
				rs = pst.executeQuery(select);  
				while(rs.next()){
					xuhao++;
					xuhaoSTR = String.valueOf(xuhao);
					NeiRong = rs.getString("内容") + "-" + rs.getDate("日期");
					object.put(xuhaoSTR, NeiRong);		
				}			
				rs.close();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
