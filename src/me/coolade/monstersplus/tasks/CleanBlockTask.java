package me.coolade.monstersplus.tasks;

import me.coolade.monstersplus.MonstersPlus;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("unused")
public class CleanBlockTask {
	private Material matToFind;
	private Material matToReplace;
	private Location loc;
	private int delay;

	@SuppressWarnings("deprecation")
	public CleanBlockTask(Material matToFind, Material matToReplace, Location loc, int delay) {
		this.matToFind = matToFind;
		this.matToReplace = matToReplace;
		this.loc = loc;
		this.delay = delay;
		Bukkit.getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin,
				new CleanBlockTaskHelper(matToFind, matToReplace, loc), delay);
	}

	public class CleanBlockTaskHelper extends BukkitRunnable {
		private Material matToFind;
		private Material matToReplace;
		private Location loc;

		public CleanBlockTaskHelper(Material matToFind, Material matToReplace, Location loc) {
			this.matToFind = matToFind;
			this.matToReplace = matToReplace;
			this.loc = loc;
		}

		@Override
		public void run() {
			if (loc.getWorld().getBlockAt(loc).getType().equals(matToFind)) {
				loc.getWorld().getBlockAt(loc).setType(matToReplace);
			}
		}
	}
}
