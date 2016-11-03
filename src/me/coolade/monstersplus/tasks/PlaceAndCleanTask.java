package me.coolade.monstersplus.tasks;

import org.bukkit.Location;
import org.bukkit.Material;

@SuppressWarnings("unused")
public class PlaceAndCleanTask {
	private int placeDelay = 0;
	private int cleanDelay = 0;
	private Material blockType;
	private Location loc;

	public PlaceAndCleanTask(Material blockType, Location loc, int placeDelay, int cleanDelay) {
		this.placeDelay = placeDelay;
		this.cleanDelay = cleanDelay;
		this.blockType = blockType;
		this.loc = loc;
		new PlaceBlockTask(blockType, loc, placeDelay);
		new CleanBlockTask(blockType, Material.AIR, loc, cleanDelay);
	}
}
