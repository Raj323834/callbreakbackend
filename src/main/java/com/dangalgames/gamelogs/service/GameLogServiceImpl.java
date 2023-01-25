package com.dangalgames.gamelogs.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangalgames.gamelogs.dao.GameLogsDao;
import com.dangalgames.gamelogs.documents.GameLog;
import com.dangalgames.gamelogs.model.GameEvent;
import com.dangalgames.gamelogs.utills.Constant;

@Service
public class GameLogServiceImpl implements GameLogService {

	@Autowired
	private GameLogsDao logsDao;
	
	@Override
	public void saveGameLogs(GameEvent event) {

		List<GameLog> gameLogs = new ArrayList<>();
		event.getPlayers().stream().forEach(player -> {
            GameLog gameLog = new GameLog();
			gameLog.setGameId(event.getGameId());
			gameLog.setTableId(event.getTableId());
			gameLog.setName(event.getName());
			gameLog.setAction(player.getAction());
			gameLog.setPlayerId(player.getPlayerId());
			gameLog.setRoundId(event.getRoundId());
			gameLog.setTimestamp(event.getTimestamp());
			gameLog.setUsername(player.getUsername());
			gameLog.setCurrentCards(event.getCurrentCards() != null && !event.getCurrentCards().isEmpty() ?
					event.getCurrentCards() : player.getCurrentCards());
			gameLogs.add(gameLog);
		});
		
		logsDao.insert(gameLogs, getGameLogsCollectionName());
	}
	
	private String getGameLogsCollectionName() {
		LocalDateTime currentDate = LocalDateTime.now();
		int month = currentDate.getMonthValue();
		int year = currentDate.getYear();
		if (month > 0 && month < 4) {
			return Constant.GAME_LOGS_1_3 + year;
		}
		if (month > 3 && month < 7) {
			return Constant.GAME_LOGS_4_6 + year;
		}
		if (month > 6 && month < 10) {
			return Constant.GAME_LOGS_7_9 + year;
		}
		return Constant.GAME_LOGS_10_12 + year;
	}

}
