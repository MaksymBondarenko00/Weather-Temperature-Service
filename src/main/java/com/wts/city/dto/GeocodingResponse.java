package com.wts.city.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeocodingResponse(
        List<Result> results
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        public Geometry geometry;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geometry {
        public Location location;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location {
        public double lat;
        public double lng;
    }
}