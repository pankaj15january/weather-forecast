package com.weather.weatherforecast.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weather.weatherforecast.service.WeatherForecastService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherForecastServiceImpl implements WeatherForecastService {

//	Logger logger = LoggerFactory.getLogger(WeatherForecastServiceImpl.class);
	private final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather";

	private final String API_KEY = "05421eb90beaae51378b287142ff8554";

	@Autowired
	private final RestTemplate restTemplate;

	@Autowired
	private CacheManager cacheManager;

	@Override
	@Cacheable(value = "weatherForPin", key = "#pincode")
	public String getWeatherDataByPin(String pincode) {

		log.info("inside WeatherForecastServiceImpl::getWeatherDataByPin");
		String url = "http://api.openweathermap.org/data/2.5/weather?zip=" + pincode + ",in&units=metric&appid="
				+ API_KEY;
		String result = restTemplate.getForObject(url, String.class);
		log.info("inside WeatherForecastServiceImpl::getWeatherDataByPin end");
		return result;

	}

	@Override
	@Cacheable(value = "weatherForFiveDay", key = "#pincode")
	public Map<String, Object> getWeatherForFiveDay(String pincode) throws ParseException {

		log.info("inside WeatherForecastServiceImpl::getWeatherForFiveDay");
		boolean isCache = false;
		Map<String, Object> json = new HashMap<String, Object>();
		if (json != null && !json.isEmpty()) {
			isCache = true;
			json.remove("cacheIndicator");
			json.put("cacheIndicator", isCache);
		} else {
			/*
			 * Todo: API_KEY read from vault Base URL read from constant or enum
			 */
			String URL = "http://api.openweathermap.org/data/2.5/forecast?q=" + pincode
					+ "&mode=json&units=metric&appid=" + API_KEY;

			ResponseEntity<Object> responseOfWeatherForFiveDay = restTemplate.getForEntity(URL, Object.class);
			json.put("cacheIndicator", isCache);
			json.put(pincode, responseOfWeatherForFiveDay.getBody());
		}

		log.info("inside WeatherForecastServiceImpl::getWeatherForFiveDay end");
		return json;
	}

	@Scheduled(fixedRate = 60 * 1000)
	public void clearCache() {
		cacheManager.getCache("weatherForFiveDay").clear();
	}

//        @Scheduled(cron ="0 */3 * ? * *")
//            public void cacheEvictionScheduler()
//            {
//                 logger.info("inside scheduler start");
//                //clearCache();
//                 evictAllCaches();
//                logger.info("inside scheduler end");
//            }
//
//        public void evictAllCaches() {
//                 logger.info("inside clearcache");
//                cacheManager.getCacheNames().stream()
//                  .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
//            }

}
