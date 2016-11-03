package me.coolade.jobsplus.customenchanting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.coolade.monstersplus.RomanNumeral;
import me.coolade.monstersplus.Tools;

public class CustomEnchantment {
	private ArrayList<EnchantmentSet> enchantmentList = new ArrayList<>();

	public CustomEnchantment() {
		/**
		 * The list of all the custom enchantments that have been added into the
		 * game.
		 */
		enchantmentList.add(new EnchantmentSet("Ice Aspect", 2, "Warrior", new int[] { 7, 16 }, new int[] { 30, 30 },
				new int[] { 4, 8 }, EnchantableItems.MELEE, new String[] { "None" },
				"Slows the target for a short time (Swords,Axes)"));

		enchantmentList.add(new EnchantmentSet("Eye Gouge", 3, "Warrior", new int[] { 10, 18, 24 },
				new int[] { 30, 30, 30 }, new int[] { 3, 5, 7 }, EnchantableItems.MELEE, new String[] { "None" },
				"Blinds the target (Swords,Axes)"));

		enchantmentList.add(new EnchantmentSet("Confusing Aspect", 3, "Warrior", new int[] { 3, 9, 15 },
				new int[] { 15, 30, 30 }, new int[] { 2, 4, 6 }, EnchantableItems.MELEE, new String[] { "None" },
				"Causes the target to suffer from level 5 confusion (Swords,Axes)"));

		enchantmentList.add(new EnchantmentSet("Wither Aspect", 2, "Warrior", new int[] { 5, 15 }, new int[] { 30, 30 },
				new int[] { 4, 8 }, EnchantableItems.MELEE, new String[] { "Toxic Aspect" },
				"Causes the target to suffer from wither (Swords,Axes)"));

		enchantmentList.add(new EnchantmentSet("Lifesteal", 2, "Survivalist", new int[] { 10, 22 },
				new int[] { 30, 35 }, new int[] { 4, 8 }, EnchantableItems.MELEE, new String[] { "None" },
				"Has a chance to steal health on every hit. (Swords,Axes)"));

		enchantmentList.add(new EnchantmentSet("Life Shot", 2, "Survivalist", new int[] { 8, 18 }, new int[] { 30, 35 },
				new int[] { 4, 8 }, EnchantableItems.BOW, new String[] { "None" },
				"Has a chance to steal health on every hit. (Bows)"));

		enchantmentList.add(new EnchantmentSet("Ice Shot", 2, "Survivalist", new int[] { 7, 16 }, new int[] { 30, 30 },
				new int[] { 4, 8 }, EnchantableItems.BOW, new String[] { "None" },
				"Slows the target for a short time (Bows)"));

		enchantmentList.add(new EnchantmentSet("Retina", 3, "Survivalist", new int[] { 10, 18, 24 },
				new int[] { 30, 30, 30 }, new int[] { 3, 5, 7 }, EnchantableItems.BOW, new String[] { "None" },
				"Blinds the target (Bows)"));

		enchantmentList.add(new EnchantmentSet("Wither Shot", 2, "Survivalist", new int[] { 5, 15 },
				new int[] { 30, 30 }, new int[] { 4, 8 }, EnchantableItems.BOW, new String[] { "Poison Shot" },
				"Causes the target to suffer from Wither (Bows)"));

		enchantmentList.add(new EnchantmentSet("Drunk Shot", 3, "Survivalist", new int[] { 3, 9, 15 },
				new int[] { 15, 30, 30 }, new int[] { 2, 4, 6 }, EnchantableItems.BOW, new String[] { "None" },
				"Causes the target to suffer from level 5 confusion (Bows)"));

		enchantmentList.add(new EnchantmentSet("Toxic Aspect", 2, "Witchdoctor", new int[] { 8, 21 },
				new int[] { 30, 30 }, new int[] { 4, 8 }, EnchantableItems.MELEE, new String[] { "Wither Aspect" },
				"Causes the target to suffer from Poison (Swords,Axes)"));

		enchantmentList.add(new EnchantmentSet("Poison Shot", 2, "Witchdoctor", new int[] { 6, 18 },
				new int[] { 30, 30 }, new int[] { 4, 8 }, EnchantableItems.BOW, new String[] { "Wither Shot" },
				"Causes the target to suffer from Poison (Bows)"));

		enchantmentList
				.add(new EnchantmentSet("Molten Shell", 2, "Blacksmith", new int[] { 2, 10 }, new int[] { 20, 20 },
						new int[] { 2, 4 }, EnchantableItems.ARMOR, new String[] { "Wither Shell", "Poison Shell" },
						"A chance that the attacker will catch on fire. (Armor)"));
		enchantmentList
				.add(new EnchantmentSet("Wither Shell", 2, "Blacksmith", new int[] { 6, 14 }, new int[] { 20, 20 },
						new int[] { 2, 4 }, EnchantableItems.ARMOR, new String[] { "Molten Shell", "Poison Shell" },
						"A chance that the attacker will suffer from Wither. (Armor)"));
		enchantmentList
				.add(new EnchantmentSet("Poison Shell", 2, "Blacksmith", new int[] { 8, 20 }, new int[] { 20, 20 },
						new int[] { 2, 4 }, EnchantableItems.ARMOR, new String[] { "Molten Shell", "Wither Shell" },
						"A chance that the attacker will suffer from Poison. (Armor)"));

		enchantmentList.add(new EnchantmentSet("Poverty Barrier", 2, "Blacksmith", new int[] { 3, 11 },
				new int[] { 20, 20 }, new int[] { 2, 4 }, EnchantableItems.ARMOR,
				new String[] { "Confusing Barrier", "Blinding Barrier" },
				"A chance that the attacker will suffer from Extreme Hunger. (Armor)"));
		enchantmentList.add(new EnchantmentSet("Confusing Barrier", 2, "Blacksmith", new int[] { 4, 12 },
				new int[] { 20, 20 }, new int[] { 2, 4 }, EnchantableItems.ARMOR,
				new String[] { "Poverty Barrier", "Blinding Barrier" },
				"A chance that the attacker will suffer from Extreme Confusion. (Armor)"));
		enchantmentList.add(new EnchantmentSet("Blinding Barrier", 2, "Blacksmith", new int[] { 9, 22 },
				new int[] { 20, 20 }, new int[] { 2, 4 }, EnchantableItems.ARMOR,
				new String[] { "Poverty Barrier", "Confusing Barrier" },
				"A chance that the attacker will suffer from Blindness. (Armor)"));

		enchantmentList
				.add(new EnchantmentSet("Regeneration Aura", 2, "Blacksmith", new int[] { 5, 13 }, new int[] { 20, 20 },
						new int[] { 2, 4 }, EnchantableItems.ARMOR, new String[] { "Health Aura", "Berserk Aura" },
						"When attacked you have a chance to start regenerating health. (Armor)"));
		enchantmentList.add(new EnchantmentSet("Health Aura", 2, "Blacksmith", new int[] { 7, 15 },
				new int[] { 20, 20 }, new int[] { 2, 4 }, EnchantableItems.ARMOR,
				new String[] { "Regeneration Aura", "Berserk Aura" },
				"When attacked you have a chance to gain bonus health. (Armor)"));
		enchantmentList
				.add(new EnchantmentSet("Berserk Aura", 2, "Blacksmith", new int[] { 8, 18 }, new int[] { 20, 20 },
						new int[] { 2, 4 }, EnchantableItems.ARMOR, new String[] { "Regeneration Aura", "Health Aura" },
						"When attacked you have a chance to gain bonus strength. (Armor)"));

		enchantmentList.add(new EnchantmentSet("Fire Hook", 3, "Fisherman", new int[] { 4, 9, 14 },
				new int[] { 15, 0, 30 }, new int[] { 0, 1, 2 }, EnchantableItems.FISHINGROD, new String[] { "None" },
				"Causes burn damage to the target (Fishing Rod)"));

		enchantmentList.add(new EnchantmentSet("Sharp Hook", 5, "Fisherman", new int[] { 3, 5, 9, 13, 19 },
				new int[] { 8, 20, 30, 15, 30 }, new int[] { 0, 0, 0, 2, 4 }, EnchantableItems.FISHINGROD,
				new String[] { "None" }, "Increases damage (Fishing Rod)"));

		enchantmentList.add(new EnchantmentSet("Molten Hook", 1, "Fisherman", new int[] { 7 }, new int[] { 10 },
				new int[] { 1 }, EnchantableItems.FISHINGROD, new String[] { "Silk Hook" },
				"Smelts or Cooks the items (Fishing Rod)"));

		enchantmentList.add(new EnchantmentSet("Silk Hook", 1, "Fisherman", new int[] { 11 }, new int[] { 15 },
				new int[] { 1 }, EnchantableItems.FISHINGROD, new String[] { "Molten Hook" },
				"Causes Silktouch to any item caught."));

		enchantmentList.add(new EnchantmentSet("Molten Touch", 1, "Miner", new int[] { 5 }, new int[] { 15 },
				new int[] { 1 }, EnchantableItems.TOOLS, new String[] { "LOOT_BONUS_BLOCKS" },
				"Smelts items when you break them: Iron Ore - Iron Ingot, Gold Ore - Gold Ingot, Log - Charcoal, Cobblestone - Stone, Mossy Cobblestone - Moss Stone Brick (Tools), Sand - Glass, Clay - Hard Clay, Cactus - Cactus Green Dye"));
		enchantmentList.add(new EnchantmentSet("Lucky Break", 4, "Miner", new int[] { 3, 6, 10, 15 },
				new int[] { 20, 25, 30, 30 }, new int[] { 1, 2, 4, 6 }, EnchantableItems.PICKAXES,
				new String[] { "None" }, "Grants additional experience orbs when mining (Pickaxes)"));
		enchantmentList.add(new EnchantmentSet("Smash", 3, "Miner", new int[] { 4, 12, 18 }, new int[] { 20, 30, 30 },
				new int[] { 0, 2, 4 }, EnchantableItems.PICKAXES, new String[] { "None" },
				"Gives a small chance to remove a large radius of Stone or Gravel whenever mining those blocks. (Pickaxes)"));

		enchantmentList.add(new EnchantmentSet("Sheer Luck", 3, "Farmer", new int[] { 3, 8, 14 },
				new int[] { 15, 10, 30 }, new int[] { 1, 2, 3 }, EnchantableItems.SHEARS, new String[] { "None" },
				"Bonus chance to gain additional wool when shearing, Bonus chance to gain feathers, eggs, and leather when punching chickens and cows respectively (Shears)"));

		enchantmentList.add(new EnchantmentSet("Dazzle", 1, "Farmer", new int[] { 7 }, new int[] { 30 },
				new int[] { 1 }, EnchantableItems.SHEARS, new String[] { "None" },
				"Randomly changes the color of wool (Shears)"));

		enchantmentList.add(new EnchantmentSet("Papercut", 1, "Farmer", new int[] { 9 }, new int[] { 15 },
				new int[] { 1 }, EnchantableItems.SHEARS, new String[] { "None" },
				"Allows harvesting of Web and dead bush (Shears)"));

		enchantmentList.add(new EnchantmentSet("Tilling", 5, "Farmer", new int[] { 3, 6, 10, 14, 18 },
				new int[] { 15, 25, 20, 30, 30 }, new int[] { 0, 0, 2, 4, 6 }, EnchantableItems.HOES,
				new String[] { "None" }, "Tills larger areas (Your Mom, aka hoes)"));

		enchantmentList.add(new EnchantmentSet("Hydration", 5, "Farmer", new int[] { 3, 5, 9, 15, 25 },
				new int[] { 15, 25, 20, 30, 30 }, new int[] { 0, 0, 2, 4, 6 }, EnchantableItems.HOES,
				new String[] { "None" }, "Hydrate soil in a large radius (Your Mom, aka hoes)"));

		enchantmentList.add(new EnchantmentSet("Seed Finder", 5, "Farmer", new int[] { 4, 6, 11, 20, 23 },
				new int[] { 15, 25, 20, 30, 30 }, new int[] { 0, 0, 2, 4, 6 }, EnchantableItems.HOES,
				new String[] { "None" },
				"Gives a chance to find random seeds while tilling soil (Your Mom, aka hoes)"));

		enchantmentList.add(new EnchantmentSet("Puberty", 1, "Farmer", new int[] { 10 }, new int[] { 20 },
				new int[] { 2 }, EnchantableItems.TOOLS, new String[] { "None" },
				"Punch babies into adults and adults into babies (Tools)"));

		enchantmentList.add(new EnchantmentSet("Blackhole", 1, "Builder", new int[] { 7 }, new int[] { 0 },
				new int[] { 2 }, EnchantableItems.EMPTYBUCKET, new String[] { "Flow" },
				"A bucket that can never be filled (Empty Bucket)"));

		enchantmentList.add(new EnchantmentSet("Flow", 1, "Builder", new int[] { 12 }, new int[] { 0 }, new int[] { 2 },
				EnchantableItems.WATERBUCKET, new String[] { "Blackhole" },
				"A bucket that never runs out of water (Water Bucket)"));
	}

