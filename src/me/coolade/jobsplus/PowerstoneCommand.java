package me.coolade.jobsplus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import me.coolade.monstersplus.Tools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class PowerstoneCommand {
	public static final int UNENCHANTPSTONES = 2;
	public static final int ENCHANTPSTONES = 1;

	public static final int REPAIR_BSMITH_LEVEL = 2;
	public static final int REPAIRALL_BSMITH_LEVEL = 8;
	public static final int REPAIR_PSTONES = 1;
	public static final int REPAIRALL_PSTONES = 3;
	public final int EXPAMOUNT = 100;

	@SuppressWarnings("deprecation")
	public PowerstoneCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (commandLabel.toLowerCase().contains("powerstone")) {
			if (args.length == 0) {
				displayUsage(sender);
				return;
			}

			/**
			 * /Powerstone enchant, Use a powerstone to give the player a random
			 * enchantment on the item that they are currently weilding.
			 */
			else if (args.length > 0 && args[0].equalsIgnoreCase("enchant")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
					return;
				}

				final int MAXTRIES = 75;
				Player player = (Player) sender;
				PlayerInventory inv = player.getInventory();
				ItemStack inhand = inv.getItemInHand();

				if (player.getWorld().getName().toLowerCase().contains("sky")) {
					sender.sendMessage(ChatColor.RED + "You cannot use this command in this world.");
					return;
				} else if (inhand == null) {
					sender.sendMessage(ChatColor.RED + "Invalid Item");
					return;
				} else if (Tools.getAmountOfPowerstones(player) < ENCHANTPSTONES) {
					sender.sendMessage(ChatColor.RED + "You do not have enough powerstones to use this command.");
					return;
				} else if (inhand.getType().equals(Material.BOOK)) {
					sender.sendMessage(ChatColor.RED + "This command does not work with books.");
					return;
				}

				Random rand = new Random(System.currentTimeMillis());
				Enchantment[] enchantAr = Enchantment.values();

				for (int i = 0; i < MAXTRIES; i++) {
					try {
						Enchantment randEnch = enchantAr[rand.nextInt(enchantAr.length)];
						if (randEnch.canEnchantItem(inhand)) {
							int level = rand.nextInt(randEnch.getMaxLevel());
							inhand.addEnchantment(randEnch, level);
							player.sendMessage(ChatColor.AQUA + "Random Enchantment was successful.");
							Tools.removePowerstones(player, ENCHANTPSTONES);
							return;
						}
					} catch (IllegalArgumentException iae) {
					}
				}

				// If it gets to this point then we know the loops were
				// unsuccessful.
				player.sendMessage(ChatColor.RED + "Random Enchantment failed with this item.");
				return;
			} else if (args.length > 0 && args[0].equalsIgnoreCase("exp")) {

				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
					return;
				} else if (args.length == 1) {
					sender.sendMessage(ChatColor.RED + "Invalid usage: /powerstone exp <jobname>");
					return;
				}

				Player player = (Player) sender;
				String jobName = args[1];
				if (player.getWorld().getName().toLowerCase().contains("sky")) {
					sender.sendMessage(ChatColor.RED + "You cannot use this command in this world.");
					return;
				} else if (!JobsListener.isInJob(player.getName(), jobName)) {
					player.sendMessage(ChatColor.RED + "Invalid Jobname, or you are not in that job.");
					return;
				} else if (Tools.getAmountOfPowerstones(player) < 1) {
					sender.sendMessage(ChatColor.RED + "You do not have enough powerstones to use this command.");
					return;
				} else {
					// Give them the exp
					JobsListener.addJobExp(player.getName(), jobName, EXPAMOUNT);
					Tools.removePowerstones(player, 1);
					player.sendMessage(ChatColor.AQUA + "Exp was transfered Successfully.");
				}
			} else if (args.length > 0 && args[0].equalsIgnoreCase("unenchant")) {
				/**
				 * /powerstone unenchant: allows the player to randomly remove
				 * an enchant from their item and put it into a book.
				 */

				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
					return;
				}

				Player player = (Player) sender;
				PlayerInventory inv = player.getInventory();
				ItemStack inhand = inv.getItemInHand();

				if (inhand == null) {
					sender.sendMessage(ChatColor.RED + "Invalid Item");
					return;
				} else if (Tools.getAmountOfPowerstones(player) < UNENCHANTPSTONES) {
					sender.sendMessage(ChatColor.RED + "You do not have enough powerstones to use this command.");
					return;
				}

				Map<Enchantment, Integer> enchMap = inhand.getEnchantments();
				Enchantment[] enchAr = enchMap.keySet().toArray(new Enchantment[] {});
				if (enchAr.length < 1) {
					sender.sendMessage(
							ChatColor.RED + "This item does not have any Enchantments that can be Unenchanted.");
					return;
				}

				List<Enchantment> enchList = Arrays.asList(enchAr);
				Collections.shuffle(enchList);
				Enchantment ench = enchList.get(0);
				int level = enchMap.get(ench);
				inhand.removeEnchantment(ench);

				ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
				EnchantmentStorageMeta esm = (EnchantmentStorageMeta) book.getItemMeta();
				esm.addStoredEnchant(ench, level, true);
				book.setItemMeta(esm);
				inv.addItem(book);
				Tools.removePowerstones(player, UNENCHANTPSTONES);
				player.sendMessage(ChatColor.AQUA + "Unenchanting Successful");
			} else if (args.length > 0 && args[0].equalsIgnoreCase("repair")) {
				/**
				 * /powerstone repair: Repairs the item in your hand at the
				 * price of a powerstone, must be a certain blacksmith level.
				 */

				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
					return;
				}

				Player player = (Player) sender;
				PlayerInventory inv = player.getInventory();
				ItemStack inhand = inv.getItemInHand();
				if (JobsListener.getJobLevel(player.getName(), "Blacksmith") < REPAIR_BSMITH_LEVEL) {
					sender.sendMessage(
							ChatColor.RED + "You must be a " + ChatColor.GOLD + "blackmith" + ChatColor.RED + " level "
									+ ChatColor.GOLD + REPAIR_BSMITH_LEVEL + ChatColor.RED + " to use this ability.");
					return;
				} else if (inhand == null) {
					sender.sendMessage(ChatColor.RED + "Invalid Item");
					return;
				} else if (Tools.getAmountOfPowerstones(player) < REPAIR_PSTONES) {
					sender.sendMessage(ChatColor.RED + "You do not have enough powerstones to use this command.");
					return;
				}

				if (inhand.getDurability() == 0 || inhand.getType().getMaxDurability() == 0) {
					sender.sendMessage(ChatColor.RED + "The item in your hand cannot be repaired.");
					return;
				}

				inhand.setDurability((short) 0);
				Tools.removePowerstones(player, REPAIR_PSTONES);
				player.sendMessage(ChatColor.AQUA + "Repairing Successful");
			} else if (args.length > 0 && args[0].equalsIgnoreCase("repairall")) {
				/**
				 * /powerstone repairall: Repairs the item in your hand and all
				 * of your armor for a certain amount of powerstones.
				 */

				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
					return;
				}

				Player player = (Player) sender;
				PlayerInventory inv = player.getInventory();
				ItemStack inhand = inv.getItemInHand();
				if (JobsListener.getJobLevel(player.getName(), "Blacksmith") < REPAIRALL_BSMITH_LEVEL) {
					sender.sendMessage(ChatColor.RED + "You must be a " + ChatColor.GOLD + "blackmith" + ChatColor.RED
							+ " level " + ChatColor.GOLD + REPAIRALL_BSMITH_LEVEL + ChatColor.RED
							+ " to use this ability.");
					return;
				} else if (inhand == null) {
					sender.sendMessage(ChatColor.RED + "Invalid Item");
					return;
				} else if (Tools.getAmountOfPowerstones(player) < REPAIRALL_PSTONES) {
					sender.sendMessage(ChatColor.RED + "You do not have enough powerstones to use this command.");
					return;
				}

				ItemStack[] armor = inv.getArmorContents();
				for (ItemStack arm : armor) {
					if (arm != null) {
						arm.setDurability((short) 0);
					}
				}
				inv.setArmorContents(armor);
				inhand.setDurability((short) 0);
				Tools.removePowerstones(player, REPAIRALL_PSTONES);
				player.sendMessage(ChatColor.AQUA + "Repairing all was Successful");
			} else if (args.length > 0 && args[0].equalsIgnoreCase("give")) {
				/**
				 * If we are trying to give a person a certain amount of
				 * powerstones, either from console or command.
				 */
				Player player;
				int amount;
				try {
					if (sender instanceof Player && !((Player) sender).isOp()) {
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
						return;
					}

					if (args.length == 3) {
						player = Bukkit.getPlayer(args[1]);
						amount = Integer.parseInt(args[2]);
					} else if (args.length == 2) {
						if (!(sender instanceof Player)) {
							sender.sendMessage(ChatColor.RED
									+ "Only players can use this version of the powerstone give command.");
							return;
						} else {
							player = (Player) sender;
							amount = Integer.parseInt(args[1]);
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Invalid command usage.");
						return;
					}

					// Now that we figured out the player and amount, give it to
					// them.
					ItemStack pstones = Tools.getPowerstoneItem();
					pstones.setAmount(amount);
					player.getInventory().addItem(pstones);
					player.sendMessage(ChatColor.AQUA + "You received " + amount + " Powerstones!");
					return;
				} catch (NumberFormatException nfe) {
					sender.sendMessage(ChatColor.RED + "Invalid number.");
					return;
				} catch (NullPointerException npe) {
					sender.sendMessage(ChatColor.RED + "Player not found.");
					return;
				}
			} else {
				displayUsage(sender);
				return;
			}
		}
	}

	public void displayUsage(CommandSender sender) {
		/*
		 * Displays all the information regarding the /powerstone command.
		 */
		sender.sendMessage(ChatColor.AQUA + "Powerstone");
		sender.sendMessage(ChatColor.GOLD + "/powerstone enchant: " + ChatColor.WHITE + "Use " + ENCHANTPSTONES
				+ " powerstone to apply a random leveled enchantment to the item in your hand. " + ChatColor.RED
				+ "- Cost " + ENCHANTPSTONES + " Powerstone");
		sender.sendMessage(
				ChatColor.GOLD + "/powerstone exp <jobname>: " + ChatColor.WHITE + "Convert 1 powerstone into "
						+ EXPAMOUNT + " exp for a specific Job. " + ChatColor.RED + "- Cost 1 Powerstone");
		sender.sendMessage(ChatColor.GOLD + "/powerstone repair: " + ChatColor.WHITE
				+ "Repairs the item that you are currently holding. " + ChatColor.RED + "Blacksmith level "
				+ REPAIR_BSMITH_LEVEL + " - Cost " + REPAIR_PSTONES + " Powerstone.");
		sender.sendMessage(ChatColor.GOLD + "/powerstone repairall: " + ChatColor.WHITE
				+ "Repairs the item that you are currently holding and your 4 slots of armor. " + ChatColor.RED
				+ "Blacksmith level " + REPAIRALL_BSMITH_LEVEL + " - Cost " + REPAIRALL_PSTONES + " Powerstones.");
		sender.sendMessage(ChatColor.GRAY + "   Example: /powerstone exp fisherman");
		sender.sendMessage(ChatColor.GRAY + "   Example: /powerstone exp warrior");
		sender.sendMessage(ChatColor.GOLD + "/powerstone unenchant: " + ChatColor.WHITE
				+ "Randomly removes an enchantment from an item and converts it into a book, does not work with custom enchantments. "
				+ ChatColor.RED + "- Cost " + UNENCHANTPSTONES + " Powerstone");
	}
}
