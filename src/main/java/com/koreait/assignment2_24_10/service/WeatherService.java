package com.koreait.assignment2_24_10.service;

import com.koreait.assignment2_24_10.vo.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class WeatherService {

    @Value("${API_KEY}")
    private String API_KEY;

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    private static final Map<String, String> CITY_MAP = Map.ofEntries(
            Map.entry("서울", "Seoul"),
            Map.entry("대전", "Daejeon"),
            Map.entry("부산", "Busan"),
            Map.entry("인천", "Incheon"),
            Map.entry("대구", "Daegu"),
            Map.entry("광주", "Gwangju"),
            Map.entry("경기도", "Gyeonggi-do"),
            Map.entry("강원도", "Gangwon-do"),
            Map.entry("충청남도", "Chungcheongnam-do"),
            Map.entry("세종", "Sejong"),
            Map.entry("충청북도", "Chungcheongbuk-do"),
            Map.entry("전라북도", "Jeollabuk-do"),
            Map.entry("울산", "Ulsan"),
            Map.entry("경상북도", "Gyeongsangbuk-do"),
            Map.entry("전라남도", "Jeollanam-do"),
            Map.entry("경상남도", "Gyeongsangnam-do"),
            Map.entry("제주도", "Jeju-do")
    );

    public WeatherResponse getWeather(String cityName) {
        if (cityName == null || cityName.isEmpty()) {
            cityName = "서울";
        }

        cityName = getRegionCode(cityName);

        String englishCityName = CITY_MAP.getOrDefault(cityName, cityName);  // 한글을 영어로 변환
        String url = BASE_URL + "?q=" + URLEncoder.encode(englishCityName, StandardCharsets.UTF_8) + "&appid=" + API_KEY + "&units=metric";
        RestTemplate restTemplate = new RestTemplate();

        try {
            return restTemplate.getForObject(url, WeatherResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                System.err.println("도시를 찾을 수 없습니다: " + cityName);
            } else {
                System.err.println("날씨 정보를 가져오는 도중 오류가 발생했습니다: " + e.getMessage());
            }
            return null;
        } catch (Exception e) {
            System.err.println("날씨 정보를 가져오는 도중 예기치 않은 오류가 발생했습니다: " + e.getMessage());
            return null;
        }
    }

    public String getRegionCode(String cityName) {
        if (cityName.contains("인천")) return "인천";
        else if (cityName.contains("서울")) return "서울";
        else if (cityName.contains("경기")) return "경기도";
        else if (cityName.contains("강원")) return "강원도";
        else if (cityName.contains("충남") || cityName.contains("충청남도") ) return "충청남도";
        else if (cityName.contains("세종")) return "세종";
        else if (cityName.contains("대전")) return "대전";
        else if (cityName.contains("충북") || cityName.contains("충청북도")) return "충청북도";
        else if (cityName.contains("전북") || cityName.contains("전라북도")) return "전라북도";
        else if (cityName.contains("대구")) return "대구";
        else if (cityName.contains("울산")) return "울산";
        else if (cityName.contains("경북") || cityName.contains("경상북도")) return "경상북도";
        else if (cityName.contains("전남") || cityName.contains("전라남도")) return "전라남도";
        else if (cityName.contains("광주")) return "광주";
        else if (cityName.contains("경남") || cityName.contains("경상남도")) return "경상남도";
        else if (cityName.contains("부산")) return "부산";
        else if (cityName.contains("제주")) return "제주도";
        return null;
    }
}