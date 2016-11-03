package me.coolade.monstersplus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.coolade.jobsplus.CustomRecipes;
import me.coolade.jobsplus.CustomRecipes.RecipeSet;
import me.coolade.jobsplus.FishList;
import me.coolade.jobsplus.FishList.FishItemSet;
import me.coolade.jobsplus.JobsListener;
import me.coolade.jobsplus.OreRadar;
import me.coolade.jobsplus.OreRadar.OreSet;
import me.coolade.jobsplus.Tracker;
import me.coolade.jobsplus.Tracker.TrackSet;
import me.coolade.jobsplus.enchanter.EnchantList;
import me.coolade.jobsplus.enchanter.EnchantRecipe;
import me.coolade.jobsplus.witchdoctor.BrewIngredient;
import me.coolade.jobsplus.witchdoctor.BrewList;
import me.coolade.jobsplus.witchdoctor.BrewingListener;
import me.coolade.monstersplus.monsters.MonsterList;

public class FAQ {

	public static String[] topics = { "Money", "Market", "Monsters", "Quests", "Jobs", "Survivalist", "Miner",
			"Warrior", "Fisherman", "WitchDoctor", "Farmer", "Enchanter", "Builder", "Blacksmith", "Disguise",
			"Customarrows", "Oreradar", "Tracking", "Brewing", "Fishing", "Jobenchant", "Customenchant", "ArrowRecipes",
			"Farmer Recipes", "Builder Recipes", "Trophy", "Powerstones", "ExpTrading", "Bombs", "Enderdragonspawning",
			"Challenge", "Ranks", "Minigames" };

