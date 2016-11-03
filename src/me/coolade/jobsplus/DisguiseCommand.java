package me.coolade.jobsplus;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.ItemStack;

import me.coolade.monstersplus.Cooldown;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.AnimalColor;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.CreeperWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.EndermanWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.OcelotWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.SheepWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.SlimeWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.VillagerWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.WolfWatcher;

public class DisguiseCommand {

	public static final int DISGUISE_COOLDOWN = 60000;
	public static ArrayList<DisguiseSet> disguiseList = new ArrayList<DisguiseSet>() {
		private static final long serialVersionUID = 1L;

		{

			add(new DisguiseSet("Butcher", "Warrior", 2));
			add(new DisguiseSet("Zombie", "Warrior", 3));
			add(new DisguiseSet("PigZombie", "Warrior", 5));
			add(new DisguiseSet("MadEnderman", "Warrior", 7));
			add(new DisguiseSet("BabyPigman", "Warrior", 10));
			add(new DisguiseSet("SkeletonHorse", "Warrior", 15));
			add(new DisguiseSet("Ghast", "Warrior", 20));

			add(new DisguiseSet("Farmer", "Farmer", 2));
			add(new DisguiseSet("Chicken", "Farmer", 3));
			add(new DisguiseSet("Pig", "Farmer", 5));
			add(new DisguiseSet("Horse", "Farmer", 7));
			add(new DisguiseSet("ColoredSheep", "Farmer", 8));
			add(new DisguiseSet("Mule", "Farmer", 9));
			add(new DisguiseSet("MushroomCow", "Farmer", 10));
			add(new DisguiseSet("Donkey", "Farmer", 12));

			add(new DisguiseSet("BabyChicken", "Fisherman", 2));
			add(new DisguiseSet("Squid", "Fisherman", 3));
			add(new DisguiseSet("BigSlime", "Fisherman", 5));
			add(new DisguiseSet("OrangeOcelot", "Fisherman", 8));
			add(new DisguiseSet("Silverfish", "Fisherman", 12));

			add(new DisguiseSet("Cow", "Survivalist", 2));
			add(new DisguiseSet("Sheep", "Survivalist", 3));
			add(new DisguiseSet("Skeleton", "Survivalist", 5));
			add(new DisguiseSet("SiameseOcelot", "Survivalist", 7));
			add(new DisguiseSet("Wolf", "Survivalist", 10));

			add(new DisguiseSet("Villager", "Builder", 2));
			add(new DisguiseSet("Spider", "Builder", 3));
			add(new DisguiseSet("Enderman", "Builder", 5));
			add(new DisguiseSet("Ocelot", "Builder", 7));
			add(new DisguiseSet("MiniSlime", "Builder", 10));
			add(new DisguiseSet("Snowman", "Builder", 13));

			add(new DisguiseSet("Blacksmith", "Blacksmith", 2));
			add(new DisguiseSet("ZombieVillager", "Blacksmith", 3));
			add(new DisguiseSet("IronGolem", "Blacksmith", 5));
			add(new DisguiseSet("BabyZombie", "Blacksmith", 7));
			add(new DisguiseSet("MagmaCube", "Blacksmith", 10));
			add(new DisguiseSet("ZombieHorse", "Blacksmith", 15));

			add(new DisguiseSet("CaveSpider", "Miner", 2));
			add(new DisguiseSet("ChargedCreeper", "Miner", 5));
			add(new DisguiseSet("Slime", "Miner", 7));
			add(new DisguiseSet("Blaze", "Miner", 10));
			add(new DisguiseSet("Bat", "Miner", 15));

			add(new DisguiseSet("Witch", "Witchdoctor", 2));
			add(new DisguiseSet("WitherSkeleton", "Witchdoctor", 5));
			add(new DisguiseSet("Creeper", "Witchdoctor", 10));
			add(new DisguiseSet("Wither", "Witchdoctor", 20));

			add(new DisguiseSet("Librarian", "Enchanter", 2));
			add(new DisguiseSet("BabyPig", "Enchanter", 3));
			add(new DisguiseSet("BabyCow", "Enchanter", 5));
			add(new DisguiseSet("GoldSkeleton", "Enchanter", 7));
			add(new DisguiseSet("DiamondPigman", "Enchanter", 10));
			add(new DisguiseSet("FireWolf", "Enchanter", 12));

		}
	};

