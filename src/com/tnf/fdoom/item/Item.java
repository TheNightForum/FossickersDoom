package com.tnf.fdoom.item;

import java.io.Serializable;

import com.tnf.fdoom.entity.Entity;
import com.tnf.fdoom.entity.ItemEntity;
import com.tnf.fdoom.entity.Player;
import com.tnf.fdoom.gfx.Screen;
import com.tnf.fdoom.level.Level;
import com.tnf.fdoom.level.tile.Tile;
import com.tnf.fdoom.screen.ListItem;

public class Item implements ListItem, Serializable {
	
	private static final long serialVersionUID = 1L;

	public int getColor() {
		return 0;
	}

	public int getSprite() {
		return 0;
	}

	public void onTake(ItemEntity itemEntity) {
	}

	public void renderInventory(Screen screen, int x, int y) {
	}

	public boolean interact(Player player, Entity entity, int attackDir) {
		return false;
	}

	public void renderIcon(Screen screen, int x, int y) {
	}

	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir) {
		return false;
	}
	
	public boolean isDepleted() {
		return false;
	}

	public boolean canAttack() {
		return false;
	}

	public int getAttackDamageBonus(Entity e) {
		return 0;
	}

	public String getName() {
		return "";
	}

	public boolean matches(Item item) {
		return item.getClass() == getClass();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) {
			return false;
		}
		if (! (obj instanceof Item)) {
			return false;
		}
		Item item = (Item)obj;
		if (!this.getName().equals(item.getName())) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode()
	{
		return this.getName().hashCode();
	}
}