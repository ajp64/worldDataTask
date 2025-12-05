package com.epi.worldData.Controller;

import com.epi.worldData.Model.CountryData;
import com.epi.worldData.Service.DataAnalysisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@WebMvcTest(DataAnalysisController.class)
@ActiveProfiles("test")
public class DataAnalysisControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DataAnalysisService dataAnalysisService;
    @BeforeEach
    void setUp(){
        a = new CountryData();
        a.setRegion("Europe");
        a.setContinent("Europe");
        a.setSubregion("Western Europe");
        a.setType("Country");
        a.setAreaKm2(100.0);
        a.setPopulation(50.0);
        a.setGdpPerCap(20000.0);
        a.setLifeExpectancy(80.0);

        b = new CountryData();
        b.setRegion("Asia");
        b.setContinent("Asia");
        b.setSubregion("East Asia");
        b.setType("Sovereign country");
        b.setAreaKm2(200.0);
        b.setPopulation(150.0);
        b.setGdpPerCap(10000.0);
        b.setLifeExpectancy(70.0);

        when(dataAnalysisService.findAll()).thenReturn(List.of(a, b));
    }

    private CountryData a;
    private CountryData b;

    @Test
    void testNoFiltersReturnsAllData() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("dataPage"))
                .andExpect(model().attribute("dataPage", hasSize(2)))
                .andExpect(model().attribute("areaSum", closeTo(300.0, 0.0001)))
                .andExpect(model().attribute("popSum", closeTo(200.0, 0.0001)))
                .andExpect(model().attribute("gdpAv", closeTo(15000.0, 0.0001)))
                .andExpect(model().attribute("lifeExpAv", closeTo(75.0, 0.0001)));
    }

    @Test
    void testFilterByRegion() throws Exception {
        mockMvc.perform(get("/")
                        .param("region", "Europe"))
                .andExpect(status().isOk())
                .andExpect(view().name("dataPage"))
                .andExpect(model().attribute("dataPage", hasSize(1)))
                .andExpect(model().attribute("areaSum", closeTo(100.0, 0.0001)))
                .andExpect(model().attribute("popSum", closeTo(50.0, 0.0001)))
                .andExpect(model().attribute("gdpAv", closeTo(20000.0, 0.0001)))
                .andExpect(model().attribute("lifeExpAv", closeTo(80.0, 0.0001)));
    }

    @Test
    void testMultipleFilters() throws Exception {
        mockMvc.perform(get("/")
                        .param("continent", "Asia")
                        .param("type", "Sovereign country"))
                .andExpect(status().isOk())
                .andExpect(view().name("dataPage"))
                .andExpect(model().attribute("dataPage", hasSize(1)))
                .andExpect(model().attribute("areaSum", closeTo(200.0, 0.0001)))
                .andExpect(model().attribute("popSum", closeTo(150.0, 0.0001)))
                .andExpect(model().attribute("gdpAv", closeTo(10000.0, 0.0001)))
                .andExpect(model().attribute("lifeExpAv", closeTo(70.0, 0.0001)));
    }
}
