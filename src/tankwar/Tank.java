package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank implements InitValue{
	
	private int X, Y, xspeed = 1, yspeed = 1;	
	private TankClient tankClient = null;
	public static final int tankX = 30, tankY = 30;
	private boolean Up = false, Down = false, Left = false, Right = false;
	private Direction FangXiang = Direction.d5;
	private Direction ptDir = Direction.d4;
	private boolean Good;
	private Color tankColor;
	private boolean tankLive = true;
	
	/**
	 * 判断是好是坏
	 * @return
	 */
	public boolean isGood() {
		return Good;
	}
	/**
	 * 设置是好是坏
	 * @param good
	 */
	public void setGood(boolean good) {
		Good = good;
	}
	/**
	 * 判断死活
	 * @return
	 */
	public boolean isTankLive() {
		return tankLive;
	}
	/**
	 * 设置死活
	 * @param tankLive
	 */
	public void setTankLive(boolean tankLive) {
		this.tankLive = tankLive;
	}	
	/**
	 * 坦克x位置
	 * @return
	 */
	public int getY() {
		return Y;
	}
	/**
	 * 坦克Y位置
	 * @return
	 */
	public int getX() {
		return X;
	}
	/**
	 * 坦克构造函数
	 * @param x
	 * @param y
	 * @param good
	 * @param Co
	 * @param w
	 */
	public Tank(int x, int y, boolean good, Color Co, TankClient w){
		this.X = x;
		this.Y = y;
		this.Good = good;
		this.tankColor = Co;
		this.tankClient = w;
		this.tankLive = true;
		TankQD();
	}
	/**
	 * 画坦克
	 * @param g
	 */
	public void draw(Graphics g){	
		Color c = g.getColor();
		g.setColor(tankColor);
		g.fillOval(X, Y,tankX, tankY);
		g.setColor(c);
		paotong(g);
		
	}
	/**
	 * 画炮筒
	 * @param g
	 */
	private void paotong(Graphics g){
		Color c = g.getColor();
		int shenchu = 3;
		g.setColor(Color.BLACK);
		switch (ptDir) {
		case d4:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X - shenchu, Y + Tank.tankY/2);
			break;
		case d7:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X, Y);
			break;
		case d8:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X + Tank.tankX/2, Y - shenchu);
			break;
		case d9:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X + Tank.tankX, Y);
			break;
		case d6:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X + Tank.tankX + shenchu, Y + Tank.tankY/2);
			break;
		case d3:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X + Tank.tankX, Y + Tank.tankY);
			break;
		case d2:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X + Tank.tankX/2, Y + Tank.tankY + shenchu);
			break;
		case d1:
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X, Y + Tank.tankY);
			break;
		default:
			break;
		}
		g.setColor(c);
	}
	/**
	 * 启动坦克线程
	 */
	private void TankQD(){
		new Thread(new Runnable() {
			public void run() {	
				while(tankLive){
					move();					
					try {Thread.sleep(10);} catch (Exception e) {}
				}	
			}
		}).start();
	}
	/**
	 * 坦克移动
	 */
	private void move() {
		switch (FangXiang) {
		case d4:
			X = X - xspeed;
			break;
		case d7:
			X = X - xspeed;
			Y = Y - yspeed;
			break;
		case d8:
			Y = Y - yspeed;
			break;
		case d9:
			X = X + xspeed;
			Y = Y - yspeed;
			break;
		case d6:
			X = X + xspeed;
			break;
		case d3:
			X = X + xspeed;
			Y = Y + yspeed;
			break;
		case d2:
			Y = Y + yspeed;
			break;
		case d1:
			X = X - xspeed;
			Y = Y + yspeed;
			break;
		case d5:
			break;
		default:
			break;
		}	
		//子弹初始方向
		if( FangXiang != Direction.d5 ) this.ptDir = this.FangXiang;
		//坦克不能出界
		if(X < 0) X = 0;
		if(Y < 0) Y = 0;
		if(X + Tank.tankX > WindowsXlength) X = WindowsXlength - Tank.tankX;
		if(Y + Tank.tankY + 30 > WindowsYlength) Y = WindowsYlength - Tank.tankY - 30;
	}
	/**
	 * 按下按键
	 * @param e
	 */
	public void KEY(KeyEvent e){
		int Key = e.getKeyCode();
		switch (Key) {
		case KeyEvent.VK_W:
			Up = true;
			break;
		case KeyEvent.VK_S:
			Down = true;
			break;
		case KeyEvent.VK_A:
			Left = true;
			break;
		case KeyEvent.VK_D:
			Right = true;
			break;
		case KeyEvent.VK_O:
			xspeed++;
			yspeed++;
			break;
		case KeyEvent.VK_P:
			xspeed--;
			yspeed--;
			break;
		default:
			break;
		}
		PDFangXiang();
	}
	/**
	 * 抬起按键
	 * @param e
	 */
	public void noKEY(KeyEvent e){
		int Key = e.getKeyCode();
		switch (Key) {
		case KeyEvent.VK_NUMPAD0:
			fire();
			break;
		case KeyEvent.VK_W:
			Up = false;
			break;
		case KeyEvent.VK_S:
			Down = false;
			break;
		case KeyEvent.VK_A:
			Left = false;
			break;
		case KeyEvent.VK_D:
			Right = false;
			break;
		default:
			break;
		}
		PDFangXiang();
	}	
	/**
	 * 判断方向
	 */
	private void PDFangXiang(){
		if(!Up && !Down && Left && !Right)FangXiang = Direction.d4;
		else if(Up && !Down && Left && !Right)FangXiang = Direction.d7;
		else if(Up && !Down && !Left && !Right)FangXiang = Direction.d8;
		else if(Up && !Down && !Left && Right)FangXiang = Direction.d9;
		else if(!Up && !Down && !Left && Right)FangXiang = Direction.d6;
		else if(!Up && Down && !Left && Right)FangXiang = Direction.d3;
		else if(!Up && Down && !Left && !Right)FangXiang = Direction.d2;
		else if(!Up && Down && Left && !Right)FangXiang = Direction.d1;
		else if(!Up && !Down && Left && Right)FangXiang = Direction.d5;
		else if(Up && Down && !Left && !Right)FangXiang = Direction.d5;
		else if(!Up && !Down && !Left && !Right)FangXiang = Direction.d5;
		else if(Up && Down && Left && Right)FangXiang = Direction.d5;
	}
	/**
	 * 生成一个子弹
	 * @return
	 */
	public void fire(){
		if(tankClient != null){
			int x = this.X + Tank.tankX/2 - Missile.missileX/2;
			int y = this.Y + Tank.tankY/2 - Missile.missileY/2;
			Missile missile = new Missile(x, y, ptDir,tankClient);
			tankClient.missiles.add(missile);	
		}
	}
	
	/**
	 * 获取坦克的矩形
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(X, Y, tankX, tankY);
	}
}