	@SuppressWarnings("deprecation")
	public DisguiseCommand(Player player, String message) {
		/**
		 * Sets the player to a specific disguise, and displays information
		 * about it. NOTE: The args is setup different in this compared to the
		 * normal commands, because these arguments are based on split.
		 */
		String[] args = message.split(" ");
		if (args.length == 1) {
			displayUsage(player);
			return;
		} else if (args[1].equalsIgnoreCase("stop")) {
			if (DisguiseAPI.isDisguised(player)) {
				player.sendMessage(ChatColor.RED + "You removed your disguise.");
				DisguiseAPI.undisguiseToAll(player);
			} else {
				player.sendMessage(ChatColor.RED + "You are not currently disguised.");
			}
			return;
		} else if (args[1].equalsIgnoreCase("display")) {
			if (args.length == 2) {
				player.sendMessage(ChatColor.GOLD + "Displaying all disguises:");
				for (DisguiseSet ds : disguiseList) {
					displayDisguise(player, ds);
				}
			} else if (args[2].equalsIgnoreCase("available")) {
				player.sendMessage(ChatColor.GOLD + "Displaying all available disguises:");
				for (DisguiseSet ds : disguiseList) {
					if (JobsListener.getJobLevel(player.getName(), ds.getJob()) >= ds.getLevel() || player.isOp()
							|| player.hasPermission("monstersplus.disguises")) {
						displayDisguise(player, ds);
					}
				}
			} else {
				player.sendMessage(ChatColor.GOLD + "Displaying all disuises for the job " + args[2]);
				for (DisguiseSet ds : disguiseList) {
					if (ds.getJob().equalsIgnoreCase(args[2])) {
						displayDisguise(player, ds);
					}
				}
			}
			return;
		} else {
			String key = player.getName() + "disguise";
			if (Cooldown.cooldownExists(key) && !player.isOp()) {
				player.sendMessage(ChatColor.GOLD + "You can't use this for " + ChatColor.RED
						+ Cooldown.getCooldownMinutes(key) + ChatColor.GOLD + " and " + ChatColor.RED
						+ Cooldown.getCooldownSeconds(key) + " seconds.");
				return;
			}

			DisguiseSet dset = getDisguiseSet(args[1]);
			if (dset == null) {
				player.sendMessage(ChatColor.RED + "Disguise not found.");
				return;
			}
			if (JobsListener.getJobLevel(player.getName(), dset.getJob()) < dset.getLevel() && !player.isOp()
					&& !player.hasPermission("monstersplus.disguises")) {
				player.sendMessage(ChatColor.RED + "You must be a level " + ChatColor.GOLD + dset.getLevel() + " "
						+ dset.getJob() + ChatColor.RED + " to be disguised as " + ChatColor.GOLD + dset.getName());
				return;
			}

			MobDisguise disg = null;
			String name = dset.getName();
			if (name.equalsIgnoreCase("SkeletonHorse")) {
				disg = new MobDisguise(DisguiseType.SKELETON_HORSE, true);
			} else if (name.equalsIgnoreCase("Horse")) {
				disg = new MobDisguise(DisguiseType.HORSE, true);
			} else if (name.equalsIgnoreCase("Mule")) {
				disg = new MobDisguise(DisguiseType.MULE, true);
			} else if (name.equalsIgnoreCase("Donkey")) {
				disg = new MobDisguise(DisguiseType.DONKEY, true);
			} else if (name.equalsIgnoreCase("Ocelot")) {
				disg = new MobDisguise(DisguiseType.OCELOT, true);
			} else if (name.equalsIgnoreCase("IronGolem")) {
				disg = new MobDisguise(DisguiseType.IRON_GOLEM, true);
			} else if (name.equalsIgnoreCase("MagmaCube")) {
				disg = new MobDisguise(DisguiseType.MAGMA_CUBE, true);
			} else if (name.equalsIgnoreCase("ZombieHorse")) {
				disg = new MobDisguise(DisguiseType.UNDEAD_HORSE, true);
			} else if (name.equalsIgnoreCase("WitherSkeleton")) {
				disg = new MobDisguise(DisguiseType.WITHER_SKELETON, true);
			} else if (name.equalsIgnoreCase("Wither")) {
				disg = new MobDisguise(DisguiseType.WITHER, true);
			} else if (name.equalsIgnoreCase("BabyPigman")) {
				disg = new MobDisguise(DisguiseType.PIG_ZOMBIE, true);
			} else if (name.equalsIgnoreCase("BabyZombie")) {
				disg = new MobDisguise(DisguiseType.ZOMBIE, true);
			} else if (name.equalsIgnoreCase("BabyChicken")) {
				disg = new MobDisguise(DisguiseType.CHICKEN, true);
			} else if (name.equalsIgnoreCase("BabyPig")) {
				disg = new MobDisguise(DisguiseType.PIG, true);
			} else if (name.equalsIgnoreCase("BabyCow")) {
				disg = new MobDisguise(DisguiseType.COW, true);
			} else if (name.equalsIgnoreCase("ZombieVillager")) {
				disg = new MobDisguise(DisguiseType.ZOMBIE_VILLAGER, true);
			} else if (name.equalsIgnoreCase("Butcher")) {
				disg = new MobDisguise(DisguiseType.VILLAGER, true);
				VillagerWatcher watcher = (VillagerWatcher) disg.getWatcher();
				watcher.setProfession(Profession.BUTCHER);
			} else if (name.equalsIgnoreCase("Farmer")) {
				disg = new MobDisguise(DisguiseType.VILLAGER, true);
				VillagerWatcher watcher = (VillagerWatcher) disg.getWatcher();
				watcher.setProfession(Profession.FARMER);
			} else if (name.equalsIgnoreCase("Blacksmith")) {
				disg = new MobDisguise(DisguiseType.VILLAGER, true);
				VillagerWatcher watcher = (VillagerWatcher) disg.getWatcher();
				watcher.setProfession(Profession.BLACKSMITH);
			} else if (name.equalsIgnoreCase("Librarian")) {
				disg = new MobDisguise(DisguiseType.VILLAGER, true);
				VillagerWatcher watcher = (VillagerWatcher) disg.getWatcher();
				watcher.setProfession(Profession.LIBRARIAN);
			} else if (name.equalsIgnoreCase("ChargedCreeper")) {
				disg = new MobDisguise(DisguiseType.CREEPER, true);
				CreeperWatcher watcher = (CreeperWatcher) disg.getWatcher();
				watcher.setPowered(true);
			} else if (name.equalsIgnoreCase("SiameseOcelot")) {
				disg = new MobDisguise(DisguiseType.OCELOT, true);
				OcelotWatcher watcher = (OcelotWatcher) disg.getWatcher();
				watcher.setType(Ocelot.Type.SIAMESE_CAT);
			} else if (name.equalsIgnoreCase("OrangeOcelot")) {
				disg = new MobDisguise(DisguiseType.OCELOT, true);
				OcelotWatcher watcher = (OcelotWatcher) disg.getWatcher();
				watcher.setType(Ocelot.Type.RED_CAT);
			} else if (name.equalsIgnoreCase("ColoredSheep")) {
				Random rand = new Random();
				disg = new MobDisguise(DisguiseType.SHEEP, true);
				SheepWatcher watcher = (SheepWatcher) disg.getWatcher();
				watcher.setColor(AnimalColor.values()[rand.nextInt(AnimalColor.values().length)]);
			} else if (name.equalsIgnoreCase("MadEnderman")) {
				disg = new MobDisguise(DisguiseType.ENDERMAN, true);
				EndermanWatcher watcher = (EndermanWatcher) disg.getWatcher();
				watcher.setAggressive(true);
			} else if (name.equalsIgnoreCase("MiniSlime")) {
				disg = new MobDisguise(DisguiseType.SLIME, true);
				SlimeWatcher watcher = (SlimeWatcher) disg.getWatcher();
				watcher.setSize(1);
			} else if (name.equalsIgnoreCase("BigSlime")) {
				disg = new MobDisguise(DisguiseType.SLIME, true);
				SlimeWatcher watcher = (SlimeWatcher) disg.getWatcher();
				watcher.setSize(5);
			} else if (name.equalsIgnoreCase("FireWolf")) {
				disg = new MobDisguise(DisguiseType.WOLF, true);
				WolfWatcher watcher = (WolfWatcher) disg.getWatcher();
				watcher.setBurning(true);
				watcher.setAngry(true);
			} else if (name.equalsIgnoreCase("DiamondPigman")) {
				disg = new MobDisguise(DisguiseType.PIG_ZOMBIE, true);
				LivingWatcher watcher = (LivingWatcher) disg.getWatcher();
				watcher.setArmor(new ItemStack[] { new ItemStack(Material.DIAMOND_BOOTS),
						new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_CHESTPLATE),
						new ItemStack(Material.DIAMOND_HELMET) });
			} else if (name.equalsIgnoreCase("GoldSkeleton")) {
				disg = new MobDisguise(DisguiseType.SKELETON, true);
				LivingWatcher watcher = (LivingWatcher) disg.getWatcher();
				watcher.setArmor(
						new ItemStack[] { new ItemStack(Material.GOLD_BOOTS), new ItemStack(Material.GOLD_LEGGINGS),
								new ItemStack(Material.GOLD_CHESTPLATE), new ItemStack(Material.GOLD_HELMET) });
			} else {
				disg = new MobDisguise(DisguiseType.getType(EntityType.fromName(dset.getName())), true);
			}

			if (disg != null) {
				DisguiseAPI.disguiseEntity(player, disg);
				player.sendMessage(
						ChatColor.AQUA + "You are now being disguised as: " + ChatColor.GOLD + dset.getName());
				new Cooldown(player.getName() + "disguise", DISGUISE_COOLDOWN);
				return;
			}
		}
	}

	public static class DisguiseSet {
		String name;
		String job;
		int level;

		public DisguiseSet(String name, String job, int level) {
			this.name = name;
			this.job = job;
			this.level = level;
		}

		public String getName() {
			return name;
		}

		public String getJob() {
			return job;
		}

		public int getLevel() {
			return level;
		}
	}

	public static DisguiseSet getDisguiseSet(String name) {
		for (DisguiseSet ds : disguiseList) {
			if (ds.getName().equalsIgnoreCase(name)) {
				return ds;
			}
		}
		return null;
	}

	public void displayUsage(Player player) {
		player.sendMessage(ChatColor.GOLD + "Disguise:");
		player.sendMessage(
				ChatColor.AQUA + "/disguise display: " + ChatColor.WHITE + "Display the disguises for all jobs.");
		player.sendMessage(ChatColor.AQUA + "/disguise display <job>: " + ChatColor.WHITE
				+ "Display all the available disguises for a specific job.");
		player.sendMessage(ChatColor.AQUA + "/disguise display available: " + ChatColor.WHITE
				+ "Display all the disguises that you can use.");
		player.sendMessage(ChatColor.AQUA + "/disguise <disguisename>: " + ChatColor.WHITE + "Set your disguise.");
		player.sendMessage(ChatColor.AQUA + "/disguise stop: " + ChatColor.WHITE + "Remove your disguise.");
	}

	public void displayDisguise(Player player, DisguiseSet dset) {
		/**
		 * Sends the player a message displaying the disguise set.
		 */
		player.sendMessage(
				ChatColor.AQUA + dset.getName() + " " + ChatColor.WHITE + dset.getJob() + " level " + dset.getLevel());
	}
}
