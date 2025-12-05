package com.epi.worldData.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CountryData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isoA2;
    private String name;
    private String continent;
    private String region;
    private String subregion;
    private String type;
    private Double areaKm2;
    private Double population;
    private Double lifeExpectancy;
    private Double gdpPerCap;
}
