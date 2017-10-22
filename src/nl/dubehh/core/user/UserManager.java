package nl.dubehh.core.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.entity.Player;

public class UserManager {

	private HashMap<UUID, User> _users;
	private static UserManager _instance;
	
	private UserManager() {
		this._users = new HashMap<>();
	}

	public User register(Player player) {
		User user = new User(player);
		user.setQueued(true);
		_users.put(player.getUniqueId(), user);
		return user;
	}

	public User unregister(Player player) {
		User user;
		if((user = _users.remove(player.getUniqueId())) != null)
			if(user.getDataController() != null)
				user.getDataController().save();
		return user;
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
	
	public Collection<User> getUsers(){
		return this._users.values();
	}
	
	public void forEach(Consumer<User> action){
		_users.values().forEach((user) ->{
			action.accept(user);
		});
	}
	
	public int countIngame(){
		return (int) _users.values().stream().filter(user-> user.isIngame()).count();
	}
}
