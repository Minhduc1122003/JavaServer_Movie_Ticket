package com.example.demo.config;

import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI openAPI(@Value("${open.api.title}") String title, 
							@Value("${open.api.version}") String version,
							@Value("${open.api.description}") String description,
							@Value("${open.api.serverUrl}") String serverUrl,
							@Value("${open.api.serverName}") String serverName) {
		return new OpenAPI().info(new Info().title(title)
											.version(version)
											.description(description)
											.license(new License().name("API License").url("http://domain.vn/license")))
								.servers(List.of(new Server().url(serverUrl).description(serverName)));
								
	}
	
	@Bean
	public GroupedOpenApi groupedOpenApi() {
		return GroupedOpenApi.builder()
				.group("api-service")
				.packagesToScan("com.example.demo.controller")
				.build();
	}
}
