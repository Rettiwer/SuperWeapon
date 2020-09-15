package pl.rettiwer.superweapon;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import pl.rettiwer.superweapon.listeners.CraftItemListener;
import pl.rettiwer.superweapon.listeners.EntityDamageByEntityListener;
import pl.rettiwer.superweapon.listeners.InventoryClickListener;

public class Main extends JavaPlugin {
	private static Main instance;

	@Override
	public void onEnable() {
		instance = this;
		ConfigLoader.loadConfig();
		getServer().getPluginManager().registerEvents(new CraftItemListener(), this);
		getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		this.getCommand("superweapon").setExecutor(this);

	}

	@Override
	public void onDisable() {

	}

	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (cmd.getName().equalsIgnoreCase("superweapon") && sender.hasPermission("superweapon.get")) {

			if (args.length != 0 && args[0] != null) {

				if (args[0].equalsIgnoreCase("get")) {
					if (args[1] != null) {
						Player reciver = Bukkit.getPlayer(args[1]);
						if (reciver != null) {
							reciver.getInventory().addItem(ConfigLoader.MAGIC_ITEM);
							reciver.sendMessage(
									"Puff... Orzymales przedmiot, ktorym mozesz zmienic obrazenia swojej broni!");
							return true;
						} else {
							sender.sendMessage("Nie ma takiego gracza");
							return false;
						}
					} else {
						Player p = (Player) sender;
						p.getInventory().addItem(ConfigLoader.MAGIC_ITEM);
						p.sendMessage("Puff... Otrzymales przedmiot, ktorym mozesz zmienic obrazenia swojej broni!");
						return true;
					}

				} else if (args[0].equalsIgnoreCase("set")) {
					Player p = (Player) sender;
					if (p.getItemInHand().getType() != Material.AIR) {
						ConfigLoader.setNewItem(p.getItemInHand());
						p.sendMessage("Ustawiles nowy przedmiot do zmiany srednich obrazen broni!");
						return true;
					} else
						p.sendMessage("Musisz posiadac przedmiot w rece!");
					return false;
				}

			} else {
				sender.sendMessage("--- SuperWeapon ---");
				sender.sendMessage("/superweapon get - daje magiczny przedmiot.");
				sender.sendMessage("/superweapon set - ustawia nowy magiczny przemiot.");
				return false;
			}
		}
		return false;
	}

	public static Main getInstance() {
		return instance;
	}

}
