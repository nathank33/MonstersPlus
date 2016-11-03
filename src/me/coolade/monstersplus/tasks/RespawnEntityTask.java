package me.coolade.monstersplus.tasks;

import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.monsters.MonsterList;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnEntityTask extends BukkitRunnable {
	private final int msgRadius = 12;
	private LivingEntity lent;
	private double health;
	private String msg;
	private int delay;

	public RespawnEntityTask(LivingEntity lent, double health, String msg, int delay) {
		this.lent = lent;
		this.health = health;
		this.msg = msg;
		this.delay = delay;
	}

	@Override
	public void run() {
		if (!MonsterList.getTriggered(lent)) {
			MonsterList.setTriggered(lent, true);
			MonstersPlus.messageNearbyPlayers(lent.getLocation(), msg, msgRadius, msgRadius, msgRadius);
			respawnEntity(delay, lent, health);
		}
	}

	public void respawnEntity(final int delay, final LivingEntity le, final double health) {
		MonstersPlus.plugin.getServer().getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if (lent.isDead()) {
					Location loc = lent.getLocation();
					LivingEntity newLent = (LivingEntity) loc.getWorld().spawnEntity(loc, lent.getType());
					MonsterList.setTriggered(newLent, true);
					newLent.setMaxHealth(health);
					newLent.setHealth(newLent.getMaxHealth());
					newLent.setCustomName(lent.getCustomName());
					newLent.setCustomNameVisible(true);
					newLent.addPotionEffects(lent.getActivePotionEffects());

					EntityEquipment ee = lent.getEquipment();
					EntityEquipment eeNew = newLent.getEquipment();
					eeNew.setItemInHand(ee.getItemInHand());
					eeNew.setBoots(ee.getBoots());
					eeNew.setLeggings(ee.getLeggings());
					eeNew.setHelmet(ee.getHelmet());
					eeNew.setChestplate(ee.getChestplate());

				}
			}
		}, delay);
	}
}
