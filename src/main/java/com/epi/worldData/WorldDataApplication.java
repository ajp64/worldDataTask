package com.epi.worldData;

import com.epi.worldData.Service.CountryDataService;
import com.epi.worldData.Service.DataAnalysisService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WorldDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorldDataApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(CountryDataService countryDataService, DataAnalysisService dataAnalysisService) {
		return args -> {
			countryDataService.populateCountryData();
			System.out.println("Continent with most countries in list: " + dataAnalysisService.findContinentWithMostCountries());
			System.out.println("Region with largest area: " + dataAnalysisService.findRegionWithGreatestArea());
			System.out.println("Country with highest population: " + dataAnalysisService.findCountryWithHighestPopulation());
			System.out.println("Subregion with highest average GDP: " + dataAnalysisService.findSubregionWithHighestAverageGdpPerCap());
			System.out.println("Subregion with lowest average GDP: " + dataAnalysisService.findSubregionWithLowestAverageGdpPerCap());
		};
	};

}
