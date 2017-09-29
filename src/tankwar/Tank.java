package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank implements InitValue{
	public static final int Tanktype_man = 0;
	public static final int Tanktype_robot = 1;
	public static final int Tanktype_robotAI = 2;
	
	private TankClient tankClient = null;
	
	private int X, Y, xspeed = 1, yspeed = 1;	
	public static final int tankX = 30, tankY = 30;
	private boolean Up = false, Down = false, Left = false, Right = false;
	private Direction FangXiang = Direction.d5;
	private Direction ptDir = RandomEnum();
	private int Tanktype = 0;
	private Color tankColor;
	private boolean tankLive = true;

	public boolean isTankLive() {
		return tankLive;
	}
	public void setTankLive(boolean tankLive) {
		this.tankLive = tankLive;
	}
	
	public int getY() {
		return Y;
	}
	public int getX() {
		return X;
	}
	
	public Tank(int x, int y, int tanktype) {
		this.X = x;
		this.Y = y;
		this.Tanktype = tanktype;
		TankInit();
		TankQD();
	}
	
	public Tank(int x, int y, int tanktype, TankClient w){
		this(x, y, tanktype);
		this.tankClient = w;
	}
	
	/**
	 * 自动开火
	 */
	private void autoFire(){
		new Thread(new Runnable() {
			public void run() {
				Random random = new Random();
				int time = 0;
				while (true) {
					time = random.nextInt(3000);
					try { Thread.sleep(time); } catch (Exception e) {}	
					fire();
				}
			}
		}).start();
	}
	
	/**
	 * 自动行走
	 */
	private void autoMove(){
		new Thread(new Runnable() {
			public void run() {
				Random random = new Random();
				int time = 0;
				int randomI = 0;
				
				while (true) {
					time = random.nextInt(3000);
					try { Thread.sleep(time); } catch (Exception e) {}
					randomI = random.nextInt(9);
					switch (randomI ) {
					case 0:
						Tank.this.FangXiang = Direction.d4;
						break;
					case 1:
						Tank.this.FangXiang = Direction.d7;
						break;
					case 2:
						Tank.this.FangXiang = Direction.d8;
						break;
					case 3:
						Tank.this.FangXiang = Direction.d9;
						break;
					case 4:
						Tank.this.FangXiang = Direction.d6;
						break;
					case 5:
						Tank.this.FangXiang = Direction.d3;
						break;
					case 6:
						Tank.this.FangXiang = Direction.d2;
						break;
					case 7:
						Tank.this.FangXiang = Direction.d1;
						break;
					default:
						Tank.this.FangXiang = Direction.d5;
						break;
					}
				}		
			}
		}).start();
	}
	/**
	 * 坦克初始化
	 */
	private void TankInit(){
		switch (Tanktype) {
		case Tanktype_man:
			tankColor = Color.RED;
			break;
		case Tanktype_robot:
			tankColor = Color.GRAY;
			break;
		case Tanktype_robotAI:
			tankColor = Color.BLUE;
			break;
		default:
			break;
		}
		if(Tanktype != Tanktype_man){
//			autoFire();
			autoMove();
		}
	}
	/**
	 * 随机枚举
	 * @return
	 */
	private Direction RandomEnum(){
		 Direction enums[] = Direction.values();  
	     Random random = new Random();  
	     Direction ed = enums[random.nextInt(enums.length)];  
	     return ed;
	}
	
	public void draw(Graphics g){
		if(!tankLive)return;
		
		Color c = g.getColor();
		g.setColor(tankColor);
		g.fillOval(X, Y,tankX, tankY);
		g.setColor(c);
		paotong(g);
		
	}
	
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
			g.drawLine(X + Tank.tankX/2, Y + Tank.tankY/2, X - shenchu, Y + Tank.tankY/2);
			break;
		}
		g.setColor(c);
	}
	
	private void TankQD(){
		new Thread(new Runnable() {
			public void run() {	
				while(true){
					move();
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}	
			}
		}).start();
	}
	
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
		
		if(FangXiang != Direction.d5){
			this.ptDir = this.FangXiang;
		}
		
		if(X < 0) X = 0;
		if(Y < 0) Y = 0;
		if(X + Tank.tankX > WindowsXlength) X = WindowsXlength - Tank.tankX;
		if(Y + Tank.tankY + 30 > WindowsYlength) Y = WindowsYlength - Tank.tankY - 30;
	}

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
			if(ptDir == Direction.d5) ptDir = Direction.d4;
			Missile missile = new Missile(x, y, ptDir, tankClient);
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
