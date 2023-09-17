package com.abhinav.weatherPredictorApp.utils;

public class UrlBuilder {
    public static String buildURL(String apiType, String airportCode) {
        StringBuilder urlStringBuilder = new StringBuilder();
        if(apiType.equals(Constants.CURRENT_API)) {
            urlStringBuilder.append(Constants.BASE_WEATHER_URL).append(Constants.CURRENT_API)
                    .append(Constants.RESPONSE_TYPE_JSON).append(Constants.KEY_QUERY_PARAM)
                    .append("&q=").append(airportCode);
        }
        else if(apiType.equals(Constants.FORECAST_API)) {
            urlStringBuilder.append(Constants.BASE_WEATHER_URL).append(Constants.FORECAST_API)
                    .append(Constants.RESPONSE_TYPE_JSON).append(Constants.KEY_QUERY_PARAM)
                    .append("&q=").append(airportCode).append("&days=2");
        }
        else {
            throw new RuntimeException("Invalid URL parameters, URL not generated");
        }
        return urlStringBuilder.toString();
    }
}
