package com.wts.weather.service;

import com.wts.temperature.TemperatureClassifier;
import com.wts.weather.dto.WeatherRequest;
import com.wts.weather.dto.WeatherResponse;
import com.wts.weather.provider.WeatherProvider;

import java.io.IOException;

public class WeatherService {
    private final WeatherProvider weatherProvider;
    private final TemperatureClassifier classifier;

    public WeatherService(WeatherProvider weatherProvider, TemperatureClassifier classifier) {
        this.weatherProvider = weatherProvider;
        this.classifier = classifier;
    }

    public WeatherResponse getWeather(WeatherRequest req) throws IOException, InterruptedException {
        var temp = weatherProvider.getTemperature(req.city());
        var weatherType = classifier.classify(temp);
        return new WeatherResponse(temp, weatherType);
    }
}
