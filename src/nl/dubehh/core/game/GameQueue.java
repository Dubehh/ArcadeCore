package nl.dubehh.core.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import nl.dubehh.core.module.Module;
import nl.dubehh.core.module.ModuleConfiguration;
import nl.dubehh.core.module.ModuleSetting;
import nl.dubehh.core.user.UserManager;

public class GameQueue {

	private final int INITIAL = -1;
	private List<Module> _queue;
	private int _currentIndex;
	private GameManager _game;
	
	public GameQueue(GameManager game, HashSet<Module> modules){
		this._queue = new ArrayList<>(modules);
		this._game = game;
		this._currentIndex = INITIAL;
	}
	
	public Module next(){
		if(_queue.isEmpty()) return null;
		boolean cycleCompleted = false;
		int queueSize = (int) UserManager.getInstance().getUsers()
				.stream()
				.filter(user -> user.isQueued())
				.count();
		Module result = null;
		for(int i = _currentIndex+1 >= _queue.size() ? 0 
				: _currentIndex+1; i < _queue.size(); i++){
			if(cycleCompleted && _currentIndex == INITIAL) return null;
			Module module = _queue.get(i);
			if(isOption(queueSize, module)){
				_currentIndex=i;
				result = module;
				break;
			}else{
				if(i+1 >= _queue.size()){ 
					i=INITIAL;
					cycleCompleted = true;
				} else continue;
			}
		}
		return result;
	}
	
	public void setNext(Module module){
		_currentIndex = _queue.indexOf(module)-1;
	}
	
	public boolean isOption(int currentCount, Module module){
		ModuleConfiguration config = module.getModuleConfig();
		if(!(boolean) config.getSetting(ModuleSetting.ENABLED)) return false;
		else{
			int	min = (int) config.getSetting(ModuleSetting.MIN_PLAYERS);
			int max = (int) config.getSetting(ModuleSetting.MAX_PLAYERS);
			return _game.isDebugging() || ((min <= 0 || min <= currentCount) 
					&& (max <= 0 || currentCount <= max));
		}
	}
}
