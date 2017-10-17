package HTTPclient;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPclient {
	private String Addr = "";
	private String PORT = "";
	/**
	 * 构造函数
	 * @param addr
	 * @param port
	 */
	public HTTPclient(String addr,String port){
		this.Addr = addr;
		this.PORT = port;
	}
	/**
	 * 注册
	 * @param userName
	 * @param name
	 * @param password
	 * @return
	 */
	public String HTTPclientSendJoin(String userName, String password){
		try {
			URL url = new URL("http://" + Addr + ":" + PORT + "/join");			//URL地址
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();	//HTTP打开URL连接               
		    conn.setRequestMethod("POST");										//设置URL请求的方法       
		    conn.setDoInput(true);												//输入 发送POST请求必须设置
		    conn.setDoOutput(true);												//输出  发送POST请求必须设置      
		    conn.setUseCaches(true);											//客户可以使用缓存
		    
			String SENDstr = "";												//文字发送
			SENDstr = writeuserpassword(userName, password);
			byte[] FSwzbyte = SENDstr.getBytes();       
			OutputStream out = conn.getOutputStream();			
			out.write(FSwzbyte);
			out.flush();
			out.close();
		          
			byte[] buffer = new byte[1024];									//文字接收
			int length = 0;
			String resultSTR = null;        
			InputStream in = conn.getInputStream();  
			while ((length = in.read(buffer)) != -1) {
				resultSTR = new String(buffer,0,length);
			}
			in.close();     
			conn.disconnect();												//连接断开
			
			return resultSTR;
		} catch (Exception e) {
			// TODO: handle exception
			return "服务端异常";
		}		
	}	
	/**
	 * 登录
	 */
	public String HTTPclientSendLogin(String userName,String password){
		try {
			URL url = new URL("http://" + Addr + ":" + PORT + "/login");		//URL地址
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();	//HTTP打开URL连接              
		    conn.setRequestMethod("POST");										//设置URL请求的方法       
		    conn.setDoInput(true);												//输入 发送POST请求必须设置
		    conn.setDoOutput(true);												//输出  发送POST请求必须设置      
		    conn.setUseCaches(true);											//客户可以使用缓存
		    conn.connect();
		    
			String SENDstr = "";												//文字发送
			SENDstr = writeLogin(userName,password);
			byte[] FSwzbyte = SENDstr.getBytes();       
			OutputStream out = conn.getOutputStream();			
			out.write(FSwzbyte);
			out.flush();
			out.close();
		          
			byte[] buffer = new byte[1024];										//文字接收
			int length = 0;
			String resultSTR = null;        
			InputStream in = conn.getInputStream();  
			while ((length = in.read(buffer)) != -1) {
				resultSTR = new String(buffer,0,length);
			}
			in.close();     
			conn.disconnect();													//连接断开
			
			return resultSTR;
		} catch (Exception e) {
			return "服务端异常";
		}		
	}	
	/**
	 * 修改密码
	 */
	public String HTTPclientSendUpdata(String userName,String oldPassword,String newPassword){
		try {
			URL url = new URL("http://" + Addr + ":" + PORT + "/updata");		//URL地址
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();	//HTTP打开URL连接              
		    conn.setRequestMethod("POST");										//设置URL请求的方法       
		    conn.setDoInput(true);												//输入 发送POST请求必须设置
		    conn.setDoOutput(true);												//输出  发送POST请求必须设置      
		    conn.setUseCaches(true);											//客户可以使用缓存
		    
			String SENDstr = "";												//文字发送
			SENDstr = writeUpdata(userName, oldPassword, newPassword);
			byte[] FSwzbyte = SENDstr.getBytes();       
			OutputStream out = conn.getOutputStream();			
			out.write(FSwzbyte);
			out.flush();
			out.close();
		          
			byte[] buffer = new byte[1024];									//文字接收
			int length = 0;
			String resultSTR = null;        
			InputStream in = conn.getInputStream();  
			while ((length = in.read(buffer)) != -1) {
				resultSTR = new String(buffer,0,length);
			}
			in.close();     
			conn.disconnect();												//连接断开
			
			return resultSTR;
		} catch (Exception e) {
			// TODO: handle exception
			return "服务端异常";
		}		
	}
	//************************************************************************************内容
	/**
	 * 发送 注册 内容
	 */
	public String writeuserpassword(String userName,String password){
		//*************************************************************生成map
		String data = String.format("{\"用户名\":\"%s\",\"密码\":\"%s\"}",
				userName,password);
		return data;
	}
	/**
	 * 发送 登录 内容
	 */
	public String writeLogin(String userName,String password){
		//*************************************************************生成map
		String data = String.format("{\"用户名\":\"%s\",\"密码\":\"%s\"}",
				userName,password);
		return data;
	}
	/**
	 * 发送 修改密码 内容
	 */
	public String writeUpdata(String userName,String oldPassword,String newPassword){
		//*************************************************************生成map
		String data = String.format("{\"用户名\":\"%s\",\"密码\":\"%s\",\"新密码\":\"%s\"}",
				userName,oldPassword,newPassword);
		return data;
	}

}
