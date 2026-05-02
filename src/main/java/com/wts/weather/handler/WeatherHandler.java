package com.wts.weather.handler;

import com.wts.city.CityGeocodingService;
import com.wts.temperature.TemperatureClassifier;
import com.wts.weather.provider.OpenMeteoWeatherProvider;
import com.wts.weather.provider.WeatherProvider;
import com.wts.weather.service.WeatherService;

import java.io.IOException;
import java.util.Map;

public class WeatherHandler {

    private final WeatherService service;

    public WeatherHandler() {
        String apiKey = System.getenv("API_KEY");

        CityGeocodingService geoService =
                new CityGeocodingService(apiKey);

        WeatherProvider provider =
                new OpenMeteoWeatherProvider(geoService);

        this.service = new WeatherService(
                provider,
                new TemperatureClassifier()
        );
    }

    public Map<String, Object> handleRequest(Map<String, Object> event) throws IOException, InterruptedException {

        Map<String, String> queryParams =
                (Map<String, String>) event.get("queryStringParameters");

        String city = queryParams != null ? queryParams.get("city") : null;

        if (city == null || city.isBlank()) {
            return Map.of(
                    "statusCode", 400,
                    "body", "Missing 'city' query parameter"
            );
        }

        var result = service.getWeather(
                new com.wts.weather.dto.WeatherRequest(city)
        );

        return Map.of(
                "statusCode", 200,
                "body", String.format(
                        "{\"temperature\": %.2f, \"category\": \"%s\"}",
                        result.temperature(),
                        result.category()
                )
        );
    }
}