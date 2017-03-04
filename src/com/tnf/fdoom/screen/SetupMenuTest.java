package com.tnf.fdoom.screen;

import com.tnf.fdoom.GameContainer;
import com.tnf.fdoom.GameSetup;
import com.tnf.fdoom.gfx.Color;
import com.tnf.fdoom.gfx.Font;
import com.tnf.fdoom.gfx.Screen;
import com.tnf.fdoom.handlers.Handler;
import com.tnf.fdoom.sound.Sound;

public class SetupMenuTest extends Menu {
	private Menu parent;
	
	private int selected = 0;
	private GameSetup setup;
	
	public static int DEFAULT_TEXT_COLOR = Color.get(-1, 555, 555, 555);
	public static int DEFAULT_TITLE_COLOR = Color.get(112, 445, 445, 445);
	public static int DEFAULT_BACKGROUND_COLOR = Color.get(112, 112, 112, 112);
	public static int DEFAULT_BORDER_COLOR = Color.get(-1, 2, 112, 445);
	
	boolean onoff;
	
	public SetupMenuTest(Menu parent) {
		this.parent = parent;
		
		this.setup = GameContainer.getInstance().getSetup();
	}

	public void tick() {
		onoff = false;
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
				Handler.readConfig(Handler.FOW);
				Boolean isFog = Boolean.valueOf(Handler.Result);
				if (isFog){
					Handler.writeConfig(Handler.FOW, "false");
				}else{
					Handler.writeConfig(Handler.FOW, "true");
				}
			}
		}
	}

	public void render(Screen screen) {
		//screen.clear(0);
		Font.renderFrame(screen, "Game Options", 4, 1, 32, 20,
				DEFAULT_BACKGROUND_COLOR, DEFAULT_BORDER_COLOR,
				DEFAULT_TITLE_COLOR);

		int marginX = 50;
		//Font.draw("Setup game", screen, marginX + 4 * 8 + 4, 1 * 8, Color.get(0, 555, 555, 555));
		Font.draw("C change, X back", screen, marginX - 10, screen.h - 45, DEFAULT_TITLE_COLOR);

		int yo = 30;
		int checkboxColorTrue = Color.get(0, -1, 353, 454);
		int checkboxColorFalse = Color.get(0, -1, 533, 544);
		int optionColorActive = Color.get(-1, 555, 555, 555);
		int optionColorInactive = Color.get(-1, 333, 333, 333);
		
		// Fog of war

		Handler.readConfig(Handler.FOW);
		if (Boolean.valueOf(Handler.Result)){
			screen.render(marginX, yo, 0 + 1 * 32, checkboxColorTrue, 0);
		}else{
			screen.render(marginX, yo, 0 + 1 * 32, checkboxColorFalse, 0);
		}
		
		Font.draw("Disable fog of war", screen, marginX + 15, yo, selected == 0 ? optionColorActive : optionColorInactive);
	}
}
