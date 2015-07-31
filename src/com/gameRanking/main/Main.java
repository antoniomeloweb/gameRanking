package com.gameRanking.main;

import java.util.List;

import com.gameRanking.constants.Constants;
import com.gameRanking.model.GameEvent;
import com.gameRanking.service.RankingService;

/**
 * @author melo
 *
 */
public class Main {
	public static void main(String[] args) throws Exception{
		
		System.out.println("Loading events file...");
		
		List<GameEvent> eventList = RankingService.loadEventsFromFile(Constants.BASE_DIR + Constants.ARQ_ENTRADA);
		
		System.out.println("Events file loaded.");
		System.out.println("Processing game results table...");
		
		RankingService.doRanking(eventList);
		
		System.out.println("Finished.");
	}
	
	public ClassLoader getClassLoader(){
		return getClass().getClassLoader();
	}
}
