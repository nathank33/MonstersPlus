package me.coolade.monstersplus;

import static com.sk89q.worldguard.bukkit.BukkitUtil.toVector;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.massivecraft.factions.engine.EngineMain;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.massivecore.ps.PS;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

import me.coolade.jobsplus.ArrowListener;
import me.coolade.jobsplus.BuilderCommand;
import me.coolade.jobsplus.CustomRecipes;
import me.coolade.jobsplus.JobsListener;
import me.coolade.jobsplus.OreRadar;
import me.coolade.jobsplus.PowerstoneCommand;
import me.coolade.jobsplus.Tracker;
import me.coolade.jobsplus.customenchanting.CustomEnchantCommand;
import me.coolade.jobsplus.customenchanting.CustomEnchantListener;
import me.coolade.jobsplus.customitems.CustomItemListener;
import me.coolade.jobsplus.customitems.CustomItems;
import me.coolade.jobsplus.customitems.CustomShapelessRecipes;
import me.coolade.jobsplus.enchanter.EnchantCommand;
import me.coolade.jobsplus.witchdoctor.BrewingListener;
import me.coolade.monstersplus.monsters.MonsterListener;
import me.coolade.monstersplus.monsters.MonsterSpawnCommand;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.minecraft.server.v1_10_R1.AttributeInstance;
import net.minecraft.server.v1_10_R1.EntityInsentient;
import net.minecraft.server.v1_10_R1.GenericAttributes;

