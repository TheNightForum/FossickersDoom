package com.tnf.fdoom.handlers;

/**
 * Created by brayden on 5/1/17.
 */
public class Logger {

    public static final String NORMAL =  "       ";
    public static final String WARNING = "WARNING";
    public static final String ERROR =   "!ERROR!";
    private static final String DEBUG =  "DEBUG";
    private static boolean debugEnabled = false;

    public static void printDebug(String inputString, String inputType){
        if (debugEnabled){
            System.out.println(" " + inputType + " |: " + DEBUG + " - " + inputString);
        }
    }

    public static void printDebug(String inputString){
        if (debugEnabled){
            System.out.println("         |: " + DEBUG + " - " + inputString);
        }
    }

    public static void printLine(String inputString) {
        System.out.println("         | " + inputString);
    }

    public static void printLine(String inputString, String inputType) {
        System.out.println(" " + inputType + " | " + inputString);
    }

    public static void printLine(Exception e){
    	System.out.println(" " + ERROR + " | " + e);
    }

    public static void printLine(int inputInt) {
        printLine(Integer.toString(inputInt));
    }

    public static void enabledDebug(){
        debugEnabled = true;
    }

    public static boolean isInDebug(){
        return debugEnabled;
    }

    public static void printLine() {
        printLine("");
    }
}
