package com.tnf.fdoom.screen;

import com.tnf.fdoom.Game;
import com.tnf.fdoom.GameContainer;
import com.tnf.fdoom.gfx.Color;
import com.tnf.fdoom.gfx.Font;
import com.tnf.fdoom.gfx.Screen;
import com.tnf.fdoom.sound.Sound;

public class TitleMenu extends Menu {
	private int selected = 0;

	private static final String[] options = { "Start game", "Load game", "How to play", "Setup", "About" };

	public TitleMenu() {
	}

	public void tick() {
		if (input.up.clicked) selected--;
		if (input.down.clicked) selected++;

		int len = options.length;
		if (selected < 0) selected += len;
		if (selected >= len) selected -= len;

		if (input.attack.clicked || input.menu.clicked) {
			if (selected == 0) {
				Sound.test.play();
				GeneratorMenu gen = new GeneratorMenu(this);
				gen.init(game, input);
				game.setMenu(gen);
			}
			if (selected == 1) {
				Sound.test.play();
				GameContainer.getInstance().loadGame();
			}
			if (selected == 2) game.setMenu(new InstructionsMenu(this));
			if (selected == 3) game.setMenu(new SetupMenu(this));
			if (selected == 4) game.setMenu(new AboutMenu(this));
		}
	}

	public void render(Screen screen) {
		screen.clear(0);

		{ // game title
			int h = 2;
			int w = 13;
			int titleColor = Color.get(0, 010, 131, 551);
			int xo = (screen.w - w * 8) / 2;
			int yo = 24;
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					screen.render(xo + x * 8, yo + y * 8, x + (y + 6) * 32, titleColor, 0);
				}
			}
			// version
			Font.draw("v"+Game.VERSION, screen, 2, 2, Color.get(0, 111, 111, 111));
		}
		
		{ // logo image
			int h = 8;
			int w = 7;
			int titleColor = Color.get(200, 421, 551, 0);
			int xo = (screen.w - w * 8) / 2;
			int yo = 48;
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					screen.render(xo + x * 8, yo + y * 8, (x+16) + (y + 6) * 32, titleColor, 0);
				}
			}
		}

		// options
		for (int i = 0; i < options.length; i++) {
			String msg = options[i];
			int col = Color.get(0, 222, 222, 222);
			if (i == selected) {
				msg = "> " + msg + " <";
				col = Color.get(0, 555, 555, 555);
			}
			Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, (10 + i) * 12, col);
		}

		Font.draw("(Arrow keys,X and C, F5 and F9)", screen, 0, screen.h - 8, Color.get(0, 111, 111, 111));
	}
}