package com.tnf.fdoom.level.tile;

import com.tnf.fdoom.entity.Entity;
import com.tnf.fdoom.entity.ItemEntity;
import com.tnf.fdoom.entity.Player;
import com.tnf.fdoom.gfx.Color;
import com.tnf.fdoom.gfx.Screen;
import com.tnf.fdoom.item.Item;
import com.tnf.fdoom.item.ResourceItem;
import com.tnf.fdoom.item.ToolItem;
import com.tnf.fdoom.item.ToolType;
import com.tnf.fdoom.item.resource.Resource;
import com.tnf.fdoom.level.Level;
import com.tnf.fdoom.sound.Sound;

public class GrassTile extends Tile {
	public GrassTile(int id) {
		super(id);
		connectsToGrass = true;
	}

	public void render(Screen screen, Level level, int x, int y) {
		int col = Color.get(level.grassColor, level.grassColor, level.grassColor + 111, level.grassColor + 111);
		int transitionColor = Color.get(level.grassColor - 111, level.grassColor, level.grassColor + 111, level.dirtColor);

		boolean u = !level.getTile(x, y - 1).connectsToGrass;
		boolean d = !level.getTile(x, y + 1).connectsToGrass;
		boolean l = !level.getTile(x - 1, y).connectsToGrass;
		boolean r = !level.getTile(x + 1, y).connectsToGrass;

		if (!u && !l) {
			screen.render(x * 16 + 0, y * 16 + 0, 0, col, 0);
		} else
			screen.render(x * 16 + 0, y * 16 + 0, (l ? 11 : 12) + (u ? 0 : 1) * 32, transitionColor, 0);

		if (!u && !r) {
			screen.render(x * 16 + 8, y * 16 + 0, 1, col, 0);
		} else
			screen.render(x * 16 + 8, y * 16 + 0, (r ? 13 : 12) + (u ? 0 : 1) * 32, transitionColor, 0);

		if (!d && !l) {
			screen.render(x * 16 + 0, y * 16 + 8, 2, col, 0);
		} else
			screen.render(x * 16 + 0, y * 16 + 8, (l ? 11 : 12) + (d ? 2 : 1) * 32, transitionColor, 0);
		if (!d && !r) {
			screen.render(x * 16 + 8, y * 16 + 8, 3, col, 0);
		} else
			screen.render(x * 16 + 8, y * 16 + 8, (r ? 13 : 12) + (d ? 2 : 1) * 32, transitionColor, 0);
	}

	public void tick(Level level, int xt, int yt) {
		if (random.nextInt(40) != 0) return;

		int xn = xt;
		int yn = yt;

		if (random.nextBoolean())
			xn += random.nextInt(2) * 2 - 1;
		else
			yn += random.nextInt(2) * 2 - 1;

		if (level.getTile(xn, yn).equals(Tile.dirt)) {
			level.setTile(xn, yn, this, 0);
		}
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, int attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (ToolType.shovel.equals(tool.type)) {
				if (player.payStamina(4 - tool.level)) {
					level.setTile(xt, yt, Tile.dirt, 0);
					Sound.play("monsterHurt");
					if (random.nextInt(5) == 0) {
						level.add(new ItemEntity(new ResourceItem(Resource.seeds), xt * 16 + random.nextInt(10) + 3, yt * 16 + random.nextInt(10) + 3));
						return true;
					}
				}
			}
			if (ToolType.hoe.equals(tool.type)) {
				if (player.payStamina(4 - tool.level)) {
					Sound.play("monsterHurt");
					if (random.nextInt(5) == 0) {
						level.add(new ItemEntity(new ResourceItem(Resource.seeds), xt * 16 + random.nextInt(10) + 3, yt * 16 + random.nextInt(10) + 3));
						return true;
					}
					level.setTile(xt, yt, Tile.farmland, 0);
					return true;
				}
			}
		}
		return false;

	}
	
	public int getVisibilityBlocking(Level level, int x, int y, Entity e) {
		return 5;
	}
	
	@Override
	public int getFireFuelAmount(Level level, int xt, int yt)
	{
		return 1;
	}

	@Override
	public void burnFireFuel(Level level, int xt, int yt, int burnPower,
			Entity ent)
	{
		level.setTile(xt, yt, Tile.dirt, 0);
	}
}
