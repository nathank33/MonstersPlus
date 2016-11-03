package me.coolade.monstersplus.tasks;

import me.coolade.monstersplus.MonstersPlus;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayedEffect {
	@SuppressWarnings("deprecation")
	public DelayedEffect(Location loc, Effect effect, int data, int radius, long delay) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin,
				new DelayedEffectHelper(loc, effect, data, radius), delay);
	}

	public class DelayedEffectHelper extends BukkitRunnable {
		private Location loc;
		private Effect effect;
		private int data;
		private int radius;

		public DelayedEffectHelper(Location loc, Effect effect, int data, int radius) {
			this.loc = loc;
			this.effect = effect;
			this.data = data;
			this.radius = radius;
		}

		@Override
		public void run() {
			loc.getWorld().playEffect(loc, effect, data, radius);
		}
	}
}
