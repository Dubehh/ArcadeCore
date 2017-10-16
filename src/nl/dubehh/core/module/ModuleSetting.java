package nl.dubehh.core.module;

public enum ModuleSetting {
	
	MIN_PLAYERS(2),
	TELEPORT_ON_START(true),
	TELEPORT_LOCATION(null),
	ENABLED(true);
	
	private Object _default;
	
	private ModuleSetting(Object defaultValue){
		this._default = defaultValue;
	}
	
	public Object getDefault(){
		return this._default;
	}
	
	@Override
	public String toString(){
		return super.toString().toLowerCase();
	}
}

