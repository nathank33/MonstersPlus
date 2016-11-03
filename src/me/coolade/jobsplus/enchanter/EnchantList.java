package me.coolade.jobsplus.enchanter;

import java.util.ArrayList;

import org.bukkit.enchantments.Enchantment;

public class EnchantList {
	public static final int LVLCOST1 = 10;
	public static final int LVLCOST2 = 17;
	public static final int LVLCOST3 = 23;
	public static final int LVLCOST4 = 30;
	public static final int LVLCOST5 = 35;

	public static final ArrayList<EnchantRecipe> ENCHANTLIST = new ArrayList<EnchantRecipe>() {
		private static final long serialVersionUID = 1L;

		{

			add(new EnchantRecipe(Enchantment.PROTECTION_EXPLOSIONS, "BlastProtection", 2, "Enchanter", 2, LVLCOST2));
			add(new EnchantRecipe(Enchantment.DAMAGE_UNDEAD, "Smite", 2, "Enchanter", 2, LVLCOST2));
			add(new EnchantRecipe(Enchantment.PROTECTION_FIRE, "FireProtection", 2, "Enchanter", 3, LVLCOST2));
			add(new EnchantRecipe(Enchantment.PROTECTION_PROJECTILE, "ProjectileProtection", 2, "Enchanter", 3,
					LVLCOST2));
			add(new EnchantRecipe(Enchantment.DAMAGE_ALL, "Sharpness", 2, "Enchanter", 4, LVLCOST2));
			add(new EnchantRecipe(Enchantment.DIG_SPEED, "Efficiency", 1, "Enchanter", 4, LVLCOST1));
			add(new EnchantRecipe(Enchantment.PROTECTION_ENVIRONMENTAL, "Protection", 2, "Enchanter", 4, LVLCOST2));
			add(new EnchantRecipe(Enchantment.DURABILITY, "Unbreaking", 1, "Enchanter", 5, LVLCOST2));

			add(new EnchantRecipe(Enchantment.PROTECTION_PROJECTILE, "ProjectileProtection", 2, "Enchanter", 5,
					LVLCOST2));
			add(new EnchantRecipe(Enchantment.DAMAGE_ARTHROPODS, "BaneOfArthropods", 2, "Enchanter", 5, LVLCOST2));
			add(new EnchantRecipe(Enchantment.PROTECTION_FALL, "FeatherFalling", 1, "Enchanter", 6, LVLCOST1));
			add(new EnchantRecipe(Enchantment.DAMAGE_UNDEAD, "Smite", 3, "Enchanter", 6, LVLCOST3));
			add(new EnchantRecipe(Enchantment.THORNS, "Thorns", 1, "Enchanter", 6, LVLCOST1));
			add(new EnchantRecipe(Enchantment.ARROW_DAMAGE, "Power", 2, "Enchanter", 6, LVLCOST2));
			add(new EnchantRecipe(Enchantment.LOOT_BONUS_BLOCKS, "Fortune", 1, "Enchanter", 7, LVLCOST2));
			add(new EnchantRecipe(Enchantment.ARROW_DAMAGE, "Power", 3, "Enchanter", 7, LVLCOST3));
			add(new EnchantRecipe(Enchantment.DIG_SPEED, "Efficiency", 2, "Enchanter", 8, LVLCOST2));
			add(new EnchantRecipe(Enchantment.LOOT_BONUS_MOBS, "Looting", 1, "Enchanter", 8, LVLCOST2));

			add(new EnchantRecipe(Enchantment.PROTECTION_FIRE, "FireProtection", 3, "Enchanter", 9, LVLCOST3));
			add(new EnchantRecipe(Enchantment.PROTECTION_EXPLOSIONS, "BlastProtection", 3, "Enchanter", 9, LVLCOST3));
			add(new EnchantRecipe(Enchantment.PROTECTION_ENVIRONMENTAL, "Protection", 3, "Enchanter", 10, LVLCOST3));
			add(new EnchantRecipe(Enchantment.PROTECTION_FALL, "FeatherFalling", 2, "Enchanter", 10, LVLCOST2));
			add(new EnchantRecipe(Enchantment.DURABILITY, "Unbreaking", 2, "Enchanter", 10, LVLCOST3));
			add(new EnchantRecipe(Enchantment.FIRE_ASPECT, "FireAspect", 1, "Enchanter", 11, LVLCOST2));
			add(new EnchantRecipe(Enchantment.DAMAGE_ALL, "Sharpness", 3, "Enchanter", 12, LVLCOST3));
			add(new EnchantRecipe(Enchantment.THORNS, "Thorns", 2, "Enchanter", 13, LVLCOST2));
			add(new EnchantRecipe(Enchantment.ARROW_DAMAGE, "Power", 3, "Enchanter", 14, LVLCOST3));
			add(new EnchantRecipe(Enchantment.DURABILITY, "Unbreaking", 3, "Enchanter", 15, LVLCOST3));

		}
	};
	// no thorns looting fortune infinity silktouch

	private EnchantList() {
	};

	public static EnchantRecipe getEnchantRecipe(String name, int level) {
		for (EnchantRecipe es : ENCHANTLIST) {
			if (name.equalsIgnoreCase(es.getName()) && level == es.getLevel()) {
				return es;
			}
		}
		return null;
	}

}
