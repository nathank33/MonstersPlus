package me.coolade.monstersplus.tasks;

import me.coolade.monstersplus.MonstersPlus;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("unused")
public class DelayedExplosion {
	private long delay;
	private float size;
	private Location loc;

	@SuppressWarnings("deprecation")
	public DelayedExplosion(Location loc, float size, long delay) {
		this.delay = delay;
		this.loc = loc;
		this.size = size;
		Bukkit.getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new DelayedExplosionHelper(size, loc),
				delay);
	}

	public class DelayedExplosionHelper extends BukkitRunnable {
		private float size;
		private Location loc;

		public DelayedExplosionHelper(float size, Location loc) {
			this.size = size;
			this.loc = loc;
		}

		@Override
		public void run() {
			loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), size, false, false);
		}
	}
}
