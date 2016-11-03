package me.coolade.monstersplus.monsters;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.Tools;
import me.coolade.monstersplus.monsters.monsterevents.BlockBreakSpawnEvent;
import me.coolade.monstersplus.monsters.monsterevents.CaveSpiderSpawnEvent;
import me.coolade.monstersplus.monsters.monsterevents.CreeperSpawnEvent;
import me.coolade.monstersplus.monsters.monsterevents.HorseSpawnEvent;
import me.coolade.monstersplus.monsters.monsterevents.SkeletonSpawnEvent;
import me.coolade.monstersplus.monsters.monsterevents.SpiderSpawnEvent;
import me.coolade.monstersplus.monsters.monsterevents.ZombieSpawnEvent;

public class MonsterListener implements Listener {
	Random randomGen = new Random();

	public static final int POTION_DURATION = 7000000;
	private static final int MOBSPAWNER_RADIUS = 25;
	// The maximum amount of monsters allowed in the radius
	private static final int MOBSPAWNER_MAXMOBS = 45;

	@EventHandler(priority = EventPriority.NORMAL)
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (!MonstersPlus.isSpawnableLocation(event)) {
			return;
		} else if (event.getSpawnReason().equals(SpawnReason.SPAWNER)) {
			return;
		}

		Entity ent = event.getEntity();

		if (event.getSpawnReason().equals(SpawnReason.SPAWNER_EGG)) {
			/*
			 * Check to see if there are any OP's next to where the egg spawns
			 * and if there are, we can allow the spawn type to include these
			 * unique monsters.
			 */
			List<Entity> nbPlayers = Tools.getNearbyEntity(ent.getLocation(), EntityType.PLAYER, 10);
			boolean nearbyOp = false;
			for (Entity tempEnt : nbPlayers) {
				if (((Player) tempEnt).isOp()) {
					nearbyOp = true;
					break;
				}
			}
			if (!nearbyOp) {
				return;
			}
		}

		if (!(ent instanceof LivingEntity)) {
			return;
		}
		LivingEntity lent = (LivingEntity) ent;
		lent.setCustomNameVisible(false);

