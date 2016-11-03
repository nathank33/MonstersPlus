package me.coolade.jobsplus.witchdoctor;

import java.util.ArrayList;
import java.util.List;

import me.coolade.jobsplus.JobsListener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class BrewList {
	public static ArrayList<BrewIngredient> brewList = new ArrayList<BrewIngredient>() {
		private static final long serialVersionUID = 1L;

		{
			add(new BrewIngredient(Material.MAGMA_CREAM, PotionEffectType.FIRE_RESISTANCE, new int[] { 120, 240, 360 },
					new int[] { 0, 1, 2 }, 2, "Magma Cream - Fire Resistance"));

			add(new BrewIngredient(Material.CACTUS, PotionEffectType.HARM, new int[] { 0, 0, 0 }, new int[] { 0, 1, 1 },
					2, "Cactus - Instant Damage"));

			add(new BrewIngredient(Material.SPECKLED_MELON, PotionEffectType.HEAL, new int[] { 0, 0, 0 },
					new int[] { 0, 1, 2 }, 2, "Glistering Melon - Instant Health"));

			add(new BrewIngredient(Material.ROTTEN_FLESH, PotionEffectType.HUNGER, new int[] { 20, 40, 60 },
					new int[] { 0, 1, 2 }, 2, "Rotten Flesh - Hunger"));

			add(new BrewIngredient(Material.BLAZE_POWDER, PotionEffectType.INCREASE_DAMAGE, new int[] { 45, 90, 135 },
					new int[] { 0, 1, 1 }, 2, "Blaze Powder - Increased Damage"));

			add(new BrewIngredient(Material.GOLDEN_CARROT, PotionEffectType.NIGHT_VISION, new int[] { 120, 240, 360 },
					new int[] { 0, 2, 4 }, 2, "Golden Carrot - Night Vision"));

			add(new BrewIngredient(Material.SPIDER_EYE, PotionEffectType.POISON, new int[] { 10, 20, 30 },
					new int[] { 0, 1, 1 }, 2, "Spider Eye - Poison"));

			add(new BrewIngredient(Material.GHAST_TEAR, PotionEffectType.REGENERATION, new int[] { 15, 30, 45 },
					new int[] { 0, 1, 1 }, 2, "Ghast Tear - Regeneration"));

			add(new BrewIngredient(Material.SLIME_BALL, PotionEffectType.SLOW, new int[] { 20, 40, 60 },
					new int[] { 0, 1, 2 }, 2, "Slimeball - Slow"));

			add(new BrewIngredient(Material.INK_SACK, PotionEffectType.SLOW_DIGGING, new int[] { 60, 120, 180 },
					new int[] { 0, 1, 2 }, 2, "Ink Sack - Slow Digging"));

			add(new BrewIngredient(Material.SUGAR, PotionEffectType.SPEED, new int[] { 90, 180, 270 },
					new int[] { 0, 2, 3 }, 2, "Sugar - Speed"));

			add(new BrewIngredient(Material.FERMENTED_SPIDER_EYE, PotionEffectType.WEAKNESS, new int[] { 15, 30, 45 },
					new int[] { 0, 1, 2 }, 2, "Fermented Spider Eye - Weakness"));

			add(new BrewIngredient(Material.POISONOUS_POTATO, PotionEffectType.CONFUSION, new int[] { 30, 60, 90 },
					new int[] { 0, 2, 4 }, 3, "Poisonous Potato - Confusion"));

			add(new BrewIngredient(Material.COAL_BLOCK, PotionEffectType.FAST_DIGGING, new int[] { 60, 120, 180 },
					new int[] { 0, 1, 1 }, 4, "Coal Block - Fast Digging"));

			add(new BrewIngredient(Material.FEATHER, PotionEffectType.JUMP, new int[] { 30, 60, 90 },
					new int[] { 0, 1, 3 }, 4, "Feather - Jump Boost"));

			add(new BrewIngredient(Material.DIAMOND, PotionEffectType.INVISIBILITY, new int[] { 45, 90, 135 },
					new int[] { 0, 0, 0 }, 5, "Diamond - Invisibility"));

			add(new BrewIngredient(Material.ENDER_PEARL, PotionEffectType.BLINDNESS, new int[] { 20, 40, 60 },
					new int[] { 0, 2, 4 }, 6, "Ender Pearl - Blindness"));

			add(new BrewIngredient(Material.IRON_INGOT, PotionEffectType.DAMAGE_RESISTANCE, new int[] { 20, 40, 60 },
					new int[] { 0, 1, 2 }, 7, "Iron Ingot - Damage Resistance"));

			add(new BrewIngredient(Material.RAW_FISH, PotionEffectType.WATER_BREATHING, new int[] { 120, 240, 360 },
					new int[] { 0, 0, 0 }, 8, "Raw Fish - Water Breathing"));

			add(new BrewIngredient(Material.SULPHUR, "Splash", new int[] { 0, 0, 0 }, new int[] { 0, 0, 0 }, 3,
					"Gunpowder - Splash Modifier"));

			add(new BrewIngredient(Material.REDSTONE_BLOCK, "Extend 1", new int[] { 0, 0, 0 }, new int[] { 0, 0, 0 }, 6,
					"Redstone Block - Extend 1"));

			add(new BrewIngredient(Material.LAPIS_BLOCK, "Extend 2", new int[] { 0, 0, 0 }, new int[] { 0, 0, 0 }, 13,
					"Lapis Block - Extend 2"));

			add(new BrewIngredient(Material.QUARTZ_BLOCK, "Amplify 1", new int[] { 0, 0, 0 }, new int[] { 0, 0, 0 }, 8,
					"Quartz Ore - Amplify 1"));

			add(new BrewIngredient(Material.IRON_BLOCK, "Amplify 2", new int[] { 0, 0, 0 }, new int[] { 0, 0, 0 }, 20,
					"Quartz Ore - Amplify 2"));
		}
	};

	public static boolean isValidIngredient(Material mat) {
		for (BrewIngredient bi : brewList) {
			if (bi.getMat() == mat) {
				return true;
			}
		}
		return false;
	}

	public static BrewIngredient getIngredient(PotionEffectType type) {
		for (BrewIngredient bi : brewList) {
			if (bi.getEffect() == type) {
				return bi;
			}
		}
		return null;
	}

	public static BrewIngredient getIngredient(String s) {
		for (BrewIngredient bi : brewList) {
			if (bi.getDescription().toUpperCase().contains(s.toUpperCase())) {
				return bi;
			}
		}
		return null;
	}

	public static BrewIngredient getIngredient(Material mat) {
		for (BrewIngredient bi : brewList) {
			if (bi.getMat() == mat) {
				return bi;
			}
		}
		return null;
	}

	public static boolean canUseIngredient(Player player, Material mat) {
		for (BrewIngredient bi : brewList) {
			if (bi.getMat() == mat) {
				if (JobsListener.getJobLevel(player.getName(), "WitchDoctor") >= bi.getLevel()) {
					return true;
				}
			}
		}
		return false;
	}

	public static List<PotionEffectType> getNormalEffectTypes(List<Material> m) {
		/*
		 * Returns a list of all the valid effect types for a list of materials.
		 * This function will not include splash/extend/or amplify effects. Use
		 * this function to get all the standard bukkit effects.
		 */
		List<PotionEffectType> tempList = new ArrayList<PotionEffectType>();
		for (Material mat : m) {
			BrewIngredient bi = BrewList.getIngredient(mat);
			if (bi != null) {
				if (bi.getEffect() != null) {
					tempList.add(bi.getEffect());
				}
			}
		}
		return tempList;
	}
}
