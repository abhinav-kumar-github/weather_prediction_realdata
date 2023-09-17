package com.abhinav.weatherPredictorApp.models;

import java.time.LocalDateTime;
import java.time.Instant;
import java.time.ZoneId;

public class AirportInfo {
    private String name;
    private int hour;
    private int minute;
    private String cityName;
    private String country;
    private String timeZone;
    private double temperature;
    private LocalDateTime localDateTime;
    private long epoch;

    private AirportInfo () {}
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public void setEpoch(long epoch) {
        this.epoch = epoch;
        Instant instant = Instant.ofEpochSecond(epoch);
        this.localDateTime = instant.atZone(ZoneId.of(this.timeZone)).toLocalDateTime();
    }
    public String getName() {
        return this.name;
    }
    public int getHour() {
        return this.hour;
    }
    public int getMinute() {
        return this.minute;
    }
    public String getCityName() {
        return this.cityName;
    }
    public String getCountry() {
        return this.country;
    }
    public String getTimeZone() {
        return this.timeZone;
    }
    public double getTemperature() {
        return this.temperature;
    }
    public long getEpoch() {
        return this.epoch;
    }
    public LocalDateTime getLocalDateTime() {
        return this.localDateTime;
    }
    public static AirportInfoBuilder getBuilder() {
        return new AirportInfoBuilder();
    }

    public static class AirportInfoBuilder {
        private String name;
        private int hour;
        private int minute;
        public AirportInfoBuilder setName(String name) {
            this.name = name;
            return this;
        }
        public AirportInfoBuilder setHour(int hour) {
            this.hour = hour;
            return this;
        }
        public AirportInfoBuilder setMinute(int minute) {
            this.minute = minute;
            return this;
        }
        public String getName() {
            return this.name;
        }

        public int getHour() {
            return this.hour;
        }

        public int getMinute() {
            return this.minute;
        }

        public AirportInfo build () {
            if (this.getName().length() == 0 || this.name == "") {
                throw new IllegalArgumentException("Not a valid destination name");
            }
            if (this.getHour() < 0) {
                throw new IllegalArgumentException("Hour value can't be a negative");
            }
            if (this.getMinute() < 0) {
                throw new IllegalArgumentException("Minute value can't be a negative");
            }
            AirportInfo airportInfo = new AirportInfo();
            airportInfo.name = this.name;
            airportInfo.hour = this.hour;
            airportInfo.minute = this.minute;

            return airportInfo;
        }
    }
}
