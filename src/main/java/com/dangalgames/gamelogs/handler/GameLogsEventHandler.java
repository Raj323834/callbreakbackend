package com.dangalgames.gamelogs.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.dangalgames.gamelogs.model.GameEvent;
import com.dangalgames.gamelogs.service.GameEventsService;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author rajatbehl
 *
 */
@Slf4j
@Service
public class GameLogsEventHandler {

	private Gson gson = new Gson();
	
	@Autowired
	private GameEventsService service;

	@KafkaListener(topics = "#{'${pubsub.gamelogs.topic}'}", groupId = "#{'${pubsub.gamelogs.group-id}'}",  containerFactory = "gameLogsKafkaListenerContainerFactory")
	public void handleGameLogs(String message) {
		log.info("received message {}", message);
		service.processGameEvents(gson.fromJson(message, GameEvent.class));
	}
}
