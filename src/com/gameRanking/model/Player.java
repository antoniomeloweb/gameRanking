package com.gameRanking.model;

import com.gameRanking.model.helper.PlayerHelper;

/**
 * @author melo
 *
 */
public class Player extends PlayerHelper implements Comparable<Player>{
	private String name;
	
	/**
	 * Constructor method
	 * @author melo
	 * @param name
	 */
	public Player(String name){
		super(name);
		this.name=name;
	}
	
	/**
	 * return the player name
	 * @return
	 */
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		super.setName(name);
		this.name = name;
	}

	@Override
	public int compareTo(Player arg0) {
		int compared=((Player)arg0).getScore();
        /* Desc order*/
        return compared-this.getScore();
	}

	@Override
	public int getScore() {
		return super.getScore();
	}
	
}
