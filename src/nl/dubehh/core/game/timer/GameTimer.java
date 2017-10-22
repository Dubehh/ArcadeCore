package nl.dubehh.core.game.timer;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import nl.dubehh.Main;

public class GameTimer {

	private boolean _running;
	private int _delay, _time;
	private ITimerEnd _callback;
	private ITimerTick _tickEvent;
	private BukkitTask _task;
	
	public GameTimer(int delay, int time){
		this._time = time;
		this._delay = delay;
		this._callback = null;
		this._running = false;
		this._tickEvent = null;
	}
	
	public int getTime(){
		return this._time;
	}
	
	public GameTimer setTimerEndEvent(ITimerEnd callback){
		this._callback = callback;
		return this;
	}
	
	public GameTimer setTimerTickEvent(ITimerTick tick){
		this._tickEvent = tick;
		return this;
	}
	
	public void start(){
		this._running = true;
		this._task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), ()->{
			if(this._time > 0){
				if(_tickEvent != null)
					_tickEvent.onTick(_time);
				this._time--;
			}else cancel(true);
		}, this._delay*20, 20);
	}
	
	public void cancel(boolean useCallback){
		this._task.cancel();
		this._running = false;
		if(useCallback && this._callback != null)
			this._callback.onEnd();
	}

	public boolean isRunning() {
		return _running;
	}
}
