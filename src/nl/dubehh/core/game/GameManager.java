package nl.dubehh.core.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import nl.dubehh.Main;
import nl.dubehh.core.data.DataFile;
import nl.dubehh.core.game.timer.GameTimer;
import nl.dubehh.core.module.Module;
import nl.dubehh.core.module.ModuleConfiguration;
import nl.dubehh.core.module.ModuleController;
import nl.dubehh.core.module.ModuleSetting;
import nl.dubehh.core.module.construction.IModuleUserJoin;
import nl.dubehh.core.module.construction.IModuleUserLeave;
import nl.dubehh.core.user.User;
import nl.dubehh.core.user.UserManager;
import nl.dubehh.util.UConfig;

public class GameManager {
	
	private static GameManager _instance;
	private static final String SETTINGS_FILE = "settings";
	
	private DataFile _settings;
	private boolean _debugging;
	private GameQueue _queue;
	private GameState _state;
	private ModuleController _controller;
	private GameTimer _timer;
	
	private GameManager(){
		if(!Main.getInstance().getDataFolder().exists()) 
			Main.getInstance().getDataFolder().mkdir();
		this._settings = new DataFile(SETTINGS_FILE);
		this._debugging = false;
		this._state = GameState.WAITING;
		this._controller = new ModuleController(this);	
	}
	
	public Location getGameSpawn(){
		return _settings.getConfig().contains("spawn") ? 
				UConfig.unserialize(_settings.getConfig().getString("spawn")) : null;
	}
	
	public void setState(GameState state){
		this._state = state;
	}
	
	public void setDebugging(boolean debugging){
		_debugging = debugging;
	}
	
	public DataFile getSettings(){
		return this._settings;
	}
	
	public boolean isDebugging(){
		return _debugging;
	}
	
	public static GameManager getInstance(){
		if(_instance == null)
			_instance = new GameManager();
		return _instance;
	}
	
	public ModuleController getModuleController(){
		return this._controller;
	}
	
	public void loadQueue(){
		this._queue = new GameQueue(this, _controller.getModules());
	}
	
	public GameQueue getQueue(){
		return this._queue;
	}
	
	public void startTimer(GameTimer timer){
		if(this._timer != null && _timer.isRunning())
			throw new IllegalArgumentException("There's already a timer running!");
		this._timer = timer;
		this._timer.start();
	}
	
	public GameTimer getCurrentTimer(){
		return this._timer;
	}
	
	public void prepareEnd(){
		if(_timer.isRunning()) 
			_timer.cancel(true);
		startTimer(new GameTimer(0, 5).setTimerEndEvent(()-> {  end(); }));
	}
	
	private void end(){
		Module module = _controller.getCurrent();
		if(module!=null){
			_controller.unload(module);
			UserManager.getInstance().forEach((user)->{
				if(!user.isIngame()) return;
				user.getPlayer().teleport(getGameSpawn());
				user.setIngame(false);
				user.setQueued(true);
				if(module instanceof IModuleUserLeave)
					((IModuleUserLeave) module).onUserLeave(user, false);
				if(user.getDataController().isDataSource()){
					user.getDataController().save(()->{
						user.setDataController(null);
					});
				}
			});
			setState(GameState.WAITING);
		}
	}
	
	public GameState getState(){
		return this._state;
	}
	
	private void start(Module module){
		setState(GameState.INGAME);
		ModuleConfiguration cfg = module.getModuleConfig();
		boolean teleport = (boolean) cfg.getSetting(ModuleSetting.TELEPORT_ON_START);
		Location loc = teleport ? UConfig.unserialize(cfg.getSetting(ModuleSetting.TELEPORT_LOCATION).toString()) : null;
		UserManager.getInstance().forEach((user)->{
			if(!user.isIngame()) return;
			if(teleport)
				user.getPlayer().teleport(loc);
			if(module instanceof IModuleUserJoin)
				((IModuleUserJoin)module).onUserJoin(user);
		});
	}
	
	public void prepareStart(){
		Module module = _queue.next();
		if(module==null) return;
		setState(GameState.PREPARING);
		Bukkit.broadcastMessage(ChatColor.YELLOW+"Found available module: "+module.getAlias()); //TODO
		//found game -> announce
		if(_controller.load(module)){
			UserManager.getInstance().forEach((user)->{
				if(!user.isQueued()) return;
				user.setDataController(module);
				user.setIngame(true);
				user.getDataController().load(()->{
					user.getPlayer().sendMessage("data-debugging: data loaded for "+user.getPlayer().getName());
					//give stats item + help item
				});
			});
			startTimer(new GameTimer(0, 15)
				.setTimerEndEvent(()-> { start(module); })
				.setTimerTickEvent((stamp)->{
				if(stamp % 5==0 || stamp < 6) Bukkit.broadcastMessage("countdown-debugging: "+stamp+" seconds till start.");
			}));
		}
	}
	
	public void callPlayerJoin(){
		if(_state == GameState.WAITING)
			prepareStart();
	}
	
	public void callPlayerLeave(User user){
		if(_state != GameState.WAITING){
			Module module = _controller.getCurrent();
			if(module == null) return;
			if(module instanceof IModuleUserLeave)
				((IModuleUserLeave) module).onUserLeave(user, true);
			if((int) module.getModuleConfig().getSetting(ModuleSetting.MIN_PLAYERS) > UserManager.getInstance().countIngame()){
				prepareEnd();
			}
		}
	}
}
