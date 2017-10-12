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
	//ÌùÍ¼
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();		//¹¤¾ß°ü
	private static Image BackGround;									//±³¾°Í¼Æ¬
	static{
		/**
		 * »­±³¾°
		 */	
		BackGround = toolkit.getImage(Background.class.getClassLoader().getResource("images/±³¾°2.png"));		//±³¾°Í¼Æ¬
		BackGround = BackGround.getScaledInstance(WindowsXlength + PanelX * (-2), WindowsYlength + PanelY * (-2), Image.SCALE_DEFAULT);
	}

	/**
	 * ¹¹Ôìº¯Êý
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param tc
	 */
	public Background(int x, int y, PlayerClient tc) {
		this.X = x;
		this.Y = y;
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
		g.drawImage(BackGround, X, Y, null);
	}
}
