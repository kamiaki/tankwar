package tankwar;

import java.awt.Color;
import java.awt.Graphics;
/**
 * 背景类
 *
 */
public class Background {
	private TankClient tankClient;			//大管家指针
	private int X,Y,width,height;			//背景的位置和长宽
	/**
	 * 构造函数
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param tc
	 */
	public Background(int x, int y, int width, int height, TankClient tc) {
		this.X = x;
		this.Y = y;
		this.width = width;
		this.height = height;
		tankClient = tc;
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
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillRect(X, Y, width, height);
		g.setColor(c);
	}
}
