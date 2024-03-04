package com.weather.weatherforecast.service;

import java.util.List;
import java.util.Map;

import com.weather.weatherforecast.model.WeatherModel;

public interface WeatherForecastService {

//	public WeatherModel getWeatherData(double lat, double lon);
	public String getWeatherDataByPin(String pincode);
//	public Object getWeatherForFiveDay(String pincode);
	public Map<String, Object> getWeatherForFiveDay(String pincode);
	
	
}
