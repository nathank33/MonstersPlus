package me.coolade.jobsplus.customenchanting;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

import me.coolade.jobsplus.customenchanting.CustomEnchantment.EnchantmentSetOnItem;
import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.Tools;

public class CustomEnchantListener implements Listener {
	private static final double ICE_ASPECT_PR = 2.0;
	private static final double ICE_SHOT_PR = 2.5;

	private static final double LIFE_SHOT_PR = 0.40;
	private static final double LIFESTEAL_PR = 0.20;

	private static final double WITHER_ASPECT_PR = 2;
	private static final double WITHER_SHOT_PR = 2.5;

	private static final double RETINA_PR = 2.5;
	private static final double EYE_GOUGE_PR = 2.0;

	private static final double POISON_SHOT_PR = 2.5;
	private static final double TOXIC_ASPECT_PR = 2.0;

	private static final double CONFUSING_ASPECT_PR = 7;
	private static final double DRUNK_SHOT_PR = 7;

	private static final double FIRE_HOOK_PR = 3.5;
	private static final double SHARP_HOOK_PR = 2;

	private static final int SHEER_LUCK_PR = 5;
	private static final double SHEER_LUCK_CHANCE = 65;
	private static final double SHEER_LUCK_CHICKEN = 15;
	private static final double SHEER_LUCK_COW = 7;

	private static final int LUCKY_BREAK_PR = 3;

	private static final int TILLING_PR = 1;
	private static final double TILLING_DURAB_PERCENTAGE = 50;

	private static final int SEEDFINDER_PR = 3;
	private static final double SEEDFINDER_PERCENTAGE = 6;

	private static final double PUBERTY_PERCENTAGE = 50;

	private static final int HYDRATION_PR = 1;

	private static final short PAPERCUT_DURAB_AMOUNT = 5;

	private static final double SMASH_PR = 2.5;
	private static final int SMASH_RADIUS = 1;
	private static final Material[] SMASH_MATS = new Material[] { Material.STONE, Material.COBBLESTONE, Material.GRAVEL,
			Material.CLAY, Material.NETHERRACK };

	private static final double MOLTEN_SHELL_DUR = 4;
	private static final double MOLTEN_SHELL_CHANCE_PR = 10;
	private static final double POISON_SHELL_CHANCE_PR = 10;
	private static final double POISON_SHELL_DUR = 4;
	private static final int POISON_SHELL_STR = 1;
	private static final double WITHER_SHELL_DUR = 5;
	private static final int WITHER_SHELL_STR = 1;
	private static final double WITHER_SHELL_CHANCE_PR = 10;
	private static final double POVERTY_BARRIER_CHANCE_PR = 10;
	private static final int POVERTY_BARRIER_STR = 4;
	private static final double POVERTY_BARRIER_DUR = 10;
	private static final double CONFUSING_BARRIER_CHANCE_PR = 10;
	private static final double CONFUSING_BARRIER_DUR = 10;
	private static final int CONFUSING_BARRIER_STR = 30;
	private static final double BLINDING_BARRIER_CHANCE_PR = 10;
	private static final double BLINDING_BARRIER_DUR = 5;
	private static final int BLINDING_BARRIER_STR = 10;
	private static final double REGEN_AURA_DUR = 4;
	private static final int REGEN_AURA_STR = 0;
	private static final double REGEN_AURA_CHANCE_PR = 10;
	private static final double HEALTH_AURA_DUR = 3;
	private static final int HEALTH_AURA_STR = 0;
	private static final int HEALTH_AURA_CHANCE_PR = 10;
	private static final int BERSERK_AURA_CHANCE_PR = 10;
	private static final double BERSERK_AURA_DUR = 4;
	private static final int BERSERK_AURA_STR = 0;

