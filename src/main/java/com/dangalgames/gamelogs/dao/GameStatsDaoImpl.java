package com.dangalgames.gamelogs.dao;

import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.dangalgames.gamelogs.documents.GameStats;
import com.dangalgames.gamelogs.model.GameEvent;
import com.dangalgames.gamelogs.model.Player;
import com.dangalgames.gamelogs.utills.Constant;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author rajatbehl
 *
 */
@Service
@Slf4j
public class GameStatsDaoImpl implements GameStatsDao {

	@Autowired
	private ReactiveMongoTemplate template;
	
	@Override
	public void insert(GameStats gameStats) {
		template.insert(gameStats)
		.doOnError(error -> log.error("error while inserting game stats {}", gameStats, error))
		.subscribe(stats -> {
			if(stats == null) {
				log.error("failed to save game stats {}", gameStats);
			}
			
		});
	}

	@Override
	public void updatePlayerStats(GameEvent gameEvent) {
		
		Query query = Query.query(Criteria.where(Constant.GAME_ID).is(gameEvent.getGameId()));
		template.find(query, GameStats.class)
			.doOnError(error -> log.error("error while fetching game stats with gameId {}", gameEvent.getGameId(), error))
			.subscribe(gameStats -> {
				var eventPlayers = gameEvent.getPlayers().stream().collect(Collectors.toMap(Player :: getUsername, Function.identity()));
				gameStats.getPlayers().stream().forEach(player -> {
					var eventPlayer = eventPlayers.get(player.getUsername());
					double winningAmount = Double.parseDouble(eventPlayer.getAction().get(Constant.WINNING_AMOUNT));
					player.setWinningAmount(winningAmount);
					player.setWinner(player.getWinningAmount() > 0);
					player.setRank(Integer.parseInt(eventPlayer.getAction().get(Constant.RANK)));
					player.setTotalScore(Double.parseDouble(eventPlayer.getAction().get(Constant.TOTAL_SCORE)));

				});
				
				Update update = new Update();
				update.set(Constant.END_TIME, gameEvent.getEndTime());
				update.set(Constant.PLAYERS, gameStats.getPlayers());
				update.set(Constant.STATUS, gameEvent.getStatus());
				update.set(Constant.UPDATED_AT, LocalDateTime.now());
				
				
				template.updateFirst(query, update, GameStats.class)
					.doOnError(error -> log.error("error while updating game stats {}", gameStats, error))
					.subscribe(saved -> {
						if(saved == null) {
							log.error("failed to update game stats for game event {}", gameEvent);
						}
					});
			});
	}
	
}
