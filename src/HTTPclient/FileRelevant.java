package HTTPclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.FileOutputStream;  
import java.io.InputStream; 
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileRelevant {
	final static int OPEN_File = 0;
	final static int SAVE_File = 1;
	final static int FILE = 0;
	final static int FILES = 1;
	final static int FILEandFILES = 2;
	
	//***********************************************************************文件路径保存读取txt
	//将文件路径保存在目录下
	//1 文件保存地址 2 内容
	public static void filepathsave(String path,String NeiRong){
			try {
	            File writename = new File(path); // 相对路径，如果没有则要建立一个新的output。txt文件  
	            writename.createNewFile(); // 创建新文件  
	            BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
	            out.write(NeiRong); // \r\n即为换行  
	            out.flush(); // 把缓存区内容压入文件  
	            out.close(); // 最后记得关闭文件  
			} catch (Exception e) {
				e.printStackTrace();
			}		 
	}
	//将文件路径读取出来
	//1 文件保存地址
	public static String filepathread(String path){
			String filepath = null;
			File file = new File(path);
			try {		 
				if(file.isFile() && file.exists()){
					InputStreamReader read = new InputStreamReader(new FileInputStream(file),"GBK");
					BufferedReader bfreader = new BufferedReader(read);
					filepath = bfreader.readLine();
					bfreader.close();
				}else{
					filepath = "";
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			if(filepath == null)
				filepath = "";
			return filepath;
	}
	//将文件路径读取出来 读指定行数
	//1 文件保存地址 2 读指定的行数
	public static String filepathread(String path,int linenumber){
			String filepath = null;
			File file = new File(path);
			try {		 
				if(file.isFile() && file.exists()){
					InputStreamReader read = new InputStreamReader(new FileInputStream(file),"GBK");
					BufferedReader bfreader = new BufferedReader(read);
					for(int i = 0; i < linenumber; i++){
						filepath = bfreader.readLine();
					}				
					bfreader.close();
				}else{
					filepath = "";
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return filepath;
	}
	
	//***********************************************************************文件路径 保存 打开 对话框
	//文件操作对话框的开启
	//1 选择是保存还是打开 (0打开 1保存)
	public static String fileoperate(int so,int filefiles){
		int filenumber = 0;
		switch (filefiles) {
		case FILE:
			filenumber = JFileChooser.FILES_ONLY;
			break;
		case FILES:
			filenumber = JFileChooser.DIRECTORIES_ONLY;
			break;
		case FILEandFILES:
			filenumber = JFileChooser.FILES_AND_DIRECTORIES;
			break;
		default:
			break;
		}
		String txtpath = "";					//读取文件路径
		String filePath = "";					//返回打开路径，和保存路径
		JFileChooser fileChooser = null;		//文件对话框
		int returnValue = 0; 					//文件路径是否打开判断
		if(so == OPEN_File){
			txtpath = filepathread("openPath.txt");
			if(txtpath.equals(""))
				txtpath = ".\\";
			fileChooser = new JFileChooser(txtpath);
			fileChooser.setFileSelectionMode(filenumber);		//设置选择文件夹
			returnValue = fileChooser.showOpenDialog(fileChooser);					//打开打开对话框
			if(returnValue == JFileChooser.APPROVE_OPTION){ 	
				filePath= fileChooser.getSelectedFile().getAbsolutePath();			//getSelectedFile 返回选中的文件 getAbsolutePath 返回选中的文件的物理地址		
				filepathsave("openPath.txt", filePath);								//将路径写出去
			}
			if(filePath.equals("")){
				JOptionPane.showMessageDialog(null,"没有选择路径！");
			}
		}else{
			txtpath = filepathread("savePath.txt");
			if(txtpath.equals(""))
				txtpath = ".\\";
			fileChooser = new JFileChooser(txtpath);
			fileChooser.setFileSelectionMode(filenumber); 							//设置选择文件和文件夹
			returnValue = fileChooser.showSaveDialog(fileChooser);					//打开保存对话框
			if(returnValue == JFileChooser.APPROVE_OPTION){
				filePath = fileChooser.getSelectedFile().getAbsolutePath();			//getSelectedFile 返回选中的文件 getAbsolutePath 返回选中的文件的物理地址
				filepathsave("savePath.txt", filePath);								//将路径写出去
			}
			if(filePath.equals("")){
				JOptionPane.showMessageDialog(null,"没有选择路径！");
			}
		}
		return filePath;
	}
	
	//多选 文件操作对话框的开启 
	//1 选择是保存还是打开 (0打开 1保存)
	public static File[] MultiFileOperate(int so,int filefiles){
		int filenumber = 0;
		switch (filefiles) {
		case FILE:
			filenumber = JFileChooser.FILES_ONLY;
			break;
		case FILES:
			filenumber = JFileChooser.DIRECTORIES_ONLY;
			break;
		case FILEandFILES:
			filenumber = JFileChooser.FILES_AND_DIRECTORIES;
			break;
		default:
			break;
		}
		String txtpath = "";					//读取文件路径
		File[] files = null;					//返回打开路径，和保存路径
		JFileChooser fileChooser = null;		//文件对话框
		int returnValue = 0; 					//文件路径是否打开判断
		if(so == OPEN_File){
			txtpath = filepathread("openPath.txt");
			if(txtpath.equals(""))
				txtpath = ".\\";
			fileChooser = new JFileChooser(txtpath);
			fileChooser.setFileSelectionMode(filenumber);							//设置选择文件或者文件夹
			fileChooser.setMultiSelectionEnabled(true);
			returnValue = fileChooser.showOpenDialog(fileChooser);					//打开打开对话框
			if(returnValue == JFileChooser.APPROVE_OPTION){ 	
				files= fileChooser.getSelectedFiles();								//getSelectedFile 返回选中的文件 getAbsolutePath 返回选中的文件的物理地址		
				filepathsave("openPath.txt", files[0].getAbsolutePath());			//将路径写出去
			}
			if(files != null){
				if(files[0].getAbsolutePath().equals("")){
					JOptionPane.showMessageDialog(null,"没有选择路径！");
				}
			}else{
				JOptionPane.showMessageDialog(null,"没有选择路径！");
			}		
		}else{
			txtpath = filepathread("savePath.txt");
			if(txtpath.equals(""))
				txtpath = ".\\";
			fileChooser = new JFileChooser(txtpath);
			fileChooser.setFileSelectionMode(filenumber); 							//设置选择文件或者文件夹
			fileChooser.setMultiSelectionEnabled(true);
			returnValue = fileChooser.showSaveDialog(fileChooser);					//打开保存对话框
			if(returnValue == JFileChooser.APPROVE_OPTION){
				files= fileChooser.getSelectedFiles();								//getSelectedFile 返回选中的文件 getAbsolutePath 返回选中的文件的物理地址	
				filepathsave("savePath.txt", files[0].getAbsolutePath());			//将路径写出去
			}
			if(files[0].getAbsolutePath().equals("")){
				JOptionPane.showMessageDialog(null,"没有选择路径！");
			}
		}
		return files;
	}
	//***********************************************************************文件操作 复制 删除 移动
	//文件复制
	//1 原始文件位置 2 复制到的文件位置
	public static void filecopy(String oldPath, String newPath){
		int bytesum = 0;												//文件大小 多少字节数
        int byteread = 0;												//文件读取 字节数
		try{
          File oldfile = new File(oldPath);
          if(oldfile.exists()){											//如果文件存在
              InputStream inStream = new FileInputStream(oldPath);		//读入原文件   
              FileOutputStream  fs  =  new  FileOutputStream(newPath);	//复制到文件位置
              byte[] buffer = new byte[1024];							//一次读多少字节
              while((byteread = inStream.read(buffer)) != -1){    
            	  bytesum += byteread;									//文件总字节数
                  fs.write(buffer, 0, byteread);    
              }    
              inStream.close();   
              fs.close();
          }    
      }    
      catch  (Exception  e)  {    
          e.printStackTrace();    
      }    
	}
	//文件删除
	//1 要删除的文件地址
	public static void filedelete(String Path){
		try  {       
	           File myDelFile = new File(Path);    
	           myDelFile.delete();    
	       }    
	       catch  (Exception  e)  {    
	           e.printStackTrace();       
	       }    
	}
	//文件移动
	public static void filemove(String oldPath, String newPath){
		filecopy(oldPath, newPath);    
		filedelete(oldPath);    
	}
	
	//***********************************************************************文件夹内文件判断和操作
	//读取的文件名放在 链表里
	//1 文件夹性对地址 2 文件夹内文件名组成的 字符串链表 3 文件的全路径
	public static int getAllFileName(String path, List<String> fileName, List<String> fileAllPath){
		int numCount = 0;     
        String [] names;
		File file = new File(path);
        if(file.exists()){
	        names = file.list();
	        if(names != null){											//如果名字数组不为空
		        fileName.addAll(Arrays.asList(names));      			//将一个数组转化为链表 
		        numCount = names.length;    							//获取总文件数        
		        for(int i = 0; i < fileName.size(); i++){				//循环保存文件绝对路径地址
		        	fileAllPath.add(path + "\\" + fileName.get(i));
		        }
	        }
        }
        return numCount;
	}
}
