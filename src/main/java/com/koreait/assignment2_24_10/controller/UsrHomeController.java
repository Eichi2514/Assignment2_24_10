package com.koreait.assignment2_24_10.controller;

import com.koreait.assignment2_24_10.service.WeatherService;
import com.koreait.assignment2_24_10.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsrHomeController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/usr/home/main")
    public String showMain(Model model, @RequestParam(defaultValue = "서울") String city) {
        WeatherResponse weather = weatherService.getWeather(city);

        if (weather == null) {
            model.addAttribute("error", "날씨 정보를 가져오는 데 실패했습니다.");
            return "usr/home/main";
        }

        city = weatherService.getRegionCode(city);

        model.addAttribute("temperature", weather.getMain().getTemp());
        model.addAttribute("humidity", weather.getMain().getHumidity());
        model.addAttribute("windSpeed", weather.getWind().getSpeed());
        model.addAttribute("city", city);

        return "usr/home/main";
    }
}
