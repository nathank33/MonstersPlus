package me.coolade.monstersplus.monsters.monsterevents;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.coolade.monstersplus.MonstersPlus;
import me.coolade.monstersplus.monsters.MonsterList;
import me.coolade.monstersplus.monsters.MonsterListener;
import me.coolade.monstersplus.tasks.SpawnTask;

public class BlockBreakSpawnEvent {
	@SuppressWarnings("deprecation")
	public BlockBreakSpawnEvent(BlockBreakEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();
		Location blockLoc = block.getLocation();

		if (!MonstersPlus.isSpawnableLocation(blockLoc)) {
			return;
		}
		if (player.getGameMode() == GameMode.CREATIVE) {
			return;
		}

		if ((block.getType() == Material.DIRT || block.getType() == Material.GRASS)
				&& MonstersPlus.randomChance(0.15)) {
			/** Fire Ant **/
			player.sendMessage(ChatColor.GOLD + "You destroyed a Fire Ant nest!");
			final int MAX_SPAWNS = 8;
			for (int i = 0; i < MAX_SPAWNS; i++) {
				if (MonstersPlus.randomChance(50)) {
					new SpawnTask(10 * i, "Frost Ant", blockLoc);
				}
			}
		} else if (block.getType() == Material.STONE && MonstersPlus.randomChance(0.15)) {
			/** Toxic Ant **/
			player.sendMessage(ChatColor.GOLD + "You destroyed a Toxic Ant nest!");
			final int MAX_SPAWNS = 8;
			for (int i = 0; i < MAX_SPAWNS; i++) {
				if (MonstersPlus.randomChance(50)) {
					new SpawnTask(10 * i, "Toxic Ant", blockLoc);
				}
			}
		} else if ((block.getType() == Material.SNOW_BLOCK || block.getType() == Material.ICE)
				&& MonstersPlus.randomChance(1)) {
			/** Frost Ant **/
			player.sendMessage(ChatColor.YELLOW + "You destroyed a Frost Ant nest!");
			final int MAX_SPAWNS = 8;
			for (int i = 0; i < MAX_SPAWNS; i++) {
				if (MonstersPlus.randomChance(50)) {
					new SpawnTask(10 * i, "Frost Ant", blockLoc);
				}
			}
		} else if (block.getType() == Material.IRON_ORE && MonstersPlus.randomChance(1.2)) {
			/** Iron Guardian **/
			player.sendMessage(ChatColor.GOLD + "An Iron Guardian appears");
			LivingEntity lent = (LivingEntity) blockLoc.getWorld().spawnEntity(blockLoc, EntityType.ZOMBIE);
			Zombie zomb = (Zombie) lent;

			zomb.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, MonsterListener.POTION_DURATION, 1));
			zomb.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MonsterListener.POTION_DURATION, 1));
			// zomb.addPotionEffect(new
			// PotionEffect(PotionEffectType.INCREASE_DAMAGE,EntitySpawnEvent.POTION_DURATION,1));

			lent.setMaxHealth(MonsterList.getMaxHealth("Iron Guardian"));
			lent.setHealth(lent.getMaxHealth());
			zomb.setBaby(false);

			EntityEquipment eeNew = zomb.getEquipment();
			eeNew.setItemInHand(new ItemStack(Material.AIR));
			eeNew.setHelmet(new ItemStack(Material.IRON_ORE));
			eeNew.setLeggings(new ItemStack(Material.AIR));
			eeNew.setChestplate(new ItemStack(Material.AIR));
			eeNew.setBoots(new ItemStack(Material.AIR));
			eeNew.setHelmetDropChance(1F);

			lent.setCustomName(ChatColor.GOLD + "Iron Guardian");
		}
	}
}
