package com.wts.weather.dto;

public record WeatherResponse(
        String temperature,
        String category
) {
}
