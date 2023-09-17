package com.abhinav.weatherPredictorApp.services;

import com.abhinav.weatherPredictorApp.models.AirportInfo;
import com.abhinav.weatherPredictorApp.utils.Constants;
import com.abhinav.weatherPredictorApp.utils.DateTimeUtils;
import com.abhinav.weatherPredictorApp.utils.UrlBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

public class ExternalApi {
    public static String sourceAirportTimezone;
    public static double sourceAirportTemperature;
    public static AirportInfo sourceAirportInfo;
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static void getCurrentTemperature(String sourceAirportCode) {
        String URL = UrlBuilder.buildURL(Constants.CURRENT_API, sourceAirportCode);
        RestTemplate restTemplate = new RestTemplate();
        String resultString = "";

        try {
            String responseAsJsonString = restTemplate.getForObject(URL, String.class);
            if(responseAsJsonString == null || responseAsJsonString == "") {
                throw new Exception("Invalid call, no response received");
            }
            JsonNode jsonNode = objectMapper.readTree(responseAsJsonString);
            JsonNode location = jsonNode.get("location");
            JsonNode current = jsonNode.get("current");

            String name = location.get("name").asText();
            String region = location.get("region").asText();
            String country = location.get("country").asText();
            sourceAirportTimezone = location.get("tz_id").asText();
            long epoch = location.get("localtime_epoch").asLong();
            DateTimeUtils.sourceAirportTimezone = sourceAirportTimezone;
            sourceAirportTemperature = current.get("temp_f").asDouble();

            sourceAirportInfo = AirportInfo.getBuilder().setName(name).build();
            sourceAirportInfo.setCityName(region);
            sourceAirportInfo.setCountry(country);
            sourceAirportInfo.setTimeZone(sourceAirportTimezone);
            sourceAirportInfo.setTemperature(sourceAirportTemperature);
            sourceAirportInfo.setEpoch(epoch);

            System.out.println("Starting City  : " + sourceAirportCode
                    + " (" + sourceAirportInfo.getName() + ", " + sourceAirportInfo.getCountry() + ")");
            System.out.println("Time at Source : " + sourceAirportInfo.getLocalDateTime().format(DateTimeUtils.formatter)
                    + " " + sourceAirportInfo.getTimeZone());
            System.out.println("Current temperature at Source : " + sourceAirportInfo.getTemperature() + "°F ");

            resultString = "Starting Temperature in " + sourceAirportInfo.getName() + " ("
                    + sourceAirportInfo.getCountry() + ") : " + sourceAirportInfo.getTemperature() + "°F "
                    + sourceAirportInfo.getLocalDateTime().format(DateTimeUtils.formatter)
                    + " " + sourceAirportInfo.getTimeZone();

        } catch (NullPointerException ex) {
            System.out.println("URL not valid");
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
    public static void getForecastTemperature(AirportInfo airportInfo) {
        String URL = UrlBuilder.buildURL(Constants.FORECAST_API, airportInfo.getName());
        RestTemplate restTemplate = new RestTemplate();
        String resultString = "";

        long expectedEpoch = sourceAirportInfo.getEpoch()
                + (airportInfo.getHour() * 1L * 60 * 60) + (airportInfo.getMinute() * 1L * 60l);

        try {
            String responseAsJsonString = restTemplate.getForObject(URL, String.class);
            if(responseAsJsonString == null || responseAsJsonString == "") {
                throw new Exception("Invalid call, no response received");
            }
            JsonNode jsonNode = objectMapper.readTree(responseAsJsonString);
            JsonNode location = jsonNode.get("location");
            String region     = location.get("region").asText();
            String country    = location.get("country").asText();
            String timezone   = location.get("tz_id").asText();
            airportInfo.setCityName(region);
            airportInfo.setCountry(country);
            airportInfo.setTimeZone(timezone);
            airportInfo.setEpoch(expectedEpoch);
            airportInfo.setTimeZone(location.get("tz_id").asText());

            JsonNode forecastDayList = jsonNode.get("forecast").get("forecastday");

            for(int dayIndex=0; dayIndex<forecastDayList.size(); dayIndex++) {
                JsonNode forecastDay = forecastDayList.get(dayIndex);
                JsonNode dayHourList = forecastDay.get("hour");
                for(int hourIndex=0; hourIndex<dayHourList.size(); hourIndex++) {
                    JsonNode dayHour = dayHourList.get(hourIndex);
                    long hourEpoch = dayHour.get("time_epoch").asLong();
                    if (Math.abs(expectedEpoch - hourEpoch) <= 1800) {
                        double temperature = dayHour.get("temp_f").asDouble();
                        airportInfo.setTemperature(temperature);
                    }
                }
            }
        } catch (NullPointerException ex) {
            System.out.println("URL not valid");
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
