package me.coolade.jobsplus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.Tools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class OreRadar {
	private static final int SMALL_RADIUS = 6;
	private static final int LARGE_RADIUS = 20;
	private static final int RUNTIME = 300000; // milli
	private static final int COOLDOWN = 1800000; // milli
	private static final int DELAY = 400; // ticks
	public static final int MINUTE = 5;
	public static final int COOLDOWN_MINUTE = 30;
	private static HashMap<String, Long> radarCooldowns = new HashMap<String, Long>();
	private static HashMap<String, Integer> radarIds = new HashMap<String, Integer>();
	public static ArrayList<OreSet> oreSets = new ArrayList<OreSet>() {
		private static final long serialVersionUID = 1L;

		{
			add(new OreSet(Material.COAL_ORE, 2, SMALL_RADIUS));
			add(new OreSet(Material.OBSIDIAN, 3, LARGE_RADIUS));
			add(new OreSet(Material.LAPIS_ORE, 4, SMALL_RADIUS));
			add(new OreSet(Material.MOSSY_COBBLESTONE, 5, LARGE_RADIUS));
			add(new OreSet(Material.REDSTONE_ORE, 7, SMALL_RADIUS));
			add(new OreSet(Material.QUARTZ_ORE, 8, SMALL_RADIUS));
			add(new OreSet(Material.IRON_ORE, 10, SMALL_RADIUS));
			add(new OreSet(Material.GOLD_ORE, 12, SMALL_RADIUS));
			add(new OreSet(Material.DIAMOND_ORE, 15, SMALL_RADIUS));
			add(new OreSet(Material.EMERALD_ORE, 17, SMALL_RADIUS));
		}
	};

	public OreRadar(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This command can only be used by players");
			return;
		}

		Player player = (Player) sender;
		if (args.length == 0) {
			player.sendMessage(ChatColor.GREEN + "Ore Radar");
			player.sendMessage(ChatColor.RED + "/oreradar <ore>" + ChatColor.GOLD + " to track that type of ore for "
					+ MINUTE + " minutes.");
			player.sendMessage(ChatColor.RED + "/oreradar stop" + ChatColor.GOLD + " to stop tracking ore.");
			displayAvailableOres(player);
		} else if (args.length == 1) {
			if (args[0].toUpperCase().contains("STOP")) {
				if (!radarIds.containsKey(player.getName())) {
					player.sendMessage(ChatColor.RED + "You are not tracking anything.");
				} else {
					int id = radarIds.get(player.getName());
					try {
						player.sendMessage(ChatColor.RED + "You retire your senses.");
						Bukkit.getScheduler().cancelTask(id);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return;
			} else if (radarOnCooldown(player.getName()) && !player.isOp()) {
				long difference = radarCooldowns.get(player.getName()) + COOLDOWN - (new Date().getTime());
				long sec = (difference / 1000) % 60;
				long min = (difference / 1000) / 60;
				player.sendMessage(ChatColor.GOLD + "You can't use this ability for " + ChatColor.RED + min
						+ ChatColor.GOLD + " minutes and " + ChatColor.RED + sec + ChatColor.GOLD + " seconds.");

				return;
			}
			Material mat = getMatType(args[0]);
			if (mat == null) {
				player.sendMessage(ChatColor.RED + "Invalid ore type");
				return;
			} else if (!playerHasOreRadar(player, mat) && !player.isOp()) {
				OreSet os = getOreSet(mat);
				player.sendMessage(ChatColor.RED + "You must be a level " + ChatColor.GOLD + os.getLevel() + " Miner "
						+ ChatColor.RED + "to use this ability");
				return;
			} else {
				player.sendMessage(
						ChatColor.GOLD + "You focus your attention towards " + ChatColor.RED + mat.toString());
				long millis = new Date().getTime();
				radarCooldowns.put(player.getName(), (millis));
				RadarTask task = new RadarTask(player, mat);
				@SuppressWarnings("deprecation")
				int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(MonstersPlus.plugin, task, 0, DELAY);
				task.setTaskId(id);
				radarIds.put(player.getName(), id);
				return;
			}
		}
	}

	public class RadarTask extends BukkitRunnable {
		// This is a constantly running task that can cancel itself based on
		// taskid
		private int taskId = 0;
		private Player player;
		private Material mat;
		private OreSet oreSet;

		public RadarTask(Player player, Material mat) {
			this.player = player;
			this.mat = mat;
			oreSet = getOreSet(mat);
		}

		public void setTaskId(int t) {
			this.taskId = t;
		}

		@Override
		public void run() {
			if (!radarIsRunning(player.getName()) || !player.isOnline()) {
				// Cancel the event
				player.sendMessage(ChatColor.RED + "Your mining senses fade away");
				Bukkit.getScheduler().cancelTask(taskId);
			} else {
				try {
					player.sendMessage("Trying to locate ore.");
					Location loc = Tools.getClosestMaterial(player.getLocation(), oreSet.getMat(), oreSet.getRadius());
					if (loc != null) {
						Location direc = Tools.lookAt(player.getLocation(), loc);
						player.sendMessage(ChatColor.AQUA + "You sense " + mat + " nearby!");
						player.teleport(direc);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static OreSet getOreSet(Material mat) {
		for (OreSet os : oreSets) {
			if (os.getMat() == mat) {
				return os;
			}
		}
		Bukkit.broadcastMessage("Cannot find oreset " + mat);
		return null;
	}

	public static Material getMatType(String s) {
		s = s.toUpperCase();
		if (s.contains("COAL")) {
			return Material.COAL_ORE; // 2
		} else if (s.contains("MOSS")) {
			return Material.MOSSY_COBBLESTONE;
		} else if (s.contains("LAP")) {
			return Material.LAPIS_ORE;
		} else if (s.contains("OBS")) {
			return Material.OBSIDIAN; // 5
		} else if (s.contains("RED")) {
			return Material.REDSTONE_ORE;
		} else if (s.contains("IRON")) {
			return Material.IRON_ORE; // 10
		} else if (s.contains("QUA")) {
			return Material.QUARTZ_ORE;
		} else if (s.contains("GOLD")) {
			return Material.GOLD_ORE; // 15
		} else if (s.contains("DIA")) {
			return Material.DIAMOND_ORE; // 20
		} else {
			return null;
		}
	}

	public boolean radarOnCooldown(String playerName) {
		// Checks to see if the radar is on cooldown
		if (radarCooldowns.containsKey(playerName)) {
			long timestamp = radarCooldowns.get(playerName);
			long current = new Date().getTime();
			if ((timestamp + COOLDOWN) > current) {
				return true;
			}
		}
		return false;
	}

	public boolean radarIsRunning(String playerName) {
		// Checks to see if the player still has time left on his radar
		if (radarCooldowns.containsKey(playerName)) {
			long timestamp = radarCooldowns.get(playerName);
			long current = new Date().getTime();
			if ((timestamp + RUNTIME) > current) {
				return true;
			}
		}
		return false;
	}

	public boolean playerHasOreRadar(Player player, Material mat) {
		OreSet os = getOreSet(mat);
		int playerLevel = JobsListener.getJobLevel(player.getName(), "Miner");
		if (playerLevel >= os.getLevel()) {
			return true;
		}
		return false;
	}

	public void displayAvailableOres(Player player) {
		String list = ChatColor.GOLD + "Available Ores: " + ChatColor.GRAY;
		int playerLevel = JobsListener.getJobLevel(player.getName(), "Miner");

		if (playerLevel == 0) {
			player.sendMessage(ChatColor.RED + "Only Miners can use this ability.");
			return;
		}

		for (OreSet os : oreSets) {
			if (playerLevel >= os.getLevel()) {
				list += os.getMat();
				list += "  ";
			}
		}
		player.sendMessage(list);
	}

	public static class OreSet {
		private Material mat;
		private int level;
		private int radius;

		public OreSet(Material mat, int level, int radius) {
			this.mat = mat;
			this.level = level;
			this.radius = radius;
		}

		public Material getMat() {
			return mat;
		}

		public int getLevel() {
			return level;
		}

		public int getRadius() {
			return radius;
		}
	}
}
