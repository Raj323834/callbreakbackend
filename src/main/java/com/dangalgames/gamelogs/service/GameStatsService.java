package com.dangalgames.gamelogs.service;

import com.dangalgames.gamelogs.model.GameEvent;

public interface GameStatsService {

	void processGameStartEvent(GameEvent event);
	
	void processGameEndEvent(GameEvent event);
}
