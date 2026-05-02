package com.wts.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenMeteoResponse(
        Current current
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Current {
        @JsonProperty("temperature_2m")
        public double temperature;
    }
}
