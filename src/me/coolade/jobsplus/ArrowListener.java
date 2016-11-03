package me.coolade.jobsplus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import me.coolade.jobsplus.customitems.CustomItems;
import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.Pair;
import me.coolade.monstersplus.Tools;
import me.coolade.monstersplus.monsters.MonsterUpdater;
import me.coolade.monstersplus.tasks.PlaceAndCleanTask;

public class ArrowListener implements Listener {
	// Id, ArrowName,
	public static final ConcurrentHashMap<Integer, Pair<String, String>> projInstances = new ConcurrentHashMap<Integer, Pair<String, String>>();
	// Playername, Position in inventory // PlayerName
	public static final ConcurrentHashMap<String, Integer> arrowCycleInstances = new ConcurrentHashMap<String, Integer>();
	private static final double REMOVE_CHANCE = 50;

	private static final int REPULSE_RADIUS = 9;
	private static final double REPULSE_STRENGTH = 1.5;
	private static final double REPULSE_DEPR = 0.2; // Causes str depreciation
													// for more distance

	private static final int VORTEX_RADIUS = 9;
	private static final double VORTEX_STRENGTH = 0.3;

	private static final int POISON_STRENGTH = 1;
	private static final int POISON_MAX_DURATION = 9;

	private static final double RAZOR_ARROW_MULTIPLIER = 0.25;
	private static final double RAZOR_HEART_ADDITION = 0.0;
	private static final double PIERCING_ARROW_MULTIPLIER = 0.25;
	private static final double PIERCING_HEART_ADDITION = 0.0;

	private static final float EXPLOSIVE_ARROW_EXPLOSION = 2.6F;

	private static final float FIREBALL_ARROW_EXPLOSION = 2.4F;

	private static final long COMET_DELAY = 10;
	private static final int COMET_AMOUNT = 4;
	private static final int COMET_Y_BOOST = 8;
	private static final int COMET_DOWN_SPEED = -4;

	private static final int SKULLWITHER_MAX_DURATION = 7;
	private static final int SKULLWITHER_STRENGTH = 1;
	private static final double SKULLWITHER_DAMAGE = 4;

	private static final double SHREADING_PERCENTAGE = 3;

	private static final double BEASTARROW_RATIO = 3;

	@SuppressWarnings("unused")
	private static final int GRAPPLE_CLEANUP_SECONDS = 5;

	private static final int NETARROW_RADIUS = 2;
	private static final double NETARROW_CLEANUP_SECONDS = 1.5;

	private static final double BLIZZARD_RADIUS = 8;
	private static final double BLIZZARD_DURATION_MOBS = 3;
	private static final double BLIZZARD_DURATION_PLAYER = 1.5;
	private static final int BLIZZARD_STRENGTH_MOBS = 5;
	private static final int BLIZZARD_STRENGTH_PLAYER = 5;

