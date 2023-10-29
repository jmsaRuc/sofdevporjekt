# bookshipment

## Description
This is a simple CRUD application that allows you to add, edit, delete and view vessels in a database. The application is written in Java and uses JavaFX for the GUI. The database is SQLite.

## Installation
To run the application, you need to have Java 20. You can download it from [here](https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html). You also need to have Maven 3.8.1 installed. You can download it from [here](https://maven.apache.org/download.cgi).



database file is located in src/main/resources/portfolio/projekt2/database/boatshipmentDatabase.db
Date base Tabels: 

Persons (id integer PRIMARY KEY, LastName varchar(255) NOT NULL, FirstName varchar(255), Age int);

Vessels (vid integer PRIMARY KEY, vesselName varchar(255) NOT NULL, usedCapacity int, maxCapacity int, availableCapacity int, cityDateWithVidIndex int);


Dates (did integer PRIMARY KEY, dateV varchar(255) NOT NULL, cityVesselWithDidIndex int);


Citys (cid integer PRIMARY KEY, cityV varchar(255) NOT NULL, vesselDateWithCidIndex int);

Routes (rid integer PRIMARY KEY, startDid int NOT NULL, endDid int, startCid int, endCid int, rVid int);     

CityDateWithVids (cDvid integer PRIMARY KEY, cityWithVid int NOT NULL, dateWithVid int);

VesselCityWithDids (vCdid integer PRIMARY KEY, cityWithDid int NOT NULL, vesselWithDid int);

DateVesselWithCids (dVcod integer PRIMARY KEY, dateWithCid int NOT NULL, vesselWithCid int);


## projekt struckture:

📦bookshipment
 ┣ 📂src
 ┃ ┣ 📂main
 ┃ ┃ ┣ 📂java
 ┃ ┃ ┃ ┣ 📂portfolio
 ┃ ┃ ┃ ┃ ┗ 📂projekt2
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controllers
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CityDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CityDateWithVidDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CRUDHelper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Database.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜DateDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜DateVesselWithCidDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜RouteDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜VesselCityWithDidDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜VesselsDAO.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂models
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜City.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CityDateWithVid.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Date.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜DateVesselWithCid.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Route.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Vessel.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜VesselCityWithDid.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜boatshipmentApp.java  --------------------main class
 ┃ ┃ ┃ ┗ 📜module-info.java
 ┃ ┃ ┗ 📂resources
 ┃ ┃ ┃ ┗ 📂portfolio
 ┃ ┃ ┃ ┃ ┗ 📂projekt2
 ┃ ┃ ┃ ┃ ┃ ┗ 📂database
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜boatshipmentDatabase.db
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜eksDatabase.db
 ┃ ┗ 📂test
 ┃ ┃ ┗ 📂java
 ┣ 📂target
 ┃ ┣ 📂classes
 ┃ ┃ ┣ 📂portfolio
 ┃ ┃ ┃ ┗ 📂projekt2
 ┃ ┃ ┃ ┃ ┣ 📂controllers
 ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CityDAO.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CityDateWithVidDAO.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CRUDHelper.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Database.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜DateDAO.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜DateVesselWithCidDAO.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜RouteDAO.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜VesselCityWithDidDAO.class
 ┃ ┃ ┃ ┃ ┃ ┗ 📜VesselsDAO.class
 ┃ ┃ ┃ ┃ ┣ 📂database
 ┃ ┃ ┃ ┃ ┃ ┣ 📜boatshipmentDatabase.db
 ┃ ┃ ┃ ┃ ┃ ┗ 📜eksDatabase.db
 ┃ ┃ ┃ ┃ ┣ 📂models
 ┃ ┃ ┃ ┃ ┃ ┣ 📜City.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CityDateWithVid.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Date.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜DateVesselWithCid.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Route.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Vessel.class
 ┃ ┃ ┃ ┃ ┃ ┗ 📜VesselCityWithDid.class
 ┃ ┃ ┃ ┃ ┗ 📜boatshipmentApp.class
 ┃ ┃ ┗ 📜module-info.class
 ┃ ┣ 📂generated-sources
 ┃ ┃ ┗ 📂annotations
 ┃ ┣ 📂generated-test-sources
 ┃ ┃ ┗ 📂test-annotations
 ┃ ┣ 📂maven-archiver
 ┃ ┃ ┗ 📜pom.properties
 ┃ ┣ 📂maven-status
 ┃ ┃ ┗ 📂maven-compiler-plugin
 ┃ ┃ ┃ ┣ 📂compile
 ┃ ┃ ┃ ┃ ┗ 📂default-compile
 ┃ ┃ ┃ ┃ ┃ ┣ 📜createdFiles.lst
 ┃ ┃ ┃ ┃ ┃ ┗ 📜inputFiles.lst
 ┃ ┃ ┃ ┗ 📂testCompile
 ┃ ┃ ┃ ┃ ┗ 📂default-testCompile
 ┃ ┃ ┃ ┃ ┃ ┣ 📜createdFiles.lst
 ┃ ┃ ┃ ┃ ┃ ┗ 📜inputFiles.lst
 ┃ ┣ 📂test-classes
 ┃ ┗ 📜bookshipment-1.0-SNAPSHOT.jar
 ┗ 📜pom.xml