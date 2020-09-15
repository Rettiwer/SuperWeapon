package pl.rettiwer.superweapon.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.meta.ItemMeta;

import pl.rettiwer.superweapon.ConfigLoader;
import pl.rettiwer.superweapon.Damage;
import pl.rettiwer.superweapon.ItemManager;

public class CraftItemListener implements Listener {

	@EventHandler
	public void craft(CraftItemEvent e) {
		if (e.getInventory() instanceof CraftingInventory) {
			CraftingInventory inv = (CraftingInventory) e.getInventory();

			if (inv.getResult().getType() == Material.DIAMOND_SWORD || inv.getResult().getType() == Material.IRON_SWORD
					|| inv.getResult().getType() == Material.GOLD_SWORD
					|| inv.getResult().getType() == Material.STONE_SWORD
					|| inv.getResult().getType() == Material.WOOD_SWORD) {

				Damage d = ConfigLoader.getRandomDamage();
				int randomNum = ThreadLocalRandom.current().nextInt(d.getMinDamage(), d.getMaxDamage() + 1);

				List<String> lore = new ArrayList<>();
				lore.add(ChatColor.RED + "Srednie obrazenia: " + randomNum + "%");

				ItemMeta im = inv.getResult().getItemMeta();
				im.setLore(lore);
				inv.getResult().setItemMeta(im);

				inv.setResult(ItemManager.setNBTTag(inv.getResult(), "SuperWeaponDamage", randomNum));
			}
		}
	}
}
