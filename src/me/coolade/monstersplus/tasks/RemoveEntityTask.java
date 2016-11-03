package me.coolade.monstersplus.tasks;

import me.coolade.monstersplus.MonstersPlus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("unused")
public class RemoveEntityTask {
	private Entity entity;
	private int delay;

	@SuppressWarnings("deprecation")
	public RemoveEntityTask(Entity entity, int delay) {
		this.entity = entity;
		this.delay = delay;
		Bukkit.getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new RemoveEntityTaskHelper(entity), delay);
	}

	private class RemoveEntityTaskHelper extends BukkitRunnable {
		private Entity entity;

		public RemoveEntityTaskHelper(Entity entity) {
			this.entity = entity;
		}

		@Override
		public void run() {
			entity.remove();
		}
	}
}
