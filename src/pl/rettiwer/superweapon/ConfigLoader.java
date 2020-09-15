package pl.rettiwer.superweapon;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class ConfigLoader {
	public static List<Damage> damages = new ArrayList<>();
	public static ItemStack MAGIC_ITEM;

	public static void loadConfig() {
		FileConfiguration fc = Main.getInstance().getConfig();
		if (fc == null)
			createConfig();

		ConfigurationSection section = fc.getConfigurationSection("damage");
		if (section == null)
			createConfig();

		MAGIC_ITEM = fc.getItemStack("item");
		
		for (String string : section.getKeys(false)) {
			String base = "damage." + string + ".";

			int minDamage = fc.getInt(base + "minDamage");
			int maxDamage = fc.getInt(base + "maxDamage");
			double chance = fc.getDouble(base + "chance") / 100;
			damages.add(new Damage(minDamage, maxDamage, chance));
		}
	}

	public static void createConfig() {
		FileConfiguration fc = Main.getInstance().getConfig();
		
		fc.set("item", new ItemStack(Material.STICK));
		
		fc.set("disclaimer", "Suma szans(chance) nie moze byc wieksza niz 100%");

		fc.set("damage.1.minDamage", -15);
		fc.set("damage.1.maxDamage", -5);
		fc.set("damage.1.chance", 50);

		fc.set("damage.2.minDamage", -4);
		fc.set("damage.2.maxDamage", 10);
		fc.set("damage.2.chance", 40);

		fc.set("damage.3.minDamage", 11);
		fc.set("damage.3.maxDamage", 100);
		fc.set("damage.3.chance", 10);

		Main.getInstance().saveConfig();

		loadConfig();
	}
	
	public static void setNewItem(ItemStack item) {
		FileConfiguration fc = Main.getInstance().getConfig();
		
		fc.set("item", item);
		
		MAGIC_ITEM = item;

		Main.getInstance().saveConfig();

		loadConfig();
	}

	public static Damage getRandomDamage() {
		double p = Math.random();
		double cumulativeProbability = 0.0;
		for (Damage item : damages) {
			cumulativeProbability += item.getChance();
			if (p <= cumulativeProbability) {
				return item;
			}
		}
		return null;
	}
}
