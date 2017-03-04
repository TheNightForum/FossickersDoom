package com.tnf.fdoom;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.*;
import java.security.AccessControlException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.tnf.fdoom.handlers.Data;
import com.tnf.fdoom.handlers.Logger;
import com.tnf.fdoom.screen.SplashMenu;

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
	public static Game game;
	
	private static GameContainer singleton;
	
	private static JFrame jFrame;
	private static GameSetup setup;
	static String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());

	private GameContainer()
	{
		this.setup = new GameSetup();
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
	public static Game getGame()
	{
		return game;
	}
	
	/**
	 * Changes the currently active game.
	 * 
	 * @param game
	 */
	public static void setGame(Game game)
	{
		game = game;
	}
	
	/**
	 * Returns the current game setup.
	 * 
	 * @return
	 */
	public static GameSetup getSetup()
	{
		return setup;
	}
	
	/**
	 * Changes the game setup.
	 * 
	 * @param setup
	 */
	public static void setSetup(GameSetup setup)
	{
		setup = setup;
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
	public static void startGame()
	{
		game.setMinimumSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));
		game.setMaximumSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));
		game.setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));
		
		jFrame.add(game, BorderLayout.CENTER);
		jFrame.pack();
		
		game.start();
	}
	
	/**
	 * Stops the currently active game and removes it from the UI window.
	 */
	public static void stopGame()
	{
		game.stop();
		jFrame.remove(game);
	}
	

	
	/**
	 * Displays a "Save as" dialog for the user to choose a savegame filename,
	 * then writes the active game to the file.
	 */
	public static void saveGame(String worldname){
		//TODO check if folder exists. If-not create it.
		try{
			File file = new File(Data.locationSaves + worldname + "/game");
			System.out.println("Saving: " + file + ".\n");
			try{
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);

				out.writeObject(game);
				out.close();
				fileOut.close();

			} catch(FileNotFoundException e) {
				e.printStackTrace();
				Logger.printLine("Could not find File: " + worldname, Logger.ERROR);
			} catch (IOException e) {
				e.printStackTrace();
				Logger.printLine("Could not open/write File: " + worldname, Logger.ERROR);
			}
		} catch (AccessControlException e) {
			// no saving for you!
			e.printStackTrace();
		}
	}
	
	/**
	 * Displays an "Open file" dialog for the user to choose a savegame file,
	 * then deserializes the saved game and swaps it with the current one.
	 * The loaded game is then started.
	 */
	public static void loadGame(String worldname){
		try{
			File file = new File(Data.locationSaves + worldname + "/game");
			System.out.println("Loading: " + file + ".\n");

			// load game from file
			Game newGame = null;
			try {
				FileInputStream fileIn = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fileIn);

				newGame = (Game)in.readObject();

				in.close();
				fileIn.close();
			} catch (ClassNotFoundException e) {
				Logger.printLine("Class not found, could not load game: " + worldname, Logger.ERROR);
				return;
			} catch(FileNotFoundException e) {
				Logger.printLine("Could not find file: " + worldname, Logger.ERROR);
				return;
			} catch (IOException e) {
				Logger.printLine("Could not open/read File: " + worldname, Logger.ERROR);
				return;
			}

			// swap games
			stopGame();
			game = newGame;
			game.loadGame();
			startGame();
		} catch (AccessControlException e) {
			Logger.printLine("No access to the requested file: " + worldname, Logger.ERROR);
		}
	}
	
	public static void main(String[] args)
	{
		GameContainer cont = GameContainer.getInstance();
		com.tnf.fdoom.handlers.Handler.main();
		cont.init();
		cont.startGame();
	}
}
