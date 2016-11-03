package me.coolade.jobsplus.customitems;

import java.util.ArrayList;

import org.bukkit.Material;

public class CustomShapelessRecipes {
	public static ArrayList<CustomShapelessRecipe> recipes = new ArrayList<CustomShapelessRecipe>() {
		private static final long serialVersionUID = 1L;

		{
			// add(new CustomShapelessRecipe("Bacon",Material.PORK,new
			// String[]{"Ribs","Turkey"}));

		}
	};

	public CustomShapelessRecipes() {
		/*
		 * ItemStack item; ShapelessRecipe recipe; for(CustomShapelessRecipe
		 * crecipe : recipes) { item = new ItemStack(crecipe.getMat(),
		 * crecipe.getAmount(), crecipe.getDurability()); ItemMeta meta =
		 * item.getItemMeta(); meta.setDisplayName(crecipe.getName());
		 * item.setItemMeta(meta);
		 * 
		 * recipe = new ShapelessRecipe(item); String[] ingredients =
		 * crecipe.getIngredients(); for(String s : ingredients) { CustomItemSet
		 * cis = CustomItems.getCustomItem(s);
		 * recipe.addIngredient(cis.getMat()); } Bukkit.addRecipe(recipe); }
		 */
	}

	public static CustomShapelessRecipe getRecipe(String name) {
		for (CustomShapelessRecipe recipe : recipes) {
			if (recipe.getName().equals(name)) {
				return recipe;
			}
		}
		return null;
	}

	public static class CustomShapelessRecipe {
		String name;
		Material mat;
		int amount;
		short durability;
		String[] ingredients;
		String displayName;
		String description;

		public CustomShapelessRecipe(String name, Material mat, int amount, short durability, String[] ingredients,
				String displayName, String description) {
			this.name = name;
			this.mat = mat;
			this.amount = amount;
			this.durability = durability;
			this.ingredients = ingredients;
			this.displayName = displayName;
			this.description = description;
		}

		public CustomShapelessRecipe(String name, Material mat, String[] ingredients) {
			this(name, mat, 1, (short) 0, ingredients, null, null);
		}

		public CustomShapelessRecipe(String name, Material mat, int amount, String[] ingredients) {
			this(name, mat, amount, (short) 0, ingredients, null, null);
		}

		public CustomShapelessRecipe(String name, Material mat, int amount, short durability, String[] ingredients) {
			this(name, mat, amount, durability, ingredients, null, null);
		}

		public String getName() {
			return name;
		}

		public String[] getIngredients() {
			return ingredients;
		}

		public String getDisplayName() {
			return displayName;
		}

		public String getDescription() {
			return description;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setIngredients(String[] ingredients) {
			this.ingredients = ingredients;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Material getMat() {
			return mat;
		}

		public void setMat(Material mat) {
			this.mat = mat;
		}

		public int getAmount() {
			return amount;
		}

		public short getDurability() {
			return durability;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public void setDurability(short durability) {
			this.durability = durability;
		}

	}
}
