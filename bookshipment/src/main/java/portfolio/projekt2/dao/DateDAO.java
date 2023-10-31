package portfolio.projekt2.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import portfolio.projekt2.models.Date;

public class DateDAO {

  private static final String tableName = "Dates";
  private static final String dateVColumn = "dateV";
  private static final String cityVesselWithDidIndexColumn =
    "cityVesselWithDidIndex";

  private static final String didColumn = "did";

  private static final ObservableList<Date> dates;

  static {
    dates = FXCollections.observableArrayList();
    updateDatesFromDB();
  }

  public static ObservableList<Date> getDates() {
    return FXCollections.unmodifiableObservableList(dates);
  }

  private static void updateDatesFromDB() {
    String query = "SELECT * FROM " + tableName;

    try (Connection connection = Database.connect()) {
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      dates.clear();
      while (rs.next()) {
        dates.add(
          new Date(
            rs.getString(dateVColumn),
            rs.getString(cityVesselWithDidIndexColumn),
            rs.getInt(didColumn)
          )
        );
      }
    } catch (SQLException e) {
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() + ": Could not load dates from database "
        );

      dates.clear();
    }
  }

  public static void insertDate(String dateV,String cityVesselWithDidIndex) {
    //update database
    int did = (int) CRUDHelper.create(
      tableName,
      new String[] { "dateV", "cityVesselWithDidIndex" },
      new Object[] { dateV, cityVesselWithDidIndex },
      new int[] { Types.VARCHAR, Types.VARCHAR }
    );
    //update cache
    dates.add(new Date(dateV, cityVesselWithDidIndex, did));
  }

  public static void update(Date newDate) {
    //udpate database
    int rows = (int) CRUDHelper.update(
      tableName,
      new String[] { dateVColumn, cityVesselWithDidIndexColumn },
      new Object[] { newDate.getDateV(), newDate.getCityVesselWithDidIndex() },
      new int[] {
        Types.VARCHAR,
        Types.VARCHAR,
      },
      didColumn,
      Types.INTEGER,
      newDate.getDid()
    );

    if (rows == 0) throw new IllegalStateException(
      "dates to be updated with did " +
      newDate.getDid() +
      " didn't exist in database"
    );

    //update cache
    Optional<Date> optionaldate = getDate(newDate.getDid());
    optionaldate.ifPresentOrElse(
      oldDate -> {
        dates.remove(oldDate);
        dates.add(newDate);
      },
      () -> {
        throw new IllegalStateException(
          "date to be updated with did " +
          newDate.getDid() +
          " didn't exist in database"
        );
      }
    );
  }

  public static void delete(int did) {
    //update database
    CRUDHelper.delete(tableName, did, didColumn);

    //update cache
    Optional<Date> date = getDate(did);
    date.ifPresent(dates::remove);
  }

  public static Optional<Date> getDate(int did) {
    for (Date date : dates) {
      if (date.getDid() == did) return Optional.of(date);
    }
    return Optional.empty();
  }
}
