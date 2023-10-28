# bookshipment

## Description
This is a simple CRUD application that allows you to add, edit, delete and view vessels in a database. The application is written in Java and uses JavaFX for the GUI. The database is SQLite.

## Installation
To run the application, you need to have Java 20. You can download it from [here](https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html). You also need to have Maven 3.8.1 installed. You can download it from [here](https://maven.apache.org/download.cgi).




📦bookshipment
 ┣ 📂src
 ┃ ┣ 📂main
 ┃ ┃ ┣ 📂java
 ┃ ┃ ┃ ┣ 📂portfolio
 ┃ ┃ ┃ ┃ ┗ 📂projekt2
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controllers
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Controller.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CRUDHelper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Database.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜VesselsDAO.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂models
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Vessel.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜boatshipmentApp.java
 ┃ ┃ ┃ ┗ 📜module-info.java
 ┃ ┃ ┗ 📂resources
 ┃ ┃ ┃ ┗ 📂portfolio
 ┃ ┃ ┃ ┃ ┗ 📂projekt2
 ┃ ┃ ┃ ┃ ┃ ┗ 📂database
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜boatshipmentDatabase.db
 ┃ ┗ 📂test
 ┃ ┃ ┗ 📂java
 ┣ 📂target
 ┃ ┣ 📂classes
 ┃ ┃ ┣ 📂portfolio
 ┃ ┃ ┃ ┗ 📂projekt2
 ┃ ┃ ┃ ┃ ┣ 📂controllers
 ┃ ┃ ┃ ┃ ┃ ┗ 📜Controller.class
 ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CRUDHelper.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Database.class
 ┃ ┃ ┃ ┃ ┃ ┗ 📜VesselsDAO.class
 ┃ ┃ ┃ ┃ ┣ 📂database
 ┃ ┃ ┃ ┃ ┃ ┗ 📜boatshipmentDatabase.db
 ┃ ┃ ┃ ┃ ┣ 📂models
 ┃ ┃ ┃ ┃ ┃ ┗ 📜Vessel.class
 ┃ ┃ ┃ ┃ ┗ 📜boatshipmentApp.class
 ┃ ┃ ┗ 📜module-info.class
 ┃ ┗ 📂test-classes
 ┗ 📜pom.xml