/*
 * This is the class for connecting to the Data base,
 * and checking if the tables exist.
 * 
 * Is also based on code from the articel: https://edencoding.com/connect-javafx-with-sqlite/
 * 
 * 
 */

package portfolio.projekt2.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import portfolio.projekt2.boatshipmentApp;

public class Database {

  // The location of the database file
  private static final String location =
    boatshipmentApp.class.getResource("database/boatshipmentDatabase.db")
      .toExternalForm();

  private static final String requiredTable[] = new String[] {//the tables that are required to be in the database
    "Vessels",
    "Dates",
    "Citys",
    "Routes",
    "CityDateWithVids",
    "VesselCityWithDids",
    "DateVesselWithCids",
  };

  public static boolean isOK() {//this method is used to check if the database is ok
    if (!checkDrivers()) {
      return false;
    } //driver errors

    if (!checkConnection()) {
      return false;
    }
    //can't connect to db

    return checkTables(); //tables didn't exist
  }

  private static boolean checkDrivers() {//this method is used to check if the drivers are ok
    try {
      Class.forName("org.sqlite.JDBC");
      DriverManager.registerDriver(new org.sqlite.JDBC());
      return true;
    } catch (ClassNotFoundException | SQLException classNotFoundException) {
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() + ": Could not start SQLite Drivers"
        );
      return false;
    }
  }

  private static boolean checkConnection() {//this method is used to check if the connection to the database is ok
    try (Connection connection = connect()) {
      return connection != null;
    } catch (SQLException e) {
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() + ": Could not connect to database"
        );
      return false;
    }
  }

  private static boolean checkTables() {//this method is used to check if the tables are ok
    int i;
    int n = 0;
    for (i = 0; i < requiredTable.length; i++) {
      String checkTables =
        "select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
        requiredTable[i] +
        "'";

      try (Connection connection = Database.connect()) {
        PreparedStatement statement = connection.prepareStatement(checkTables);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
          if (rs.getString("tbl_name").equals(requiredTable[i]))n++;
        }
      } catch (SQLException exception) {
        Logger
          .getAnonymousLogger()
          .log(
            Level.SEVERE,
            LocalDateTime.now() + ": Could not find tables in database"
          );
        return false;
      }
      if(n==requiredTable.length){return true;}
    }
    return false;
  }

  protected static Connection connect() {//this method is used to connect to the database
    String dbPrefix = "jdbc:sqlite:";
    Connection connection;
    try {
      connection = DriverManager.getConnection(dbPrefix + location);
    } catch (SQLException exception) {
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() +
          ": Could not connect to SQLite DB at " +
          location
        );
      return null;
    }
    return connection;
  }
}
