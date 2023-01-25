package com.dangalgames.gamelogs.documents;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dangalgames.gamelogs.enums.Event;

import lombok.Data;
/**
 * 
 * @author rajatbehl
 *
 */
@Data
public class GameLog {
	
	private Event name;
	private String tableId;
	private String gameId;
	private int roundId;
	private String playerId;
	private String username;
    private Map<String, String> action;
	private Date timestamp;
	private List<String> currentCards;
	private LocalDateTime createdAt = LocalDateTime.now();
	
}
