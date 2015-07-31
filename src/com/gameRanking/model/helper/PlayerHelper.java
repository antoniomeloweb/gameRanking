package com.gameRanking.model.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.gameRanking.constants.Constants;
import com.gameRanking.model.GameEvent;

/**
 * @author melo
 *
 */
public abstract class PlayerHelper {
	
	private List<GameEvent> kills = new ArrayList<GameEvent>();
	private List<GameEvent> deaths = new ArrayList<GameEvent>();
	private String name;
	
	/**
	 * When this class is invoked by the child constructor it 
	 * must sets the name of the player in order to work further
	 * @param name
	 */
	protected PlayerHelper(String name) {
        this.name = name;
    }
	
	/**
	 * sets the name of the user
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * method to return the name of the preferred weapon used by the current user
	 * @return
	 */
	public String getWeaponOfChoice(){
		
		//if player didn´t kill anybody
		if(kills.size()==0 || getWeaponList().size()==0){
			return "...";
		}
		
		List<String> list = getWeaponList();
		
		TreeMap<String, Integer> weaponUseage = new TreeMap<String, Integer>();
		
		for(String str: list){
			if(!weaponUseage.containsKey(str)){
				weaponUseage.put(str, 1);
			}else{
				weaponUseage.put(str, weaponUseage.get(str).intValue()+1);
			}
		}
		
		ValueComparator vc =  new ValueComparator(weaponUseage);
        TreeMap<String, Integer> sorted = new TreeMap<String, Integer>(vc);
		sorted.putAll(weaponUseage);
		
		return sorted.firstKey();
	}
	
	/**
	 * Method to return all the weapons used by the player
	 * @return
	 */
	public List<String> getWeaponList(){
		List<String> list = new ArrayList<String>();
		
		for(GameEvent g: kills){
			list.add(g.getWeapon());
		}
		
		return list;
	}
	
	/**
	 * returns the maximum streak for the current player
	 * streak is the maximum kills in a row a user can do without being killed
	 * @return
	 */
	public Integer getStreak(){
		List<GameEvent> allUserEvents = getAllUserEvents();
		
		Integer maxStreak = 0;
		Integer streak = 0;
		for(GameEvent g: allUserEvents){
			if(g.getAuthor().equals(this.name)){
				streak++;
			}else{
				if(streak>maxStreak){
					maxStreak=streak;
				}
				streak=0;
			}
		}
		return streak;
	}
	
	/**
	 * Returns a list with both kills and deaths lists ordered by GameEventTime
	 * @return
	 */
	public List<GameEvent> getAllUserEvents(){
		List<GameEvent> allUserEvents = new ArrayList<GameEvent>();
		
		allUserEvents.addAll(getKills());
		allUserEvents.addAll(getDeaths());
		
		Collections.sort(allUserEvents);
		
		return allUserEvents;
	}
	
	/**
	 * gets the score based on the kills and deaths lists
	 * @return
	 */
	public int getScore(){
		return kills.size() - deaths.size();
	}
	
	/**
	 * add a GameEvent to the kill list
	 * @param g
	 */
	public void assignKill(GameEvent g){
		this.kills.add(g);
	}
	
	/**
	 * add a GameEvent to the death list
	 * @param g
	 */
	public void assignDeath(GameEvent g){
		this.deaths.add(g);
	}
	
	/**
	 * gets the kills list
	 * @return
	 */
	public List<GameEvent> getKills() {
		return kills;
	}

	/**
	 * sets the kills list
	 * @param kills
	 */
	public void setKills(List<GameEvent> kills) {
		this.kills = kills;
	}

	/**
	 * gets the death list
	 * @return
	 */
	public List<GameEvent> getDeaths() {
		return deaths;
	}

	/**
	 * Sets the death list
	 * @param deaths
	 */
	public void setDeaths(List<GameEvent> deaths) {
		this.deaths = deaths;
	}

	/**
	 * Multikills is a bonus method
	 * @return
	 */
	public boolean multiKillAward(){
		
		if(getKills().size()>=Constants.AWARD_MULTI_KILL_COUNT){
			for(int a=0; a < (getKills().size() - Constants.AWARD_MULTI_KILL_COUNT); a++){
				GameEvent g1 = getKills().get(a);
				GameEvent g2 = getKills().get(a+Constants.AWARD_MULTI_KILL_COUNT-1);
				
				if(g1.getTimeEvent().compareTo(g2.getTimeEvent())<Constants.AWARD_MULTI_KILL_TIME){
					return true;
				}
			}	
		}
		return false;
	}
	
}

/**
 * Inner class to implements a comparator
 * @author melo
 *
 */
class ValueComparator implements Comparator<String> {

    Map<String, Integer> base;
    public ValueComparator(TreeMap<String, Integer> base) {
        this.base = base;
    }
    
    public int compare(String a, String b) {
    	//doing desc order
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}

