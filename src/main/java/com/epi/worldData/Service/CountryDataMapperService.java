package com.epi.worldData.Service;

import com.epi.worldData.Model.CountryData;
import com.epi.worldData.Model.CountryDataCSVRepresentation;
import com.epi.worldData.util.CSVMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Mapper service, that uses the Builder and Getters from the data models to map between the CSV and database

@Service
public class CountryDataMapperService implements CSVMapper<CountryData, CountryDataCSVRepresentation> {

    @Override
    public CountryData mapTo(CountryDataCSVRepresentation k) {
        return CountryData.builder()
                .isoA2(k.getIsoA2())
                .name(k.getName())
                .continent(k.getContinent())
                .region(k.getRegion())
                .subregion(k.getSubregion())
                .type(k.getType())
                .areaKm2(k.getArea_km2())
                .population(checkPop(Optional.ofNullable(k.getPop()).orElse(0.0)))
                .lifeExpectancy(checkLifeExp(Optional.ofNullable(k.getLifeExp()).orElse(0.0)))
                .gdpPerCap(k.getGdpPerCap())
                .build();
    }

    @Override
    public CountryDataCSVRepresentation unmapFrom(CountryData t) {
        return CountryDataCSVRepresentation.builder()
                .isoA2(t.getIsoA2())
                .name(t.getName())
                .continent(t.getContinent())
                .region(t.getRegion())
                .subregion(t.getSubregion())
                .type(t.getType())
                .area_km2(t.getAreaKm2())
                .pop(checkPop(t.getPopulation()))
                .lifeExp(checkLifeExp(t.getLifeExpectancy()))
                .gdpPerCap(t.getGdpPerCap())
                .build();
    }

    private Double checkLifeExp(Double lifeExp) {
        if (lifeExp > 90 || lifeExp < 40) {
            return null;
        }

        return lifeExp;
    }

    private Double checkPop(Double pop) {
        if (pop > 2000000000 || pop < 400) {
            return null;
        }

        return pop;
    }
}