	@EventHandler(priority = EventPriority.NORMAL)
	public void onArrowShot(EntityShootBowEvent event) {
		/**
		 * This method will determine if the player shot a special arrow, If the
		 * arrow was special than it will be under the item's metadata under the
		 * category of "customArrow"
		 */
		Entity ent = event.getEntity();
		Entity proj = event.getProjectile();
		if (ent instanceof Player) {
			Player player = (Player) ent;
			ItemStack arrowType = Tools.getFirstItem(player, new ItemStack(Material.ARROW));
			arrowType = arrowCycleCheck(player, arrowType);

			if (arrowType != null && proj instanceof Projectile) {
				if (!CustomItems.isCustomItem(arrowType)) {
					return;
				}
				Projectile arrow = (Projectile) proj;
				infinityBowCheck(player);
				Projectile projPointer = arrow;
				if (CustomItems.isCustomItem("Fireball Arrow", arrowType)) {
					if (!MonstersPlus.isPVPLocation(arrow.getLocation())
							&& !MonstersPlus.isBuildLocation(player, arrow.getLocation())) {
						return;
					}
					arrow.remove();
					LargeFireball fball = player.launchProjectile(LargeFireball.class);
					fball.setShooter(player);
					projPointer = fball;
				} else if (CustomItems.isCustomItem("Comet Arrow", arrowType)) {
					if (!MonstersPlus.isPVPLocation(arrow.getLocation())
							&& !MonstersPlus.isBuildLocation(player, arrow.getLocation())) {
						return;
					}
					arrow.remove();
					SmallFireball fball = player.launchProjectile(SmallFireball.class);
					fball.setShooter(player);
					projPointer = fball;
				} else if (CustomItems.isCustomItem("Skull Arrow", arrowType)) {
					if (!MonstersPlus.isPVPLocation(arrow.getLocation())
							&& !MonstersPlus.isBuildLocation(player, arrow.getLocation())) {
						return;
					}
					arrow.remove();
					WitherSkull fball = player.launchProjectile(WitherSkull.class);
					fball.setShooter(player);
					projPointer = fball;
				}
				projInstances.put(projPointer.getEntityId(), new Pair<String, String>(
						ChatColor.stripColor(arrowType.getItemMeta().getDisplayName()), player.getName()));
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onArrowHit(ProjectileHitEvent event) {
		/**
		 * This method will do all the functionality for the customized arrows.
		 */
		Entity ent = event.getEntity();
		if (ent instanceof Projectile) {
			Projectile arrow = (Projectile) ent;
			if (!projInstances.containsKey(arrow.getEntityId())) {
				return;
			}
			Pair<String, String> pair = projInstances.get(arrow.getEntityId());
			projInstances.remove(arrow.getEntityId());
			String customType = pair.getFirst();
			String shooterName = pair.getSecond();
			Location loc = arrow.getLocation();
			World world = loc.getWorld();

			if (MonstersPlus.isPVPLocation(arrow.getLocation())) {
				Player shooter = Bukkit.getPlayer(shooterName);

				if (customType.equals("Repulse Arrow")) {
					repulseArea(shooter, arrow);
					if (MonstersPlus.randomChance(REMOVE_CHANCE)) {
						arrow.remove();
					}
				} else if (customType.equals("Vortex Arrow")) {
					vortexArea(shooter, arrow);
					if (MonstersPlus.randomChance(REMOVE_CHANCE)) {
						arrow.remove();
					}
				} else if (customType.equals("Torch Arrow") && MonstersPlus.isBuildLocation(shooter, loc)) {
					torchArea(shooter, arrow);
				} else if (customType.equals("Explosive Arrow")) {
					arrow.remove();
					world.createExplosion(loc.getX(), loc.getY(), loc.getZ(), EXPLOSIVE_ARROW_EXPLOSION, false, false);
				} else if (customType.equals("Fireball Arrow")) {
					arrow.remove();
					world.createExplosion(loc.getX(), loc.getY(), loc.getZ(), FIREBALL_ARROW_EXPLOSION, false, false);
				} else if (customType.equals("Comet Arrow")) {
					arrow.remove();
					cometArea(shooter, loc);
				} else if (customType.equals("Grapple Arrow")) {
					if (!MonstersPlus.isBuildLocation(shooter, arrow.getLocation())
							|| (!MonstersPlus.isEnderpearlAllowed(arrow.getLocation()) && !shooter.isOp())) {
						return;
					}

					arrow.remove();
					BlockIterator iterator = new BlockIterator(world, arrow.getLocation().toVector(),
							arrow.getVelocity().normalize(), 0, 4);
					Block block = null;
					Block prevBlock = null;

					/*
					 * Cycle through all of the arrows in a Line until we find
					 * the one that is not Air. Keep track of the previous block
					 * because we want the block right before the one that is
					 * air.
					 */
					while (iterator.hasNext()) {
						prevBlock = block;
						block = iterator.next();
						if (block.getType() != Material.AIR) {
							// Go forward 1 additional time so that we make sure
							// we are into the wall.
							if (iterator.hasNext()) {
								block = iterator.next();
							}
							break;
						}
					}
					// Prevent NPE
					if (prevBlock == null) {
						prevBlock = block;
					}

					shooter.teleport(block.getLocation());
					if (prevBlock.getType() == Material.AIR
							&& MonstersPlus.isBuildLocation(shooter, prevBlock.getLocation())) {
						// new PlaceAndCleanTask(Material.LADDER,
						// prevBlock.getLocation(), 0, GRAPPLE_CLEANUP_SECONDS *
						// 20);
					}
				} else if (customType.equals("Net Arrow")) {
					arrow.remove();
					Block block = world.getBlockAt(loc);

					for (int i = NETARROW_RADIUS * -1 + 1; i <= NETARROW_RADIUS; i++) {
						for (int j = NETARROW_RADIUS * -1 + 1; j <= NETARROW_RADIUS; j++) {
							for (int k = NETARROW_RADIUS * -1 + 1; k <= NETARROW_RADIUS; k++) {
								Block tempBlock = world.getBlockAt(block.getLocation().add(i, j, k));

								/*
								 * Make sure that the block is AIR and
								 * Buildable, but also make sure that the block
								 * underneath is NOT AIR so that we know we are
								 * placing the net on the ground
								 */
								if (tempBlock.getType() == Material.AIR
										&& MonstersPlus.isBuildLocation(shooter, tempBlock.getLocation())
										&& world.getBlockAt(tempBlock.getLocation().add(0, -1, 0))
												.getType() != Material.AIR) {
									new PlaceAndCleanTask(Material.WEB, tempBlock.getLocation(), 0,
											(int) (NETARROW_CLEANUP_SECONDS * 20));
								}
							}
						}
					}
				} else if (customType.equals("Blizzard Arrow")) {
					blizzardArea(shooter, arrow);
					arrow.remove();
				}

			}
		}
	}

	@SuppressWarnings("unused")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onArrowDamage(EntityDamageByEntityEvent event) {
		/**
		 * This method will do all the functionality for the customized arrows.
		 */
		if (!(event.getEntity() instanceof LivingEntity)) {
			return;
		}
		LivingEntity lent = (LivingEntity) event.getEntity();
		if (!(event.getDamager() instanceof Projectile)) {
			return;
		}
		Projectile proj = (Projectile) event.getDamager();
		if (!(proj.getShooter() instanceof Player)) {
			return;
		}

		Player shooter = (Player) proj.getShooter();
		Projectile arrow = proj;

		if (!projInstances.containsKey(arrow.getEntityId())) {
			return;
		}
		Pair<String, String> pair = projInstances.get(arrow.getEntityId());
		projInstances.remove(arrow.getEntityId());
		String customType = pair.getFirst();
		String shooterName = pair.getSecond();
		Location loc = arrow.getLocation();
		if (MonstersPlus.isPVPLocation(arrow.getLocation())) {
			if (customType.equals("Shuffle Arrow")) {
				if (MonstersPlus.isEnderpearlAllowed(arrow.getLocation()) || shooter.isOp()) {
					shuffleEntities(shooter, lent);
				}
			} else if (customType.equals("Poison Arrow")) {
				Random randGen = new Random();
				int duration = randGen.nextInt(POISON_MAX_DURATION - 2) + 2;
				lent.addPotionEffect(new PotionEffect(PotionEffectType.POISON, duration * 20, POISON_STRENGTH));
			} else if (customType.equals("Skull Arrow")) {
				Random randGen = new Random();
				int duration = randGen.nextInt(SKULLWITHER_MAX_DURATION - 2) + 2;
				if (MonstersPlus.isPVPLocation(arrow.getLocation())) {
					lent.addPotionEffect(
							new PotionEffect(PotionEffectType.WITHER, duration * 20, SKULLWITHER_STRENGTH));
					lent.damage(SKULLWITHER_DAMAGE);
				}
			} else if (customType.equals("Razor Arrow")) {
				EntityEquipment ee = lent.getEquipment();
				int emptySlots = 0;
				// Notice this is checking if the armor slot is empty
				if (ee.getBoots() == null) {
					emptySlots++;
				}
				if (ee.getLeggings() == null) {
					emptySlots++;
				}
				if (ee.getHelmet() == null) {
					emptySlots++;
				}
				if (ee.getChestplate() == null) {
					emptySlots++;
				}

				double totalDamage = event.getDamage();
				totalDamage += (event.getDamage() * emptySlots * RAZOR_ARROW_MULTIPLIER);
				totalDamage += (emptySlots * RAZOR_HEART_ADDITION);
				event.setDamage(totalDamage);
			} else if (customType.equals("Piercing Arrow")) {
				EntityEquipment ee = lent.getEquipment();
				int totalEquips = 0;

				if (ee.getBoots() != null) {
					totalEquips++;
				}
				if (ee.getLeggings() != null) {
					totalEquips++;
				}
				if (ee.getHelmet() != null) {
					totalEquips++;
				}
				if (ee.getChestplate() != null) {
					totalEquips++;
				}

				double totalDamage = event.getDamage();
				totalDamage += (event.getDamage() * totalEquips * PIERCING_ARROW_MULTIPLIER);
				totalDamage += (totalEquips * PIERCING_HEART_ADDITION);
				event.setDamage(totalDamage);
			} else if (customType.equals("Shreading Arrow")) {
				double dmg = lent.getHealth() * (SHREADING_PERCENTAGE / 100);
				double newHealth = lent.getHealth() - dmg;
				if (newHealth < 0) {
					newHealth = 0;
				}
				lent.setHealth(newHealth);
			} else if (customType.equals("Beast Arrow")) {
				if (!(lent instanceof Player)) {
					event.setDamage(event.getDamage() * BEASTARROW_RATIO);
				}
			} else if (customType.equals("Lasso Arrow")) {
				if (lent.getLocation().getWorld().equals(shooter.getLocation().getWorld())) {
					if (lent instanceof EnderDragon) {
						return;
					}

					lent.teleport(shooter.getLocation());
				}
			}
		}
	}

	public void repulseArea(Player shooter, Projectile arrow) {
		List<Entity> entList = arrow.getNearbyEntities(REPULSE_RADIUS, REPULSE_RADIUS, REPULSE_RADIUS);
		for (Entity entity : entList) {
			if (entity instanceof LivingEntity) {
				// Make sure that we arent changing the shooters speed
				if (!(entity instanceof Player) || ((Player) entity).getName() != shooter.getName()) {
					Vector arrowVec = arrow.getLocation().toVector();
					Vector tarVec = entity.getLocation().toVector();
					Vector distVec = tarVec.subtract(arrowVec);
					Vector normVec = distVec.clone().normalize();
					Vector finalVec = (normVec.multiply(REPULSE_STRENGTH))
							.multiply((1 / (distVec.length() * REPULSE_DEPR)));
					// finalVec.setY(0.3);

					entity.setVelocity(finalVec);
				}
			}
		}
	}

	public void blizzardArea(Player shooter, Projectile arrow) {
		List<Entity> entList = arrow.getNearbyEntities(BLIZZARD_RADIUS, BLIZZARD_RADIUS, BLIZZARD_RADIUS);
		for (Entity entity : entList) {
			if (entity instanceof LivingEntity) {
				// Make sure that we arent changing the shooters speed
				if (!(entity instanceof Player) || ((Player) entity).getName() != shooter.getName()) {
					LivingEntity lent = (LivingEntity) entity;
					MonsterUpdater.createIceRing(lent, BLIZZARD_DURATION_MOBS, false);

					if (lent instanceof Player) {
						lent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
								(int) (BLIZZARD_DURATION_PLAYER * 20), BLIZZARD_STRENGTH_PLAYER, true));
					} else {
						lent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
								(int) (BLIZZARD_DURATION_MOBS * 20), BLIZZARD_STRENGTH_MOBS, true));
					}
				}
			}
		}
	}

	public void vortexArea(Player shooter, Projectile arrow) {
		List<Entity> entList = arrow.getNearbyEntities(VORTEX_RADIUS, VORTEX_RADIUS, VORTEX_RADIUS);
		for (Entity entity : entList) {
			if (entity instanceof LivingEntity) {
				// Make sure that we arent changing the shooters speed
				if (!(entity instanceof Player) || ((Player) entity).getName() != shooter.getName()) {
					Vector arrowVec = arrow.getLocation().toVector();
					Vector tarVec = entity.getLocation().toVector();
					Vector distVec = arrowVec.subtract(tarVec);
					Vector finalVec = (distVec.multiply(VORTEX_STRENGTH));
					entity.setVelocity(finalVec);
				}
			}
		}
	}

	public void torchArea(Player player, Projectile arrow) {
		if (MonstersPlus.isBuildLocation(player, arrow.getLocation())) {
			World world = arrow.getWorld();
			BlockIterator iterator = new BlockIterator(world, arrow.getLocation().toVector(),
					arrow.getVelocity().normalize(), 0, 4);
			Block hitBlock = null;
			while (iterator.hasNext()) {
				hitBlock = iterator.next();
				if (hitBlock.getType() != Material.AIR) {
					break;
				}
			}
			// Now that we have the correct block, we need to find out which
			// face it was
			if (hitBlock != null) {
				Location newLoc = arrow.getLocation().getBlock().getLocation().subtract(hitBlock.getLocation());

				// Make sure we don't have values that are too far away to be a
				// face
				double[] vals = { newLoc.getX(), newLoc.getY(), newLoc.getZ() };
				int totalTries = 7; // If the randomization does not get it
									// correct in 3 tries than it cancels.
				while (totalTries > 0) {
					double largestVal = 0;
					for (int i = 0; i < vals.length; i++) {
						double temp = Math.abs(vals[i]);
						if (temp > largestVal) {
							largestVal = temp;
						}
					}

					// Get rid of the values that are smaller than the
					// largestval
					for (int i = 0; i < vals.length; i++) {
						// Use this math.abs so that we retain the negative sign
						// if necessary
						if (Math.abs(vals[i]) >= 1) {
							vals[i] /= Math.abs(vals[i]);
						}
					}

					// If there are still multiple values of 1 or -1, we need to
					// get it so that there is only one
					int totalOnesFound = 0;
					for (int i = 0; i < vals.length; i++) {
						if ((int) Math.abs(vals[i]) == 1) {
							totalOnesFound++;
						}
					}
					// randomly set one of the ones to 0
					while (totalOnesFound > 1) {
						Random random = new Random();
						int rand = random.nextInt(vals.length);
						if (Math.abs(vals[rand]) == 1) {
							vals[rand] = 0;
							totalOnesFound--;
						}
					}
					Block relBlock = hitBlock.getRelative((int) vals[0], (int) vals[1], (int) vals[2]);
					if (relBlock.getType() == Material.AIR || relBlock.getType() == Material.LONG_GRASS) {
						relBlock.setType(Material.TORCH);
						arrow.remove();
						return;
					} else {
						totalTries--;
					}
				}
			}
		}
	}

	public void shuffleEntities(LivingEntity lent1, LivingEntity lent2) {
		if (lent1.getLocation().getWorld() == lent2.getLocation().getWorld()) {
			if (lent1 instanceof EnderDragon || lent2 instanceof EnderDragon) {
				return;
			}

			Location temp = lent1.getLocation();
			lent1.teleport(lent2.getLocation());
			lent2.teleport(temp);
		}
	}

	public void cometArea(final Player player, final Location loc) {
		for (int i = 0; i < COMET_AMOUNT; i++) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new Runnable() {
				@Override
				public void run() {
					Location tempLoc = loc.add(0, COMET_Y_BOOST, 0);
					LargeFireball fball = loc.getWorld().spawn(tempLoc, LargeFireball.class);
					fball.setVelocity(new Vector(0, COMET_DOWN_SPEED, 0));
					projInstances.put(fball.getEntityId(),
							new Pair<String, String>("Fireball Arrow", player.getName()));
				}
			}, COMET_DELAY * (i + 1));
		}

	}

	public void infinityBowCheck(Player player) {
		/*
		 * This method checks to see if the user's bow had the infinity
		 * enchantment on it. If the bow has infinity, and the arrow was custom,
		 * we need to remove that arrow.
		 */
		if (player == null) {
			return;
		}
		ItemStack arrow = Tools.getFirstItem(player, new ItemStack(Material.ARROW));
		int slotPos = Tools.getFirstItemSlot(player, new ItemStack(Material.ARROW));
		// If the item is actually a custom arrow itemstack, then we need to
		// check if the bow has infinity
		if (CustomItems.isCustomItem(arrow)) {
			@SuppressWarnings("deprecation")
			ItemStack bow = player.getItemInHand();
			if (bow != null) {
				if (bow.getEnchantments().containsKey(Enchantment.ARROW_INFINITE)) {
					if (arrow.getAmount() == 1) {
						player.getInventory().setItem(slotPos, new ItemStack(Material.AIR));
					} else {
						arrow.setAmount(arrow.getAmount() - 1);
					}
				}
			}
		}
	}

	@EventHandler
	public void onBowLeftClick(PlayerInteractEvent event) {
		if (!event.getAction().equals(Action.LEFT_CLICK_AIR) && !event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			return;
		}
		if (event.getMaterial() != Material.BOW) {
			return;
		}
		Player player = event.getPlayer();
		List<Pair<ItemStack, Integer>> itemSlotList = locateItemsInInv(player, Material.ARROW);
		if (itemSlotList.size() < 2) {
			return;
		}
		int slotToAdd = -1;
		// If the player isn't in the list add him
		if (!arrowCycleInstances.containsKey(player.getName())) {
			slotToAdd = itemSlotList.get(0).getSecond();
		} else {
			// else we need to add the next type of arrow found
			int currentInvSlot = arrowCycleInstances.get(player.getName());
			arrowCycleInstances.remove(player.getName());
			for (int i = 0; i < itemSlotList.size(); i++) {
				if (itemSlotList.get(i).getSecond() > currentInvSlot) {
					slotToAdd = itemSlotList.get(i).getSecond();
					break;
				}
			}
			if (slotToAdd == -1) {
				slotToAdd = itemSlotList.get(0).getSecond();
			}
		}
		arrowCycleInstances.put(player.getName(), slotToAdd);
		ItemStack tempStack = player.getInventory().getItem(slotToAdd);
		if (!isRegularArrow(tempStack)) {
			player.sendMessage("Equipped " + tempStack.getItemMeta().getDisplayName());
		} else {
			player.sendMessage("Equipped " + "Arrow");
		}
	}

	public static List<Pair<ItemStack, Integer>> locateItemsInInv(Player player, Material mat) {
		// Returns a list with the itemstacks represented by mat, and an integer
		// representing the slot it was found in.
		List<Pair<ItemStack, Integer>> list = new ArrayList<Pair<ItemStack, Integer>>();
		PlayerInventory inv = player.getInventory();
		ItemStack[] contents = inv.getContents();
		for (int i = 0; i < contents.length; i++) {
			if (contents[i] != null && contents[i].getType() == mat) {
				list.add(new Pair<ItemStack, Integer>(contents[i], i));
			}
		}
		return list;
	}

	@SuppressWarnings("deprecation")
	private ItemStack arrowCycleCheck(Player player, ItemStack istack) {
		if (!arrowCycleInstances.containsKey(player.getName())) {
			return istack;
		}
		player.updateInventory();
		PlayerInventory inv = player.getInventory();
		int savedSlot = arrowCycleInstances.get(player.getName());
		int firstArrowSlot = inv.first(Material.ARROW);
		if (savedSlot == firstArrowSlot) {
			return istack;
		} else {
			ItemStack itemInSlot = inv.getItem(savedSlot);
			if (itemInSlot != null && itemInSlot.getType() == Material.ARROW) {
				// If the saved slot is a regular arrow with infinity bow
				if (player.getItemInHand().containsEnchantment(Enchantment.ARROW_INFINITE)) {
					int firstSlotAmount = istack.getAmount();
					if (!isRegularArrow(itemInSlot)) {
						if (!isRegularArrow(istack)) {
							firstSlotAmount++;
						}

						if (itemInSlot.getAmount() == 1) {
							inv.setItem(savedSlot, new ItemStack(Material.AIR));
						} else {
							itemInSlot.setAmount(itemInSlot.getAmount() - 1);
						}
					}
					istack.setAmount(firstSlotAmount);
				} else {
					istack.setAmount(istack.getAmount() + 1);
					if (itemInSlot.getAmount() == 1) {
						inv.setItem(savedSlot, new ItemStack(Material.AIR));
					} else {
						itemInSlot.setAmount(itemInSlot.getAmount() - 1);
					}
				}
				/*
				 * There is a wierd glitch where there is a custom arrow first,
				 * but a SINGLE regular arrow in the cycled slot, and the bow is
				 * not infinity then the regular arrow is ignored and the custom
				 * arrow is used instead.
				 */
				player.updateInventory();
				return itemInSlot;
			}
		}
		return istack;
	}

	public boolean isRegularArrow(ItemStack istack) {
		return istack.getType() == Material.ARROW && (!istack.hasItemMeta() || !istack.getItemMeta().hasDisplayName());
	}
}
