package com.dangalgames.gamelogs.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;

import com.dangalgames.gamelogs.documents.GameLog;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author rajatbehl
 *
 */
@Service
@Slf4j
public class GameLogsDaoImpl implements GameLogsDao {
	
	@Autowired
	private ReactiveMongoTemplate template;

	@Override
	public void insert(List<GameLog> gameLogs, String collectionName) {
		template.insert(gameLogs, collectionName)
		.doOnError(error -> log.error("error while inserting game logs {}", gameLogs))
			.subscribe(inserted -> {
				if(inserted == null) {
					log.error("failed to bulk insert gamelogs {}", gameLogs);
				}
			});
	}
}
