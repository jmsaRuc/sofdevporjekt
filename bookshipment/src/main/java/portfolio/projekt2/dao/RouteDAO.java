//DAO for Route, data schema "TABLE Routes (rid integer PRIMARY KEY, startDid int NOT NULL, endDid int, startCid int, endCid int, rVid)int;", make it int same way as other DAOs (CityDAO, DateDAO, RouteDAO)

package portfolio.projekt2.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import portfolio.projekt2.models.Route;

public class RouteDAO {

  private static final String tableName = "Routes";
  private static final String startDidColumn = "startDid";
  private static final String endDidColumn = "endDid";
  private static final String startCidColumn = "startCid";
  private static final String endCidColumn = "endCid";
  private static final String rVidColumn = "rVid";

  private static final String ridColumn = "rid";

  private static final ObservableList<Route> routes;

  static {
    routes = FXCollections.observableArrayList();
    updateRoutesFromDB();
  }

  public static ObservableList<Route> getRoutes() {
    return FXCollections.unmodifiableObservableList(routes);
  }

  private static void updateRoutesFromDB() {
    String query = "SELECT * FROM " + tableName;

    try (Connection connection = Database.connect()) {
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      routes.clear();
      while (rs.next()) {
        routes.add(
          new Route(
            rs.getInt(startDidColumn),
            rs.getInt(endDidColumn),
            rs.getInt(startCidColumn),
            rs.getInt(endCidColumn),
            rs.getInt(rVidColumn),
            rs.getInt(ridColumn)
          )
        );
      }
    } catch (SQLException e) {
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() + ": Could not load routes from database "
        );

      routes.clear();
    }
  }

  public static void insertRoute(
    int startDid,
    int endDid,
    int startCid,
    int endCid,
    int rVid
  ) {
    //update database
    int rid = (int) CRUDHelper.create(
      tableName,
      new String[] { "startDid", "endDid", "startCid", "endCid", "rVid" },
      new Object[] { startDid, endDid, startCid, endCid, rVid },
      new int[] {
        Types.INTEGER,
        Types.INTEGER,
        Types.INTEGER,
        Types.INTEGER,
        Types.INTEGER,
      }
    );
    //update cache
    routes.add(new Route(startDid, endDid, startCid, endCid, rVid, rid));
  }

  public static void update(Route newRoute) {
    int rows = (int) CRUDHelper.update(
      tableName,
      new String[] {
        startDidColumn,
        endDidColumn,
        startCidColumn,
        endCidColumn,
        rVidColumn,
      },
      new Object[] {
        newRoute.getStartDid(),
        newRoute.getEndDid(),
        newRoute.getStartCid(),
        newRoute.getEndCid(),
        newRoute.getRVid(),
      },
      new int[] {
        Types.INTEGER,
        Types.INTEGER,
        Types.INTEGER,
        Types.INTEGER,
        Types.INTEGER,
      },
      ridColumn,
      Types.INTEGER,
      newRoute.getRid()
    );

    if (rows == 0) throw new IllegalStateException(
      "Rout to be updated with rid " +
      newRoute.getRid() +
      " didn't exist in database"
    );

    //update cache
    Optional<Route> optionalnewRoute = getRoute(newRoute.getRid());
    optionalnewRoute.ifPresentOrElse(
      oldnewRoute -> {
        routes.remove(oldnewRoute);
        routes.add(newRoute);
      },
      () -> {
        throw new IllegalStateException(
          "newRoute to be updated with vid " +
          newRoute.getRid() +
          " didn't exist in database"
        );
      }
    );
  }

  public static Optional<Route> getRoute(int rid) {
    for (Route newRoute : routes) {
      if (newRoute.getRid() == rid) return Optional.of(newRoute);
    }
    return Optional.empty();
  }

  public static void delete(int rid) {
    //update database
    CRUDHelper.delete(tableName, rid, ridColumn);

    //update cache
    Optional<Route> newRoute = getRoute(rid);
    newRoute.ifPresent(routes::remove);
  }
}
