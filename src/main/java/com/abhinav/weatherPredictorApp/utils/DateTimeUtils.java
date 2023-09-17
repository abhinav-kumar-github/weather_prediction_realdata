package com.abhinav.weatherPredictorApp.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm ");
    public static String sourceAirportTimezone;
    public static ZonedDateTime getLocalizedTime(LocalDateTime localDateTime, String sourceZone, String destinationZone) {
        return localDateTime.atZone(ZoneId.of(sourceZone)).withZoneSameInstant(ZoneId.of(destinationZone));
    }
}
