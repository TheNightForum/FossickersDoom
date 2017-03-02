package com.tnf.fdoom.entity;

import com.tnf.fdoom.crafting.Crafting;
import com.tnf.fdoom.gfx.Color;
import com.tnf.fdoom.screen.CraftingMenu;

public class Workbench extends Furniture {
	public Workbench() {
		super("Workbench");
		col = Color.get(-1, 100, 321, 431);
		sprite = 4;
		xr = 3;
		yr = 2;
	}

	public boolean use(Player player, int attackDir) {
		player.game.setMenu(new CraftingMenu(Crafting.workbenchRecipes, player));
		return true;
	}
}