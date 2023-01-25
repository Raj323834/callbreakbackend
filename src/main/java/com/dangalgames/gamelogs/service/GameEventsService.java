package com.dangalgames.gamelogs.service;

import com.dangalgames.gamelogs.model.GameEvent;
/**
 * 
 * @author rajatbehl
 *
 */
public interface GameEventsService {

	void processGameEvents(GameEvent event);
}
