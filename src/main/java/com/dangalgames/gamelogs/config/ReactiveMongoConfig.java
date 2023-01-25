package com.dangalgames.gamelogs.config;

import java.util.concurrent.TimeUnit;

import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.SocketSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Configuration
@Slf4j
public class ReactiveMongoConfig {
	
	@Value("${spring.aws.secretmanager.secretName}")
	private String secretName;
	
	@Value("${spring.aws.secretmanager.region}")
	private String region;

	@Autowired
	private Config config;
	
	@Autowired
	private ConsulConfig consul;

	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate() {
		MongoClient mongoClient = MongoClients.create(createMongoClientSettings());
		return new ReactiveMongoTemplate(mongoClient, config.getDatabase());
	}

	private MongoClientSettings createMongoClientSettings() {
		return MongoClientSettings.builder().readConcern(ReadConcern.DEFAULT)
				.writeConcern(WriteConcern.W1).readPreference(ReadPreference.secondaryPreferred())
				.applyConnectionString(new ConnectionString(getMongoUri()))
				.applyToConnectionPoolSettings(connPoolBuilder -> ConnectionPoolSettings.builder()
						.maxConnectionIdleTime(config.getMongoDbMaxConnectionIdleTime(), TimeUnit.MILLISECONDS)
						.minSize(config.getMongoDbCorePoolSize())
						.maxSize(config.getMongoDbMaxPoolSize()))
				.applyToSocketSettings(socketBuilder -> SocketSettings.builder()
						.connectTimeout(config.getMongoDbConnectionTimeout(), TimeUnit.MILLISECONDS))
				.uuidRepresentation(UuidRepresentation.STANDARD)
				.build();
	}
	
	private String getMongoUri() {
		
		Region region = Region.of(consul.getString("spring.aws.secretmanager.region", this.region));
		SecretsManagerClient client = SecretsManagerClient.builder().region(region).build();

		String secretName = consul.getString("spring.aws.secretmanager.secretName", this.secretName);
		GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder().secretId(secretName).build();
		
		GetSecretValueResponse getSecretValueResponse = null;
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
			if (getSecretValueResponse.secretString() != null) {
				String secret = getSecretValueResponse.secretString();
				log.info("Secret value", secret);
				JsonNode secretsJson = objectMapper.readTree(secret);
				String host = secretsJson.get("host").textValue();
				String username = secretsJson.get("username").textValue();
				String password = secretsJson.get("password").textValue();
				
				StringBuilder builder = new StringBuilder();
				builder.append("mongodb://");
				builder.append(username);
				builder.append(":");
				builder.append(password);
				builder.append("@");
				builder.append(host);
				builder.append("/admin?replicaSet=rs0");
				
				log.info("connection string {}", builder.toString());
				
				return builder.toString();
			}
		} catch (Exception e) {
			log.error("Error while getting secret values");

		}
		return "";
	}
	
}
