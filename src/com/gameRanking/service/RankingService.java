package com.gameRanking.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.gameRanking.constants.Constants;
import com.gameRanking.model.GameEvent;
import com.gameRanking.model.Player;
import com.gameRanking.util.Utils;

/**
 * @author melo
 *
 */
public final class RankingService {	
	/**
	 * Base method that calls the main methods to 
	 * populate object lists of players and events
	 * and then starts building the table results.
	 * @param gameEventList
	 * @return
	 * @throws Exception
	 */
	public static String doRanking(List<GameEvent> gameEventList) throws Exception{
		
		List<Player> player = new ArrayList<Player>();
		
		player.addAll(fillPlayerList(gameEventList));
		
		assignPlayerEvents(player, gameEventList);
		
		Collections.sort(player);
		
		printGameRankings(player);
		
		return null;		
	}
	
	/**
	 * Main method responsible for showing the table of results
	 * @param player
	 */
	public static void printGameRankings(List<Player> player){
		System.out.println("...............................................................................");
		System.out.println("# \t "+ Utils.padRight("Player", 20) +"\t Score \t Kills \t Deaths\t Pref Weapon\t Streak");
		System.out.println("...............................................................................");
		int ranking=1;
		for(Player p: player){
			//not show <WORLD> in the rankings
			if(!p.getName().equals(Constants.TOKEN_WORLD)){
				System.out.println(ranking++ + "\t " + 
							   Utils.padRight(p.getName(), 20) +"\t "+ 
							   p.getScore() +"\t "+ 
							   p.getKills().size() +"\t "+ 
							   p.getDeaths().size() +"\t"+
							   Utils.padRight(p.getWeaponOfChoice(),15) + "  " +
							   p.getStreak());
			}
		}
		System.out.println("...............................................................................");
		System.out.println("Winner: "+player.get(0).getName()+" || Weapon of choice: "+player.get(0).getWeaponOfChoice());
		System.out.println("...............................................................................");
		System.out.println("Multi kill Awards (Kills "+ Constants.AWARD_MULTI_KILL_COUNT +" in less than "+Utils.seconds2minutes(Constants.AWARD_MULTI_KILL_TIME)+" minutes)");
		
		boolean somebodyAwarded=false;
		
		for(Player p:player){
			if(p.multiKillAward()){
				somebodyAwarded=true;
				System.out.println(p.getName()+" awarded!");
			}
		}
		
		if(!somebodyAwarded)
			System.out.println("Nobody awarded at this match.");
		System.out.println("...............................................................................");
	}
	
	/**
	 * Method to assign to the Player object two lists of its own events
	 * 1) kills -> a list of events where the player is a killer
	 * 2) deaths -> a list of events where the player was killed
	 * @param playerList
	 * @param gameEventList
	 */
	public static void assignPlayerEvents(List<Player> playerList, List<GameEvent> gameEventList){
		
		for(GameEvent g: gameEventList){
			//only look inside event if it큦 a kill event
			if(Constants.EVENT_KILL.equals(g.getEventType())){
				for(Player p: playerList){
					if(p.getName().equals(g.getAuthor())){
						p.assignKill(g);
					}
					if(p.getName().equals(g.getVictim())){
						p.assignDeath(g);
					}
				}
			}
		}
	}
	
	/**
	 * Method to get all players on the current game, 
	 * based on the entries received in the external file
	 * @param gameEvent
	 * @return
	 */
	public static List<Player> fillPlayerList(List<GameEvent> gameEvent){
		
		List<Player> pList = new ArrayList<Player>();
		
		for(GameEvent g: gameEvent){
			//only look inside event if it큦 a kill event
			if(Constants.EVENT_KILL.equals(g.getEventType())){
				
				if(isPlayerNew(pList, g)){
					pList.add(new Player(g.getAuthor()));
				}
				
				if(isVictimNewPlayer(pList, g)){
					pList.add(new Player(g.getVictim()));
				}
			}
		}
		
		return pList;
	}
	
	/**
	 * method to help implementation of the fillPlayerList
	 * @param pList
	 * @param g
	 * @return
	 */
	private static boolean isPlayerNew(List<Player> pList, GameEvent g){
		boolean isNew=true;
		ploop:
		for(Player p: pList){
			//if the mapped event is valid and it큦 a kill event
			if(g.isValid()){
				//if name found, so it큦 not a new player
				if(p.getName().equals(g.getAuthor())){
					isNew=false;
					break ploop;
				}
			}
		}
		return isNew;
	}

	/**
	 * method to help implementation of the fillPlayerList
	 * @param pList
	 * @param g
	 * @return
	 */
	private static boolean isVictimNewPlayer(List<Player> pList, GameEvent g){
		boolean isNew=true;
		ploop:
		for(Player p: pList){
			//if the mapped event is valid and it큦 a kill event
			if(g.isValid()){
				//if name found, so it큦 not a new player
				if(p.getName().equals(g.getVictim())){
					isNew=false;
					break ploop;
				}
			}
		}
		return isNew;
	}	
	
	/**
	 * Method to load the entry file
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static List<GameEvent> loadEventsFromFile(String fileName) throws Exception{
		
		BufferedReader inputStream = new BufferedReader(new FileReader(fileName));
		
		Scanner s = new Scanner(inputStream);
		s.useDelimiter("[\r\n]+"); 
		
		List<GameEvent> eventList = new ArrayList<GameEvent>();
		
		while (s.hasNext()) {
            eventList.add(new GameEvent(s.next()));
        }
		
		s.close();
		
		return eventList;
	}
}
