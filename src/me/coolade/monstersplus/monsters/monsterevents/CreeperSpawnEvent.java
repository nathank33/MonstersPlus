package me.coolade.monstersplus.monsters.monsterevents;

import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.monsters.MonsterList;
import me.coolade.monstersplus.monsters.MonsterListener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CreeperSpawnEvent {
	LivingEntity lent;
	String mobName;

	public CreeperSpawnEvent(LivingEntity lent, String mobName) {
		this.lent = lent;
		this.mobName = mobName;
		createMonster();
	}

	public CreeperSpawnEvent(LivingEntity lent) {
		this(lent, MonsterList.generateMonster(EntityType.CREEPER, lent.getLocation().getBlock().getBiome()));
	}

	@SuppressWarnings("deprecation")
	public void createMonster() {
		if (mobName == null) {
			return;
		}

		EntityEquipment ee = lent.getEquipment();
		ee.setHelmetDropChance(0F);
		ee.setChestplateDropChance(0F);
		ee.setLeggingsDropChance(0F);
		ee.setBootsDropChance(0F);
		ee.setItemInHandDropChance(0F);

		Creeper creeper = (Creeper) lent;
		/** Squirt EASY **/
		if (mobName.equalsIgnoreCase("Squirt")) {
			creeper.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 1));
		} else if (mobName.equalsIgnoreCase("Heavy Creeper")) {
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, MonsterListener.POTION_DURATION, 1));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, MonsterListener.POTION_DURATION, 1));
		}
		/** Heat Seeker Medium **/
		else if (mobName.equalsIgnoreCase("Heat Seeker")) {
			MonstersPlus.setSpeed(creeper, 0.45);
		}
		/** Creepillar Medium **/
		else if (mobName.equalsIgnoreCase("Creepillar")) {
			Location loc = creeper.getLocation();

			LivingEntity body1 = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.CREEPER);
			LivingEntity body2 = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.CREEPER);

			creeper.setCustomName(ChatColor.GOLD + "Creepillar");
			body1.setCustomName(ChatColor.GOLD + "Creepillar");
			body2.setCustomName(ChatColor.GOLD + "Creepillar");

			creeper.setPassenger(body1);
			body1.setPassenger(body2);

			double creepillarHp = MonsterList.getMaxHealth("Creepillar");
			creeper.setMaxHealth(creepillarHp);
			body1.setMaxHealth(creepillarHp - 1);
			body2.setMaxHealth(creepillarHp - 1);

			creeper.setHealth(creeper.getMaxHealth());
			body1.setHealth(body1.getMaxHealth());
			body2.setHealth(body2.getMaxHealth());
		}
		/** Little Boy HARD **/
		else if (mobName.equalsIgnoreCase("Little Boy")) {
			creeper.setPowered(true);
		}

		double health = MonsterList.getMaxHealth(mobName);
		if (health > 0) {
			lent.setMaxHealth(health);
			lent.setHealth(health);
			lent.setCustomName(MonsterList.getColoredMobName(mobName));
		}
	}
}
