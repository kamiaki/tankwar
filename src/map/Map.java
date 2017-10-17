package map;

import java.util.ArrayList;

import player.Explode;
import player.Item;
import player.Missile;
import player.Player;
import playerClient.PlayerClient;

public class Map implements playerClient.InitValue{
	private playerClient.PlayerClient playerClient;
	/**
	 * 构造函数
	 * @param playerClient
	 */
	public Map(playerClient.PlayerClient playerClient) {
		this.playerClient = playerClient;
	}
	/**
	 * 关闭 地图 方法
	 */
	public void MapOff(){
		//清除背景 
		if(playerClient.background != null){
			playerClient.background = null;
		}
		//清除墙
		if(playerClient.walls != null){
			for(int i = 0; i < playerClient.walls.size(); i++){
				playerClient.walls.remove(i);
			}
			playerClient.walls.clear();
			playerClient.walls = null;
		}	
		//清除门
		if(playerClient.doors != null){
			for(int i = 0; i < playerClient.doors.size(); i++){
				playerClient.doors.remove(i);
			}
			playerClient.doors.clear();
			playerClient.doors = null;
		}	
		//清除物品
		if(playerClient.Items != null){
			playerClient.CreateItemPD = false;
			for(int i = 0; i < playerClient.Items.size(); i++){
				playerClient.Items.get(i).setLive(false);
				playerClient.Items.remove(i);
				i--;
			}
			playerClient.Items.clear();
			playerClient.Items = null;
		}
		//清除敌人坦克
		if(playerClient.enemyPlayers != null){
			playerClient.CreateEnemyPlayersPD = false;
			for(int i = 0; i < playerClient.enemyPlayers.size(); i++){
				playerClient.enemyPlayers.get(i).setPlayerLive(false);
				playerClient.enemyPlayers.remove(i);
				i--;
			}
			playerClient.enemyPlayers.clear();
			playerClient.enemyPlayers = null;
		}
		//清除子弹
		if(playerClient.missiles != null){
			for(int i = 0; i < playerClient.missiles.size(); i++){
				playerClient.missiles.get(i).setLive(false);
				playerClient.missiles.remove(i);
				i--;
			}
			playerClient.missiles.clear();
			playerClient.missiles = null;
			playerClient.missiles = new ArrayList<Missile>();		
		}
		//清除爆炸
		if(playerClient.explodes != null){
			for(int i = 0; i < playerClient.explodes.size(); i++){
				playerClient.explodes.get(i).setLive(false);
				playerClient.explodes.remove(i);
				i--;
			}
			playerClient.explodes.clear();
			playerClient.explodes = null;
			playerClient.explodes = new ArrayList<Explode>();
		}
	}
	
	/**
	 * 创建森林1
	 */
	public void CreateWoods1(){
		//设置玩家位置
		playerClient.myPlayer.setX(1400);
		playerClient.myPlayer.setY(400);
		//加载背景
		if(playerClient.background == null)playerClient.background = new Background(0, 0, 1,playerClient);
		//加载墙
		if(playerClient.walls == null)playerClient.walls = new ArrayList<Wall>();
		playerClient.walls.add(new Wall(100, 100, 100, 50, playerClient));
		playerClient.walls.add(new Wall(300, 300, 50, 100, playerClient));
		//加载门
		if(playerClient.doors == null)playerClient.doors = new ArrayList<Door>();
		playerClient.doors.add(new Door(1500, 450, Door_city,playerClient));
		//加载物品
		if(playerClient.Items == null)playerClient.Items = new ArrayList<Item>();
		playerClient.CreateItemPD = true;
		new Thread(playerClient.new CreateItem()).start();
		//加载敌人坦克
		if(playerClient.enemyPlayers == null)playerClient.enemyPlayers = new ArrayList<Player>();
		playerClient.CreateEnemyPlayersPD = true;
		new Thread(playerClient.new CreateEnemyPlayer()).start();
		//加载子弹
		if(playerClient.missiles == null)playerClient.missiles = new ArrayList<Missile>();
		//加载爆炸
		if(playerClient.explodes == null)playerClient.explodes = new ArrayList<Explode>();
	}
	/**
	 * 创建城市
	 */
	public void CreateCity(){
		//设置玩家位置
		playerClient.myPlayer.setX(1400);
		playerClient.myPlayer.setY(400);
		//加载背景
		if(playerClient.background == null)playerClient.background = new Background(0, 0, 2,playerClient);
		//加载墙
		if(playerClient.walls == null)playerClient.walls = new ArrayList<Wall>();
		playerClient.walls.add(new Wall(500, 100, 100, 50, playerClient));
		playerClient.walls.add(new Wall(200, 200, 50, 100, playerClient));
		//加载门
		if(playerClient.doors == null)playerClient.doors = new ArrayList<Door>();
		playerClient.doors.add(new Door(0, 450, Door_woods,playerClient));
		//加载子弹
		if(playerClient.missiles == null)playerClient.missiles = new ArrayList<Missile>();
		//加载爆炸
		if(playerClient.explodes == null)playerClient.explodes = new ArrayList<Explode>();
	}
}
