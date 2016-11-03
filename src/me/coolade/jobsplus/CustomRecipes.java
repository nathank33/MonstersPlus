package me.coolade.jobsplus;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomRecipes {
	public static final ArrayList<RecipeSet> arrowList = new ArrayList<RecipeSet>() {
		private static final long serialVersionUID = 1L;

		{
			add(new RecipeSet("Repulse Arrow", "Survivalist", 2, "An arrow that launches nearby enemies away.",
					"2. Obsidian  5. Stick  8. Feather"));
			add(new RecipeSet("Torch Arrow", "Survivalist", 3, "Creates a torch at the arrow's location.",
					"2. Torch  5. Stick  8. Feather"));
			add(new RecipeSet("Lasso Arrow", "Survivalist", 4,
					"Teleports the enemy to your location, great against players running away.",
					"2. Vine  5. Stick  8. Feather"));
			add(new RecipeSet("Vortex Arrow", "Survivalist", 5, "Pulls nearby enemies to the arrow's location.",
					"2. SlimeBall  5. Stick  8. Feather"));
			add(new RecipeSet("Net Arrow", "Survivalist", 6, "Creates a Web net at the arrow's location.",
					"1. String  2. String  3. String  5. Stick  8. Feather"));
			add(new RecipeSet("Shuffle Arrow", "Survivalist", 8, "Causes the shooter to switch places with the target.",
					"2. EnderPearl  5. Stick  8. Feather"));
			add(new RecipeSet("Grapple Arrow", "Survivalist", 10,
					"Teleports the shooter to the arrow location, Hold Crouch to latch onto objects.",
					"2. Lead  5. Stick  8. Feather"));
			add(new RecipeSet("Blizzard Arrow", "Survivalist", 11,
					"Causes all nearby enemies to suffer from intense slow.",
					"1. Ice  2. Ice  3. Ice  5. Stick  8. Feather"));

			add(new RecipeSet("Beast Arrow", "Warrior", 2, "Does triple damage to monsters.",
					"1. Flint  2. Flint  3. Flint  5. Stick  8. Feather"));
			add(new RecipeSet("Fireball Arrow", "Warrior", 3, "Launches an explosive Fireball.",
					"1. Coal  2. Gunpowder  3. Coal  5. Stick  8. Feather"));
			add(new RecipeSet("Razor Arrow", "Warrior", 4, "Does double damage to unarmored targets.",
					"1. Glass  2. Glass  3. Glass  5. Stick  8. Feather"));
			add(new RecipeSet("Skull Arrow", "Warrior", 5, "Launches a WitherSkull that applies Wither II.",
					"1. Bone  2. Rotten Flesh  3. Bone  5. Stick  8. Feather"));
			add(new RecipeSet("Piercing Arrow", "Warrior", 6, "Does double damage to fully armored targets.",
					"2. Iron Ingot  5. Stick  8. Feather"));
			add(new RecipeSet("Explosive Arrow", "Warrior", 8, "Creates a large explosion at the impact zone.",
					"1. Gunpowder  2. Gunpowder  3. Gunpowder  5. Stick  8. Feather"));
			add(new RecipeSet("Poison Arrow", "Warrior", 10, "Causes the target to suffer from level 2 poison.",
					"2. Spider Eye  5. Stick  8. Feather"));
			add(new RecipeSet("Shreading Arrow", "Warrior", 11,
					"Does 3% of the targets current HP, great against Legendaries.",
					"1. Arrow  2. Arrow  3. Arrow  5. Stick  8. Feather"));
			add(new RecipeSet("Comet Arrow", "Warrior", 12, "Fireballs fall from the sky to the impact zone.",
					"1. Gunpowder  2. Blaze Rod  3. Gunpowder  5. Stick  8. Feather"));
		}
	};
	public static final ArrayList<RecipeSet> builderList = new ArrayList<RecipeSet>() {
		private static final long serialVersionUID = 1L;

		{
			add(new RecipeSet("Cave Spider Spawner", "Builder", 2, "Cave Spider Spawner",
					"1-4. String 5. Mobspawner  6-9. String", 6));
			add(new RecipeSet("Spider Spawner", "Builder", 3, "Spider Spawner",
					"1-4. Spider Eye 5. MobSpawner  6-9. Spidereye", 6));
			add(new RecipeSet("Bat Spawner", "Builder", 4, "Bat Spawner", "1-4. Coal 5. MobSpawner  6-9. Coal", 6));
			add(new RecipeSet("Zombie Spawner", "Builder", 5, "Zombie Spawner",
					"1-4. RottenFlesh 5. MobSpawner  6-9. RottenFlesh", 7));
			add(new RecipeSet("Skeleton Spawner", "Builder", 6, "Skeleton Spawner",
					"1-4. Bone 5. MobSpawner  6-9. Bone", 10));
			add(new RecipeSet("Creeper Spawner", "Builder", 7, "Creeper Spawner",
					"1-4. Gunpowder 5. MobSpawner  6-9. Gunpowder", 15));
			add(new RecipeSet("Enderman Spawner", "Builder", 8, "Enderman Spawner",
					"1-4. Enderpearl 5. MobSpawner  6-9. Enderpearl", 7));
			add(new RecipeSet("Blaze Spawner", "Builder", 9, "Blaze Spawner",
					"1-4. Blazerod 5. MobSpawner  6-9. Blazerod", 11));
			add(new RecipeSet("Silverfish Spawner", "Builder", 11, "Silverfish Spawner",
					"1-4. Rawfish 5. MobSpawner  6-9. Rawfish", 10));
			add(new RecipeSet("Zombie Pigman Spawner", "Builder", 13, "Zombie Pigman Spawner",
					"1-4. Gold Ore 5. MobSpawner  6-9. Gold Ore", 15));
			add(new RecipeSet("Snow Golem Spawner", "Builder", 15, "Snow Golem Spawner",
					"1-4. Snow Ball 5. MobSpawner  6-9. Snow Ball", 7));

			add(new RecipeSet("Cracked Stone Brick", "Builder", 2, "Cracked Stone Brick", "1. Stone Brick 2. Flint"));
			add(new RecipeSet("Chiseled Stone Brick", "Builder", 4, "Chiseled Stone Brick",
					"2. Flint 4. Flint 5. Stone Brick 6. Flint 8. Flint"));
			add(new RecipeSet("Mossy Cobblestone", "Builder", 5, "Mossy Cobblestone",
					"2. Seeds 4. Seeds 5. Cobblestone 6. Seeds 8. Seeds"));
			add(new RecipeSet("Mossy Stone Brick", "Builder", 6, "Mossy Stone Brick",
					"2. Seeds 4. Seeds 5. Stonebrick 6. Seeds 8. Seeds"));
			add(new RecipeSet("End Portal", "Builder", 10, "End Portal",
					"1. Emerald 2. Emerald 3. Emerald 4-9. Enderstone"));
		}
	};
	public static final ArrayList<RecipeSet> farmerList = new ArrayList<RecipeSet>() {
		private static final long serialVersionUID = 1L;

		{
			add(new RecipeSet("Chicken", "Farmer", 2, "Chicken Spawn Egg", "1-4. Seeds 5. Egg  6-9. Seeds"));
			add(new RecipeSet("Cow", "Farmer", 3, "Cow Spawn Egg", "1-4. Wheat 5. Egg  6-9. Wheat"));
			add(new RecipeSet("Pig", "Farmer", 4, "Pig Spawn Egg", "1-4. Carrot 5. Egg  6-9. Carrot"));
			add(new RecipeSet("Sheep", "Farmer", 5, "Sheep Spawn Egg", "1-4. Wool 5. Egg  6-9. Wool"));
			add(new RecipeSet("Squid", "Farmer", 5, "Squid Spawn Egg", "1-4. Inksac 5. Egg  6-9. Inksac"));
			add(new RecipeSet("Wolf", "Farmer", 6, "Wolf Spawn Egg", "1-4. Bone 5. Egg  6-9. Melon Seeds"));
			add(new RecipeSet("Ocelot", "Farmer", 8, "Ocelot Spawn Egg", "1-4. Milk 5. Egg  6-9. Milk"));
			add(new RecipeSet("Spider", "Farmer", 9, "Spider Spawn Egg",
					"1. Lapis Block 2-4. Spider Eye 5. Egg  6-9. Spider Eye"));
			add(new RecipeSet("Skeleton", "Farmer", 10, "Skeleton Spawn Egg",
					"1. Lapis Block 2-4. Bone 5. Egg  6-9. Bone"));
			add(new RecipeSet("Blaze", "Farmer", 11, "Blaze Spawn Egg",
					"1. Lapis Block 2-4. Blaze Rod 5. Egg  6-9. Blaze Rod"));
			add(new RecipeSet("Enderman", "Farmer", 11, "Enderman Spawn Egg",
					"1. Lapis Block 2-4. Enderpearl 5. Egg  6-9. Enderpearl"));
			add(new RecipeSet("Zombie", "Farmer", 12, "Zombie Spawn Egg",
					"1. Lapis Block 2-4. Rotten Flesh 5. Egg  6-9. Rotten Flesh"));
			add(new RecipeSet("Mooshroom", "Farmer", 12, "Mooshroom Spawn Egg",
					"1-4. Mushroom Soup 5. Egg  6-9. Mushroom Soup"));
			add(new RecipeSet("Creeper", "Farmer", 13, "Creeper Spawn Egg",
					"1. Lapis Block 2-4. Gunpowder 5. Egg  6-9. Gunpowder"));
			add(new RecipeSet("Slime", "Farmer", 13, "Slime Spawn Egg",
					"1. Lapis Block 2-4. Slimeball 5. Egg  6-9. Slimeball"));
			add(new RecipeSet("Horse", "Farmer", 15, "Horse Spawn Egg", "1-4. Lead 5. Egg  6-9. Lead"));
			add(new RecipeSet("Villager", "Farmer", 18, "Villager Spawn Egg", "1-4. Emerald 5. Egg 6-9. Emerald"));

			add(new RecipeSet("Saddle", "Farmer", 3, "Saddle", "1-4. Leather 5. String 6. Leather"));
			add(new RecipeSet("Iron Horse Armor", "Farmer", 6, "Iron Horse Armor", "3-5. Iron Ingot 7-8. Iron Ingot"));
			add(new RecipeSet("Golden Horse Armor", "Farmer", 10, "Golden Horse Armor",
					"3-5. Gold Ingot 7-8. Gold Ingot"));
			add(new RecipeSet("Diamond Horse Armor", "Farmer", 14, "Diamond Horse Armor", "3-5. Diamond 7-8. Diamond"));
		}
	};
	public static final ArrayList<RecipeSet> bombList = new ArrayList<RecipeSet>() {
		private static final long serialVersionUID = 1L;

		{
			add(new RecipeSet("Survivalist Bomb", "Survivalist", 10, "A stun bomb that immobilizes targets.",
					"1. Gunpowder  2. Iron Axe  3-4. Gunpowder 5. Egg  6-9. Gunpowder"));
			add(new RecipeSet("Warrior Bomb", "Warrior", 10,
					"A fire bomb that ignites nearby targets and spawns a temporary "
							+ "powerful Zombie Pigman that will attack the nearest entity.",
					"1. Gunpowder  2. Iron Sword  3-4. Gunpowder 5. Egg  6-9. Gunpowder"));
			add(new RecipeSet("WitchDoctor Bomb", "WitchDoctor", 10,
					"A Wither bomb that withers nearby foes and spawns temporary wither skeletons.",
					"1. Gunpowder  2. Glass Bottle  3-4. Gunpowder 5. Egg  6-9. Gunpowder"));
			add(new RecipeSet("Miner Bomb", "Miner", 10, "A cluster bomb that explodes repeatedly.",
					"1. Gunpowder  2. Iron Pickaxe  3-4. Gunpowder 5. Egg  6-9. Gunpowder"));
			add(new RecipeSet("Fisherman Bomb", "Fisherman", 10, "A lightning bomb that electricutes nearby enemies.",
					"1. Gunpowder  2. Fishing Rod  3-4. Gunpowder 5. Egg  6-9. Gunpowder"));
			add(new RecipeSet("Blacksmith Bomb", "Blacksmith", 10,
					"A bomb that summons anvils from the sky to crush foes.",
					"1. Gunpowder  2. Furnace   3-4. Gunpowder 5. Egg  6-9. Gunpowder"));
		}
	};

	@SuppressWarnings("deprecation")
	public CustomRecipes() {
		/*
		 * Add the "Unique" To the lore so that we know this item was created
		 * from the server and not just renamed.
		 */

		/** Repulse Arrow **/
		ItemStack item = new ItemStack(Material.ARROW, 8);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(ChatColor.GREEN + "Repulse Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" E ", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('E', Material.OBSIDIAN);
		Bukkit.addRecipe(recipe);

		/** Vortex Arrow **/
		item = new ItemStack(Material.ARROW, 24);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Vortex Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape(" E ", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('E', Material.SLIME_BALL);
		Bukkit.addRecipe(recipe);

		/** Shuffle Arrow **/
		item = new ItemStack(Material.ARROW, 6);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Shuffle Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape(" E ", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('E', Material.ENDER_PEARL);
		Bukkit.addRecipe(recipe);

		/** Torch Arrow **/
		item = new ItemStack(Material.ARROW, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Torch Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape(" E ", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('E', Material.TORCH);
		Bukkit.addRecipe(recipe);

		/** Poison Arrow **/
		item = new ItemStack(Material.ARROW, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Poison Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape(" K ", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('K', Material.SPIDER_EYE);
		Bukkit.addRecipe(recipe);

		/** Razor Arrow **/
		item = new ItemStack(Material.ARROW, 3);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Razor Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape("EEE", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('E', Material.GLASS);
		Bukkit.addRecipe(recipe);

		/** Piercing Arrow **/
		item = new ItemStack(Material.ARROW, 4);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Piercing Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape(" E ", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('E', Material.IRON_INGOT);
		Bukkit.addRecipe(recipe);

		/** Explosive Arrow **/
		item = new ItemStack(Material.ARROW, 6);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_RED + "Explosive Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape("EEE", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('E', Material.SULPHUR);
		Bukkit.addRecipe(recipe);

		/** Fireball Arrow **/
		item = new ItemStack(Material.ARROW, 6);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Fireball Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape("ABA", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('B', Material.SULPHUR);
		recipe.setIngredient('A', Material.COAL);
		Bukkit.addRecipe(recipe);

		/** Comet Arrow **/
		item = new ItemStack(Material.ARROW, 16);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_RED + "Comet Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape("ABA", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('B', Material.BLAZE_ROD);
		recipe.setIngredient('A', Material.SULPHUR);
		Bukkit.addRecipe(recipe);

		/** Skull Arrow **/
		item = new ItemStack(Material.ARROW, 3);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_GRAY + "Skull Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape("ABA", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('B', Material.ROTTEN_FLESH);
		recipe.setIngredient('A', Material.BONE);
		Bukkit.addRecipe(recipe);

		/** Shreading Arrow **/
		item = new ItemStack(Material.ARROW, 6);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Shreading Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('A', Material.ARROW);
		Bukkit.addRecipe(recipe);

		/** Beast Arrow **/
		item = new ItemStack(Material.ARROW, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Beast Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('A', Material.FLINT);
		Bukkit.addRecipe(recipe);

		/** Grapple Arrow **/
		item = new ItemStack(Material.ARROW, 20);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Grapple Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape(" A ", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('A', Material.LEASH);
		Bukkit.addRecipe(recipe);

		/** Net Arrow **/
		item = new ItemStack(Material.ARROW, 3);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Net Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('A', Material.STRING);
		Bukkit.addRecipe(recipe);

		/** Lasso Arrow **/
		item = new ItemStack(Material.ARROW, 2);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_BLUE + "Lasso Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape(" A ", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('A', Material.VINE);
		Bukkit.addRecipe(recipe);

		/** Blizzard Arrow **/
		item = new ItemStack(Material.ARROW, 3);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Blizzard Arrow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);

		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", " S ", " F ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('A', Material.ICE);
		Bukkit.addRecipe(recipe);

		/** Chiseled Stone Brick **/
		item = new ItemStack(Material.SMOOTH_BRICK, 1, (short) 3);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Chiseled Stone Brick");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape(" A ", "ABA", " A ");
		recipe.setIngredient('A', Material.FLINT);
		recipe.setIngredient('B', Material.SMOOTH_BRICK);
		Bukkit.addRecipe(recipe);

		/** Cracked Stone Brick **/
		item = new ItemStack(Material.SMOOTH_BRICK, 1, (short) 2);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Cracked Stone Brick");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("BA ", "   ", "  ");
		recipe.setIngredient('A', Material.FLINT);
		recipe.setIngredient('B', Material.SMOOTH_BRICK);
		Bukkit.addRecipe(recipe);

		/** Mossy Cobblestone **/
		item = new ItemStack(Material.MOSSY_COBBLESTONE, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Mossy Cobblestone");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape(" A ", "ABA", " A ");
		recipe.setIngredient('A', Material.SEEDS);
		recipe.setIngredient('B', Material.COBBLESTONE);
		Bukkit.addRecipe(recipe);

		/** Mossy Stone Brick **/
		item = new ItemStack(Material.SMOOTH_BRICK, 1, (short) 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Mossy Stone Brick");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape(" A ", "ABA", " A ");
		recipe.setIngredient('A', Material.SEEDS);
		recipe.setIngredient('B', Material.SMOOTH_BRICK);
		Bukkit.addRecipe(recipe);

		/** Ender Portal Frame **/
		item = new ItemStack(Material.ENDER_PORTAL_FRAME, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "End Portal");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "BBB", "BBB");
		recipe.setIngredient('A', Material.EMERALD);
		recipe.setIngredient('B', Material.ENDER_STONE);
		Bukkit.addRecipe(recipe);

		/** Saddle **/
		item = new ItemStack(Material.SADDLE, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Saddle");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "   ");
		recipe.setIngredient('A', Material.LEATHER);
		recipe.setIngredient('B', Material.STRING);
		Bukkit.addRecipe(recipe);

		/** Golden Horse Armor **/
		item = new ItemStack(Material.GOLD_BARDING, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Golden Horse Armor");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("  A", "AA ", "AA ");
		recipe.setIngredient('A', Material.GOLD_INGOT);
		Bukkit.addRecipe(recipe);

		/** Iron Horse Armor **/
		item = new ItemStack(Material.IRON_BARDING, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Iron Horse Armor");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("  A", "AA ", "AA ");
		recipe.setIngredient('A', Material.IRON_INGOT);
		Bukkit.addRecipe(recipe);

		/** Diamond Horse Armor **/
		item = new ItemStack(Material.DIAMOND_BARDING, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Diamond Horse Armor");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("  A", "AA ", "AA ");
		recipe.setIngredient('A', Material.DIAMOND);
		Bukkit.addRecipe(recipe);

		/** Chicken Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 93);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Chicken");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.SEEDS);
		recipe.setIngredient('B', Material.EGG);
		Bukkit.addRecipe(recipe);

		/** Cow Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 92);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Cow");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.WHEAT);
		recipe.setIngredient('B', Material.EGG);
		Bukkit.addRecipe(recipe);

		/** Pig Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 90);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Pig");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.CARROT_ITEM);
		recipe.setIngredient('B', Material.EGG);
		Bukkit.addRecipe(recipe);

		/** Sheep Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 91);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Sheep");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.WOOL);
		recipe.setIngredient('B', Material.EGG);
		Bukkit.addRecipe(recipe);

		/** Squid Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 94);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Squid");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.INK_SACK);
		recipe.setIngredient('B', Material.EGG);
		Bukkit.addRecipe(recipe);

		/** Wolf Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 95);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Wolf");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABC", "CCC");
		recipe.setIngredient('A', Material.BONE);
		recipe.setIngredient('B', Material.EGG);
		recipe.setIngredient('C', Material.MELON_SEEDS);
		Bukkit.addRecipe(recipe);

		/** Ocelot Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 98);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Ocelot");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.MILK_BUCKET);
		recipe.setIngredient('B', Material.EGG);
		Bukkit.addRecipe(recipe);

		/** Mooshroom Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 96);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Mooshroom");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.MUSHROOM_SOUP);
		recipe.setIngredient('B', Material.EGG);
		Bukkit.addRecipe(recipe);

		/** Horse Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 100);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Horse");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.LEASH);
		recipe.setIngredient('B', Material.EGG);
		Bukkit.addRecipe(recipe);

		/** Villager Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 120);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Villager");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape(" A ", "ABA", " A ");
		recipe.setIngredient('A', Material.EMERALD);
		recipe.setIngredient('B', Material.EGG);
		Bukkit.addRecipe(recipe);

		/** Zombie Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 54);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Zombie");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("CAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.ROTTEN_FLESH);
		recipe.setIngredient('B', Material.EGG);
		recipe.setIngredient('C', Material.LAPIS_BLOCK);
		Bukkit.addRecipe(recipe);

		/** Skeleton Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 51);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Skeleton");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("CAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.BONE);
		recipe.setIngredient('B', Material.EGG);
		recipe.setIngredient('C', Material.LAPIS_BLOCK);
		Bukkit.addRecipe(recipe);

		/** Creeper Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 50);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Creeper");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("CAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.SULPHUR);
		recipe.setIngredient('B', Material.EGG);
		recipe.setIngredient('C', Material.LAPIS_BLOCK);
		Bukkit.addRecipe(recipe);

		/** Spider Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 52);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Spider");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("CAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.SPIDER_EYE);
		recipe.setIngredient('B', Material.EGG);
		recipe.setIngredient('C', Material.LAPIS_BLOCK);
		Bukkit.addRecipe(recipe);

		/** Enderman Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 58);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Enderman");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("CAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.ENDER_PEARL);
		recipe.setIngredient('B', Material.EGG);
		recipe.setIngredient('C', Material.LAPIS_BLOCK);
		Bukkit.addRecipe(recipe);

		/** Slime Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 55);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Slime");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("CAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.SLIME_BALL);
		recipe.setIngredient('B', Material.EGG);
		recipe.setIngredient('C', Material.LAPIS_BLOCK);
		Bukkit.addRecipe(recipe);

		/** Blaze Spawn Egg **/
		item = new ItemStack(Material.MONSTER_EGG, 1, (short) 61);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Blaze");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("CAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.BLAZE_ROD);
		recipe.setIngredient('B', Material.EGG);
		recipe.setIngredient('C', Material.LAPIS_BLOCK);
		Bukkit.addRecipe(recipe);

		/** CaveSpider Spawner **/
		item = new ItemStack(Material.MOB_SPAWNER, 1, (short) 59);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Cave Spider" + ChatColor.WHITE + " Spawner");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.STRING);
		recipe.setIngredient('B', Material.MOB_SPAWNER, -1);
		Bukkit.addRecipe(recipe);

		/** Spider Spawner **/
		item = new ItemStack(Material.MOB_SPAWNER, 1, (short) 52);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Spider" + ChatColor.WHITE + " Spawner");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.SPIDER_EYE);
		recipe.setIngredient('B', Material.MOB_SPAWNER, -1);
		Bukkit.addRecipe(recipe);

		/** Bat Spawner **/
		item = new ItemStack(Material.MOB_SPAWNER, 1, (short) 65);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Bat" + ChatColor.WHITE + " Spawner");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.COAL);
		recipe.setIngredient('B', Material.MOB_SPAWNER, -1);
		Bukkit.addRecipe(recipe);

		/** Zombie Spawner **/
		item = new ItemStack(Material.MOB_SPAWNER, 1, (short) 54);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Zombie" + ChatColor.WHITE + " Spawner");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.ROTTEN_FLESH);
		recipe.setIngredient('B', Material.MOB_SPAWNER, -1);
		Bukkit.addRecipe(recipe);

		/** Skeleton Spawner **/
		item = new ItemStack(Material.MOB_SPAWNER, 1, (short) 51);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Skeleton" + ChatColor.WHITE + " Spawner");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.BONE);
		recipe.setIngredient('B', Material.MOB_SPAWNER, -1);
		Bukkit.addRecipe(recipe);

		/** Creeper Spawner **/
		item = new ItemStack(Material.MOB_SPAWNER, 1, (short) 50);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Creeper" + ChatColor.WHITE + " Spawner");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.SULPHUR);
		recipe.setIngredient('B', Material.MOB_SPAWNER, -1);
		Bukkit.addRecipe(recipe);

		/** Enderman Spawner **/
		item = new ItemStack(Material.MOB_SPAWNER, 1, (short) 58);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Enderman" + ChatColor.WHITE + " Spawner");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.ENDER_PEARL);
		recipe.setIngredient('B', Material.MOB_SPAWNER, -1);
		Bukkit.addRecipe(recipe);

		/** Blaze Spawner **/
		item = new ItemStack(Material.MOB_SPAWNER, 1, (short) 61);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Blaze" + ChatColor.WHITE + " Spawner");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.BLAZE_ROD);
		recipe.setIngredient('B', Material.MOB_SPAWNER, -1);
		Bukkit.addRecipe(recipe);

		/** Silverfish Spawner **/
		item = new ItemStack(Material.MOB_SPAWNER, 1, (short) 60);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Silverfish" + ChatColor.WHITE + " Spawner");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.RAW_FISH);
		recipe.setIngredient('B', Material.MOB_SPAWNER, -1);
		Bukkit.addRecipe(recipe);

		/** Zombie Pigman Spawner **/
		item = new ItemStack(Material.MOB_SPAWNER, 1, (short) 57);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Zombie Pigman" + ChatColor.WHITE + " Spawner");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.GOLD_ORE);
		recipe.setIngredient('B', Material.MOB_SPAWNER, -1);
		Bukkit.addRecipe(recipe);

		/** Snow Golem Spawner **/
		item = new ItemStack(Material.MOB_SPAWNER, 1, (short) 97);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Snow Golem" + ChatColor.WHITE + " Spawner");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('A', Material.EGG);
		recipe.setIngredient('B', Material.MOB_SPAWNER, -1);
		Bukkit.addRecipe(recipe);

		/** Survivalist Bomb **/
		item = new ItemStack(Material.EGG, 3);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Survivalist Bomb");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("BCB", "BAB", "BBB");
		recipe.setIngredient('A', Material.EGG);
		recipe.setIngredient('B', Material.SULPHUR);
		recipe.setIngredient('C', Material.IRON_AXE);
		Bukkit.addRecipe(recipe);
		/** Warrior Bomb **/
		item = new ItemStack(Material.EGG, 3);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Warrior Bomb");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("BCB", "BAB", "BBB");
		recipe.setIngredient('A', Material.EGG);
		recipe.setIngredient('B', Material.SULPHUR);
		recipe.setIngredient('C', Material.IRON_SWORD);
		Bukkit.addRecipe(recipe);
		/** WitchDoctor Bomb **/
		item = new ItemStack(Material.EGG, 3);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "WitchDoctor Bomb");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("BCB", "BAB", "BBB");
		recipe.setIngredient('A', Material.EGG);
		recipe.setIngredient('B', Material.SULPHUR);
		recipe.setIngredient('C', Material.GLASS_BOTTLE);
		Bukkit.addRecipe(recipe);
		/** Miner Bomb **/
		item = new ItemStack(Material.EGG, 3);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_GRAY + "Miner Bomb");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("BCB", "BAB", "BBB");
		recipe.setIngredient('A', Material.EGG);
		recipe.setIngredient('B', Material.SULPHUR);
		recipe.setIngredient('C', Material.IRON_PICKAXE);
		Bukkit.addRecipe(recipe);
		/** Fisherman Bomb **/
		item = new ItemStack(Material.EGG, 3);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Fisherman Bomb");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("BCB", "BAB", "BBB");
		recipe.setIngredient('A', Material.EGG);
		recipe.setIngredient('B', Material.SULPHUR);
		recipe.setIngredient('C', Material.FISHING_ROD);
		Bukkit.addRecipe(recipe);
		/** Blacksmith Bomb **/
		item = new ItemStack(Material.EGG, 3);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Blacksmith Bomb");
		meta.setLore(Arrays.asList("Unique"));
		item.setItemMeta(meta);
		recipe = new ShapedRecipe(item);
		recipe.shape("BCB", "BAB", "BBB");
		recipe.setIngredient('A', Material.EGG);
		recipe.setIngredient('B', Material.SULPHUR);
		recipe.setIngredient('C', Material.FURNACE);
		Bukkit.addRecipe(recipe);
	}

	public static class RecipeSet {
		private String name;
		private String job;
		private int level;
		private String description;
		private String recipeDescription;
		private int powerstones;

		public RecipeSet(String name, String job, int level, String description, String recipeDescription,
				int powerstones) {
			this.name = name;
			this.job = job;
			this.level = level;
			this.description = description;
			this.recipeDescription = recipeDescription;
			this.powerstones = powerstones;
		}

		public RecipeSet(String name, String job, int level, String description, String recipeDescription) {
			this(name, job, level, description, recipeDescription, 0);
		}

		public String getName() {
			return name;
		}

		public String getJob() {
			return job;
		}

		public int getLevel() {
			return level;
		}

		public String getDescription() {
			return description;
		}

		public String getRecipeDescription() {
			return recipeDescription;
		}

		public int getPowerstones() {
			return powerstones;
		}

		public void setName(String s) {
			name = s;
		}

		public void setJob(String s) {
			job = s;
		}

		public void setLevel(int i) {
			level = i;
		}

		public void setDescription(String s) {
			description = s;
		}

		public void setPowerstones(int i) {
			powerstones = i;
		}
	}

	public static RecipeSet getRecipe(String name) {
		for (RecipeSet rs : arrowList) {
			if (name.contains(rs.getName())) {
				return rs;
			}
		}
		for (RecipeSet rs : builderList) {
			if (name.contains(rs.getName())) {
				return rs;
			}
		}
		for (RecipeSet rs : farmerList) {
			if (name.contains(rs.getName())) {
				return rs;
			}
		}
		for (RecipeSet rs : bombList) {
			if (name.contains(rs.getName())) {
				return rs;
			}
		}
		return null;
	}

	public static String getRequiredJob(String name) {
		if (name == null) {
			return null;
		}

		RecipeSet rs = getRecipe(name);
		if (rs != null) {
			return rs.getJob();
		}

		return null;
	}

	public static int getRequiredLevel(String name) {
		if (name == null) {
			return -1;
		}

		RecipeSet rs = getRecipe(name);
		if (rs != null) {
			return rs.getLevel();
		}

		return -1;
	}

	public static boolean hasRecipe(Player player, String name) {
		if (name == null) {
			return false;
		}
		RecipeSet rs = getRecipe(name);
		if (rs == null) {
			return false;
		}

		int requiredLevel = rs.getLevel();
		int playerLevel = JobsListener.getJobLevel(player.getName(), rs.getJob());
		if (playerLevel >= requiredLevel) {
			return true;
		} else {
			return false;
		}
	}
}
