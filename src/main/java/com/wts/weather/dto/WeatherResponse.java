package com.wts.weather.dto;

public record WeatherResponse(
        double temperature,
        String category
) {
}
