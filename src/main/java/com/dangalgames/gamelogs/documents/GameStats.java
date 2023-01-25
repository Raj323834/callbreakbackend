package com.dangalgames.gamelogs.documents;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.dangalgames.gamelogs.enums.GameStatus;
import com.dangalgames.gamelogs.model.GameEvent;
import com.dangalgames.gamelogs.model.Player;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author rajatbehl
 *
 */
@Data
@Document(collection = "game_stats")
@NoArgsConstructor
public class GameStats {

	private String gameId;
	private String tableId;
	private String tableName;
	private double entryFee;
	private double rake;
	private List<Player> players;
	private GameStatus status;
	private Date startTime;
	private Date endTime;
	private int noOfRounds;
	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime updatedAt = LocalDateTime.now();
	
	public GameStats(GameEvent event) {
		this.gameId = event.getGameId();
		this.tableId = event.getTableId();
		this.tableName = event.getTableName();
		this.entryFee = event.getEntryFee();
		this.rake = event.getRake();
		this.players = event.getPlayers();
		this.startTime = event.getStartTime();
		this.noOfRounds = event.getNoOfRounds();
		this.status = event.getStatus();
	}
	
}