	public ArrayList<EnchantmentSet> getEnchantmentList() {
		/**
		 * Returns the entire list of enchantments that have been added into the
		 * game
		 */
		return enchantmentList;
	}

	public EnchantmentSet getEnchantmentSet(String name) {
		/**
		 * Returns a specific enchantmentSet from the enchantment list based on
		 * the name of the eset.
		 */
		for (EnchantmentSet es : enchantmentList) {
			if (es.getName().equalsIgnoreCase(name.toUpperCase())) {
				return es;
			}
		}
		return null;
	}

	public boolean isValidEnchantType(ItemStack item, EnchantmentSet eset) {

		/**
		 * Checks to see if the ItemStack is valid for a specific
		 * enchantmentSet, this can be based on if the enchantment is meant for
		 * armor, tools, weapons, etc.
		 */
		String[] validItems = eset.getValidItems();
		if (validItems == null) {
			return true;
		}

		for (int i = 0; i < validItems.length; i++) {
			Material tempMat = Material.getMaterial(validItems[i]);
			// if(tempMat == null)
			// Bukkit.broadcastMessage("isValidEnchantItem could not find: " +
			// validItems[i]);
			if (item.getType() == tempMat) {
				return true;
			}
		}
		return false;
	}

	public boolean hasNoConflictingEnchantments(ItemStack item, EnchantmentSet eset) {
		/**
		 * Checks an ItemStack to make sure that it does not have any
		 * conflicting enchantments with a specific enchantmentSet.
		 */
		if (item == null) {
			return false;
		}

		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		String[] conflicEnchs = eset.getConflictingEnchantments();

		if (conflicEnchs == null) {
			return true;
		}

		// Check all the custom enchantments first
		if (lore != null) {
			List<EnchantmentSetOnItem> esois = getCustomEnchantments(item);
			for (int i = 0; i < conflicEnchs.length; i++) {
				if (hasCustomEnchantmentType(esois, conflicEnchs[i])) {
					return false;
				}
			}
		}

		// Check all the bukkit enchantments
		for (int i = 0; i < conflicEnchs.length; i++) {
			Enchantment ench = Enchantment.getByName(conflicEnchs[i]);
			if (ench != null) {
				if (item.containsEnchantment(ench)) {
					return false;
				}
			}
		}
		return true;
	}

