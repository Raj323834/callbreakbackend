package com.dangalgames.gamelogs.model;

import java.util.Date;
import java.util.List;

import com.dangalgames.gamelogs.enums.Event;
import com.dangalgames.gamelogs.enums.GameStatus;

import lombok.Data;
/**
 * 
 * @author rajatbehl
 *
 */
@Data
public class GameEvent {
	
	/*
	 * Common attributes
	 */
	private Event name;
	private String tableId;
	private String gameId;
	private List<Player> players;
	/*
	 * Game Logs attribute
	 */
	private int roundId;
	private int seatId;
	private List<String> currentCards;
	private Date timestamp;
	private boolean placedByRealPlayer;
	
	/*
	 * Game Stats attributes
	 */
	private String tableName;
	private GameStatus status;
	private Date startTime;
	private double entryFee;
	private double rake;
	private int noOfRounds;
	/*
	 * Game End attributes
	 */
	private Date endTime;
	
}
