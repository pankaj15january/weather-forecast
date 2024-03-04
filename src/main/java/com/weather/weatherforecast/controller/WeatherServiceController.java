package com.weather.weatherforecast.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.weather.weatherforecast.model.WeatherModel;
import com.weather.weatherforecast.service.WeatherForecastService;

@RestController
public class WeatherServiceController {

	@Autowired
	private WeatherForecastService weatherForecastService;
	
	@GetMapping("/weather/pin/{pinCode}")
    public String getWeatherByPin(@PathVariable("pinCode") final String pincode) {
		String response = weatherForecastService.getWeatherDataByPin(pincode);
		return response;
    }
	
	@GetMapping("/fiveday/weather/pin/{pinCode}")
	 public ResponseEntity getWeather(@PathVariable("pinCode") final String pincode) {
//	 List<WeatherModel> weatherInfo = weatherForecastService.getWeatherForFive(pincode);
	 Object weatherInfo = weatherForecastService.getWeatherForFiveDay(pincode);
	 return ResponseEntity.ok().body(weatherInfo);
	 }

}
