package me.coolade.jobsplus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftFallingSand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

import me.coolade.jobsplus.CustomRecipes.RecipeSet;
import me.coolade.jobsplus.customitems.CustomItems;
import me.coolade.monstersplus.Cooldown;
import me.coolade.monstersplus.ExperienceManager;
import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.Pair;
import me.coolade.monstersplus.Tools;
import me.coolade.monstersplus.monsters.MonsterSpawnCommand;
import me.coolade.monstersplus.tasks.DelayedEffect;
import me.coolade.monstersplus.tasks.DelayedExplosion;
import me.coolade.monstersplus.tasks.DelayedLightning;
import me.coolade.monstersplus.tasks.DelayedSpawn;
import me.coolade.monstersplus.tasks.PlaceBlockTask;
import me.coolade.monstersplus.tasks.RemoveEntityTask;
import me.libraryaddict.disguise.DisguiseAPI;

public class JobsListener implements Listener {
	Random randomGen = new Random();
	public static final double ENCH_EXP = 12.5; // 2.5
	public static final double REPAIR_EXP = 15.0; // 3.0
	public static final int ENDERSPAWNING_LEVEL = 7;
	public static final int ENDERSPAWNING_COOLDOWN = 3600000;
	public static final int BOMB_COOLDOWN = 10000;

	public static final Material[] CHALLENGE_ITEMS = new Material[] { Material.COAL, Material.IRON_INGOT,
			Material.GOLD_INGOT, Material.DIAMOND };
	public static final int[] CHALLENGE_LEVELS = new int[] { 5, 10, 15, 25 };
	public static final long[] CHALLENGE_COOLDOWNS = new long[] { 120000, 300000, 900000, 3600000 };

