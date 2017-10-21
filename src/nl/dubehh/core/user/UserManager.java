package nl.dubehh.core.user;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class UserManager {

	private HashMap<UUID, User> _users;
	private static UserManager _instance;
	
	private UserManager() {
		this._users = new HashMap<>();
	}

	public void register(Player player) {
		User user = new User(player);
		_users.put(player.getUniqueId(), user);
	}

	public void unregister(Player player) {
		User user;
		if((user = _users.remove(player.getUniqueId())) != null)
			user.getDataController().save();
	}

	public User fetch(Player player) {
		return _users.containsKey(player.getUniqueId()) ? 
				_users.get(player.getUniqueId()) : null;
	}
	
	public static UserManager getInstance(){
		if(_instance == null)
			_instance = new UserManager();
		return _instance;
	}
}
