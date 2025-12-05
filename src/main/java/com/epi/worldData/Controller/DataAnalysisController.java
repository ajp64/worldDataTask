package com.epi.worldData.Controller;

import com.epi.worldData.Model.CountryData;
import com.epi.worldData.Service.DataAnalysisService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
This controller uses the /countryData endpoint to return an HTML template, which is populated with data
from the dataAnalysisService. Request parameters from the UI are used for filtering.
 */
@Controller
public class DataAnalysisController {

    private final DataAnalysisService dataAnalysisService;

    public DataAnalysisController(DataAnalysisService dataAnalysisService) {this.dataAnalysisService = dataAnalysisService;}

    @GetMapping(value = "/")
    public String index(
            Model model,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String continent,
            @RequestParam(required = false) String subregion,
            @RequestParam(required = false) String type
    ) {
        List<CountryData> dataPage = dataAnalysisService.findAll();

        List<CountryData> filteredList = dataPage.stream()
                        .filter(p -> region == null || p.getRegion().toLowerCase().contains(region.toLowerCase()))
                        .filter(p -> continent == null || p.getContinent().toLowerCase().contains(continent.toLowerCase()))
                        .filter(p -> subregion == null || p.getSubregion().toLowerCase().contains(subregion.toLowerCase()))
                        .filter(p -> type == null || type.isEmpty() || p.getType().toLowerCase().matches(type.toLowerCase()))
                        .collect(Collectors.toList());

        double areaSum = sumDouble(filteredList, CountryData::getAreaKm2);
        double popSum  = sumDouble(filteredList, CountryData::getPopulation);
        double gdpAv  = averageDouble(filteredList, CountryData::getGdpPerCap);
        double lifeExpAv  = averageDouble(filteredList, CountryData::getLifeExpectancy);

        model.addAttribute("dataPage", filteredList);
        model.addAttribute("areaSum", areaSum);
        model.addAttribute("popSum", popSum);
        model.addAttribute("gdpAv", gdpAv);
        model.addAttribute("lifeExpAv", lifeExpAv);

        return "dataPage";
    }

    @GetMapping(value = "/download-csv")
    public ResponseEntity<Resource> downloadCsv() throws IOException {
        String tmp = System.getProperty("java.io.tmpdir");
        Path path = Paths.get(tmp, "output.csv");

        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=output.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }

    @GetMapping(value = "/popDensity")
    public String popDensity(Model model) {
        Map<String, Double> regionPopDensity = dataAnalysisService.findAveragePopDensityByRegion();

        model.addAttribute("chartData", regionPopDensity);

        return "popDensityGraph";
    }

    @GetMapping(value = "/questions")
    public String questions(Model model) {
        String mostCountries = dataAnalysisService.findContinentWithMostCountries();
        String regionGreatestArea = dataAnalysisService.findRegionWithGreatestArea();
        String countryHighestPop = dataAnalysisService.findCountryWithHighestLifeExpectancy();
        String subregionHighestAvGdpPerCap = dataAnalysisService.findSubregionWithHighestAverageGdpPerCap();
        String subregionLowestAvGdpPerCap = dataAnalysisService.findSubregionWithLowestAverageGdpPerCap();

        model.addAttribute("mostCountries", mostCountries);
        model.addAttribute("largestRegion", regionGreatestArea);
        model.addAttribute("countryHighestLifeExp", countryHighestPop);
        model.addAttribute("lowestGdpSubregion", subregionLowestAvGdpPerCap);
        model.addAttribute("highestGdpSubregion", subregionHighestAvGdpPerCap);

        return "dataInsights";
    }

    private <T> double sumDouble(List<T> list, Function<T, Double> getter) {
        return list.stream()
                .map(getter)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private <T> double averageDouble(List<T> list, Function<T, Double> getter) {
        DoubleSummaryStatistics stats = list.stream()
                .map(getter)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();

        return stats.getAverage();
    }

}