	private CustomEnchantment custEnch = new CustomEnchantment();

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerHit(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Entity entDamager = event.getDamager();
		Entity entTarget = event.getEntity();
		boolean wasShot = false;

		if (entDamager instanceof Arrow) {
			ProjectileSource projSource = ((Arrow) entDamager).getShooter();
			if (!(projSource instanceof Entity)) {
				return;
			}
			entDamager = (Entity) projSource;
			wasShot = true;
		}

		if (!(entDamager instanceof LivingEntity) || !(entTarget instanceof LivingEntity)) {
			return;
		}

		LivingEntity lentTar = (LivingEntity) entTarget;
		LivingEntity lentDam = (LivingEntity) entDamager;

		if (!MonstersPlus.isPVPLocation(lentDam.getLocation())) {
			return;
		}

		EntityEquipment equip = lentDam.getEquipment();
		ItemStack[] targetArmor = lentTar.getEquipment().getArmorContents();
		@SuppressWarnings("deprecation")
		ItemStack inhand = equip.getItemInHand();
		List<EnchantmentSetOnItem> inhandEnchs = custEnch.getCustomEnchantments(inhand);
		List<EnchantmentSetOnItem> tarArmorEnchs = custEnch.combineSimilarEnchantments(targetArmor);

		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Lifesteal")) {
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Lifesteal");
			int rank = esoi.getRank();
			double health = lentDam.getHealth();
			health += (LIFESTEAL_PR * rank);

			if (health > lentDam.getMaxHealth()) {
				health = lentDam.getMaxHealth();
			}
			lentDam.setHealth(health);
		}
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Life Shot") && wasShot) {
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Life Shot");
			int rank = esoi.getRank();
			double health = lentDam.getHealth();
			health += (LIFE_SHOT_PR * rank);

