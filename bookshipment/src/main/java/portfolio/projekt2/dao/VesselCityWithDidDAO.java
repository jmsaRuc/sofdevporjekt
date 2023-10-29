package portfolio.projekt2.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import portfolio.projekt2.models.VesselCityWithDid;

public class VesselCityWithDidDAO {

  private static final String tableName = "VesselCityWithDids";
  private static final String cityWithDidColumn = "cityWithDid";
  private static final String vesselWithDidColumn = "vesselWithDid";

  private static final String vCdidColumn = "vCdid";

  private static final ObservableList<VesselCityWithDid> vesselCityWithDids;

  static {
    vesselCityWithDids = FXCollections.observableArrayList();
    updateVesselCityWithDidsFromDB();
  }

  public static ObservableList<VesselCityWithDid> getVesselCityWithDids() {
    return FXCollections.unmodifiableObservableList(vesselCityWithDids);
  }

  private static void updateVesselCityWithDidsFromDB() {
    String query = "SELECT * FROM " + tableName;

    try (Connection connection = Database.connect()) {
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      vesselCityWithDids.clear();
      while (rs.next()) {
        vesselCityWithDids.add(
          new VesselCityWithDid(
            rs.getInt(cityWithDidColumn),
            rs.getInt(vesselWithDidColumn),
            rs.getInt(vCdidColumn)
          )
        );
      }
    } catch (SQLException e) {
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() +
          ": Could not load vesselCityWithDids from database "
        );
    }
  }

  public static void instertVesselCityWithDid(
    int cityWithDid,
    int vesselWithDid
  ) {
    int vCdid = (int) CRUDHelper.create(
      tableName,
      new String[] { "cityWithDid", "vesselWithDid" },
      new Object[] { cityWithDid, vesselWithDid },
      new int[] { Types.INTEGER, Types.INTEGER }
    );
    //update cache
    vesselCityWithDids.add(
      new VesselCityWithDid(cityWithDid, vesselWithDid, vCdid)
    );
  }

  public static void udpate(VesselCityWithDid newVesselCityWithDid) {
    int rows = (int) CRUDHelper.update(
      tableName,
      new String[] { cityWithDidColumn, vesselWithDidColumn },
      new Object[] {
        newVesselCityWithDid.getCityWithDid(),
        newVesselCityWithDid.getVesselWithDid(),
      },
      new int[] { Types.INTEGER, Types.INTEGER },
      vCdidColumn,
      Types.INTEGER,
      newVesselCityWithDid.getVCdid()
    );

    if (rows == 0) throw new IllegalStateException(
      "Vesse to be updated with vid " +
      newVesselCityWithDid.getVCdid() +
      " didn't exist in database"
    );

    //update cache
    Optional<VesselCityWithDid> optionalVesselCityWithDid = getVesselCityWithDid(
      newVesselCityWithDid.getVCdid()
    );
    optionalVesselCityWithDid.ifPresentOrElse(
      oldVesselCityWithDid -> {
        vesselCityWithDids.remove(oldVesselCityWithDid);
        vesselCityWithDids.add(newVesselCityWithDid);
      },
      () -> {
        throw new IllegalStateException(
          "Vesse to be updated with vid " +
          newVesselCityWithDid.getVCdid() +
          " didn't exist in database"
        );
      }
    );
  }

  public static Optional<VesselCityWithDid> getVesselCityWithDid(int vCdid) {
    for (VesselCityWithDid VesselCityWithDid : vesselCityWithDids) {
      if (VesselCityWithDid.getVCdid() == vCdid) return Optional.of(
        VesselCityWithDid
      );
    }
    return Optional.empty();
  }

  public static void delete(int vCdid) {
    CRUDHelper.delete(tableName, vCdid, vCdidColumn);

    Optional<VesselCityWithDid> VesselCityWithDid = getVesselCityWithDid(vCdid);
    VesselCityWithDid.ifPresent(vesselCityWithDids::remove);
  }
}
