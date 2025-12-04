# Development log for project

### 1:
Wrote out a plan for the approach to the task. Start by creating a Spring project, that contains a service for parsing a CSV and storing into a relational database. For now, will use H2 as a quick database solution.
Researched effective ways to process CSV's in Spring, and used resources from Baeldung and a Medium article as a starting point. Using the opencsv dependency, which is a csv parser library for Java.
### Current progress:
 - Succesfully parsing the worldData.csv and storing the data in the database
 - Handling #NA values for Doubles by returning nulls
 - Mapping is set up to only store one row for each country, removing duplicates

### Notes:
 - Some duplicate lines have different data, need to ensure all data for each country is returned
 - Some Double values are negative or 0. These should be handled as nulls (considered just setting all negatives as positives, but looking at the data it seems some negative values are incorrect)

### References
https://opencsv.sourceforge.net/#general
<br>
https://www.baeldung.com/opencsv
<br>
https://medium.com/@mohamedhedi.aissi/spring-boot-csv-service-using-opencsv-5afd5c66c125


### 2:
Added functionality to output a CSV with the cleaned data which is stored in the database. Created the DataAnalysis service and repository methods required for querying and answering the questions about the data. 
Current App flow is:
On startup, CSV is cleaned and stored in database -> Answers to questions are printed in the console -> cleaned CSV is output
### Current progress:
 - CSV is cleaned, stored in DB, and a new CSV is output from the cleaned data in the DB
 - Repository and Service set up to answer provided questions about the dataset

### Notes:
 - Currently just answering the questions by printing to the console, could present this better through a UI
 - Added a controller class, as I was considering the option of providing endpoints for accessing the DataAnalysis service class. This could be implemented later.

### 3:
Added a UI using Thymeleaf, that displays the data as a table in the browsers, with fields to filter the data, and displays showing sums and averages of data from the selected set.

### Current progress:
 - UI can be accessed using the /countryData endpoint, which allows the user to filter and view sums/averages

### Notes:
 - Currently keeping everything in a monorepo. This should make it easier to deploy, however a microservice structure could be more clean / decoupled.
 - Presentation is very basic - UI could be much cleaner and appealing

### 4:
Improved UI, by adding a button to download the CSV. Added a dockerfile, created an image and uploaded it to my dockerhub (ajp64/epi-task). Added more tests for repo methods. 

### Current progress:
 - App can be run in docker, by building the image and accessing the UI on http:localhost:8080/countryData. 

### Notes:
 - Testing ideally should be much more robust, i.e. integration testing.
 - Need to add bar graph to UI.
 - Filters in the UI should be relaxed, currently need exact matches by case to properly filter. 