	public List<EnchantmentSetOnItem> getCustomEnchantments(ItemStack item) {
		/**
		 * This method returns all of the enchantments on a specific item, It
		 * returns it as a map with enchantments, and the rank of the
		 * enchantment UNTESTED: NEEDS A CHECK based on if the enchantment has a
		 * hidden color code.
		 */
		List<EnchantmentSetOnItem> esets = new ArrayList<EnchantmentSetOnItem>();
		if (!item.hasItemMeta() || !item.getItemMeta().hasLore()) {
			return esets;
		}

		List<String> lore = item.getItemMeta().getLore();
		for (String s : lore) {
			// Strip apart the string so that we can get the enchantment and its
			// roman numeral rank
			String strippedString = ChatColor.stripColor(s);
			boolean isValid = false;
			String name = "";
			int rank = 0;
			try {
				int spaceIndex = strippedString.lastIndexOf(' ');
				if (spaceIndex > -1) {
					name = strippedString.substring(0, spaceIndex);
					rank = RomanNumeral.valueOf(strippedString.substring(spaceIndex + 1));
					isValid = true;
				}
			} catch (Exception e) {
			}

			if (isValid) {
				EnchantmentSet eset = this.getEnchantmentSet(name);
				if (eset != null) {
					esets.add(new EnchantmentSetOnItem(eset, rank));
				}
			}
		}
		return esets;
	}