public class MonstersPlus extends JavaPlugin {
	public static WorldGuardPlugin worldGuard;
	public static MonstersPlus plugin;
	public static CoreProtectAPI coreProtect;
	public static Permission permission = null;
	public static Economy economy = null;
	public static Chat chat = null;
	private final Logger logger = Logger.getLogger("Minecraft");

	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has Been Enabled!");
		worldGuard = getWorldGuard();
		coreProtect = getCoreProtect();
		setupPermissions();
		setupChat();
		setupEconomy();
		plugin = this;
		registerEvents();
		registerRecipes();
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(pdfFile.getName() + " Has Been Disabled!");
		BrewingListener.removeAllCauldrons();
	}

	public void registerEvents() {
		PluginManager manager = this.getServer().getPluginManager();
		manager.registerEvents(new MonsterListener(), this);
		manager.registerEvents(new JobsListener(), this);
		manager.registerEvents(new ArrowListener(), this);
		manager.registerEvents(new BrewingListener(), this);
		manager.registerEvents(new CustomEnchantListener(), this);
		manager.registerEvents(new CustomItemListener(), this);
		BrewingListener.removeGlobalCauldrons("world");
		// MonsterListener.startMonsterCleaningTask();
	}

	public void registerRecipes() {
		new CustomRecipes();
		new CustomShapelessRecipes();
		CustomItems.init();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("jobenchant")) {
			new EnchantCommand(sender, cmd, commandLabel, args);
		} else if (commandLabel.equalsIgnoreCase("oreradar")) {
			new OreRadar(sender, cmd, commandLabel, args);
		} else if (commandLabel.equalsIgnoreCase("track")) {
			new Tracker(sender, cmd, commandLabel, args);
		} else if (commandLabel.equalsIgnoreCase("faq")) {
			new FAQ(sender, cmd, commandLabel, args);
		} else if (commandLabel.equalsIgnoreCase("customenchant")) {
			new CustomEnchantCommand(sender, cmd, commandLabel, args);
		} else if (commandLabel.toLowerCase().contains("powerstone")) {
			new PowerstoneCommand(sender, cmd, commandLabel, args);
		} else if (commandLabel.equalsIgnoreCase("customspawn")) {
			new MonsterSpawnCommand(sender, cmd, commandLabel, args);
		} else if (commandLabel.equalsIgnoreCase("builder")) {
			new BuilderCommand(sender, cmd, commandLabel, args);
		}
		return true;
	}

	private WorldGuardPlugin getWorldGuard() {
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

		// WorldGuard may not be loaded
		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			return null; // Maybe you want throw an exception instead
		}
		return (WorldGuardPlugin) plugin;
	}

	private CoreProtectAPI getCoreProtect() {
		Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

		// Check that CoreProtect is loaded
		if (plugin == null || !(plugin instanceof CoreProtect)) {
			return null;
		}

		// Check that the API is enabled
		CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
		if (CoreProtect.isEnabled() == false) {
			return null;
		}

		// Check that a compatible version of the API is loaded
		if (CoreProtect.APIVersion() < 2) {
			return null;
		}

		return CoreProtect;
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return (chat != null);
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	public static Location getRandomValidLocation(Location loc, int xRad, int yRad, int zRad) {
		int MAX_ATTEMPTS = 25;
		Random randomGen = new Random();
		int randx = 0, randy = 0, randz = 0;
		double x = 0, y = 0, z = 0;

		for (int i = 0; i < MAX_ATTEMPTS; i++) {
			randx = randomGen.nextInt(xRad * 2) - xRad;
			randy = randomGen.nextInt(yRad * 2) - yRad;
			randz = randomGen.nextInt(zRad * 2) - zRad;
			Location tempLoc = new Location(loc.getWorld(), loc.getX() + randx, loc.getY() + randy, loc.getZ() + randz);

			if (tempLoc.getBlock().getType() == Material.AIR) {
				World theWorld = loc.getWorld();
				x = tempLoc.getX();
				y = tempLoc.getY();
				z = tempLoc.getZ();

				if (theWorld.getBlockAt(new Location(theWorld, x, y + 1, z)).getType() == Material.AIR) {
					;
				}
				{
					if (theWorld.getBlockAt(new Location(theWorld, x, y - 1, z)).getType() != Material.AIR) {
						return tempLoc;
					}
				}
			}
		}
		return loc;
	}

	public static void messageNearbyPlayers(Location loc, String message, int xRad, int yRad, int zRad) {
		ArrayList<String> nearbyPlayers = getNearbyPlayers(loc, xRad, yRad, zRad);
		for (int i = 0; i < nearbyPlayers.size(); i++) {
			Bukkit.getPlayer(nearbyPlayers.get(i)).sendMessage(message);
		}
	}

	public static ArrayList<String> getNearbyPlayers(Location loc, int xRad, int yRad, int zRad) {
		ArrayList<String> playerNames = new ArrayList<String>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getWorld() == loc.getWorld()) {
				Location playerLoc = player.getLocation();
				if (Math.abs(playerLoc.getX() - loc.getX()) < xRad) {
					if (Math.abs(playerLoc.getY() - loc.getY()) < yRad) {
						if (Math.abs(playerLoc.getZ() - loc.getZ()) < zRad) {
							playerNames.add(player.getName());
						}
					}
				}
			}
		}
		return playerNames;
	}

	public static void delayThread(final int ticks, final String world) {
		MonstersPlus.plugin.getServer().getScheduler().scheduleSyncDelayedTask(MonstersPlus.plugin, new Runnable() {
			@Override
			public void run() {
				int destinationTime = (int) Bukkit.getWorld(world).getTime() + ticks;
				while ((int) Bukkit.getWorld(world).getTime() < destinationTime) {
				}
			}
		}, 0);
	}

	public static boolean randomChance(double chance) {
		Random randomGen = new Random();
		double rand = randomGen.nextDouble() * 100;
		if (rand <= chance) {
			return true;
		}
		return false;
	}

	public static Double getSpeed(LivingEntity lent) {
		AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) lent).getHandle())
				.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
		return attributes.getValue();
	}

	public static void setSpeed(LivingEntity lent, double speed) {
		AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) lent).getHandle())
				.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
		attributes.setValue(speed);
	}

	public static Double getAttack(LivingEntity lent) {
		AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) lent).getHandle())
				.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE);
		return attributes.getValue();
	}

	public static void setAttack(LivingEntity lent, double attack) {
		AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) lent).getHandle())
				.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE);
		attributes.setValue(attack);
	}

	public static boolean isSpawnableLocation(CreatureSpawnEvent event) {
		// If the event has already been cancelled then return
		if (event.isCancelled()) {
			return false;
		}

		// If the monster was spawned by a plugin then return
		SpawnReason reason = event.getSpawnReason();
		if (reason.equals(SpawnReason.CUSTOM)) {
			return false;
		}

		Entity ent = event.getEntity();
		LivingEntity lent = (LivingEntity) ent;

		// If this entity is riding another one, we want to return
		if (lent.isInsideVehicle() || lent.isLeashed()) {
			return false;
		}

		return isSpawnableLocation(lent.getLocation());
	}

	@SuppressWarnings("deprecation")
	public static boolean isSpawnableLocation(Location loc) {
		String worldName = loc.getWorld().getName();
		if (!worldName.equalsIgnoreCase("world")) {
			return false;
		}

		// Worldguard protection
		if (worldGuard != null) {
			RegionManager rm = MonstersPlus.worldGuard.getRegionManager(loc.getWorld());
			com.sk89q.worldedit.Vector vec = toVector(loc);
			ApplicableRegionSet set = rm.getApplicableRegions(vec);
			if (!set.allows(DefaultFlag.MOB_SPAWNING)) {
				return false;
			}
		}

		// Factions protection
		if (Bukkit.getPluginManager().getPlugin("Factions") != null
				&& Bukkit.getPluginManager().getPlugin("mcore") != null) {
			Faction faction = BoardColl.get().getFactionAt(PS.valueOf(loc));
			if (!faction.getFlag(MFlag.getFlagMonsters())) {
				return false;
			}
		}

		return true;
	}

	@SuppressWarnings("deprecation")
	public static boolean isPVPLocation(Location loc) {
		// if(!loc.getWorld().getName().toString().equalsIgnoreCase("world"))
		// return false;

		// Worldguard protection
		if (worldGuard != null) {
			RegionManager rm = MonstersPlus.worldGuard.getRegionManager(loc.getWorld());
			com.sk89q.worldedit.Vector vec = toVector(loc);
			ApplicableRegionSet set = rm.getApplicableRegions(vec);
			if (!set.allows(DefaultFlag.PVP)) {
				return false;
			}
		}

		if (Bukkit.getPluginManager().getPlugin("Factions") != null
				&& Bukkit.getPluginManager().getPlugin("mcore") != null) {
			// Factions protection
			Faction faction = BoardColl.get().getFactionAt(PS.valueOf(loc));
			if (!faction.getFlag(MFlag.getFlagPvp())) {
				return false;
			}
		}

		return true;
	}

	@SuppressWarnings("deprecation")
	public static boolean isBuildLocation(Player player, Location loc) {
		if (!loc.getWorld().getName().toString().equalsIgnoreCase("world")) {
			return false;
		}

		if (player.isOp()) {
			return true;
		}

		// Worldguard protection
		if (worldGuard != null) {
			RegionManager rm = MonstersPlus.worldGuard.getRegionManager(loc.getWorld());
			com.sk89q.worldedit.Vector vec = toVector(loc);
			ApplicableRegionSet set = rm.getApplicableRegions(vec);
			LocalPlayer lplayer = worldGuard.wrapPlayer(player);
			if (!set.canBuild(lplayer)) {
				return false;
			}
		}

		// Factions protection
		if (Bukkit.getPluginManager().getPlugin("Factions") != null
				&& Bukkit.getPluginManager().getPlugin("mcore") != null) {
			if (!EngineMain.canPlayerBuildAt(player, PS.valueOf(loc.getBlock()), true)) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public static boolean isEnderpearlAllowed(Location loc, Player player) {
		if (player.isOp()) {
			return true;
		}
		// Worldguard protection
		if (worldGuard != null) {
			RegionManager rm = MonstersPlus.worldGuard.getRegionManager(loc.getWorld());
			com.sk89q.worldedit.Vector vec = toVector(loc);
			ApplicableRegionSet set = rm.getApplicableRegions(vec);
			LocalPlayer lplayer = worldGuard.wrapPlayer(player);
			return set.allows(DefaultFlag.ENDERPEARL, lplayer);
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public static boolean isEnderpearlAllowed(Location loc) {
		// Worldguard protection
		if (worldGuard != null) {
			RegionManager rm = MonstersPlus.worldGuard.getRegionManager(loc.getWorld());
			com.sk89q.worldedit.Vector vec = toVector(loc);
			ApplicableRegionSet set = rm.getApplicableRegions(vec);
			return set.allows(DefaultFlag.ENDERPEARL);
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public static boolean isFlagAllowed(Location loc, StateFlag flag) {
		if (worldGuard != null) {
			RegionManager rm = MonstersPlus.worldGuard.getRegionManager(loc.getWorld());
			com.sk89q.worldedit.Vector vec = toVector(loc);
			ApplicableRegionSet set = rm.getApplicableRegions(vec);
			return set.allows(flag);
		}
		return true;
	}

	public static boolean isTargetting(TargetReason reason) {
		// Bukkit.broadcastMessage(reason.toString());
		if (reason.equals(TargetReason.TARGET_DIED)) {
			return false;
		} else if (reason.equals(TargetReason.FORGOT_TARGET)) {
			return false;
		} else {
			return true;
		}
	}

	public static void pullEntity(Entity ent, Location loc, final double power) {
		// Add the players vector to a force vector from the pull
		if (ent.getWorld() == loc.getWorld()) {
			Vector playerVec = ent.getLocation().toVector();
			Vector destinationVec = playerVec.subtract(loc.toVector());
			Vector normVec = destinationVec.normalize();
			ent.setVelocity(playerVec.add(normVec.multiply(power * -1)));
		}
	}
}
