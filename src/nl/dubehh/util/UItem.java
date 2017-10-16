package nl.dubehh.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UItem {
	
	private ItemStack _item;
	private ItemMeta _meta;
	private List<String> _lore;
	
	private UItem(Material mat){
		this._item = new ItemStack(mat);
		this._meta = _item.getItemMeta();
		this._meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
		this._lore = new ArrayList<>();
	}
	
	public UItem setAmount(int amount){
		this._item.setAmount(amount);
		return this;
	}
	
	public UItem setName(String name){
		this._meta.setDisplayName(name);
		return this;
	}
	
	public UItem addLore(String... lore){
		for(String s : lore)
			_lore.add(ChatColor.GRAY+s);
		return this;
	}
	
	public UItem addLore(List<String> lore){
		_lore.addAll(lore);
		return this;
	}
	
	public UItem setDurability(short durability){
		this._item.setDurability(durability);
		return this;
	}
	
	public UItem addEnchant(Enchantment ench, int level){
		this._item.addUnsafeEnchantment(ench, level);
		return this;
	}
	
	public UItem addItemFlag(ItemFlag... flag){
		this._meta.addItemFlags(flag);
		return this;
	}
	
	public ItemStack toItem(){
		this._meta.setLore(_lore);
		this._meta.setUnbreakable(true);
		return _item;
	}
	
	public static UItem create(Material mat){
		return new UItem(mat);
	}
}
