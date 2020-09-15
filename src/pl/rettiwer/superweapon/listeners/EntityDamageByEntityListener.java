package pl.rettiwer.superweapon.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import pl.rettiwer.superweapon.ItemManager;

public class EntityDamageByEntityListener implements Listener {

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (p.getItemInHand() != null) {
				ItemStack item = p.getItemInHand();
				double damage = 1 + (ItemManager.getNBTTag(item, "SuperWeaponDamage") / 100);
				double dmg = e.getDamage() * damage;
				e.setDamage(dmg);
			}
		}
	}

}
