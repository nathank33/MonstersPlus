package me.coolade.monstersplus.monsters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.Tools;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class MonsterList {
	public static final Logger logger = Logger.getLogger("Minecraft");
	private static Random random = new Random();
	public static final double EASY_SPAWN = 27;
	public static final double MEDIUM_SPAWN = 19;
	public static final double HARD_SPAWN = 11;
	public static final double LEGENDARY_SPAWN = 1.5;
	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;
	public static final int LEGENDARY = 4;
	public static final int SPECIAL = 5; // Handled directly inside of specific
											// file
	public static final double PSTONE_NORMAL = 0;
	public static final double PSTONE_EASY = 2.0;
	public static final double PSTONE_MEDIUM = 3.75;
	public static final double PSTONE_HARD = 15;
	public static final double PSTONE_LEGENDARY = 125;
	public static ArrayList<MonsterStruct> listOfMonsters = new ArrayList<MonsterStruct>() {
		private static final long serialVersionUID = 1L;

		{

			add(new MonsterStruct("Zombie Charger", 30, EntityType.ZOMBIE, EASY));
			add(new MonsterStruct("Burning Walker", 31, EntityType.ZOMBIE, EASY, BiomeSets.HOT));
			add(new MonsterStruct("Lumber Zombie", 32, EntityType.ZOMBIE, EASY));
			add(new MonsterStruct("Zombie Reaper", 33, EntityType.ZOMBIE, EASY));
			add(new MonsterStruct("Zombie Fisherman", 34, EntityType.ZOMBIE, EASY, BiomeSets.WATERFRONT));
			add(new MonsterStruct("Rotten Fighter", 35, EntityType.ZOMBIE, EASY));
			add(new MonsterStruct("Lame Brain", 36, EntityType.ZOMBIE, EASY, BiomeSets.SWAMP));
			add(new MonsterStruct("Zombie Climber", 37, EntityType.ZOMBIE, EASY, BiomeSets.EXTREME_HILLS));
			add(new MonsterStruct("Petty Thief", 38, EntityType.ZOMBIE, EASY));
			add(new MonsterStruct("The Restless", 51, EntityType.ZOMBIE, MEDIUM));
			add(new MonsterStruct("Stink Swarm", 52, EntityType.ZOMBIE, MEDIUM));
			add(new MonsterStruct("Lunatic", 53, EntityType.ZOMBIE, MEDIUM));
			add(new MonsterStruct("Undead Monk", 54, EntityType.ZOMBIE, MEDIUM));
			add(new MonsterStruct("Famished Lurker", 55, EntityType.ZOMBIE, MEDIUM, BiomeSets.HOT));
			add(new MonsterStruct("Cold Corpse", 56, EntityType.ZOMBIE, MEDIUM, BiomeSets.COLD));
			add(new MonsterStruct("Rotting Jack", 57, EntityType.ZOMBIE, MEDIUM));
			add(new MonsterStruct("Frosted Biter", 58, EntityType.ZOMBIE, MEDIUM, BiomeSets.COLD));
			add(new MonsterStruct("Cactuar", 60, EntityType.ZOMBIE, MEDIUM, BiomeSets.HOT));
			add(new MonsterStruct("Cactii", 61, EntityType.ZOMBIE, MEDIUM, BiomeSets.HOT));
			add(new MonsterStruct("Burnt Ghoul", 100, EntityType.ZOMBIE, HARD, BiomeSets.HOT));
			add(new MonsterStruct("Undying", 101, EntityType.ZOMBIE, HARD));
			add(new MonsterStruct("Arctic Zed", 102, EntityType.ZOMBIE, HARD, BiomeSets.COLD));
			add(new MonsterStruct("Hardened Thief", 59, EntityType.ZOMBIE, HARD));
			add(new MonsterStruct("Royal Thief", 103, EntityType.ZOMBIE, LEGENDARY));
			add(new MonsterStruct("Qactuar", 104, EntityType.ZOMBIE, HARD, BiomeSets.HOT));

			add(new MonsterStruct("Skeleton Knight", 30, EntityType.SKELETON, EASY));
			add(new MonsterStruct("Skeleton Fighter", 31, EntityType.SKELETON, EASY));
			add(new MonsterStruct("Bone Soldier", 32, EntityType.SKELETON, EASY));
			add(new MonsterStruct("Cranial Basher", 33, EntityType.SKELETON, EASY));
			add(new MonsterStruct("Skeleton Pirate", 34, EntityType.SKELETON, EASY, BiomeSets.WATERFRONT));
			add(new MonsterStruct("Skeleton Fisherman", 35, EntityType.SKELETON, EASY, BiomeSets.WATERFRONT));
			add(new MonsterStruct("Hot Bones", 36, EntityType.SKELETON, EASY, BiomeSets.HOT));
			add(new MonsterStruct("Dead Gardener", 37, EntityType.SKELETON, EASY));
			add(new MonsterStruct("Flayed", 50, EntityType.SKELETON, MEDIUM));
			add(new MonsterStruct("Skeleton Champion", 51, EntityType.SKELETON, MEDIUM));
			add(new MonsterStruct("Holy Skeleton", 52, EntityType.SKELETON, MEDIUM));
			add(new MonsterStruct("Iced Sharpshooter", 53, EntityType.SKELETON, MEDIUM, BiomeSets.COLD));
			add(new MonsterStruct("Dazzler", 54, EntityType.SKELETON, MEDIUM, BiomeSets.SWAMP));
			add(new MonsterStruct("Famished Walker", 55, EntityType.SKELETON, MEDIUM, BiomeSets.HOT));
			add(new MonsterStruct("Cinderton", 56, EntityType.SKELETON, MEDIUM, BiomeSets.HOT));
			add(new MonsterStruct("Sumo Skeleton", 57, EntityType.SKELETON, MEDIUM));
			add(new MonsterStruct("Death Knight", 97, EntityType.SKELETON, HARD));
			add(new MonsterStruct("Arsonic Archer", 93, EntityType.SKELETON, HARD));
			add(new MonsterStruct("Skeleton Sniper", 100, EntityType.SKELETON, HARD));
			add(new MonsterStruct("Miner of Death", 101, EntityType.SKELETON, HARD, BiomeSets.EXTREME_HILLS));
			add(new MonsterStruct("Spinechiller", 102, EntityType.SKELETON, HARD, BiomeSets.COLD));
			add(new MonsterStruct("Pyre", 103, EntityType.SKELETON, HARD, BiomeSets.HOT));
			add(new MonsterStruct("Bad Blacksmith", 104, EntityType.SKELETON, HARD));
			add(new MonsterStruct("Crazed Skeleton", 105, EntityType.SKELETON, HARD, BiomeSets.SWAMP));
			add(new MonsterStruct("Executioner", 106, EntityType.SKELETON, HARD));
			add(new MonsterStruct("Archfiend", 107, EntityType.SKELETON, HARD));

			add(new MonsterStruct("Black Widow", 30, EntityType.SPIDER, EASY));
			add(new MonsterStruct("Witch Apprentice", 31, EntityType.SPIDER, EASY));
			add(new MonsterStruct("Mountain Pincer", 32, EntityType.SPIDER, EASY, BiomeSets.EXTREME_HILLS));
			add(new MonsterStruct("Scorpion", 33, EntityType.SPIDER, EASY));
			add(new MonsterStruct("Stinger", 50, EntityType.SPIDER, MEDIUM));
			add(new MonsterStruct("Tarantula", 51, EntityType.SPIDER, MEDIUM));
			add(new MonsterStruct("Rock Scorpion", 53, EntityType.SPIDER, MEDIUM));
			add(new MonsterStruct("Witch of the East", 54, EntityType.SPIDER, MEDIUM));
			add(new MonsterStruct("Desert Scorpion", 55, EntityType.SPIDER, MEDIUM, BiomeSets.HOT));
			add(new MonsterStruct("Ice Spider", 56, EntityType.SPIDER, MEDIUM, BiomeSets.COLD));
			add(new MonsterStruct("Warlock", 57, EntityType.SPIDER, MEDIUM));
			add(new MonsterStruct("Brown Recluse", 100, EntityType.SPIDER, HARD));
			add(new MonsterStruct("Witch of the West", 101, EntityType.SPIDER, HARD));
			add(new MonsterStruct("Water Stinger", 102, EntityType.SPIDER, HARD, BiomeSets.WATERFRONT));
			add(new MonsterStruct("Filth Purch", 103, EntityType.SPIDER, HARD, BiomeSets.SWAMP));
			add(new MonsterStruct("Swamp Witch", 104, EntityType.SPIDER, HARD, BiomeSets.SWAMP));
			add(new MonsterStruct("Dark Mage", 105, EntityType.SPIDER, HARD));

			add(new MonsterStruct("Squirt", 26, EntityType.CREEPER, EASY));
			add(new MonsterStruct("Heavy Creeper", 27, EntityType.CREEPER, EASY));
			add(new MonsterStruct("Droopy", 28, EntityType.CREEPER, EASY));
			add(new MonsterStruct("Hot Head", 29, EntityType.CREEPER, EASY));
			add(new MonsterStruct("Swollen Creeper", 65, EntityType.CREEPER, MEDIUM));
			add(new MonsterStruct("Heat Seeker", 23, EntityType.CREEPER, MEDIUM));
			add(new MonsterStruct("Creepillar", 29, EntityType.CREEPER, MEDIUM));
			add(new MonsterStruct("Starving Creeper", 41, EntityType.CREEPER, MEDIUM, BiomeSets.HOT));
			add(new MonsterStruct("Reforming Creeper", 38, EntityType.CREEPER, MEDIUM));
			add(new MonsterStruct("Cold Bomb", 33, EntityType.CREEPER, MEDIUM, BiomeSets.COLD));
			add(new MonsterStruct("Gas Bag", 53, EntityType.CREEPER, MEDIUM));
			add(new MonsterStruct("Vortex Creep", 35, EntityType.CREEPER, HARD));
			add(new MonsterStruct("Little Boy", 100, EntityType.CREEPER, HARD));
			add(new MonsterStruct("Quaker", 102, EntityType.CREEPER, HARD));

			add(new MonsterStruct("Atilla", 470, EntityType.ZOMBIE, LEGENDARY));
			add(new MonsterStruct("Sleepy Hollow", 510, EntityType.ZOMBIE, LEGENDARY));
			add(new MonsterStruct("Hyde", 480, EntityType.ZOMBIE, LEGENDARY));
			add(new MonsterStruct("Juggler", 201, EntityType.ZOMBIE, LEGENDARY));
			add(new MonsterStruct("Hocus", 560, EntityType.ZOMBIE, LEGENDARY));
			add(new MonsterStruct("Pocus", 590, EntityType.ZOMBIE, SPECIAL));
			add(new MonsterStruct("Achilles", 680, EntityType.ZOMBIE, LEGENDARY));
			add(new MonsterStruct("Piglet", 1354, EntityType.ZOMBIE, LEGENDARY));
			add(new MonsterStruct("Wilbur", 1350, EntityType.ZOMBIE, LEGENDARY));

			add(new MonsterStruct("Legolas", 550, EntityType.SKELETON, LEGENDARY));
			add(new MonsterStruct("Hawkeye", 570, EntityType.SKELETON, LEGENDARY));
			add(new MonsterStruct("Tickles", 505, EntityType.SKELETON, LEGENDARY));
			add(new MonsterStruct("Captain Ahab", 615, EntityType.SKELETON, LEGENDARY));

			add(new MonsterStruct("Infearno", 360, EntityType.SPIDER, LEGENDARY));
			add(new MonsterStruct("Hadamard", 1770, EntityType.SPIDER, LEGENDARY));
			add(new MonsterStruct("Lesath", 825, EntityType.SPIDER, LEGENDARY));
			add(new MonsterStruct("Shelob", 945, EntityType.SPIDER, LEGENDARY));
			add(new MonsterStruct("Arachne", 1022, EntityType.SPIDER, LEGENDARY));
			add(new MonsterStruct("Charlotte", 1021, EntityType.SPIDER, LEGENDARY));
			add(new MonsterStruct("Raikou", 1650, EntityType.SPIDER, LEGENDARY));
			add(new MonsterStruct("Entei", 1660, EntityType.SPIDER, LEGENDARY));
			add(new MonsterStruct("Suicune", 1670, EntityType.SPIDER, LEGENDARY));
			add(new MonsterStruct("Barkira", 1760, EntityType.SPIDER, LEGENDARY));
			// Giant Cactuar
			// Sabrina
			// Godly Thief

			add(new MonsterStruct("Frost Ant", 15, EntityType.SPIDER, SPECIAL));
			add(new MonsterStruct("Fire Ant", 13, EntityType.SPIDER, SPECIAL));
			add(new MonsterStruct("Toxic Ant", 14, EntityType.SPIDER, SPECIAL));
			add(new MonsterStruct("Iron Guardian", 100, EntityType.ZOMBIE, SPECIAL));
			add(new MonsterStruct("Undead Horse", 70, EntityType.HORSE, SPECIAL));
			add(new MonsterStruct("Skeleton Horse", 70, EntityType.HORSE, SPECIAL));
			add(new MonsterStruct("Bronco of Death", 125, EntityType.HORSE, SPECIAL));
			add(new MonsterStruct("Plague Stallion", 125, EntityType.HORSE, SPECIAL));
		}
	};
	public static ArrayList<MonsterTrophy> trophyList = new ArrayList<MonsterTrophy>() {
		private static final long serialVersionUID = 1L;

		{
			add(new MonsterTrophy("Atilla", ChatColor.RED + "Atilla Killa", new ItemStack(Material.TNT),
					Enchantment.PROTECTION_EXPLOSIONS, 3));
			add(new MonsterTrophy("Sleepy Hollow", ChatColor.DARK_BLUE + "Sleepy's Missing Head",
					new ItemStack(Material.SKULL_ITEM, 1, (short) 2), Enchantment.KNOCKBACK, 2));
			add(new MonsterTrophy("Hyde", ChatColor.AQUA + "Hyde's Hide", new ItemStack(Material.FIREBALL),
					Enchantment.PROTECTION_EXPLOSIONS, 2));
			add(new MonsterTrophy("Juggler", ChatColor.GOLD + "Juggler's Sticky Ball",
					new ItemStack(Material.SLIME_BALL), Enchantment.THORNS, 1));
			add(new MonsterTrophy("Hocus", ChatColor.GREEN + "Hocus' Book of Spells", new ItemStack(Material.BOOK),
					Enchantment.THORNS, 2));
			add(new MonsterTrophy("Pocus", ChatColor.DARK_RED + "Energy Rod of Pocus",
					new ItemStack(Material.BLAZE_ROD), Enchantment.DIG_SPEED, 2));
			add(new MonsterTrophy("Achilles", ChatColor.WHITE + "Achilles' Divine Arrow", new ItemStack(Material.ARROW),
					Enchantment.DAMAGE_UNDEAD, 3));
			add(new MonsterTrophy("Piglet", ChatColor.RED + "Piglet Bacon", new ItemStack(Material.PORK),
					Enchantment.DURABILITY, 2));
			add(new MonsterTrophy("Wilbur", ChatColor.GREEN + "Wilbur's Son",
					new ItemStack(Material.MONSTER_EGG, 1, (short) 90), Enchantment.ARROW_DAMAGE, 1));
			add(new MonsterTrophy("Legolas", ChatColor.DARK_GREEN + "Legolas' Leg", new ItemStack(Material.BONE),
					Enchantment.DURABILITY, 2));
			add(new MonsterTrophy("Hawkeye", ChatColor.DARK_AQUA + "Hawkeye's Eye",
					new ItemStack(Material.EYE_OF_ENDER), Enchantment.ARROW_DAMAGE, 1));
			add(new MonsterTrophy("Tickles", ChatColor.DARK_GRAY + "Tickle's Tickler", new ItemStack(Material.FEATHER),
					Enchantment.LOOT_BONUS_BLOCKS, 1));
			add(new MonsterTrophy("Captain Ahab", ChatColor.DARK_BLUE + "Ahab's Ancient Rod",
					new ItemStack(Material.FISHING_ROD), Enchantment.DAMAGE_ALL, 3));
			add(new MonsterTrophy("Infearno", ChatColor.DARK_RED + "Infearno Jelly",
					new ItemStack(Material.MAGMA_CREAM), Enchantment.ARROW_FIRE, 1));
			add(new MonsterTrophy("Hadamard", ChatColor.GOLD + "Hadamard Jelly", new ItemStack(Material.SLIME_BALL),
					Enchantment.ARROW_INFINITE, 1));
			add(new MonsterTrophy("Lesath", ChatColor.RED + "Lesath's Web", new ItemStack(Material.WEB),
					Enchantment.PROTECTION_ENVIRONMENTAL, 3));
			add(new MonsterTrophy("Shelob", ChatColor.YELLOW + "Shelob's Child", new ItemStack(Material.EGG),
					Enchantment.DAMAGE_ARTHROPODS, 2));
			add(new MonsterTrophy("Arachne", ChatColor.GOLD + "Arachne's Rotten Eyeball",
					new ItemStack(Material.FERMENTED_SPIDER_EYE), Enchantment.FIRE_ASPECT, 1));
			add(new MonsterTrophy("Charlotte", ChatColor.DARK_AQUA + "Charlotte's Web", new ItemStack(Material.WEB),
					Enchantment.ARROW_FIRE, 3));
			add(new MonsterTrophy("Raikou", ChatColor.AQUA + "Raikou's Collar", new ItemStack(Material.LEASH),
					Enchantment.PROTECTION_ENVIRONMENTAL, 2));
			add(new MonsterTrophy("Entei", ChatColor.DARK_RED + "Entei's Collar", new ItemStack(Material.LEASH),
					Enchantment.PROTECTION_ENVIRONMENTAL, 2));
			add(new MonsterTrophy("Suicune", ChatColor.DARK_BLUE + "Suicune's Collar", new ItemStack(Material.LEASH),
					Enchantment.PROTECTION_ENVIRONMENTAL, 2));
			add(new MonsterTrophy("Barkira", ChatColor.GRAY + "Barkira's Bone", new ItemStack(Material.BONE),
					Enchantment.DAMAGE_UNDEAD, 1));

		}
	};

	/*
	 * Manhattan DeathTouch Gas Bag Nightwalker
	 * 
	 */

	private MonsterList() {
	}

	public static class MonsterStruct {
		private String name;
		private double health;
		private EntityType monsterType;
		private int difficulty;
		private Biome[] biomes;

		public MonsterStruct(String name, double health, EntityType monsterType, int difficulty, Biome[] biomes) {
			this.name = name;
			this.health = health;
			this.monsterType = monsterType;
			this.difficulty = difficulty;
			this.biomes = biomes;
		}

		public MonsterStruct(String name, double health, EntityType monsterType, int difficulty) {
			this(name, health, monsterType, difficulty, null);
		}

		public String getName() {
			return name;
		}

		public double getMaxHealth() {
			return health;
		}

		public EntityType getMonsterType() {
			return monsterType;
		}

		public int getDifficulty() {
			return difficulty;
		}

		public void setName(String s) {
			name = s;
		}

		public void setMaxHealth(double h) {
			health = h;
		}

		public void setMonsterType(EntityType et) {
			monsterType = et;
		}

		public void setDifficulty(int t) {
			difficulty = t;
		}

		public Biome[] getBiomes() {
			return biomes;
		}

		public void setBiomes(Biome[] biomes) {
			this.biomes = biomes;
		}

	}

	public static class MonsterTrophy {
		private String mobName;
		private String displayName;
		private ItemStack item;
		private Enchantment ench;
		private int enchLevel;

		public MonsterTrophy(String mobName, String displayName, ItemStack item, Enchantment ench, int enchLevel) {
			this.mobName = mobName;
			this.displayName = displayName;
			this.item = item;
			this.ench = ench;
			this.enchLevel = enchLevel;
		}

		public MonsterTrophy(String mobName, String displayName, ItemStack item) {
			this(mobName, displayName, item, null, 1);
		}

		public String getMobName() {
			return mobName;
		}

		public String getDisplayName() {
			return displayName;
		}

		public ItemStack getItem() {
			return item;
		}

		public Enchantment getEnchantment() {
			return ench;
		}

		public int getEnchantmentLevel() {
			return enchLevel;
		}

		public void setMobName(String t) {
			mobName = t;
		}

		public void setDisplayName(String t) {
			displayName = t;
		}

		public void setItem(ItemStack t) {
			item = t;
		}

		public void setEnchantment(Enchantment t) {
			ench = t;
		}

		public void setEnchantmentLevel(int t) {
			enchLevel = t;
		}
	}

	public static double getMaxHealth(String name) {
		for (MonsterStruct monster : listOfMonsters) {
			if (monster.getName() == name) {
				return monster.getMaxHealth();
			}
		}
		return 0;
	}

	public static MonsterStruct getMonster(String name) {
		for (MonsterStruct monster : listOfMonsters) {
			if (monster.getName().toLowerCase().contains(name.toLowerCase())) {
				return monster;
			}
		}
		return null;
	}

	public static MonsterStruct getMonster(LivingEntity lent) {
		if (!MonsterList.hasMetaName(lent)) {
			return null;
		}

		String name = MonsterList.getMetaName(lent);
		for (MonsterStruct monster : listOfMonsters) {
			if (monster.getName().equals(name)) {
				return monster;
			}
		}
		return null;
	}

	public static int getMonsterDifficulty(LivingEntity lent) {
		MonsterStruct monStru = getMonster(lent);
		if (monStru != null) {
			return monStru.getDifficulty();
		}
		return -1;
	}

	public static boolean isCustomMonster(String name, LivingEntity lent) {
		if (hasCustomName(lent)) {
			MonsterStruct monster = getMonster(name);
			if (monster == null) {
				return false;
			}
			EntityType mType = monster.getMonsterType();
			EntityType lType = lent.getType();

			if (monster.getMaxHealth() == lent.getMaxHealth() && mType.equals(lType)) {
				return true;
			} else if (monster.getMaxHealth() == lent.getMaxHealth() && mType.equals(EntityType.SPIDER)
					&& !(lType.equals(EntityType.ZOMBIE) || lType.equals(EntityType.SKELETON)
							|| lType.equals(EntityType.CREEPER) || lType.equals(EntityType.ZOMBIE))) {
				return true;
			/*
			 * else if(monster.getMaxHealth() == lent.getMaxHealth() &&
			 * !lent.getType().equals(EntityType.SKELETON) &&
			 * !lent.getType().equals(EntityType.ZOMBIE) &&
			 * !lent.getType().equals(EntityType.SPIDER) &&
			 * !lent.getType().equals(EntityType.CREEPER)) return true;
			 */
			}
		}
		return false;
	}

	public static boolean isCustomMonster(LivingEntity lent) {
		if (hasCustomName(lent)) {
			for (MonsterStruct monStru : listOfMonsters) {
				if (monStru.getMaxHealth() == lent.getMaxHealth() && monStru.getMonsterType() == lent.getType()) {
					return true;
				} else if (monStru.getMaxHealth() == lent.getMaxHealth() && !lent.getType().equals(EntityType.SKELETON)
						&& !lent.getType().equals(EntityType.ZOMBIE) && !lent.getType().equals(EntityType.SPIDER)
						&& !lent.getType().equals(EntityType.CREEPER)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isValidBiome(Biome biome, Biome[] biomes) {

		/**
		 * Checks to see if the Biome is inside of the array of biomes.
		 */
		if (biome == null || biomes == null) {
			return true;
		}

		for (Biome b : biomes) {
			if (b == biome) {
				return true;
			}
		}
		return false;
	}

	public static String generateMonster(EntityType entType) {
		return generateMonster(entType, null);
	}

	public static String generateMonster() {
		EntityType[] types = new EntityType[] { EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER,
				EntityType.SPIDER };
		Random rand = new Random();
		EntityType type = types[rand.nextInt(types.length)];
		return generateMonster(type, null);
	}

	public static String generateMonster(EntityType entType, Biome biome) {
		/*
		 * This method generates a the name of the type of monster that should
		 * spawn
		 * 
		 */
		int chosenDifficulty = 0;
		double randomNum = random.nextDouble() * 100;
		if (randomNum < LEGENDARY_SPAWN) {
			chosenDifficulty = 4;
		} else if (randomNum < HARD_SPAWN + LEGENDARY_SPAWN) {
			chosenDifficulty = 3;
		} else if (randomNum < MEDIUM_SPAWN + HARD_SPAWN + LEGENDARY_SPAWN) {
			chosenDifficulty = 2;
		} else if (randomNum < EASY_SPAWN + MEDIUM_SPAWN + HARD_SPAWN + LEGENDARY_SPAWN) {
			chosenDifficulty = 1;
		} else {
			return null;
		}

		ArrayList<MonsterStruct> tempList = new ArrayList<MonsterStruct>(listOfMonsters);
		Collections.shuffle(tempList);
		for (MonsterStruct mstruct : tempList) {
			if (mstruct.getDifficulty() == chosenDifficulty && mstruct.getMonsterType() == entType
					&& isValidBiome(biome, mstruct.getBiomes())) {
				return mstruct.getName();
			}
		}
		return null;
	}

	public static String getRandomMonsterRank(int rank, EntityType entType) {
		/**
		 * Based on the rank this method returns the name of a monster.
		 */
		ArrayList<MonsterStruct> tempList = new ArrayList<MonsterStruct>(listOfMonsters);
		Collections.shuffle(tempList);
		for (MonsterStruct mstruct : tempList) {
			if (mstruct.getDifficulty() == rank && mstruct.getMonsterType().equals(entType)) {
				return mstruct.getName();
			}
		}
		return null;
	}

	public static String getRandomMonsterRank(int rank) {
		/**
		 * Based on the rank this method returns the name of a monster.
		 */
		ArrayList<MonsterStruct> tempList = new ArrayList<MonsterStruct>(listOfMonsters);
		Collections.shuffle(tempList);
		for (MonsterStruct mstruct : tempList) {
			if (mstruct.getDifficulty() == rank) {
				return mstruct.getName();
			}
		}
		return null;
	}

	public static String getColoredMobName(String name) {
		MonsterStruct monstru = MonsterList.getMonster(name);
		if (monstru != null) {
			int diff = monstru.getDifficulty();
			if (diff == 1) {
				return ChatColor.YELLOW + name;
			} else if (diff == 2) {
				return ChatColor.GOLD + name;
			} else if (diff == 3) {
				return ChatColor.RED + name;
			} else if (diff == 4) {
				return ChatColor.DARK_PURPLE + name;
			}
		}
		return name;
	}

	public static boolean getTriggered(LivingEntity lent) {
		// These are metadata mutator/accessor methods for a specific
		// LivingEntity
		List<MetadataValue> data = lent.getMetadata("triggered");
		if (data.size() > 0) {
			if (data.get(0).asBoolean() == true) {
				return true;
			}
		}
		return false;
	}

	public static void setTriggered(LivingEntity lent, boolean b) {
		lent.setMetadata("triggered", new FixedMetadataValue(MonstersPlus.plugin, b));
	}

	public static int getTargetId(LivingEntity lent) {
		// These are metadata mutator/accessor methods for a specific
		// LivingEntity
		List<MetadataValue> data = lent.getMetadata("targetId");
		if (data.size() > 0) {
			return data.get(0).asInt();

		}
		return -1;
	}

	public static boolean hasTargetId(LivingEntity lent) {
		int targetName = getTargetId(lent);
		if (targetName != -1) {
			return true;
		}
		return false;
	}

	public static void setTargetId(LivingEntity lent, int num) {
		lent.setMetadata("targetId", new FixedMetadataValue(MonstersPlus.plugin, num));
	}

	public static String getMetaName(LivingEntity lent) {
		// Meta name is used to save the custom monsters name to its metadata
		// That way we can still access the name even if the healthbars plugin
		// changes it.
		List<MetadataValue> data = lent.getMetadata("targetName");
		if (data.size() > 0) {
			return data.get(0).asString();

		}
		return null;
	}

	public static boolean hasMetaName(LivingEntity lent) {
		String targetName = getMetaName(lent);
		if (targetName == null) {
			return false;
		}
		return true;
	}

	public static void setMetaName(LivingEntity lent, String name) {
		lent.setMetadata("targetName", new FixedMetadataValue(MonstersPlus.plugin, name));
	}

	public static void updateMonsterMetaName(LivingEntity lent) {
		/*
		 * This method will check if the monster is a custom monster, and then
		 * it will save its display name to setMetaName(); This method will also
		 * have to consider the healthbars plugin which adds llllllllllll bars
		 * to represent the monsters health.
		 * 
		 * HEALTHBAR INT VALUES: 9608, 9612, 32
		 */
		if (lent instanceof Player) {
			return;
		}
		if (lent.getCustomName() == null) {
			return;
		}

		// Make sure we are not looking at a HealthBar nametag.
		String stripped = ChatColor.stripColor(lent.getCustomName());
		if (!hasMetaName(lent) && !stripped.contains(Character.toString((char) 9608))
				&& !stripped.contains(Character.toString((char) 9612))) {
			setMetaName(lent, stripped);
		}
	}

	public static boolean hasCustomName(LivingEntity lent) {
		return (lent.getCustomName() != null);
	}

	public static void updateCustomName(LivingEntity lent) {
		if (!(lent instanceof Player)) {
			if (!lent.isCustomNameVisible()) {
				lent.setCustomNameVisible(true);
			}
		}
	}

	public static void dropPowerstones(LivingEntity lent) {
		/*
		 * This method takes the monsters saved metaname and then checks if it
		 * can find the monster structure that represents that monster. If it
		 * can find it Then it takes the monsters difficulty to determine the
		 * rate at which it will drop a powerstone.
		 */
		if (lent instanceof Player) {
			return;
		}
		if (!hasMetaName(lent)) {
			return;
		}

		String name = getMetaName(lent);
		if (MonsterList.isCustomMonster(name, lent)) {
			MonsterStruct monstru = MonsterList.getMonster(name);
			int difficulty = monstru.getDifficulty();
			double chance = 0;
			if (difficulty == MonsterList.EASY) {
				chance = PSTONE_EASY;
			} else if (difficulty == MonsterList.MEDIUM) {
				chance = PSTONE_MEDIUM;
			} else if (difficulty == MonsterList.HARD) {
				chance = PSTONE_HARD;
			}

			if (difficulty == MonsterList.LEGENDARY) {
				Tools.spawnPowerstone(lent.getLocation(), (int) (PSTONE_LEGENDARY / 100));
				chance = PSTONE_LEGENDARY % 100;
			}

			if (Tools.randomChance(chance)) {
				Tools.spawnPowerstone(lent.getLocation(), 1);
			}
		}
	}

	public static MonsterTrophy getMonsterTrophy(String mobName) {
		for (MonsterTrophy mtro : trophyList) {
			if (mtro.getMobName().equalsIgnoreCase(mobName)) {
				return mtro;
			}
		}

		return null;
	}

	public static ItemStack createMonsterTrophy(String mobName) {
		MonsterTrophy mtro = getMonsterTrophy(mobName);
		if (mtro == null) {
			return null;
		}
		ItemStack istack = new ItemStack(mtro.getItem());
		ItemMeta meta = istack.getItemMeta();
		meta.setDisplayName(mtro.getDisplayName());

		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_PURPLE + "Trophy");
		meta.setLore(lore);
		istack.setItemMeta(meta);

		if (mtro.getEnchantment() != null) {
			istack.addUnsafeEnchantment(mtro.getEnchantment(), mtro.getEnchantmentLevel());
		}

		return istack;
	}

	public static void dropTrophy(LivingEntity lent) {
		if (lent instanceof Player) {
			return;
		}
		if (!hasMetaName(lent)) {
			return;
		}

		String name = getMetaName(lent);
		if (MonsterList.isCustomMonster(name, lent)) {
			MonsterStruct monstru = MonsterList.getMonster(name);
			int difficulty = monstru.getDifficulty();
			if (difficulty == MonsterList.LEGENDARY || difficulty == MonsterList.SPECIAL) {
				ItemStack trophy = createMonsterTrophy(monstru.getName());
				if (trophy != null) {
					lent.getWorld().dropItemNaturally(lent.getLocation(), trophy);
				}
			}
		}
	}
}
