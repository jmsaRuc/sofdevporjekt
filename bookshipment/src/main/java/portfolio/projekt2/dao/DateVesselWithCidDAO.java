/*
 * As a lot of the DAOclasses are the same, but with diffrent values,
 * there wil only in detb comments in the first one:
 *  @CityDAO 
 * 
 * This is the DAO class for the DateVesselWithCid table in the database
 * 
 * The DateVesselWithCidDAO class is used to connect to the database and to get,
 * insert, update and delete DateVesselWithCid from the database.
 * 
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
import portfolio.projekt2.models.DateVesselWithCid;

public class DateVesselWithCidDAO {

  private static final String tableName = "DateVesselWithCids";
  private static final String dateWithCidColumn = "dateWithCid";
  private static final String vesselWithCidColumn = "vesselWithCid";
  private static final String cidColumn = "cid";

  private static final String dVidColumn = "dVid";

  private static final ObservableList<DateVesselWithCid> dateVesselWithCids;

  static {
    dateVesselWithCids = FXCollections.observableArrayList();
    updateDateVesselWithCidsFromDB();
  }

  public static ObservableList<DateVesselWithCid> getDateVesselWithCids() {
    return FXCollections.unmodifiableObservableList(dateVesselWithCids);
  }

  private static void updateDateVesselWithCidsFromDB() {
    String query = "SELECT * FROM " + tableName;

    try (Connection connection = Database.connect()) {
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      dateVesselWithCids.clear();
      while (rs.next()) {
        dateVesselWithCids.add(
          new DateVesselWithCid(
            rs.getInt(dateWithCidColumn),
            rs.getInt(vesselWithCidColumn),
            rs.getInt(cidColumn),
            rs.getInt(dVidColumn)
          )
        );
      }
    } catch (SQLException e) {
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() +
          ": Could not load dateVesselWithCids from database "
        );
    }
  }

  public static void instertDateVesselWithCid(
    int dateWithCid,
    int vesselWithCid,
    int cid
  ) {
    int dVid = (int) CRUDHelper.create(
      tableName,
      new String[] { "dateWithCid", "vesselWithCid", "cid" },
      new Object[] { dateWithCid, vesselWithCid, cid },
      new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER }
    );
    //update cache
    dateVesselWithCids.add(
      new DateVesselWithCid(dateWithCid, vesselWithCid, cid ,dVid)
    );
  }

  public static void udpate(DateVesselWithCid newDateVesselWithCid) {
    int rows = (int) CRUDHelper.update(
      tableName,
      new String[] { dateWithCidColumn, vesselWithCidColumn, cidColumn },
      new Object[] {
        newDateVesselWithCid.getDateWithCid(),
        newDateVesselWithCid.getVesselWithCid(),
        newDateVesselWithCid.getCidIn()
      },
      new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER },
      dVidColumn,
      Types.INTEGER,
      newDateVesselWithCid.getdVid()
    );

    if (rows == 0) throw new IllegalStateException(
      "Vesse to be updated with vid " +
      newDateVesselWithCid.getdVid() +
      " didn't exist in database"
    );

    //update cache
    Optional<DateVesselWithCid> optionalDateVesselWithCid = getDateVesselWithCid(
      newDateVesselWithCid.getdVid()
    );
    optionalDateVesselWithCid.ifPresentOrElse(
      oldDateVesselWithCid -> {
        dateVesselWithCids.remove(oldDateVesselWithCid);
        dateVesselWithCids.add(newDateVesselWithCid);
      },
      () -> {
        throw new IllegalStateException(
          "Vesse to be updated with vid " +
          newDateVesselWithCid.getdVid() +
          " didn't exist in database"
        );
      }
    );
  }

  public static Optional<DateVesselWithCid> getDateVesselWithCid(int dVid) {
    for (DateVesselWithCid DateVesselWithCid : dateVesselWithCids) {
      if (DateVesselWithCid.getdVid() == dVid) return Optional.of(
        DateVesselWithCid
      );
    }
    return Optional.empty();
  }

  public static void delete(int dVid) {
    CRUDHelper.delete(tableName, dVid, dVidColumn);

    Optional<DateVesselWithCid> DateVesselWithCid = getDateVesselWithCid(dVid);
    DateVesselWithCid.ifPresent(dateVesselWithCids::remove);
  }
}
