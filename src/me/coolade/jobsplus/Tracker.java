package me.coolade.jobsplus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.Tools;

public class Tracker {
	private static final int SMALL_RADIUS = 40;
	private static final int MEDIUM_RADIUS = 100;
	private static final int LARGE_RADIUS = 350;
	private static final int RUNTIME = 300000; // milli
	private static final int COOLDOWN = 1800000; // milli
	private static final int DELAY = 400; // ticks
	public static final int MINUTE = 5;
	public static final int COOLDOWN_MINUTE = 30;
	private static final String WAR = "Warrior";
	private static final String FAR = "Farmer";
	private static HashMap<String, Long> trackerCooldowns = new HashMap<String, Long>();
	private static HashMap<String, Integer> trackerIds = new HashMap<String, Integer>();
	public static ArrayList<TrackSet> trackSets = new ArrayList<TrackSet>() {
		private static final long serialVersionUID = 1L;

		{
			add(new TrackSet(EntityType.ZOMBIE, WAR, 2, SMALL_RADIUS));
			add(new TrackSet(EntityType.SKELETON, WAR, 3, SMALL_RADIUS));
			add(new TrackSet(EntityType.CREEPER, WAR, 5, SMALL_RADIUS));
			add(new TrackSet(EntityType.SPIDER, WAR, 7, SMALL_RADIUS));
			add(new TrackSet(EntityType.SILVERFISH, WAR, 9, SMALL_RADIUS));
			add(new TrackSet(EntityType.ENDERMAN, WAR, 10, SMALL_RADIUS));
			add(new TrackSet(EntityType.SLIME, WAR, 12, MEDIUM_RADIUS));
			add(new TrackSet(EntityType.CAVE_SPIDER, WAR, 14, SMALL_RADIUS));

			add(new TrackSet(EntityType.PIG_ZOMBIE, WAR, 4, SMALL_RADIUS));
			add(new TrackSet(EntityType.GHAST, WAR, 6, SMALL_RADIUS));
			add(new TrackSet(EntityType.BLAZE, WAR, 11, SMALL_RADIUS));
			add(new TrackSet(EntityType.MAGMA_CUBE, WAR, 13, MEDIUM_RADIUS));
			add(new TrackSet(EntityType.PLAYER, WAR, 15, LARGE_RADIUS));

			add(new TrackSet(EntityType.CHICKEN, FAR, 2, MEDIUM_RADIUS));
			add(new TrackSet(EntityType.SHEEP, FAR, 3, MEDIUM_RADIUS));
			add(new TrackSet(EntityType.COW, FAR, 4, MEDIUM_RADIUS));
			add(new TrackSet(EntityType.PIG, FAR, 5, MEDIUM_RADIUS));
			add(new TrackSet(EntityType.SQUID, FAR, 6, MEDIUM_RADIUS));
			add(new TrackSet(EntityType.BAT, FAR, 7, MEDIUM_RADIUS));
			add(new TrackSet(EntityType.WOLF, FAR, 10, MEDIUM_RADIUS));
			add(new TrackSet(EntityType.OCELOT, FAR, 12, MEDIUM_RADIUS));
			add(new TrackSet(EntityType.HORSE, FAR, 15, LARGE_RADIUS));
		}
	};

	public Tracker(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This command can only be used by players");
			return;
		}