	public FAQ(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length == 0) {
			displayTopics(sender);
		} else if (args.length > 0) {
			if (args[0].equalsIgnoreCase("Jobs") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Jobs: " + ChatColor.WHITE
						+ "Choosing a job allows you to earn money by doing various activities. "
						+ "Each job grants unique features only obtainable for each profession. "
						+ "Each player may have up to 3 jobs. Certain jobs allow players to get custom enchantments and disguises. "
						+ "To learn more about jobs type: " + ChatColor.RED
						+ "/jobs, /jobs help, /jobs browse, /jobs join, /faq jobs explist"
						+ "/faq survivalist, /faq warrior, /faq miner, /faq fisherman, /faq witchdoctor,"
						+ "/faq farmer, /faq enchanter, /faq builder, /faq blacksmith, /faq customenchant, /faq disguise, /faq bombs");
			} else if (args[0].equalsIgnoreCase("jobs") && args.length > 1 && args[1].equalsIgnoreCase("explist")) {
				sender.sendMessage(ChatColor.GOLD + "Jobs Explist: ");
				double x = 1000;
				for (int i = 1; i < 31; i++) {
					double temp = x + (x * (0.12 * (Math.pow(i - 1, 1.50))));
					sender.sendMessage("Level: " + i + " requires " + temp + " exp");
				}

			} else if (args[0].equalsIgnoreCase("Survivalist")) {
				sender.sendMessage(ChatColor.GOLD + "Survivalist: " + ChatColor.WHITE
						+ "The Survivalist receives money by breaking trees, "
						+ "building wooden structures, killing animals, and killing certain monsters. " + ChatColor.AQUA
						+ "Unique abilities: Custom Arrow Recipes. " + ChatColor.WHITE + "To learn more type: "
						+ ChatColor.RED + "/faq customarrows, /faq arrowrecipes, /jobs info survivalist");
				sender.sendMessage(
						ChatColor.DARK_RED + "Spawner based monsters are ignored for experience and money. ");
			} else if (args[0].equalsIgnoreCase("Miner")) {
				sender.sendMessage(ChatColor.GOLD + "Miner: " + ChatColor.WHITE
						+ "The Miner receives money by collecting precious ore such as diamond. " + ChatColor.AQUA
						+ "Unique Abilities: Ore Radar. " + ChatColor.WHITE + "To learn more type: " + ChatColor.RED
						+ "/jobs info miner, /faq oreradar");
			} else if (args[0].equalsIgnoreCase("Warrior")) {
				sender.sendMessage(ChatColor.GOLD + "Warrior: " + ChatColor.WHITE
						+ "The Warrior receives money for all monsters and animals. " + ChatColor.AQUA
						+ "Unique Abilities: Monster Tracking, Custom Arrow Recipes, challenge. " + ChatColor.WHITE
						+ "To learn more type: " + ChatColor.RED
						+ "/jobs info warrior, /faq tracking, /faq customarrows, /faq challenge");
				sender.sendMessage(
						ChatColor.DARK_RED + "Spawner based monsters are ignored for experience and money. ");
			} else if (args[0].equalsIgnoreCase("Fisherman")) {
				sender.sendMessage(ChatColor.GOLD + "Fisherman: " + ChatColor.WHITE
						+ "The Fisherman receives money for fishing various items and cooking fish. As fishermen level "
						+ "they are able to fish many different items ranging from food to emeralds and monster spawners. "
						+ "Higher level fishermen will also be able to catch many of the same item at once. "
						+ ChatColor.AQUA + "Unique Abilities: Custom Fishing " + ChatColor.WHITE
						+ "To learn more type: " + ChatColor.RED + "/jobs info fisherman, /faq fishing");
			} else if (args[0].equalsIgnoreCase("Witchdoctor")) {
				sender.sendMessage(ChatColor.GOLD + "Witchdoctor: " + ChatColor.WHITE
						+ "The Witchdoctor receives money and experience for brewing potions and crafting fireworks. "
						+ "Witchdoctors unlock a special brewing technique that allows them the ability to brew unique "
						+ "potions such as blindness, wither, confusion, jump, and dig speed. High level Witchdoctors "
						+ "unlock the ability to amplify, extend, and stir potions. Stiring the potion allows for multiple "
						+ "potion effects to be added onto a single potion. " + ChatColor.AQUA
						+ "Unique Abilities: Custom Brewing " + ChatColor.WHITE + "To learn more type: " + ChatColor.RED
						+ "/jobs info witchdoctor, /faq brewing, /faq brewing process, /faq brewing list");
			} else if (args[0].equalsIgnoreCase("Farmer") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Farmer: " + ChatColor.WHITE
						+ "Farmers receive money by creating farms to harvest crops. "
						+ "Farmers unlock the ability to track animals, create spawn eggs, and other custom recipes. "
						+ ChatColor.AQUA
						+ "Unique Abilities: Animal Tracking, Custom Farmer Recipes, Spawn Egg Crafting"
						+ ChatColor.WHITE + "To learn more type: " + ChatColor.RED
						+ "/jobs info farmer, /faq tracking, /faq farmer recipes");
			} else if (args[0].equalsIgnoreCase("Enchanter")) {
				sender.sendMessage(ChatColor.GOLD + "Enchanter: " + ChatColor.WHITE
						+ "Enchanters gain money by enchanting items, they receive an exp bonus based "
						+ "on the item that they enchant. Enchanters also gain " + JobsListener.ENCH_EXP
						+ " exp for each level of enchantment that they use. Similarly, Enchanters receive "
						+ JobsListener.REPAIR_EXP + " exp for each level used to rename an item. "
						+ "Enchanters gain the ability to use the command /jobenchant, which allows them to "
						+ "directly enchant an item. Enchanters can also create every custom enchantment available. "
						+ ChatColor.AQUA
						+ "Unique Abilities: Direct Enchanting (/jobenchant), All Custom Enchantments (/customenchant)"
						+ ChatColor.WHITE + "To learn more type: " + ChatColor.RED
						+ "/jobs info enchanter, /faq jobenchant, /faq customenchant, /faq powerstones");
			} else if (args[0].equalsIgnoreCase("Builder") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Builder: " + ChatColor.WHITE
						+ "Builders earn money and experience by placing certain blocks on the ground. "
						+ "As builders gain levels they use custom recipes to create new blocks, such as "
						+ "Chiseled Bricks, Mossy Stone Bricks, and End Portals. They can also change "
						+ "the types of Mob Spawners. Builders can spawn EnderDragons in The End. "
						+ "Builders can use commands to place blocks from farther away (/builder)." + ChatColor.AQUA
						+ "Unique Abilities: Custom Block Recipes, Mob Spawner Changing, EnderDragon Spawning "
						+ ChatColor.WHITE + "To learn more type: " + ChatColor.RED
						+ "/jobs info builder, /faq builder recipes, /faq powerstones, /faq EnderDragonSpawning, /builder");
			} else if (args[0].equalsIgnoreCase("Blacksmith")) {
				sender.sendMessage(ChatColor.GOLD + "Blacksmith: " + ChatColor.WHITE
						+ "Blacksmiths receive money for crafting various armors, and weapons. "
						+ "Alternatively, Blacksmiths can gain money by smelting gold and iron. "
						+ "As a bonus, Blacksmiths gain " + JobsListener.REPAIR_EXP + " exp for each level "
						+ "used to repair an item. " + ChatColor.AQUA + "Unique Abilities: " + ChatColor.WHITE
						+ "To learn more type: " + ChatColor.RED + "/jobs info blacksmith");
			} else if (args[0].equalsIgnoreCase("Disguise") || args[0].equalsIgnoreCase("Disguises")) {
				sender.sendMessage(ChatColor.GOLD + "Disguise: " + ChatColor.WHITE
						+ "As you level up your jobs you get access to more types of disguises. "
						+ "Your diguise will be disabled when engaged in PVP or if you type /disguise stop. "
						+ ChatColor.WHITE + "To learn more type: " + ChatColor.RED
						+ "/disguise, /disguise display, /disguise display available");
			} else if (args[0].equalsIgnoreCase("Customarrows")) {
				sender.sendMessage(ChatColor.GOLD + "Custom Arrows: " + ChatColor.WHITE
						+ "Custom arrows are recipes granted to Survivalists and Warriors. You can cycle through the arrows in "
						+ "your inventory by left clicking with a bow.");
				for (RecipeSet rs : CustomRecipes.arrowList) {
					sender.sendMessage(ChatColor.AQUA + rs.getName() + " - " + ChatColor.WHITE + rs.getDescription()
							+ " (" + rs.getJob() + " level " + rs.getLevel() + ")");
				}
				sender.sendMessage(
						ChatColor.WHITE + "To find the recipes type: " + ChatColor.RED + "/faq arrowrecipes");
			} else if (args[0].equalsIgnoreCase("OreRadar") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Ore Radar: " + ChatColor.WHITE
						+ "Oreradar is an ability that helps a miner locate specific ore. "
						+ "By typing /oreradar <ore>, the Oreradar will begin searching nearby blocks to "
						+ "determine if they are the correct type. If it finds the correct ore it will automatically "
						+ "turn the miner towards the direction of the ore. The oreradar scans the nearby area once "
						+ "every 30 seconds, continues for " + OreRadar.MINUTE + " minutes, and has a cooldown of "
						+ OreRadar.COOLDOWN_MINUTE + " minutes. " + ChatColor.GOLD + "To display the ore levels type: "
						+ ChatColor.RED + "/faq oreradar levels");
			} else if (args[0].equalsIgnoreCase("OreRadar") && args.length > 1 && args[1].equalsIgnoreCase("levels")) {
				sender.sendMessage(ChatColor.GOLD + "Ore Radar Levels: ");
				for (OreSet os : OreRadar.oreSets) {
					sender.sendMessage(ChatColor.AQUA + os.getMat().toString() + ChatColor.WHITE + ": Miner level "
							+ os.getLevel() + " Scan Radius: " + os.getRadius());
				}
			} else if (args[0].equalsIgnoreCase("tracking") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Tracking: " + ChatColor.WHITE
						+ "Tracking is an ability given to warriors and farmers to help them locate specific "
						+ "monsters and animals. By typing /track <mobtype>, the tracker will search a large radius "
						+ "attempting to locate that specific type of entity. When the entity is found the tracker "
						+ "will turn the user to the monster or animal. " + "The tracker scans the nearby area once "
						+ "every 30 seconds, continues for " + Tracker.MINUTE + " minutes, and has a cooldown of "
						+ Tracker.COOLDOWN_MINUTE + " minutes." + ChatColor.GOLD
						+ "To display the tracking levels type: " + ChatColor.RED
						+ "/faq tracking warrior, /faq tracking farmer");
			} else if (args[0].equalsIgnoreCase("tracking") && args.length > 1 && args[1].equalsIgnoreCase("warrior")) {
				sender.sendMessage(ChatColor.GOLD + "Tracking Warrior: ");
				for (TrackSet ts : Tracker.trackSets) {
					if (ts.getJob().equalsIgnoreCase("Warrior")) {
						sender.sendMessage(ChatColor.AQUA + ts.getType().toString() + ChatColor.WHITE
								+ ": Warrior level " + ts.getLevel() + " Scan Radius: " + ts.getRadius());
					}
				}
			} else if (args[0].equalsIgnoreCase("tracking") && args.length > 1 && args[1].equalsIgnoreCase("farmer")) {
				sender.sendMessage(ChatColor.GOLD + "Tracking Warrior: ");
				for (TrackSet ts : Tracker.trackSets) {
					if (ts.getJob().equalsIgnoreCase("farmer")) {
						sender.sendMessage(ChatColor.AQUA + ts.getType().toString() + ChatColor.WHITE
								+ ": Warrior level " + ts.getLevel() + " Scan Radius: " + ts.getRadius());
					}
				}
			} else if (args[0].equalsIgnoreCase("brewing") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Brewing: " + ChatColor.WHITE
						+ "Custom Brewing is an ability only available to Witchdoctors, which allows them "
						+ "to create fully unique potions. As Witchdoctors gain levels they unlock the option "
						+ "to stir the potion which allows for multiple potion effects on a single potion. "
						+ "To utilize the custom brewing process, a Witchdoctor must be level 2 and have the following: "
						+ "A netherwart, cauldron, water bucket, and an effect ingredient. (Optional) Amplify Ingredient, "
						+ "Time extention ingredient, Splash Ingredient, and a Bone for Stiring. "
						+ "Each brewing process yields 3 potions, and adding multiple effect ingredients will not stack on the potion "
						+ "unless used with stiring/amplify. While custom brewing, Witchdoctors gain "
						+ BrewingListener.INGREDIENT_EXP + " per ingredient added into the cauldron. " + ChatColor.GOLD
						+ "To display the Brewing process and ingredient list type: " + ChatColor.RED
						+ "/faq brewing process, /faq brewing list");
			} else if (args[0].equalsIgnoreCase("brewing") && args.length > 1 && args[1].equalsIgnoreCase("process")) {
				sender.sendMessage(ChatColor.GOLD + "Brewing Process: " + ChatColor.WHITE
						+ "1. Find a cauldron and fill it with water. "
						+ "2. Right click the cauldron while holding a netherwart. "
						+ "3. Insert an effect ingredient such as Rotten Flesh or Cactus. "
						+ "4. (Optional) Insert splash/amplify/extend ingredient such as Gunpowder or "
						+ "a Redstone Block to improve the potion. "
						+ "5. (Optional) If you have unlocked Stiring, right click the cauldron with a bone to "
						+ "repeat the process for an additional 1 or 2 effect. "
						+ "6. Right click the cauldron 3 times with an empty bottle to collect your potions. ");
			} else if (args[0].equalsIgnoreCase("brewing") && args.length > 1 && args[1].equalsIgnoreCase("list")) {
				sender.sendMessage(ChatColor.GOLD + "Brewing List: ");
				sender.sendMessage(ChatColor.DARK_PURPLE + "Stiring level 1: " + BrewingListener.STIR_LEVEL1);
				sender.sendMessage(ChatColor.DARK_PURPLE + "Stiring level 2: " + BrewingListener.STIR_LEVEL2);
				for (BrewIngredient bi : BrewList.brewList) {
					sender.sendMessage(ChatColor.AQUA + bi.getMat().toString() + ChatColor.WHITE + ": "
							+ bi.getDescription() + ",  Level: " + bi.getLevel());
				}
			} else if (args[0].equalsIgnoreCase("fishing") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Fishing: " + ChatColor.WHITE
						+ "Custom fishing is granted to fishermen, allowing them to catch many "
						+ "different items as they become more advanced. Higher level fishermen have higher chance to catch better items with more quantity. "
						+ "Reaching certain levels as a fisherman unlocks new tiers, which allows them to catch new sets of items. "
						+ ChatColor.GOLD + "To display the Fishing Tiers and items type: " + ChatColor.RED
						+ "/faq fishing list");
			} else if (args[0].equalsIgnoreCase("fishing") && args.length > 1 && args[1].equalsIgnoreCase("list")) {
				sender.sendMessage(ChatColor.GOLD + "Fishing List: ");
				sender.sendMessage(ChatColor.AQUA + "Tier 1: Level " + FishList.TIER1_LEVEL);
				for (FishItemSet fis : FishList.fishListTier1) {
					sender.sendMessage(ChatColor.WHITE + fis.getMaterial().toString());
				}
				sender.sendMessage(ChatColor.AQUA + "Tier 2: Level " + FishList.TIER2_LEVEL);
				for (FishItemSet fis : FishList.fishListTier2) {
					sender.sendMessage(ChatColor.WHITE + fis.getMaterial().toString());
				}
				sender.sendMessage(ChatColor.AQUA + "Tier 3: Level " + FishList.TIER3_LEVEL);
				for (FishItemSet fis : FishList.fishListTier3) {
					sender.sendMessage(ChatColor.WHITE + fis.getMaterial().toString());
				}
				sender.sendMessage(ChatColor.AQUA + "Tier 4: Level " + FishList.TIER4_LEVEL);
				for (FishItemSet fis : FishList.fishListTier4) {
					sender.sendMessage(ChatColor.WHITE + fis.getMaterial().toString());
				}
				sender.sendMessage(ChatColor.AQUA + "Tier 5: Level " + FishList.TIER5_LEVEL);
				for (FishItemSet fis : FishList.fishListTier5) {
					sender.sendMessage(ChatColor.WHITE + fis.getMaterial().toString());
				}
				sender.sendMessage(ChatColor.AQUA + "Tier 6: Level " + FishList.TIER6_LEVEL);
				for (FishItemSet fis : FishList.fishListTier6) {
					sender.sendMessage(ChatColor.WHITE + fis.getMaterial().toString());
				}
			} else if (args[0].equalsIgnoreCase("jobenchant") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "/Jobenchant: " + ChatColor.WHITE
						+ "/jobenchant is the command given to enchanters. It allows them enchant an item "
						+ "directly rather than have to use an enchantment table. As enchanters level up they "
						+ "get access to more enchantments. " + ChatColor.GOLD + "To display the Jobenchant list type: "
						+ ChatColor.RED + "/faq jobenchant list");
			} else if (args[0].equalsIgnoreCase("Jobenchant") && args.length > 1 && args[1].equalsIgnoreCase("list")) {

				int amtPerPage = 8;
				int page = 1;

				if (args.length > 2) {
					try {
						page = Integer.parseInt(args[2]);
					} catch (Exception E) {
					}
				}

				if (page < 1) {
					page = 1;
				}

				sender.sendMessage(ChatColor.GOLD + "Jobenchant List: " + ChatColor.RED + "Page " + page);
				for (int i = (page - 1) * amtPerPage; i < EnchantList.ENCHANTLIST.size(); i++) {
					if (i > ((page - 1) * amtPerPage) + amtPerPage) {
						break;
					}
					EnchantRecipe er = EnchantList.ENCHANTLIST.get(i);
					sender.sendMessage(ChatColor.AQUA + er.getName() + ChatColor.WHITE + " " + er.getLevel() + ", "
							+ er.getJob() + " Level: " + er.getJobLevel() + " ExpCost: " + er.getExpCost());
				}
				sender.sendMessage(ChatColor.GOLD + "For more: /faq jobenchant list " + (page + 1));
			} else if (args[0].equalsIgnoreCase("enderdragonspawning") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "EnderDragon Spawning: " + ChatColor.WHITE + "Level "
						+ ChatColor.RED + JobsListener.ENDERSPAWNING_LEVEL + " builders " + ChatColor.WHITE
						+ "can spawn EnderDragons in The End with Obsidian. Create an E out of Obsidian and then right click the very bottom left block "
						+ "with Flint and Steel. The E is 5 blocks tall and should take 11 obsidian total. "
						+ ChatColor.GOLD + "For more information type: " + ChatColor.RED + "/faq builder");
			} else if (args[0].equalsIgnoreCase("arrowrecipes") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Recipes: ");
				for (RecipeSet rs : CustomRecipes.arrowList) {
					sender.sendMessage(
							ChatColor.AQUA + rs.getName() + ChatColor.WHITE + ": " + rs.getRecipeDescription());
				}
			} else if (args[0].equalsIgnoreCase("builder") && args.length > 1 && args[1].equalsIgnoreCase("recipes")) {
				sender.sendMessage(ChatColor.GOLD + "Builder Recipes: " + ChatColor.WHITE
						+ "These recipes are only granted to players with the job: Builder.");
				for (RecipeSet rs : CustomRecipes.builderList) {
					sender.sendMessage(ChatColor.AQUA + rs.getName() + " - " + ChatColor.WHITE + " (" + rs.getJob()
							+ " level " + rs.getLevel() + ") Powerstones: " + rs.getPowerstones());
					sender.sendMessage(ChatColor.WHITE + "   " + rs.getRecipeDescription());
				}
			} else if (args[0].equalsIgnoreCase("farmer") && args.length > 1 && args[1].equalsIgnoreCase("recipes")) {
				sender.sendMessage(ChatColor.GOLD + "Builder Recipes: " + ChatColor.WHITE
						+ "These recipes are only granted to players with the job: Farmer.");
				for (RecipeSet rs : CustomRecipes.farmerList) {
					sender.sendMessage(ChatColor.AQUA + rs.getName() + " - " + ChatColor.WHITE + " (" + rs.getJob()
							+ " level " + rs.getLevel() + ") Powerstones: " + rs.getPowerstones());
					sender.sendMessage(ChatColor.WHITE + "   " + rs.getRecipeDescription());
				}
			} else if (args[0].equalsIgnoreCase("Bombs") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Bomb Recipes: " + ChatColor.WHITE
						+ "Most jobs get a single type of bomb, and there is a cooldown before you can use an additional bomb.");
				for (RecipeSet rs : CustomRecipes.bombList) {
					sender.sendMessage(ChatColor.AQUA + rs.getName() + " - " + ChatColor.WHITE + " (" + rs.getJob()
							+ " level " + rs.getLevel() + ") " + rs.getDescription());
					sender.sendMessage(ChatColor.WHITE + "   " + rs.getRecipeDescription());
				}
			} else if (args[0].toLowerCase().contains("monster") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Monsters: " + ChatColor.WHITE
						+ "The monsters in this server are unique and come in 5 difficulties: " + "Normal, "
						+ ChatColor.YELLOW + "Easy, " + ChatColor.GOLD + "Medium, " + ChatColor.RED + "Hard, "
						+ ChatColor.DARK_PURPLE + "Legendary. " + ChatColor.WHITE
						+ "Legendary monsters are rare andextremely dangerous, but "
						+ "when defeated they drop collectable trophies. "
						+ "When killing a unique monster of any kind they have a chance to drop powerstones. "
						+ ChatColor.GOLD + "To display more information type: " + ChatColor.RED
						+ "/faq powerstones, /faq trophy");
			} else if (args[0].equalsIgnoreCase("trophy") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Monsters: " + ChatColor.WHITE
						+ "Trophies are collectable items dropped by Legendary monsters. "
						+ "They grant bragging rights and proof of the monsters you have managed "
						+ "to defeat in combat. There are currently " + ChatColor.RED + MonsterList.trophyList.size()
						+ ChatColor.WHITE + " trophies in existance. " + ChatColor.GOLD
						+ "To display more information type: " + ChatColor.RED + "/faq monsters");
			} else if (args[0].equalsIgnoreCase("exptrading") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Exp Trading: " + ChatColor.WHITE
						+ "Right click an enchantment table with a Bucket "
						+ "or a Glass Bottle to create a Bucket o' EXP, or a Bottle o' EXP respectively. The Bucket is worth 1000 exp, and "
						+ "the bottle is worth 100 exp.");
			} else if (args[0].equalsIgnoreCase("customenchant") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Custom Enchant: " + ChatColor.WHITE
						+ "/customenchant is the command that allows a player to create items with "
						+ "enchantments not normally in minecraft. For example: Lifesteal, Wither Shot, Ice Aspect. "
						+ "Custom enchantments either cost regular minecraft exp, or powerstones. Powerstones are rare items dropped "
						+ "by custom monsters. To have access to custom enchantments you must be part of that specific job. "
						+ "However, Enchanters have access to every custom enchantment. " + ChatColor.GOLD
						+ "To display more information type: " + ChatColor.RED + "/customenchant, /faq powerstones");
			} else if (args[0].equalsIgnoreCase("powerstones") || args[0].equalsIgnoreCase("powerstone")) {
				sender.sendMessage(ChatColor.GOLD + "Powerstones: " + ChatColor.WHITE);
				sender.sendMessage("Powerstones are dropped by custom monsters. ");
				sender.sendMessage("Powerstones can be used to give your item a random enchantment. " + ChatColor.GREEN
						+ " (/powerstone enchant) " + ChatColor.WHITE);
				sender.sendMessage("Powerstones can convert item enchantments into book enchantments. "
						+ ChatColor.GREEN + "(/powerstone unenchant) " + ChatColor.WHITE);
				sender.sendMessage("Powerstones can be converted into Job Experience. " + ChatColor.GREEN
						+ " (/powerstone exp <jobname>) " + ChatColor.WHITE);
				sender.sendMessage("Powerstones can create custom enchantments such as Lifesteal. " + ChatColor.GREEN
						+ " (/Customenchant) " + ChatColor.WHITE);
				sender.sendMessage("Powerstones can be used by builders to change Mobspawner types." + ChatColor.GREEN
						+ " (/faq builder recipes) " + ChatColor.WHITE);
				sender.sendMessage("Powerstones can be used by Blacksmiths to repair items." + ChatColor.GREEN
						+ " (/powerstone, /powerstone repair, /powerstone repairall) " + ChatColor.WHITE);
				sender.sendMessage(ChatColor.WHITE
						+ "Unfortunately, powerstones are rare and are most often found by the strongest monsters. "
						+ "Drop Chances: " + "Normal - " + MonsterList.PSTONE_NORMAL + "% " + ChatColor.YELLOW
						+ "Easy - " + MonsterList.PSTONE_EASY + "% " + ChatColor.GOLD + "Medium - "
						+ MonsterList.PSTONE_MEDIUM + "% " + ChatColor.RED + "Hard - " + MonsterList.PSTONE_HARD + "% "
						+ ChatColor.DARK_PURPLE + "Legendary - " + MonsterList.PSTONE_LEGENDARY + "% " + ChatColor.GOLD
						+ "To display more information type: " + ChatColor.RED
						+ "/powerstone, /faq monsters, /faq trophy, /faq customenchant, /faq builder");
			} else if (args[0].equalsIgnoreCase("money") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Ways to earn money: ");
				sender.sendMessage(ChatColor.AQUA + "1. Jobs: " + ChatColor.WHITE
						+ "Earn money by completing various job tasks. (/faq jobs)");
				sender.sendMessage(ChatColor.AQUA + "2. Quests: " + ChatColor.WHITE
						+ "Complete quests offered by NPC's around the town. (/faq quests)");
				sender.sendMessage(ChatColor.AQUA + "2. Maze: " + ChatColor.WHITE
						+ "Collect items and money while completing the maze, (/warp maze).");
				sender.sendMessage(ChatColor.AQUA + "4. Shop: " + ChatColor.WHITE
						+ "Warp to the wild (/randomlocation or /warp wild), collect items, and sell them to the store (/warp shop)");
				sender.sendMessage(ChatColor.AQUA + "5. Market: " + ChatColor.WHITE
						+ "Sell items to other players using /market, /market listings, /market help, (/faq market)");
				sender.sendMessage(ChatColor.AQUA + "6. PVP. : " + ChatColor.WHITE
						+ "Kill other players in the Wild or BendingArena, players gain money for killing each other.");
				sender.sendMessage(ChatColor.AQUA + "7. Mobarena. : " + ChatColor.WHITE
						+ "Kill monsters and collect items in the Mobarena (/warp mobarena), this works best if you are a Warrior or Survivalist (/faq jobs).");
				sender.sendMessage(ChatColor.AQUA + "8. Voting. : " + ChatColor.WHITE
						+ "Gain $50 for every server vote, go to www.bendcraft.enjin.com (lower left hand corner). If you are cracked you won't be able to vote on all the links.");

			} else if (args[0].equalsIgnoreCase("market") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Market: " + ChatColor.WHITE
						+ "Players can sell and buy items with each other using the Market. "
						+ "To see available items type /market listings. " + "To sell an item do /market create. "
						+ "To collect an item or money do /market mail. " + ChatColor.GOLD
						+ "To display more information type: " + ChatColor.RED + "/market help");
			} else if (args[0].equalsIgnoreCase("quests") && args.length == 1) {
				sender.sendMessage(
						ChatColor.GOLD + "Quests: " + ChatColor.WHITE + "Quests are offered by NPC's around the spawn. "
								+ "NPC's that offer quests have flames that float around them every couple seconds. "
								+ "RIGHT CLICK the NPC to begin the conversation and receive the quest. "
								+ "Type /quests to see which quests you have already accepted. "
								+ "Avoid quests in the quest area, they are much harder than the easy quests.");
			} else if (args[0].equalsIgnoreCase("ranks") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Ranks: " + ChatColor.WHITE
						+ "WhiteLotus - Top 5 most active players of the week that are of the Member rank. "
						+ "Top Voter - vote 24 times, AirMonk/Waterspirit/EarthKing/FireLord - Win a Bending tournament on saturday. "
						+ "PVP Master - Win the sunday pvp tournament. FireFerret - Rank top 3 in PBA matches. "
						+ "Soldier/Captain/General/AirMaster/WaterMaster/EarthMaster/FireMaster - Donate. "
						+ "Helper(Staff) - play for at least 3 months and be recommended by 3 other staff members.");
			} else if (args[0].equalsIgnoreCase("Minigames") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Minigames: " + ChatColor.WHITE
						+ "MobArena - Fight monsters for item rewards (/ma join), Spleef - Cash reward (/spleef join). "
						+ "Deathmatch - PVP for a cash reward (/dm join), Dungeons - Search dungeons for cash/item reward signs. "
						+ "Maze - Cash/Item rewards, Skyblock - just for fun.");
			} else if (args[0].equalsIgnoreCase("Challenge") && args.length == 1) {
				sender.sendMessage(ChatColor.GOLD + "Challenge: " + ChatColor.WHITE
						+ "Warriors: Place a Coal, IronIngot, GoldIngot, or Diamond into an ItemFrame and right click the "
						+ "frame with a Skull, a monster will spawn. Depending on the item the monster will be easy, medium, hard, or legendary, "
						+ "and there is a cooldown on each item. " + ChatColor.RED + "Warrior Levels" + ChatColor.WHITE
						+ ": " + ChatColor.YELLOW + JobsListener.CHALLENGE_LEVELS[0] + ChatColor.WHITE + ", "
						+ ChatColor.GOLD + JobsListener.CHALLENGE_LEVELS[1] + ChatColor.WHITE + ", "
						+ ChatColor.DARK_RED + JobsListener.CHALLENGE_LEVELS[2] + ChatColor.WHITE + ", "
						+ ChatColor.DARK_PURPLE + JobsListener.CHALLENGE_LEVELS[3] + ChatColor.WHITE + ".");
			} else {
				displayTopics(sender);
			}
		}
	}

	public void displayTopics(CommandSender sender) {
		String s = ChatColor.GOLD + "Topics: " + ChatColor.WHITE;
		ChatColor color = ChatColor.WHITE;
		for (int i = 0; i < topics.length; i++) {
			s += color;
			s += topics[i].toString();
			s += ", ";
			color = color == ChatColor.WHITE ? ChatColor.LIGHT_PURPLE : ChatColor.WHITE;
		}

		sender.sendMessage(s);
	}
}
