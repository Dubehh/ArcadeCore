package nl.dubehh.core.data.database.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.dubehh.core.user.User;

public class Table {
	
	private String _name;
	private List<TableColumn> _columns;
	private Map<TableColumn, String> _registration;
	private TableColumn _primaryKey;
	
	public Table(String name){
		this._name = name;
		this._columns = new ArrayList<>();
		this._registration = new HashMap<>();
	}
	
	protected void registerColumn(TableColumn... c){
		for(TableColumn column : c)
			this.registerColumn(column);
	}
	
	public List<TableColumn> getColumns(){
		return this._columns;
	}
	
	protected void registerColumn(TableColumn c){
		this._columns.add(c);
		if(c.isPrimary())
			this._primaryKey=c;
	}
	
	protected void setDefault(TableColumn c, String value){
		_registration.put(c, value);
	}
	
	public String getName(){
		return this._name;
	}
	
	public String creation(){
		StringBuilder rtn = new StringBuilder();
		rtn.append("CREATE TABLE IF NOT EXISTS `"+this._name+"` (");
		for(TableColumn column : this._columns)
			rtn.append(column.asQuery()+",");
		rtn.append("PRIMARY KEY (`"+this._primaryKey.getName()+"`)) ");
		rtn.append("ENGINE=InnoDB DEFAULT CHARSET=latin1");
		return rtn.toString();
	}
	
	public String registration(User u){
		StringBuilder columns = new StringBuilder();
		StringBuilder values = new StringBuilder();
		this._registration.forEach((k, v) -> {
			String val = v.replaceAll("%uuid%", u.getUUID().toString())
						  .replaceAll("%name%", u.getPlayer().getName());
			columns.append(",").append("`"+k.getName()+"`");
			values.append(",").append(val);
		});
		return "INSERT IGNORE INTO `"+this._name+"` ("+
			columns.toString().substring(1)+")"+
			" VALUES ("+values.substring(1)+");";
	}
	
}
