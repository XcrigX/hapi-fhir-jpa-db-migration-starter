package com.github.xcrigx.hapi.migratedb;

import com.github.xcrigx.hapi.migratedb.config.SpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@Import(SpringConfig.class)
//@EnableConfigurationProperties(ApplicationProperties.class)
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
