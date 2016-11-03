package me.coolade.jobsplus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.coolade.monstersplus.MonstersPlus;

public class FishList {
	private final static int MAX_LEVEL = 30;
	public final static int TIER1_LEVEL = 1;
	public final static int TIER2_LEVEL = 3;
	public final static int TIER3_LEVEL = 5;
	public final static int TIER4_LEVEL = 8;
	public final static int TIER5_LEVEL = 12;
	public final static int TIER6_LEVEL = 15;

	public static ArrayList<FishItemSet> fishListTier6 = new ArrayList<FishItemSet>() {
		private static final long serialVersionUID = 1L;

		{
			add(new FishItemSet(Material.MOB_SPAWNER, 0.3, 1, 3, 2));
			add(new FishItemSet(Material.EMERALD, 0.3, 1, 3.0, 5));
			add(new FishItemSet(Material.GOLDEN_APPLE, (short) 0, 0.9, 1, 4.5, 5));
			add(new FishItemSet(Material.DIAMOND, 1.5, 1, 6.0, 7));
			add(new FishItemSet(Material.GOLD_ORE, 2.5, 1, 6.0, 16));
		}
	};
	public static ArrayList<FishItemSet> fishListTier5 = new ArrayList<FishItemSet>() {
		private static final long serialVersionUID = 1L;

		{
			add(new FishItemSet(Material.MAGMA_CREAM, 1.0, 1, 4.0, 8));
			add(new FishItemSet(Material.BLAZE_ROD, 1.0, 1, 4.0, 8));
			add(new FishItemSet(Material.ANVIL, 1.0, 1, 2.0, 3));
			add(new FishItemSet(Material.EXP_BOTTLE, 2.0, 1, 5.0, 55));
			add(new FishItemSet(Material.GOLD_NUGGET, 2.5, 5, 6.0, 27));
			add(new FishItemSet(Material.NETHER_STAR, 0.5, 1, 3.0, 1));
		}
	};
	public static ArrayList<FishItemSet> fishListTier4 = new ArrayList<FishItemSet>() {
		private static final long serialVersionUID = 1L;

		{
			add(new FishItemSet(Material.BLAZE_POWDER, 1.0, 1, 3.0, 8));
			add(new FishItemSet(Material.ENDER_PEARL, 1.0, 1, 3.0, 8));
			add(new FishItemSet(Material.SLIME_BALL, 1.0, 1, 2.0, 8));
			add(new FishItemSet(Material.IRON_ORE, 2.0, 1, 7.0, 15));
			add(new FishItemSet(Material.OBSIDIAN, 1.0, 1, 5.0, 7));
		}
	};
	public static ArrayList<FishItemSet> fishListTier3 = new ArrayList<FishItemSet>() {
		private static final long serialVersionUID = 1L;

		{
			add(new FishItemSet(Material.REDSTONE, 3.0, 5, 5.0, 38));
			add(new FishItemSet(Material.LEATHER_CHESTPLATE, 3.0, 1, 0.5, 3));
			add(new FishItemSet(Material.LEATHER_LEGGINGS, 3.0, 1, 0.5, 3));
			add(new FishItemSet(Material.GOLD_AXE, 1.0, 1, 2.0, 3));
			add(new FishItemSet(Material.FERMENTED_SPIDER_EYE, 1.0, 1, 3.5, 8));
		}
	};
	public static ArrayList<FishItemSet> fishListTier2 = new ArrayList<FishItemSet>() {
		private static final long serialVersionUID = 1L;

		{
			add(new FishItemSet(Material.LAPIS_ORE, 2.5, 1, 5.0, 7));
			add(new FishItemSet(Material.LEATHER_BOOTS, 3.0, 1, 0.5, 1));
			add(new FishItemSet(Material.LEATHER_HELMET, 3.0, 1, 0.5, 1));
			add(new FishItemSet(Material.PAPER, 2.5, 1, 0.5, 12));
			add(new FishItemSet(Material.SUGAR_CANE, 3.0, 2, 1.0, 12));
			add(new FishItemSet(Material.FEATHER, 3.0, 2, 1.0, 12));
		}
	};
	public static ArrayList<FishItemSet> fishListTier1 = new ArrayList<FishItemSet>() {
		private static final long serialVersionUID = 1L;

		{
			add(new FishItemSet(Material.LEATHER, 4.0, 1, 4.0, 22));
			add(new FishItemSet(Material.ARROW, 3.0, 5, 1.0, 65));
			add(new FishItemSet(Material.APPLE, 2.0, 1, 5.0, 10));
			add(new FishItemSet(Material.CARROT_ITEM, 4.0, 1, 2.0, 18));
			add(new FishItemSet(Material.MELON, 2.0, 1, 1.0, 25));
			add(new FishItemSet(Material.RAW_BEEF, 4.0, 1, 2.0, 20));
			add(new FishItemSet(Material.RAW_CHICKEN, 4.0, 1, 2.0, 20));
		}
	};

