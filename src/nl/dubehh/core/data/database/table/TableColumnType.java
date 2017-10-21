package nl.dubehh.core.data.database.table;

public enum TableColumnType {
	
	VARCHAR_NORMAL("varchar(100)"),
	VARCHAR_UUID("varchar(255)"),
	INT("int(11)");
	
	private String _format;
	private TableColumnType(String format){
		this._format = format;
	}
	
	@Override
	public String toString(){
		return this._format;
	}
}