	public ItemStack addCustomEnchantment(ItemStack istack, String enchName, int rank) {
		/**
		 * Using the name of the enchantmentSet, and Item, and a Rank, this
		 * method attempts to add the enchantment onto the item.
		 */
		if (istack == null || istack.getType() == Material.AIR) {
			return null;
		}

		EnchantmentSet eset = this.getEnchantmentSet(enchName);
		if (eset == null) {
			return null;
		}

		// Remove any previous versions of the enchantment
		removeCustomEnchantment(istack, enchName);
		ItemMeta meta = istack.getItemMeta();
		List<String> lore = meta.getLore();
		if (lore == null) {
			lore = new ArrayList<String>();
		}
		lore.add(ChatColor.GRAY + eset.getName() + " " + RomanNumeral.convertToRoman(rank));

		meta.setLore(lore);
		istack.setItemMeta(meta);

		if (istack.getEnchantments().isEmpty()) {
			// If there are no enchantments on the item, then we add this custom
			// glow effect.
			ItemStack glowStack = Tools.addGlow(istack);
			return glowStack;
		}
		return istack;

	}

	public boolean removeCustomEnchantment(ItemStack istack, String enchName) {
		/**
		 * Removes a custom enchantment from an item, returns true if the
		 * enchantment was removed.
		 */
		if (istack == null || istack.getType() == Material.AIR || !istack.hasItemMeta()
				|| !istack.getItemMeta().hasLore()) {
			return false;
		}

		ItemMeta meta = istack.getItemMeta();
		List<String> lore = meta.getLore();
		for (int i = 0; i < lore.size(); i++) {
			if (lore.get(i).contains(enchName)) {
				lore.remove(i);
				i--;
			}
		}
		meta.setLore(lore);
		istack.setItemMeta(meta);
		return false;
	}

