package me.coolade.monstersplus.tasks;

import me.coolade.monstersplus.MonstersPlus;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("unused")
public class PlaceBlockTask {
	private Material mat;
	private Location loc;
	private int delay;

	@SuppressWarnings("deprecation")
	public PlaceBlockTask(Material mat, Location loc, int delay) {
		this.mat = mat;
		this.loc = loc;
		this.delay = delay;
		Bukkit.getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new PlaceBlockTaskHelper(mat, loc), delay);
	}

	public class PlaceBlockTaskHelper extends BukkitRunnable {
		private Material blockType;
		private Location loc;

		public PlaceBlockTaskHelper(Material blockType, Location loc) {
			this.blockType = blockType;
			this.loc = loc;
		}

		@Override
		public void run() {
			loc.getWorld().getBlockAt(loc).setType(blockType);
		}
	}
}
