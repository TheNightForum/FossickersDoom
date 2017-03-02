package com.tnf.fdoom.crafting;

import com.tnf.fdoom.entity.Player;
import com.tnf.fdoom.item.ToolItem;
import com.tnf.fdoom.item.ToolType;

public class ToolRecipe extends Recipe {
	private ToolType type;
	private int level;

	public ToolRecipe(ToolType type, int level) {
		super(new ToolItem(type, level));
		this.type = type;
		this.level = level;
	}

	public void craft(Player player) {
		player.inventory.add(0, new ToolItem(type, level));
	}
}
