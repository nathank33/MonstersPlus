package me.coolade.monstersplus.monsters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.Tools;
import me.coolade.monstersplus.tasks.CleanBlockTask;
import me.coolade.monstersplus.tasks.PlaceAndCleanTask;
import me.coolade.monstersplus.tasks.RespawnEntityTask;
import me.coolade.monstersplus.tasks.TaskTools;
import me.coolade.monstersplus.tasks.TntThrowTask;
import me.coolade.monstersplus.tasks.VortexCreepTask;

public class MonsterUpdater {
	Random randomGen = new Random();
	private static final double POTION_CHANCE = 65;
	@SuppressWarnings("unused")
	private static final int COLD_BOMB_RADIUS = 5;
	private static final int ROCK_SCORP_DAMAGE = 2;
	private static final double TRACK_STOP_CHANCE = 10;
	private static final boolean TP_IN_SKYWORLD = false;

	public MonsterUpdater(EntityDeathEvent event) {
		// Regular monster death events
		final int rad = 30; // If there are players within this range then
							// continue
		if (!(event.getEntity() instanceof LivingEntity)) {
			return;
		}

		LivingEntity lent = event.getEntity();
		if (MonsterList.hasCustomName(lent)) {
			ArrayList<String> nearbyPlayers = MonstersPlus.getNearbyPlayers(lent.getLocation(), rad, rad, rad);
			if (nearbyPlayers.size() > 0) {
				if (MonsterList.isCustomMonster("Holy Skeleton", lent)) {
					updateHolySkeleton(lent);
				} else if (MonsterList.isCustomMonster("Reforming Creeper", lent)) {
					updateReformingCreeper(lent);
				}
			}
		}
		MonsterList.dropPowerstones(lent);
		MonsterList.dropTrophy(lent);
	}

	public MonsterUpdater(EntityExplodeEvent event) {
		// Explosive events
		if (event.isCancelled()) {
			return;
		} else if (event.getEntity() instanceof Creeper) {
			Creeper creep = (Creeper) event.getEntity();
			if (MonsterList.isCustomMonster("Cold Bomb", creep)) {
				potionEffectEntityAOE(creep.getLocation(), 6, PotionEffectType.SLOW, 2, 4 * 20, 100);
			} else if (MonsterList.isCustomMonster("Droopy", creep)) {
				potionEffectEntityAOE(creep.getLocation(), 6, PotionEffectType.BLINDNESS, 15, 7 * 20, 100);
			} else if (MonsterList.isCustomMonster("Gas Bag", creep)) {
				potionEffectEntityAOE(creep.getLocation(), 5, PotionEffectType.POISON, 1, 6 * 20, 100);
			} else if (MonsterList.isCustomMonster("Hot Head", creep)) {
				burnEntityAOE(creep.getLocation(), 5, 7 * 20, 100);
			} else if (MonsterList.isCustomMonster("Little Boy", creep)) {
				createExplosion(creep.getLocation(), 4, 100);
			} else if (MonsterList.isCustomMonster("Quaker", creep)) {
				liftEntityUpwardAOE(creep.getLocation(), 10, 1.25, 100);
			}
		}
	}

	public MonsterUpdater(LivingEntity lent, LivingEntity target, TargetReason reason) {
		// Tracking Events
		if (MonsterList.hasCustomName(lent) && !lent.isCustomNameVisible()) {
			MonsterList.updateCustomName(lent);
		}
		if (MonsterList.isCustomMonster("Vortex Creep", lent)) {
			updateVortexCreep(lent);
		} else if (MonsterList.isCustomMonster("Atilla", lent)) {
			updateAtilla(lent);
		}
	}