	private static ConcurrentHashMap<Integer, Pair<String, String>> eggMap = new ConcurrentHashMap<Integer, Pair<String, String>>();
	private static ArrayList<Integer> anvilList = new ArrayList<Integer>();

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCraft(CraftItemEvent event) {
		/**
		 * Custom Item Crafting protection If a player crafts a special item, we
		 * need to check their job level to make sure that it is within the
		 * correct limits.
		 */
		if (event.isCancelled()) {
			return;
		}

		ItemStack istack = event.getRecipe().getResult();
		if (!istack.hasItemMeta()) {
			return;
		}

		ItemMeta meta = istack.getItemMeta();
		if (!meta.hasDisplayName()) {
			return;
		}

		String itemName = ChatColor.stripColor(meta.getDisplayName());

		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			if (player.isOp()) {
				return;
			}

			// Check to make sure they have the required job and level for the
			// recipe
			if (CustomItems.isCustomItem(istack)) {
				RecipeSet recipe = CustomRecipes.getRecipe(itemName);
				if (recipe == null) {
					return;
				}

				if (!CustomRecipes.hasRecipe(player, itemName)) {
					player.sendMessage(ChatColor.BLUE + "Job: " + ChatColor.WHITE
							+ CustomRecipes.getRequiredJob(itemName) + ChatColor.BLUE + " Required Level: "
							+ ChatColor.WHITE + CustomRecipes.getRequiredLevel(itemName));
					event.setCancelled(true);
				}
				// Make sure they have enough powerstones
				else if (Tools.getAmountOfPowerstones(player) < recipe.getPowerstones()) {
					player.sendMessage(ChatColor.RED + "This enchantment costs " + ChatColor.GOLD
							+ recipe.getPowerstones() + ChatColor.RED + " powerstones, you only have " + ChatColor.GOLD
							+ Tools.getAmountOfPowerstones(player) + ChatColor.RED + ".");
					event.setCancelled(true);
				} else {
					Tools.removePowerstones(player, recipe.getPowerstones());
				}
			}
			player.updateInventory();
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onFished(PlayerFishEvent event) {
		/**
		 * Custom Fishing Manager This is the event that deals with fishing. If
		 * the player who caused the fishing event is inside of the Jobs
		 * "Fisherman" then it will generate an item based on the players job
		 * level.
		 */
		if (event.isCancelled()) {
			return;
		}

		State eventState = event.getState();
		Entity ent = event.getCaught();
		Player player = event.getPlayer();
		int fisherLevel = getJobLevel(player.getName(), "Fisherman");

		if (eventState == State.CAUGHT_FISH && fisherLevel > 0) {
			if (ent instanceof Item) {
				Item item = (Item) ent;
				ItemStack istack = item.getItemStack();

				// Once the player hooks the fish we need to calculate which
				// entity it needs to change into
				// Based on the level of the job
				FishList.generateFishedItem(istack, fisherLevel);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEnchant(EnchantItemEvent event) {
		/**
		 * Enchanting Exp Manager Gives the player a certain amount of exp when
		 * they enchant an item.
		 */
		if (event.isCancelled()) {
			return;
		}

		String playerName = event.getEnchanter().getName();
		if (getJobLevel(playerName, "Enchanter") > 0) {
			int expLevel = event.getExpLevelCost();
			addJobExp(playerName, "Enchanter", expLevel * ENCH_EXP);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onRepair(final InventoryClickEvent event) {
		/**
		 * Repairing Item Exp Manager Gives the player exp when they rename or
		 * repair an item.
		 */
		if (event.isCancelled()) {
			return;
		}

		Player player = (Player) event.getView().getPlayer();
		if (player.getGameMode() != GameMode.SURVIVAL) {
			return;
		}

		Inventory inv = event.getInventory();
		int slot = event.getSlot();

		if (inv instanceof AnvilInventory && slot == 2) {
			ItemStack inCursor = event.getCurrentItem();
			if (inCursor != null) {
				List<String> jobList = new ArrayList<String>();
				jobList.add("Enchanter");
				jobList.add("Blacksmith");

				ExpPayTimer task = new ExpPayTimer(player, player.getLevel(), jobList, REPAIR_EXP);
				task.runTask(MonstersPlus.plugin);
			}
		}
	}

	public class ExpPayTimer extends BukkitRunnable {
		/**
		 * ExpPayTimer for managing the Anvil Exp differences This task is used
		 * to to delay a check between when the user clicks to repair an item,
		 * and the next tick afterword. We can then compare the exp between the
		 * two to find out how many levels was taken. Then it loops through the
		 * jobs and gives them the exp that they earned.
		 */
		Player player;
		int prevLevel;
		List<String> jobs;
		double multiplier;

		public ExpPayTimer(Player player, int prevLevel, List<String> jobs, double mult) {
			this.player = player;
			this.prevLevel = prevLevel;
			this.jobs = jobs;
			this.multiplier = mult;
		}

		@Override
		public void run() {
			if (player.getGameMode() != GameMode.SURVIVAL) {
				return;
			}

			int currentLevel = player.getLevel();
			int levelDiff = prevLevel - currentLevel;

			for (String s : jobs) {
				addJobExp(player.getName(), s, levelDiff * multiplier);
			}
		}

	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onSpawnerClick(final InventoryClickEvent event) {
		/**
		 * PigSpawner Protection Protection from users being able to get
		 * pigspawners from fishing.
		 */
		if (event.isCancelled()) {
			return;
		}

		Player player = (Player) event.getView().getPlayer();
		if (player == null || player.getGameMode() != GameMode.SURVIVAL) {
			return;
		}

		ItemStack item = event.getCurrentItem();
		if (item != null && item.getType() == Material.MOB_SPAWNER && item.getDurability() == (short) 0) {
			item.setDurability((short) 54);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerRightClick(PlayerInteractEvent event) {
		/**
		 * Builder Ability: Enderdragon Summoning If they create an E made out
		 * of obsidian and right click it with flint and steel then an
		 * Enderdragon should spawn.
		 */
		if (event.isCancelled()) {
			return;
		}
		if (!event.hasBlock()) {
			return;
		}
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			return;
		}

		Player player = event.getPlayer();
		ItemStack inhand = player.getItemInHand();

		if (inhand.getType() != Material.FLINT_AND_STEEL) {
			return;
		}
		if (!player.getWorld().getName().toLowerCase().contains("the_end")) {
			return;
		}

		Block block = event.getClickedBlock();
		World world = block.getWorld();

		if (block.getType() != Material.OBSIDIAN) {
			return;
		}
		if (!isInJob(player.getName(), "Builder") || getJobLevel(player.getName(), "Builder") < ENDERSPAWNING_LEVEL) {
			player.sendMessage(ChatColor.RED + "You must be at least a level " + ChatColor.GOLD + ENDERSPAWNING_LEVEL
					+ " builder " + ChatColor.RED + " to spawn an EnderDragon.");
			return;
		}

		// Find out which direction the E is pointing
		int xdir = 0;
		int zdir = 0;
		if (world.getBlockAt(block.getLocation().add(1, 0, 0)).getType() == Material.OBSIDIAN) {
			xdir = 1;
		} else if (world.getBlockAt(block.getLocation().add(-1, 0, 0)).getType() == Material.OBSIDIAN) {
			xdir = -1;
		} else if (world.getBlockAt(block.getLocation().add(0, 0, 1)).getType() == Material.OBSIDIAN) {
			zdir = 1;
		} else if (world.getBlockAt(block.getLocation().add(0, 0, -1)).getType() == Material.OBSIDIAN) {
			zdir = -1;
		} else {
			return;
		}

		// Make sure there are 11 obsidian within the region
		int count = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				Block tempBlock = world.getBlockAt(block.getLocation().add(xdir * j, i, zdir * j));
				if (tempBlock.getType() == Material.OBSIDIAN) {
					count++;
				}
			}
		}
		if (count != 11) {
			return;
		}

		// Check to make sure the 4 empty spaces are where they should be
		Block air1 = world.getBlockAt(block.getLocation().add(xdir * 1, 1, zdir * 1));
		Block air2 = world.getBlockAt(block.getLocation().add(xdir * 2, 1, zdir * 2));
		Block air3 = world.getBlockAt(block.getLocation().add(xdir * 1, 3, zdir * 1));
		Block air4 = world.getBlockAt(block.getLocation().add(xdir * 2, 3, zdir * 2));
		// if(air1.getType() != Material.AIR || air2.getType() != Material.AIR
		// || air3.getType() != Material.AIR || air4.getType() != Material.AIR)
		// return;
		String key = player.getName() + "enderspawning";
		if (Cooldown.cooldownExists(key) && !player.isOp()) {
			player.sendMessage(ChatColor.GOLD + "You can't use this for " + ChatColor.RED
					+ Cooldown.getCooldownMinutes(key) + ChatColor.GOLD + " and " + ChatColor.RED
					+ Cooldown.getCooldownSeconds(key) + " seconds.");
			return;
		}
		new Cooldown(key, ENDERSPAWNING_COOLDOWN);

		if (air1.getType() == Material.AIR) {
			air1.setType(Material.FIRE);
		}
		if (air2.getType() == Material.AIR) {
			air2.setType(Material.FIRE);
		}
		if (air3.getType() == Material.AIR) {
			air3.setType(Material.FIRE);
		}
		if (air4.getType() == Material.AIR) {
			air4.setType(Material.FIRE);
		}

		// Create some explosions to look cool.
		for (int i = 1; i < 4; i++) {
			new DelayedExplosion(air3.getLocation(), 2F, 20 * i);
		}

		// Remove all the blocks
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				Block tempBlock = world.getBlockAt(block.getLocation().add(xdir * j, i, zdir * j));
				new PlaceBlockTask(Material.AIR, tempBlock.getLocation(), 80);
			}
		}
		new DelayedSpawn(block.getLocation().add(0, 10, 0), EntityType.ENDER_DRAGON, 80);

	}

	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent event) {
		if (event.isCancelled()) {
			return;
		}
		LivingEntity lent = event.getEntity();

		if (lent instanceof EnderDragon && !lent.getLocation().getWorld().getName().toLowerCase().contains("the_end")) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		/**
		 * Disguise Command Overrider Disable the /disguise command handled by
		 * disguiseLib, and use the one that we create with DisguiseCommand.
		 */
		if (event.getMessage().toLowerCase().contains(("/disguise"))) {
			event.setCancelled(true);
			new DisguiseCommand(event.getPlayer(), event.getMessage());
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		/**
		 * Disguise Remover If a player hits or is hit by another player then we
		 * need to remove both of their disguises and place them on cooldown.
		 */
		if (event.isCancelled()) {
			return;
		}
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player p1 = (Player) event.getEntity();
			Player p2 = (Player) event.getDamager();
			if (DisguiseAPI.isDisguised(p1)) {
				p1.sendMessage(ChatColor.RED + "Your disguise has been blown due to PVP.");
				DisguiseAPI.undisguiseToAll(p1);
			}
			if (DisguiseAPI.isDisguised(p2)) {
				p2.sendMessage(ChatColor.RED + "Your disguise has been blown due to PVP.");
				DisguiseAPI.undisguiseToAll(p2);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRightClickEnchantTable(PlayerInteractEvent event) {
		/**
		 * EXP converting into Bottles and Buckets If a player right clicks on
		 * an enchantment table with an empty bucket or empty bottle then we
		 * will attempt to convert their exp into an item that will grant exp.
		 */
		if (event.isCancelled()) {
			return;
		}
		if (!event.hasBlock()) {
			return;
		}
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			return;
		}

		Player player = event.getPlayer();
		ItemStack inhand = player.getItemInHand();
		if (inhand.getType() != Material.BUCKET && inhand.getType() != Material.GLASS_BOTTLE) {
			return;
		}

		Block block = event.getClickedBlock();
		if (block.getType() != Material.ENCHANTMENT_TABLE) {
			return;
		}
		event.setCancelled(true);
		if (CustomItems.isCustomItem(inhand)) {
			player.sendMessage(ChatColor.RED + "Use a normal Bucket or Glass Bottle to store your exp.");
			return;
		}
		World world = block.getWorld();
		ExperienceManager eManager = new ExperienceManager(player);
		if (inhand.getType() == Material.GLASS_BOTTLE) {
			if (eManager.getCurrentExp() >= 100) {
				ItemStack item = CustomItems.createCustomItem("Bottle o' EXP", 1);
				item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = meta.getLore();
				lore.add(ChatColor.GRAY + "100 experience");
				meta.setLore(lore);
				item.setItemMeta(meta);
				world.dropItemNaturally(block.getLocation(), item);
				eManager.changeExp(-100);
			} else {
				player.sendMessage(ChatColor.RED + "You must have 100 EXP to create 1 Bottle o' EXP.");
				return;
			}
		} else if (inhand.getType() == Material.BUCKET) {
			if (eManager.getCurrentExp() >= 1000) {
				ItemStack item = CustomItems.createCustomItem("Bucket o' EXP", 1);
				item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = meta.getLore();
				lore.add(ChatColor.GRAY + "1000 experience");
				meta.setLore(lore);
				item.setItemMeta(meta);
				world.dropItemNaturally(block.getLocation(), item);
				eManager.changeExp(-1000);
			} else {
				player.sendMessage(ChatColor.RED + "You must have 1000 EXP to create 1 Bucket o' EXP.");
				return;
			}
		}
		Tools.decrementItemInHand(player, 1);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRightClickForExp(PlayerInteractEvent event) {
		/**
		 * EXP restoring with EXP bucket or EXP bottle. When a player right
		 * clicks while holding a Bucket o' EXP or Bottle o' EXP we need to
		 * restore their exp and remove the item.
		 */
		if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		Player player = event.getPlayer();
		ItemStack inhand = player.getItemInHand();
		ExperienceManager eManager = new ExperienceManager(player);

		if (CustomItems.isCustomItem("Bottle o' EXP", inhand)) {
			eManager.changeExp(100);
			player.sendMessage(ChatColor.AQUA + "Received 100 EXP.");
			event.setCancelled(true);
			Tools.decrementItemInHand(player, 1);
		} else if (CustomItems.isCustomItem("Bucket o' EXP", inhand)) {
			eManager.changeExp(1000);
			player.sendMessage(ChatColor.AQUA + "Received 1000 EXP.");
			event.setCancelled(true);
			Tools.decrementItemInHand(player, 1);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEggThrow(ProjectileLaunchEvent event) {
		/**
		 * Egg Bomb throwing When a user attempts to throw an egg we need to
		 * check the item in their hand to see if it was a custom egg or not. If
		 * it is custom then put its ID in the eggMap.
		 */
		Projectile proj = event.getEntity();
		ProjectileSource projSource = proj.getShooter();
		if (!(projSource instanceof Player)) {
			return;
		}
		Player player = (Player) projSource;
		ItemStack inhand = player.getItemInHand();

		if (!inhand.hasItemMeta() || !inhand.getItemMeta().hasDisplayName()) {
			return;
		}
		String name = ChatColor.stripColor(inhand.getItemMeta().getDisplayName());
		if (!CustomItems.isCustomItem(inhand)) {
			return;
		}
		String key = player.getName() + "bombs";
		if (Cooldown.cooldownExists(key) && !player.isOp()) {
			player.sendMessage(ChatColor.RED + "You must wait " + Cooldown.getCooldownMinutes(key) + " minutes and "
					+ Cooldown.getCooldownSeconds(key) + " seconds before using another bomb.");
			event.setCancelled(true);
			inhand.setAmount(inhand.getAmount() + 1);
			return;
		}
		new Cooldown(key, BOMB_COOLDOWN);
		eggMap.put(proj.getEntityId(), new Pair<String, String>(name, player.getName()));
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEggHit(ProjectileHitEvent event) {
		/**
		 * Bombs that relate to specific jobs When a Egg hits the ground we need
		 * to check if it was a Bomb such as the Survivalist Bomb. If it was a
		 * bomb then we need to provide the effect that the bomb is suppose to
		 * have. See CustomRecipes.java
		 */
		Random rand = new Random();
		Entity entity = event.getEntity();
		if (!(entity instanceof Egg)) {
			return;
		}
		if (!eggMap.containsKey(entity.getEntityId())) {
			return;
		}

		Location loc = entity.getLocation();
		Pair<String, String> pair = eggMap.get(entity.getEntityId());
		Player player = Bukkit.getPlayer(pair.getSecond());
		String itemName = pair.getFirst();
		eggMap.remove(entity.getEntityId());
		if ((!MonstersPlus.isPVPLocation(loc) || !MonstersPlus.isFlagAllowed(loc, DefaultFlag.USE)) && !player.isOp()) {
			return;
		}

		if (itemName.equals("Survivalist Bomb")) {
			List<LivingEntity> lEntities = Tools.getNearbyLivingEntities(loc, 6);
			for (LivingEntity lent : lEntities) {
				Tools.potionEffectEntity(lent, PotionEffectType.BLINDNESS, 80, 4 * 20, 100);
				Tools.potionEffectEntity(lent, PotionEffectType.NIGHT_VISION, 80, 4 * 20, 100);
				Tools.potionEffectEntity(lent, PotionEffectType.SLOW, 80, 4 * 20, 100);
			}
		} else if (itemName.equals("Warrior Bomb")) {
			List<LivingEntity> lEntities = Tools.getNearbyLivingEntities(loc, 6);
			for (LivingEntity lent : lEntities) {
				Tools.burnEntity(lent, 8 * 20, 100);
			}
			LivingEntity lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.PIG_ZOMBIE);
			EntityEquipment ee = lent.getEquipment();
			ee.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
			ee.setArmorContents(
					new ItemStack[] { new ItemStack(Material.GOLD_HELMET), new ItemStack(Material.GOLD_CHESTPLATE),
							new ItemStack(Material.GOLD_LEGGINGS), new ItemStack(Material.GOLD_BOOTS) });
			ee.setItemInHandDropChance(0F);
			ee.setHelmetDropChance(0F);
			ee.setChestplateDropChance(0F);
			ee.setLeggingsDropChance(0F);
			ee.setBootsDropChance(0F);
			new RemoveEntityTask(lent, 10 * 20);

			if (lEntities.size() > 0) {
				((Monster) lent).setTarget(lEntities.get(rand.nextInt(lEntities.size())));
			}

		} else if (itemName.equals("WitchDoctor Bomb")) {
			List<LivingEntity> lEntities = Tools.getNearbyLivingEntities(loc, 6);
			for (LivingEntity lent : lEntities) {
				Tools.potionEffectEntity(lent, PotionEffectType.WITHER, 1, 4 * 20, 100);
			}
			for (int i = 0; i < 3; i++) {
				LivingEntity lent = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
				((Skeleton) lent).setSkeletonType(SkeletonType.WITHER);
				EntityEquipment ee = lent.getEquipment();
				ee.setItemInHand(new ItemStack(Material.BOW));
				ee.setItemInHandDropChance(0F);
				lent.setMaxHealth(100);
				lent.setHealth(lent.getMaxHealth());
				new RemoveEntityTask(lent, 10 * 20);
				if (lEntities.size() > 0) {
					((Monster) lent).setTarget(lEntities.get(rand.nextInt(lEntities.size())));
				}
			}
		} else if (itemName.equals("Miner Bomb")) {
			final float SIZE = 2F;
			final int DELAY = 3;
			for (int i = 0; i < 26; i += 2) {
				Location loc2 = loc.clone().add(i, 0, 0);
				new DelayedExplosion(loc2, SIZE, DELAY * i);
				loc2 = loc.clone().add(-i, 0, 0);
				new DelayedExplosion(loc2, SIZE, DELAY * i);
				loc2 = loc.clone().add(0, 0, i);
				new DelayedExplosion(loc2, SIZE, DELAY * i);
				loc2 = loc.clone().add(0, 0, -i);
				new DelayedExplosion(loc2, SIZE, DELAY * i);

				loc2 = loc.clone().add(i, 0, i);
				new DelayedExplosion(loc2, SIZE, DELAY * i);
				loc2 = loc.clone().add(i, 0, -i);
				new DelayedExplosion(loc2, SIZE, DELAY * i);
				loc2 = loc.clone().add(-i, 0, i);
				new DelayedExplosion(loc2, SIZE, DELAY * i);
				loc2 = loc.clone().add(-i, 0, -i);
				new DelayedExplosion(loc2, SIZE, DELAY * i);
			}
		} else if (itemName.equals("Fisherman Bomb")) {
			List<LivingEntity> lEntities = Tools.getNearbyLivingEntities(loc, 6);
			for (LivingEntity lent : lEntities) {
				lent.getWorld().strikeLightning(lent.getLocation());
			}
			for (int i = -5; i <= 5; i++) {
				for (int j = -5; j <= 5; j++) {
					if (Tools.randomChance(10)) {
						new DelayedLightning(loc.clone().add(i, 0, j), rand.nextInt(150));
					}
				}
			}
		} else if (itemName.equals("Blacksmith Bomb")) {
			World world = loc.getWorld();
			for (int i = -5; i <= 5; i++) {
				for (int j = -5; j <= 5; j++) {
					if (Tools.randomChance(65)) {
						FallingBlock fblock = world.spawnFallingBlock(loc.clone().add(i, 20, j), Material.ANVIL,
								(byte) 1);
						fblock.setFallDistance(50F);
						fblock.setDropItem(false);
						((CraftFallingSand) fblock).getHandle().a(true);
						anvilList.add(fblock.getEntityId());
					}
				}
			}
		}
	}

	@EventHandler
	public void onAnvilFall(EntityChangeBlockEvent event) {
		/**
		 * Blacksmith Bomb anvil removal When an anvil hits the ground we need
		 * to check if it's ID is contained in the anvilList. If it is in the
		 * list then we need to remove the remove the anvil when it hits the
		 * ground.
		 */
		Entity ent = event.getEntity();
		@SuppressWarnings("unused")
		Block block = event.getBlock();

		if (!(ent instanceof FallingBlock)) {
			return;
		}
		FallingBlock fblock = (FallingBlock) ent;
		if (anvilList.contains(fblock.getEntityId())) {
			anvilList.remove(new Integer(fblock.getEntityId()));
			event.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRightClickForReachCommand(PlayerInteractEvent event) {
		/**
		 * Builder ability: Reach When a player right clicks the air with a
		 * block we need to check if their name is inside of the
		 * BuilderCommand.reachInstances map. If it is there then we need to
		 * check if the block should have been placed from a far distance.
		 */
		if (!event.isBlockInHand()) {
			return;
		}
		if (event.getAction() != Action.RIGHT_CLICK_AIR) {
			return;
		}

		Player player = event.getPlayer();
		if (!BuilderCommand.reachInstances.containsKey(player.getName())) {
			return;
		}
		int reach = BuilderCommand.reachInstances.get(player.getName());
		List<Block> blocks = player.getLastTwoTargetBlocks(new HashSet<Byte>(), reach);
		if (!MonstersPlus.isBuildLocation(player, blocks.get(0).getLocation())) {
			return;
		}
		try {
			if (blocks.get(1).getType() != Material.AIR) {
				Block block = blocks.get(0);
				BlockPlaceEvent myEvent = new BlockPlaceEvent(block, block.getState(), blocks.get(1),
						player.getItemInHand(), player, true);
				Bukkit.getPluginManager().callEvent(myEvent);

				if (!myEvent.isCancelled()) {
					block.setType(player.getItemInHand().getType());
					if (player.getGameMode() != GameMode.CREATIVE) {
						Tools.decrementItemInHand(player, 1);
					}
					if (MonstersPlus.coreProtect != null) {
						MonstersPlus.coreProtect.logPlacement(player.getName(), block.getLocation(),
								player.getItemInHand().getTypeId(), block.getData());
					}
				}

			}
		} catch (Exception e) {
		}
	}

	@EventHandler
	public void onRightClickForChallenge(PlayerInteractEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Player player = event.getPlayer();
		Entity ent = event.getRightClicked();
		if (!(ent instanceof ItemFrame)) {
			return;
		}
		ItemFrame iframe = (ItemFrame) ent;

		@SuppressWarnings("deprecation")
		ItemStack inhand = player.getItemInHand();
		if (inhand.getType() != Material.SKULL_ITEM) {
			return;
		}
		ItemStack frameItem = iframe.getItem();
		for (int i = 0; i < CHALLENGE_ITEMS.length; i++) {
			if (frameItem.getType() == CHALLENGE_ITEMS[i]) {
				event.setCancelled(true);
				int level = JobsListener.getJobLevel(player.getName(), "Warrior");
				if (level < CHALLENGE_LEVELS[i]) {
					player.sendMessage(ChatColor.RED + "You must be a warrior level " + ChatColor.GOLD
							+ CHALLENGE_LEVELS[i] + ChatColor.RED + " to use this.");
					return;
				} else if (!MonstersPlus.isBuildLocation(player, ent.getLocation())) {
					player.sendMessage(ChatColor.RED + "You don't have permission to spawn monsters here.");
					return;
				}

				String key = player.getName() + "challenge" + i;
				if (Cooldown.cooldownExists(key) && !player.isOp()) {
					player.sendMessage(ChatColor.RED + "You must wait " + ChatColor.GOLD
							+ Cooldown.getCooldownMinutes(key) + ChatColor.RED + " minutes and " + ChatColor.GOLD
							+ Cooldown.getCooldownSeconds(key) + ChatColor.RED
							+ " seconds before using this ability again.");
					return;
				}
				new Cooldown(key, CHALLENGE_COOLDOWNS[i]);
				Random rand = new Random();
				Location loc = iframe.getLocation().clone();
				loc.getWorld().playSound(loc, Sound.values()[rand.nextInt(Sound.values().length)], 1F,
						rand.nextFloat());

				for (int j = 0; j < 40; j++) {
					new DelayedEffect(
							loc.clone().add(rand.nextInt(20) - 10, rand.nextInt(4) - 2, rand.nextInt(20) - 10),
							Effect.ENDER_SIGNAL, 0, 10, rand.nextInt(20));
				}

				@SuppressWarnings("unused")
				LivingEntity lent = MonsterSpawnCommand
						.spawnRandomMonster(loc.clone().add(rand.nextInt(20) - 10, 1, rand.nextInt(20) - 10), i + 1);

				iframe.setItem(new ItemStack(Material.AIR));
			}
		}

	}

	public static int getJobLevel(String playerName, String jobName) {
		Job job = Jobs.getJob(jobName);
		JobsPlayer jplayer = Jobs.getPlayerManager().getJobsPlayer(playerName);
		if (jplayer.isInJob(job)) {
			JobProgression jprog = jplayer.getJobProgression(job);
			return jprog.getLevel();
		}
		return 0;
	}

	public static boolean isInJob(String playerName, String jobName) {
		Job job = Jobs.getJob(jobName);
		JobsPlayer jplayer = Jobs.getPlayerManager().getJobsPlayer(playerName);

		if (jplayer.isInJob(job)) {
			return true;
		}
		return false;
	}

	public static boolean addJobExp(String playerName, String jobName, double exp) {
		Job job = Jobs.getJob(jobName);
		JobsPlayer jplayer = Jobs.getPlayerManager().getJobsPlayer(playerName);
		JobProgression jprog = jplayer.getJobProgression(job);

		if (isInJob(playerName, jobName)) {
			jprog.addExperience(exp);
			return true;
		}
		return false;
	}
}
