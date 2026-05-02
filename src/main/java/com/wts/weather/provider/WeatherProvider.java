package com.wts.weather.provider;

import java.io.IOException;

public interface WeatherProvider {
    double getTemperature(String city) throws IOException, InterruptedException;
}
