package com.wts.city;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wts.city.dto.GeocodingResponse;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class CityGeocodingService {

    private final String apiKey;
    private final HttpClient client;
    private final ObjectMapper mapper;

    public CityGeocodingService(String apiKey) {
        this.apiKey = apiKey;
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    public double[] getCoordinates(String city) {
        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);

            var url = String.format(
                    "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s",
                    encodedCity,
                    apiKey
            );

            var request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            var geo = mapper.readValue(response.body(), GeocodingResponse.class);

            if (geo.results() == null || geo.results().isEmpty()) {
                throw new RuntimeException("City not found: " + city);
            }

            var lat = geo.results().getFirst().geometry.location.lat;
            var lon = geo.results().getFirst().geometry.location.lon;

            return new double[]{lat, lon};

        } catch (Exception e) {
            throw new RuntimeException("Failed to get coordinates", e);
        }
    }
}