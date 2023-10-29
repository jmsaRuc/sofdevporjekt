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

  private static final String dVcidColumn = "dVcid";

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
            rs.getInt(dVcidColumn)
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
    int vesselWithCid
  ) {
    int dVcid = (int) CRUDHelper.create(
      tableName,
      new String[] { "dateWithCid", "vesselWithCid" },
      new Object[] { dateWithCid, vesselWithCid },
      new int[] { Types.INTEGER, Types.INTEGER }
    );
    //update cache
    dateVesselWithCids.add(
      new DateVesselWithCid(dateWithCid, vesselWithCid, dVcid)
    );
  }

  public static void udpate(DateVesselWithCid newDateVesselWithCid) {
    int rows = (int) CRUDHelper.update(
      tableName,
      new String[] { dateWithCidColumn, vesselWithCidColumn },
      new Object[] {
        newDateVesselWithCid.getDateWithCid(),
        newDateVesselWithCid.getVesselWithCid(),
      },
      new int[] { Types.INTEGER, Types.INTEGER },
      dVcidColumn,
      Types.INTEGER,
      newDateVesselWithCid.getdVcid()
    );

    if (rows == 0) throw new IllegalStateException(
      "Vesse to be updated with vid " +
      newDateVesselWithCid.getdVcid() +
      " didn't exist in database"
    );

    //update cache
    Optional<DateVesselWithCid> optionalDateVesselWithCid = getDateVesselWithCid(
      newDateVesselWithCid.getdVcid()
    );
    optionalDateVesselWithCid.ifPresentOrElse(
      oldDateVesselWithCid -> {
        dateVesselWithCids.remove(oldDateVesselWithCid);
        dateVesselWithCids.add(newDateVesselWithCid);
      },
      () -> {
        throw new IllegalStateException(
          "Vesse to be updated with vid " +
          newDateVesselWithCid.getdVcid() +
          " didn't exist in database"
        );
      }
    );
  }

  public static Optional<DateVesselWithCid> getDateVesselWithCid(int dVcid) {
    for (DateVesselWithCid DateVesselWithCid : dateVesselWithCids) {
      if (DateVesselWithCid.getdVcid() == dVcid) return Optional.of(
        DateVesselWithCid
      );
    }
    return Optional.empty();
  }

  public static void delete(int dVcid) {
    CRUDHelper.delete(tableName, dVcid, dVcidColumn);

    Optional<DateVesselWithCid> DateVesselWithCid = getDateVesselWithCid(dVcid);
    DateVesselWithCid.ifPresent(dateVesselWithCids::remove);
  }
}
