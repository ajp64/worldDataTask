package com.epi.worldData.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CountryData {
    @Id
    private String isoA2;
    private String name;
    private String continent;
    private String region;
    private String subregion;
    private String type;
    private int areaKm2;
    private int population;
    private int lifeExpectancy;
    private int gdpPerCap;
}
