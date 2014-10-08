package me.coolade.monstersplus.tasks;

import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.monsters.MonsterList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayedSpawn
{
	private long delay = 0;
	private EntityType type;
	private Location loc;
	
	public DelayedSpawn(Location loc, EntityType type, long delay)
	{
		this.delay = delay;
		this.loc = loc;
		this.type = type;
		Bukkit.getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new DelayedSpawnHelper(type, loc), delay);
	}
	
	public class DelayedSpawnHelper extends BukkitRunnable
	{
		private EntityType type;
		private Location loc;
		public DelayedSpawnHelper(EntityType type, Location loc)
		{
			this.type = type;
			this.loc = loc;
		}
		public void run() 
		{
			loc.getWorld().spawnEntity(loc, type);
		}
	}
}

