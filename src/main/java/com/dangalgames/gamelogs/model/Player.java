package com.dangalgames.gamelogs.model;

import java.util.List;
import java.util.Map;

import lombok.Data;
/**
 * 
 * @author rajatbehl
 *
 */
@Data
public class Player {

	private String playerId;
	private String username;
	private boolean isWinner;
	private double winningAmount;
	private int rank;
	private double totalScore;
	private Map<String, String> action;
	private List<String> currentCards;
	
}
