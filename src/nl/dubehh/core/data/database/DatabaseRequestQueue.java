package nl.dubehh.core.data.database;

public class DatabaseRequestQueue {

	private int _maxRequests, _notifications;
	private Runnable _callback;
	public DatabaseRequestQueue(int requests, Runnable callback){
		this._maxRequests = requests;
		this._callback = callback;
		this._notifications = 0;
	}
	
	public void ping(){
		_notifications++;
		if(_notifications == _maxRequests)
			_callback.run();
	}
	
}
