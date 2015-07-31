package com.gameRanking.constants;

import java.util.Locale;

/**
 * @author melo
 *
 */
public final class Constants {
	public static String BASE_DIR = "src//resources//";
	
	//public static String ARQ_ENTRADA = "gameRaw.txt";	
	public static String ARQ_ENTRADA = "originalFile.txt";	
	
	public static Locale LOCALE = Locale.ENGLISH;
	public static String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	public static String EVENT_GAME_START = "GAME START";
	public static String EVENT_GAME_OVER = "GAME OVER";
	public static String EVENT_KILL = "KILL";
	public static String EVENT_UNKNOWN = "UNKNOWN";
	
	public static String TOKEN_DATE_SEPARATOR = " - ";
	public static String TOKEN_KILLED = "killed";
	public static String TOKEN_USING = "using";
	public static String TOKEN_BY = "by";
	public static String TOKEN_NEW_MATCH = "New match";
	public static String TOKEN_HAS_STARTED = "has started";
	public static String TOKEN_MATCH = "Match";
	public static String TOKEN_HAS_ENDED = "has ended";
	public static String TOKEN_WORLD = "<WORLD>";
	
	public static int AWARD_MULTI_KILL_TIME = 300;
	public static int AWARD_MULTI_KILL_COUNT = 5;
}
