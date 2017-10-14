package HTTPclient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import net.sf.json.JSONObject;

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
	public String HTTPclientSendJoin(String userName,String name,String password){
		try {
			URL url = new URL("http://" + Addr + ":" + PORT + "/join");			//URL地址
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();	//HTTP打开URL连接             
//			conn.setConnectTimeout(30000);										//设置连接主机超时（单位：毫秒） 
//			conn.setReadTimeout(30000); 										//设置从主机读取数据超时（单位：毫秒）       
		    conn.setRequestMethod("POST");										//设置URL请求的方法       
		    conn.setDoInput(true);												//输入 发送POST请求必须设置
		    conn.setDoOutput(true);												//输出  发送POST请求必须设置      
		    conn.setUseCaches(true);											//客户可以使用缓存
		    
			String SENDstr = "";												//文字发送
			SENDstr = writeuserpassword(userName,name,password);
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
//			conn.setConnectTimeout(30000);										//设置连接主机超时（单位：毫秒） 
//			conn.setReadTimeout(30000); 										//设置从主机读取数据超时（单位：毫秒）       
		    conn.setRequestMethod("POST");										//设置URL请求的方法       
		    conn.setDoInput(true);												//输入 发送POST请求必须设置
		    conn.setDoOutput(true);												//输出  发送POST请求必须设置      
		    conn.setUseCaches(true);											//客户可以使用缓存
		    
			String SENDstr = "";												//文字发送
			SENDstr = writeLogin(userName,password);
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
	 * 修改密码
	 */
	public String HTTPclientSendUpdata(String userName,String oldPassword,String newPassword){
		try {
			URL url = new URL("http://" + Addr + ":" + PORT + "/updata");		//URL地址
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();	//HTTP打开URL连接             
//			conn.setConnectTimeout(30000);										//设置连接主机超时（单位：毫秒） 
//			conn.setReadTimeout(30000); 										//设置从主机读取数据超时（单位：毫秒）       
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

	/**
	 * 发送用户操作信息
	 * @param userName
	 * @param NeiRong
	 */
	public void UserMessage(String userName,String NeiRong){
		try {
			URL url = new URL("http://" + Addr + ":" + PORT + "/userMessage");		
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();		
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(true);
			
			String sendSTR = "";											//文字发送
			sendSTR = writeUserMessage(userName,NeiRong);
			byte[] FSwz = sendSTR.getBytes();
			OutputStream ots = conn.getOutputStream();
			ots.write(FSwz);
			ots.flush();
			ots.close();
			
			byte[] buffer = new byte[1024];									//文字接收
			int length = 0;
			String resultSTR = null;        
			InputStream in = conn.getInputStream();  
			while ((length = in.read(buffer)) != -1) {
				resultSTR = new String(buffer,0,length);
			}
			in.close();     
			conn.disconnect();												//连接断开
			System.out.println(resultSTR);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 接收 用户历史信息
	 * @param userName
	 * @param v
	 */
	public void UserHistory(String userName,Vector<String> v){
		try {
			URL url = new URL("http://" + Addr + ":" + PORT + "/userHistory");		
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();		
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(true);
			
			String sendSTR = "";											//文字发送
			sendSTR = writeUserHistory(userName);
			byte[] FSwz = sendSTR.getBytes();
			OutputStream ots = conn.getOutputStream();
			ots.write(FSwz);
			ots.flush();
			ots.close();
					
			InputStream in = conn.getInputStream();
			InputStreamReader inr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(inr);         
			String line = "";
			StringBuilder builder = new StringBuilder();
			while((line = br.readLine())!=null){
				builder.append(line);
			}	
			in.close();     
			conn.disconnect();										//连接断开
			
			String resultSTR = null;        
			resultSTR = builder.toString();													
			JSONObject jsonObject = new JSONObject();
			Map<String, String> map = new HashMap<String,String>();
			jsonObject = JSONObject.fromObject(resultSTR);
			map.putAll(jsonObject);
			for (int i = 0; i < jsonObject.size(); i++) {
				v.add(map.get(String.valueOf(i+1)));				//内容是 从 1 开始的
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//************************************************************************************内容
	/**
	 * 发送 注册 内容
	 */
	public String writeuserpassword(String userName,String name,String password){
		//*************************************************************创建日期
		String datetime = "";		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");		//设置日期格式
		datetime = df.format(new Date());								// new Date()为获取当前系统时间
		
		//*************************************************************创建IP地址MAC号
		String IpMac = "";
		try {
			String Ip = InetAddress.getLocalHost().toString();
			
			byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			StringBuffer sb = new StringBuffer("");
			for(int i=0; i<mac.length; i++) {
				if(i!=0) {
					sb.append("-");
				}	
				int temp = mac[i]&0xff;
				String str = Integer.toHexString(temp);
				if(str.length()==1) {
					sb.append("0"+str);
				}else {
					sb.append(str);
				}
			}
			String MacStr = sb.toString().toUpperCase();	
			IpMac = Ip + "@" + MacStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		//*************************************************************生成map
		String data = String.format("{\"用户名\":\"%s\",\"姓名\":\"%s\",\"密码\":\"%s\",\"注册日期\":\"%s\",\"IP地址\":\"%s\"}",
				userName,name,password,datetime,IpMac);
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
	/**
	 * 写用户操作信息
	 * @param userName
	 * @param NeiRong
	 * @return
	 */
	public String writeUserMessage(String userName,String NeiRong){
		
		//*************************************************************创建日期
		String datetime = "";		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		//设置日期格式
		datetime = df.format(new Date());										// new Date()为获取当前系统时间
		
		//*************************************************************创建IP地址MAC号
		String IpMac = "";
		try {
			String Ip = InetAddress.getLocalHost().toString();
			
			byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			StringBuffer sb = new StringBuffer("");
			for(int i=0; i<mac.length; i++) {
				if(i!=0) {
					sb.append("-");
				}	
				int temp = mac[i]&0xff;
				String str = Integer.toHexString(temp);
				if(str.length()==1) {
					sb.append("0"+str);
				}else {
					sb.append(str);
				}
			}
			String MacStr = sb.toString().toUpperCase();	
			IpMac = Ip + "@" + MacStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		//*************************************************************生成map
		String data = String.format("{\"用户名\":\"%s\",\"内容\":\"%s\",\"日期\":\"%s\",\"IP地址\":\"%s\"}",
				userName,NeiRong,datetime,IpMac);
		return data;
	}
	/**
	 * 接收 用户历史信息
	 * @param userName
	 * @return
	 */
	public String writeUserHistory(String userName){
		//*************************************************************生成map
		String data = String.format("{\"用户名\":\"%s\"}",
				userName);
		return data;
	}
}
