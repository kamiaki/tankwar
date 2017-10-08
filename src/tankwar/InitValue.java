package tankwar;
/**
 * 常量类
 *
 */
public interface InitValue {
	public static final int WindowsXlength = 1600;		//窗口长
	public static final int WindowsYlength = 900;		//窗口宽
	public static final int PanelX = -5,PanelY = -5;	//游戏面板位置
	
	public static final int type_player = 0;			//玩家类别
	public static final int type_enemy = 1;				//敌人类别
	public static final int type_boss = 2;				//boss类别
	
	public static final int Misslie_putong = 0;			//普通炮
	public static final int Misslie_bafang = 1;			//八方炮
	public static final int Misslie_zhuizong = 2;		//追踪炮
	
	public static final int Misslie_putongX = 20;		//普通炮
	public static final int Misslie_bafangX = 100;		//八方炮
	public static final int Misslie_zhuizongX = 30;		//追踪炮
}
