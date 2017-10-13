package playerwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
/**
 * ±³¾°Àà
 *
 */
public class Background implements InitValue{
	private PlayerClient tankClient;										//´ó¹Ü¼ÒÖ¸Õë
	private int X,Y;													//±³¾°µÄÎ»ÖÃºÍ³¤¿í
	private int BeiJingID;												//±³¾°ID
	//ÌùÍ¼
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();		//¹¤¾ß°ü
	private static Image BackGround1;									//±³¾°Í¼Æ¬
	private static Image BackGround2;									//±³¾°Í¼Æ¬
	static{
		/**
		 * »­±³¾°
		 */	
		BackGround1 = toolkit.getImage(Background.class.getClassLoader().getResource("images/±³¾°2.png"));		//±³¾°Í¼Æ¬
		BackGround1 = BackGround1.getScaledInstance(WindowsXlength + PanelX * (-2), WindowsYlength + PanelY * (-2), Image.SCALE_DEFAULT);
		BackGround2 = toolkit.getImage(Background.class.getClassLoader().getResource("images/±³¾°1.jpg"));		//±³¾°Í¼Æ¬
		BackGround2 = BackGround2.getScaledInstance(WindowsXlength + PanelX * (-2), WindowsYlength + PanelY * (-2), Image.SCALE_DEFAULT);
	}

	/**
	 * ¹¹Ôìº¯Êý
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param tc
	 */
	public Background(int x, int y,int beijingid, PlayerClient tc) {
		this.X = x;
		this.Y = y;
		this.BeiJingID = beijingid;
		tankClient = tc;
	}
	/**
	 * »­±³¾°
	 * @param g
	 */
	public void draw(Graphics g){
		BackgroundPicture(g);
	}
	/**
	 * ±³¾°ÌùÍ¼
	 * @param g
	 */
	private void BackgroundPicture(Graphics g){
		switch (BeiJingID) {
		case 1:
			g.drawImage(BackGround1, X, Y, null);
			break;
		case 2:
			g.drawImage(BackGround2, X, Y, null);
			break;
		default:
			g.drawImage(BackGround1, X, Y, null);
			break;
		}
		
	}
}
