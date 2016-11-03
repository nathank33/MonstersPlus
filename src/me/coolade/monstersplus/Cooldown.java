package me.coolade.monstersplus;

import java.util.concurrent.ConcurrentHashMap;

public class Cooldown {
	private static ConcurrentHashMap<String, Long> totalCooldowns = new ConcurrentHashMap<String, Long>();
	private String key;
	private long time;

	public Cooldown(String key, long time) {
		this.key = key;
		this.time = time + System.currentTimeMillis();
		if (totalCooldowns.containsKey(key)) {
			totalCooldowns.remove(key);
		}

		if (time > 0) {
			totalCooldowns.put(key, this.time);
		}
	}

	public Cooldown(String key) {
		this(key, -1);
	}

	public String getKey() {
		return key;
	}

	public long getTime() {
		return time - System.currentTimeMillis();
	}

	public long getCooldownMinutes() {
		return (getTime() / 1000) / 60;
	}

	public long getCooldownSeconds() {
		return (getTime() / 1000) % 60;
	}

	public void setKey(String t) {
		key = t;
	}

	public void setCooldown(long t) {
		time = t;
	}

	@Override
	public String toString() {
		return "Key: " + getKey() + " Cooldown: " + getTime();
	}

	public static long getCooldownSeconds(String key) {
		return (getCooldown(key) / 1000) % 60;
	}

	public static long getCooldownMinutes(String key) {
		return (getCooldown(key) / 1000) / 60;
	}

	public static long getCooldown(String key) {
		if (totalCooldowns.containsKey(key)) {
			return totalCooldowns.get(key) - System.currentTimeMillis();
		}
		return 0;
	}

	public static boolean keyExists(String t) {
		return totalCooldowns.containsKey(t);
	}

	public static boolean cooldownExists(Cooldown cooldown) {
		return cooldownExists(cooldown.getKey());
	}

	public static boolean cooldownExists(String key) {
		if (totalCooldowns.containsKey(key)) {
			long timeRemaining = totalCooldowns.get(key) - System.currentTimeMillis();
			if (timeRemaining <= 0) {
				totalCooldowns.remove(key);
				return false;
			}
			return true;
		}
		return false;
	}
}
