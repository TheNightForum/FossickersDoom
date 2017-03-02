package com.tnf.fdoom.screen;

import com.tnf.fdoom.Game;
import com.tnf.fdoom.GameContainer;
import com.tnf.fdoom.gfx.Color;
import com.tnf.fdoom.gfx.Font;
import com.tnf.fdoom.gfx.Screen;
import com.tnf.fdoom.gfx.SpriteSheet;
import com.tnf.fdoom.handlers.Logger;
import com.tnf.fdoom.level.Level;
import com.tnf.fdoom.level.tile.Tile;
import com.tnf.fdoom.sound.Sound;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayMenu extends Menu {
	private int selected = 0;
	public static final int MINIGAME_WIDTH = Game.WIDTH >> 4;
	public static final int MINIGAME_HEIGHT = Game.HEIGHT >> 4;
	private Level miniGame;
	private Screen miniScreen;
	private int tickCount;

	public static int DEFAULT_TEXT_COLOR = Color.get(-1, 555, 555, 555);
	public static int DEFAULT_TITLE_COLOR = Color.get(112, 445, 445, 445);
	public static int DEFAULT_BACKGROUND_COLOR = Color.get(112, 112, 112, 112);
	public static int DEFAULT_BORDER_COLOR = Color.get(-1, 2, 112, 445);

	private AtomicBoolean miniLoaded;
	//private static final String[] options = { "Start game", "Load game", "How to play", "Setup", "About" };
	private static final String[] options = { "Continue", "New", "Load", "How to play", "Back"};
	public PlayMenu() {
		miniLoaded = new AtomicBoolean();
		miniLoaded.set(false);
		Thread thread = new Thread() {
			public void run() {
				miniGame = new Level(128, 128, 0, null);
				miniGame.trySpawn(10000);
				// simulate some history
				for (int i = 0; i < 1000; i++) {
					miniGame.tick();
				}
				miniLoaded.set(true);
			};
		};
		thread.start();

		try {
			miniScreen = new Screen(MINIGAME_WIDTH * 16, MINIGAME_HEIGHT * 16, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream("/icons.png"))));
		} catch (IOException e) {
			Logger.printLine("Oh no. Could not load Sprite for game...", Logger.ERROR);
		}
	}

	public void tick() {
		tickCount++;
		if (input.up.clicked) selected--;
		if (input.down.clicked) selected++;
		if (input.up.clicked) Sound.pickup.play();
		if (input.down.clicked) Sound.pickup.play();

		int len = options.length;
		if (selected < 0) selected += len;
		if (selected >= len) selected -= len;

		if (input.attack.clicked || input.menu.clicked) {
			if (selected == 0) {
				Sound.test.play();
				//game.setMenu(new PlayMenu());
			}
			if (selected == 1) {
				Sound.test.play();
				GeneratorMenu gen = new GeneratorMenu(this);
				gen.init(game, input);
				game.setMenu(gen);
			}
			if (selected == 2) {
				Sound.test.play();
				//game.setMenu(new AboutMenu());
			}
			if (selected == 3) {
				Sound.test.play();

			}
			if (selected == 4) {
				Sound.test.play();
				game.setMenu(new TitleMenu());
			}
		}
		if (miniLoaded.get()) {
			miniGame.tick();
			Tile.tickCount++;
		}
	}

	public void render(Screen screen) {
		screen.clear(0);
		int marginX = 10;
		if (miniLoaded.get()) {
			int xScroll = (int)(Math.cos((tickCount / 10000.0) * 2*Math.PI) * (miniGame.w * 8) / 2) + (miniGame.w * 8) / 2 + MINIGAME_WIDTH * 16 / 2;
			int yScroll = (int)(Math.sin((tickCount / 10000.0) * 2*Math.PI) * (miniGame.h * 8) / 2) + (miniGame.h * 8) / 2 + MINIGAME_HEIGHT * 16 / 2;
			miniGame.renderBackground(miniScreen, xScroll, yScroll);
			miniGame.renderSprites(miniScreen, xScroll, yScroll);
			miniScreen.copyRect(screen, 5, Game.HEIGHT - MINIGAME_HEIGHT*16 - 5, MINIGAME_WIDTH * 16, MINIGAME_HEIGHT * 16);
		}

		Font.renderFrame(screen, "", 8, 6, 28, 17,
				DEFAULT_BACKGROUND_COLOR, DEFAULT_BORDER_COLOR,
				DEFAULT_TITLE_COLOR);
		{ // game title
			int h = 2;
			int w = 14;
			int titleColor = Color.get(-1, 0, Color.rgb(0, 0, 0), Color.rgb(255, 0, 0));
			int xo = (screen.w - w * 8) / 2;
			int yo = 24;
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					screen.render(xo + x * 8, yo + y * 8, x + (y + 6) * 32, titleColor, 0);
				}
			}
		}

		// options
		for (int i = 0; i < options.length; i++) {
			String msg = options[i];
			int col = Color.get(-1, 222, 222, 222);
			if (i == selected) {
				msg = "> " + msg + " <";
				col = Color.get(-1, 555, 555, 555);
			}
			Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, (8 + i) * 12 - 22, col);
		}
	}
}
