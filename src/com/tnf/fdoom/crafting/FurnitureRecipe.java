package com.tnf.fdoom.crafting;

import com.tnf.fdoom.GameContainer;
import com.tnf.fdoom.entity.Furniture;
import com.tnf.fdoom.entity.Player;
import com.tnf.fdoom.handlers.Logger;
import com.tnf.fdoom.item.FurnitureItem;

public class FurnitureRecipe extends Recipe {
	private Class<? extends Furniture> clazz;

	public FurnitureRecipe(Class<? extends Furniture> clazz) throws InstantiationException, IllegalAccessException {
		super(new FurnitureItem(clazz.newInstance()));
		this.clazz = clazz;
	}

	public void craft(Player player) {
		try {
			player.inventory.add(0, new FurnitureItem(clazz.newInstance()));
		} catch (Exception e) {
			Logger.printLine("Could not give the player the selected furniture item.", Logger.ERROR);
		}
	}
}
