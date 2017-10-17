package HTTPserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import javafx.print.JobSettings;
import net.sf.json.JSONObject;

public class HTTPserver {
	private int PORT = 0;
	private int LINKNUMBER = 0;
	private HttpServer server = null;
	private HttpServerProvider provider = null;
	
	public HTTPserver(int port,int linknumber){
		this.PORT = port;
		this.LINKNUMBER = linknumber;
	}
	
	/**
	 * 服务器开启
	 */
	public boolean HTTPserverStrat(){
		try {
			provider = HttpServerProvider.provider();
	        server = provider.createHttpServer(new InetSocketAddress(PORT), LINKNUMBER); 				//1.端口   2. 连接等待数
	       
	        server.createContext("/join", new joinHttpHandler());   									//注册信息
	        server.createContext("/login",new loginHttpHandler());										//登录信息
	        server.createContext("/updata",new updataHttpHandler());									//修改信息
	        
			ExecutorService pool = Executors.newCachedThreadPool();										//线程池		
	        server.setExecutor(pool);																	//null为单线程
	        server.start();	
	        return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	/**
	 * 服务器关闭
	 */
	public boolean HTTPserverOff(){
		try {
			if(server != null){
		        server.stop(1);  //几秒后停止
		        return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 注册
	 * @author Administrator
	 *
	 */
	class joinHttpHandler implements HttpHandler
	{
	    @Override
	    public void handle(HttpExchange HttpConn) throws IOException
	    {
	    	boolean PD = false;
	        //*********************************************************读取内容
	        String readSTR = null;								
	        byte[] readBuffer = new byte[1024];
	        int readLength = 0; 
	        InputStream in = HttpConn.getRequestBody(); 
	        while ((readLength = in.read(readBuffer)) != -1) {
	        	readSTR = new String(readBuffer,0,readLength);
			}
	        in.close();
	        
	        //*********************************************************根据读取的内容，决定发送的内容   	        	        							
	        JSONObject jsonObj  = JSONObject.fromObject(readSTR);
	    	Map<String, String> map = new HashMap<String,String>();				
	    	map.putAll(jsonObj);
			MySQLRelevant mysqlID = new MySQLRelevant();
			mysqlID.connectionPlayerGameID();
			if(mysqlID.selectPlayerGameUserNameID(map)){
				PD = false;
			}else{
				PD = mysqlID.writePlayerGameID(map);
			}
			mysqlID.deconnectionSQL();
			
	        //*********************************************************发送数据到客户端  	       
	        byte[] writeBuffer = null;
	        if(PD == true){
		        writeBuffer = "注册成功".getBytes();
	        }else{
	        	writeBuffer = "注册失败".getBytes();
	        }
	        HttpConn.sendResponseHeaders(HttpURLConnection.HTTP_OK, writeBuffer.length); 
	        OutputStream out = HttpConn.getResponseBody();
	        out.write(writeBuffer);
	        out.flush();  
	        out.close();
	        
	        //*********************************************************关闭http       
	        HttpConn.close();           
	    } //handle函数    
		
	}//DyanHttpHandler类

	/**
	 * 登录
	 */
	class loginHttpHandler implements HttpHandler
	{
	    @Override
	    public void handle(HttpExchange HttpConn) throws IOException
	    {
	    	boolean PD = false;       
	        //*********************************************************读取内容
	        String readSTR = null;								
	        byte[] readBuffer = new byte[1024];
	        int readLength = 0; 
	        InputStream in = HttpConn.getRequestBody(); 
	        while ((readLength = in.read(readBuffer)) != -1) {
	        	readSTR = new String(readBuffer,0,readLength);
			}
	        in.close();
	        
	        //*********************************************************根据读取的内容，决定发送的内容   	        	        							
	        JSONObject jsonObj  = JSONObject.fromObject(readSTR);
	    	Map<String, String> map = new HashMap<String,String>();				
	    	map.putAll(jsonObj);				
			MySQLRelevant mysqlID = new MySQLRelevant();
			mysqlID.connectionPlayerGameID();
			if(mysqlID.selectPlayerGameUserNamePassWordID(map)){
				PD = true;
			}else{
				PD = false;
			}
			mysqlID.deconnectionSQL();
			
	        //*********************************************************发送数据到客户端  	       
	        byte[] writeBuffer = null;
	        if(PD == true){
		        writeBuffer = "登录成功".getBytes();
	        }else{
	        	writeBuffer = "登录失败".getBytes();
	        }
	        HttpConn.sendResponseHeaders(HttpURLConnection.HTTP_OK, writeBuffer.length); 
	        OutputStream out = HttpConn.getResponseBody();
	        out.write(writeBuffer);
	        out.flush();  
	        out.close();
	        
	        //*********************************************************关闭http	        
	        HttpConn.close();         
	    } //handle函数    	
	}//DyanHttpHandler类

	/**
	 * 修改
	 */
	class updataHttpHandler implements HttpHandler
	{
	    @Override
	    public void handle(HttpExchange HttpConn) throws IOException
	    {
	    	boolean PD = false;       
	        //*********************************************************读取内容
	        String readSTR = null;								
	        byte[] readBuffer = new byte[1024];
	        int readLength = 0; 
	        InputStream in = HttpConn.getRequestBody(); 
	        while ((readLength = in.read(readBuffer)) != -1) {
	        	readSTR = new String(readBuffer,0,readLength);
			}
	        in.close();
	        
	        //*********************************************************根据读取的内容，决定发送的内容   	        	        							
	        JSONObject jsonObj  = JSONObject.fromObject(readSTR);
	    	Map<String, String> map = new HashMap<String,String>();				
	    	map.putAll(jsonObj);				
			MySQLRelevant mysqlID = new MySQLRelevant();
			mysqlID.connectionPlayerGameID();
			if(mysqlID.selectPlayerGameUserNamePassWordID(map)){
				if(mysqlID.updataPlayerGameID(map)){
					PD = true;
				}
			}else{
				PD = false;
			}
			mysqlID.deconnectionSQL();
			
	        //*********************************************************发送数据到客户端  	       
	        byte[] writeBuffer = null;
	        if(PD == true){
		        writeBuffer = "修改成功".getBytes();
	        }else{
	        	writeBuffer = "修改失败".getBytes();
	        }
	        HttpConn.sendResponseHeaders(HttpURLConnection.HTTP_OK, writeBuffer.length); 
	        OutputStream out = HttpConn.getResponseBody();
	        out.write(writeBuffer);
	        out.flush(); 
	        out.close();
	        //*********************************************************关闭http
	        HttpConn.close();         
	    } //handle函数    	
	}//DyanHttpHandler类

}
