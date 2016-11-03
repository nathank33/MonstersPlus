package me.coolade.monstersplus.tasks;

import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.monsters.MonsterList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

//Used for spawning monsters that come from the ground in intervals, such as fire ants.
@SuppressWarnings("unused")
public class SpawnTask {
	private int ticks;
	private String mobname;
	private Location loc;

	public SpawnTask(int ticks, String mobname, Location loc) {

		this.ticks = ticks;
		this.mobname = mobname;
		this.loc = loc;
		createMonsterDelayed(ticks, mobname, loc);
	}

	public void createMonsterDelayed(final int ticks, final String mobname, final Location loc) {
		// This method will be used if we need to create multiple monsters with
		// a delay
		// To specify the monster use mobname, sending a string.
		MonstersPlus.plugin.getServer().getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new Runnable() {
			@Override
			public void run() {
				if (mobname == "Fire Ant") {
					final String name = ChatColor.GOLD + "Fire Ant";
					LivingEntity lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.SILVERFISH);
					lent.setMaxHealth(MonsterList.getMaxHealth("Fire Ant"));
					lent.setHealth(lent.getMaxHealth());
					lent.setCustomName(name);
					MonstersPlus.setSpeed(lent, 1.3);
				} else if (mobname == "Toxic Ant") {
					final String name = ChatColor.GOLD + "Toxic Ant";
					LivingEntity lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.SILVERFISH);
					lent.setMaxHealth(MonsterList.getMaxHealth("Toxic Ant"));
					lent.setHealth(lent.getMaxHealth());
					lent.setCustomName(name);
					MonstersPlus.setSpeed(lent, 1.1);
				} else if (mobname == "Frost Ant") {
					final String name = ChatColor.YELLOW + "Frost Ant";
					LivingEntity lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.SILVERFISH);
					lent.setMaxHealth(MonsterList.getMaxHealth("Frost Ant"));
					lent.setHealth(lent.getMaxHealth());
					lent.setCustomName(name);
					MonstersPlus.setSpeed(lent, 0.9);
				}
			}
		}, ticks);
	}
}