		if (ent instanceof Zombie && !(ent instanceof PigZombie)) {
			new ZombieSpawnEvent(lent);
		} else if (ent instanceof Skeleton) {
			new SkeletonSpawnEvent(lent);
		} else if (ent instanceof Spider && !(ent instanceof CaveSpider)) {
			new SpiderSpawnEvent(lent);
		} else if (lent instanceof Creeper) {
			new CreeperSpawnEvent(lent);
		} else if (ent instanceof CaveSpider) {
			new CaveSpiderSpawnEvent(lent);
		} else if (ent instanceof Horse) {
			new HorseSpawnEvent(lent);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent event) {
		new BlockBreakSpawnEvent(event);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityTarget(EntityTargetLivingEntityEvent event) {
		/*
		 * Check to see if the entities target is a player and then if the
		 * entity has a custom name, show it as visible
		 * 
		 */
		if (event.isCancelled()) {
			return;
		}

		Entity target = event.getTarget();
		Entity ent = event.getEntity();

		TargetReason reason = event.getReason();

		if (ent instanceof LivingEntity) {
			if (target instanceof Player) {
				LivingEntity lent = (LivingEntity) ent;
				LivingEntity lentPlayer = (LivingEntity) target;

				new MonsterUpdater(lent, lentPlayer, reason);
				MonsterList.updateMonsterMetaName(lent);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		// If a human and monster attack then we need to update the
		// Title of the monster
		boolean isProjectile = false;

		if (event.isCancelled()) {
			return;
		}
		Entity damager = event.getDamager();
		Entity target = event.getEntity();

		if (damager instanceof Projectile) {
			damager = ((LivingEntity) ((Projectile) damager).getShooter());
			isProjectile = true;
		}

		if (damager instanceof LivingEntity && target instanceof LivingEntity) {
			LivingEntity lentDamager = (LivingEntity) damager;
			LivingEntity lentTarget = (LivingEntity) target;

			new MonsterUpdater(lentDamager, lentTarget, isProjectile);
			MonsterList.updateMonsterMetaName(lentDamager);
			MonsterList.updateMonsterMetaName(lentTarget);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onCreeperExplode(EntityExplodeEvent event) {
		new MonsterUpdater(event);
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDeath(EntityDeathEvent event) {
		new MonsterUpdater(event);
		/**
		 * If the one of the items that is being dropped has a custom name then
		 * we want it to drop with full durability restored.
		 */
		Entity ent = event.getEntity();
		if (ent instanceof LivingEntity && !(ent instanceof Player)) {
			LivingEntity lent = (LivingEntity) ent;
			if (MonsterList.isCustomMonster(lent) && (lent instanceof Zombie) || (lent instanceof Skeleton)) {
				EntityEquipment inv = lent.getEquipment();
				if (modifyDropDurability(event, inv.getHelmet(), inv.getHelmetDropChance() * 100)) {
					inv.setHelmet(new ItemStack(Material.AIR));
				}
				if (modifyDropDurability(event, inv.getChestplate(), inv.getChestplateDropChance() * 100)) {
					inv.setChestplate(new ItemStack(Material.AIR));
				}
				if (modifyDropDurability(event, inv.getLeggings(), inv.getLeggingsDropChance() * 100)) {
					inv.setLeggings(new ItemStack(Material.AIR));
				}
				if (modifyDropDurability(event, inv.getBoots(), inv.getBootsDropChance() * 100)) {
					inv.setBoots(new ItemStack(Material.AIR));
				}
				if (modifyDropDurability(event, inv.getItemInHand(), inv.getItemInHandDropChance() * 100)) {
					inv.setItemInHand(new ItemStack(Material.AIR));
				}
			}
		}
	}

	private boolean modifyDropDurability(EntityDeathEvent event, ItemStack item, float dropChance) {
		if (dropChance > 0F && dropChance != 18.5) // 18.5 is the default value
		{
			item.setDurability((short) 0);
			if (Tools.randomChance(dropChance)) {
				event.getDrops().add(item);
			}
			return true;
		}
		return false;
	}

	public void onProjectileFire(ProjectileLaunchEvent event) {
		new MonsterUpdater(event);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onTame(EntityTameEvent event) {
		if (event.isCancelled()) {
			return;
		}
		LivingEntity lent = event.getEntity();
		if (MonsterList.isCustomMonster(lent)) {
			event.setCancelled(true);
		}
	}

	public boolean isPlayerAttackMob(Entity damager, Entity target) {
		if (damager instanceof Projectile) {
			ProjectileSource projSource = ((Projectile) damager).getShooter();
			if (!(projSource instanceof Entity)) {
				return false;
			}
			damager = (Entity) projSource;
		}

		// If a player shoots a living npc
		if ((target instanceof LivingEntity && !(target instanceof Player)) && damager instanceof Player) {
			return true;
		}
		return false;
	}

	public boolean isMobAttackPlayer(Entity damager, Entity target) {
		if (damager instanceof Projectile) {
			ProjectileSource projSource = ((Projectile) damager).getShooter();
			if (!(projSource instanceof Entity)) {
				return false;
			}
			damager = (Entity) projSource;
		}

		// If a living npc shoots a player
		if ((damager instanceof LivingEntity && !(damager instanceof Player)) && target instanceof Player) {
			return true;
		}
		return false;
	}

	/*
	 * Monster Cleaner, use this to prevent too many monsters from spawning from
	 * MobSpawners
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onMobSpawner(CreatureSpawnEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getSpawnReason().equals(SpawnReason.SPAWNER)) {
			List<Entity> nbMons = Tools.getNearbyMonsters(event.getLocation(), MOBSPAWNER_RADIUS);
			if (nbMons.size() > MOBSPAWNER_MAXMOBS) {
				event.setCancelled(true);
			}
		}
	}
}