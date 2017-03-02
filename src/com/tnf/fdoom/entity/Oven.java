package com.tnf.fdoom.entity;

import com.tnf.fdoom.crafting.Crafting;
import com.tnf.fdoom.gfx.Color;
import com.tnf.fdoom.screen.CraftingMenu;

public class Oven extends Furniture {
	public Oven() {
		super("Oven");
		col = Color.get(-1, 000, 332, 442);
		sprite = 2;
		xr = 3;
		yr = 2;
	}

	public boolean use(Player player, int attackDir) {
		player.game.setMenu(new CraftingMenu(Crafting.ovenRecipes, player));
		return true;
	}
}