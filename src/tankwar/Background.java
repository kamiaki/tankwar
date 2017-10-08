package tankwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
/**
 * 背景类
 *
 */
public class Background implements InitValue{
	private TankClient tankClient;										//大管家指针
	private int X,Y;													//背景的位置和长宽
	private String Path;												//地址
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();		//工具包
	private Image BackGround;											//背景图片
	
	/**
	 * 构造函数
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param tc
	 */
	public Background(int x, int y, String path, TankClient tc) {
		this.X = x;
		this.Y = y;
		this.Path = path;
		tankClient = tc;
		HuaWall();
	}
	/**
	 * 画背景
	 * @param g
	 */
	public void draw(Graphics g){
		BackgroundPicture(g);
	}
	/**
	 * 背景贴图
	 * @param g
	 */
	private void BackgroundPicture(Graphics g){
		g.drawImage(BackGround, X, Y, null);
	}
	/**
	 * 画背景
	 */
	public void HuaWall(){			
		BackGround = toolkit.getImage(Explode.class.getClassLoader().getResource(Path));		//背景图片
		BackGround = BackGround.getScaledInstance(WindowsXlength + PanelX * (-2), WindowsYlength + PanelY * (-2), Image.SCALE_DEFAULT);
	}
}
