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

  private static final String tableName = "Citys";
  private static final String CityVColumn = "cityV";
  private static final String vesselDateWithCidIndexColumn =
    "vesselDateWithCidIndex";

  private static final String cidColumn = "cid";

  private static final ObservableList<City> citys;

  static {
    citys = FXCollections.observableArrayList();
    upCityCitysFromDB();
  }

  public static ObservableList<City> getCitys() {
    return FXCollections.unmodifiableObservableList(citys);
  }

  private static void upCityCitysFromDB() {
    String query = "SELECT * FROM " + tableName;

    try (Connection connection = Database.connect()) {
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      citys.clear();
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
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() + ": Could not load citys from database upCityCitysFromDB"
        );

      citys.clear();
    }
  }

  public static void insertCity(String cityV, String cityVesselWithDidIndex) {
    //upCity database
    int cid = (int) CRUDHelper.create(
      tableName,
      new String[] { "cityV", "vesselDateWithCidIndex" },
      new Object[] { cityV, cityVesselWithDidIndex },
      new int[] { Types.VARCHAR, Types.VARCHAR }
    );
    //upCity cache
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

    if (rows == 0) throw new IllegalStateException(
      "citys to be upCityd with cid " +
      newCity.getCid() +
      " didn't exist in database"
    );

    //upCity cache
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

  public static void delete(int cid) {
    //upCity database
    CRUDHelper.delete(tableName, cid, cidColumn);

    //upCity cache
    Optional<City> city = getCity(cid);
    city.ifPresent(citys::remove);
  }

  public static Optional<City> getCity(int cid) {
    for (City city : citys) {
      if (city.getCid() == cid) return Optional.of(city);
    }
    return Optional.empty();
  }
}