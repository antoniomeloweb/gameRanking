package com.gameRanking.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.gameRanking.constants.Constants;

/**
 * @author melo
 *
 */
public final class Utils {	
	
	/**
	 * Method to return the Author within an rawString entry
	 * @param entry
	 * @return
	 */
	public static String takeEventAuthor(String entry){
		entry = entryWithNoDate(entry);
		
		String[] str = entry.split(" ");
		String completeName = str[0];
		
		for(int i=1;i<str.length;i++){
			if(!Constants.TOKEN_KILLED.equals(str[i])){
				completeName += " "+str[i];
			}else{
				break;
			}
		}
		
		return completeName;
	}
	
	/**
	 * Method to clear the date from the beginning of entry
	 * @param entry
	 * @return
	 */
	public static String entryWithNoDate(String entry){
		int fullDateLength = (Constants.DATE_FORMAT+Constants.TOKEN_DATE_SEPARATOR).length();
		if(entry.length()<=fullDateLength){
			return entry;
		}else{
			return entry.substring(fullDateLength,entry.length());		
		}
	}
	
	/**
	 * Method to return the eventTime within an rawString entry
	 * @param entry
	 * @return
	 * @throws ParseException
	 */
	public static Calendar takeEventTime(String entry) throws ParseException{
		String str = entry.substring(0,Constants.DATE_FORMAT.length());
		Calendar cal;
		cal = String2Calendar(str);
		return cal;
	}
	
	/**
	 * Method to return the victim within an rawString entry
	 * @param entry
	 * @return
	 */
	public static String takeEventVictim(String entry){
		entry = entryWithNoDate(entry);
		
		String[] strFull = entry.split(Constants.TOKEN_KILLED+" ");
		String[] strVictim = strFull[1].split(" ");
		String completeName = strVictim[0];
		
		String victimToken;
		
		if(entry.indexOf(Constants.TOKEN_WORLD)>=0){
			victimToken = Constants.TOKEN_BY;
		}else{
			victimToken = Constants.TOKEN_USING;	
		}
		
		for(int i=1;i<strVictim.length;i++){
			if(!victimToken.equals(strVictim[i])){
				completeName += " "+strVictim[i];
			}else{
				break;
			}
		}
		
		return completeName;
	}
	
	/**
	 * Method to return the weapon within an rawString entry
	 * @param entry
	 * @return
	 */
	public static String takeEventWeapon(String entry){
		String[] str = entry.split(Constants.TOKEN_USING);
		return str[1];
	}
	
	/**
	 * Method to return the eventType within an rawString entry
	 * @param entry
	 * @return
	 * @throws Exception
	 */
	public static String takeEventType(String entry) throws Exception{
		//removing date from entry
		int fullDateLength = (Constants.DATE_FORMAT+Constants.TOKEN_DATE_SEPARATOR).length();
		if(entry.length()<=fullDateLength){
			return Constants.EVENT_UNKNOWN;
		}
		
		entry = entryWithNoDate(entry);
		
		//New match *** has started
		if(entry.matches(Constants.TOKEN_NEW_MATCH+"\\s+.*\\s"+Constants.TOKEN_HAS_STARTED)){
			return Constants.EVENT_GAME_START;
		
		//Match *** has ended
		}else if(entry.matches(Constants.TOKEN_MATCH+"\\s+.*\\s"+Constants.TOKEN_HAS_ENDED)){
			return Constants.EVENT_GAME_OVER;
		
		//*** killed ***
		}else if(entry.matches(".*"+Constants.TOKEN_KILLED+".*")){
			return Constants.EVENT_KILL;
		}
		
		return Constants.EVENT_UNKNOWN;
	}
	
	/**
	 * get a String with current time formatted as Constants.DATE_FORMAT
	 * @return
	 */
	public static String getTimeFormatted(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		Date date = cal.getTime();             
		SimpleDateFormat format1 = new SimpleDateFormat(Constants.DATE_FORMAT);
		return format1.format(date);
	}
	
	/**
	 * Convert String to Calendar
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Calendar String2Calendar(String str) throws ParseException{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Constants.LOCALE);
		cal.setTime(sdf.parse(str));// all done

		return cal;
		
	}
	
	/**
	 * arcade method
	 * @param s
	 * @param n
	 * @return
	 */
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}

	/**
	 * Friendly show minutes
	 * @param seconds
	 * @return
	 */
	public static String seconds2minutes (int seconds){
		String minutes = String.valueOf(Math.round(seconds/60));
		int secs = seconds%60;
		
		return minutes + ((secs==0)?"":(":"+secs));		
		
	}
}
