package com.wts.weather.handler;

import com.wts.city.CityGeocodingService;
import com.wts.temperature.TemperatureClassifier;
import com.wts.weather.dto.WeatherRequest;
import com.wts.weather.dto.WeatherResponse;
import com.wts.weather.provider.OpenMeteoWeatherProvider;
import com.wts.weather.provider.WeatherProvider;
import com.wts.weather.service.WeatherService;

import java.io.IOException;

public class WeatherHandler {

    private final WeatherService service;

    public WeatherHandler() {
        var apiKey = System.getenv("API_KEY");

        CityGeocodingService geoService =
                new CityGeocodingService(apiKey);

        WeatherProvider provider =
                new OpenMeteoWeatherProvider(geoService);

        this.service = new WeatherService(
                provider,
                new TemperatureClassifier()
        );
    }

    public WeatherResponse handleRequest(WeatherRequest request) throws IOException, InterruptedException {
        return service.getWeather(request);
    }
}