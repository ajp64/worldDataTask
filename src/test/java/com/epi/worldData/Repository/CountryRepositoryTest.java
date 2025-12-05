package com.epi.worldData.Repository;

import com.epi.worldData.Model.CountryData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class CountryRepositoryTest {
    @Autowired
    private CountryRepository testSubject;

    private CountryData testCountry1;
    private CountryData testCountry2;
    private CountryData testCountry3;

    @BeforeEach
    public void setUp() {
        testCountry1 = new CountryData(1L, "MN",
                "Mongolia",
                "Asia",
                "Asia",
                "Eastern Asia",
                "Sovereign country",
                1544322.15,
                2923896.0,
                68.847,
                11348.647);

        testCountry2 = new CountryData(2L, "BD",
                "Bangladesh",
                "Asia",
                "Asia",
                "Southern Asia",
                "Sovereign country",
                133782.1414,
                1.59405279E8,
                71.803,
                2973.041565);

        testCountry3 = new CountryData(3L, "PL",
                "Poland",
                "Europe",
                "Europe",
                "Eastern Europe",
                "Sovereign country",
                310402.333,
                3.8011735E7,
                77.60243902,
                24347.07366);


        testSubject.save(testCountry1);
        testSubject.save(testCountry2);
        testSubject.save(testCountry3);
    }

    @AfterEach
    public void tearDown() {
        testSubject.deleteAll();
    }

    @Test
    void testFindContinentWithMostCountries() {
        String result = testSubject.findContinentWithMostCountries();

        assertEquals(result, "Asia");
    }

    @Test
    void testFindDistinctByRegion() {
        List<String> result = testSubject.findDistinctByRegion();

        assertEquals(2, result.size());
        assertThat(result).contains("Europe", "Asia");
    }

    @Test
    void testSumAreaByRegion() {
        Double result = testSubject.sumAreaByRegion("Asia");
        Double actual = testCountry1.getAreaKm2() + testCountry2.getAreaKm2();

        assertEquals(actual , result);
    }

    @Test
    void testFindTopByOrderByLifeExpectancyDesc() {
        CountryData result = testSubject.findTopByOrderByLifeExpectancyDesc();

        assertEquals("Poland" , result.getName());
    }

    @Test
    void testFindDistinctBySubregion() {
        List<String> result = testSubject.findDistinctBySubregion();

        assertEquals(3, result.size());
        assertThat(result).contains("Eastern Asia", "Eastern Europe", "Southern Asia");
    }

    @Test
    void testCountBySubregion() {
        Double result = testSubject.countBySubregion("Southern Asia");

        assertEquals(1, result);
    }

    @Test
    void testSumGdppercapBySubregion() {
        Double result = testSubject.sumGdppercapBySubregion("Eastern Europe");
        Double actual = testCountry3.getGdpPerCap();

        assertEquals(actual , result);
    }

    @Test
    void testSumPopulationByRegion() {
        Double result = testSubject.sumPopulationByRegion("Asia");
        Double actual = testCountry1.getPopulation() + testCountry2.getPopulation();

        assertEquals(actual , result);
    }
}
