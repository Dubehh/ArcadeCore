package nl.dubehh.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import nl.dubehh.core.user.UserManager;

public class ConnectEvent implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		e.setJoinMessage(null);
		UserManager.getInstance().register(e.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		e.setQuitMessage(null);
		UserManager.getInstance().unregister(e.getPlayer());
		
	}
}
