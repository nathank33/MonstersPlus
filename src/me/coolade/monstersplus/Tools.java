package me.coolade.monstersplus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagList;

public class Tools {
	private Tools() {
	}

	public static Location getClosestMaterial(Location startingLoc, Material mat, int rad) {
		// Iterates through a radius of blocks and finds if any are matching the
		// parameter material
		World world = startingLoc.getWorld();
		int i = 0;
		while (i < rad) {
			int j = 0;
			while (j < rad) {
				int k = 0;
				while (k < rad) {
					Location temp = startingLoc.clone();
					Block newBlock = world.getBlockAt(temp.add(new Location(world, i, j, k)));

					if (newBlock.getType() == mat) {
						return newBlock.getLocation();
					}

					if (k > 0) {
						k *= -1;
					} else {
						k = Math.abs(k) + 1;
					}
				}
				if (j > 0) {
					j *= -1;
				} else {
					j = Math.abs(j) + 1;
				}
			}
			if (i > 0) {
				i *= -1;
			} else {
				i = Math.abs(i) + 1;
			}
		}
		return null;
	}

	public static Location lookAt(Location loc, Location lookat) {
		// Clone the loc to prevent applied changes to the input loc
		loc = loc.clone();

		// Values of change in distance (make it relative)
		double dx = lookat.getX() - loc.getX();
		double dy = lookat.getY() - loc.getY();
		double dz = lookat.getZ() - loc.getZ();

		// Set yaw
		if (dx != 0) {
			// Set yaw start value based on dx
			if (dx < 0) {
				loc.setYaw((float) (1.5 * Math.PI));
			} else {
				loc.setYaw((float) (0.5 * Math.PI));
			}
			loc.setYaw(loc.getYaw() - (float) Math.atan(dz / dx));
		} else if (dz < 0) {
			loc.setYaw((float) Math.PI);
		}

		// Get the distance from dx/dz
		double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

		// Set pitch
		loc.setPitch((float) -Math.atan(dy / dxz));

		// Set values, convert to degrees (invert the yaw since Bukkit uses a
		// different yaw dimension format)
		loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
		loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);

