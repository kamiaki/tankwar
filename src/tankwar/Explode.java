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
	private static Image ExplodePicture2 = toolkit.getImage(Explode.class.getClassLoader().getResource("images/±¬Õ¨2.png"));	//±³¾°Í¼Æ¬
	private static int ExplodeXY1 = 50; 
	private static int ExplodeXY2 = 100;
	static{
		ExplodePicture1 = ExplodePicture1.getScaledInstance(ExplodeXY1 * 4, ExplodeXY1 * 4, Image.SCALE_DEFAULT);
		ExplodePicture2 = ExplodePicture2.getScaledInstance(ExplodeXY2 * 8, ExplodeXY2    , Image.SCALE_DEFAULT);
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
//		BaoZhaQD1();
		BaoZhaQD2();
	}
	/**
	 * »­±¬Õ¨
	 * @param g
	 */
	public void draw(Graphics g){	
		if(live) {
//			ExplodePicture1(g);
			ExplodePicture2(g);
		}
	}
	//*********************************************************************************±¬Õ¨1
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
			for(int j = 0; j < 4; j++){
				x1 = j * ExplodeXY1;
				y1 = i * ExplodeXY1;
				x2 = (j + 1) * ExplodeXY1;
				y2 = (i + 1) * ExplodeXY1;
				try {Thread.sleep(30);} catch (Exception e) {}
			}
		}
		live = false;
		tankClient.explodes.remove(Explode.this);
	}	
	//*********************************************************************************±¬Õ¨2
	/**
	 * »­±¬Õ¨Í¼ ÇÐÍ¼Æ¬
	 * @param g
	 */
	private void ExplodePicture2(Graphics g) {
		g.drawImage(ExplodePicture2, X, Y, X + ExplodeXY2, Y + ExplodeXY2, x1, y1, x2, y2, null);
	}
	/**
	 * Æô¶¯±¬Õ¨Ïß³Ì ÇÐÍ¼Æ¬
	 */
	private void BaoZhaQD2(){
		new Thread(new Runnable() {
			public void run() {
				if(live){				
					ExplodeXC2();
				}
			}
		}).start();
	}
	/**
	 * ±¬Õ¨ÌùÍ¼Êý¾Ý¸üÐÂ ÇÐÍ¼Æ¬
	 */
	private void ExplodeXC2(){			
		for(int i = 0; i < 1; i++){
			for(int j = 0; j < 8; j++){
				x1 = j * ExplodeXY2;
				y1 = i * ExplodeXY2;
				x2 = (j + 1) * ExplodeXY2;
				y2 = (i + 1) * ExplodeXY2;
				try {Thread.sleep(30);} catch (Exception e) {}
			}
		live = false;
		tankClient.explodes.remove(Explode.this);
		}
	}
}
