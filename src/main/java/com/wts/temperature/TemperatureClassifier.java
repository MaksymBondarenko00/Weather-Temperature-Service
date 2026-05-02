package com.wts.temperature;

public class TemperatureClassifier {
    public String classify(double temp) {
        if (temp < 0) return "Freezing";
        if (temp <= 10) return "Cold";
        if (temp <= 20) return "Mild";
        if (temp <= 30) return "Warm";
        return "Hot";
    }
}
