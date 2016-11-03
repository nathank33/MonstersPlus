package me.coolade.monstersplus.monsters.monsterevents;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.monsters.MonsterList;
import me.coolade.monstersplus.monsters.MonsterListener;

public class SpiderSpawnEvent {
	/*
	 * Instead of using a field lent, we want to directly pass the lent that was
	 * sent to us, that way if it came through a command it would be able to
	 * modify the original lent, not a copy reference.
	 */
	LivingEntity lent;
	String mobName;

	public SpiderSpawnEvent(LivingEntity lent, String mobName) {
		this.lent = lent;
		this.mobName = mobName;
		createMonster();
	}

	public SpiderSpawnEvent(LivingEntity lent) {
		this(lent, MonsterList.generateMonster(EntityType.SPIDER, lent.getLocation().getBlock().getBiome()));
	}

	public LivingEntity getLent() {
		return lent;
	}

	public String getMobName() {
		return mobName;
	}

	public void setLent(LivingEntity lent) {
		this.lent = lent;
	}

	public void setMobName(String mobName) {
		this.mobName = mobName;
	}

	public void createMonster() {
		// EntityEquipment ee = lent.getEquipment();
		if (mobName == null) {
			return;
		}

		/** Black Widow EASY **/
		if (mobName.equalsIgnoreCase("Black Widow")) {
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 1));
		} else if (mobName.equalsIgnoreCase("Mountain Pincer")) {
			MonstersPlus.setSpeed(lent, 0.40);
		} else if (mobName.equalsIgnoreCase("Witch Apprentice")) {
			Location loc = lent.getLocation();
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WITCH);
		}
		/** Scorpion Medium **/
		else if (mobName.equalsIgnoreCase("Scorpion")) {
			Location loc = lent.getLocation();
			lent.remove();
			LivingEntity scorp = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.SILVERFISH);
			lent = scorp;
		}
		/** Rock Scorpion Medium **/
		else if (mobName.equalsIgnoreCase("Rock Scorpion")) {
			Location loc = lent.getLocation();
			lent.remove();
			LivingEntity scorp = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.SILVERFISH);
			lent = scorp;
		}
		/** Stinger MEDIUM **/
		else if (mobName.equalsIgnoreCase("Stinger")) {
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.INCREASE_DAMAGE, MonsterListener.POTION_DURATION, 1));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 1));
		} else if (mobName.equalsIgnoreCase("Desert Scorpion")) {
			Location loc = lent.getLocation();
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.SILVERFISH);
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 100));
			lent.setFireTicks(MonsterListener.POTION_DURATION);
		} else if (mobName.equalsIgnoreCase("Witch of the East")) {
			Location loc = lent.getLocation();
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WITCH);
		} else if (mobName.equalsIgnoreCase("Tarantula")) {
			lent.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, MonsterListener.POTION_DURATION, 5));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, MonsterListener.POTION_DURATION, 2));
		} else if (mobName.equalsIgnoreCase("Ice Spider")) {
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.INCREASE_DAMAGE, MonsterListener.POTION_DURATION, 1));
		} else if (mobName.equalsIgnoreCase("Warlock")) {
			Location loc = lent.getLocation();
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WITCH);
		}
		/** BROWN RECLUSE Hard **/
		else if (mobName.equalsIgnoreCase("Brown Recluse")) {
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.INCREASE_DAMAGE, MonsterListener.POTION_DURATION, 1));
		} else if (mobName.equalsIgnoreCase("Witch of the West")) {
			Location loc = lent.getLocation();
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WITCH);
		} else if (mobName.equalsIgnoreCase("Dark Mage")) {
			Location loc = lent.getLocation();
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WITCH);
		} else if (mobName.equalsIgnoreCase("Water Stinger")) {
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.INCREASE_DAMAGE, MonsterListener.POTION_DURATION, 1));
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, MonsterListener.POTION_DURATION, 1));
		} else if (mobName.equalsIgnoreCase("Filth Purch")) {
			Location loc = lent.getLocation();
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.SILVERFISH);
		} else if (mobName.equalsIgnoreCase("Swamp Witch")) {
			Location loc = lent.getLocation();
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WITCH);
		}

		/** Infearno Legendary **/
		else if (mobName.equalsIgnoreCase("Infearno")) {
			Location loc = lent.getLocation();
			lent.remove();
			LivingEntity newLent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.MAGMA_CUBE);
			((MagmaCube) newLent).setSize(7);
			newLent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 50));
			lent = newLent;
		}
		/** Hadamard Legendary **/
		else if (mobName.equalsIgnoreCase("Hadamard")) {
			Location loc = lent.getLocation();
			lent.remove();
			LivingEntity newLent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.SLIME);
			((Slime) newLent).setSize(5);
			newLent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 50));
			lent = newLent;
		}
		/** Lesath Legendary **/
		else if (mobName.equalsIgnoreCase("Lesath")) {
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 50));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 2));
		}
		/** Shelob Legendary **/
		else if (mobName.equalsIgnoreCase("Shelob")) {
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 2));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 1));
		}
		/** Arachne Legendary **/
		else if (mobName.equalsIgnoreCase("Arachne")) {
			Location loc = lent.getLocation();
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 2));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 1));
		}
		/** Charlotte Legendary **/
		else if (mobName.equalsIgnoreCase("Charlotte")) {
			lent.setMaxHealth(MonsterList.getMaxHealth("Charlotte"));
			lent.setHealth(lent.getMaxHealth());
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.INCREASE_DAMAGE, MonsterListener.POTION_DURATION, 2));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 3));
			lent.setCustomName(ChatColor.DARK_PURPLE + "Charlotte");
		}
		/** Raikou Legendary **/
		else if (mobName.equalsIgnoreCase("Raikou")) {
			Location loc = lent.getLocation();
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WOLF);
			((Wolf) lent).setAngry(true);
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 50));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 1));
		}
		/** Entei Legendary **/
		else if (mobName.equalsIgnoreCase("Entei")) {
			Location loc = lent.getLocation();
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WOLF);
			((Wolf) lent).setAngry(true);
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 100));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 1));
			lent.setFireTicks(MonsterListener.POTION_DURATION);
		}
		/** Suicune Legendary **/
		else if (mobName.equalsIgnoreCase("Suicune")) {
			Location loc = lent.getLocation();
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WOLF);
			((Wolf) lent).setAngry(true);
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 100));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 1));
		}
		/** Barkira Legendary **/
		else if (mobName.equalsIgnoreCase("Barkira")) {
			Location loc = lent.getLocation();
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WOLF);
			((Wolf) lent).setAngry(true);
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 100));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 1));
		}

		double health = MonsterList.getMaxHealth(mobName);
		if (health > 0) {
			lent.setMaxHealth(health);
			lent.setHealth(health);
			lent.setCustomName(MonsterList.getColoredMobName(mobName));
		}
	}
}
