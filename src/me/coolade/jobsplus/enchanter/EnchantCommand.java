package me.coolade.jobsplus.enchanter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.coolade.jobsplus.JobsListener;
import me.coolade.monstersplus.Tools;

public class EnchantCommand {
	private static final double MULTIPLIER = 15;

	public EnchantCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		/*
		 * This will handle all information regarding the jobenchant command
		 * format: /jobenchant enchantmentname power
		 */

		String PLAYERS_ONLY = ChatColor.RED + "Only players can use this command";
		String INVALID_ENCHANT = ChatColor.RED
				+ "Invalid enchantment, type /jobenchant to see your available enchantments";
		String INVALID_ITEM = ChatColor.RED + "You can use that enchantment on this item";
		if (commandLabel.equalsIgnoreCase("jobenchant")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(PLAYERS_ONLY);
				return;
			}
			Player player = (Player) sender;
			if (args.length == 0) {
				displayUsage(player);
			} else if (args.length > 0 && args[0].toUpperCase().charAt(0) == 'D') {
				int page = 1;
				if (args.length == 2) {
					try {
						page = Integer.parseInt(args[1]);
					} catch (NumberFormatException e) {
					}
				}
				;

				displayAvailableEnchantments(player, page);
			} else if (args.length == 1) {
				displayUsage(player);
				player.sendMessage(ChatColor.RED + "Please enter the enchantment and the level");
			} else if (args.length > 1) {
				String arg0 = args[0];
				String arg1 = args[1];
				int val;
				try {
					val = Integer.parseInt(arg1);
				} catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Invalid Enchantment.");
					return;
				}
				EnchantRecipe recipe = EnchantList.getEnchantRecipe(arg0, val);
				if (recipe == null) {
					player.sendMessage(INVALID_ENCHANT);
				} else {
					// The enchantment was found now we need to check their
					// level
					String jobName = recipe.getJob();
					int jobLevel = recipe.getJobLevel();
					int playerLevel = JobsListener.getJobLevel(player.getName(), jobName);
					if (playerLevel < jobLevel) {
						player.sendMessage(ChatColor.RED + "This enchantment is only available for a" + ChatColor.AQUA
								+ " level " + jobLevel + " " + jobName);
					}

					else if (player.getLevel() < recipe.getExpCost()) {
						player.sendMessage(ChatColor.RED + "You do not have enough exp for this enchantment, "
								+ "Level : " + player.getLevel() + " Required: " + recipe.getExpCost());
					}

					else {

						PlayerInventory inv = player.getInventory();
						@SuppressWarnings("deprecation")
						ItemStack inhand = inv.getItemInHand();

						if (inhand.getType() == Material.AIR) {
							player.sendMessage(ChatColor.RED + "You must have an item selected to enchant");
						} else {
							try {
								/*
								 * Attempt to add the enchantment onto the item.
								 * Try to add it safely so that it can only be
								 * added the the correct type of items. And also
								 * do an additional enchantment type check to
								 * make sure that the enchantments do not
								 * conflict.
								 */

								Enchantment ench = recipe.getType();
								if (Tools.isConflictingEnchantment(inhand, ench)) {
									player.sendMessage(ChatColor.RED
											+ "This enchantment conflicts with your current enchantments.");
									return;
								}

								inhand.addEnchantment(recipe.getType(), recipe.getLevel());
								player.setLevel(player.getLevel() - recipe.getExpCost());
								JobsListener.addJobExp(player.getName(), "Enchanter", recipe.getExpCost() * MULTIPLIER);
								player.sendMessage(ChatColor.AQUA + "Enchant Successful!");

							} catch (Exception e) {
								player.sendMessage(INVALID_ITEM);
							}
						}
					}
				}
			}
		}
	}

	public void displayUsage(Player player) {
		player.sendMessage(ChatColor.GOLD + "/jobenchant display" + ChatColor.WHITE + ": Display available enchants");
		player.sendMessage(ChatColor.GOLD + "/jobenchant enchantname level" + ChatColor.WHITE
				+ ": To enchant the item in your hand type ");
		player.sendMessage(ChatColor.RED + "For Example:" + ChatColor.GOLD + " /jobenchant protection 1");
		player.sendMessage(ChatColor.GOLD + "             /jobenchant thorns 2");
	}

	public void displayAvailableEnchantments(Player player, int page) {
		/*
		 * Handle all the information regarding the use of /jobenchant display #
		 * Depending on the page is what will enchantments from the list will be
		 * displayed.
		 */

		final int PER_PAGE = 8;
		List<EnchantRecipe> recipes = new ArrayList<EnchantRecipe>();
		for (EnchantRecipe er : EnchantList.ENCHANTLIST) {
			if (JobsListener.getJobLevel(player.getName(), er.getJob()) >= er.getJobLevel()) {
				recipes.add(er);
			}
		}

		// Make sure that we have a valid page so we don't go out of bounds.
		int totalPages = ((recipes.size() / PER_PAGE) + 1);
		if (page > totalPages) {
			page = totalPages;
		}
		if (page < 1) {
			page = 1;
		}

		player.sendMessage(
				ChatColor.RED + "Displaying Enchantments   " + ChatColor.YELLOW + "Page " + page + " of " + totalPages);

		int startPoint = ((page - 1) * (PER_PAGE));
		int endPoint = startPoint + PER_PAGE;
		for (int i = startPoint; i < endPoint; i++) {
			if (i < recipes.size()) {
				EnchantRecipe recipe = recipes.get(i);
				player.sendMessage(ChatColor.GOLD + recipe.getName() + "    " + ChatColor.LIGHT_PURPLE
						+ recipe.getLevel() + ChatColor.AQUA + "    ExpCost: " + recipe.getExpCost());
			} else {
				return;
			}
		}
	}

}
