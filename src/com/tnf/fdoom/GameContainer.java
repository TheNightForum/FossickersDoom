package com.tnf.fdoom;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.AccessControlException;
import javax.swing.JFrame;

import com.tnf.fdoom.handlers.Data;
import com.tnf.fdoom.handlers.Handler;
import com.tnf.fdoom.screen.SplashMenu;
import com.tnf.fdoom.handlers.Logger;
/**
 * 		Game Container
 *
 * Main class used as a manager of a currently running game.
 *
 * Purpose, responsibilities, functions:
 *  - Entry-point for starting the game.
 *  - Creator and owner of the UI (JFrame).
 *  - Singleton interface, handles one "main" game (though the number of games
 *  	running at the same time is theoretically not limited by this).
 *  - Save and load function (+ GUI), swaps loaded game with the active one.
 *
 * @author CrazyWolf
 */
public class GameContainer
{
	private static GameContainer singleton;

	private JFrame jFrame;

	private Game game;
	private GameSetup setup;

	private GameContainer()
	{
		this.setup = new GameSetup();
		// TODO: load setup from file

		this.game = new Game();
		this.game.initGraphics();
		this.game.setMenu(new SplashMenu());
	}

	/**
	 * Returns a singleton instance of this class.
	 *
	 * @return singleton
	 */
	public static GameContainer getInstance()
	{
		if (singleton == null) {
			singleton = new GameContainer();
		}
		return singleton;
	}

	/**
	 * Returns the currently active game.
	 *
	 * @return game
	 */
	public Game getGame()
	{
		return this.game;
	}

	/**
	 * Changes the currently active game.
	 *
	 * @param game
	 */
	public void setGame(Game game)
	{
		this.game = game;
	}

	/**
	 * Returns the current game setup.
	 *
	 * @return
	 */
	public GameSetup getSetup()
	{
		return this.setup;
	}

	/**
	 * Changes the game setup.
	 *
	 * @param setup
	 */
	public void setSetup(GameSetup setup)
	{
		this.setup = setup;
	}

	/**
	 * Performs a one-time initialization of the environment.
	 * Namely it creates a UI window (JFrame) which is later used to display the game.
	 */
	public void init()
	{
		JFrame frame = new JFrame(Game.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);

		jFrame = frame;
	}

	/**
	 * The currently active game is attached to the UI window and then started.
	 */
	public void startGame()
	{
		game.setMinimumSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));
		game.setMaximumSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));
		game.setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));

		jFrame.add(game, BorderLayout.CENTER);
		jFrame.pack();

		this.game.start();
	}

	/**
	 * Stops the currently active game and removes it from the UI window.
	 */
	public void stopGame()
	{
		this.game.stop();
		this.jFrame.remove(game);
	}

	/**
	 * Displays a "Save as" dialog for the user to choose a savegame filename,
	 * then writes the active game to the file.
	 */
	public void saveGame(String worldname)
	{
		//TODO: check if folder exists. IF-NOT, create it.
		try{
			File file = new File(Data.locationSaves + worldname + "/game");
			Logger.printLine("Saving: " + file + ".");
			try{
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);

				out.writeObject(game);
				out.close();
				fileOut.close();
			} catch (FileNotFoundException e) {
				Logger.printLine(worldname + " File not found. Could not load game file.", Logger.ERROR);
			} catch (IOException e) {
				Logger.printLine("Could not read: " + worldname + ", therefor could nto load save.", Logger.ERROR);
			}
		} catch (AccessControlException e) {
			Logger.printLine("Could not save: " + worldname + " Due to access Control.", Logger.ERROR);
		}
	}

	/**
	 * Displays an "Open file" dialog for the user to choose a savegame file,
	 * then deserializes the saved game and swaps it with the current one.
	 * The loaded game is then started.
	 */
	public void loadGame(String worldname)
	{
		try{
			File file = new File(Data.locationSaves + worldname + "/game");
			Logger.printLine("Loading: " + file + ".");
			Game newGame = null;
			try {
				FileInputStream fileIn = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fileIn);

				newGame = (Game)in.readObject();

				in.close();
				fileIn.close();
			} catch (ClassNotFoundException e) {
				Logger.printLine("Could not find class while loading save.", Logger.ERROR);
				return;
			} catch (FileNotFoundException e) {
				Logger.printLine("Could not find file while loading save.", Logger.ERROR);
				return;
			} catch (IOException e) {
				Logger.printLine("Could not start file: " + worldname, Logger.ERROR);
			}
		} catch (AccessControlException e) {
			Logger.printLine("Could not load the game file: " + worldname, Logger.ERROR);
		}
	}

	public static void main(String[] args)
	{
		GameContainer cont = GameContainer.getInstance();
		Handler.main();
		cont.init();
		cont.startGame();
	}

}
