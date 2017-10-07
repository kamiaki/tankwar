package tankwar;

import java.awt.Color;
import java.awt.Graphics;
/**
 * ±¨’®¿‡
 *
 */
public class Explode {
	private TankClient tankClient;		//¥Ûπ‹º“÷∏’Î
	private int X, Y;					//±¨’® X Y ◊¯±Í
	private int diameter = 0;			//±¨’®÷±æ∂
	private int diameterMAX = 20;		//±¨’®◊Ó¥Û÷±æ∂
	private boolean live = true;		//±¨’® «∑Ò¥ÊªÓ
	
	/**
	 * ±¨’® «∑Ò¥ÊªÓ
	 * @return
	 */
	public boolean isLive() {
		return live;
	}
	/**
	 * …Ë÷√±¨’®À¿ªÓ
	 * @param live
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * ππ‘Ï∫Ø ˝
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
	 * ª≠±¨’®
	 * @param g
	 */
	public void draw(Graphics g){	
		if(live) {
			ExplodePicture(g);
		}
	}
	/**
	 * ª≠±¨’®Õº
	 * @param g
	 */
	private void ExplodePicture(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(X, Y, diameter, diameter);
		g.setColor(c);
	}
	/**
	 * ∆Ù∂Ø±¨’®œﬂ≥Ã
	 */
	private void BaoZhaQD(){
		new Thread(new Runnable() {
			public void run() {
				while(live){				
					ExplodePicture();
					try {Thread.sleep(10);} catch (Exception e) {}
				}
			}
		}).start();
	}
	/**
	 * ±¨’®Ã˘Õº
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
		live = false;
		tankClient.explodes.remove(Explode.this);
	}
}
