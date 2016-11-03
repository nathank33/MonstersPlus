package me.coolade.jobsplus.witchdoctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.Cauldron;

import me.coolade.jobsplus.JobsListener;
import me.coolade.monstersplus.Tools;

public class BrewingListener implements Listener {
	public static final int BREWING_LEVEL = 2;
	public static final int STIR_LEVEL1 = 7;
	public static final int STIR_LEVEL2 = 15;
	public static final int MAX_REFILLS = 3;
	public static final double INGREDIENT_EXP = 2;
	public static HashMap<Location, CustomCauldron> cauldList = new HashMap<Location, CustomCauldron>();
	public static ArrayList<Location> locationList = new ArrayList<Location>();

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onRightClickCauldron(PlayerInteractEvent event) {
		/*
		 * This is the main listener to handle all custom brewing, Everything
		 * will be handled using metadata and lists cauldList is the list that
		 * holds the CustomCauldron information/ids
		 */
		if (event.isCancelled()) {
			return;
		}
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if (!(block.getState().getData() instanceof Cauldron)) {
			return;
		}

		Cauldron cauldron = (Cauldron) block.getState().getData();
		ItemStack inhand = player.getInventory().getItemInHand();
		PlayerInventory inv = player.getInventory();
		if (inhand == null) {
			return;
		}

		CustomCauldron custCauld = getCustomCauldron(block.getLocation());
		// Check if it is their cauldron
		if (custCauld != null && custCauld.getPlayerName() != player.getName()) {
			player.sendMessage(ChatColor.RED + "This is not your custom cauldron");
			return;
		}
		if (custCauld != null && !Tools.hasEmptyFaces(block.getLocation())) {
			player.sendMessage(ChatColor.RED + "The blocks next to the cauldron must be empty to continue process.");
			return;
		}

		// The initial starting process for the cauldron
		if (inhand.getType() == Material.NETHER_STALK && custCauld == null) {
			int jobsLevel = JobsListener.getJobLevel(player.getName(), "WitchDoctor");
			if (jobsLevel < BREWING_LEVEL) {
				player.sendMessage(ChatColor.RED + "Custom brewing is only available for level " + ChatColor.GOLD
						+ BREWING_LEVEL + " WitchDoctors.");
				return;
			}

			if (!cauldron.isFull()) {
				player.sendMessage(
						ChatColor.RED + "The cauldron must be filled with water to start the custom brewing process.");
				return;
			}

			if (!Tools.hasEmptyFaces(block.getLocation())) {
				player.sendMessage(ChatColor.RED
						+ "The blocks next to the cauldron must be empty to start the custom brewing process.");
				return;
			}

			if (hasCustomCauldron(player.getName())) {
				player.sendMessage(ChatColor.RED + "You already have a cauldron.");
				return;
			}

			if (custCauld == null) // Create a new Custom Cauldron
			{
				// Start the new custom process
				custCauld = new CustomCauldron(block.getLocation(), player.getName());
				cauldList.put(block.getLocation(), custCauld);
				locationList.add(block.getLocation());
				player.sendMessage(ChatColor.GOLD + "Custom Brewing has begun");

				ItemFrame nframe = Tools.addItemFrame(block, BlockFace.NORTH);
				Tools.addItemFrame(block, BlockFace.SOUTH);
				Tools.addItemFrame(block, BlockFace.EAST);
				Tools.addItemFrame(block, BlockFace.WEST);
				nframe.setItem(new ItemStack(Material.POTION, 1, (short) 69));
				custCauld.setCurrentStep(1);
			}
		}

		else if (inhand.getType() == Material.BONE && custCauld != null) // Handle
																			// all
																			// stiring
																			// stuff
		{
			// Do stuff for stiring
			int stirs = custCauld.getStirs();
			int level = JobsListener.getJobLevel(player.getName(), "WitchDoctor");
			if (stirs == 2) {
				player.sendMessage(ChatColor.RED + "You cannot stir anymore.");
			} else if ((stirs == 0 && level >= STIR_LEVEL1) || (stirs == 1 && level >= STIR_LEVEL2)) {
				player.sendMessage(ChatColor.GOLD + "You begin stirring the potion");
				cleanUpSigns(custCauld);

				if (stirs == 0) {
					custCauld.setCurrentStep(5);
				} else if (stirs == 1) {
					custCauld.setCurrentStep(9);
				}

				custCauld.setStirs(stirs + 1);

				// IMPORTANT: Fill any excess spots with air
				ArrayList<Material> mats = custCauld.getMats();
				while (mats.size() < (3 * custCauld.getStirs())) {
					custCauld.addMat(Material.AIR);
				}

			} else {
				player.sendMessage(ChatColor.RED + "You must be a " + ChatColor.GOLD + "WitchDoctor " + ChatColor.RED
						+ "level " + ChatColor.GOLD + STIR_LEVEL1 + ChatColor.RED + " to stir once, and level "
						+ ChatColor.GOLD + STIR_LEVEL2 + ChatColor.RED + " to stir twice.");
			}
		}

		else if (inhand.getType() == Material.GLASS_BOTTLE && custCauld != null) // Give
																					// the
																					// player
																					// a
																					// potion
		{

			int step = custCauld.getCurrentStep();
			if (step != 0) {
				if (!custCauld.hasValidPotion()) {
					player.sendMessage(ChatColor.RED + "Invalid potion type.");
					custCauld.dispenseItems();
					custCauld.remove();
					return;
				}

				int refills = custCauld.getRefills();
				// If this is the last time that they can refill their potion
				// bottle

				if (refills >= MAX_REFILLS) {
					// Redundancy check
					removeCustomCauldron(custCauld.getLocation());
					return;
				}

				else if (refills < MAX_REFILLS) {
					// Generate the potion
					player.sendMessage(ChatColor.AQUA + "Potion brewing successfully.");
					ItemStack potion = custCauld.generatePotion();
					if (potion != null) {
						if (inv.contains(new ItemStack(Material.POTION, 1, (short) 0))) {
							inv.remove((new ItemStack(Material.POTION, 1, (short) 0)));
						}
						player.getInventory().addItem(potion);
					}
				}

				if (refills == MAX_REFILLS - 1) {
					// Clean up the cauldron
					removeCustomCauldron(custCauld.getLocation());
					player.sendMessage(ChatColor.RED + "The potion has been diluted.");
					return;
				}
				custCauld.setRefills(refills + 1);
				return;
			}
		} else if (custCauld != null) // Attempt to add whatever item they have
										// into the cauldron
		{

			int step = custCauld.getCurrentStep();
			// Check to make sure they are on one of the item add steps
			if (step % 4 != 0) {
				if (!BrewList.isValidIngredient(inhand.getType())) {
					player.sendMessage(ChatColor.RED + "Invalid Ingredient");
					return;
				} else if (!BrewList.canUseIngredient(player, inhand.getType())) {
					BrewIngredient bi = BrewList.getIngredient(inhand.getType());
					player.sendMessage(ChatColor.RED + "You must be a level " + ChatColor.GOLD + bi.getLevel()
							+ " WitchDoctor " + ChatColor.RED + "to use this ingredient.");

				} else {
					updateSignIngredient(custCauld, inhand);
					custCauld.addMat(inhand.getType());

					if (inhand.getAmount() == 1) {
						inv.setItemInHand(new ItemStack(Material.AIR));
					} else {
						inhand.setAmount(inhand.getAmount() - 1);
					}

					custCauld.setCurrentStep(step + 1);

					player.sendMessage(ChatColor.BLUE + "Adding Ingredient");

					if (custCauld.hasValidPotion()) {
						JobsListener.addJobExp(player.getName(), "WitchDoctor", INGREDIENT_EXP);
					}
				}
			} else {
				player.sendMessage(ChatColor.GOLD + "Fill empty bottles with this potion.");
				return;
			}
		}

		// If another person clicks the cauldron
		// If the cauldron is not null and they have a netherwart
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onItemFrameBreak(HangingBreakEvent event) {
		// Check to see if the attached item is on a Cauldron. If it is then
		// cancel the event.
		if (event.isCancelled()) {
			return;
		}

		Hanging han = event.getEntity();
		Block hangBlock = han.getLocation().getWorld().getBlockAt(han.getLocation()).getRelative(han.getAttachedFace());
		if (hangBlock.getType() == Material.CAULDRON) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onItemFrameRightClick(PlayerInteractEntityEvent event) {
		// IF the player tries to add an item into a cauldron itemframe then
		// cancel the event.
		if (event.isCancelled()) {
			return;
		}
		Entity ent = event.getRightClicked();

		if (ent.getType() == EntityType.ITEM_FRAME) {
			if (ent instanceof Hanging) {
				Hanging han = (Hanging) ent;
				Block hangBlock = han.getLocation().getWorld().getBlockAt(han.getLocation())
						.getRelative(han.getAttachedFace());
				if (hangBlock.getType() == Material.CAULDRON) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onCauldronBreak(BlockBreakEvent event) {
		/*
		 * This will handle all information regarding cauldron breaking. When a
		 * cauldron breaks it should have its own itemframes removed but not the
		 * itemframes of any next to it. Do not add a check to see if it is only
		 * a custom cauldron, we need to check every cauldron incase one of the
		 * cauldrons is not inside of the list anymore.
		 */
		if (event.isCancelled()) {
			return;
		}

		Block block = event.getBlock();
		if (block.getType() == Material.CAULDRON) {
			// Check to make sure that the cauldron had all the frames, and was
			// not just
			// a cauldron placed next to another cauldron.
			List<Entity> nbFrames = Tools.getNearbyEntity(block.getLocation(), EntityType.ITEM_FRAME, 1.5);

			if (nbFrames.size() > 2) {
				Tools.removeNearbyEntity(block.getLocation(), EntityType.ITEM_FRAME, 1.5);
			}

			if (cauldList.containsKey(block.getLocation())) {
				removeCustomCauldron(block.getLocation());
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onCauldronPlace(BlockPlaceEvent event) {
		/*
		 * Check to make sure they are not placing this cauldron next to an
		 * already existing custom cauldron.
		 */
		if (event.isCancelled()) {
			return;
		}
		Material type = event.getBlock().getType();
		if (type != Material.CAULDRON && !type.toString().toLowerCase().contains("block")) {
			return;
		}
		Block cauld = event.getBlock();
		for (Location loc : locationList) {
			if (Tools.getDistanceSquared(cauld.getLocation(), loc) < Math.pow(2, 2)) {
				Player player = event.getPlayer();
				if (type == Material.CAULDRON) {
					player.sendMessage(
							ChatColor.RED + "You cannot place a cauldron next to an active brewing cauldron.");
				}
				event.setCancelled(true);
			}
		}
	}

	public static CustomCauldron getCustomCauldron(Location loc) {
		if (cauldList.containsKey(loc)) {
			return cauldList.get(loc);
		}
		return null;
	}

	public void updateSignIngredient(CustomCauldron custCauld, ItemStack istack) {
		int modStep = custCauld.getCurrentStep() % 4;
		if (modStep == 0) {
			return;
		}

		ItemFrame frame;
		if (modStep == 1) {
			frame = Tools.getFaceItemFrame(custCauld.getLocation(), BlockFace.EAST);
		} else if (modStep == 2) {
			frame = Tools.getFaceItemFrame(custCauld.getLocation(), BlockFace.SOUTH);
		} else {
			frame = Tools.getFaceItemFrame(custCauld.getLocation(), BlockFace.WEST);
		}

		if (frame != null) {
			frame.setItem(new ItemStack(istack.getType()));
		}
	}

	public void cleanUpSigns(CustomCauldron custCauld) {
		ItemFrame eframe = Tools.getFaceItemFrame(custCauld.getLocation(), BlockFace.EAST);
		ItemFrame wframe = Tools.getFaceItemFrame(custCauld.getLocation(), BlockFace.WEST);
		ItemFrame sframe = Tools.getFaceItemFrame(custCauld.getLocation(), BlockFace.SOUTH);
		eframe.setItem(new ItemStack(Material.AIR));
		wframe.setItem(new ItemStack(Material.AIR));
		sframe.setItem(new ItemStack(Material.AIR));
	}

	public static void removeCustomCauldron(Location loc) {
		if (cauldList.containsKey(loc)) {
			CustomCauldron custCauld = cauldList.get(loc);
			custCauld.remove();
			locationList.remove(loc);
		}
	}

	public static void removeAllCauldrons() {
		// Cycles through the list of active custom cauldrons and removes them.
		for (int i = 0; i < locationList.size(); i++) {
			removeCustomCauldron(locationList.get(i));
			locationList.remove(i);
		}
	}

	public static void removeGlobalCauldrons(String worldName) {
		/*
		 * Use this method in the case of crashes which would not allow for all
		 * the item frames to be removed from custom cauldrons. By using this it
		 * will search through all server item frames and check if there are any
		 * cauldrons nearby. It will remove itemframes from the cauldron.
		 */
		World world = Bukkit.getWorld(worldName);
		List<Entity> wents = world.getEntities();
		for (int i = 0; i < wents.size(); i++) {
			Entity ent = wents.get(i);
			if (ent.getType() == EntityType.ITEM_FRAME) {
				Block block = world.getBlockAt(ent.getLocation());
				if (block.getType() == Material.DISPENSER) {
					Tools.removeNearbyEntity(block.getLocation(), EntityType.ITEM_FRAME, 1.5);
				}
			}
		}
	}

	public static boolean hasCustomCauldron(String playerName) {
		// Checks to see if the player already has an active custom cauldron.
		for (int i = 0; i < locationList.size(); i++) {
			CustomCauldron custCauld = getCustomCauldron(locationList.get(i));
			if (custCauld != null) {
				if (custCauld.getPlayerName().equals(playerName)) {
					return true;
				}
			}
		}
		return false;
	}

}
