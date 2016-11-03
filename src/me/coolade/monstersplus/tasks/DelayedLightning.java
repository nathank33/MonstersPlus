package me.coolade.monstersplus.tasks;

import me.coolade.monstersplus.MonstersPlus;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("unused")
public class DelayedLightning {
	private long delay;
	private Location loc;

	@SuppressWarnings("deprecation")
	public DelayedLightning(Location loc, long delay) {
		this.delay = delay;
		this.loc = loc;
		Bukkit.getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new DelayedLightningHelper(loc), delay);
	}

	public class DelayedLightningHelper extends BukkitRunnable {
		private float size;
		private Location loc;

		public DelayedLightningHelper(Location loc) {
			this.loc = loc;
		}

		@Override
		public void run() {
			loc.getWorld().strikeLightning(loc);
		}
	}
}
