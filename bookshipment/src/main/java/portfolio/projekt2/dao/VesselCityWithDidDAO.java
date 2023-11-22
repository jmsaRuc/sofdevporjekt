/*
 * As a lot of the DAOclasses are the same, but with diffrent values,
 * there wil only in detb comments in the first one:
 *  @CityDAO 
 * 
 * This is the DAO class for the VesselCityWithDid table in the database
 * 
 * The VesselCityWithDidDAO class is used to connect to the database and to get,
 * insert, update and delete VesselCityWithDid from the database.
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
import portfolio.projekt2.models.VesselCityWithDid;

public class VesselCityWithDidDAO {

  private static final String tableName = "VesselCityWithDids";
  private static final String cityWithDidColumn = "cityWithDid";
  private static final String vesselWithDidColumn = "vesselWithDid";
  private static final String didColumn = "did";
  private static final String vCidColumn = "vCid";

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
            rs.getInt(didColumn),
            rs.getInt(vCidColumn)
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
    int vesselWithDid,
    int did
  ) {
    int vCid = (int) CRUDHelper.create(
      tableName,
      new String[] { "cityWithDid", "vesselWithDid", "did" },
      new Object[] { cityWithDid, vesselWithDid, did },
      new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER}
    );
    //update cache
    vesselCityWithDids.add(
      new VesselCityWithDid(cityWithDid, vesselWithDid, did ,vCid)
    );
  }

  public static void udpate(VesselCityWithDid newVesselCityWithDid) {
    int rows = (int) CRUDHelper.update(
      tableName,
      new String[] { cityWithDidColumn, vesselWithDidColumn, didColumn },
      new Object[] {
        newVesselCityWithDid.getCityWithDid(),
        newVesselCityWithDid.getVesselWithDid(),
        newVesselCityWithDid.getDidIn(),
      },
      new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER },
      vCidColumn,
      Types.INTEGER,
      newVesselCityWithDid.getVCid()
    );

    if (rows == 0) throw new IllegalStateException(
      "Vesse to be updated with vid " +
      newVesselCityWithDid.getVCid() +
      " didn't exist in database"
    );

    //update cache
    Optional<VesselCityWithDid> optionalVesselCityWithDid = getVesselCityWithDid(
      newVesselCityWithDid.getVCid()
    );
    optionalVesselCityWithDid.ifPresentOrElse(
      oldVesselCityWithDid -> {
        vesselCityWithDids.remove(oldVesselCityWithDid);
        vesselCityWithDids.add(newVesselCityWithDid);
      },
      () -> {
        throw new IllegalStateException(
          "Vesse to be updated with vid " +
          newVesselCityWithDid.getVCid() +
          " didn't exist in database"
        );
      }
    );
  }

  public static Optional<VesselCityWithDid> getVesselCityWithDid(int vCid) {
    for (VesselCityWithDid VesselCityWithDid : vesselCityWithDids) {
      if (VesselCityWithDid.getVCid() == vCid) return Optional.of(
        VesselCityWithDid
      );
    }
    return Optional.empty();
  }

  public static void delete(int vCid) {
    CRUDHelper.delete(tableName, vCid, vCidColumn);

    Optional<VesselCityWithDid> VesselCityWithDid = getVesselCityWithDid(vCid);
    VesselCityWithDid.ifPresent(vesselCityWithDids::remove);
  }
}
