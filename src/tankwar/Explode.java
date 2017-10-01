package tankwar;

import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	private TankClient TC ;
	int X, Y;
	private boolean live = true;
	private int diameter = 0;
	private int diameterMAX = 20;
	
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
		this.TC =tc;
		BaoZhaQD();
	}
	/**
	 * ª≠±¨’®
	 * @param g
	 */
	public void draw(Graphics g){		
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
					if(!live){
						TC.explodes.remove(Explode.this);
					}
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
	}
}
