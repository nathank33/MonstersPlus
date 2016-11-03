package me.coolade.jobsplus.customenchanting;

import me.coolade.jobsplus.JobsListener;
import me.coolade.jobsplus.customenchanting.CustomEnchantment.EnchantmentSet;
import me.coolade.monstersplus.Tools;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CustomEnchantCommand {
	public static CustomEnchantment enchantmentList = new CustomEnchantment();

	public CustomEnchantCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("customenchant")) {
			if (args.length == 0) {
				displayUsage(sender);
				return;
			} else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
				sender.sendMessage(ChatColor.AQUA + "Listing all of the enchantments:");
				displayEnchantments(sender);
				return;
			} else if (args.length > 1 && args[0].equalsIgnoreCase("list")) {
				sender.sendMessage(ChatColor.AQUA + "Listing the enchantments for the job " + ChatColor.GOLD + args[1]
						+ ChatColor.AQUA + ":");
				displayEnchantmentsJob(sender, args[1]);
				return;
			} else if (args.length > 0 && args[0].equalsIgnoreCase("info")) {
				if (args.length > 1) {
					String ench = args[1];
					if (args.length > 2) {
						ench = ench + " " + args[2];
					}
					displayInfo(sender, ench);
					return;
				} else {
					displayUsage(sender);
					return;
				}
			} else if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Only players can use this command.");
				return;
			} else if (args.length > 1 && args[0].equalsIgnoreCase("powerstones")) {
				if (sender.isOp()) {
					int amount = Integer.parseInt(args[1]);
					try {
						Tools.spawnPowerstone(((Player) sender).getLocation(), amount);
					} catch (Exception e) {
					}
				}
				return;
			} else if (args.length > 0 && args[0].equalsIgnoreCase("available")) {
				displayAvailable((Player) sender);
				return;
			}

			Player player = (Player) sender;
			String ench = args[0];
			int enchLevel = 1;
			if (args.length > 1) {
				// Examples: /customenchant lifesteal 1
				// /customenchant ice shot 1
				// We have to do some checks to see if we need to parse the
				// second or third argument
				try {
					if (args.length == 2) {
						if (Character.isDigit(args[1].charAt(0))) {
							enchLevel = Integer.parseInt(args[1]);
						} else {
							ench = ench + " " + args[1];
						}
					}
					if (args.length > 2) {
						enchLevel = Integer.parseInt(args[2]);
						ench = ench + " " + args[1];

					}
				} catch (Exception e) {
				}
				;
				if (enchLevel < 1 && !player.isOp()) {
					player.sendMessage(ChatColor.RED + "" + enchLevel + " is an invalid enchantment level.");
					return;
				}
			}
			tryToAddCustomEnchantment(player, ench, enchLevel);
		}
	}

	public void displayUsage(CommandSender sender) {
		sender.sendMessage(ChatColor.AQUA + "Custom Enchant");
		sender.sendMessage(ChatColor.GOLD + "Info: " + ChatColor.WHITE + "Custom Enchantments are given based on "
				+ "job levels. Custom enchants usually require a certain amount of powerstones to use. "
				+ "Some enchantments can only be used on specific items, such as swords or bows. "
				+ "Moreover, some enchantments conflict with other enchantments.");
		sender.sendMessage(
				ChatColor.GOLD + "/customenchant list: " + ChatColor.WHITE + "display all of the custom enchantments.");
		sender.sendMessage(ChatColor.GOLD + "/customenchant list <jobname>: " + ChatColor.WHITE
				+ "display all of the custom enchantments for a specific job.");
		sender.sendMessage(ChatColor.GOLD + "/customenchant available: " + ChatColor.WHITE
				+ "display enchantments that you have access to.");
		sender.sendMessage(ChatColor.GOLD + "/customenchant info <enchantname>: " + ChatColor.WHITE
				+ "display more information about a specific enchantment.");
		sender.sendMessage(ChatColor.GOLD + "/customenchant <enchantname> (#): " + ChatColor.WHITE
				+ "Enchant the item in your hand with a specific custom enchantment.");
		sender.sendMessage(ChatColor.GOLD + "For example: " + ChatColor.RED + "/customenchant available");
		sender.sendMessage(ChatColor.GOLD + "For example: " + ChatColor.RED + "/customenchant info Retina");
		sender.sendMessage(ChatColor.GOLD + "For example: " + ChatColor.RED + "/customenchant Lifesteal 2");
		sender.sendMessage(ChatColor.GOLD + "For example: " + ChatColor.RED + "/customenchant Ice Shot");
	}

	public void displayInfo(CommandSender sender, String enchant) {
		EnchantmentSet eset = enchantmentList.getEnchantmentSet(enchant);
		if (eset == null) {
			sender.sendMessage(ChatColor.RED + enchant + " is an invalid custom enchantment");
			return;
		}

		sender.sendMessage(ChatColor.AQUA + eset.getName() + ChatColor.WHITE + "  Max Rank: " + eset.getMaxLevel());
		sender.sendMessage(ChatColor.GOLD + "  " + eset.getDescription());
		sender.sendMessage(ChatColor.GRAY + "Enchanters can also create this enchantment.");
		// Create this appended string to display all the available ranks
		int[] levels = eset.getJobLevels();
		for (int i = 0; i < levels.length; i++) {
			sender.sendMessage(ChatColor.GOLD + "  Rank " + (i + 1) + ChatColor.WHITE + ": " + eset.getJobName()
					+ " level " + levels[i] + ", Exp Cost: " + eset.getExpsRequired()[i] + ", Powerstone cost: "
					+ eset.getPowerstones()[i]);
		}
		String conflict = "None";
		if (eset.getConflictingEnchantments() != null) {
			conflict = "";
			String[] conflictingEnchs = eset.getConflictingEnchantments();
			for (int i = 0; i < conflictingEnchs.length; i++) {
				conflict += conflictingEnchs[i];
				conflict += " ";
			}
		}
		sender.sendMessage(ChatColor.GOLD + "Conflicting Enchantments: " + ChatColor.WHITE + conflict);
	}

	public void displayEnchantments(CommandSender sender) {
		for (EnchantmentSet eset : enchantmentList.getEnchantmentList()) {
			sender.sendMessage(ChatColor.GOLD + eset.getName() + ": " + ChatColor.WHITE + eset.getJobName());
		}
	}

	public void displayEnchantmentsJob(CommandSender sender, String jobName) {
		for (EnchantmentSet eset : enchantmentList.getEnchantmentList()) {
			if (eset.getJobName().equalsIgnoreCase(jobName)) {
				sender.sendMessage(ChatColor.GOLD + eset.getName() + ": " + ChatColor.WHITE + eset.getJobName());
			}
		}
	}

	public void displayAvailable(Player player) {
		player.sendMessage(ChatColor.AQUA + "Available Enchantments");
		for (EnchantmentSet eset : enchantmentList.getEnchantmentList()) {
			int[] levels = eset.getJobLevels();
			int jobLevel = JobsListener.getJobLevel(player.getName(), eset.getJobName());
			int enchJobLevel = JobsListener.getJobLevel(player.getName(), "Enchanter");

			String availableRanks = "";
			for (int i = 0; i < levels.length; i++) {
				if (jobLevel >= levels[i] || enchJobLevel >= levels[i]) {
					availableRanks = availableRanks + (i + 1) + " ";
				}
			}

			if (!availableRanks.equals("")) {
				player.sendMessage(ChatColor.GOLD + eset.getName() + ": " + ChatColor.WHITE + availableRanks);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public boolean tryToAddCustomEnchantment(Player player, String enchName, int level) {
		if (enchName == null) {
			return false;
		}

		EnchantmentSet eset = enchantmentList.getEnchantmentSet(enchName);
		if (eset == null) {
			player.sendMessage(ChatColor.RED + "Could not find the enchantment: " + ChatColor.GREEN + enchName);
			return false;
		} else if (level > eset.getMaxLevel() || level < 1) {
			player.sendMessage(ChatColor.RED + "Invalid enchantment level.");
			return false;
		}

		if (!player.isOp()) {
			if (JobsListener.getJobLevel(player.getName(), eset.getJobName()) < eset.getJobLevels()[level - 1]
					&& JobsListener.getJobLevel(player.getName(), "Enchanter") < eset.getJobLevels()[level - 1]) {
				player.sendMessage(ChatColor.RED + "You must be a " + ChatColor.GOLD + eset.getJobName()
						+ " or Enchanter " + ChatColor.RED + "level " + ChatColor.GOLD + eset.getJobLevels()[level - 1]
						+ ChatColor.RED + " to use " + eset.getName() + " " + level + ".");
				return false;
			} else if (player.getLevel() < eset.getExpsRequired()[level - 1]) {
				player.sendMessage(ChatColor.RED + "This enchantment costs " + ChatColor.GOLD
						+ eset.getExpsRequired()[level - 1] + ChatColor.RED + " experience, you have " + ChatColor.GOLD
						+ player.getLevel() + ChatColor.RED + ".");
				return false;
			} else if (Tools.getAmountOfPowerstones(player) < eset.getPowerstones()[level - 1]) {
				player.sendMessage(ChatColor.RED + "This enchantment costs " + ChatColor.GOLD
						+ eset.getPowerstones()[level - 1] + ChatColor.RED + " powerstones, you only have "
						+ ChatColor.GOLD + Tools.getAmountOfPowerstones(player) + ChatColor.RED + ".");
				return false;
			}
		}

		PlayerInventory inv = player.getInventory();
		ItemStack inhand = inv.getItemInHand();
		if (!player.isOp()) {
			if (!enchantmentList.isValidEnchantType(inhand, eset)) {
				player.sendMessage(ChatColor.RED + "You cannot put this enchantment onto that item.");
				return false;
			} else if (!enchantmentList.hasNoConflictingEnchantments(inhand, eset)) {
				player.sendMessage(ChatColor.RED + "This enchantment conflicts with your current enchantments.");
				return false;
			} else if (inhand.getAmount() > 1) {
				player.sendMessage(ChatColor.RED + "You can only enchant 1 item at a time.");
				return false;
			}
		}

		Tools.removePowerstones(player, eset.getPowerstones()[level - 1]);
		player.setLevel(player.getLevel() - eset.getExpsRequired()[level - 1]);
		ItemStack newItem = enchantmentList.addCustomEnchantment(inhand, eset.getName(), level);
		if (newItem != null) {
			inv.setItemInHand(newItem);
		}
		player.sendMessage(ChatColor.AQUA + "Enchant Successful");

		return true;
	}

}
