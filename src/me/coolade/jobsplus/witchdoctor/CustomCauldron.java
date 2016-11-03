package me.coolade.jobsplus.witchdoctor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.coolade.monstersplus.Tools;

@SuppressWarnings("deprecation")
public class CustomCauldron {
	private Location loc;
	private int currentStep;
	private int stirs;
	private int refills;
	private String playerName;
	private ArrayList<Material> mats = new ArrayList<Material>();

	public CustomCauldron(Location loc, String playerName) {
		this.loc = loc;
		this.playerName = playerName;
		this.currentStep = 0;
		this.stirs = 0;
		this.refills = 0;
	}

	public Location getLocation() {
		return loc;
	}

	public int getStirs() {
		return stirs;
	}

	public int getRefills() {
		return refills;
	}

	public int getCurrentStep() {
		return currentStep;
	}

	public String getPlayerName() {
		return playerName;
	}

	public ArrayList<Material> getMats() {
		return mats;
	}

	public void setLocation(Location t) {
		loc = t;
	}

	public void setCurrentStep(int t) {
		currentStep = t;
	}

	public void setStirs(int t) {
		stirs = t;
	}

	public void setRefills(int t) {
		refills = t;
	}

	public void setMats(ArrayList<Material> t) {
		mats = t;
	}

	public void setPlayerName(String t) {
		playerName = t;
	}

	public void clearMats() {
		mats.clear();
	}

	public void addMat(Material mat) {
		mats.add(mat);
	}

	public ItemStack generatePotion() {
		// Splits the mats list up into sections of 3, so that it can calculate
		// which type of potion it should generate. Then it is added into the
		// effects list
		// to create a new type of potion.
		boolean splash = false;
		ArrayList<Material> cloneMats = new ArrayList<Material>(mats);
		ArrayList<Material> tempMats = new ArrayList<Material>();
		ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();

		for (int i = 0; i < cloneMats.size(); i++) {
			tempMats.add(cloneMats.get(i));
			if (tempMats.size() == 3) {
				// If the 3 temp materials are a valid potion, then create the
				// potion effect
				if (isValidPotion(tempMats)) {
					PotionEffect temp = addCustomEffect(tempMats);
					if (temp != null) {
						effects.add(temp);
					}
					tempMats.clear();
				}
			}
			// Create a new potion effect with the leftovers
			else if (i == cloneMats.size() - 1) {
				if (isValidPotion(tempMats)) {
					PotionEffect temp = addCustomEffect(tempMats);
					if (temp != null) {
						effects.add(temp);
					}
					tempMats.clear();
				}
			}
		}

		// Use the list of potioneffects to add onto a potion
		if (mats.contains(BrewList.getIngredient("Splash").getMat())) {
			splash = true;
		}
		if (effects.size() > 0) {
			Random random = new Random();
			Potion potion = new Potion(random.nextInt(9) + 1);

			potion.setSplash(splash);
			ItemStack itemPotion = potion.toItemStack(1);

			PotionMeta potMeta = (PotionMeta) itemPotion.getItemMeta();
			potMeta.clearCustomEffects();

			for (int i = 0; i < effects.size(); i++) {
				potMeta.addCustomEffect(effects.get(i), true);
			}
			itemPotion.setItemMeta(potMeta);
			return itemPotion;
		}
		return null;
	}

	public PotionEffect addCustomEffect(List<Material> items) {
		int amplify = 0;
		int extend = 0;
		if (items.contains(BrewList.getIngredient("Amplify 2").getMat())) {
			amplify = 2;
		} else if (items.contains(BrewList.getIngredient("Amplify 1").getMat())) {
			amplify = 1;
		}
		if (items.contains(BrewList.getIngredient("Extend 2").getMat())) {
			extend = 2;
		} else if (items.contains(BrewList.getIngredient("Extend 1").getMat())) {
			extend = 1;
		}

		List<PotionEffectType> validEffects = BrewList.getNormalEffectTypes(items);
		if (validEffects.size() > 0) {

			// Generate the potion from one of the valid effect types
			Random random = new Random();
			PotionEffectType petype;
			if (validEffects.size() > 1) {
				petype = validEffects.get(random.nextInt(validEffects.size()));
			} else {
				petype = validEffects.get(0);
			}

			if (petype != null) {
				BrewIngredient bi = BrewList.getIngredient(petype);
				PotionEffect effect = new PotionEffect(bi.getEffect(), bi.getDurations()[extend] * 20,
						bi.getAmps()[amplify]);
				return effect;
			}
		}
		return null;
	}

	public boolean hasValidPotion() {
		for (Material mat : mats) {
			BrewIngredient bi = BrewList.getIngredient(mat);
			if (bi != null) {
				if (bi.getEffect() != null) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isValidPotion(List<Material> m) {
		for (Material mat : m) {
			BrewIngredient bi = BrewList.getIngredient(mat);
			if (bi != null) {
				if (bi.getEffect() != null) {
					return true;
				}
			}
		}
		return false;
	}

	public void dispenseItems() {
		for (int i = 0; i < mats.size(); i++) {
			loc.getWorld().dropItem(loc, new ItemStack(mats.get(i)));
			mats.remove(i);
			i--;
		}
	}

	public void remove() {
		Tools.removeNearbyEntity(loc, EntityType.ITEM_FRAME, 2);
		if (BrewingListener.cauldList.containsKey(loc)) {
			BrewingListener.cauldList.remove(loc);
		}
	}

	@Override
	public String toString() {
		return loc + " " + mats + " " + playerName;
	}
}
