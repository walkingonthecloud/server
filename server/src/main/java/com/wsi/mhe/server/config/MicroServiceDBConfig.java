package com.wsi.mhe.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConfigurationProperties(prefix = "microservicedb")
@Data
public class MicroServiceDBConfig {

	private DatabaseConfiguration configuration;

	@Bean (name = "jdbcTemplateMCS")
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(getConfiguration().createDataSource());
	}
}