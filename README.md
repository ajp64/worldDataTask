# Development log for project

### 1:
Wrote out a plan for the approach to the task. Start by creating a Spring project, that contains a service for parsing a CSV and storing into a relational database. For now, will use H2 as a quick database solution.
Researched effective ways to process CSV's in Spring, and used resources from Baeldung and a Medium article as a starting point. Using the opencsv dependency, which is a csv parser library for Java.
### Current progress:
 - Succesfully parsing the worldData.csv and storing the data in the database
 - Handling #NA values for Doubles by returning nulls
 - Mapping is set up to only store one row for each country, removing duplicates

### Next steps:
 - Some duplicate lines have different data, need to ensure all data for each country is returned
 - Some Double values are negative or 0. These should be handled as nulls, as the information does not appear reliable

### References
https://opencsv.sourceforge.net/#general
<br>
https://www.baeldung.com/opencsv
<br>
https://medium.com/@mohamedhedi.aissi/spring-boot-csv-service-using-opencsv-5afd5c66c125




