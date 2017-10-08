package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * 墙类
 *
 */
public class Wall {
	TankClient tankClient;
	private int x,y,w,h;
	
	/**
	 * 构造方法
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param tankClient
	 */
	public Wall(int x, int y, int w, int h, TankClient tankClient) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tankClient = tankClient;
	}
	/**
	 * 画墙
	 * @param g
	 */
	public void draw(Graphics g) {
		WallPicture(g);
	}
	/**
	 * 画墙图片
	 * @param g
	 */
	public void WallPicture(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
	/**
	 * 获取墙的矩形
	 * @return
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
}
