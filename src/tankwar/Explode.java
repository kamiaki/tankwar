package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
/**
 * 爆炸类
 *
 */
import java.awt.Toolkit;
public class Explode {
	private TankClient tankClient;		//大管家指针
	private int X, Y;					//爆炸 X Y 坐标
	private boolean live = true;		//爆炸是否存活
	//贴图
	private int step;							//画到了第几步
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();			//工具包
	private static Image[] images = {
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
	}; 							
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
		if(live) {
			ExplodePicture(g);
		}
	}
	/**
	 * 画爆炸图
	 * @param g
	 */
	private void ExplodePicture(Graphics g) {
		g.drawImage(images[step], X, Y, null);
	}
	/**
	 * 启动爆炸线程
	 */
	private void BaoZhaQD(){
		new Thread(new Runnable() {
			public void run() {
				if(live){				
					ExplodeXC();
				}
			}
		}).start();
	}
	/**
	 * 爆炸贴图数据更新
	 */
	private void ExplodeXC(){				
		for(step = 0; step < images.length; step++){
			try {Thread.sleep(10);} catch (Exception e) {}
		}
		live = false;
		tankClient.explodes.remove(Explode.this);
	}
}
