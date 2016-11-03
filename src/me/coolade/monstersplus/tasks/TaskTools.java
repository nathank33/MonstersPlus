package me.coolade.monstersplus.tasks;

import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.monsters.MonsterList;

import org.bukkit.entity.LivingEntity;

public class TaskTools {
	private TaskTools() {
	}

	public static void setTriggeredDelayed(final int ticks, final LivingEntity le, final boolean b) {
		MonstersPlus.plugin.getServer().getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new Runnable() {
			@Override
			public void run() {
				MonsterList.setTriggered(le, b);
			}
		}, ticks);
	}

	public static void messageNearbyDelayed(final int radius, final int ticks, final String message,
			final LivingEntity le) {
		MonstersPlus.plugin.getServer().getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new Runnable() {
			@Override
			public void run() {
				MonstersPlus.messageNearbyPlayers(le.getLocation(), message, radius, radius, radius);
			}
		}, ticks);
	}
}
