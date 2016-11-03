package me.coolade.monstersplus.tasks;

import java.util.ArrayList;

import me.coolade.monstersplus.MonstersPlus;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Snowball;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@SuppressWarnings("unused")
public class ArrowShootTask extends BukkitRunnable {
	public final int MAX_DISTANCE = 80;
	public static ArrayList<Integer> arrowShootTaskIds = new ArrayList<Integer>();
	private LivingEntity lent;
	private boolean isValid;
	private int taskId = 0;
	private int range;
	private double shootSpeed;
	private double runningSpeed;
	private double stoppingSpeed;

	public ArrowShootTask(LivingEntity lent, int range, double shootSpeed, double runningSpeed, double stoppingSpeed) {
		this.lent = lent;
		this.range = range;
		this.shootSpeed = shootSpeed;
		this.runningSpeed = runningSpeed;
		this.stoppingSpeed = stoppingSpeed;

		if (arrowShootTaskIds.contains(lent.getEntityId())) {
			isValid = false;
		} else {
			isValid = true;
			arrowShootTaskIds.add(lent.getEntityId());
		}
	}

	public void setTaskId(int id) {
		this.taskId = id;
	}

	@Override
	public void run() {
		Monster monster = (Monster) lent;
		LivingEntity target = monster.getTarget();
		Location monsterLoc = monster.getLocation();
		Location targetLoc = target.getLocation();

		if (monsterLoc.getWorld() != targetLoc.getWorld()) {
			isValid = false;
		} else {
			double distance = monsterLoc.distance(targetLoc);
			if (!isValid || lent.isDead() || distance > MAX_DISTANCE) {
				Bukkit.getScheduler().cancelTask(taskId);
			} else if (distance < range && lent.hasLineOfSight(target)) {
				// shootArrowAtTarget(monster,target);
			}
		}
	}

	public void shootArrowAtTarget(LivingEntity lent, LivingEntity target) {
		Location lentLoc = lent.getLocation();
		Location targetLoc = target.getLocation();

		if (lentLoc.getWorld() == targetLoc.getWorld()) {

			Snowball snow = lentLoc.getWorld().spawn(lentLoc, Snowball.class);
			snow.setShooter(lent);
			snow.setVelocity(lent.getEyeLocation().getDirection().multiply(2));

			Arrow a = lent.launchProjectile(Arrow.class);

			adjustArrow(a, targetLoc);
		}
	}

	public void adjustArrow(final Arrow arrow, final Location targetLoc) {
		MonstersPlus.plugin.getServer().getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new Runnable() {
			@Override
			public void run() {
				Vector lentLocVec = arrow.getLocation().toVector();
				Vector targLocVec = targetLoc.toVector();
				Vector distanceVec = targLocVec.subtract(lentLocVec);
				arrow.setVelocity(distanceVec);
			}
		}, 1);
	}

}
