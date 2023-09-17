package com.abhinav.weatherPredictorApp.services;

import com.abhinav.weatherPredictorApp.models.AirportInfo;
import com.abhinav.weatherPredictorApp.utils.DateTimeUtils;

public class TemperatureAnalysis {
    public static void service(AirportInfo airportInfo) {
        ExternalApi.getForecastTemperature(airportInfo);
        printDestinationAirportDetails(airportInfo);
    }

    private static void printDestinationAirportDetails(AirportInfo airportInfo) {
        boolean isSweaterNeeded = false;
        double sourceAirportTemperature = ExternalApi.sourceAirportInfo.getTemperature();
        System.out.println("\n#######################################################");
        System.out.println("Destination : " + airportInfo.getName() + " (" + airportInfo.getCityName()
                + ", " + airportInfo.getCountry() + ")");
        System.out.println("Travel time : " + airportInfo.getHour() + " hour " + airportInfo.getMinute() + " minute");
        System.out.println("Reaching Time (source TimeZone)      : "
                + DateTimeUtils.getLocalizedTime(airportInfo.getLocalDateTime(), airportInfo.getTimeZone(),
                ExternalApi.sourceAirportInfo.getTimeZone()).format(DateTimeUtils.formatter)
                + " " + ExternalApi.sourceAirportInfo.getTimeZone());
        System.out.println("Reaching Time (destination Timezone) : "
                + airportInfo.getLocalDateTime().format(DateTimeUtils.formatter) + " " + airportInfo.getTimeZone());
        System.out.println("Temperature at destination           : " + airportInfo.getTemperature() + "°F");
        if (sourceAirportTemperature - airportInfo.getTemperature() > 20.0) {
            isSweaterNeeded = true;
        }
        System.out.println("Sweater needed : " + (isSweaterNeeded ? "Yes, You will feel cold" : "No, it's okay"));
    }
}
