package me.coolade.jobsplus.customitems;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CustomItemListener implements Listener {
	public static double PASSIVE_MOB_DROPRATE = 20;

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryClick(final InventoryClickEvent event) {
		/**
		 * Block Renaming to Custom items and invalid recipes Cancel any events
		 * in which the user is trying to modify a custom item in the anvil. If
		 * a player tries to cheat the custom recipes by using an invalid item
		 * then we must cancel the crafting.
		 */
		if (event.isCancelled()) {
			return;
		}

		Player player = (Player) event.getView().getPlayer();
		if (player == null) {
			return;
		}

		Inventory inv = event.getInventory();
		if (inv instanceof AnvilInventory) {
			AnvilInventory ainv = (AnvilInventory) inv;
			// if there is an item in slot 0 or slot 3 then we need to check its
			// name, and make sure
			// it is not a custom item. If one of the items is custom then we
			// need to close the inventory.
			ItemStack[] contents = ainv.getContents();
			for (ItemStack istack : contents) {
				if (istack != null) {
					if (CustomItems.isCustomItem(istack)) {
						event.setCancelled(true);
						player.closeInventory();
						player.sendMessage(ChatColor.RED + "You cannot create or rename custom items.");
						return;
					}
				}
			}
		}
		/**
		 * If it is a craftingInventory then we need to check to make sure that
		 * the user is not trying to cheat the system and provide the fake named
		 * ingredients. Check all the ingredients in the inventory with the
		 * ingredients for the specific recipe. Notes: When we get the contents
		 * of the CraftingInventory, it will include the resultant item aswell.
		 * For example, if the recipe was Turkey + Ribs = Bacon then the
		 * contents would have all the items. We need to make sure that the
		 * resultant item is only found 1 time in the contents.
		 */
		/*
		 * else if(inv instanceof CraftingInventory) { int slot =
		 * event.getSlot(); ItemStack istack = inv.getItem(slot); if(slot == 0
		 * && istack != null && istack.hasItemMeta() &&
		 * istack.getItemMeta().hasDisplayName()) { String resultName =
		 * istack.getItemMeta().getDisplayName();
		 * if(CustomItems.isCustomItem(resultName)) { CustomShapelessRecipe
		 * recipe = CustomShapelessRecipes.getRecipe(resultName); if(recipe ==
		 * null) return;
		 * 
		 * //Check all the ingredients String[] ingredients =
		 * recipe.getIngredients(); ItemStack[] contents = inv.getContents();
		 * int resultItemFoundCounter = 0;
		 * 
		 * for(ItemStack item : contents) // All of the items found in the
		 * crafting bench { if(item.getType() != Material.AIR) { boolean isValid
		 * = false; String invItemName = item.hasItemMeta() == true ?
		 * item.getItemMeta().getDisplayName() : item.getType().toString();
		 * //Check if the name of this specific item is inside of the
		 * ingredients list. for(String ingredientName : ingredients) // All of
		 * the required items for the specific recipe {
		 * if(ingredientName.equals(invItemName)) { isValid = true; break; } }
		 * if(invItemName.equals(recipe.getName())) { //Make sure that the
		 * result item is only found 1 time. resultItemFoundCounter++; isValid =
		 * resultItemFoundCounter == 1 ? true : false; }
		 * 
		 * if(!isValid) { event.setCancelled(true); player.closeInventory();
		 * player.sendMessage(ChatColor.RED + "This is an invalid recipe.");
		 * return; } } } } } }
		 */
	}
	/*
	 * @EventHandler(priority = EventPriority.NORMAL) public void
	 * onEntityDeath(EntityDeathEvent event) { Entity ent = event.getEntity();
	 * World world = ent.getWorld(); Random rand = new Random(); int
	 * passiveMobAmt = 1; if(Tools.randomChance(30)) passiveMobAmt++;
	 * 
	 * if(ent instanceof Cow){ if(Tools.randomChance(PASSIVE_MOB_DROPRATE)) {
	 * event.getDrops().clear();
	 * event.getDrops().add(CustomItems.createCustomItem("Ribs",
	 * passiveMobAmt)); } } else if(ent instanceof Chicken){
	 * if(Tools.randomChance(PASSIVE_MOB_DROPRATE)) { event.getDrops().clear();
	 * event.getDrops().add(CustomItems.createCustomItem("Turkey",
	 * passiveMobAmt)); } } else if(ent instanceof Sheep){
	 * if(Tools.randomChance(PASSIVE_MOB_DROPRATE)) { event.getDrops().clear();
	 * event.getDrops().add(CustomItems.createCustomItem("Raw Lamb",
	 * passiveMobAmt)); } } else if(ent instanceof Pig){
	 * if(Tools.randomChance(PASSIVE_MOB_DROPRATE)) { event.getDrops().clear();
	 * event.getDrops().add(CustomItems.createCustomItem("Ham", passiveMobAmt));
	 * } } else if(ent instanceof Squid){
	 * if(Tools.randomChance(PASSIVE_MOB_DROPRATE)) { event.getDrops().clear();
	 * event.getDrops().add(CustomItems.createCustomItem("Raw Squid",
	 * passiveMobAmt)); } } }
	 */
}