			if (health > lentDam.getMaxHealth()) {
				health = lentDam.getMaxHealth();
			}
			lentDam.setHealth(health);
		}
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Ice Aspect")) {
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Ice Aspect");
			int rank = esoi.getRank();
			lentTar.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) (ICE_ASPECT_PR * rank * 20), 1));
		}
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Ice Shot") && wasShot) {
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Ice Shot");
			int rank = esoi.getRank();
			lentTar.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) (ICE_SHOT_PR * rank * 20), 1));
		}
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Wither Aspect")) {
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Wither Aspect");
			int rank = esoi.getRank();
			lentTar.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int) (WITHER_ASPECT_PR * rank * 20), 1));
		}
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Wither Shot") && wasShot) {
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Wither Shot");
			int rank = esoi.getRank();
			lentTar.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int) (WITHER_SHOT_PR * rank * 20), 1));
		}
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Eye Gouge")) {
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Eye Gouge");
			int rank = esoi.getRank();
			lentTar.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (int) (EYE_GOUGE_PR * rank * 20), 2));
		}
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Retina") && wasShot) {
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Retina");
			int rank = esoi.getRank();
			lentTar.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (int) (RETINA_PR * rank * 20), 2));
		}
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Toxic Aspect")) {
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Toxic Aspect");
			int rank = esoi.getRank();
			lentTar.addPotionEffect(new PotionEffect(PotionEffectType.POISON, (int) (TOXIC_ASPECT_PR * rank * 20), 1));
		}
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Poison Shot") && wasShot) {
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Poison Shot");
			int rank = esoi.getRank();
			lentTar.addPotionEffect(new PotionEffect(PotionEffectType.POISON, (int) (POISON_SHOT_PR * rank * 20), 1));
		}
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Confusing Aspect")) {
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Confusing Aspect");
			int rank = esoi.getRank();
			lentTar.addPotionEffect(
					new PotionEffect(PotionEffectType.CONFUSION, (int) (CONFUSING_ASPECT_PR * rank * 20), 5));
		}
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Drunk Shot")) {
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Drunk Shot");
			int rank = esoi.getRank();
			lentTar.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (int) (DRUNK_SHOT_PR * rank * 20), 5));
		}
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Puberty")) {
			Player player = (Player) entDamager;
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Puberty");
			int rank = esoi.getRank();
			if (lentTar instanceof Ageable && MonstersPlus.isBuildLocation(player, lentTar.getLocation())) {
				Ageable ageable = (Ageable) lentTar;
				if (Tools.randomChance(PUBERTY_PERCENTAGE * rank)) {
					if (!ageable.isAdult()) {
						ageable.setAdult();
					} else {
						ageable.setBaby();
					}
				}
			} else if (lentTar instanceof Zombie && MonstersPlus.isBuildLocation(player, lentTar.getLocation())) {
				Zombie zomb = (Zombie) lentTar;
				if (Tools.randomChance(PUBERTY_PERCENTAGE * rank)) {
					if (!zomb.isBaby()) {
						zomb.setBaby(true);
					} else {
						zomb.setBaby(false);
					}
				}
			}
		}
		if (event.getCause() != DamageCause.CUSTOM && event.getCause() != DamageCause.PROJECTILE) {
			if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Sheer Luck")) {
				EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Sheer Luck");
				int rank = esoi.getRank();
				if (lentTar instanceof Chicken) {
					if (Tools.randomChance(SHEER_LUCK_CHICKEN * rank)) {
						lentTar.getWorld().dropItemNaturally(lentTar.getLocation(), new ItemStack(Material.EGG));
					}
					if (Tools.randomChance(SHEER_LUCK_CHICKEN * rank)) {
						lentTar.getWorld().dropItemNaturally(lentTar.getLocation(), new ItemStack(Material.FEATHER));
					}
				} else if (lentTar instanceof Cow) {
					if (Tools.randomChance(SHEER_LUCK_COW * rank)) {
						lentTar.getWorld().dropItemNaturally(lentTar.getLocation(), new ItemStack(Material.LEATHER));
					}
				}
			}
			if (custEnch.hasCustomEnchantmentType(tarArmorEnchs, "Molten Shell")) {
				EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(tarArmorEnchs, "Molten Shell");
				int rank = esoi.getRank();
				if (Tools.randomChance(rank * MOLTEN_SHELL_CHANCE_PR)) {
					lentDam.setFireTicks((int) (MOLTEN_SHELL_DUR * 20));
				}
			}
			if (custEnch.hasCustomEnchantmentType(tarArmorEnchs, "Poison Shell")) {
				EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(tarArmorEnchs, "Poison Shell");
				int rank = esoi.getRank();
				if (Tools.randomChance(rank * POISON_SHELL_CHANCE_PR)) {
					lentDam.addPotionEffect(
							new PotionEffect(PotionEffectType.POISON, (int) (POISON_SHELL_DUR * 20), POISON_SHELL_STR));
				}
			}
			if (custEnch.hasCustomEnchantmentType(tarArmorEnchs, "Wither Shell")) {
				EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(tarArmorEnchs, "Wither Shell");
				int rank = esoi.getRank();
				if (Tools.randomChance(rank * WITHER_SHELL_CHANCE_PR)) {
					lentDam.addPotionEffect(
							new PotionEffect(PotionEffectType.WITHER, (int) (WITHER_SHELL_DUR * 20), WITHER_SHELL_STR));
				}
			}
			if (custEnch.hasCustomEnchantmentType(tarArmorEnchs, "Poverty Barrier")) {
				EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(tarArmorEnchs, "Poverty Barrier");
				int rank = esoi.getRank();
				if (Tools.randomChance(rank * POVERTY_BARRIER_CHANCE_PR)) {
					lentDam.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, (int) (POVERTY_BARRIER_DUR * 20),
							POVERTY_BARRIER_STR));
				}
			}
			if (custEnch.hasCustomEnchantmentType(tarArmorEnchs, "Confusing Barrier")) {
				EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(tarArmorEnchs, "Confusing Barrier");
				int rank = esoi.getRank();
				if (Tools.randomChance(rank * CONFUSING_BARRIER_CHANCE_PR)) {
					lentDam.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,
							(int) (CONFUSING_BARRIER_DUR * 20), CONFUSING_BARRIER_STR));
				}
			}
			if (custEnch.hasCustomEnchantmentType(tarArmorEnchs, "Blinding Barrier")) {
				EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(tarArmorEnchs, "Blinding Barrier");
				int rank = esoi.getRank();
				if (Tools.randomChance(rank * BLINDING_BARRIER_CHANCE_PR)) {
					lentDam.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,
							(int) (BLINDING_BARRIER_DUR * 20), BLINDING_BARRIER_STR));
				}
			}
			if (custEnch.hasCustomEnchantmentType(tarArmorEnchs, "Regeneration Aura")) {
				EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(tarArmorEnchs, "Regeneration Aura");
				int rank = esoi.getRank();
				if (Tools.randomChance(rank * REGEN_AURA_CHANCE_PR)) {
					lentTar.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, (int) (REGEN_AURA_DUR * 20),
							REGEN_AURA_STR));
				}
			}
			if (custEnch.hasCustomEnchantmentType(tarArmorEnchs, "Health Aura")) {
				EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(tarArmorEnchs, "Health Aura");
				int rank = esoi.getRank();
				if (Tools.randomChance(rank * HEALTH_AURA_CHANCE_PR)) {
					lentTar.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, (int) (HEALTH_AURA_DUR * 20),
							HEALTH_AURA_STR));
				}
			}
			if (custEnch.hasCustomEnchantmentType(tarArmorEnchs, "Berserk Aura")) {
				EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(tarArmorEnchs, "Berserk Aura");
				int rank = esoi.getRank();
				if (Tools.randomChance(rank * BERSERK_AURA_CHANCE_PR)) {
					lentTar.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,
							(int) (BERSERK_AURA_DUR * 20), BERSERK_AURA_STR));
				}
			}
		}

	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onFished(PlayerFishEvent event) {
		if (event.isCancelled()) {
			return;
		}

		State eventState = event.getState();
		Entity ent = event.getCaught();
		Player player = event.getPlayer();

		@SuppressWarnings("deprecation")
		ItemStack inhand = player.getInventory().getItemInHand();
		List<EnchantmentSetOnItem> inhandEnchs = custEnch.getCustomEnchantments(inhand);

		if (eventState == State.CAUGHT_FISH) {
			if (ent instanceof Item) {
				Item item = (Item) ent;
				ItemStack istack = item.getItemStack();
				if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Molten Hook")) {
					istack = Tools.cookItem(istack);
				} else if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Silk Hook")) {
					istack = Tools.silkTouchItem(istack);
				}
			}
		} else if (eventState == State.CAUGHT_ENTITY && ent instanceof LivingEntity) {
			LivingEntity lentTar = (LivingEntity) ent;
			if (!MonstersPlus.isPVPLocation(lentTar.getLocation())) {
				return;
			}

			if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Fire Hook")) {
				EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Fire Hook");
				int rank = esoi.getRank();
				lentTar.setFireTicks((int) (FIRE_HOOK_PR * rank * 20));
			}
			if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Sharp Hook")) {
				EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Sharp Hook");
				int rank = esoi.getRank();
				double damage = SHARP_HOOK_PR * rank;
				lentTar.damage(damage);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled()) {
			return;
		}

		Block block = event.getBlock();
		Location loc = block.getLocation();
		World world = block.getWorld();
		Player player = event.getPlayer();

		@SuppressWarnings("deprecation")
		ItemStack inhand = player.getInventory().getItemInHand();
		List<EnchantmentSetOnItem> inhandEnchs = custEnch.getCustomEnchantments(inhand);
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Molten Touch")) {
			if (!MonstersPlus.isBuildLocation(player, loc)) {
				return;
			}
			if (block.getType() == Material.IRON_ORE) {
				Tools.removeNearbyEntityDelay(loc, Material.IRON_ORE, 1.3, 1L);
				world.dropItemNaturally(loc, new ItemStack(Material.IRON_INGOT, 1));
			} else if (block.getType() == Material.GOLD_ORE) {
				Tools.removeNearbyEntityDelay(loc, Material.GOLD_ORE, 1.3, 1L);
				world.dropItemNaturally(loc, new ItemStack(Material.GOLD_INGOT, 1));
			} else if (block.getType() == Material.LOG) {
				Tools.removeNearbyEntityDelay(loc, Material.LOG, 1.3, 1L);
				world.dropItemNaturally(loc, new ItemStack(Material.COAL, 1, (short) 1));
			} else if (block.getType() == Material.COBBLESTONE) {
				Tools.removeNearbyEntityDelay(loc, Material.COBBLESTONE, 1.3, 1L);
				world.dropItemNaturally(loc, new ItemStack(Material.STONE, 1));
			} else if (block.getType() == Material.MOSSY_COBBLESTONE) {
				Tools.removeNearbyEntityDelay(loc, Material.MOSSY_COBBLESTONE, 1.3, 1L);
				world.dropItemNaturally(loc, new ItemStack(Material.SMOOTH_BRICK, 1, (short) 1));
			} else if (block.getType() == Material.SAND) {
				Tools.removeNearbyEntityDelay(loc, Material.SAND, 1.3, 1L);
				world.dropItemNaturally(loc, new ItemStack(Material.GLASS));
			} else if (block.getType() == Material.CLAY) {
				Tools.removeNearbyEntityDelay(loc, Material.CLAY_BALL, 1.3, 1L);
				world.dropItemNaturally(loc, new ItemStack(Material.HARD_CLAY));
			} else if (block.getType() == Material.CACTUS) {
				Tools.removeNearbyEntityDelay(loc, Material.CACTUS, 1.3, 1L);
				world.dropItemNaturally(loc, new ItemStack(Material.INK_SACK, 1, (short) 2));
			}
		}
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Lucky Break")) {
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Lucky Break");
			int rank = esoi.getRank();
			if (event.getExpToDrop() > 0) {
				event.setExpToDrop(event.getExpToDrop() + (LUCKY_BREAK_PR * rank));
			}
		}
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Smash")) {
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Smash");
			final int rank = esoi.getRank();
			if ((block.getType() == Material.STONE || block.getType() == Material.GRAVEL)
					&& Tools.randomChance(SMASH_PR * rank)) {
				for (int x = SMASH_RADIUS * -1; x <= SMASH_RADIUS; x++) {
					for (int y = SMASH_RADIUS * -1; y <= SMASH_RADIUS; y++) {
						for (int z = SMASH_RADIUS * -1; z <= SMASH_RADIUS; z++) {
							Block tblock = world.getBlockAt(block.getLocation().add(x, y, z));
							if (Arrays.asList(SMASH_MATS).contains(tblock.getType())) {
								if (MonstersPlus.isBuildLocation(player, tblock.getLocation())
										&& !(x == 0 && y == 0 && z == 0)) {
									if (tblock.getType() == Material.STONE) {
										if (inhand.getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
											world.dropItemNaturally(tblock.getLocation(),
													new ItemStack(Material.STONE));
										} else {
											world.dropItemNaturally(tblock.getLocation(),
													new ItemStack(Material.COBBLESTONE));
										}
										tblock.setType(Material.AIR);
									} else if (tblock.getType() == Material.GRAVEL) {
										tblock.setType(Material.AIR);
										if (Tools.randomChance(25)) {
											world.dropItemNaturally(tblock.getLocation(),
													new ItemStack(Material.FLINT));
										} else {
											world.dropItemNaturally(tblock.getLocation(),
													new ItemStack(Material.GRAVEL));
										}
									} else {
										world.dropItemNaturally(tblock.getLocation(), new ItemStack(tblock.getType()));
										tblock.setType(Material.AIR);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBucketFill(PlayerBucketFillEvent event) {
		if (event.isCancelled()) {
			return;
		}

		Player player = event.getPlayer();
		@SuppressWarnings("deprecation")
		ItemStack inhand = player.getInventory().getItemInHand();
		List<EnchantmentSetOnItem> inhandEnchs = custEnch.getCustomEnchantments(inhand);
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Blackhole")) {
			event.setItemStack(inhand);
			event.setCancelled(true);
			@SuppressWarnings("deprecation")
			Block block = player.getTargetBlock(new HashSet<Byte>(), 5);
			if (block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER
					|| block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA) {
				block.setType(Material.AIR);
			}
			final ItemStack fstack = inhand;
			final Player fplayer = player;
			MonstersPlus.plugin.getServer().getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new Runnable() {
				@Override
				@SuppressWarnings("deprecation")
				public void run() {
					fplayer.setItemInHand(fstack);
				}
			}, 1);
			player.updateInventory();
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBucketEmpty(PlayerBucketEmptyEvent event) {
		if (event.isCancelled()) {
			return;
		}

		Player player = event.getPlayer();
		@SuppressWarnings("deprecation")
		ItemStack inhand = player.getInventory().getItemInHand();
		List<EnchantmentSetOnItem> inhandEnchs = custEnch.getCustomEnchantments(inhand);
		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Flow")) {
			event.setItemStack(inhand);
		}
		player.updateInventory();
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onSheer(PlayerShearEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}

		Player player = event.getPlayer();
		Entity ent = event.getEntity();
		if (!(ent instanceof Sheep)) {
			return;
		}

		@SuppressWarnings("deprecation")
		ItemStack inhand = player.getInventory().getItemInHand();
		List<EnchantmentSetOnItem> inhandEnchs = custEnch.getCustomEnchantments(inhand);
		Sheep sheep = (Sheep) ent;
		Location loc = ent.getLocation();

		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Sheer Luck")) {
			EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Sheer Luck");
			int rank = esoi.getRank();
			Wool wool = new Wool();
			wool.setColor(sheep.getColor());
			ItemStack item = wool.toItemStack();
			item.setAmount(2);
			for (int i = 0; i < rank * SHEER_LUCK_PR; i++) {
				if (Tools.randomChance(SHEER_LUCK_CHANCE)) {
					loc.getWorld().dropItemNaturally(loc, item);
				}
			}
		}

		if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Dazzle")) {
			Random rand = new Random();
			DyeColor[] vals = DyeColor.values();
			final Sheep fsheep = sheep;
			final DyeColor originalColor = sheep.getColor();
			sheep.setColor(vals[rand.nextInt(vals.length)]);

			Bukkit.getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new Runnable() {
				@Override
				public void run() {
					fsheep.setColor(originalColor);
				}
			}, 1);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Player player = event.getPlayer();
		if (event.hasBlock()) {
			Block block = event.getClickedBlock();
			World world = block.getWorld();
			ItemStack inhand = player.getItemInHand();
			List<EnchantmentSetOnItem> inhandEnchs = custEnch.getCustomEnchantments(inhand);
			if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Tilling")
					&& event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Tilling");
				int radius = esoi.getRank() * TILLING_PR;
				for (int x = radius * -1; x < radius; x++) {
					for (int z = radius * -1; z < radius; z++) {
						Block tblock = world.getBlockAt(block.getLocation().add(x, 0, z));
						if (tblock.getType() == Material.DIRT || tblock.getType() == Material.GRASS) {
							if (tblock.getType() != Material.SOIL
									&& MonstersPlus.isBuildLocation(player, tblock.getLocation())
									&& !(x == 0 && z == 0)) {
								tblock.setType(Material.SOIL);
								if (Tools.randomChance(TILLING_DURAB_PERCENTAGE)) {
									inhand.setDurability((short) (inhand.getDurability() + 1));
								}
							}
						}
					}
				}
			}
			if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Seed Finder")
					&& event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Seed Finder");
				int perRank = esoi.getRank() * SEEDFINDER_PR;
				if (block.getType() == Material.DIRT || block.getType() == Material.GRASS) {
					if (MonstersPlus.isBuildLocation(player, block.getLocation())) {
						for (int i = 0; i < perRank; i++) {
							if (Tools.randomChance(SEEDFINDER_PERCENTAGE)) {
								Tools.dropRandomSeed(block.getLocation().add(0, 1, 0));
							}
						}
					}
				}

			}
			if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Hydration")
					&& event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				EnchantmentSetOnItem esoi = custEnch.getCustomEnchantmentType(inhandEnchs, "Hydration");
				int radius = esoi.getRank() * HYDRATION_PR;
				for (int x = radius * -1; x < radius; x++) {
					for (int z = radius * -1; z < radius; z++) {
						Block tblock = world.getBlockAt(block.getLocation().add(x, 0, z));
						if (tblock.getType() == Material.SOIL
								&& MonstersPlus.isBuildLocation(player, tblock.getLocation())) {
							tblock.setData((byte) 7);
						}
					}
				}
			}
			if (custEnch.hasCustomEnchantmentType(inhandEnchs, "Papercut")
					&& event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (block.getType() == Material.WEB && MonstersPlus.isBuildLocation(player, block.getLocation())) {
					block.setType(Material.AIR);
					world.dropItemNaturally(block.getLocation(), new ItemStack(Material.WEB, 1));
					inhand.setDurability((short) (inhand.getDurability() + PAPERCUT_DURAB_AMOUNT));
				} else if (block.getType() == Material.DEAD_BUSH
						&& MonstersPlus.isBuildLocation(player, block.getLocation())) {
					block.setType(Material.AIR);
					world.dropItemNaturally(block.getLocation(), new ItemStack(Material.DEAD_BUSH, 1));
					inhand.setDurability((short) (inhand.getDurability() + PAPERCUT_DURAB_AMOUNT));
				}
			}
		}

	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onItemClick(InventoryClickEvent event) {
		/**
		 * Due to the bug caused by enderchests, in which custom enchantments
		 * were being renamed with a weird purple A, we will use this method to
		 * check and clean up any bugged enchantment names.
		 */
		if (event.isCancelled()) {
			return;
		}
		if (!event.isLeftClick()) {
			return;
		}

		ItemStack item = event.getCurrentItem();
		if (item == null) {
			return;
		}

		if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();

			for (int i = 0; i < lore.size(); i++) {
				String s = lore.get(i);
				lore.remove(s);
				s = fixJunkCharacters(s);
				lore.add(s);
			}
			meta.setLore(lore);

			if (meta.hasDisplayName()) {
				String name = meta.getDisplayName();
				name = fixJunkCharacters(name);
				meta.setDisplayName(name);
			}

			item.setItemMeta(meta);
		}
	}

	public String fixJunkCharacters(String s) {
		String temp = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = s.charAt(i);
			if (ch < 124 || ch == 167) {
				temp += s.charAt(i);
			}
		}
		return temp;
	}

}