	public class EnchantmentSet {
		private String name;
		private int maxLevel;
		private String jobName;
		private int[] jobLevels;
		private int[] expsRequired;
		private int[] powerstones;
		private String[] validItems;
		private String[] conflictingEnchantments;
		private String description;

		public EnchantmentSet(String name, int maxLevel, String jobName, int[] jobLevels, int[] expsRequired,
				int[] powerstones, String[] validItems, String[] conflictingEnchantments, String description) {
			this.name = name;
			this.maxLevel = maxLevel;
			this.jobName = jobName;
			this.jobLevels = jobLevels;
			this.expsRequired = expsRequired;
			this.powerstones = powerstones;
			this.validItems = validItems;
			this.conflictingEnchantments = conflictingEnchantments;
			this.description = description;
		}

		public EnchantmentSet(String name, int maxLevel, String jobName, int[] jobLevels, int[] expRequired,
				int[] powerstones, String description) {
			this(name, maxLevel, jobName, jobLevels, expRequired, powerstones, null, null, description);
		}

		public String getName() {
			return name;
		}

		public int getMaxLevel() {
			return maxLevel;
		}

		public String getJobName() {
			return jobName;
		}

		public int[] getJobLevels() {
			return jobLevels;
		}

		public int[] getExpsRequired() {
			return expsRequired;
		}

