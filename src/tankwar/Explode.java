package tankwar;

import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	private TankClient tankClient;		//大管家指针
	private int X, Y;					//爆炸 X Y 坐标
	private int diameter = 0;			//爆炸直径
	private int diameterMAX = 20;		//爆炸最大直径
	private boolean live = true;		//爆炸是否存活
	
	/**
	 * 爆炸是否存活
	 * @return
	 */
	public boolean isLive() {
		return live;
	}
	/**
	 * 设置爆炸死活
	 * @param live
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * 构造函数
	 * @param x
	 * @param y
	 * @param tc
	 */
	public Explode(int x,int y,TankClient tc){
		this.X = x;
		this.Y = y;
		this.tankClient = tc;
		BaoZhaQD();
	}
	/**
	 * 画爆炸
	 * @param g
	 */
	public void draw(Graphics g){		
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(X, Y, diameter, diameter);
		g.setColor(c);
	}
	/**
	 * 启动爆炸线程
	 */
	private void BaoZhaQD(){
		new Thread(new Runnable() {
			public void run() {
				while(live){				
					ExplodePicture();
				}
			}
		}).start();
	}
	/**
	 * 爆炸贴图
	 */
	private void ExplodePicture(){
		for(int i = 1; i < diameterMAX; i += 5){
			diameter = i;
			try {Thread.sleep(20);} catch (Exception e) {}
		}
		for(int i = diameterMAX; i > 0; i -= 10){
			diameter = i;
			try {Thread.sleep(20);} catch (Exception e) {}
		}
		tankClient.explodes.remove(Explode.this);
		live = false;
	}
}
