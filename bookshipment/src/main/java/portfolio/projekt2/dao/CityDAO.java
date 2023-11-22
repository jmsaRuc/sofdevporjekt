/*
 * As a lot of the DAOclasses are the same, but with diffrent values,
 * there wil only in detb comments in the first one:
 *  @CityDAO 
 * 
 * This is the DAO class for the City table in the database
 * 
 * The CityDAO class is used to connect to the database and to get,
 *  insert, update and delete citys from the database
 * 
 * 
 */


package portfolio.projekt2.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import portfolio.projekt2.models.City;

public class CityDAO {

  private static final String tableName = "Citys"; //table name in database
  private static final String CityVColumn = "cityV";//the column name in database
  private static final String vesselDateWithCidIndexColumn =
    "vesselDateWithCidIndex";//the column name in database

  private static final String cidColumn = "cid";//the column name in database

  private static final ObservableList<City> citys;//the list of citys

   // Create an observable ArrayList of City objects
  // This is a static variable, so it will be shared
  // by all instances of the City class
  static {
    citys = FXCollections.observableArrayList();
    // Populate the observableArrayList with City objects
    // by reading each row from the City table in the database
    upCityCitysFromDB();
  }

 // Create a list of cities
public static ObservableList<City> getCitys() {
  // Return an unmodifiable version of the citys list.
  return FXCollections.unmodifiableObservableList(citys);
}

//todo: write comments for this method
  private static void upCityCitysFromDB() {//this method is used to upCity the citys from the database
    String query = "SELECT * FROM " + tableName;

    try (Connection connection = Database.connect()) {//connect to the database
      // Prepare the statement
      PreparedStatement statement = connection.prepareStatement(query);
      // Execute the query
      ResultSet rs = statement.executeQuery();
      // Clear the list
      citys.clear();
      // Loop over the results
      while (rs.next()) {
        citys.add(
          new City(
            rs.getString(CityVColumn),
            rs.getString(vesselDateWithCidIndexColumn),
            rs.getInt(cidColumn)
          )
        );
      }
    } catch (SQLException e) {
      // Log the exception
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() + ": Could not load citys from database upCityCitysFromDB"
        );

      citys.clear();
    }
  }
//todo: write comments for this method
  public static void insertCity(String cityV, String cityVesselWithDidIndex) {//this method is used to insert a city into the database
    
    //insert into database
    int cid = (int) CRUDHelper.create(
      tableName,
      new String[] { "cityV", "vesselDateWithCidIndex" },
      new Object[] { cityV, cityVesselWithDidIndex },
      new int[] { Types.VARCHAR, Types.VARCHAR }
    );
    //updateCity cache
    citys.add(new City(cityV, cityVesselWithDidIndex, cid));
  }

  public static void update(City newCity) {
    //udpate database
    int rows = (int) CRUDHelper.update(
      tableName,
      new String[] { CityVColumn, vesselDateWithCidIndexColumn },
      new Object[] { newCity.getCityV(), newCity.getVesselDateWithCidIndex() },
      new int[] {
        Types.VARCHAR,
        Types.VARCHAR,
      },
      cidColumn,
      Types.INTEGER,
      newCity.getCid()
    );
      //check if the citys to be upCityd existed in the database
    if (rows == 0) throw new IllegalStateException(
      "citys to be upCityd with cid " +
      newCity.getCid() +
      " didn't exist in database"
    );

    //updateCity cache
    Optional<City> optionalCity = getCity(newCity.getCid());
    optionalCity.ifPresentOrElse(
      oldCity -> {
        citys.remove(oldCity);
        citys.add(newCity);
      },
      () -> {
        throw new IllegalStateException(
          "City to be upCityd with cid " +
          newCity.getCid() +
          " didn't exist in database"
        );
      }
    );
  }

  public static void delete(int cid) {//this method is used to delete a city from the database
    //delete from database
    CRUDHelper.delete(tableName, cid, cidColumn);

    //updateCity cache
    Optional<City> city = getCity(cid);
    city.ifPresent(citys::remove);
  }

  public static Optional<City> getCity(int cid) {//this method is used to get a city from the database
    for (City city : citys) {
      if (city.getCid() == cid) return Optional.of(city);
    }
    return Optional.empty();
  }
}