	public MonsterUpdater(LivingEntity damager, LivingEntity target, boolean isProjectile) {
		// Damage Events
		if (MonsterList.hasCustomName(damager) && !damager.isCustomNameVisible() && !(target instanceof Player)) {
			MonsterList.updateCustomName(damager);
		} else if (MonsterList.hasCustomName(target) && !target.isCustomNameVisible() && !(target instanceof Player)) {
			MonsterList.updateCustomName(target);
		}

		updateMonsterTracking(damager);
		updateMonsterTracking(target);

		if (MonsterList.isCustomMonster("Scorpion", damager)) {
			Tools.potionEffectEntity(target, PotionEffectType.POISON, 0, 70, POTION_CHANCE);
		} else if (MonsterList.isCustomMonster("Arsonic Archer", damager)) {
			Tools.potionEffectEntity(target, PotionEffectType.POISON, 1, 100, 100);
		} else if (MonsterList.isCustomMonster("Fire Ant", damager)) {
			Tools.burnEntity(target, 80, 50);
		} else if (MonsterList.isCustomMonster("Toxic Ant", damager)) {
			Tools.potionEffectEntity(target, PotionEffectType.POISON, 0, 80, 50);
		} else if (MonsterList.isCustomMonster("Frost Ant", damager)) {
			Tools.potionEffectEntity(target, PotionEffectType.SLOW, 2, 80, 50);
		} else if (MonsterList.isCustomMonster("Stink Swarm", target) && (damager instanceof Player)) {
			updateStinkSwarm(target);
		} else if (MonsterList.isCustomMonster("Atilla", target) && (damager instanceof Player)) {
			Tools.damageWithoutArmorDamage(damager, target, 2);
		} else if (MonsterList.isCustomMonster("Sleepy Hollow", target) && (damager instanceof Player)) {
			liftEntityUpward(damager, 1, 33);
		} else if (MonsterList.isCustomMonster("Legolas", damager)) {
			updateLegolasAttack(damager, target);
		} else if (MonsterList.isCustomMonster("Legolas", target) && (damager instanceof Player)) {
			updateLegolasDamaged(damager, target);
		} else if (MonsterList.isCustomMonster("Hawkeye", damager)) {
			updateHawkeyeAttack(damager, target);
		} else if (MonsterList.isCustomMonster("Hawkeye", target) && (damager instanceof Player)) {
			teleportEntityToEntity(damager, target, 25);
		} else if (MonsterList.isCustomMonster("Captain Ahab", target) && (damager instanceof Player)) {
			updateCaptainAhabDamaged(damager, target);
		} else if (MonsterList.isCustomMonster("Pocus", damager)) {
			updatePocusAttack(damager, target);
		} else if (MonsterList.isCustomMonster("Pocus", target) && (damager instanceof Player)) {
			teleportEntityToEntity(damager, target, 25);
		} else if (MonsterList.isCustomMonster("Hocus", target) && (damager instanceof Player)) {
			teleportEntityToEntity(target, damager, 25);
		} else if (MonsterList.isCustomMonster("Infearno", damager)) {
			updateInfearnoAttack(damager, target);
		} else if (MonsterList.isCustomMonster("Hadamard", damager)) {
			updateHadamardAttack(damager, target);
		} else if (MonsterList.isCustomMonster("Lesath", damager)) {
			updateLesathAttack(damager, target);
		} else if (MonsterList.isCustomMonster("Shelob", damager)) {
			Tools.damageEntity(damager, target, 7);
		} else if (MonsterList.isCustomMonster("Shelob", target) && (damager instanceof Player)) {
			updateShelobDamaged(damager, target);
		} else if (MonsterList.isCustomMonster("Raikou", damager)) {
			updateRaikouAttack(damager, target);
		} else if (MonsterList.isCustomMonster("Raikou", target) && (damager instanceof Player)) {
			updateRaikouDamaged(damager, target);
		} else if (MonsterList.isCustomMonster("Tickles", target) && (damager instanceof Player)) {
			teleportEntityToEntity(damager, target, 25);
		} else if (MonsterList.isCustomMonster("Entei", damager)) {
			updateEnteiAttack(damager, target);
		} else if (MonsterList.isCustomMonster("Entei", target) && (damager instanceof Player)) {
			teleportEntityToEntity(damager, target, 25);
		} else if (MonsterList.isCustomMonster("Suicune", damager)) {
			updateSuicuneAttack(damager, target);
		} else if (MonsterList.isCustomMonster("Suicune", target) && (damager instanceof Player)) {
			teleportEntityToEntity(damager, target, 25);
		} else if (MonsterList.isCustomMonster("Barkira", damager)) {
			updateBarkiraAttack(damager, target);
		} else if (MonsterList.isCustomMonster("Barkira", target) && (damager instanceof Player)) {
			teleportEntityToEntity(damager, target, 25);
		} else if (MonsterList.isCustomMonster("Hyde", target) && (damager instanceof Player)) {
			updateHydeDamaged(damager, target);
		} else if (MonsterList.isCustomMonster("Juggler", target) && (damager instanceof Player)) {
			updateJugglerDamaged(damager, target, isProjectile);
		} else if (MonsterList.isCustomMonster("Achilles", damager)) {
			Tools.potionEffectEntity(target, PotionEffectType.SLOW, 2, 120, 100);
		} else if (MonsterList.isCustomMonster("Achilles", target) && (damager instanceof Player)) {
			updateAchillesDamaged(damager, target);
		} else if (MonsterList.isCustomMonster("Piglet", target) && (damager instanceof Player)) {
			updatePigletDamaged(damager, target);
		} else if (MonsterList.isCustomMonster("Wilbur", target) && (damager instanceof Player)) {
			updateWilburDamaged(damager, target);
		} else if (MonsterList.isCustomMonster("Arachne", damager)) {
			Tools.damageEntity(damager, target, 7);
		} else if (MonsterList.isCustomMonster("Arachne", target) && (damager instanceof Player)) {
			updateArachneDamaged(damager, target);
		} else if (MonsterList.isCustomMonster("Charlotte", damager)) {
			Tools.damageEntity(damager, target, 7);
		} else if (MonsterList.isCustomMonster("Charlotte", target) && (damager instanceof Player)) {
			updateCharlotteDamaged(damager, target);
		} else if (MonsterList.isCustomMonster("Skeleton Sniper", damager)) {
			Tools.potionEffectEntity(target, PotionEffectType.BLINDNESS, 10, 50, 100);
		} else if (MonsterList.isCustomMonster("Brown Recluse", damager)) {
			Tools.potionEffectEntity(target, PotionEffectType.CONFUSION, 12, 300, POTION_CHANCE);
		} else if (MonsterList.isCustomMonster("Lunatic", damager)) {
			Tools.potionEffectEntity(target, PotionEffectType.CONFUSION, 8, 200, POTION_CHANCE);
		} else if (MonsterList.isCustomMonster("The Restless", damager)) {
			Tools.potionEffectEntity(target, PotionEffectType.SLOW, 5, 200, 50);
		} else if ((MonsterList.isCustomMonster("Starving Creeper", target)
				|| MonsterList.isCustomMonster("Famished Lurker", target)) && (damager instanceof Player)) {
			Tools.potionEffectEntity(damager, PotionEffectType.HUNGER, 2, 160, 50);
		} else if (MonsterList.isCustomMonster("Rock Scorpion", target) && (damager instanceof Player)) {
			Tools.damageEntity(target, damager, ROCK_SCORP_DAMAGE);
		} else if ((MonsterList.isCustomMonster("Burning Walker", target)
				|| MonsterList.isCustomMonster("Burnt Ghoul", target)
				|| MonsterList.isCustomMonster("Hot Bones", target)
				|| MonsterList.isCustomMonster("Desert Scorpion", target)) && (damager instanceof Player)) {
			Tools.burnEntity(damager, 2 * 20, 40);
		} else if (MonsterList.isCustomMonster("Burning Walker", damager)
				|| MonsterList.isCustomMonster("Burnt Ghoul", target)) {
			Tools.burnEntity(target, 4 * 20, 100);
		} else if (MonsterList.isCustomMonster("Pyre", damager) || MonsterList.isCustomMonster("Archfiend", damager)) {
			Tools.burnEntity(target, 8 * 20, 100);
		} else if ((MonsterList.isCustomMonster("Cold Corpse", target)
				|| MonsterList.isCustomMonster("Frosted Biter", target)) && (damager instanceof Player)) {
			Tools.potionEffectEntity(damager, PotionEffectType.SLOW, 1, 60, 50);
		} else if (MonsterList.isCustomMonster("Arctic Zed", damager)
				|| MonsterList.isCustomMonster("Iced Sharpshooter", damager)
				|| MonsterList.isCustomMonster("Spinechiller", damager)
				|| MonsterList.isCustomMonster("Archfiend", damager)
				|| MonsterList.isCustomMonster("Ice Spider", damager)) {
			Tools.potionEffectEntity(target, PotionEffectType.SLOW, 2, 60, 100);
		} else if (MonsterList.isCustomMonster("Crazed Skeleton", damager)) {
			Tools.potionEffectEntity(target, PotionEffectType.POISON, 1, 3 * 20, 100);
		} else if (MonsterList.isCustomMonster("Filth Purch", damager)) {
			Tools.potionEffectEntity(target, PotionEffectType.WITHER, 1, 4 * 20, 100);
		} else if (MonsterList.isCustomMonster("Swamp Witch", target) && (damager instanceof Player)) {
			Tools.potionEffectEntity(damager, PotionEffectType.WITHER, 1, 6 * 20, 20);
		} else if (MonsterList.isCustomMonster("Warlock", target) && (damager instanceof Player)) {
			Tools.potionEffectEntity(damager, PotionEffectType.CONFUSION, 50, 30 * 20, 25);
		} else if (MonsterList.isCustomMonster("Dazzler", damager)) {
			Tools.potionEffectEntity(target, PotionEffectType.CONFUSION, 50, 16 * 20, 100);
		} else if (MonsterList.isCustomMonster("Famished Walker", damager)) {
			Tools.potionEffectEntity(target, PotionEffectType.HUNGER, 2, 8 * 20, 100);
		} else if (MonsterList.isCustomMonster("Dark Mage", target) && (damager instanceof Player)) {
			Tools.potionEffectEntity(damager, PotionEffectType.BLINDNESS, 80, 5 * 20, 50);
		} else if (MonsterList.isCustomMonster("Sumo Skeleton", damager)) {
			liftEntityUpward(target, 0.6, 50);
		}

		if (isProjectile) {
			if (MonsterList.isCustomMonster("Infearno", target) || MonsterList.isCustomMonster("Hadamard", target)) {
				teleportEntityToEntity(target, damager, 100);
			}
		}
	}

