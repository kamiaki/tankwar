package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

/**
 * 物品类
 */
public class Item {
	private TankClient tankClient;				//大管家类
	private int x, y, w, h;						//x y坐标 何种物品
	private ItemsType itemsType; 				//物品种类
	private int[][] GuiJi = new int[3][2];		//随机晃动位置
	private boolean live;						//物品生命
	int step = 0;								//物品小范围移动到了那一步
	
	/**
	 * 获取物品种类
	 * @return
	 */
	public ItemsType getItemsType() {
		return itemsType;
	}
	/**
	 * 设置物品种类
	 * @param itemsType
	 */
	public void setItemsType(ItemsType itemsType) {
		this.itemsType = itemsType;
	}
	/**
	 * 获取物品生命
	 * @return
	 */
	public boolean isLive() {
		return live;
	}
	/**
	 * 设置物品生命
	 * @param live
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * 构造函数
	 * @param x
	 * @param y
	 * @param Item_type
	 * @param tankClient
	 */
	public Item(int x, int y, int w, int h,ItemsType Item_type,TankClient tankClient) {
		this.live = true;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.itemsType = Item_type;
		this.tankClient = tankClient;		
		GuiJi[0][0] = x;
		GuiJi[0][1] = y;
		GuiJi[1][0] = x + 2;
		GuiJi[1][1] = y;
		GuiJi[2][0] = x + 1;
		GuiJi[2][1] = y + 2;		
		ItemQD();
	}	
	/**
	 * 画物品
	 * @param g
	 */
	public void draw(Graphics g) {
		if(live) {
			ItemPicture(g);
		}
	}
	/**
	 * 画物品图片
	 * @param g
	 */
	private void ItemPicture(Graphics g) {
		Color c = g.getColor();
		switch (itemsType) {
		case Blood:
			g.setColor(Color.RED);
			break;
		case WeaponBaFang:
			g.setColor(Color.BLUE);
			break;		
		case WeaponZhuiZong:
			g.setColor(Color.MAGENTA);
			break;
		default:
			g.setColor(Color.BLACK);
			break;
		}
		g.fillOval(x, y, w, h);
		g.setColor(c);
	}
	/**
	 * 碰撞检测矩形
	 * @return
	 */
	public Rectangle getRect() {
		return new Rectangle(x,y,w,h);
	}
	/**
	 * 启动物品数据更新程序
	 */
	private void ItemQD(){
		ItemXianCheng();
	}
	/**
	 * 物品线程
	 */
	private void ItemXianCheng() {
		new Thread(new Runnable() {
			public void run() {
				while(live) {			
					move();
					try {Thread.sleep(10);} catch (Exception e) {}	
				}			
			}
		}).start();
	}
	//物品随机晃动
	private void move() {
		step++;
		if(step == GuiJi.length) {
			step = 0;
		}
		x = GuiJi[step][0];
		y = GuiJi[step][1];
	}
	/**
	 * 物品撞墙
	 * @param w
	 * @return
	 */
	public boolean ZhuangWall(Wall w) {
		if(this.live && Item.this.getRect().intersects(w.getRect())) {
			return true;
		}
		return false;
	}
	/**
	 * 物品撞链表墙
	 * @param w
	 * @return
	 */
	public boolean ZhuangWalls(List<Wall> walls) {
		for(int i = 0; i < walls.size(); i++) {
			if(ZhuangWall(walls.get(i))){
				return true;
			}
		}
		return false;
	}
}
