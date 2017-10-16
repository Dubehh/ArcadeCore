package nl.dubehh.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public final class UConfig {

	public static String serialize(Location loc){
		return loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+","+loc.getYaw()+","+loc.getPitch();
	}
	
	public static Location unserialize(String s){
		String[] st = s.split(",");
        return new Location(Bukkit.getWorld(st[0]), Integer.parseInt(st[1]), Integer.parseInt(st[2]), Integer.parseInt(st[3]), Float.parseFloat(st[4]), Float.parseFloat(st[5]));
	}
}
