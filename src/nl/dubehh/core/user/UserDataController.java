package nl.dubehh.core.user;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import nl.dubehh.core.data.database.request.DatabaseFetchRequest;
import nl.dubehh.core.data.database.request.DatabaseUpdateRequest;
import nl.dubehh.core.data.database.table.TableColumn;
import nl.dubehh.core.module.Module;

public class UserDataController {
	
	private HashMap<String, Object> _data;
	private User _user;
	private Module _module;
	
	public UserDataController(User user, Module module){
		this._user = user;
		this._module = module;
		this._data = new HashMap<>();
	}
	
	public void load(){
		new DatabaseUpdateRequest(this._module.getDataTable().registration(this._user), ()->{
			String query = "SELECT * FROM "+this._module.getDataTable().getName()+" WHERE uuid = '"+_user.getUUID()+"'";
			new DatabaseFetchRequest(query, (set) -> {
				try {
					if(!set.next()) return;
					for(TableColumn column : _module.getDataTable().getColumns())
						_data.put(column.getName(), set.getObject(column.getName()));
					_module.onUserJoin(_user);
				} catch (Exception e) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Failed to load data! "
							+"; module "+_module.getName()
						    +"; player: "+_user.getPlayer().getName());
				}
			});
		});
	}
	
	public void save(){ 
		save(null); 
	}
	
	public void save(Runnable callback){
		StringBuilder query = new StringBuilder();
		_data.forEach((k, v) -> {
			query.append(", ")
				.append(k+" = ")
				.append(v instanceof String ? "'"+v.toString()+"'" : v.toString());
		});
		String pattern = "UPDATE "+_module.getDataTable().getName()+" SET "+query.toString().substring(2)+" WHERE uuid = '"+this._user.getUUID()+"'";
		new DatabaseUpdateRequest(pattern, callback).query();
	}
	
	public UserDataPiece get(String key){
		return new UserDataPiece(this, key);
	}

	protected void update(String key, Object val) {
		_data.put(key.toLowerCase(), val);
	}
	
	protected Object fetch(String key){
		return isValidKey(key) ? _data.get(key.toLowerCase()) : null;
	}

	protected boolean isValidKey(String key) {
		return _data.containsKey(key.toLowerCase());
	}
}
