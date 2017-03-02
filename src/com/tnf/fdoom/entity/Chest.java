package com.tnf.fdoom.entity;

import com.tnf.fdoom.gfx.Color;
import com.tnf.fdoom.screen.ContainerMenu;

public class Chest extends Furniture {
	public Inventory inventory = new Inventory();

	public Chest() {
		super("Chest");
		col = Color.get(-1, 110, 331, 552);
		sprite = 1;
	}

	public boolean use(Player player, int attackDir) {
		player.game.setMenu(new ContainerMenu(player, "Chest", inventory));
		return true;
	}
}