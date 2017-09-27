package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Tank {
	private int x, y, speed = 1;
	private boolean Up = false, Down = false, Left = false, Right = false;
	private enum direction {d4,d7,d8,d9,d6,d3,d2,d1,d5};
	private direction FangXiang = direction.d5;
	
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
		QD();
	}
	
	public void draw(Graphics g){
		
		g.setColor(Color.RED);
		g.fillOval(x, y,30, 30);	
	}
	
	private void QD(){
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
			x = x - speed;
			break;
		case d7:
			x = x - speed;
			y = y - speed;
			break;
		case d8:
			y = y - speed;
			break;
		case d9:
			x = x + speed;
			y = y - speed;
			break;
		case d6:
			x = x + speed;
			break;
		case d3:
			x = x + speed;
			y = y + speed;
			break;
		case d2:
			y = y + speed;
			break;
		case d1:
			x = x - speed;
			y = y + speed;
			break;
		case d5:
			break;
		default:
			break;
		}
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
			speed++;
			break;
		case KeyEvent.VK_P:
			speed--;
			break;
		default:
			break;
		}
		PDFangXiang();
	}
	
	public void noKEY(KeyEvent e){
		int Key = e.getKeyCode();
		switch (Key) {
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
		if(!Up && !Down && Left && !Right)FangXiang = direction.d4;
		else if(Up && !Down && Left && !Right)FangXiang = direction.d7;
		else if(Up && !Down && !Left && !Right)FangXiang = direction.d8;
		else if(Up && !Down && !Left && Right)FangXiang = direction.d9;
		else if(!Up && !Down && !Left && Right)FangXiang = direction.d6;
		else if(!Up && Down && !Left && Right)FangXiang = direction.d3;
		else if(!Up && Down && !Left && !Right)FangXiang = direction.d2;
		else if(!Up && Down && Left && !Right)FangXiang = direction.d1;
		else if(!Up && !Down && Left && Right)FangXiang = direction.d5;
		else if(Up && Down && !Left && !Right)FangXiang = direction.d5;
		else if(!Up && !Down && !Left && !Right)FangXiang = direction.d5;
		else if(Up && Down && Left && Right)FangXiang = direction.d5;
	}
}
