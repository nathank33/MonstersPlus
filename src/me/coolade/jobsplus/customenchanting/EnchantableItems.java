package me.coolade.jobsplus.customenchanting;

import java.util.ArrayList;

import me.coolade.monstersplus.Tools;

public class EnchantableItems {
	public static String[] SWORDS = new String[] { "WOOD_SWORD", "STONE_SWORD", "GOLD_SWORD", "IRON_SWORD",
			"DIAMOND_SWORD" };

	public static String[] AXES = new String[] { "WOOD_AXE", "STONE_AXE", "GOLD_AXE", "IRON_AXE", "DIAMOND_AXE" };

	public static String[] MELEE = Tools.stringConcatAll(new ArrayList<String[]>() {
		private static final long serialVersionUID = 1L;

		{
			add(SWORDS);
			add(AXES);
		}
	});

	public static String[] HOES = new String[] { "WOOD_HOE", "STONE_HOE", "GOLD_HOE", "IRON_HOE", "DIAMOND_HOE" };

	public static String[] SHOVELS = new String[] { "WOOD_SPADE", "STONE_SPADE", "GOLD_SPADE", "IRON_SPADE",
			"DIAMOND_SPADE" };

	public static String[] PICKAXES = new String[] { "WOOD_PICKAXE", "STONE_PICKAXE", "GOLD_PICKAXE", "IRON_PICKAXE",
			"DIAMOND_PICKAXE" };

	public static String[] TOOLS = Tools.stringConcatAll(new ArrayList<String[]>() {
		private static final long serialVersionUID = 1L;

		{
			add(HOES);
			add(SHOVELS);
			add(PICKAXES);
			add(AXES);
		}
	});

	public static String[] SHEARS = new String[] { "SHEARS" };

	public static String[] HELMETS = new String[] { "LEATHER_HELMET", "CHAINMAIL_HELMET", "GOLD_HELMET", "IRON_HELMET",
			"DIAMOND_HELMET" };

	public static String[] CHESTPLATES = new String[] { "LEATHER_CHESTPLATE", "CHAINMAIL_CHESTPLATE", "GOLD_CHESTPLATE",
			"IRON_CHESTPLATE", "DIAMOND_CHESTPLATE" };

	public static String[] LEGGINGS = new String[] { "LEATHER_LEGGINGS", "CHAINMAIL_LEGGINGS", "GOLD_LEGGINGS",
			"IRON_LEGGINGS", "DIAMOND_LEGGINGS" };

	public static String[] BOOTS = new String[] { "LEATHER_BOOTS", "CHAINMAIL_BOOTS", "GOLD_BOOTS", "IRON_BOOTS",
			"DIAMOND_BOOTS" };

	public static String[] ARMOR = Tools.stringConcatAll(new ArrayList<String[]>() {
		private static final long serialVersionUID = 1L;

		{
			add(HELMETS);
			add(CHESTPLATES);
			add(LEGGINGS);
			add(BOOTS);
		}
	});

	public static String[] BOW = new String[] { "BOW" };

	public static String[] FISHINGROD = new String[] { "FISHING_ROD" };

	public static String[] EMPTYBUCKET = new String[] { "BUCKET" };
	public static String[] WATERBUCKET = new String[] { "WATER_BUCKET" };

	public static String[] BUCKETS = Tools.stringConcatAll(new ArrayList<String[]>() {
		private static final long serialVersionUID = 1L;

		{
			add(EMPTYBUCKET);
			add(WATERBUCKET);
		}
	});

	private EnchantableItems() {
	}

}
