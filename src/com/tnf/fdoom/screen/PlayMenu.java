package com.tnf.fdoom.screen;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.imageio.ImageIO;

import com.tnf.fdoom.Game;
import com.tnf.fdoom.GameContainer;
import com.tnf.fdoom.gfx.Color;
import com.tnf.fdoom.gfx.Font;
import com.tnf.fdoom.gfx.Screen;
import com.tnf.fdoom.gfx.SpriteSheet;
import com.tnf.fdoom.handlers.Handler;
import com.tnf.fdoom.handlers.Logger;
import com.tnf.fdoom.sound.Sound;
import com.tnf.fdoom.level.Level;
import com.tnf.fdoom.level.tile.Tile;

public class PlayMenu extends Menu {
	private int selected = 0;
	public static final int MINIGAME_WIDTH = Game.WIDTH >> 4;
	public static final int MINIGAME_HEIGHT = Game.HEIGHT >> 4;
	private Level miniGame;
	private Screen miniScreen;
	private int tickCount;
	private Menu parent;
	
	public static int DEFAULT_TITLE_COLOR = Color.get(112, 445, 445, 445);
	public static int DEFAULT_BACKGROUND_COLOR = Color.get(112, 112, 112, 112);
	public static int DEFAULT_BORDER_COLOR = Color.get(-1, 2, 112, 445);
	
	private AtomicBoolean miniLoaded;
	
	private static final String[] options = { "Resume", "Start New", "Load Game", "Back" };
	
	public PlayMenu(Menu parent) {
		this.parent = parent;
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
			Logger.printLine("Somehow, we have managed to lose the res files. Could not load the menus. This is going to be fatal.", Logger.ERROR);
		}
	}

	public void tick() {
		
		tickCount++;
		if (input.up.clicked) selected--;
		if (input.down.clicked) selected++;
		
		int len = options.length;
		if (selected < 0) selected += len;
		if (selected >= len) selected -= len;

		if (input.attack.clicked || input.menu.clicked) {
			if(selected == 0){
				Sound.test.play();
				Handler.readConfig(Handler.LastWorld);
				String worldname = Handler.Result;
				GameContainer.loadGame(worldname);
			}
			if(selected == 1) game.setMenu(new NewWorldMenu());
			if(selected == 2) game.setMenu(new LoadWorldMenu(this));
			if(selected == 3) game.setMenu(parent);
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