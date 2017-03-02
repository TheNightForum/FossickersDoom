package com.tnf.fdoom.entity;

import com.tnf.fdoom.crafting.Crafting;
import com.tnf.fdoom.gfx.Color;
import com.tnf.fdoom.screen.CraftingMenu;

public class Furnace extends Furniture {
	public Furnace() {
		super("Furnace");
		col = Color.get(-1, 000, 222, 333);
		sprite = 3;
		xr = 3;
		yr = 2;
	}

	public boolean use(Player player, int attackDir) {
		player.game.setMenu(new CraftingMenu(Crafting.furnaceRecipes, player));
		return true;
	}
}