		public int[] getPowerstones() {
			return powerstones;
		}

		public String[] getValidItems() {
			return validItems;
		}

		public String[] getConflictingEnchantments() {
			return conflictingEnchantments;
		}

		public String getDescription() {
			return description;
		}
	}

	public class EnchantmentSetOnItem {
		/**
		 * Represents a customenchantment on a specific item, holds the Custom
		 * enchantment as an EnchantmentSet and holds the rank of the
		 * enchantment on the item as Rank.
		 */
		private EnchantmentSet enchantmentSet;
		private int rank;

		public EnchantmentSetOnItem(EnchantmentSet enchantmentSet, int rank) {
			this.enchantmentSet = enchantmentSet;
			this.rank = rank;
		}

		public EnchantmentSet getEnchantmentSet() {
			return enchantmentSet;
		}

		public int getRank() {
			return rank;
		}

		public void setEnchantmentSet(EnchantmentSet eset) {
			enchantmentSet = eset;
		}

		public void setRank(int t) {
			rank = t;
		}

		@Override
		public String toString() {
			return enchantmentSet + ", " + rank;
		}
	}

	public boolean hasCustomEnchantmentType(List<EnchantmentSetOnItem> esoiList, String enchName) {
		for (EnchantmentSetOnItem esoi : esoiList) {
			if (esoi.getEnchantmentSet().getName().equalsIgnoreCase(enchName)) {
				return true;
			}
		}
		return false;
	}

	public EnchantmentSetOnItem getCustomEnchantmentType(List<EnchantmentSetOnItem> esoiList, String enchName) {
		for (EnchantmentSetOnItem esoi : esoiList) {
			if (esoi.getEnchantmentSet().getName().equalsIgnoreCase(enchName)) {
				return esoi;
			}
		}
		return null;
	}

	public List<EnchantmentSetOnItem> combineSimilarEnchantments(ItemStack[] items) {
		ArrayList<EnchantmentSetOnItem> totalEnchs = new ArrayList<EnchantmentSetOnItem>();

		for (ItemStack item : items) {
			List<EnchantmentSetOnItem> tempEnchs = this.getCustomEnchantments(item);

			/*
			 * Cycle through all of the enchantments on this specific item Cycle
			 * through all of the enchantments that have been added into the
			 * total, if it has been added to the total already then we need to
			 * update the totals rank. If it has not been added then we need to
			 * add it with this rank.
			 */
			boolean addToList = true;
			for (int i = 0; i < tempEnchs.size(); i++) {
				EnchantmentSetOnItem tempEnch = tempEnchs.get(i);

				for (int j = 0; j < totalEnchs.size(); j++) {
					EnchantmentSetOnItem totalEnch = totalEnchs.get(j);
					if (tempEnch.getEnchantmentSet().getName().equals(totalEnch.getEnchantmentSet().getName())) {
						totalEnch.setRank(totalEnch.getRank() + tempEnch.getRank());
						addToList = false; // Since we found a match we don't
											// need to add it to the list, it is
											// already there
					}
				}
				if (addToList) {
					totalEnchs.add(tempEnch);
				}

				addToList = true; // Update it back
			}
		}
		return totalEnchs;
	}
}
