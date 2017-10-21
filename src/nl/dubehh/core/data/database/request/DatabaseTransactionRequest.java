package nl.dubehh.core.data.database.request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.bukkit.Bukkit;

import nl.dubehh.Main;
import nl.dubehh.core.data.database.DatabaseConnectionHandler;

public class DatabaseTransactionRequest implements Runnable{

	private Runnable _callback;
	private String[] _transaction;
	
	public DatabaseTransactionRequest(Runnable callback, String... transaction){
		this._callback = callback;
		this._transaction = transaction;
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), this);
	}
	
	public DatabaseTransactionRequest(Runnable callback, List<String> transaction){
		this(callback, transaction.toArray(new String[transaction.size()]));
	}

	@Override
	public void run() {
		try(Connection connection = DatabaseConnectionHandler.getInstance().getSource().getConnection()){
			connection.setAutoCommit(false);
			for(String query : _transaction){
				PreparedStatement stmt = connection.prepareStatement(query);
				stmt.execute();
			}
			connection.commit();
			if(_callback!=null)
				_callback.run();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
