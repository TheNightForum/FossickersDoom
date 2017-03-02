package com.tnf.fdoom.screen;

import com.tnf.fdoom.GameContainer;
import com.tnf.fdoom.GameSetup;
import com.tnf.fdoom.gfx.Color;
import com.tnf.fdoom.gfx.Font;
import com.tnf.fdoom.gfx.Screen;
import com.tnf.fdoom.sound.Sound;

public class SetupMenu extends Menu {
	private Menu parent;
	
	private int selected = 0;
	private GameSetup setup;
	
	public SetupMenu(Menu parent) {
		this.parent = parent;
		
		this.setup = GameContainer.getInstance().getSetup();
	}

	public void tick() {
		if (input.up.clicked) selected--;
		if (input.down.clicked) selected++;
		if (selected < 0) {
			selected = 0;
		}
		if (selected > 0) {
			selected = 0;
		}
		if (input.menu.clicked) {
			game.setMenu(parent);
		}
		if (input.attack.clicked) {
			// Fog of war
			if (selected == 0) {
				Sound.craft.play();
				setup.disableFogOfWar = !setup.disableFogOfWar;
			}
		}
	}

	public void render(Screen screen) {
		screen.clear(0);

		int marginX = 10;
		Font.draw("Setup game", screen, marginX + 4 * 8 + 4, 1 * 8, Color.get(0, 555, 555, 555));
		Font.draw("C change, X back", screen, 1, screen.h - 8, Color.get(0, 222, 222, 222));

		int yo = 30;
		int checkboxColorTrue = Color.get(121, 0, 353, 454);
		int checkboxColorFalse = Color.get(211, 0, 533, 544);
		int optionColorActive = Color.get(0, 555, 555, 555);
		int optionColorInactive = Color.get(0, 333, 333, 333);
		
		// Fog of war
		screen.render(marginX, yo, 0 + 1 * 32, setup.disableFogOfWar ? checkboxColorTrue : checkboxColorFalse, 0);
		Font.draw("Disable fog of war", screen, marginX + 15, yo, selected == 0 ? optionColorActive : optionColorInactive);
	}
}
