package com.epi.worldData.Service;

import com.epi.worldData.Model.CountryData;
import com.epi.worldData.Model.CountryDataCSVRepresentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryDataMapperServiceTest {

    @Mock
    CountryDataMapperService testSubject;

    final private CountryDataCSVRepresentation csv =
            new CountryDataCSVRepresentation(
                    "isoCSV",
                    "nameCSV",
                    "continentCSV",
                    "regionCSV",
                    "subregionCSV",
                    "typeCSV",
                    1.0,
                    2000.0,
                    50.0,
                    4.0);

    final private CountryData entityData =
            new CountryData(1L,
                    "isoEntity",
                    "nameEntity",
                    "continentEntity",
                    "regionEntity",
                    "subregionEntity",
                    "typeEntity",
                    0.1,
                    1500.0,
                    60.0,
                    0.4);

    @BeforeEach
    void setUp(){
        testSubject = new CountryDataMapperService() {};
    }

    @Test
    void testMapTo()
    {
        CountryData result = testSubject.mapTo(csv);
        assertEquals(result.getIsoA2(), csv.getIsoA2());
        assertEquals(result.getName(), csv.getName());
        assertEquals(result.getPopulation(), csv.getPop());
        assertEquals(result.getGdpPerCap(), csv.getGdpPerCap());
    }

    @Test
    void testUnMapFrom()
    {
        CountryDataCSVRepresentation result = testSubject.unmapFrom(entityData);
        assertEquals(result.getRegion(), entityData.getRegion());
        assertEquals(result.getSubregion(), entityData.getSubregion());
        assertEquals(result.getLifeExp(), entityData.getLifeExpectancy());
        assertEquals(result.getArea_km2(), entityData.getAreaKm2());
    }
}
