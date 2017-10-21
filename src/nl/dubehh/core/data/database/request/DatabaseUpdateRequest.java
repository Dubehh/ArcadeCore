package nl.dubehh.core.data.database.request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import nl.dubehh.Main;
import nl.dubehh.core.data.database.DatabaseConnectionHandler;
import nl.dubehh.core.data.database.DatabaseRequestQueue;

public class DatabaseUpdateRequest implements Runnable{

	private String _query;
	private Runnable _callback;
	private DatabaseRequestQueue _owner;
	
	public DatabaseUpdateRequest(String query, Runnable callback) {
		this._callback = callback;
		this._query = query;
	}
	
	public DatabaseUpdateRequest(String query){
		this(query, null);
	}
	
	public void query(){
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), this);
	}
	
	public void setQueueOwner(DatabaseRequestQueue queue){
		this._owner = queue;
	}
	
	@Override
	public void run() {
		try(Connection c = DatabaseConnectionHandler.getInstance().getSource().getConnection()){
			PreparedStatement stmt = c.prepareStatement(_query);
			stmt.execute();
			if(_callback!=null)
				_callback.run();
			if(_owner != null) 
				_owner.ping();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