		return loc;
	}

	public static Location getClosestEntityType(Entity ent, EntityType etype, int radius) {
		List<Entity> nbEntList = ent.getNearbyEntities(radius, radius, radius);
		organizeEntityList(nbEntList, ent.getLocation());
		for (Entity e : nbEntList) {
			if (e.getType() == etype) {
				return e.getLocation();
			}
		}
		return null;
	}

	public static void organizeEntityList(List<Entity> entList, Location loc) {
		/*
		 * This method organizes a list of entities that surround a certain
		 * location. They are organized by closest to furthest.
		 */
		boolean keepGoing = true;
		while (keepGoing == true) {
			keepGoing = false;
			for (int i = 0; i < entList.size() - 1; i++) {
				if (getDistanceSquared(loc, entList.get(i).getLocation()) > getDistanceSquared(loc,
						entList.get(i + 1).getLocation())) {
					keepGoing = true;
					Entity temp;
					temp = entList.get(i);
					entList.set(i, entList.get(i + 1));
					entList.set(i + 1, temp);
				}
			}
		}
	}

	public static double getDistanceSquared(Location loc1, Location loc2) {
		// Calculates the distance between two locations.
		return loc1.toVector().distanceSquared(loc2.toVector());
	}

	public static boolean hasEmptyFaces(Location loc) {
		// Checks to see if the location has empty faces surrounding it.
		World world = loc.getWorld();
		if (world.getBlockAt((int) loc.getX() + 1, (int) loc.getY(), (int) loc.getZ()).getType() == Material.AIR) {
			if (world.getBlockAt((int) loc.getX() - 1, (int) loc.getY(), (int) loc.getZ()).getType() == Material.AIR) {
				if (world.getBlockAt((int) loc.getX(), (int) loc.getY(), (int) loc.getZ() + 1)
						.getType() == Material.AIR) {
					if (world.getBlockAt((int) loc.getX() + 1, (int) loc.getY(), (int) loc.getZ() - 1)
							.getType() == Material.AIR) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static ItemFrame addItemFrame(Block block, BlockFace face) {
		// Attempts to add an itemframe onto a specific blockface of a block
		final Material mat = Material.GLASS;
		Block northBlock = block.getWorld().getBlockAt(block.getX(), block.getY(), block.getZ() - 1);
		Block southBlock = block.getWorld().getBlockAt(block.getX(), block.getY(), block.getZ() + 1);
		Block eastBlock = block.getWorld().getBlockAt(block.getX() - 1, block.getY(), block.getZ());
		Block westBlock = block.getWorld().getBlockAt(block.getX() + 1, block.getY(), block.getZ());

		if (northBlock.getType() == Material.AIR) {
			northBlock.setType(mat);
		}
		if (southBlock.getType() == Material.AIR) {
			southBlock.setType(mat);
		}
		if (eastBlock.getType() == Material.AIR) {
			eastBlock.setType(mat);
		}
		if (westBlock.getType() == Material.AIR) {
			westBlock.setType(mat);
		}

		if (face == BlockFace.NORTH) {
			northBlock.setType(Material.AIR);
		} else if (face == BlockFace.SOUTH) {
			southBlock.setType(Material.AIR);
		} else if (face == BlockFace.EAST) {
			eastBlock.setType(Material.AIR);
		} else if (face == BlockFace.WEST) {
			westBlock.setType(Material.AIR);
		}

		ItemFrame itemFrame = (ItemFrame) block.getWorld().spawnEntity(block.getLocation(), EntityType.ITEM_FRAME);

		if (northBlock.getType() == mat) {
			northBlock.setType(Material.AIR);
		}
		if (southBlock.getType() == mat) {
			southBlock.setType(Material.AIR);
		}
		if (eastBlock.getType() == mat) {
			eastBlock.setType(Material.AIR);
		}
		if (westBlock.getType() == mat) {
			westBlock.setType(Material.AIR);
		}

		return itemFrame;
	}

	public static List<Entity> getNearbyEntities(Location loc, double radius) {
		// Gets the nearby entities for a specific location, rather than bukkits
		// Entity only method
		List<Entity> closeEntities = new ArrayList<Entity>();
		List<Entity> totalEntities = loc.getWorld().getEntities();
		for (Entity ent : totalEntities) {
			if (loc.getWorld().equals(ent.getWorld())) {
				if (loc.distanceSquared(ent.getLocation()) < Math.pow(radius, 2)) {
					closeEntities.add(ent);
				}
			}
		}
		return closeEntities;
	}

	public static List<LivingEntity> getNearbyLivingEntities(Location loc, double radius) {
		List<Entity> entities = getNearbyEntities(loc, radius);
		List<LivingEntity> lEntities = new ArrayList<LivingEntity>();
		for (Entity ent : entities) {
			if (ent instanceof LivingEntity) {
				lEntities.add((LivingEntity) ent);
			}
		}
		return lEntities;
	}

	public static List<String> getNearbyPlayers(Location loc, double radius) {
		// Gets the nearby entities for a specific location, rather than bukkits
		// Entity only method
		List<String> playerNames = new ArrayList<String>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getLocation().getWorld().equals(loc.getWorld())) {
				if (loc.distanceSquared(player.getLocation()) < Math.pow(radius, 2)) {
					playerNames.add(player.getName());
				}
			}
		}
		return playerNames;
	}

	public static List<Entity> getNearbyMonsters(Location loc, double radius) {
		List<Entity> nbEnts = getNearbyEntities(loc, radius);
		for (int i = 0; i < nbEnts.size(); i++) {
			Entity ent = nbEnts.get(i);
			if (!(ent instanceof Monster)) {
				nbEnts.remove(i);
				i--;
			}
		}
		return nbEnts;
	}

	public static ItemFrame getFaceItemFrame(Location loc, BlockFace face) {
		// returns any itemframe that is on the direction of the blockface
		List<Entity> nbEnts = Tools.getNearbyEntities(loc, 1.5);
		for (int i = 0; i < nbEnts.size(); i++) {
			if (!(nbEnts.get(i) instanceof ItemFrame)) {
				nbEnts.remove(i);
				i--;
			}
		}

		for (Entity ent : nbEnts) {
			if ((int) ent.getLocation().getYaw() == 0 && face == BlockFace.NORTH) {
				return (ItemFrame) ent;
			} else if ((int) ent.getLocation().getYaw() == 90 && face == BlockFace.WEST) {
				return (ItemFrame) ent;
			} else if ((int) ent.getLocation().getYaw() == 180 && face == BlockFace.SOUTH) {
				return (ItemFrame) ent;
			} else if ((int) ent.getLocation().getYaw() == 270 && face == BlockFace.EAST) {
				return (ItemFrame) ent;
			}
		}
		return null;
	}

	public static void removeNearbyEntity(Location loc, EntityType type, double radius) {
		List<Entity> nbEnts = Tools.getNearbyEntities(loc, radius);
		for (int i = 0; i < nbEnts.size(); i++) {
			if (nbEnts.get(i).getType() == type) {
				nbEnts.get(i).remove();
			}
		}
	}

	public static void removeNearbyEntity(Location loc, Material mat, double radius) {
		List<Entity> nbEnts = Tools.getNearbyEntities(loc, radius);
		for (int i = 0; i < nbEnts.size(); i++) {
			Entity nb = nbEnts.get(i);
			if (nb instanceof Item) {
				Item item = (Item) nb;
				ItemStack istack = item.getItemStack();
				if (istack.getType() == mat) {
					nbEnts.get(i).remove();
				}
			}
		}
	}

	public static void removeNearbyEntityDelay(final Location loc, final Material mat, final double radius,
			final long delay) {
		MonstersPlus.plugin.getServer().getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new Runnable() {
			@Override
			public void run() {
				removeNearbyEntity(loc, mat, radius);
			}
		}, delay);
	}

	public static List<Entity> getNearbyEntity(Location loc, EntityType type, double radius) {
		List<Entity> nbEnts = Tools.getNearbyEntities(loc, radius);
		for (int i = 0; i < nbEnts.size(); i++) {
			if (nbEnts.get(i).getType() != type) {
				nbEnts.remove(i);
				i--;
			}
		}
		return nbEnts;
	}

	public static List<Entity> getNearbyEntityDelay(Location loc, EntityType type, double radius, int delay) {
		List<Entity> nbEnts = Tools.getNearbyEntities(loc, radius);
		for (int i = 0; i < nbEnts.size(); i++) {
			if (nbEnts.get(i).getType() != type) {
				nbEnts.remove(i);
				i--;
			}
		}
		return nbEnts;
	}

	public static ItemStack getFirstItem(Player player, ItemStack istack) {
		// Searches the players inventory for the first instance of the specific
		// Material type.
		PlayerInventory inv = player.getInventory();
		ItemStack[] contents = inv.getContents();
		for (int i = 0; i < contents.length; i++) {
			if (contents[i] != null) {
				if (contents[i].getType() == istack.getType()) {
					return contents[i];
				}
			}
		}
		return null;
	}

	public static int getFirstItemSlot(Player player, ItemStack istack) {
		/*
		 * Returns the position of the slot for a specific item.
		 */
		PlayerInventory inv = player.getInventory();
		ItemStack[] contents = inv.getContents();
		for (int i = 0; i < contents.length; i++) {
			if (contents[i] != null) {
				if (contents[i].getType() == istack.getType()) {
					return i;
				}
			}
		}
		return -1;
	}

	public static ItemStack getPowerstoneItem() {
		ItemStack pstone = new ItemStack(Material.REDSTONE);
		ItemMeta meta = pstone.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_RED + "Powerstone");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_RED + "Powerstone");
		lore.add("Unique");
		meta.setLore(lore);
		pstone.setItemMeta(meta);
		pstone.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		return pstone;
	}

	public static void spawnPowerstone(Location loc, int quantity) {
		ItemStack pstone = getPowerstoneItem();
		pstone.setAmount(quantity);
		loc.getWorld().dropItemNaturally(loc, pstone);
	}

	public static boolean isPowerstone(ItemStack item) {
		if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()
				|| !item.getItemMeta().hasLore()) {
			return false;
		}

		ItemMeta meta = item.getItemMeta();
		String name = meta.getDisplayName();
		List<String> lore = meta.getLore();

		if (name.contains("Powerstone")) {
			for (String s : lore) {
				if (s.contains("Powerstone")) {
					return true;
				}
			}
		}
		return false;
	}

	public static int getAmountOfPowerstones(Player player) {
		if (player == null) {
			return -1;
		}

		PlayerInventory inv = player.getInventory();
		ItemStack[] istacks = inv.getContents();

		int totalStones = 0;
		for (int i = 0; i < istacks.length; i++) {
			if (isPowerstone(istacks[i])) {
				totalStones += istacks[i].getAmount();
			}
		}
		return totalStones;
	}

	public static void removePowerstones(Player player, int amount) {
		if (player == null) {
			return;
		}

		PlayerInventory inv = player.getInventory();
		ItemStack[] istacks = inv.getContents();
		int amountRemaining = amount;

		for (int i = 0; i < istacks.length; i++) {
			if (isPowerstone(istacks[i])) {
				// If the they have enough in a single stack
				if (istacks[i].getAmount() >= amountRemaining) {
					istacks[i].setAmount(istacks[i].getAmount() - amountRemaining);
					break;
				} else if (istacks[i].getAmount() == amountRemaining) {
					istacks[i].setType(Material.AIR);
					break;
				} else {
					amountRemaining -= istacks[i].getAmount();
					istacks[i].setType(Material.AIR);
				}
			}
		}
		player.getInventory().setContents(istacks);
	}

	public static String[] stringConcatAll(List<String[]> stringLists) {
		int totalSize = 0;
		for (String[] strings : stringLists) {
			totalSize += strings.length;
		}

		String[] tempArray = new String[totalSize];
		int index = 0;
		for (String[] strings : stringLists) {
			for (int i = 0; i < strings.length; i++) {
				tempArray[index] = strings[i];
				index++;
			}
		}
		return tempArray;
	}

	public static ItemStack addGlow(ItemStack item) {
		net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = null;
		if (!nmsStack.hasTag()) {
			tag = new NBTTagCompound();
			nmsStack.setTag(tag);
		}
		if (tag == null) {
			tag = nmsStack.getTag();
		}
		NBTTagList ench = new NBTTagList();
		tag.set("ench", ench);
		nmsStack.setTag(tag);
		return CraftItemStack.asCraftMirror(nmsStack);
	}

	public static ItemStack silkTouchItem(ItemStack item) {
		Material mat = item.getType();
		if (mat.equals(Material.COAL)) {
			item.setType(Material.COAL_ORE);
		} else if (mat.equals(Material.DIAMOND)) {
			item.setType(Material.DIAMOND_ORE);
		} else if (mat.equals(Material.EMERALD)) {
			item.setType(Material.EMERALD_ORE);
		} else if (mat.equals(Material.QUARTZ)) {
			item.setType(Material.QUARTZ_ORE);
		} else if (mat.equals(Material.DIRT)) {
			item.setType(Material.GRASS);
		} else if (mat.equals(Material.COBBLESTONE)) {
			item.setType(Material.STONE);
		} else if (mat.equals(Material.REDSTONE)) {
			item.setType(Material.REDSTONE_ORE);
			item.setAmount(item.getAmount() / 6);
		} else if (mat.equals(Material.INK_SACK) && item.getDurability() == (short) 4) {
			item.setType(Material.LAPIS_ORE);
			item.setAmount(item.getAmount() / 6);
		}

		return item;
	}

	public static ItemStack cookItem(ItemStack item) {
		Material mat = item.getType();
		if (mat.equals(Material.RAW_FISH)) {
			item.setType(Material.COOKED_FISH);
		} else if (mat.equals(Material.RAW_BEEF)) {
			item.setType(Material.COOKED_BEEF);
		} else if (mat.equals(Material.RAW_CHICKEN)) {
			item.setType(Material.COOKED_CHICKEN);
		} else if (mat.equals(Material.PORK)) {
			item.setType(Material.GRILLED_PORK);
		} else if (mat.equals(Material.IRON_ORE)) {
			item.setType(Material.IRON_INGOT);
		} else if (mat.equals(Material.GOLD_INGOT)) {
			item.setType(Material.GOLD_INGOT);
		}
		return item;
	}

	public static boolean randomChance(double chance) {
		Random randomGen = new Random();
		double rand = randomGen.nextDouble() * 100;
		if (rand <= chance) {
			return true;
		}
		return false;
	}

	public static boolean isConflictingEnchantment(ItemStack item, Enchantment ench) {
		/*
		 * Adds up the amount of specific types of enchantments and checks if
		 * the total is greater than 1. If it is greater than 1 than we know
		 * that there are multiple enchants that would conflict.
		 */
		HashMap<Enchantment, Integer> enchs = new HashMap<Enchantment, Integer>(item.getEnchantments());
		enchs.put(ench, 1);
		int protectors = 0;
		int damagers = 0;
		int pickaxers = 0;
		if (enchs.containsKey(Enchantment.PROTECTION_ENVIRONMENTAL)) {
			protectors++;
		}
		if (enchs.containsKey(Enchantment.PROTECTION_EXPLOSIONS)) {
			protectors++;
		}
		if (enchs.containsKey(Enchantment.PROTECTION_FIRE)) {
			protectors++;
		}
		if (enchs.containsKey(Enchantment.PROTECTION_PROJECTILE)) {
			protectors++;
		}
		if (enchs.containsKey(Enchantment.DAMAGE_ALL)) {
			damagers++;
		}
		if (enchs.containsKey(Enchantment.DAMAGE_ARTHROPODS)) {
			damagers++;
		}
		if (enchs.containsKey(Enchantment.DAMAGE_UNDEAD)) {
			damagers++;
		}
		if (enchs.containsKey(Enchantment.SILK_TOUCH)) {
			pickaxers++;
		}
		if (enchs.containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {
			pickaxers++;
		}

		if (protectors > 1 || damagers > 1 || pickaxers > 1) {
			return true;
		}
		return false;
	}

	public static boolean inFactionTerritory(String playerName) {
		Player player = Bukkit.getPlayer(playerName);
		Faction areaFaction = BoardColl.get().getFactionAt(PS.valueOf(player.getLocation()));
		MPlayer mplayer = MPlayer.get(player);
		Faction playerFaction = mplayer.getFaction();

		// Bukkit.broadcastMessage("PFac:" + playerFaction.getName() + " AFac:"
		// + areaFaction.getName());

		if (!areaFaction.getName().equalsIgnoreCase("Wilderness") && !areaFaction.getName().equalsIgnoreCase("Warzone")
				&& areaFaction.getName().equals(playerFaction.getName())) {
			return true;
		}
		return false;
	}

	public static List<Block> getAdjacentBlocks(Block block) {
		/*
		 * Returns all the nearby blocks for block
		 */
		List<Block> blocks = new ArrayList<Block>();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (!(i == 0 && j == 0)) {
					blocks.add(block.getRelative(i, 0, j));
				}
			}
		}

		return blocks;
	}

	// the shooting player, his eyelocation, his view direction
	// (player.getEyeLocation().getDirection()) and the speed of the spawned
	// snowball:
	public static void shootWithAngles(LivingEntity player, Location location, Vector direction, double speed,
			int[] angles, String projType) {

		// making sure that the vector is length 1
		direction.normalize();

		// some trick, to get a vector pointing in the player's view direction,
		// but on the x-z-plane only and without problems when looking straight
		// up (x, z = 0 then)
		Vector dirY = (new Location(location.getWorld(), 0, 0, 0, location.getYaw(), 0)).getDirection().normalize();
		for (int angle : angles) {
			Vector vec;
			if (angle != 0) {
				vec = rotateYAxis(dirY, angle);
				vec.multiply(Math.sqrt(vec.getX() * vec.getX() + vec.getZ() * vec.getZ())).subtract(dirY);
				vec = direction.clone().add(vec).normalize();
			} else {
				vec = direction.clone();
			}

			Projectile proj;
			if (projType.equalsIgnoreCase("Snowball")) {
				proj = location.getWorld().spawn(location, Snowball.class);
			} else if (projType.equalsIgnoreCase("Fireball")) {
				proj = location.getWorld().spawn(location, Fireball.class);
			} else if (projType.equalsIgnoreCase("Witherskull")) {
				proj = location.getWorld().spawn(location, WitherSkull.class);
			} else if (projType.equalsIgnoreCase("SmallFireball")) {
				proj = location.getWorld().spawn(location, SmallFireball.class);
			} else if (projType.equalsIgnoreCase("LargeFireball")) {
				proj = location.getWorld().spawn(location, LargeFireball.class);
			} else {
				proj = location.getWorld().spawn(location, Arrow.class);
			}

			proj.setShooter(player);
			proj.setVelocity(vec.clone().multiply(speed));
		}
	}

	public static Vector rotateYAxis(Vector dir, double angleD) {
		// rotate vector "dir" "angleD" degree on the x-z-(2D)-plane
		double angleR = Math.toRadians(angleD);
		double x = dir.getX();
		double z = dir.getZ();
		double cos = Math.cos(angleR);
		double sin = Math.sin(angleR);
		return (new Vector(x * cos + z * (-sin), 0.0, x * sin + z * cos)).normalize();
	}

	@SuppressWarnings("deprecation")
	public static void damageEntity(LivingEntity damager, LivingEntity target, double damage) {
		target.damage(damage);
		target.setLastDamageCause(new EntityDamageByEntityEvent(damager, target, DamageCause.CUSTOM, damage));
	}

	public static void damageWithoutArmorDamage(LivingEntity damager, LivingEntity target, double damage) {
		try {
			short durHelm = 0, durChest = 0, durLeg = 0, durBoot = 0;
			EntityEquipment ee = target.getEquipment();
			ItemStack helm = ee.getHelmet();
			ItemStack chest = ee.getChestplate();
			ItemStack leg = ee.getLeggings();
			ItemStack boot = ee.getBoots();

			if (helm != null) {
				durHelm = helm.getDurability();
			}
			if (chest != null) {
				durChest = chest.getDurability();
			}
			if (leg != null) {
				durLeg = leg.getDurability();
			}
			if (boot != null) {
				durBoot = boot.getDurability();
			}

			Tools.damageEntity(damager, target, damage);

			if (helm != null) {
				helm.setDurability(durHelm);
			}
			if (chest != null) {
				chest.setDurability(durChest);
			}
			if (leg != null) {
				leg.setDurability(durLeg);
			}
			if (boot != null) {
				boot.setDurability(durBoot);
			}

			if (helm != null) {
				ee.setHelmet(helm);
			}
			if (chest != null) {
				ee.setChestplate(chest);
			}
			if (leg != null) {
				ee.setLeggings(leg);
			}
			if (boot != null) {
				ee.setBoots(boot);
			}
		} catch (NullPointerException npe) {
			Player player = (Player) target;
			Logger logger = Logger.getLogger("Minecraft");
			logger.info("NullPointerException in Tools.damageWithoutArmorDamage.");
			logger.info("PlayerName: " + player.getName());
			logger.info("Damage: " + damage);
			logger.info("Armor Contents: " + player.getEquipment().toString());
		}
	}

	public static void dropRandomSeed(Location loc) {
		Random random = new Random();
		Material[] mats = new Material[] { Material.SEEDS, Material.MELON_SEEDS, Material.CARROT, Material.POTATO,
				Material.PUMPKIN_SEEDS };
		ItemStack istack = new ItemStack(mats[random.nextInt(mats.length)]);
		loc.getWorld().dropItemNaturally(loc, istack);

	}

	public static void potionEffectEntity(LivingEntity lent, PotionEffectType type, int strength, int duration,
			double chance) {
		if (Tools.randomChance(chance)) {
			lent.addPotionEffect(new PotionEffect(type, duration, strength));
		}
	}

	public static void burnEntity(LivingEntity lent, int duration, double chance) {
		if (Tools.randomChance(chance)) {
			lent.setFireTicks(duration);
		}
	}

	public static ItemStack setItemDisplayName(ItemStack istack, String name) {
		ItemMeta meta = istack.getItemMeta();
		meta.setDisplayName(name);
		istack.setItemMeta(meta);
		return istack;
	}

	@SuppressWarnings("deprecation")
	public static void decrementItemInHand(Player player, int amount) {
		PlayerInventory inv = player.getInventory();
		ItemStack inhand = inv.getItemInHand();
		if (inhand.getAmount() > amount) {
			inhand.setAmount(inhand.getAmount() - amount);
		} else {
			player.setItemInHand(new ItemStack(Material.AIR));
		}
	}
}
