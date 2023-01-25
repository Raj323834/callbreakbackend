package com.dangalgames.gamelogs.dao;

import com.dangalgames.gamelogs.documents.GameStats;
import com.dangalgames.gamelogs.model.GameEvent;
/**
 * 
 * @author rajatbehl
 *
 */
public interface GameStatsDao {

	void insert(GameStats gameStats);
	
	void updatePlayerStats(GameEvent gameEvent);
	
}
