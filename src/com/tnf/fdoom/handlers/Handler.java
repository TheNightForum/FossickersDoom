package com.tnf.fdoom.handlers;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import java.net.URLConnection;
import java.util.Properties;

import com.tnf.fdoom.Game;
import com.tnf.fdoom.handlers.Logger;

public class Handler
{
  public static Boolean isInternet;
  public static Boolean needsUpdate;
  public static String Result = "";

  private static final String FirstTime = "First_Time";
  public static final String FOW = "Fog_Of_War";
  private static final String UsingLauncher = "Using_Launcher";
  private static final String LastWorld = "Last_World";
  private static final String CurrentWorld = "Current_World";
  private static final String PlayerName = "Player_Name";

  public static void main(){
    runChecks();
    setImages();
    checkInternet();
    if(isInternet){
      Logger.printLine("There is internet.");
      isGameUpToDate();
      getLatestGameVersion();
    }else{
      Logger.printLine("There is no internet.", Logger.WARNING);
    }
    if (isGameUpToDate() == 1) needsUpdate = true;
    else if (isGameUpToDate() == 0) needsUpdate = false;
  }
  public static void writeConfig(String option, String newSetting ) {
      OutputStream out = null;
      try {
          Properties props = new Properties();
          File f = new File(Data.locationOptions + "config.properties");

              props.load(new FileReader(f));
              props.setProperty(option, newSetting);

          out = new FileOutputStream(f);
          props.store(out, "Please do not manually edit this file.");
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          if (out != null) {
              try {
                  out.close();
              } catch (IOException ex) {
                  System.out.println("IOException: Could not close config.properties output stream; " + ex.getMessage());
                  ex.printStackTrace();
              }
          }
      }
  }
  public static String readConfig(String toRead){
      Properties prop = new Properties();
      InputStream input = null;

      try {
          input = new FileInputStream(Data.locationOptions + "config.properties");

          // load a properties file
          prop.load(input);

          Result = prop.getProperty(toRead);
      } catch (IOException ex) {
          ex.printStackTrace();
      } finally {
          if (input != null) {
              try {
                  input.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return toRead;
  }
  private static void runChecks(){
    File file = new File(Data.locationOptions + "config.properties");
    try{
      if (!file.exists()) {
        file.createNewFile();
        writeConfig(FirstTime, "true");
        writeConfig(PlayerName, "null");
        writeConfig(UsingLauncher, "false");
        writeConfig(FOW, "true");
        writeConfig(LastWorld, "null");
        writeConfig(CurrentWorld, "null");
      }
    }catch (IOException e){
      Logger.printLine("Oh no, could not create the options file.", Logger.ERROR);
    }
  }
  private static void setImages(){

  }
  private static void checkInternet(){
    try {
      URL url = new URL(Data.BaseUrl);
      URLConnection connection = url.openConnection();
      connection.connect();
      isInternet = true;
    } catch (Exception e) {
      isInternet = false;
    }
  }

    private static int isGameUpToDate() {
		String lVersion = getLatestGameVersion();
		return lVersion.equals("Could not connect to webserver.") ? -1:Game.VERSION.equalsIgnoreCase(getLatestGameVersion()) ? 0:1;
	}

    private static String getLatestGameVersion() {
        try {
            Connection c = new Connection(new URL(Data.BaseUrl + "Games/fdoom/version"));
            c.createConnection();
            for(String s : c.readURL()) {
                return s.trim();
            }
        } catch (MalformedURLException ex) {
          Logger.printLine("Could not connect to webserver", Logger.WARNING);
          return "Could not connect to webserver.";
        } catch (IOException ex) {
          Logger.printLine("Could not connect to webserver", Logger.WARNING);
          return "Could not connect to webserver.";
        }
        return Game.VERSION;
    }

}
