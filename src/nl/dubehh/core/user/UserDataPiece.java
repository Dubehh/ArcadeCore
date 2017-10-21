package nl.dubehh.core.user;

public class UserDataPiece {
	
	private String _key;
	private Object _value;
	private UserDataController _parent;
	
	public UserDataPiece(UserDataController parent, String key){
		this._key = key;
		this._parent = parent;
		if(!parent.isValidKey(key)) 
			throw new NullPointerException("Nonexisting key requested from user data!");
		else this._value = parent.fetch(key);
	}
	
	public int toInt(){
		return (int) _value;
	}
	
	public void increment(int amount){
		int newval = toInt()+amount;
		_parent.update(_key, newval);
		_value = newval;
	}
	
	public void set(Object val){
		_parent.update(_key, val);
	}
	
	@Override
	public String toString(){
		return this._value.toString();
	}
}
