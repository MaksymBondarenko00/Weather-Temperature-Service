package com.wts.weather.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wts.city.CityGeocodingService;
import com.wts.weather.dto.OpenMeteoResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenMeteoWeatherProvider implements WeatherProvider {

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();
    private final CityGeocodingService geocodingService;

    public OpenMeteoWeatherProvider(CityGeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @Override
    public double getTemperature(String city) {
        try {
            double[] coords = geocodingService.getCoordinates(city);

            double lat = coords[0];
            double lon = coords[1];

            String url = String.format(
                    "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current=temperature_2m",
                    lat, lon
            );

            var request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to fetch weather. Status: " + response.statusCode());
            }

            var apiResponse =
                    mapper.readValue(response.body(), OpenMeteoResponse.class);

            return apiResponse.current().temperature;

        } catch (Exception e) {
            throw new RuntimeException("Failed to get temperature", e);
        }
    }
}