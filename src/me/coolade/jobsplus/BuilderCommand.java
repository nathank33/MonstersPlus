package me.coolade.jobsplus;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuilderCommand {
	public static final int[] REACH_LEVELS = new int[] { 2, 7, 12, 17, 22 };
	public static final int[] REACH_DISTANCE = new int[] { 6, 8, 10, 12, 14 };
	public static ConcurrentHashMap<String, Integer> reachInstances = new ConcurrentHashMap<String, Integer>();

	/*
	 * public static final int BIOME_LEVEL = 12; public static final int
	 * BIOME_COST = 100; public static final Biome[] BIOME_TYPES = new
	 * Biome[]{Biome.BIRCH_FOREST, Biome.DESERT, Biome.EXTREME_HILLS,
	 * Biome.FOREST, Biome.ICE_PLAINS, Biome.JUNGLE, Biome.MESA, Biome.PLAINS,
	 * Biome.SAVANNA, Biome.SWAMPLAND, Biome.TAIGA};
	 */

	public BuilderCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
			return;
		}
		Player player = (Player) sender;
		int level = JobsListener.getJobLevel(player.getName(), "Builder");
		if (args.length == 1 && args[0].equalsIgnoreCase("reach")) {
			/**
			 * A player has the ability to toggle on "Reach" which allows them
			 * to place blocks at a much greater distance away. The event is
			 * handled inside of JobsListener.java.
			 */
			if (level < REACH_LEVELS[0]) {
				player.sendMessage(ChatColor.RED + "You must be a builder level " + ChatColor.GOLD + REACH_LEVELS[0]
						+ ChatColor.RED + " to use this command.");
				return;
			} else if (reachInstances.containsKey(player.getName())) {
				player.sendMessage(ChatColor.GOLD + "Reach has been toggled off.");
				reachInstances.remove(player.getName());
				return;
			} else {
				int reach = REACH_DISTANCE[0];
				for (int i = 1; i < REACH_LEVELS.length; i++) {
					if (level >= REACH_LEVELS[i]) {
						reach = REACH_DISTANCE[i];
					}
				}
				player.sendMessage(ChatColor.GOLD + "Reach has been toggled on with a distance of " + reach + ".");
				reachInstances.put(player.getName(), reach);
				return;
			}
		}
		/*
		 * else if(args.length == 2 && args[0].equalsIgnoreCase("biome")) {
		 * if(level < BIOME_LEVEL){ player.sendMessage(ChatColor.RED +
		 * "You must be a builder level " + ChatColor.GOLD + BIOME_LEVEL +
		 * ChatColor.RED + " to use this command."); return; } else
		 * if(!MonstersPlus.economy.has(player, BIOME_COST)) {
		 * 
		 * } MonstersPlus.economy.withdrawPlayer(player, BIOME_COST)
		 * 
		 * try {
		 * 
		 * } catch(Exception e) {
		 * 
		 * } }
		 */
		displayUsage(player);
	}

	public void displayUsage(Player player) {
		player.sendMessage(ChatColor.GOLD + "Builder");
		player.sendMessage(ChatColor.AQUA + "/builder reach" + ChatColor.WHITE
				+ ": Allows you to place blocks from farther away, type it again to toggle it off.");
		String s = "";
		for (int i = 0; i < REACH_LEVELS.length; i++) {
			s = s + "Level " + ChatColor.GOLD + REACH_LEVELS[i] + ChatColor.WHITE + " - " + ChatColor.RED
					+ REACH_DISTANCE[i] + ChatColor.WHITE + " blocks, ";
		}
		player.sendMessage(s);
	}
}
