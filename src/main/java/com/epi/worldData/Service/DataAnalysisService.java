package com.epi.worldData.Service;

import com.epi.worldData.Repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
The DataAnalysisService accesses the repository layer, using methods from there to fetch data and either simply return it,
or perform further logic and calculations to answer questions about the data.
 */

@Service
public class DataAnalysisService {

    private final CountryRepository repository;

    public DataAnalysisService(CountryRepository repository) { this.repository = repository;}

    public String findContinentWithMostCountries() {
        return repository.findContinentWithMostCountries();
    }

    // This method finds all distinct regions in the dataset, then gets the sum of area for each region. These values are
    // put in a map of region to areaSum, and the region with the highest area is returned as a string.
    public String findRegionWithGreatestArea() {
        Map<String, Double> regionAreas = new HashMap<>();
        List<String> allRegions = repository.findDistinctByRegion();
        allRegions.forEach(region -> {
            Double regionArea = repository.sumAreaByRegion(region);
            regionAreas.put(region, regionArea);
        });

        return regionAreas.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No value found");
    }

    public String findCountryWithHighestPopulation() {
        return repository.findTopByOrderByPopulationDesc().getName();
    }

    public String findSubregionWithHighestAverageGdpPerCap() {
        Map<String, Double> subregionAverageGdp = createMapOfSubregionToAvGdpPerCap();

        return subregionAverageGdp.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No value found");
    }

    public String findSubregionWithLowestAverageGdpPerCap() {
        Map<String, Double> subregionAverageGdp = createMapOfSubregionToAvGdpPerCap();

        return subregionAverageGdp.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No value found");
    }

    // This method creates a map of Subregion to Average GDP per capita, and is called in the two above methods
    // to return the highest and lowest Subregion by gdp per capita.
    private Map<String, Double> createMapOfSubregionToAvGdpPerCap() {
        Map<String, Double> subregionAverageGdp = new HashMap<>();
        List<String> allSubregions = repository.findDistinctBySubregion();
        allSubregions.forEach(subregion -> {
            Double subregionGDPSum = repository.sumGdppercapBySubregion(subregion);
            if (subregionGDPSum != null) {
                Double numberOfSubregionEntries = repository.countBySubregion(subregion);
                Double averageGDP = subregionGDPSum / numberOfSubregionEntries;
                subregionAverageGdp.put(subregion, averageGDP);
            }
        });

        return subregionAverageGdp;
    }
}
