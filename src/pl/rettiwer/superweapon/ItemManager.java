package pl.rettiwer.superweapon;

import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R1.NBTTagCompound;

public class ItemManager {

	public static ItemStack setNBTTag(ItemStack is, String name, int val) {
		net.minecraft.server.v1_8_R1.ItemStack itemstack = CraftItemStack.asNMSCopy(is);
		NBTTagCompound comp = itemstack.getTag();
		if (comp == null)
			comp = new NBTTagCompound();
		comp.setInt(name, val);
		itemstack.setTag(comp);
		is = CraftItemStack.asBukkitCopy(itemstack);
		itemstack = CraftItemStack.asNMSCopy(is);
		return is;
	}

	public static double getNBTTag(ItemStack is, String name) {
		net.minecraft.server.v1_8_R1.ItemStack itemstack = CraftItemStack.asNMSCopy(is);
		NBTTagCompound comp = itemstack.getTag();
		if (comp != null) {
			if (itemstack.getTag().hasKey(name)) {
				return itemstack.getTag().getInt(name);
			}
		}
		return 0;
	}
}