	public static void generateFishedItem(ItemStack istack, int currentLevel) {
		/*
		 * This method will go through a cloned/shuffed list of fishable items
		 * and try to select one. It will base the percentage off the fisher job
		 * level as a ratio.
		 */
		final double BUFFER = 0.3; // Add a small buffer to allow for a
									// realistic chance at the max level
		Random randGen = new Random();
		ArrayList<FishItemSet> cloneList = new ArrayList<FishItemSet>();
		if (currentLevel >= TIER1_LEVEL)
			cloneList.addAll(fishListTier1);
		if (currentLevel >= TIER2_LEVEL)
			cloneList.addAll(fishListTier2);
		if (currentLevel >= TIER3_LEVEL)
			cloneList.addAll(fishListTier3);
		if (currentLevel >= TIER4_LEVEL)
			cloneList.addAll(fishListTier4);
		if (currentLevel >= TIER5_LEVEL)
			cloneList.addAll(fishListTier5);
		if (currentLevel >= TIER6_LEVEL)
			cloneList.addAll(fishListTier6);
		Collections.shuffle(cloneList);

		for (FishItemSet fis : cloneList) {
			double chanceForItem = (((fis.getFinalRate() - fis.getStartRate()) / (double) MAX_LEVEL)
					* (double) currentLevel) + fis.getStartRate();
			double maxQty = (((fis.getFinalQty() - fis.getStartQty()) / MAX_LEVEL) * currentLevel) + fis.getStartQty()
					+ BUFFER;
			int chosenQty = (int) (randGen.nextDouble() * maxQty + 0.5);

			if (MonstersPlus.randomChance(chanceForItem)) {
				/*
				 * for(Enchantment ench : istack.getEnchantments().keySet())
				 * istack.removeEnchantment(ench);
				 * 
				 * istack.setType(fis.getMaterial());
				 * istack.setAmount(chosenQty);
				 */

				/*
				 * if(fis.getMaterial() == Material.GOLDEN_APPLE)
				 * istack.setDurability((short)0);
				 */

				/*
				 * if(istack.getDurability() > 1 && istack.getDurability() < 5)
				 * istack.setDurability((short)0);
				 */

				ItemStack tempStack = new ItemStack(fis.getMaterial(), chosenQty);
				istack.setDurability(tempStack.getDurability());
				istack.setAmount(tempStack.getAmount());
				istack.setData(tempStack.getData());
				istack.setItemMeta(tempStack.getItemMeta());
				istack.setType(tempStack.getType());

				if (istack.getType() == Material.MOB_SPAWNER) {
					istack.setDurability((short) 54);
					istack.setDurability(getMobSpawnerDur());
				}

				return;
			}
		}
	}

	public static short getMobSpawnerDur() {
		Short[] ar = new Short[] { 50, 51, 52, 54, 57, 58, 59, 60, 61, 66 };
		ArrayList<Short> list = new ArrayList<Short>(Arrays.asList(ar));
		Collections.shuffle(list);
		return list.get(0).shortValue();
	}

	public static class FishItemSet {
		/*
		 * The startRate is for a Fisher of level 1, finalRate is for a max
		 * level fisher The startQty is the max amount of drops for a new fisher
		 * The finalQty is the max amount of drops for an expert fixher
		 */
		private Material item;
		private short durab;
		private double startRate;
		private int startQty;
		private double finalRate;
		private int finalQty;

		public FishItemSet(Material item, short durab, double startRate, int startQty, double finalRate, int finalQty) {
			this.item = item;
			this.durab = durab;
			this.startRate = startRate;
			this.startQty = startQty;
			this.finalRate = finalRate;
			this.finalQty = finalQty;
		}

		public FishItemSet(Material item, double startRate, int startQty, double finalRate, int finalQty) {
			this(item, (short) 0, startRate, startQty, finalRate, finalQty);
		}

		public Material getMaterial() {
			return item;
		}

		public short getDurability() {
			return durab;
		}

		public double getStartRate() {
			return startRate;
		}

		public double getStartQty() {
			return startQty;
		}

		public double getFinalRate() {
			return finalRate;
		}

		public double getFinalQty() {
			return finalQty;
		}

		public void setMaterial(Material t) {
			item = t;
		}

		public void setDurability(short t) {
			durab = t;
		}

		public void setStartRate(double d) {
			startRate = d;
		}

		public void setStartQty(int i) {
			startQty = i;
		}

		public void setFinalRate(double d) {
			finalRate = d;
		}

		public void setFinalQty(int i) {
			finalQty = i;
		}
	}

}
