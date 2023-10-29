package portfolio.projekt2.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import portfolio.projekt2.models.CityDateWithVid;

public class CityDateWithVidDAO {

  private static final String tableName = "CityDateWithVids";
  private static final String cityWithVidColumn = "cityWithVid";
  private static final String dateWithVidColumn = "dateWithVid";

  private static final String cDvidColumn = "cDvid";

  private static final ObservableList<CityDateWithVid> cityDateWithVids;

  static {
    cityDateWithVids = FXCollections.observableArrayList();
    updatecityDateWithVidsFromDB();
  }

  public static ObservableList<CityDateWithVid> getCityDateWithVids() {
    return FXCollections.unmodifiableObservableList(cityDateWithVids);
  }

  private static void updatecityDateWithVidsFromDB() {
    String query = "SELECT * FROM " + tableName;

    try (Connection connection = Database.connect()) {
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      cityDateWithVids.clear();
      while (rs.next()) {
        cityDateWithVids.add(
          new CityDateWithVid(
            rs.getInt(cityWithVidColumn),
            rs.getInt(dateWithVidColumn),
            rs.getInt(cDvidColumn)
          )
        );
      }
    } catch (SQLException e) {
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() +
          ": Could not load cityDateWithVids from database "
        );
    }
  }

  public static void instertCityDateWithVid(int cityWithVid, int dateWithVid) {
    int cDvid = (int) CRUDHelper.create(
      tableName,
      new String[] { "cityWithVid", "dateWithVid" },
      new Object[] { cityWithVid, dateWithVid },
      new int[] { Types.INTEGER, Types.INTEGER }
    );
    //update cache
    cityDateWithVids.add(new CityDateWithVid(cityWithVid, dateWithVid, cDvid));
  }

  public static void udpate(CityDateWithVid newCityDateWithVid) {
    int rows = (int) CRUDHelper.update(
      tableName,
      new String[] { cityWithVidColumn, dateWithVidColumn },
      new Object[] {
        newCityDateWithVid.getCityWithVid(),
        newCityDateWithVid.getDateWithVid(),
      },
      new int[] { Types.INTEGER, Types.INTEGER },
      cDvidColumn,
      Types.INTEGER,
      newCityDateWithVid.getCDvid()
    );

    if (rows == 0) throw new IllegalStateException(
      "cityDateWithVidDAO to be updated with vid " +
      newCityDateWithVid.getCDvid() +
      " didn't exist in database"
    );

    //update cache
    Optional<CityDateWithVid> optionalcityDateWithVid = getCityDateWithVid(
      newCityDateWithVid.getCDvid()
    );
    optionalcityDateWithVid.ifPresentOrElse(
      oldcityDateWithVid -> {
        cityDateWithVids.remove(oldcityDateWithVid);
        cityDateWithVids.add(newCityDateWithVid);
      },
      () -> {
        throw new IllegalStateException(
          "cityDateWithVidDAO to be updated with vid " +
          newCityDateWithVid.getCDvid() +
          " didn't exist in database"
        );
      }
    );
  }

  public static Optional<CityDateWithVid> getCityDateWithVid(int cDvid) {
    for (CityDateWithVid cityDateWithVid : cityDateWithVids) {
      if (cityDateWithVid.getCDvid() == cDvid) return Optional.of(
        cityDateWithVid
      );
    }
    return Optional.empty();
  }

  public static void delete(int cDvid) {
    CRUDHelper.delete(tableName, cDvid, cDvidColumn);

    Optional<CityDateWithVid> cityDateWithVid = getCityDateWithVid(cDvid);
    cityDateWithVid.ifPresent(cityDateWithVids::remove);
  }
}
