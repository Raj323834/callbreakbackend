package com.dangalgames.gamelogs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangalgames.gamelogs.dao.GameStatsDao;
import com.dangalgames.gamelogs.documents.GameStats;
import com.dangalgames.gamelogs.model.GameEvent;

@Service
public class GameStatsServiceImpl implements GameStatsService {

	@Autowired
	private GameStatsDao statsDao;
	
	@Override
	public void processGameStartEvent(GameEvent event) {
		statsDao.insert(new GameStats(event));
	}

	@Override
	public void processGameEndEvent(GameEvent event) {
		statsDao.updatePlayerStats(event);
	}

}
