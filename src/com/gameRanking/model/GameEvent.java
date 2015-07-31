package com.gameRanking.model;

import java.text.ParseException;
import java.util.Calendar;

import com.gameRanking.constants.Constants;
import com.gameRanking.util.Utils;

/**
 * @author melo
 *
 */
public class GameEvent implements Comparable<GameEvent>{

	private String eventType;
	private Calendar timeEvent;
	private String author;
	private String victim;
	private String weapon;
	private String rawString;
	private boolean valid;
	
	public GameEvent(){}
	
	/**
	 * Method constructor receives the entry and populates the fields?
	 * eventType, timeEvent, author, victim, weapon, rawString and valid
	 * of the GameEvent itself
	 * @param entry
	 */
	public GameEvent(String entry){
		this.rawString = entry;
		this.valid=true;

		//EVENT TYPE
		try{
			this.eventType = Utils.takeEventType(entry);
		}catch(Exception e){
			System.out.println("Error taking Event Type of: "+rawString);
			this.valid=false;
		}
		
		//EVENT TIME
		try{
			this.timeEvent = Utils.takeEventTime(entry);
		}catch(ParseException e){
			this.timeEvent = null;
			this.valid=false;
			System.out.println("Error parsing date of Event: "+rawString);
		}
		
		// EVENT AUTHOR, VICTIM and WEAPON
		if(Constants.EVENT_KILL.equals(this.eventType)){
			try{
				this.author = Utils.takeEventAuthor(entry);
				this.victim = Utils.takeEventVictim(entry);
				
				//if world, so there´s no weapon
				if(!Constants.TOKEN_WORLD.equals(this.author)){
					this.weapon = Utils.takeEventWeapon(entry);
				}else{
					this.weapon = "";
				}
			}catch(Exception e){
				this.valid=false;
				System.out.println("Error defining details of Event: "+rawString);
			}
		}else{
			this.author = "";
			this.victim = "";
			this.weapon = "";
		}
	}
	


	
	/**
	 * Method to get a eventTyoe of a GameEvent
	 * @return
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * Method to set a eventType of a GameEvent
	 * @param eventType
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * Method to get a calendar of a GameEvent
	 * @return
	 */
	public Calendar getTimeEvent() {
		return timeEvent;
	}

	/**
	 * Method to set a calendar of a GameEvent
	 * @param timeEvent
	 */
	public void setTimeEvent(Calendar timeEvent) {
		this.timeEvent = timeEvent;
	}

	/**
	 * Method to get a author of a GameEvent
	 * @return
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Method to set a author of a GameEvent
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Method to get a victim of a GameEvent
	 * @return
	 */
	public String getVictim() {
		return victim;
	}

	/**
	 * Method to set a victim of a GameEvent
	 * @param victim
	 */
	public void setVictim(String victim) {
		this.victim = victim;
	}

	/**
	 * Method to get a weapon of a GameEvent
	 * @return
	 */
	public String getWeapon() {
		return weapon;
	}

	/**
	 * Method to set a weapon of a GameEvent
	 * @param weapon
	 */
	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
	
	/**
	 * Method to get the rawString of a GameEvent
	 * @return
	 */
	public String getRawString() {
		return rawString;
	}

	
	/**
	 * Method to set the rawString of a GameEvent
	 * @param rawString
	 */
	public void setRawString(String rawString) {
		this.rawString = rawString;
	}

	/**
	 * Method to inform if a GameEvent is valid
	 * @return
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Method to set valid to GameEvent
	 * @param valid
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@Override
	public int compareTo(GameEvent g) {
		if(this.getTimeEvent().compareTo(g.getTimeEvent())>0){
			return 1;
		}else{
			return -1;
		}
	}
	
}
