# Running the code
## Docker
The easiest way to run the application is using Docker. I have pushed an image to my Dockerhub. 
(If you're unfamiliar with Docker you can get started [here](https://www.docker.com/get-started/))
<br>
With Docker running, pull the image down using:
<br>
<br>
`docker pull ajp64/epi-task:latest`
<br>
<br>
Once the image has downloaded, run with:
<br>
<br>
`docker run -p 8080:8080 ajp64/epi-task`
<br>
<br>
Note this is using port 8080, so this must be available on your local computer.

If successful, the application should be accessible through [http://localhost:8080](http://localhost:8080/)

## Clone repo, and build locally
Alternatively, you can build locally by cloning this repo. You will need installed:
 - Java 17
 - Maven (I'm using 3.9.6)

Clone the repo either by copying the url from GitHub, and using the following commands:

`git clone <repo_url>`  
`cd <project_folder>`

When in the project folder, install using Maven:

`mvn clean install`

If successful, you should be able to run the app using the following command, and access through [http://localhost:8080](http://localhost:8080/):

```bash
mvn spring-boot:run
```

# Walkthrough
The app contains the worldData.csv file that you provided. When the app runs, this file is parsed and stored in a database, and a cleaned CSV is exported. 
<br>
<br>
When you go to the route page of the app (http://localhost:8080, if running using instructions above) there is a table displaying all the data stored in the database. There are fields availble to filter the data, with running sums and averages of different columns which update depending on the filtered dataset. 
<br>
<br>
There is a download CSV button, which will download a CSV of the data as stored in the database to your local drive.
<br>
<br>
There are two button links, one which will take you to a page answering the questions that were provided in the task, and another which links to a bar graph of population density by region. 


# Planning 
 - For language and framework, I decided to use Java in Spring. This was mainly because I felt most comfortable using it to complete the task in the alloted time, and the Spring framerwork is well established with many useful libraries which I figured would help with this task.

 - I initially thought about a microservice arrangement, using a front end and back end service. But I decided to keep it to a monorepo, as I figured that would be the simplest way to deliver the software. I wanted to create a docker image that was easy to download and run, and by keeping it to a single service in an image, I wouldn't have to spend time composing multiple containers.

 - To keep it streamlined, rather than using a front end framework like React or Angular, I went with Thymeleaf, an HTML templating library that integrates with Spring. Although this might not be the best choice in a professional project, it provided what I needed to create a quick, effective UI that met the requirements.

- Considering the database, I decided to use the h2 database in Spring as an in memory solution for persistence. This a quick and easy way to set up a relational database within the app. In a real world solution, ideally this app would use persistance so it doesn't need to be seeded each time the app runs, but for the purposes of this exercise I thought it was a nice and easy configurable solution.

- I planned to used the typical Controller -> Service -> Repository model. Using a single Controller to handle the UI, which wired in a service that provided the information about the data required. Other services could handle the CSV requirements. 


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

### 5:
Fixed filter case sensitivity issue, where filters had to match exactly what was in the table to work. Now any case should filter. Noticed I had a question wrong (was getting highest pop instead of life exp).
When investigating this, I noticed there was an obvious piece of incorrect data for South Africa's life expectancy. Guarded against this by returning null when parsing the data, for any life expectancy below 40 or above 90.
Added a bar graph using Thymeleaf with Google Charts. 

### Current progress:
 - App now runs on http:localhost:8080. Now contains links to the questions from the task, and a graph of population density.

### References
https://www.wimdeblauwe.com/blog/2021/01/05/using-google-charts-with-thymeleaf/


# Potential for improvement 
 - The main area I would want to focus more on is the testing. While I have added unit tests across Repository, Service and Controller classes, the app is definitely not fully tested, and is not integration tested. It could use proper integration tests exercising more inter-service interactions, as well as setting up a mock data set for better testing of repository methods. 

 - Splitting this project out into a microservice based structure with a more robust front end framework could improve it and make it more reuseable. Everything is quite tightly coupled currently. In a microservice structure, one service could handle all data requirements, a seperate service could handle CSV requirements, and the front end could be handled on a seperate service, with all 3 using RESTful api calls. 

 - The UI is very basic. There could be a lot of improvement there, ideally through the use of better CSS or libraries for visualisations. The list of data should be paginated, or perhaps lazy loaded as the user scrolls.

 - Currently, when the app runs the CSV file is automatically parsed and stored in the database, then automatically exported to temp location. This could be improved by allowing the user to provide the CSV for insertion into the database, and only exporting when the request is made. 