		Player player = (Player) sender;
		if (args.length == 0) {
			player.sendMessage(ChatColor.GREEN + "Tracker");
			player.sendMessage(ChatColor.RED + "/track <mob>" + ChatColor.GOLD + " to track that type of entity for "
					+ MINUTE + " minutes.");
			player.sendMessage(ChatColor.RED + "/track stop" + ChatColor.GOLD + " to stop tracking mobs.");
			displayTrackableEntities(player);
		} else if (args.length == 1) {
			if (args[0].toUpperCase().contains("STOP")) {
				if (!trackerIds.containsKey(player.getName())) {
					player.sendMessage(ChatColor.RED + "You are not tracking anything.");
				} else {
					int id = trackerIds.get(player.getName());
					try {
						player.sendMessage(ChatColor.RED + "You stop tracking.");
						Bukkit.getScheduler().cancelTask(id);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return;
			} else if (trackerOnCooldown(player.getName())) {
				long difference = trackerCooldowns.get(player.getName()) + COOLDOWN - (new Date().getTime());
				long sec = (difference / 1000) % 60;
				long min = (difference / 1000) / 60;
				player.sendMessage(ChatColor.GOLD + "You can't use this ability for " + ChatColor.RED + min
						+ ChatColor.GOLD + " minutes and " + ChatColor.RED + sec + ChatColor.GOLD + " seconds.");

				return;
			}
			EntityType etype = getEntityType(args[0]);
			if (etype == null) {
				player.sendMessage(ChatColor.RED + "Invalid Entity type");
				return;
			} else if (!playerCanTrackType(player, etype)) {
				TrackSet ts = getTrackSet(etype);
				if (ts != null) {
					player.sendMessage(ChatColor.RED + "You must be a level " + ChatColor.GOLD + ts.getLevel() + " "
							+ ts.getJob() + " " + ChatColor.RED + "to use this ability");
				} else {
					player.sendMessage(ChatColor.RED + "Invalid entity type");
				}
				return;
			} else {
				player.sendMessage(ChatColor.GOLD + "You begin tracking " + ChatColor.RED + etype.toString());
				long millis = new Date().getTime();
				trackerCooldowns.put(player.getName(), (millis));
				TrackTask task = new TrackTask(player, etype);
				@SuppressWarnings("deprecation")
				int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(MonstersPlus.plugin, task, 0, DELAY);
				task.setTaskId(id);
				trackerIds.put(player.getName(), id);
				return;
			}
		}
	}

	public class TrackTask extends BukkitRunnable {
		// This is a constantly running task that can cancel itself based on
		// taskid
		private int taskId = 0;
		private Player player;
		private TrackSet trackSet;

		public TrackTask(Player player, EntityType etype) {
			this.player = player;
			trackSet = getTrackSet(etype);
		}

		public void setTaskId(int t) {
			this.taskId = t;
		}

		public void run() {
			if (!trackerIsRunning(player.getName()) || !player.isOnline()) {
				// Cancel the event
				player.sendMessage(ChatColor.RED + "Your grow tired of tracking");
				Bukkit.getScheduler().cancelTask(taskId);
			} else {
				try {
					player.sendMessage("Attempting to track.");

					Location loc = Tools.getClosestEntityType(player, trackSet.getType(), trackSet.getRadius());
					if (loc != null) {
						Location direc = Tools.lookAt(player.getLocation(), loc);
						player.sendMessage(ChatColor.AQUA + "You sense a " + trackSet.getType() + " nearby!");
						player.teleport(direc);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public TrackSet getTrackSet(EntityType etype) {
		for (TrackSet os : trackSets) {
			if (os.getType() == etype)
				return os;
		}
		Bukkit.broadcastMessage("Cannot find trackset type " + etype);
		return null;
	}

	public static EntityType getEntityType(String s) {
		s = s.toUpperCase();
		if (s.contains("ZOM") && !s.contains("PIG"))
			return EntityType.ZOMBIE;
		else if ((s.contains("ZOM") && s.contains("PIG")) || s.contains("PIGM"))
			return EntityType.PIG_ZOMBIE;
		else if (s.contains("SKE"))
			return EntityType.SKELETON;
		else if (s.contains("CREE"))
			return EntityType.CREEPER;
		else if (s.contains("SPID") && !s.contains("CAVE"))
			return EntityType.SPIDER;
		else if (s.contains("SPID") && s.contains("CAVE"))
			return EntityType.CAVE_SPIDER;
		else if (s.contains("ENDE"))
			return EntityType.ENDERMAN;
		else if (s.contains("SILV"))
			return EntityType.SILVERFISH;
		else if (s.contains("BLA"))
			return EntityType.BLAZE;
		else if (s.contains("GHA"))
			return EntityType.GHAST;
		else if (s.contains("MAG"))
			return EntityType.MAGMA_CUBE;
		else if (s.contains("SLI"))
			return EntityType.SLIME;
		else if (s.contains("PLA"))
			return EntityType.PLAYER;

		else if (s.contains("BAT"))
			return EntityType.BAT;
		else if (s.contains("WOLF"))
			return EntityType.WOLF;
		else if (s.contains("OCE"))
			return EntityType.OCELOT;
		else if (s.contains("CHIC"))
			return EntityType.CHICKEN;
		else if (s.contains("COW"))
			return EntityType.COW;
		else if (s.contains("PIG"))
			return EntityType.PIG;
		else if (s.contains("SHE"))
			return EntityType.SHEEP;
		else if (s.contains("HOR"))
			return EntityType.HORSE;
		else if (s.contains("SQU"))
			return EntityType.SQUID;

		else
			return null;
	}

	public boolean trackerOnCooldown(String playerName) {
		// Checks to see if the radar is on cooldown
		if (trackerCooldowns.containsKey(playerName)) {
			long timestamp = trackerCooldowns.get(playerName);
			long current = new Date().getTime();
			if ((timestamp + COOLDOWN) > current)
				return true;
		}
		return false;
	}

	public boolean trackerIsRunning(String playerName) {
		// Checks to see if the player still has time left on his radar
		if (trackerCooldowns.containsKey(playerName)) {
			long timestamp = trackerCooldowns.get(playerName);
			long current = new Date().getTime();
			if ((timestamp + RUNTIME) > current)
				return true;
		}
		return false;
	}

	public boolean playerCanTrackType(Player player, EntityType etype) {
		TrackSet ts = getTrackSet(etype);
		if (ts != null) {
			int playerLevel = JobsListener.getJobLevel(player.getName(), ts.getJob());
			if (playerLevel >= ts.getLevel())
				return true;
		}
		return false;
	}

	public void displayTrackableEntities(Player player) {
		String list = ChatColor.GOLD + "Trackable Entities: " + ChatColor.GRAY;
		int warLevel = JobsListener.getJobLevel(player.getName(), "Warrior");
		int farLevel = JobsListener.getJobLevel(player.getName(), "Farmer");

		if (warLevel == 0 && farLevel == 0) {
			player.sendMessage(ChatColor.RED + "Only Warriors and Farmers can use this ability.");
			return;
		}

		for (TrackSet ts : trackSets) {
			if (ts.getJob().equalsIgnoreCase("Warrior") && warLevel >= ts.getLevel()) {
				list += ts.getType();
				list += "  ";
			} else if (ts.getJob().equalsIgnoreCase("Farmer") && farLevel >= ts.getLevel()) {
				list += ts.getType();
				list += "  ";
			}
		}
		player.sendMessage(list);
	}

	public static class TrackSet {
		private EntityType etype;
		private String job;
		private int level;
		private int radius;

		public TrackSet(EntityType etype, String job, int level, int radius) {
			this.job = job;
			this.etype = etype;
			this.level = level;
			this.radius = radius;
		}

		public EntityType getType() {
			return etype;
		}

		public int getLevel() {
			return level;
		}

		public int getRadius() {
			return radius;
		}

		public String getJob() {
			return job;
		}
	}

}