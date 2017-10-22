package nl.dubehh.event;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import nl.dubehh.core.game.GameManager;
import nl.dubehh.core.user.UserManager;
import nl.dubehh.util.UChat;

public class ConnectEvent implements Listener{
	
	private GameManager _game;
	private UserManager _users;
	
	public ConnectEvent(){
		this._game = GameManager.getInstance();
		this._users = UserManager.getInstance();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		e.setJoinMessage(null);
		Player player = e.getPlayer();
		player.setLevel(0);
		player.setExp(0);
		player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
		if(_game.getGameSpawn() != null)
			player.teleport(_game.getGameSpawn());
		else 
			UChat.ERROR.send(player, "Main spawn not set yet, use "+ChatColor.WHITE+"/arcade setspawn"+ChatColor.GRAY+" to set the spawn.");
		_users.register(player);
		_game.callPlayerJoin();
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		e.setQuitMessage(null);
		_game.callPlayerLeave(_users.unregister(e.getPlayer()));
	}
}
