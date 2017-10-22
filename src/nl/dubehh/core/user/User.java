package nl.dubehh.core.user;

import java.util.UUID;

import org.bukkit.entity.Player;

import nl.dubehh.core.module.Module;

public class User {
	
	private boolean _ingame, _queued;
	private UserDataController _data;
	private Player _player;
	
	public User(Player player){
		this._player = player;
	}
	
	public UUID getUUID(){
		return _player.getUniqueId();
	}
	
	public Player getPlayer(){
		return this._player;
	}
	
	public void setDataController(Module module){
		this._data = new UserDataController(this, module);
	}
	
	public boolean isIngame(){
		return this._ingame;
	}
	
	public void setIngame(boolean ingame){
		this._ingame = ingame;
	}
	
	public void setQueued(boolean queued){
		this._queued = queued;
	}
	
	public UserDataController getDataController(){
		return this._data;
	}

	public boolean isQueued() {
		return _queued;
	}
	
}
