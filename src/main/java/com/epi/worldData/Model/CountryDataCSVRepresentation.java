package com.epi.worldData.Model;

import com.epi.worldData.util.StringParser;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.epi.worldData.util.DoubleParser;
import lombok.*;

// POJO which represents how the CSV data will get mapped to the database
// For Doubles, uses DoubleConverter to return null when non-double values are provided in the dataset

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryDataCSVRepresentation {

    @CsvCustomBindByName(column = "iso_a2", converter = StringParser.class)
    private String isoA2;
    @CsvBindByName(column = "name_long")
    private String name;
    @CsvBindByName(column = "continent")
    private String continent;
    @CsvBindByName(column = "region_un")
    private String region;
    @CsvBindByName(column = "subregion")
    private String subregion;
    @CsvBindByName(column = "type")
    private String type;
    @CsvBindByName(column = "area_km2")
    @CsvCustomBindByName(converter = DoubleParser.class)
    private Double area_km2;
    @CsvBindByName(column = "pop")
    @CsvCustomBindByName(converter = DoubleParser.class)
    private Double pop;
    @CsvBindByName(column = "lifeExp")
    @CsvCustomBindByName(converter = DoubleParser.class)
    private Double lifeExp;
    @CsvBindByName(column = "gdpPercap")
    @CsvCustomBindByName(converter = DoubleParser.class)
    private Double gdpPerCap;
}
