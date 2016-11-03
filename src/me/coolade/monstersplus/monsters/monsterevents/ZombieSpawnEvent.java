package me.coolade.monstersplus.monsters.monsterevents;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.coolade.jobsplus.customenchanting.CustomEnchantCommand;
import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.Tools;
import me.coolade.monstersplus.monsters.MonsterList;
import me.coolade.monstersplus.monsters.MonsterListener;

public class ZombieSpawnEvent {
	final static float DROP_CHANCE = 0.07F;

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

	LivingEntity lent;
	String mobName;

	public ZombieSpawnEvent(LivingEntity lent, String mobName) {
		this.lent = lent;
		this.mobName = mobName;
		createMonster();
	}

	public ZombieSpawnEvent(LivingEntity lent) {
		this(lent, MonsterList.generateMonster(EntityType.ZOMBIE, lent.getLocation().getBlock().getBiome()));
	}

	@SuppressWarnings("deprecation")
	public void createMonster() {
		if (mobName == null) {
			return;
		}

		Zombie zomb = (Zombie) lent;
		EntityEquipment ee = lent.getEquipment();
		zomb.setBaby(false);
		zomb.setVillager(false);
		Location loc = lent.getLocation();
		ee.setHelmetDropChance(0F);
		ee.setChestplateDropChance(0F);
		ee.setLeggingsDropChance(0F);
		ee.setBootsDropChance(0F);
		ee.setItemInHandDropChance(0F);

		/** ZOMBIE CHARGER easy **/
		if (mobName.equalsIgnoreCase("Zombie Charger")) {
			MonstersPlus.setSpeed(lent, 0.40);
		}
		/** LUMBER ZOMBIE easy **/
		else if (mobName.equalsIgnoreCase("Lumber Zombie")) {

			ee.setItemInHand(new ItemStack(Material.GOLD_AXE));
			ee.setHelmet(new ItemStack(Material.AIR));
			ee.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			ee.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.LEATHER_BOOTS));
		} else if (mobName.equalsIgnoreCase("Petty Thief")) {
			ee.setItemInHand(new ItemStack(Material.IRON_INGOT));
			ee.setHelmet(new ItemStack(Material.IRON_HELMET));
			ee.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			ee.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.IRON_BOOTS));
			ee.setItemInHandDropChance(1F);
		}
		/** BURNING WALKER easy **/
		else if (mobName.equalsIgnoreCase("Burning Walker")) {
			ItemStack boots = Tools.setItemDisplayName(new ItemStack(Material.DIAMOND_BOOTS),
					ChatColor.RED + "Fire Treads");
			boots.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 6);
			ee.setItemInHand(new ItemStack(Material.FIREBALL));
			ee.setBoots(boots);
			ee.setHelmet(new ItemStack(Material.SKULL_ITEM, 1, (short) 1));
			ee.setBootsDropChance(DROP_CHANCE);

			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 100));
			lent.setFireTicks(MonsterListener.POTION_DURATION);
		} else if (mobName.equalsIgnoreCase("Zombie Reaper")) {
			ItemStack inhand = new ItemStack(Material.IRON_HOE);
			inhand.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
			ee.setItemInHand(inhand);
		} else if (mobName.equalsIgnoreCase("Zombie Fisherman")) {
			ItemStack inhand = new ItemStack(Material.FISHING_ROD);
			inhand.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
			CustomEnchantCommand.enchantmentList.addCustomEnchantment(inhand, "Sharp Hook", 2);
			ee.setItemInHand(inhand);
			ee.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
			ee.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
			ee.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
			ee.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
			ee.setItemInHandDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Rotten Fighter")) {
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.INCREASE_DAMAGE, MonsterListener.POTION_DURATION, 1));
			ItemStack item = Tools.setItemDisplayName(new ItemStack(Material.DIAMOND_LEGGINGS),
					ChatColor.DARK_GREEN + "Tough Brawler Pants");
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
			item.addUnsafeEnchantment(Enchantment.OXYGEN, 2);
			item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			ee.setItemInHand(new ItemStack(Material.AIR));
			ee.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
			ee.setChestplate(new ItemStack(Material.AIR));
			ee.setLeggings(item);
			ee.setBoots(new ItemStack(Material.AIR));
			ee.setLeggingsDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Lame Brain")) {
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, MonsterListener.POTION_DURATION, 1));
			ItemStack helm = new ItemStack(Material.GOLD_HELMET);
			ItemStack leg = new ItemStack(Material.GOLD_LEGGINGS);
			ItemStack inhand = Tools.setItemDisplayName(new ItemStack(Material.SLIME_BALL),
					ChatColor.GREEN + "Brainzzzz");
			helm.addUnsafeEnchantment(Enchantment.THORNS, 2);
			leg.addUnsafeEnchantment(Enchantment.THORNS, 2);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 5);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);

			ee.setItemInHand(inhand);
			ee.setHelmet(helm);
			ee.setLeggings(leg);

			ee.setItemInHandDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Zombie Climber")) {
			ItemStack helm = new ItemStack(Material.AIR);
			ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
			ItemStack chest = new ItemStack(Material.AIR);
			ItemStack boot = new ItemStack(Material.LEATHER_BOOTS);
			ItemStack inhand = Tools.setItemDisplayName(new ItemStack(Material.LEASH),
					ChatColor.GOLD + "Mountaineers Rope");
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
			inhand.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 3);

			ee.setItemInHand(inhand);
			ee.setHelmet(helm);
			ee.setLeggings(leg);
			ee.setChestplate(chest);
			ee.setBoots(boot);

			ee.setItemInHandDropChance(DROP_CHANCE);
		}
		/** UNDEAD MONK Medium **/
		else if (mobName.equalsIgnoreCase("Undead Monk")) {
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.INCREASE_DAMAGE, MonsterListener.POTION_DURATION, 1));
			ee.setHelmet(new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
			ee.setHelmetDropChance((float) .25);
		}
		/** Lunatic Medium **/
		else if (mobName.equalsIgnoreCase("Lunatic")) {
			zomb.setBaby(false);
			zomb.setVillager(true);
			ItemStack cactus = Tools.setItemDisplayName(new ItemStack(Material.CACTUS),
					ChatColor.GREEN + "Cactuar Head");
			cactus.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
			cactus.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 4);
			cactus.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
			ee.setItemInHand(cactus);
			ee.setItemInHandDropChance(DROP_CHANCE);
		}
		/** TheRestless Medium **/
		else if (mobName.equalsIgnoreCase("The Restless")) {
			zomb.setBaby(false);
			zomb.setVillager(false);
			ItemStack bed = Tools.setItemDisplayName(new ItemStack(Material.LEASH), ChatColor.DARK_BLUE + "The Dream");
			CustomEnchantCommand.enchantmentList.addCustomEnchantment(bed, "Wither Aspect", 1);
			ee.setItemInHand(bed);
			ee.setItemInHandDropChance(DROP_CHANCE);
		}
		/** Rotting Jack MEDIUM **/
		else if (mobName.equalsIgnoreCase("Rotting Jack")) {
			MonstersPlus.setSpeed(lent, 0.40);
			ItemStack helm = Tools.setItemDisplayName(new ItemStack(Material.JACK_O_LANTERN),
					ChatColor.GOLD + "Jack Mask");
			ItemStack item = Tools.setItemDisplayName(new ItemStack(Material.GOLD_AXE), ChatColor.GOLD + "Jack Hack");
			helm.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);
			helm.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
			helm.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 1);
			item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 3);
			item.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 2);
			ee.setItemInHand(item);
			ee.setHelmet(helm);
			ee.setItemInHandDropChance(DROP_CHANCE);
			ee.setHelmetDropChance(DROP_CHANCE);
		}
		/** Stink Swarm MEDIUM **/
		else if (mobName.equalsIgnoreCase("Stink Swarm")) {

		} else if (mobName.equalsIgnoreCase("Cactuar")) {
			lent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, MonsterListener.POTION_DURATION, 1));
			ItemStack item = Tools.setItemDisplayName(new ItemStack(Material.DIAMOND_BOOTS),
					ChatColor.GREEN + "Cactuar Greaves");
			ItemStack cactus = new ItemStack(Material.CACTUS);
			cactus.addUnsafeEnchantment(Enchantment.THORNS, 2);
			ee.setHelmet(cactus);
			item.addUnsafeEnchantment(Enchantment.THORNS, 4);
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
			ee.setBoots(item);
			ee.setBootsDropChance(0.05F);
		} else if (mobName.equalsIgnoreCase("Cactii")) {
			lent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, MonsterListener.POTION_DURATION, 1));
			ItemStack item = Tools.setItemDisplayName(new ItemStack(Material.DIAMOND_LEGGINGS),
					ChatColor.GREEN + "Cactii Jammers");
			item.addUnsafeEnchantment(Enchantment.THORNS, 4);
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
			ee.setLeggings(item);
			ItemStack cactus = new ItemStack(Material.CACTUS);
			cactus.addUnsafeEnchantment(Enchantment.THORNS, 3);
			ee.setHelmet(cactus);
			ee.setLeggingsDropChance(0.05F);
		} else if (mobName.equalsIgnoreCase("Hardened Thief")) {
			ee.setItemInHand(new ItemStack(Material.GOLD_INGOT));
			ee.setHelmet(new ItemStack(Material.GOLD_HELMET));
			ee.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
			ee.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.GOLD_BOOTS));
			ee.setItemInHandDropChance(1F);
		} else if (mobName.equalsIgnoreCase("Famished Lurker")) {
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, MonsterListener.POTION_DURATION, 1));
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.INCREASE_DAMAGE, MonsterListener.POTION_DURATION, 2));
			ItemStack item = Tools.setItemDisplayName(new ItemStack(Material.CHAINMAIL_CHESTPLATE),
					ChatColor.DARK_GREEN + "Famine Mail");
			item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
			item.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 2);
			CustomEnchantCommand.enchantmentList.addCustomEnchantment(item, "Poverty Barrier", 1);

			ee.setItemInHand(new ItemStack(Material.STICK));
			ee.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
			ee.setLeggings(item);
			ee.setChestplateDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Cold Corpse")) {

			ItemStack item = Tools.setItemDisplayName(new ItemStack(Material.ICE),
					ChatColor.DARK_BLUE + "Vigilance Cube");
			item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 3);
			item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 3);
			CustomEnchantCommand.enchantmentList.addCustomEnchantment(item, "Ice Aspect", 1);

			ee.setItemInHand(new ItemStack(Material.IRON_SWORD));
			ee.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.LEATHER_BOOTS));
			ee.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			ee.setHelmet(item);
			ee.setHelmetDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Frosted Biter")) {
			ee.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
			ee.setItemInHandDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Arctic Zed")) {
			MonstersPlus.setSpeed(lent, 0.29);
			ItemStack inhand = Tools.setItemDisplayName(new ItemStack(Material.DIAMOND_HOE),
					ChatColor.DARK_AQUA + "Zed Hoe");
			inhand.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
			CustomEnchantCommand.enchantmentList.addCustomEnchantment(inhand, "Hydration", 1);

			ee.setItemInHand(inhand);
			ee.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
			ee.setItemInHandDropChance(DROP_CHANCE);
		}
		/** UNDYING Hard **/
		else if (mobName.equalsIgnoreCase("Undying")) {
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.INCREASE_DAMAGE, MonsterListener.POTION_DURATION, 1));

			ItemStack hoe = Tools.setItemDisplayName(new ItemStack(Material.DIAMOND_HOE), ChatColor.RED + "Killsickle");
			hoe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
			hoe.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
			hoe.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 2);
			hoe.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 1);

			ee.setItemInHand(hoe);
			ee.setHelmet(new ItemStack(Material.AIR));
			ee.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			ee.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.LEATHER_BOOTS));

			ee.setItemInHandDropChance(DROP_CHANCE);
		} else if (mobName.equalsIgnoreCase("Royal Thief")) {
			ee.setItemInHand(new ItemStack(Material.DIAMOND));
			ee.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
			ee.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			ee.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			ee.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
			ee.setItemInHandDropChance(1F);
		} else if (mobName.equalsIgnoreCase("Burnt Ghoul")) {
			ee.setItemInHand(new ItemStack(Material.IRON_SWORD));
			ee.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			ee.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		} else if (mobName.equalsIgnoreCase("Qactuar")) {
			lent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, MonsterListener.POTION_DURATION, 1));
			ItemStack item = Tools.setItemDisplayName(new ItemStack(Material.DIAMOND_CHESTPLATE),
					ChatColor.GREEN + "Qactuar Jacket");
			item.addUnsafeEnchantment(Enchantment.THORNS, 4);
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
			ee.setChestplate(item);
			ItemStack cactus = new ItemStack(Material.CACTUS);
			cactus.addUnsafeEnchantment(Enchantment.THORNS, 4);
			ee.setHelmet(cactus);
			ee.setChestplateDropChance(0.05F);
		}

		/** ATILLA Legendary **/
		else if (mobName.equalsIgnoreCase("Atilla")) {
			MonstersPlus.setAttack(lent, 20);
			ItemStack helm = new ItemStack(Material.GOLD_HELMET);
			ItemStack leg = new ItemStack(Material.GOLD_LEGGINGS);
			ItemStack chest = new ItemStack(Material.GOLD_CHESTPLATE);
			ItemStack boot = new ItemStack(Material.GOLD_BOOTS);
			ItemStack tnt = new ItemStack(Material.TNT);
			tnt.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 6);
			helm.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 30);
			leg.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 30);
			chest.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 30);
			chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 8);
			boot.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 90);

			ee.setItemInHand(tnt);
			ee.setHelmet(helm);
			ee.setLeggings(leg);
			ee.setChestplate(chest);
			ee.setBoots(boot);
		}
		/** Sleepy Hollow Legendary **/
		else if (mobName.equalsIgnoreCase("Sleepy Hollow")) {
			lent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, MonsterListener.POTION_DURATION, 1));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 2));

			ItemStack leg = new ItemStack(Material.IRON_LEGGINGS);
			ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
			ItemStack boot = new ItemStack(Material.IRON_BOOTS);
			ItemStack inhand = new ItemStack(Material.DIAMOND_SWORD);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 26);
			inhand.addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);
			leg.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 90);
			chest.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 90);
			boot.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			chest.addUnsafeEnchantment(Enchantment.THORNS, 6);

			ee.setItemInHand(inhand);
			ee.setLeggings(leg);
			ee.setChestplate(chest);
			ee.setBoots(boot);
		}
		/** Hyde Legendary **/
		else if (mobName.equalsIgnoreCase("Hyde")) {
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 1));
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 50));

			ItemStack helm = new ItemStack(Material.SKULL_ITEM, 1, (short) 5);
			ItemStack leg = new ItemStack(Material.DIAMOND_LEGGINGS);
			ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
			ItemStack boot = new ItemStack(Material.IRON_BOOTS);
			ItemStack inhand = new ItemStack(Material.DIAMOND_SWORD);

			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 30);

			leg.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 90);
			chest.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 90);
			chest.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 90);
			boot.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

			ee.setItemInHand(inhand);
			ee.setHelmet(helm);
			ee.setLeggings(leg);
			ee.setChestplate(chest);
			ee.setBoots(boot);
		}
		/** Juggler Legendary **/
		else if (mobName.equalsIgnoreCase("Juggler")) {
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, MonsterListener.POTION_DURATION, 1));
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 50));

			ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
			ItemStack leg = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			ItemStack boot = new ItemStack(Material.CHAINMAIL_BOOTS);
			ItemStack inhand = new ItemStack(Material.CLAY_BALL);

			leg.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 90);
			chest.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 90);
			chest.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 90);
			boot.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

			ee.setItemInHand(inhand);
			ee.setHelmet(helm);
			ee.setLeggings(leg);
			ee.setChestplate(chest);
			ee.setBoots(boot);
		}
		/** Hocus Pocus Legendary **/
		else if (mobName.equalsIgnoreCase("Hocus")) {
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.WITCH);
			lent.setMaxHealth(MonsterList.getMaxHealth("Hocus"));
			lent.setHealth(lent.getMaxHealth());

			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 50));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 3));

			ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
			ItemStack leg = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			ItemStack boot = new ItemStack(Material.CHAINMAIL_BOOTS);

			leg.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 90);
			chest.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 90);
			chest.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 90);
			boot.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

			ee.setHelmet(helm);
			ee.setLeggings(leg);
			ee.setChestplate(chest);
			ee.setBoots(boot);
			lent.setCustomName(ChatColor.DARK_PURPLE + "Hocus");

			LivingEntity pocus = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.BLAZE);
			pocus.setMaxHealth(MonsterList.getMaxHealth("Pocus"));
			pocus.setHealth(pocus.getMaxHealth());
			pocus.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 50));
			pocus.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 1));
			pocus.setCustomName(ChatColor.DARK_PURPLE + "Pocus");
			LivingEntity bat = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.BAT);
			bat.setMaxHealth(100);
			bat.setHealth(bat.getMaxHealth());
			bat.addPotionEffect(
					new PotionEffect(PotionEffectType.FIRE_RESISTANCE, MonsterListener.POTION_DURATION, 50));
			bat.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, MonsterListener.POTION_DURATION, 2));
			bat.setPassenger(lent);
			lent.setPassenger(pocus);
		}
		/** Achilles Legendary **/
		else if (mobName.equalsIgnoreCase("Achilles")) {
			zomb.setVillager(false);
			lent.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, MonsterListener.POTION_DURATION, 5));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 2));

			// ItemStack helm = new ItemStack(Material.DIAMOND_HELMET);
			ItemStack leg = new ItemStack(Material.DIAMOND_LEGGINGS);
			ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
			ItemStack boot = new ItemStack(Material.DIAMOND_BOOTS);
			ItemStack inhand = new ItemStack(Material.DIAMOND_SWORD);

			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 26);
			chest.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 90);
			boot.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);

			ee.setItemInHand(inhand);
			ee.setLeggings(leg);
			ee.setChestplate(chest);
			ee.setBoots(boot);
		}
		/** Piglet Legendary **/
		else if (mobName.equalsIgnoreCase("Piglet")) {
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.PIG_ZOMBIE);

			lent.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, MonsterListener.POTION_DURATION, 5));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 2));
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, MonsterListener.POTION_DURATION, 1));

			ItemStack inhand = new ItemStack(Material.DIAMOND_SWORD);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 42);

			ee.setItemInHand(inhand);
		}
		/** Wilbur Legendary **/
		else if (mobName.equalsIgnoreCase("Wilbur")) {
			lent.remove();
			lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.PIG_ZOMBIE);
			lent.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, MonsterListener.POTION_DURATION, 8));
			lent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 2));
			lent.addPotionEffect(
					new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, MonsterListener.POTION_DURATION, 1));

			ItemStack inhand = new ItemStack(Material.DIAMOND_SWORD);
			inhand.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 42);
			ee.setItemInHand(inhand);
		}

		double health = MonsterList.getMaxHealth(mobName);
		if (health > 0) {
			lent.setMaxHealth(health);
			lent.setHealth(health);
			lent.setCustomName(MonsterList.getColoredMobName(mobName));
		}
	}
}
