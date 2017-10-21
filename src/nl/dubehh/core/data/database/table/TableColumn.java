package nl.dubehh.core.data.database.table;

public class TableColumn {
	
	private boolean 
		_nullable, 
		_primary,
		_hasDefault;
	private String 
		_name, 
		_default;
	private TableColumnType _type;
	
	private TableColumn(String name){
		this._name = name;
		this._default = "NULL";
		this._hasDefault = false;
		this._nullable = true;
		this._type = TableColumnType.VARCHAR_NORMAL;
	}
	
	public static TableColumn create(String name){
		return new TableColumn(name);
	}
	
	public TableColumn setPrimary(boolean primary){
		this._primary = primary;
		return this;
	}
	
	public boolean isPrimary(){
		return this._primary;
	}
	
	public TableColumn setNullable(boolean nullable){
		this._nullable = nullable;
		return this;
	}
	
	public TableColumn setType(TableColumnType type){
		this._type = type;
		return this;
	}
	
	public String getName(){
		return this._name;
	}
	
	public TableColumn setDefault(Object val){
		this._default = "'"+val+"'";
		this._hasDefault=true;
		return this;
	}
	
	public String asQuery(){
		return "`"+this._name+"` "+
				this._type.toString()+" "+
				(this._nullable ? "" : "NOT ")+" NULL "+
				(this._hasDefault ?"DEFAULT "+this._default : "");
	}
	
}
