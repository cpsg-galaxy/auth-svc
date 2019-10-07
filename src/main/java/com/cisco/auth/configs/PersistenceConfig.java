package com.cisco.auth.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.cisco.auth.repositories")
public class PersistenceConfig {}
