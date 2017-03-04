package com.tnf.fdoom.handlers;

import com.tnf.fdoom.Game;

import java.io.File;

public class Data {

	public static String OPERATING_SYSTEM = System.getProperty("os.name").toLowerCase();
	public static String BaseUrl = "https://TheNightForum.github.io/";
	public static String location = "";
	public static String locationSaves = "";
	public static String locationRes = "";
	public static String locationOptions = "";
	public static String locationLogs = "";
	public static String locationBin = "";
	static{
		if (OPERATING_SYSTEM.indexOf("win") >= 0) {
      Logger.printLine("User is on WINDOWS.");
      if (Game.isDev){
        location = System.getenv("APPDATA") + "\\fossickersdoom\\";
        locationBin = location + "bin\\";
        locationSaves = location + "saves\\";
        locationRes = location + "res\\";
        locationOptions = location + "options\\";
        locationLogs = location + "logs\\";
      }
		} else if (OPERATING_SYSTEM.indexOf("mac") >= 0) {
      Logger.printLine("User is on MAC.");
      if (Game.isDev){
  			location = System.getProperty( "user.home" ) + "/fossickersdoom/";
  			locationBin = location + "bin/";
  			locationSaves = location + "saves/";
  			locationRes = location + "res/";
  			locationOptions = location + "options/";
  			locationLogs = location + "logs/";
      }
		} else if (OPERATING_SYSTEM.indexOf("nix") >= 0 || OPERATING_SYSTEM.indexOf("nux") >= 0 || OPERATING_SYSTEM.indexOf("aix") > 0) {
      Logger.printLine("User is on LINUX.");
      if (Game.isDev){
        location = System.getProperty( "user.home" ) + "/fossickersdoom/";
  			locationBin = location + "bin/";
  			locationSaves = location + "saves/";
  			locationRes = location + "res/";
  			locationOptions = location + "options/";
  			locationLogs = location + "logs/";
      }
		} else if (OPERATING_SYSTEM.indexOf("sunos") >= 0) {
      Logger.printLine("Solaris detected.", Logger.WARNING);
      Logger.printLine("Will use LINUX configs.");
      if (Game.isDev){
  			location = System.getProperty( "user.home" ) + "/fossickersdoom/";
        locationBin = location + "bin/";
        locationSaves = location + "saves/";
        locationRes = location + "res/";
        locationOptions = location + "options/";
        locationLogs = location + "logs/";
      }
		} else {
      Logger.printLine("Dont know what OS the system is.", Logger.WARNING);
      Logger.printLine("Will pretend it is LINUX.");
      if (Game.isDev) {
  			location = System.getProperty( "user.home" ) + "/fossickersdoom/";
  			locationBin = location + "bin/";
  			locationSaves = location + "saves/";
  			locationRes = location + "res/";
  			locationOptions = location + "options/";
  			locationLogs = location + "logs/";
      }
		}
			File file = new File(location);
			file.mkdirs();
			File bin = new File(locationBin);
			bin.mkdirs();
			File saves = new File(locationSaves);
			saves.mkdirs();
			File res = new File(locationRes);
			res.mkdirs();
			File options = new File(locationOptions);
			options.mkdirs();
			File logs = new File(locationLogs);
			logs.mkdirs();
    Logger.printDebug("Game root directory: " + location);
    Logger.printDebug("Game bin directory: " + locationBin);
    Logger.printDebug("Save directory: " + locationSaves);
    Logger.printDebug("Res directory: " + locationRes);
    Logger.printDebug("Options directory: " + locationOptions);
    Logger.printDebug("Logs directory: " + locationLogs);
		}

}
