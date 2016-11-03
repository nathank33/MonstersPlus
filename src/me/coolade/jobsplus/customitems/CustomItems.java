package me.coolade.jobsplus.customitems;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.coolade.jobsplus.CustomRecipes;
import me.coolade.jobsplus.CustomRecipes.RecipeSet;
import me.coolade.jobsplus.customitems.CustomShapelessRecipes.CustomShapelessRecipe;

public class CustomItems {
	public static ArrayList<CustomItemSet> customItems = new ArrayList<CustomItemSet>() {
		private static final long serialVersionUID = 1L;

		{
			add(new CustomItemSet("Powerstone", Material.REDSTONE));
			add(new CustomItemSet("Bottle o' EXP", Material.GLASS_BOTTLE));
			add(new CustomItemSet("Bucket o' EXP", Material.BUCKET));
		}
	};

	public static void init() {
		for (CustomShapelessRecipe recipe : CustomShapelessRecipes.recipes) {
			customItems.add(new CustomItemSet(recipe.getName(), recipe.getMat(), recipe.durability));
		}
		for (RecipeSet recipe : CustomRecipes.arrowList) {
			customItems.add(new CustomItemSet(recipe.getName(), Material.ARROW));
		}
		for (RecipeSet recipe : CustomRecipes.bombList) {
			customItems.add(new CustomItemSet(recipe.getName(), Material.EGG));
		}
	}

	public static class CustomItemSet {
		String name;
		Material mat;
		short durability;

		public CustomItemSet(String name, Material mat, short durability) {
			this.name = name;
			this.mat = mat;
			this.durability = durability;
		}

		public CustomItemSet(String name, Material mat) {
			this(name, mat, (short) 0);
		}

		public String getName() {
			return name;
		}

		public Material getMat() {
			return mat;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setMat(Material mat) {
			this.mat = mat;
		}

		public int getDurability() {
			return durability;
		}

		public void setDurability(short durability) {
			this.durability = durability;
		}

	}

	/*
	 * public static boolean isCustomItem(String name) { if(name != null)
	 * for(CustomItemSet cis : customItems) {
	 * if(cis.getName().equals(ChatColor.stripColor(name))) return true; }
	 * return false; }
	 */
	public static boolean isCustomItem(String s, ItemStack item) {
		if (s == null || item == null || !item.hasItemMeta()) {
			return false;
		}

		ItemMeta meta = item.getItemMeta();
		if (!meta.hasDisplayName()) {
			return false;
		}

		String itemName = meta.getDisplayName();
		if (itemName != null) {
			itemName.toUpperCase();
			s.toUpperCase();

			if (itemName.contains(s)) {
				if (!meta.hasLore()) {
					return false;
				}

				List<String> lore = meta.getLore();
				for (String strLore : lore) {
					if (strLore.contains("Unique")) {
						return true;
					}
				}

			}
		}
		return false;
	}

	public static boolean isCustomItem(ItemStack istack) {
		if (istack != null && istack.getType() != Material.AIR && istack.hasItemMeta()) {
			if (!istack.getItemMeta().hasDisplayName()) {
				return false;
			}
			if (istack.getItemMeta().hasLore()) {
				List<String> lore = istack.getItemMeta().getLore();
				for (String s : lore) {
					if (s.contains("Unique")) {
						return true;
					}
				}
			}

		}
		return false;
	}
	/*
	 * public static boolean isCustomItem(String s, ItemStack item) { /**
	 * Returns true if the itemstack is a customitem and specifically has the
	 * same name as s.
	 */
	/*
	 * if(s == null || item == null || !item.hasItemMeta() ||
	 * !item.getItemMeta().hasDisplayName()) return false;
	 * 
	 * ItemMeta meta = item.getItemMeta(); String itemName =
	 * ChatColor.stripColor(meta.getDisplayName()); if(itemName != null &&
	 * CustomItems.isCustomItem(itemName)) {
	 * if(itemName.equalsIgnoreCase(ChatColor.stripColor(s))) return true; }
	 * return false; } public static boolean isCustomItem(ItemStack istack) {
	 * /** Checks if an item represents an item in the CustomItems ArrayList.
	 */
	/*
	 * if(istack != null) { String name = istack.getItemMeta().getDisplayName();
	 * return CustomItems.isCustomItem(name); } return false; }
	 */

	public static CustomItemSet getCustomItem(String name) {
		/**
		 * Checks if an item represents an item in the CustomItems ArrayList.
		 */
		if (name != null) {
			for (CustomItemSet cis : customItems) {
				if (cis.getName().equals(ChatColor.stripColor(name))) {
					return cis;
				}
			}
		}
		return null;
	}

	public static ItemStack createCustomItem(String name, int amount) {
		CustomItemSet cis = getCustomItem(name);
		if (cis != null) {
			ItemStack istack = new ItemStack(cis.getMat(), amount, (short) cis.getDurability());
			ItemMeta meta = istack.getItemMeta();
			meta.setDisplayName(name);
			meta.setLore(new ArrayList<String>() {
				private static final long serialVersionUID = 1L;

				{
					add(ChatColor.DARK_PURPLE + "Unique");
				}
			});
			istack.setItemMeta(meta);
			return istack;
		}
		return null;
	}
}
