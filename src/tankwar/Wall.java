package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
/**
 * 墙类
 *
 */
public class Wall {
	TankClient tankClient;
	private int x,y,w,h;
	private String Path;
	private ImageIcon wallImageIcon; 
	private Image wallPicture;
	
	/**
	 * 构造方法
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param tankClient
	 */
	public Wall(int x, int y, int w, int h, String path, TankClient tankClient) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.Path = path;
		this.tankClient = tankClient;
		HuaWall();
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
		g.drawImage(wallPicture, x, y, null);
	}
	/**
	 * 获取墙的矩形
	 * @return
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
	/**
	 * 画墙壁
	 */
	public void HuaWall(){			
		wallImageIcon = new ImageIcon(Wall.class.getClassLoader().getResource(Path));
		wallPicture = wallImageIcon.getImage();
		wallPicture = wallPicture.getScaledInstance(w, h, Image.SCALE_DEFAULT);
	}
}
