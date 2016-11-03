package me.coolade.monstersplus.monsters;

import org.bukkit.block.Biome;

public class BiomeSets {
	public static Biome[] COLD = new Biome[] { Biome.COLD_BEACH, Biome.TAIGA_COLD, Biome.TAIGA_COLD_HILLS,
			Biome.MUTATED_TAIGA_COLD, Biome.FROZEN_OCEAN, Biome.FOREST_HILLS, Biome.FROZEN_RIVER, Biome.ICE_FLATS,
			Biome.ICE_MOUNTAINS, Biome.MUTATED_ICE_FLATS };
	public static Biome[] DESERT = new Biome[] { Biome.DESERT, Biome.DESERT_HILLS, Biome.MUTATED_DESERT, };
	public static Biome[] HOT = new Biome[] { Biome.DESERT, Biome.DESERT_HILLS, Biome.MUTATED_DESERT, Biome.HELL };
	public static Biome[] HELL = new Biome[] { Biome.HELL };
	public static Biome[] SWAMP = new Biome[] { Biome.SWAMPLAND, Biome.MUTATED_SWAMPLAND };
	public static Biome[] EXTREME_HILLS = new Biome[] { Biome.EXTREME_HILLS, Biome.EXTREME_HILLS_WITH_TREES,
			Biome.MUTATED_EXTREME_HILLS, Biome.MUTATED_EXTREME_HILLS_WITH_TREES, Biome.SMALLER_EXTREME_HILLS };
	public static Biome[] RIVER = new Biome[] { Biome.RIVER };
	public static Biome[] WATERFRONT = new Biome[] { Biome.RIVER, Biome.BEACHES, Biome.DEEP_OCEAN, Biome.OCEAN };

	private BiomeSets() {
	}
}
