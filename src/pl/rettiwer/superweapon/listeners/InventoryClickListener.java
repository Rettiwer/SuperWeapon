package pl.rettiwer.superweapon.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pl.rettiwer.superweapon.ConfigLoader;
import pl.rettiwer.superweapon.Damage;
import pl.rettiwer.superweapon.ItemManager;


public class InventoryClickListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().getTitle().equalsIgnoreCase("Tu wló¿ przedmiot")) {
			ItemStack random = new ItemStack(Material.getMaterial(175), 1);

			ItemMeta im = random.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Losuj nowe obrazenia");
			random.setItemMeta(im);

			if (e.getCurrentItem().equals(random)) {
				ItemStack[] content = e.getInventory().getContents();
				List<ItemStack> items = Arrays.asList(content);

				List<Material> mats = new ArrayList<>();
				for (ItemStack is : items) {
					if (is != null && is.getTypeId() != 0) {
						mats.add(is.getType());
					}
				}

				if (mats.size() > 2) {
					e.getWhoClicked().sendMessage(
							ChatColor.RED + "Nie mozesz zmieniac srednich obrazen dla wiecej niz jednego przedmiotu!");
					e.setCancelled(true);
				}

				if (mats.contains(Material.DIAMOND_SWORD)) {

					Damage d = ConfigLoader.getRandomDamage();
					int randomNum = ThreadLocalRandom.current().nextInt(d.getMinDamage(), d.getMaxDamage() + 1);

					ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);

					List<String> lore = new ArrayList<>();
					lore.add(ChatColor.RED + "Srednie obrazenia: " + randomNum + "%");

					ItemMeta ims = sword.getItemMeta();
					ims.setLore(lore);
					sword.setItemMeta(ims);

					e.getWhoClicked().getInventory()
							.addItem(ItemManager.setNBTTag(sword, "SuperWeaponDamage", randomNum));
					e.getWhoClicked().closeInventory();
					e.getWhoClicked().getInventory().removeItem(ConfigLoader.MAGIC_ITEM);
				} else {
					e.getWhoClicked().sendMessage(ChatColor.RED + "Przedmiotem zmiany srednich obrazen musi byc miecz!");
					e.setCancelled(true);
				}
				e.setCancelled(true);
			}
		}

		if (e.getInventory() instanceof AnvilInventory) {
			if (e.getRawSlot() == 2) {
				ItemStack result = e.getCurrentItem();
				if (result != null) {
					if (result.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Losuj nowe obrazenia")
							|| result.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Tu wló¿ przedmiot")) {
						e.getWhoClicked().sendMessage(ChatColor.RED + "Nie mozesz nadac takiej nazwy temu przedmiotowi!");
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (p.getItemInHand().getTypeId() != 0) {
				ItemStack hItem = p.getItemInHand();
				ItemStack mItem = ConfigLoader.MAGIC_ITEM;

				if (hItem.getItemMeta().getDisplayName() != null) {

					if (hItem.getType().equals(mItem.getType()) && hItem.getItemMeta().getDisplayName()
							.equalsIgnoreCase(mItem.getItemMeta().getDisplayName())) {
						Inventory i = Bukkit.createInventory(p, 54, "Tu wló¿ przedmiot");

						ItemStack random = new ItemStack(Material.getMaterial(175), 1);

						ItemMeta im = random.getItemMeta();
						im.setDisplayName(ChatColor.GOLD + "Losuj nowe obrazenia");
						random.setItemMeta(im);

						i.setItem(49, random);

						p.openInventory(i);
					}
				}
			}
		}
	}
}
