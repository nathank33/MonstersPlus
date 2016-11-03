package me.coolade.monstersplus.tasks;

import java.util.ArrayList;

import me.coolade.monstersplus.MonstersPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class VortexCreepTask {
	private String message = ChatColor.RED + "Vortex Creep begins to power up!";
	private int hradius = 13;
	private int vradius = 6;
	private int pullAmount = 10;
	private int repeatDelay = 20;
	private double pullPower = 0.36;
	private LivingEntity lent;

	public VortexCreepTask(LivingEntity lent, int hradius, int vradius, int pullAmount, int startDelay, int repeatDelay,
			double pullPower, String message) {
		this.lent = lent;
		this.hradius = hradius;
		this.vradius = vradius;
		this.pullAmount = pullAmount;
		this.repeatDelay = repeatDelay;
		this.pullPower = pullPower;
		this.message = message;
		if (message != null) {
			MonstersPlus.messageNearbyPlayers(lent.getLocation(), message, hradius, vradius, hradius);
		}

		for (int i = 0; i < pullAmount; i++) {
			startVortex(i * repeatDelay + startDelay, pullPower, lent, hradius, vradius);
		}
	}

	public VortexCreepTask(LivingEntity lent, int hradius, int vradius, int pullAmount, int startDelay, int repeatDelay,
			double pullPower) {
		this(lent, hradius, vradius, pullAmount, startDelay, repeatDelay, pullPower, null);
	}

	public void startVortex(int delay, final double power, final LivingEntity le, final int hradius,
			final int vradius) {
		MonstersPlus.plugin.getServer().getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new Runnable() {
			@Override
			public void run() {
				if (!lent.isDead()) {
					ArrayList<String> playerNames = MonstersPlus.getNearbyPlayers(le.getLocation(), hradius, vradius,
							hradius);
					for (int i = 0; i < playerNames.size(); i++) {
						Player player = Bukkit.getPlayer(playerNames.get(i));
						MonstersPlus.pullEntity(player, le.getLocation(), power);
					}
				}
			}
		}, delay);
	}

	public boolean checkHeightDifference(Location loc1, Location loc2) {
		if (!loc1.getWorld().equals(loc2.getWorld())) {
			return false;
		}

		if (Math.abs(loc1.getY() - loc2.getY()) < vradius) {
			return true;
		}
		return false;
	}
}
