package com.weather.weatherforecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WeatherForecastApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherForecastApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplateBean() {
		return new RestTemplate();
	}

}
