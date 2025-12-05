package com.epi.worldData.Repository;

import com.epi.worldData.Model.CountryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
The repository methods use either direct SQL queries or JPA Query language to access data from the DB. These are called
in the service layer to answer the specified questions about the data.
 */

@Repository
public interface CountryRepository extends JpaRepository<CountryData, String> {

    @Query(
            value = "SELECT continent FROM COUNTRY_DATA GROUP BY continent ORDER BY COUNT(*) DESC LIMIT 1",
            nativeQuery = true
    )
    String findContinentWithMostCountries();

    @Query(value = "SELECT DISTINCT region FROM COUNTRY_DATA", nativeQuery = true)
    List<String> findDistinctByRegion();

    @Query(value = "SELECT SUM(area_km2) FROM COUNTRY_DATA WHERE region = ?1", nativeQuery = true)
    Double sumAreaByRegion(String region);

    CountryData findTopByOrderByPopulationDesc();
    @Query(value = "SELECT DISTINCT subregion FROM COUNTRY_DATA", nativeQuery = true)
    List<String> findDistinctBySubregion();
    Double countBySubregion(String subregion);
    @Query(value = "SELECT SUM(gdp_per_cap) FROM COUNTRY_DATA WHERE subregion = ?1", nativeQuery = true)
    Double sumGdppercapBySubregion(String subregion);

    @Query(value = "SELECT SUM(population) FROM COUNTRY_DATA WHERE region = ?1", nativeQuery = true)
    Double sumPopulationByRegion(String region);
}
