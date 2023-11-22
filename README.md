# bookshipment

## Description
This is a simple CRUD application that allows you to add, edit, delete and view vessels in a database. The application is written in Java and uses JavaFX for the GUI. The database is SQLite.

## Installation
To run the application, you need to have Java 20. You can download it from [here](https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html). You also need to have Maven 3.8.1 installed. You can download it from [here](https://maven.apache.org/download.cgi).


## Usage

The datebase should be populated, if its not, uncomment the  @load() 
 method in the start method, but remeber to uncoment it after the first run,
 or the database will be populated every time the application is started. 

Features: you can search for a avalibel route, with eny combination of start city, end city, start date, end date, the only value need is the "avalybel capacity" of the vessel, the others can be left blank. 

You can book a route by clicking on the route in the table, and then clicking the "book" button. 

You can only book a route if the avalybel capacity is greater or equal to the amount of gargo you want to book.

The way you add a new route is be writing it in the "bookshipment\src\main\resources\portfolio\projekt2\routes\routes1.csv" file in the same format as the other routes. 
Duplicate routes are wil automaticly be filtered out, and not added to the database. 



## projekt struckture:

```txt
📦bookshipment
 ┣ 📂src
 ┃ ┣ 📂main
 ┃ ┃ ┣ 📂java
 ┃ ┃ ┃ ┣ 📂portfolio
 ┃ ┃ ┃ ┃ ┗ 📂projekt2
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controllers
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Controller.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CityDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CityDateWithVidDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CRUDHelper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Database.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜DateDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜DateVesselWithCidDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜RouteDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SearchFunctions.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜VesselCityWithDidDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜VesselsDAO.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂models
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜City.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CityDateWithVid.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CsvReader.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜DataLoader.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Date.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜DateVesselWithCid.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Route.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Vessel.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜VesselCityWithDid.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜boatshipmentApp.java --------THE MAIN JAVA FILE!!
 ┃ ┃ ┃ ┗ 📜module-info.java
 ┃ ┃ ┗ 📂resources
 ┃ ┃ ┃ ┗ 📂portfolio
 ┃ ┃ ┃ ┃ ┗ 📂projekt2
 ┃ ┃ ┃ ┃ ┃ ┣ 📂database
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜boatshipmentDatabase.db
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜eksDatabase.db
 ┃ ┃ ┃ ┃ ┃ ┗ 📂routes
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜routes1.csv
 ┃ ┗ 📂test
 ┃ ┃ ┗ 📂java
 ┣ 📂target
 ┃ ┣ 📂classes
 ┃ ┃ ┣ 📂portfolio
 ┃ ┃ ┃ ┗ 📂projekt2
 ┃ ┃ ┃ ┃ ┣ 📂controllers
 ┃ ┃ ┃ ┃ ┃ ┗ 📜Controller.class
 ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CityDAO.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CityDateWithVidDAO.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CRUDHelper.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Database.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜DateDAO.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜DateVesselWithCidDAO.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜RouteDAO.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜SearchFunctions.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜VesselCityWithDidDAO.class
 ┃ ┃ ┃ ┃ ┃ ┗ 📜VesselsDAO.class
 ┃ ┃ ┃ ┃ ┣ 📂database
 ┃ ┃ ┃ ┃ ┃ ┣ 📜boatshipmentDatabase.db
 ┃ ┃ ┃ ┃ ┃ ┗ 📜eksDatabase.db
 ┃ ┃ ┃ ┃ ┣ 📂models
 ┃ ┃ ┃ ┃ ┃ ┣ 📜City.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CityDateWithVid.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CsvReader.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜DataLoader.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Date.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜DateVesselWithCid.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Route.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Vessel.class
 ┃ ┃ ┃ ┃ ┃ ┗ 📜VesselCityWithDid.class
 ┃ ┃ ┃ ┃ ┣ 📂routes
 ┃ ┃ ┃ ┃ ┃ ┗ 📜routes1.csv
 ┃ ┃ ┃ ┃ ┗ 📜boatshipmentApp.class
 ┃ ┃ ┗ 📜module-info.class
 ┃ ┣ 📂generated-sources
 ┃ ┃ ┗ 📂annotations
 ┃ ┣ 📂generated-test-sources
 ┃ ┃ ┗ 📂test-annotations
 ┃ ┣ 📂maven-status
 ┃ ┃ ┗ 📂maven-compiler-plugin
 ┃ ┃ ┃ ┣ 📂compile
 ┃ ┃ ┃ ┃ ┗ 📂default-compile
 ┃ ┃ ┃ ┃ ┃ ┣ 📜createdFiles.lst
 ┃ ┃ ┃ ┃ ┃ ┗ 📜inputFiles.lst
 ┃ ┃ ┃ ┗ 📂testCompile
 ┃ ┃ ┃ ┃ ┗ 📂default-testCompile
 ┃ ┃ ┃ ┃ ┃ ┗ 📜inputFiles.lst
 ┃ ┗ 📂test-classes
 ┗ 📜pom.xml
```
 ## Date base Tabels: 

database file is located in src/main/resources/portfolio/projekt2/database/boatshipmentDatabase.db
 

CREATE TABLE Routes (rid integer PRIMARY KEY, startDid int NOT NULL, endDid int, startCid int, endCid int, rVid int);

CREATE TABLE CityDateWithVids (cDid integer PRIMARY KEY, cityWithVid int NOT NULL, dateWithVid int, vid int);

CREATE TABLE DateVesselWithCids (dVid integer PRIMARY KEY, dateWithCid int NOT NULL, vesselWithCid int, cid int);

CREATE TABLE VesselCityWithDids (vCid integer PRIMARY KEY, cityWithDid int NOT NULL, vesselWithDid int, did int);

CREATE TABLE Vessels (vid integer PRIMARY KEY, vesselName varchar(255) NOT NULL, usedCapacity int, maxCapacity int, availableCapacity int,
 cityDateWithVidIndex varchar(255));

CREATE TABLE Dates (did integer PRIMARY KEY, dateV varchar(255) NOT NULL, cityVesselWithDidIndex varchar(255));

CREATE TABLE Citys (cid integer PRIMARY KEY, cityV varchar(255) NOT NULL, vesselDateWithCidIndex varchar(255));