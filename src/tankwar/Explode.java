package tankwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
/**
 * ±¬Õ¨Àà
 *
 */
public class Explode {
	private TankClient tankClient;		//´ó¹Ü¼ÒÖ¸Õë
	private int X, Y;					//±¬Õ¨ X Y ×ø±ê
	private boolean live = true;		//±¬Õ¨ÊÇ·ñ´æ»î
	private int x1,y1,x2,y2;			//½ØÈ¡Í¼Æ¬Î»ÖÃ
	//ÌùÍ¼
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();			//¹¤¾ß°ü								
	private static Image ExplodePicture1 = toolkit.getImage(Explode.class.getClassLoader().getResource("images/±¬Õ¨.png"));	//±³¾°Í¼Æ¬
	private static int ExplodeXY1 = 50; 
	static{
		ExplodePicture1 = ExplodePicture1.getScaledInstance(ExplodeXY1 * 4, ExplodeXY1 * 4, Image.SCALE_DEFAULT);
	}
	
	/**
	 * ±¬Õ¨ÊÇ·ñ´æ»î
	 * @return
	 */
	public boolean isLive() {
		return live;
	}
	/**
	 * ÉèÖÃ±¬Õ¨ËÀ»î
	 * @param live
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * ¹¹Ôìº¯Êý
	 * @param x
	 * @param y
	 * @param tc
	 */
	public Explode(int x,int y,TankClient tc){
		this.X = x;
		this.Y = y;
		this.tankClient = tc;
		BaoZhaQD1();
	}
	/**
	 * »­±¬Õ¨
	 * @param g
	 */
	public void draw(Graphics g){	
		if(live) {
			ExplodePicture1(g);
		}
	}
	/**
	 * »­±¬Õ¨Í¼ ÇÐÍ¼Æ¬
	 * @param g
	 */
	private void ExplodePicture1(Graphics g) {
		g.drawImage(ExplodePicture1, X, Y, X + ExplodeXY1, Y + ExplodeXY1, x1, y1, x2, y2, null);
	}
	/**
	 * Æô¶¯±¬Õ¨Ïß³Ì ÇÐÍ¼Æ¬
	 */
	private void BaoZhaQD1(){
		new Thread(new Runnable() {
			public void run() {
				if(live){				
					ExplodeXC1();
				}
			}
		}).start();
	}
	/**
	 * ±¬Õ¨ÌùÍ¼Êý¾Ý¸üÐÂ ÇÐÍ¼Æ¬
	 */
	private void ExplodeXC1(){			
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; i++){
				x1 = j * ExplodeXY1;
				y1 = i * ExplodeXY1;
				x2 = (j + 1) * ExplodeXY1;
				y2 = (i + 1) * ExplodeXY1;
				try {Thread.sleep(100);} catch (Exception e) {}
			}
		}
		live = false;
		tankClient.explodes.remove(Explode.this);
	}
}
