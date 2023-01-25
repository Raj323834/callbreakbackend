package com.dangalgames.gamelogs.service;

import com.dangalgames.gamelogs.model.GameEvent;

public interface GameLogService {

	void saveGameLogs(GameEvent event);
}
