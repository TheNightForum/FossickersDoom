package com.tnf.fdoom.level.tile;

import com.tnf.fdoom.entity.AirWizard;
import com.tnf.fdoom.entity.Entity;
import com.tnf.fdoom.entity.Player;
import com.tnf.fdoom.entity.particle.SmashParticle;
import com.tnf.fdoom.entity.particle.TextParticle;
import com.tnf.fdoom.gfx.Color;
import com.tnf.fdoom.gfx.Screen;
import com.tnf.fdoom.item.Item;
import com.tnf.fdoom.item.ToolItem;
import com.tnf.fdoom.item.ToolType;
import com.tnf.fdoom.level.Level;

public class CloudCactusTile extends Tile {
	public CloudCactusTile(int id) {
		super(id);
	}

	public void render(Screen screen, Level level, int x, int y) {
		int color = Color.get(444, 111, 333, 555);
		screen.render(x * 16 + 0, y * 16 + 0, 17 + 1 * 32, color, 0);
		screen.render(x * 16 + 8, y * 16 + 0, 18 + 1 * 32, color, 0);
		screen.render(x * 16 + 0, y * 16 + 8, 17 + 2 * 32, color, 0);
		screen.render(x * 16 + 8, y * 16 + 8, 18 + 2 * 32, color, 0);
	}

	public boolean mayPass(Level level, int x, int y, Entity e) {
		if (e instanceof AirWizard) return true;
		return false;
	}

	public void hurt(Level level, int x, int y, Entity source, int dmg, int attackDir) {
		hurt(level, x, y, 0);
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, int attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (ToolType.pickaxe.equals(tool.type)) {
				if (player.payStamina(6 - tool.level)) {
					hurt(level, xt, yt, 1);
					return true;
				}
			}
		}
		return false;
	}

	public void hurt(Level level, int x, int y, int dmg) {
		int damage = level.getData(x, y) + 1;
		level.add(new SmashParticle(x * 16 + 8, y * 16 + 8));
		level.add(new TextParticle("" + dmg, x * 16 + 8, y * 16 + 8, Color.get(-1, 500, 500, 500)));
		if (dmg > 0) {
			if (damage >= 10) {
				level.setTile(x, y, cloud, 0);
			} else {
				level.setData(x, y, damage);
			}
		}
	}

	public void bumpedInto(Level level, int x, int y, Entity entity) {
		if (entity instanceof AirWizard) return;
		entity.hurt(this, x, y, 3);
	}
}