	public MonsterUpdater(ProjectileLaunchEvent event) {
		if (event.getEntity() instanceof Arrow) {
			ProjectileSource projSource = ((Arrow) event.getEntity()).getShooter();
			if (!(projSource instanceof LivingEntity)) {
				return;
			}
			LivingEntity lent = (LivingEntity) projSource;
			if (MonsterList.isCustomMonster("Hawkeye", lent)) {
				updateHawkeyeShot(lent, (Arrow) event.getEntity());
			}
		}
	}

	public void updateMonsterTracking(LivingEntity lent) {
		if (!(lent instanceof Monster)) {
			return;
		}
		Monster mob = (Monster) lent;
		if (Tools.randomChance(TRACK_STOP_CHANCE)) {
			mob.setTarget(lent);
		}
	}

	@SuppressWarnings("deprecation")
	public void updateStinkSwarm(LivingEntity lent) {
		// Set the maxhealth lower so that that we know that this monster has
		// been updated
		double updatedHealth = MonsterList.getMaxHealth("Stink Swarm") - 1;
		if (lent.getHealth() > updatedHealth) {
			lent.setHealth(updatedHealth);
		}
		if (updatedHealth > 0) {
			lent.setMaxHealth(updatedHealth);
		}

		Location loc = lent.getLocation();
		int rand = randomGen.nextInt(4) + 1;
		for (int i = 0; i < rand; i++) {
			LivingEntity swarm = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);

			Zombie swarmZomb = (Zombie) swarm;
			swarmZomb.setBaby(randomGen.nextBoolean());
			swarmZomb.setVillager(randomGen.nextBoolean());
			swarmZomb.setHealth(3);
			swarmZomb.setHealth(swarmZomb.getMaxHealth());
			swarmZomb.setCustomName(ChatColor.GREEN + "Stink Swarm");
		}
	}

	public void updateVortexCreep(LivingEntity lent) {
		if (!MonsterList.getTriggered(lent)) {
			final String message = ChatColor.RED + "Vortex Creep begins to power up!";
			final int amount = 10;
			final int hradius = 13;
			final int vradius = 6;
			final int delay = 20;
			final int startDelay = 0;
			final double power = 0.36;
			final int cooldown = 30;
			MonsterList.setTriggered(lent, true);
			new VortexCreepTask(lent, hradius, vradius, amount, startDelay, delay, power, message);
			// Reset the creeper
			TaskTools.setTriggeredDelayed((amount * delay + startDelay + cooldown), lent, false);
		}

	}

	public void updateReformingCreeper(LivingEntity lent) {
		final int respawnDelay = 80;
		String msg = ChatColor.GOLD + "The Creeper begins to reform";
		new RespawnEntityTask(lent, lent.getMaxHealth(), msg, respawnDelay).runTask(MonstersPlus.plugin);
	}

	public void updateHolySkeleton(LivingEntity lent) {
		final int respawnDelay = 80;
		String msg = ChatColor.GOLD + "The Holy Skeleton is Resurrecting";
		double health = lent.getMaxHealth();
		new RespawnEntityTask(lent, health, msg, respawnDelay).runTask(MonstersPlus.plugin);
	}

	public void updateAtilla(LivingEntity lent) {
		final long delay = 0;
		final long repeatDelay = 100;
		final int range = 25;
		final int blastTicks = 35;
		final int maximumThrows = -1;
		final int maxDistance = 80;
		new TntThrowTask(lent, range, maxDistance, maximumThrows, blastTicks, delay, repeatDelay);
	}

	public void updateLegolasAttack(LivingEntity damager, LivingEntity target) {
		final double PULL = 3.0;
		if (MonstersPlus.randomChance(35)) {
			int potionDuration = 8 * 20;
			target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, potionDuration, 1));
		}
		if (MonstersPlus.randomChance(100)) {
			int potionDuration = 12 * 20;
			target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, potionDuration, 15));
		}
		if (MonstersPlus.randomChance(0)) {
			MonstersPlus.pullEntity(target, damager.getLocation(), PULL);
		}
	}

	public void updateLegolasDamaged(LivingEntity damager, LivingEntity target) {
		Tools.damageWithoutArmorDamage(target, damager, 1);
		if (MonstersPlus.randomChance(30)) {
			int potionDuration = 4 * 20;
			damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, potionDuration, 3));
		}
	}

	public void updateHawkeyeAttack(LivingEntity damager, LivingEntity target) {
		teleportEntityToEntity(damager, target, 15);
	}

	public void updateHawkeyeShot(LivingEntity lent, Arrow arrow) {
		WitherSkull skull = lent.getWorld().spawn(arrow.getLocation(), WitherSkull.class);
		skull.setVelocity(arrow.getVelocity());
		skull.setShooter(lent);
	}

	public void updateInfearnoAttack(LivingEntity damager, LivingEntity target) {
		target.setFireTicks(10 * 20);
		Tools.damageEntity(damager, target, 7);
		target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, 1));
	}

	public void updateHadamardAttack(LivingEntity damager, LivingEntity target) {
		Tools.damageEntity(damager, target, 5);
		target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10 * 20, 2));
		target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 60 * 20, 20));
		target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10 * 20, 2));
	}

	public void updateLesathAttack(LivingEntity damager, LivingEntity target) {
		Tools.damageEntity(damager, target, 6);
		target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 6 * 20, 3));
		Location loc = target.getLocation();

		if (Tools.randomChance(50) && MonstersPlus.isSpawnableLocation(loc)
				&& loc.getWorld().getBlockAt(loc).getType().equals(Material.AIR)) {
			new PlaceAndCleanTask(Material.WEB, loc, 0, 60);
		}
	}

	public void updateRaikouAttack(LivingEntity damager, LivingEntity target) {
		Tools.damageEntity(damager, target, 6);
		Location loc = target.getLocation();
		if (Tools.randomChance(75)) {
			loc.getWorld().strikeLightning(loc);
			loc.getWorld().strikeLightning(loc);
			Tools.damageEntity(damager, target, 3);
		}
	}

	public void updateRaikouDamaged(LivingEntity damager, LivingEntity target) {
		teleportEntityToEntity(target, damager, 30);
		Location loc = damager.getLocation();
		if (Tools.randomChance(33)) {
			loc.getWorld().strikeLightning(loc);
			loc.getWorld().strikeLightning(loc);
			Tools.damageEntity(damager, target, 4);
		}
	}

	public void updateEnteiAttack(LivingEntity damager, LivingEntity target) {
		Tools.damageEntity(damager, target, 6);
		target.setFireTicks(8 * 20);
		Location loc = target.getLocation();
		if (Tools.randomChance(40)) {
			Block destBlock = loc.getWorld().getBlockAt((int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
			if (MonstersPlus.isSpawnableLocation(destBlock.getLocation()) && destBlock.getType().equals(Material.AIR)) {
				if (target instanceof Player
						&& MonstersPlus.isBuildLocation((Player) target, destBlock.getLocation())) {
					new PlaceAndCleanTask(Material.LAVA, destBlock.getLocation(), 0, 60);
				}
			}
		}
	}

	public void updateSuicuneAttack(LivingEntity damager, LivingEntity target) {
		Tools.damageEntity(damager, target, 8);
		target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10 * 20, 1));
		target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 8 * 20, 2));
		@SuppressWarnings("unused")
		Location loc = target.getLocation();
		if (Tools.randomChance(33)) {
			createIceRing(target, 2, true);
		}
	}

	public static void createIceRing(LivingEntity target, double seconds, boolean useSlow) {
		Location loc = target.getLocation();
		Block destBlock = loc.getWorld().getBlockAt((int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
		List<Block> blocks = Tools.getAdjacentBlocks(destBlock);
		for (Block block : blocks) {
			if (MonstersPlus.isSpawnableLocation(block.getLocation()) && block.getType().equals(Material.AIR))
			// if(MonstersPlus.isBuildLocation((Player)target,
			// block.getLocation()))
			{
				// Make sure to try and remove water aswell incase some of the
				// ice melts.
				new PlaceAndCleanTask(Material.ICE, block.getLocation(), 0, (int) (seconds * 20));
				new CleanBlockTask(Material.WATER, Material.AIR, block.getLocation(), (int) (seconds * 20) * 2);
				new CleanBlockTask(Material.STATIONARY_WATER, Material.AIR, block.getLocation(),
						(int) (seconds * 20) * 2);
				if (useSlow) {
					target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2 * 20, 5));
				}
			}
		}
	}

	public void updateBarkiraAttack(LivingEntity damager, LivingEntity target) {
		Tools.damageEntity(damager, target, 6);
		Location loc = target.getLocation();
		if (Tools.randomChance(50)) {
			loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 1.6F, false, false);
		}
	}

	public void updateHydeDamaged(LivingEntity damager, LivingEntity target) {
		teleportEntityToEntity(target, damager, 33);
		Tools.damageEntity(damager, target, 3);
		if (Tools.randomChance(50)) {
			int[] angles = { 15 };
			final int YDIFF = 3;
			if (MonstersPlus.isSpawnableLocation(damager.getLocation())
					&& MonstersPlus.isBuildLocation((Player) damager, damager.getLocation())) {
				for (int i = 0; i < 5; i++) {
					int x = randomGen.nextInt(10) - 5;
					int z = randomGen.nextInt(10) - 5;
					Vector vec = damager.getLocation().toVector()
							.subtract(target.getLocation().add(x, i * YDIFF, z).toVector());
					Tools.shootWithAngles(target, target.getLocation().add(x, i * YDIFF, z), vec, 1, angles,
							"largefireball");
				}
			}
		}
	}

	public void updateJugglerDamaged(LivingEntity damager, LivingEntity target, boolean isProjectile) {
		final double LIFT = 1.3;
		if (!target.getWorld().getBlockAt(target.getLocation().add(0, -1, 0)).getType().equals(Material.AIR)) {
			target.setHealth(target.getMaxHealth());
		}

		if (isProjectile) {
			target.damage(50);
			Location loc = target.getLocation();
			if (!target.isDead()) {
				loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 1.1F, false, false);
				target.setVelocity(new Vector(randomGen.nextDouble() - 0.50, LIFT, randomGen.nextDouble() - 0.50));
				target.getWorld().playEffect(target.getLocation(), Effect.POTION_BREAK, 3);
			}
		} else {
			target.setHealth(target.getMaxHealth());
			if (Tools.randomChance(15)) {
				((Player) damager).sendMessage(ChatColor.GOLD + "Hint: Keep shooting Juggler with arrows.");
			}
		}
	}

	public void updateShelobDamaged(LivingEntity damager, LivingEntity target) {
		final int hrange = 10;
		final int vrange = 3;
		final double chance = 65;
		final int total = 10;
		Location loc = damager.getLocation();

		if (Tools.randomChance(40)) {
			for (int i = 0; i < total; i++) {
				double x = loc.getX() + randomGen.nextInt(hrange) - (hrange / 2);
				double y = loc.getY() + randomGen.nextInt(vrange) - (vrange / 2);
				double z = loc.getZ() + randomGen.nextInt(hrange) - (hrange / 2);
				Location blockLoc = new Location(loc.getWorld(), x, y, z);
				Block block = loc.getWorld().getBlockAt(blockLoc);

				if (Tools.randomChance(chance) && MonstersPlus.isSpawnableLocation(blockLoc)
						&& block.getType().equals(Material.AIR)) {
					new PlaceAndCleanTask(Material.WEB, blockLoc, 2, 60);
				}
			}
		}
	}

	public void updateCaptainAhabDamaged(LivingEntity damager, LivingEntity target) {
		final double LIFT = 1.6;
		final int amount = 7;
		final int hradius = 15;
		final int vradius = 10;
		final int startDelay = 2;
		final int repeatDelay = 2;
		final double power = 0.55;
		final int cooldown = 0;
		final LivingEntity flent = target;

		teleportEntityToEntity(damager, target, 20);

		if (!MonsterList.getTriggered(target)) {
			if (Tools.randomChance(15)) {
				MonstersPlus.plugin.getServer().getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin,
						new Runnable() {
							@Override
							public void run() {
								flent.setVelocity(new Vector(0, LIFT, 0));
							}
						}, 1);

				MonsterList.setTriggered(target, true);
				new VortexCreepTask(target, hradius, vradius, amount, startDelay, repeatDelay, power);
				TaskTools.setTriggeredDelayed((amount * repeatDelay + startDelay + cooldown), target, false);

				for (String playerName : Tools.getNearbyPlayers(target.getLocation(), 15)) {
					Player player = Bukkit.getPlayer(playerName);
					player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 4 * 20, 2));
					Tools.damageEntity(target, player, 4);
				}
			}
		}
	}

	public void updatePocusAttack(LivingEntity damager, LivingEntity target) {
		teleportEntityToEntity(damager, target, 35);
		target.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 0, 2));
		target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 6 * 20, 4));
	}

	public void updateAchillesDamaged(LivingEntity damager, LivingEntity target) {
		teleportEntityToEntity(target, damager, 15);
		if (Tools.randomChance(15)) {
			for (String playerName : Tools.getNearbyPlayers(target.getLocation(), 15)) {
				Bukkit.getPlayer(playerName).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 60 * 20, 45));
				Tools.damageWithoutArmorDamage(target, damager, 4);
			}
		}
	}

	public void updatePigletDamaged(LivingEntity damager, LivingEntity target) {
		teleportEntityToEntity(target, damager, 35);
		for (String playerName : Tools.getNearbyPlayers(target.getLocation(), 20)) {
			Tools.damageWithoutArmorDamage(target, Bukkit.getPlayer(playerName), 8);
		}
	}

	@SuppressWarnings("deprecation")
	public void updateWilburDamaged(LivingEntity damager, LivingEntity target) {
		final int hrange = 13;
		final int vrange = 3;
		final int total = 5;
		Location loc = damager.getLocation();

		teleportEntityToEntity(target, damager, 33);
		if (Tools.randomChance(15)) {
			for (int i = 0; i < total; i++) {
				double x = loc.getX() + randomGen.nextInt(hrange) - (hrange / 2);
				double y = loc.getY() + randomGen.nextInt(vrange) - (vrange / 2);
				double z = loc.getZ() + randomGen.nextInt(hrange) - (hrange / 2);
				Location blockLoc = new Location(loc.getWorld(), x, y, z);
				Block block = loc.getWorld().getBlockAt(blockLoc);

				if (block.getType().equals(Material.AIR)) {
					LivingEntity pzomb = (LivingEntity) blockLoc.getWorld().spawnEntity(blockLoc,
							EntityType.PIG_ZOMBIE);
					pzomb.getEquipment().setItemInHand(new ItemStack(Material.IRON_AXE));
					pzomb.getEquipment().setItemInHandDropChance(0F);
				}
			}
		}
	}

	public void updateArachneDamaged(LivingEntity damager, LivingEntity target) {
		final int hrange = 13;
		final int vrange = 3;
		final int total = 5;
		Location loc = damager.getLocation();

		teleportEntityToEntity(target, damager, 33);
		if (Tools.randomChance(15)) {
			for (int i = 0; i < total; i++) {
				double x = loc.getX() + randomGen.nextInt(hrange) - (hrange / 2);
				double y = loc.getY() + randomGen.nextInt(vrange) - (vrange / 2);
				double z = loc.getZ() + randomGen.nextInt(hrange) - (hrange / 2);
				Location blockLoc = new Location(loc.getWorld(), x, y, z);
				Block block = loc.getWorld().getBlockAt(blockLoc);

				if (block.getType().equals(Material.AIR)) {
					blockLoc.getWorld().spawnEntity(blockLoc, EntityType.CAVE_SPIDER);
				}
			}
		}
	}

	public void updateCharlotteDamaged(LivingEntity damager, LivingEntity target) {
		teleportEntityToEntity(target, damager, 33);
		final int radius = 15;

		if (Tools.randomChance(15)) {
			for (String playerName : Tools.getNearbyPlayers(target.getLocation(), radius)) {
				Player player = Bukkit.getPlayer(playerName);
				Location loc = player.getLocation();
				Block block = loc.getWorld().getBlockAt(loc);
				if (MonstersPlus.isSpawnableLocation(loc) && block.getType().equals(Material.AIR)) {
					new PlaceAndCleanTask(Material.WEB, loc, 1, 60);
				}
				player.setFireTicks(6 * 20);
			}
		}
	}

	public static void liftEntityUpward(LivingEntity lent, double power, double chance) {
		if (Tools.randomChance(chance)) {
			lent.setVelocity(new Vector(0, power, 0));
		}
	}

	public static void potionEffectEntityAOE(Location loc, int radius, PotionEffectType type, int strength,
			int duration, double chance) {
		if (Tools.randomChance(chance)) {
			List<Entity> nearbyEnts = Tools.getNearbyEntities(loc, radius);
			for (Entity ent : nearbyEnts) {
				if (ent instanceof LivingEntity) {
					((LivingEntity) ent).addPotionEffect(new PotionEffect(type, duration, strength));
				}
			}
		}
	}

	public static void burnEntityAOE(Location loc, int radius, int duration, double chance) {
		if (Tools.randomChance(chance)) {
			List<Entity> nearbyEnts = Tools.getNearbyEntities(loc, radius);
			for (Entity ent : nearbyEnts) {
				if (ent instanceof LivingEntity) {
					((LivingEntity) ent).setFireTicks(duration);
				}
			}
		}
	}

	public static void liftEntityUpwardAOE(Location loc, int radius, double power, double chance) {
		if (Tools.randomChance(chance)) {
			List<Entity> nearbyEnts = Tools.getNearbyEntities(loc, radius);
			for (Entity ent : nearbyEnts) {
				if (ent instanceof LivingEntity) {
					liftEntityUpward(((LivingEntity) ent), power, chance);
				}
			}
		}
	}

	public static void createExplosion(Location loc, float strength, double chance) {
		if (Tools.randomChance(chance)) {
			loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), strength, false, false);
		}
	}

	public static void teleportEntityToEntity(LivingEntity lent1, LivingEntity lent2, double chance) {
		if (Tools.randomChance(chance)) {
			if (lent1.getWorld().getName().toLowerCase().contains("skyworld") && !TP_IN_SKYWORLD) {
				return;
			}
			lent1.teleport(lent2.getLocation());
		}
	}

}
