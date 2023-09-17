package com.abhinav.weatherPredictorApp;

import com.abhinav.weatherPredictorApp.models.AirportInfo;
import com.abhinav.weatherPredictorApp.repository.ImportFile;
import com.abhinav.weatherPredictorApp.services.ExternalApi;
import com.abhinav.weatherPredictorApp.services.TemperatureAnalysis;
import com.abhinav.weatherPredictorApp.utils.Constants;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class WeatherPredictorApplication {
	public static void main(String[] args) {
//		SpringApplication.run(WeatherPredictorApplication.class, args);
		System.out.println("Weather Predictor Application");
		String filePath = Constants.FILE_PATH;
		ArrayList<AirportInfo> destinationAirports = ImportFile.getAllDestinationAirport(filePath);
		//ExternalAPI.getCurrentTemperature("AKL");
		ExternalApi.getCurrentTemperature("SFO");
		//ExternalAPI.sourceAirportInfo.setTemperature(48.0d);
		destinationAirports.forEach(TemperatureAnalysis::service);
	}

}
