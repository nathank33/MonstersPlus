package me.coolade.monstersplus.monsters;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.coolade.monstersplus.monsters.MonsterList.MonsterStruct;
import me.coolade.monstersplus.monsters.monsterevents.CreeperSpawnEvent;
import me.coolade.monstersplus.monsters.monsterevents.HorseSpawnEvent;
import me.coolade.monstersplus.monsters.monsterevents.SkeletonSpawnEvent;
import me.coolade.monstersplus.monsters.monsterevents.SpiderSpawnEvent;
import me.coolade.monstersplus.monsters.monsterevents.ZombieSpawnEvent;

public class MonsterSpawnCommand {
	private final int MAXDISTANCE = 50;

	@SuppressWarnings("deprecation")
	public MonsterSpawnCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player) && args.length < 4) {
			sender.sendMessage(ChatColor.RED + "Only players can use this command.");
			return;
		}

		// Player player = (Player)sender;
		if (commandLabel.equalsIgnoreCase("customspawn")) {
			if (!sender.isOp() && !sender.hasPermission("monsersplus.customspawn")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
				return;
			} else if (args.length < 1) {
				sender.sendMessage(ChatColor.RED + "Not enough arguments.");
				return;
			} else {
				String mobName = args[0];
				mobName = mobName.replace('+', ' '); // Used for spaced name
														// concatenation
				int amount = 1;
				double percentage = 100;

				try {
					if (args.length > 1) {
						amount = Integer.parseInt(args[1]);
					}
					if (args.length > 2) {
						percentage = Double.parseDouble(args[2]);
					}
				} catch (NumberFormatException nfe) {
					// player.sendMessage(ChatColor.RED + "Invalid numbers
					// specified for amount or health percentage.");
					// return;
				}

				// Spawn the monster based on type.
				Location loc = null;
				if (sender instanceof Player) {
					loc = ((Player) sender).getTargetBlock(new HashSet<Byte>(), MAXDISTANCE).getLocation();
				}
				if (args.length > 3) {
					try {
						Player player2 = Bukkit.getPlayer(args[3]);
						if (player2 != null) {
							loc = player2.getLocation();
						} else {
							String[] commas = args[3].split(",");
							if (commas.length == 4) {
								World world = Bukkit.getWorld(commas[0]);
								if (world != null) {
									loc = new Location(world, Double.parseDouble(commas[1]),
											Double.parseDouble(commas[2]), Double.parseDouble(commas[3]));
								}
							}
						}
					} catch (Exception e) {
					}
					if (loc == null) {
						sender.sendMessage("Invalid Usage");
						return;
					}
				}
				loc = loc.add(0, 3, 0);
				MonsterStruct mstru = MonsterList.getMonster(mobName);
				if (mstru == null) {
					sender.sendMessage(ChatColor.RED + "Monster not found.");
					return;
				}

				mobName = mstru.getName();
				EntityType entType = mstru.getMonsterType();
				for (int i = 0; i < amount; i++) {
					LivingEntity lent = spawnCustomMonster(mobName, entType, loc);
					lent.setHealth(lent.getHealth() * (percentage / 100));
				}

				sender.sendMessage(ChatColor.AQUA + "Monster successfully spawned.");
			}
		} else {

		}
	}

	public static LivingEntity spawnCustomMonster(String mobName, EntityType entType, Location loc) {
		LivingEntity lent = (LivingEntity) loc.getWorld().spawnEntity(loc, entType);

		if (entType.equals(EntityType.SKELETON)) {
			new SkeletonSpawnEvent(lent, mobName);
		} else if (entType.equals(EntityType.ZOMBIE)) {
			ZombieSpawnEvent event = new ZombieSpawnEvent(lent, mobName);
			lent = event.getLent();
		} else if (entType.equals(EntityType.CREEPER)) {
			new CreeperSpawnEvent(lent, mobName);
		} else if (entType.equals(EntityType.HORSE)) {
			new HorseSpawnEvent(lent, mobName);
		} else {
			SpiderSpawnEvent event = new SpiderSpawnEvent(lent, mobName);
			lent = event.getLent();
		}
		return lent;
	}

	public static LivingEntity spawnRandomMonster(Location loc, int rank) {
		String mobName = MonsterList.getRandomMonsterRank(rank);
		MonsterStruct mstru = MonsterList.getMonster(mobName);
		return spawnCustomMonster(mobName, mstru.getMonsterType(), loc);
	}
}
