package com.dangalgames.gamelogs.enums;
/**
 * 
 * @author rajatbehl
 *
 */
public enum Event {

	GAME_START,
	GAME_END,
	MOVE_CARD,
	INITIAL_CARDS,
	PLAY_REQUEST,
	ROUND_START,
	PLAYER_BID,
	END_OF_ROUND,
	JOIN_TABLE,
	REJOIN_TABLE,
	PLAYER_QUIT,
	TURN_MISS;
	
	public static boolean isGameStatsEvent(Event event) {
		return event == GAME_START || event == GAME_END;
	}
}
