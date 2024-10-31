package com.koreait.assignment2_24_10.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {
    private Main main;
    private Wind wind;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Main {
        private double temp;
        private int humidity;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Wind {
        private double speed;
    }
}
