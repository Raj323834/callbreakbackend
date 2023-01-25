package com.dangalgames.gamelogs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class Config {

	@Value("${pubsub.namespace}")
	private String namespace;

	@Value("${pubsub.gamelogs.topic}")
	private String callbreakTopic;

	@Value("${pubsub.gamelogs.group-id}")
	private String callbreakGroupId;

	@Value("${pubsub.gamelogs.consumer-count}")
	private int callbreakconsumerCount;
	
	@Value("${consul.url}")
	private String consulURL;
	
	@Value("${consul.path}")
	private String consulPath;
	
	// Keep mongo config in mongo config class
	
	@Value("${spring.data.mongodb.database}")
	private String database;
	
	@Value("${spring.data.mongodb.uri}")
	private String mongoUri;
	
	@Value("${mongo-db.connection-pool.core}")
	private int mongoDbCorePoolSize;
	
	@Value("${mongo-db.connection-pool.max}")
	private int mongoDbMaxPoolSize;
	
	@Value("${mongo-db.connection-pool.maxIdleTime}")
	private int mongoDbMaxConnectionIdleTime;
	
	@Value("${mongo-db.connection-pool.timeout}")
	private int mongoDbConnectionTimeout;

}
