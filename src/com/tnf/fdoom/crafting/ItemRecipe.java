package com.tnf.fdoom.crafting;

import com.tnf.fdoom.GameContainer;
import com.tnf.fdoom.entity.Player;
import com.tnf.fdoom.handlers.Logger;
import com.tnf.fdoom.item.Item;

public class ItemRecipe extends Recipe
{
	private Class<? extends Item> clazz;

	public ItemRecipe(Class<? extends Item> clazz) throws InstantiationException, IllegalAccessException
	{
		super(clazz.newInstance());
		this.clazz = clazz;
	}

	public void craft(Player player)
	{
		try
		{
			player.inventory.add(0, clazz.newInstance());
		}
		catch (Exception e)
		{
			//throw new RuntimeException(e);
			Logger.printLine("Could not add item from recipe to player.", Logger.ERROR);
		}
	}

}