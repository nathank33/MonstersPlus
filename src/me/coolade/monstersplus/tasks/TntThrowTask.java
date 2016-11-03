package me.coolade.monstersplus.tasks;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import me.coolade.monstersplus.MonstersPlus;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@SuppressWarnings("unused")
public class TntThrowTask {

	private static ArrayList<Integer> tntThrowTaskIds = new ArrayList<Integer>();
	private static ConcurrentHashMap<Integer, Integer> idsAndThrowCounter = new ConcurrentHashMap<Integer, Integer>();

	private boolean isValid;
	private LivingEntity lent;
	private int cancelDistance;
	private long delay;
	private long repeatDelay;
	private int maxThrows;
	private int range;
	private int blastTicks;

	@SuppressWarnings("deprecation")
	public TntThrowTask(LivingEntity lent, int range, int cancelDistance, int maxThrows, int blastTicks, long delay,
			long repeatDelay) {
		this.lent = lent;
		this.range = range;
		this.cancelDistance = cancelDistance;
		this.maxThrows = maxThrows;
		this.blastTicks = blastTicks;
		this.delay = delay;
		this.repeatDelay = repeatDelay;

		if (tntThrowTaskIds.contains(lent.getEntityId())) {
			isValid = false;
		} else {
			isValid = true;
			tntThrowTaskIds.add(lent.getEntityId());
			idsAndThrowCounter.put(lent.getEntityId(), 0);
			TaskInitializer task = new TaskInitializer(lent, range, cancelDistance, maxThrows);
			task.setTaskId(
					Bukkit.getScheduler().scheduleSyncRepeatingTask(MonstersPlus.plugin, task, delay, repeatDelay));
		}

	}

	private class TaskInitializer extends BukkitRunnable {
		private int taskId = 0;
		private LivingEntity lent;
		private int cancelDistance;
		private int maxThrows;
		private int range;
		private int blastTicks;

		public TaskInitializer(LivingEntity lent, int range, int cancelDistance, int maxThrows) {
			this.lent = lent;
			this.range = range;
			this.cancelDistance = cancelDistance;
			this.maxThrows = maxThrows;
		}

		@Override
		public void run() {
			try {
				Monster monster = (Monster) lent;
				LivingEntity target = monster.getTarget();
				Location monsterLoc = monster.getLocation();
				Location targetLoc = target.getLocation();

				if (monsterLoc.getWorld() != targetLoc.getWorld()) {
					isValid = false;
				} else {
					double distance = monsterLoc.distance(targetLoc);
					if (!isValid || lent.isDead() || distance > cancelDistance) {
						cancelThisTask();
						return;
					} else if (distance < range && lent.hasLineOfSight(target)
							&& MonstersPlus.isSpawnableLocation(monsterLoc)
							&& MonstersPlus.isSpawnableLocation(targetLoc)) {
						/*
						 * Keep track of the maximum amount of throws allowed by
						 * the task In this case we will need to access the
						 * concurrentHashMap to grab the current throws for this
						 * specific taskId.
						 */

						if (maxThrows > 0) {
							int throwCounter = 0;
							if (idsAndThrowCounter.contains(taskId)) {
								throwCounter = idsAndThrowCounter.get(taskId);
							}
							throwCounter++;
							if (throwCounter < maxThrows) {

								idsAndThrowCounter.put(taskId, throwCounter);
							} else {
								cancelThisTask();
								return;
							}
						} else {
							throwTntAtTarget(monster, target);
						}
					}
				}
			} catch (Exception e) {
			}
		}

		public void setTaskId(int id) {
			this.taskId = id;
		}

		public void cancelThisTask() {
			Bukkit.getScheduler().cancelTask(taskId);
			tntThrowTaskIds.remove(taskId);
			idsAndThrowCounter.remove(taskId);
		}
	}

	public void throwTntAtTarget(LivingEntity lent, LivingEntity target) {
		try {
			Location lentLoc = lent.getLocation();
			lentLoc.setY(lentLoc.getY() + 2);
			Location targetLoc = target.getLocation();

			if (lentLoc.getWorld() == targetLoc.getWorld()) {
				TNTPrimed tnt = lentLoc.getWorld().spawn(lentLoc, TNTPrimed.class);

				Vector lentLocVec = lentLoc.toVector();
				Vector targLocVec = targetLoc.toVector();
				Vector distanceVec = targLocVec.subtract(lentLocVec);
				Vector targetVelocity = target.getVelocity();

				Vector adjustedVec = distanceVec.add(targetVelocity);
				adjustedVec = adjustedVec.multiply(new Vector(0.065, -0.19, 0.065));

				tnt.setVelocity(adjustedVec);
				tnt.setFuseTicks(blastTicks);
			}
		} catch (Exception e) {
		}
	}
}
