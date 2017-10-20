package nl.dubehh.core.game;

import nl.dubehh.Main;
import nl.dubehh.core.game.timer.GameTimer;
import nl.dubehh.core.module.ModuleController;

public class GameManager {
	
	private static GameManager _instance;
	
	private ModuleController _controller;
	private GameTimer _timer;
	
	private GameManager(){
		if(!Main.getInstance().getDataFolder().exists()) 
			Main.getInstance().getDataFolder().mkdir();
		this._controller = new ModuleController();	
	}
	
	public static GameManager getInstance(){
		if(_instance == null)
			_instance = new GameManager();
		return _instance;
	}
	
	public ModuleController getModuleController(){
		return this._controller;
	}
	
	public GameTimer setTimer(GameTimer timer){
		if(timer != null && this._timer != null)
			throw new IllegalArgumentException("There's already a timer running!");
		this._timer = timer;
		return this._timer;
	}
	
	public GameTimer getCurrentTimer(){
		return this._timer;
	}
}
