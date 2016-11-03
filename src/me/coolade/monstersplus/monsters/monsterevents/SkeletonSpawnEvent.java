package me.coolade.monstersplus.monsters.monsterevents;

import me.coolade.jobsplus.customenchanting.CustomEnchantCommand;
import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.Tools;
import me.coolade.monstersplus.monsters.MonsterList;
import me.coolade.monstersplus.monsters.MonsterListener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SkeletonSpawnEvent {
	final static float DROP_CHANCE = 0.07F;
	LivingEntity lent;
	String mobName;
	boolean command;

	public SkeletonSpawnEvent(LivingEntity lent, String mobName) {
		this.lent = lent;
		this.mobName = mobName;
		createMonster();
	}

	public SkeletonSpawnEvent(LivingEntity lent) {
		this(lent, MonsterList.generateMonster(EntityType.SKELETON, lent.getLocation().getBlock().getBiome()));
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

		/** Skeleton Knight **/
		if (mobName.equalsIgnoreCase("Skeleton Knight")) {
			ee.setItemInHand(new ItemStack(Material.WOOD_SWORD));
			ee.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		}
		/** Skeleton Fighter **/
		else if (mobName.equalsIgnoreCase("Skeleton Fighter")) {
			ee.setItemInHand(new ItemStack(Material.STONE_AXE));
		} else if (mobName.equalsIgnoreCase("Skeleton Pirate")) {
			ItemStack inhand = Tools.setItemDisplayName(new ItemStack(Material.FLINT_AND_STEEL),
					ChatColor.DARK_RED + "Pirate Flint");
			inhand.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);
			inhand.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
			CustomEnchantCommand.enchantmentList.addCustomEnchantment(inhand, "Wither Aspect", 1);

			ee.setItemInHand(inhand);
			ee.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
			ee.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
			ee.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
			ee.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
			ee.setItemInHandDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Cranial Basher")) {
			ItemStack inhand = Tools.setItemDisplayName(new ItemStack(Material.SKULL_ITEM),
					ChatColor.DARK_RED + "Scourge");
			inhand.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
			ee.setItemInHand(inhand);
			ee.setHelmet(new ItemStack(Material.IRON_HELMET));
			ee.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
			ee.setItemInHandDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Hot Bones")) {
			ItemStack inhand = Tools.setItemDisplayName(new ItemStack(Material.BONE),
					ChatColor.DARK_RED + "Zwei Bone of Ra");
			inhand.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 3);
			ee.setItemInHand(inhand);
			ee.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
			ee.setItemInHandDropChance(DROP_CHANCE);
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 100));
			lent.setFireTicks(MonsterListener.POTION_DURATION);
		} else if (mobName.equalsIgnoreCase("Skeleton Fisherman")) {
			ItemStack inhand = Tools.setItemDisplayName(new ItemStack(Material.FISHING_ROD),
					ChatColor.DARK_RED + "Rod of Prophecy");
			inhand.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
			CustomEnchantCommand.enchantmentList.addCustomEnchantment(inhand, "Fire Hook", 1);

			ee.setItemInHand(inhand);
			ee.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
			ee.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
			ee.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
			ee.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
			ee.setItemInHandDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Dead Gardener")) {
			ItemStack inhand = new ItemStack(Material.IRON_HOE);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
			ee.setItemInHand(inhand);
			ee.setHelmet(new ItemStack(Material.GRASS));
			ee.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			ee.setBoots(new ItemStack(Material.LEATHER_BOOTS));
		}
		/** Bone Soldier **/
		else if (mobName.equalsIgnoreCase("Bone Soldier")) {
			ee.setItemInHand(new ItemStack(Material.STONE_AXE));
			ee.setHelmet(new ItemStack(Material.LEATHER_HELMET));
			ee.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			ee.setBoots(new ItemStack(Material.AIR));
		}
		/** Skeleton Champion Medium **/
		else if (mobName.equalsIgnoreCase("Skeleton Champion")) {
			ee.setItemInHand(new ItemStack(Material.IRON_SWORD));
			ee.setHelmet(new ItemStack(Material.AIR));
			ee.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
			ee.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.AIR));
		} else if (mobName.equalsIgnoreCase("Dazzler")) {
			ItemStack bow = Tools.setItemDisplayName(new ItemStack(Material.BOW), ChatColor.DARK_GRAY + "Kridershot");
			bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 3);
			bow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
			bow.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
			CustomEnchantCommand.enchantmentList.addCustomEnchantment(bow, "Drunk Shot", 1);
			ee.setItemInHand(bow);

			ee.setHelmet(new ItemStack(Material.IRON_HELMET));
			ee.setLeggings(new ItemStack(Material.AIR));
			ee.setChestplate(new ItemStack(Material.AIR));
			ee.setBoots(new ItemStack(Material.IRON_BOOTS));
			ee.setItemInHandDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Famished Walker")) {
			ItemStack inhand = new ItemStack(Material.ROTTEN_FLESH);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
			ee.setItemInHand(inhand);
		} else if (mobName.equalsIgnoreCase("Cinderton")) {
			ItemStack inhand = new ItemStack(Material.BLAZE_ROD);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
			ee.setItemInHand(inhand);
			ItemStack item = new ItemStack(Material.LEATHER_HELMET);
			ItemStack item2 = new ItemStack(Material.LEATHER_CHESTPLATE);
			ItemStack item3 = new ItemStack(Material.LEATHER_LEGGINGS);
			ItemStack item4 = new ItemStack(Material.LEATHER_BOOTS);
			item.addUnsafeEnchantment(Enchantment.THORNS, 2);
			item2.addUnsafeEnchantment(Enchantment.THORNS, 2);
			item3.addUnsafeEnchantment(Enchantment.THORNS, 2);
			item4.addUnsafeEnchantment(Enchantment.THORNS, 2);
			ee.setHelmet(item);
			ee.setChestplate(item2);
			ee.setLeggings(item3);
			ee.setBoots(item4);
		} else if (mobName.equalsIgnoreCase("Sumo Skeleton")) {
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 1));
			ItemStack inhand = new ItemStack(Material.AIR);
			ee.setItemInHand(inhand);
			ItemStack helm = Tools.setItemDisplayName(new ItemStack(Material.LEATHER_HELMET),
					ChatColor.AQUA + "Sky Cap");
			helm.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
			helm.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 5);
			ee.setHelmet(helm);
			ee.setHelmetDropChance(DROP_CHANCE);
		}
		/** Holy Skeleton Medium **/
		else if (mobName.equalsIgnoreCase("Holy Skeleton")) {
			ItemStack bow = Tools.setItemDisplayName(new ItemStack(Material.BOW), ChatColor.AQUA + "Holy Bow");
			bow.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 2);
			bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
			bow.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
			ee.setItemInHand(bow);
			ee.setItemInHandDropChance(DROP_CHANCE);
		}
		/** Flayed MEDIUM **/
		else if (mobName.equalsIgnoreCase("Flayed")) {
			lent.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, MonsterListener.POTION_DURATION, 1));

			// Fire bow
			ItemStack bow = Tools.setItemDisplayName(new ItemStack(Material.BOW),
					ChatColor.DARK_GRAY + "Flay them Living!");
			bow.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 3);
			bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 2);
			bow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
			ee.setItemInHand(bow);
			// Wither Skull
			ee.setHelmet(new ItemStack(Material.SKULL_ITEM, 1, (short) 1));
			ee.setItemInHandDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Iced Sharpshooter")) {
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 2);
			ee.setItemInHand(bow);
		}
		/** Arsonic Archer **/
		else if (mobName.equalsIgnoreCase("Arsonic Archer")) {
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 5);

			ee.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
			ee.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
			ee.setItemInHand(bow);
		}
		/** Skeleton Sniper **/
		else if (mobName.equalsIgnoreCase("Skeleton Sniper")) {
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 6);
			ee.setItemInHand(bow);

			ee.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
			ee.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
			ee.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.LEATHER_BOOTS));
		}
		/** Death Knight HARD **/
		else if (mobName.equalsIgnoreCase("Death Knight")) {
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 2));

			// Fire bow
			ItemStack inhand = Tools.setItemDisplayName(new ItemStack(Material.BOW),
					ChatColor.DARK_GRAY + "Raining " + ChatColor.RED + "Death");
			inhand.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 2);
			inhand.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 4);
			ee.setItemInHand(inhand);

			ee.setHelmet(new ItemStack(Material.AIR));
			ee.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			ee.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.LEATHER_BOOTS));
			ee.setItemInHandDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Miner of Death")) {
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 1));
			ItemStack inhand = Tools.setItemDisplayName(new ItemStack(Material.DIAMOND_PICKAXE),
					ChatColor.DARK_AQUA + "War Pick");
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
			inhand.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
			inhand.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
			inhand.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 2);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 2);
			ee.setItemInHand(inhand);
			ee.setHelmet(new ItemStack(Material.LEATHER_HELMET));
			ee.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			ee.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.LEATHER_BOOTS));
			ee.setItemInHandDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Spinechiller")) {
			ItemStack inhand = Tools.setItemDisplayName(new ItemStack(Material.IRON_AXE),
					ChatColor.DARK_AQUA + "The Chiller");
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
			inhand.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
			CustomEnchantCommand.enchantmentList.addCustomEnchantment(inhand, "Ice Aspect", 1);

			ee.setItemInHand(inhand);
			ee.setHelmet(new ItemStack(Material.AIR));
			ee.setLeggings(new ItemStack(Material.DIAMOND_CHESTPLATE));
			ee.setChestplate(new ItemStack(Material.AIR));
			ee.setBoots(new ItemStack(Material.AIR));
			ee.setItemInHandDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Pyre")) {
			ItemStack inhand = Tools.setItemDisplayName(new ItemStack(Material.FURNACE), ChatColor.RED + "The Pyre");
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 100));
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 3);
			ee.setItemInHand(inhand);
			ee.setHelmet(new ItemStack(Material.GOLD_HELMET));
			ee.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
			ee.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.GOLD_BOOTS));
			ee.setItemInHandDropChance(DROP_CHANCE);
			lent.setFireTicks(MonsterListener.POTION_DURATION);
		} else if (mobName.equalsIgnoreCase("Bad Blacksmith")) {
			ItemStack inhand = Tools.setItemDisplayName(new ItemStack(Material.ANVIL),
					ChatColor.DARK_AQUA + "Worthy Anvil");
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.INCREASE_DAMAGE, MonsterListener.POTION_DURATION, 2));
			inhand.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
			inhand.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 2);
			ee.setItemInHand(inhand);
			ee.setHelmet(new ItemStack(Material.AIR));
			ee.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			ee.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.GOLD_BOOTS));
			ee.setItemInHandDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Crazed Skeleton")) {
			ItemStack inhand = Tools.setItemDisplayName(new ItemStack(Material.CACTUS),
					ChatColor.DARK_AQUA + "Crazy Cactus");
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 3);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 3);
			ee.setItemInHand(inhand);
			ee.setHelmet(new ItemStack(Material.AIR));
			ee.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			ee.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.AIR));
			ee.setItemInHandDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Executioner")) {
			MonstersPlus.setSpeed(lent, 0.28);
			ItemStack inhand = Tools.setItemDisplayName(new ItemStack(Material.DIAMOND_AXE),
					ChatColor.DARK_AQUA + "The Beheader");
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 2);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 2);
			inhand.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
			inhand.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
			ee.setItemInHand(inhand);
			ee.setHelmet(new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
			ee.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			ee.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.IRON_BOOTS));
			ee.setItemInHandDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Archfiend")) {
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 1));
			ItemStack inhand = Tools.setItemDisplayName(new ItemStack(Material.BOW), ChatColor.DARK_AQUA + "Fiend Bow");
			inhand.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 3);
			inhand.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
			inhand.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
			inhand.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
			ee.setItemInHand(inhand);
			ee.setHelmet(new ItemStack(Material.AIR));
			ee.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			ee.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.GOLD_BOOTS));
			ee.setItemInHandDropChance(DROP_CHANCE);
		}
		/** Legolas LEGENDARY **/
		else if (mobName.equalsIgnoreCase("Legolas")) {
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 13);
			bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 80);

			ItemStack helm = new ItemStack(Material.AIR);
			ItemStack leg = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
			ItemStack boot = new ItemStack(Material.CHAINMAIL_BOOTS);

			leg.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			boot.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			leg.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 90);

			ee.setItemInHand(bow);
			ee.setHelmet(helm);
			ee.setLeggings(leg);
			ee.setChestplate(chest);
			ee.setBoots(boot);
		}
		/** Hawkeye LEGENDARY **/
		else if (mobName.equalsIgnoreCase("Hawkeye")) {
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 80);

			ItemStack helm = new ItemStack(Material.SKULL_ITEM, 1, (short) 1);
			ItemStack leg = new ItemStack(Material.DIAMOND_LEGGINGS);
			ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
			ItemStack boot = new ItemStack(Material.DIAMOND_BOOTS);

			leg.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 90);
			leg.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 90);
			leg.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6);

			ee.setItemInHand(bow);
			ee.setHelmet(helm);
			ee.setLeggings(leg);
			ee.setChestplate(chest);
			ee.setBoots(boot);
		}
		/** Tickles LEGENDARY **/
		else if (mobName.equalsIgnoreCase("Tickles")) {
			((Skeleton) lent).setSkeletonType(SkeletonType.WITHER);
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 2));

			ItemStack bow = new ItemStack(Material.FEATHER);
			bow.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 18);
			ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
			helm.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
			helm.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 90);

			ee.setItemInHand(bow);
			ee.setHelmet(helm);
		}
		/** Captain Ahab LEGENDARY **/
		else if (mobName.equalsIgnoreCase("Captain Ahab")) {
			ItemStack inhand = new ItemStack(Material.FISHING_ROD);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 12);

			ItemStack helm = new ItemStack(Material.CHAINMAIL_HELMET);
			ItemStack leg = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			ItemStack boot = new ItemStack(Material.CHAINMAIL_BOOTS);

			leg.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 90);
			leg.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6);

			ee.setItemInHand(inhand);
			ee.setHelmet(helm);
			ee.setLeggings(leg);
			ee.setChestplate(chest);
			ee.setBoots(boot);
		}

		double health = MonsterList.getMaxHealth(mobName);
		if (health > 0) {
			lent.setMaxHealth(health);
			lent.setHealth(health);
			lent.setCustomName(MonsterList.getColoredMobName(mobName));
		}
	}
}
