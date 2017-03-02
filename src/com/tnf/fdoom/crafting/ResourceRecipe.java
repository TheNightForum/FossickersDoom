package com.tnf.fdoom.crafting;

import com.tnf.fdoom.entity.Player;
import com.tnf.fdoom.item.ResourceItem;
import com.tnf.fdoom.item.resource.Resource;

public class ResourceRecipe extends Recipe {
	private Resource resource;
	private int count;

	public ResourceRecipe(Resource resource, int count) {
		super(new ResourceItem(resource, count));
		this.resource = resource;
		this.count = count;
	}
	
	public ResourceRecipe(Resource resource) {
		this(resource, 1);
	}

	public void craft(Player player) {
		player.inventory.add(0, new ResourceItem(resource, count));
	}
}
