package com.dangalgames.gamelogs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangalgames.gamelogs.enums.Event;
import com.dangalgames.gamelogs.model.GameEvent;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author rajatbehl
 *
 */
@Service
@Slf4j
public class GameEventsServiceImpl implements GameEventsService {

	@Autowired
	private GameStatsService statsService;
	
	@Autowired
	private GameLogService gameLogService;

	@Override
	public void processGameEvents(GameEvent event) {
		if (Event.isGameStatsEvent(event.getName())) {
			switch (event.getName()) {
			case GAME_START:
				statsService.processGameStartEvent(event);
				break;
			case GAME_END:
				statsService.processGameEndEvent(event);
				break;

			default:
				log.error("unhandled event {}", event);

			}
		} 
		
		gameLogService.saveGameLogs(event);
		
	}
}