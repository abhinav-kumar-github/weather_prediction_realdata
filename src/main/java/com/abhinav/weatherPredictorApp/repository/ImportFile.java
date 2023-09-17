package com.abhinav.weatherPredictorApp.repository;

import com.abhinav.weatherPredictorApp.models.AirportInfo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ImportFile {
    public static ArrayList<AirportInfo> getAllDestinationAirport(String filePath) {
        ArrayList<AirportInfo> destinationAirports = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] values = line.split(",");
                if (values.length != 3) {
                    System.out.println("Invalid content in line number " + lineNumber + "skipping this record");
                } else {
                    String name = values[0];
                    int hour = Integer.parseInt(values[1]);
                    int minute = Integer.parseInt(values[2]);
                    AirportInfo airportInfo = AirportInfo.getBuilder().setName(name)
                            .setHour(hour).setMinute(minute).build();
                    destinationAirports.add(airportInfo);
                }
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return destinationAirports;
    }
}
