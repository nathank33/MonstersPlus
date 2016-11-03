package me.coolade.monstersplus.tasks;

import me.coolade.monstersplus.MonstersPlus;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("unused")
public class RepeatableLivingEntityTask {
	/**
	 * Creates a repeating task from the perspective of a LivingEntity named
	 * lent. Modify the Run method to adjust what happens, the rest of the code
	 * should remain the same. The InitialDelay and RepeatDelay are in ticks not
	 * milliseconds. Example to create a task: Entity ent = event.getEntity();
	 * if(ent instanceof LivingEntity) { LivingEntity lent = (LivingEntity)ent;
	 * new RepeatableLivingEntityTask(lent, 30, 0, 20); //cancel at 30, no
	 * initial delay, repeat once per second (if 20 tps) return; }
	 */
	private LivingEntity lent;
	private int cancelDistance;

	@SuppressWarnings("deprecation")
	public RepeatableLivingEntityTask(LivingEntity lent, int cancelDistance, long initialDelay, long repeatDelay) {
		this.lent = lent;
		this.cancelDistance = cancelDistance;
		RepeatableLivingEntityTaskHelper task = new RepeatableLivingEntityTaskHelper(lent, cancelDistance);
		task.setTaskId(
				Bukkit.getScheduler().scheduleSyncRepeatingTask(MonstersPlus.plugin, task, initialDelay, repeatDelay));
	}

	private class RepeatableLivingEntityTaskHelper extends BukkitRunnable {
		private int taskId = -1;
		private LivingEntity lent;
		private int cancelDistance;

		public RepeatableLivingEntityTaskHelper(LivingEntity lent, int cancelDistance) {
			this.lent = lent;
			this.cancelDistance = cancelDistance;
		}

		@Override
		public void run() {
			/*
			 * EDIT THIS CODE, the example below assumes that the monster
			 * already has a target before this task was created. If the
			 * monsters target is null than this task will be cancelled.
			 */
			if (!(lent instanceof Monster)) {
				cancelThisTask();
				return;
			}
			Monster monster = (Monster) lent;
			LivingEntity target = monster.getTarget();
			if (target == null) {
				cancelThisTask();
				return;
			}

			Location monsterLoc = monster.getLocation();
			Location targetLoc = target.getLocation();
			if (!monsterLoc.getWorld().equals(targetLoc.getWorld()) || lent.isDead() || target.isDead()) {
				/*
				 * If the target was a player it is possible that the player
				 * could have teleported away while fighting, and if the
				 * locations are in two seperate worlds than it is impossible to
				 * measure the distances.
				 */
				cancelThisTask();
				return;
			}
			double distance = monsterLoc.distance(targetLoc);
			if (distance > cancelDistance) {
				cancelThisTask();
				return;
			}
		}

		public void setTaskId(int id) {
			this.taskId = id;
		}

		public void cancelThisTask() {
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}

}
