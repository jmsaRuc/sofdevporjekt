package portfolio.projekt2.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import portfolio.projekt2.models.Vessel;

public class VesselsDAO {

  private static final String tableName = "Vessels";
  private static final String vesselNameColumn = "vesselName";
  private static final String usedCapacityColumn = "usedCapacity";
  private static final String maxCapacityColumn = "maxCapacity";
  private static final String availableCapacityColumn = "availableCapacity";
  private static final String cityDateWithVidColum = "cityDateWithVid";

  private static final String vidColumn = "vid";

  private static final ObservableList<Vessel> vessels;

  static {
    vessels = FXCollections.observableArrayList();
    updateVesselsFromDB();
  }

  public static ObservableList<Vessel> getVessels() {
    return FXCollections.unmodifiableObservableList(vessels);
  }

  private static void updateVesselsFromDB() {
    String query = "SELECT * FROM " + tableName;

    try (Connection connection = Database.connect()) {
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      vessels.clear();
      while (rs.next()) {
        vessels.add(
          new Vessel(
            rs.getString(vesselNameColumn),
            rs.getInt(usedCapacityColumn),
            rs.getInt(maxCapacityColumn),
            rs.getInt(availableCapacityColumn),
            rs.getInt(cityDateWithVidColum),
            rs.getInt(vidColumn)
          )
        );
      }
    } catch (SQLException e) {
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() + ": Could not load Vessels from database "
        );
      vessels.clear();
    }
  }

  public static void insertVessel(
    String vesselName,
    int usedCapacity,
    int maxCapacity,
    int availableCapacity,
    int cityDateWithVid
  ) {
    //update database
    int vid = (int) CRUDHelper.create(
      tableName,
      new String[] {
        "VesselName, UsedCapacity, maxCapacity, AvailableCapacity, CityDateWithVid",
      },
      new Object[] {
        vesselName,
        usedCapacity,
        maxCapacity,
        availableCapacity,
        cityDateWithVid,
      },
      new int[] {
        Types.VARCHAR,
        Types.INTEGER,
        Types.INTEGER,
        Types.INTEGER,
        Types.INTEGER,
      }
    );
    //update cache
    vessels.add(
      new Vessel(
        vesselName,
        maxCapacity,
        usedCapacity,
        availableCapacity,
        cityDateWithVid,
        vid
      )
    );
  }

  public static void update(Vessel newVessel) {
    //udpate database
    int rows = (int) CRUDHelper.update(
      tableName,
      new String[] {
        vesselNameColumn,
        usedCapacityColumn,
        maxCapacityColumn,
        availableCapacityColumn,
        cityDateWithVidColum,
      },
      new Object[] {
        newVessel.getVesselName(),
        newVessel.getUsedCapacity(),
        newVessel.getMaxCapacity(),
        newVessel.getAvailableCapacity(),
        newVessel.getCityDateWithVid(),
      },
      new int[] {
        Types.VARCHAR,
        Types.INTEGER,
        Types.INTEGER,
        Types.INTEGER,
        Types.INTEGER,
      },
      vidColumn,
      Types.INTEGER,
      newVessel.getVid()
    );

    if (rows == 0) throw new IllegalStateException(
      "Person to be updated with vid " +
      newVessel.getVid() +
      " didn't exist in database"
    );

    //update cache
    Optional<Vessel> optionalVessel = getVessel(newVessel.getVid());
    optionalVessel.ifPresentOrElse(
      oldVessel -> {
        vessels.remove(oldVessel);
        vessels.add(newVessel);
      },
      () -> {
        throw new IllegalStateException(
          "Person to be updated with vid " +
          newVessel.getVid() +
          " didn't exist in database"
        );
      }
    );
  }

  public static int delete(String tableName, int vid) {
    String sql = "DELETE FROM " + tableName + " WHERE vid = ?";
    try (Connection conn = Database.connect()) {
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, vid);
      return pstmt.executeUpdate();
    } catch (SQLException e) {
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() +
          ": Could not delete from " +
          tableName +
          " by vid " +
          vid +
          " because " +
          e.getCause()
        );
      return -1;
    }
  }
  

  public static Optional<Vessel> getVessel(int vid) {
    for (Vessel vessel : vessels) {
      if (vessel.getVid() == vid) return Optional.of(vessel);
    }
    return Optional.empty();
  }
}
