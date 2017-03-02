package com.tnf.fdoom.entity;

import com.tnf.fdoom.crafting.Crafting;
import com.tnf.fdoom.gfx.Color;
import com.tnf.fdoom.screen.CraftingMenu;

/**
 * 		Brewery
 *
 * Enables the player to brew ale (maybe other things in the future).
 *
 * @author CrazyWolf
 */
public class Brewery extends Furniture {
	public Brewery() {
		super("Brewery");
		col = Color.get(-1, 000, 224, 335);
		sprite = 3;
		xr = 3;
		yr = 2;
	}

	public boolean use(Player player, int attackDir) {
		player.game.setMenu(new CraftingMenu(Crafting.breweryRecipes, player));
		return true;
	}
}
