package me.coolade.monstersplus.monsters.monsterevents;

import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.Tools;
import me.coolade.monstersplus.monsters.MonsterList;
import me.coolade.monstersplus.monsters.MonsterListener;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HorseSpawnEvent {
	private LivingEntity lent;
	private String mobName;

	public HorseSpawnEvent(LivingEntity lent, String mobName) {
		this.lent = lent;
		this.mobName = mobName;
		createMonster();
	}

	public HorseSpawnEvent(LivingEntity lent) {
		this(lent, MonsterList.generateMonster(EntityType.SKELETON));
	}

	public void createMonster() {
		Horse horse = (Horse) lent;
		if (horse == null) {
			return;
		}
		if (mobName == null) {
			return;
		}

		if (Tools.randomChance(2.0) || mobName.equalsIgnoreCase("Undead Horse")) {
			horse.setVariant(Variant.UNDEAD_HORSE);
			horse.setTamed(true);
			horse.setCustomName("Undead Horse");
			horse.setMaxHealth(MonsterList.getMaxHealth("Undead Horse"));
			horse.setHealth(horse.getMaxHealth());
		} else if (Tools.randomChance(2.0) || mobName.equalsIgnoreCase("Skeleton Horse")) {
			horse.setVariant(Variant.SKELETON_HORSE);
			horse.setTamed(true);
			horse.setCustomName("Skeleton Horse");
			horse.setMaxHealth(MonsterList.getMaxHealth("Skeleton Horse"));
			horse.setHealth(horse.getMaxHealth());
		} else if (Tools.randomChance(1.5) || mobName.equalsIgnoreCase("Bronco of Death")) {
			horse.setVariant(Variant.SKELETON_HORSE);
			horse.setTamed(true);
			horse.setJumpStrength(2);
			horse.setMaxHealth(MonsterList.getMaxHealth("Bronco of Death"));
			horse.setHealth(horse.getMaxHealth());

			MonstersPlus.setSpeed(horse, 0.40);

			horse.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 3));
			horse.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, MonsterListener.POTION_DURATION, 1));

			horse.setCustomName(ChatColor.WHITE + "Bronco" + ChatColor.GRAY + " of" + ChatColor.RED + " Death");
		} else if (Tools.randomChance(1.5) || mobName.equalsIgnoreCase("Plague Stallion")) {
			horse.setVariant(Variant.UNDEAD_HORSE);
			horse.setTamed(true);
			horse.setJumpStrength(2);
			horse.setMaxHealth(MonsterList.getMaxHealth("Plague Stallion"));
			horse.setHealth(horse.getMaxHealth());

			MonstersPlus.setSpeed(horse, 0.40);

			horse.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 3));
			horse.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, MonsterListener.POTION_DURATION, 1));

			horse.setCustomName(ChatColor.DARK_GREEN + "Plague" + ChatColor.DARK_PURPLE + " Stallion");
		}
	}

}
