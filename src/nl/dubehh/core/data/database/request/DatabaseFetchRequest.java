package nl.dubehh.core.data.database.request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

import org.bukkit.Bukkit;

import nl.dubehh.Main;
import nl.dubehh.core.data.database.DatabaseConnectionHandler;
import nl.dubehh.core.data.database.DatabaseRequestQueue;

public class DatabaseFetchRequest implements Runnable{

	private String _query;
	private Consumer<ResultSet> _callback;
	private DatabaseRequestQueue _owner;
	
	public DatabaseFetchRequest(String query, Consumer<ResultSet> callback) {
		this._callback = callback;
		this._query = query;
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
			ResultSet set = stmt.executeQuery();
			_callback.accept(set);
			if(_owner != null) 
				_owner.ping();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
