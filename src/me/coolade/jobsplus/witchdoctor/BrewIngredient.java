package me.coolade.jobsplus.witchdoctor;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class BrewIngredient {
	private Material mat;
	private PotionEffectType effect;
	private String special;
	private int[] durations;
	private int[] amps;
	private int level;
	private String description;

	public BrewIngredient(Material mat, PotionEffectType effect, String special, int[] durations, int[] amps, int level,
			String description) {
		this.mat = mat;
		this.effect = effect;
		this.special = special;
		this.durations = durations;
		this.amps = amps;
		this.level = level;
		this.description = description;
	}

	public BrewIngredient(Material mat, PotionEffectType effect, int[] durations, int[] amps, int level,
			String description) {
		this(mat, effect, null, durations, amps, level, description);
	}

	public BrewIngredient(Material mat, String special, int[] durations, int[] amps, int level, String description) {
		this(mat, null, special, durations, amps, level, description);
	}

	public Material getMat() {
		return mat;
	}

	public PotionEffectType getEffect() {
		return effect;
	}

	public String getSpecial() {
		return special;
	}

	public int[] getDurations() {
		return durations;
	}

	public int[] getAmps() {
		return amps;
	}

	public int getLevel() {
		return level;
	}

	public String getDescription() {
		return description;
	}

	public void Material(Material t) {
		mat = t;
	}

	public void setEffect(PotionEffectType t) {
		effect = t;
	}

	public void setSpecial(String t) {
		special = t;
	}

	public void setDurations(int[] t) {
		durations = t;
	}

	public void setAmps(int[] t) {
		amps = t;
	}

	public void setDescription(String t) {
		description = t;
	}

	public void setLevel(int t) {
		level = t;
	}
}
