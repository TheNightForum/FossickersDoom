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

public class FlowerTile extends GrassTile {
	
	public static int[] flowerColors = new int[] {
		// basic
		555, // white
		// almost-basic
		455, 545, 554, 
		/*/ colored 
		510, 511, 521, 522, 523, 524, // red
		225, 135, // blue
		515, 525, // purple
		/**/
	};
	
	public FlowerTile(int id) {
		super(id);
		tiles[id] = this;
		connectsToGrass = true;
	}

	public void render(Screen screen, Level level, int x, int y) {
		super.render(screen, level, x, y);

		int data = level.getData(x, y);
		int shape = (data / 16) % 2;
		int headColor = flowerColors[(data*7 + x*13 + y*level.w*3)%flowerColors.length];
		int flowerCol = Color.get(10, level.grassColor, headColor, 440);

		if (shape == 0) screen.render(x * 16 + 0, y * 16 + 0, 1 + 1 * 32, flowerCol, 0);
		if (shape == 1) screen.render(x * 16 + 8, y * 16 + 0, 1 + 1 * 32, flowerCol, 0);
		if (shape == 1) screen.render(x * 16 + 0, y * 16 + 8, 1 + 1 * 32, flowerCol, 0);
		if (shape == 0) screen.render(x * 16 + 8, y * 16 + 8, 1 + 1 * 32, flowerCol, 0);
	}

	public boolean interact(Level level, int x, int y, Player player, Item item, int attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.shovel) {
				if (player.payStamina(4 - tool.level)) {
					level.add(new ItemEntity(new ResourceItem(Resource.flower), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
					level.add(new ItemEntity(new ResourceItem(Resource.flower), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
					level.setTile(x, y, grass, 0);
					return true;
				}
			}
		}
		return false;
	}

	public void hurt(Level level, int x, int y, Entity source, int dmg, int attackDir) {
		int count = random.nextInt(2) + 1;
		for (int i = 0; i < count; i++) {
			level.add(new ItemEntity(new ResourceItem(Resource.flower), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
		}
		level.setTile(x, y, grass, 0);
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
		level.setTile(xt, yt, dirt, 0);
	}
}