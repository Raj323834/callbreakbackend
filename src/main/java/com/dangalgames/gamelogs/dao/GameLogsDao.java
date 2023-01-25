package com.dangalgames.gamelogs.dao;

import java.util.List;

import com.dangalgames.gamelogs.documents.GameLog;
/**
 * 
 * @author rajatbehl
 *
 */
public interface GameLogsDao {

	void insert(List<GameLog> gameLogs, String collectionName);
}
