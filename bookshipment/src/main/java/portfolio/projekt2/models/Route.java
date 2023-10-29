//class for Rote, make it int same way as (City, Date, Vessel)

package portfolio.projekt2.models;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Route {

  private final ReadOnlyIntegerProperty startDid;
  private final ReadOnlyIntegerProperty endDid;
  private final ReadOnlyIntegerProperty startCid;
  private final ReadOnlyIntegerProperty endCid;
  private final ReadOnlyIntegerProperty rVid;
  private final int rid;

  public Route(
    Integer startDid,
    Integer endDid,
    Integer startCid,
    Integer endCid,
    Integer rVid,
    int rid
  ) {
    this.startDid = new SimpleIntegerProperty(startDid);
    this.endDid = new SimpleIntegerProperty(endDid);
    this.startCid = new SimpleIntegerProperty(startCid);
    this.endCid = new SimpleIntegerProperty(endCid);
    this.rVid = new SimpleIntegerProperty(rVid);
    this.rid = rid;
  }

  public int getRid() {
    return rid;
  }

  public int getStartDid() {
    return startDid.get();
  }

  public ReadOnlyIntegerProperty startDidProperty() {
    return startDid;
  }

  public int getEndDid() {
    return endDid.get();
  }

  public ReadOnlyIntegerProperty endDidProperty() {
    return endDid;
  }

  public int getStartCid() {
    return startCid.get();
  }

  public ReadOnlyIntegerProperty startCidProperty() {
    return startCid;
  }

  public int getEndCid() {
    return endCid.get();
  }

  public ReadOnlyIntegerProperty endCidProperty() {
    return endCid;
  }

  public int getRVid() {
    return rVid.get();
  }

  public ReadOnlyIntegerProperty rVidProperty() {
    return rVid;
  }

  @Override
  public String toString() {
    return (
      "Route [" +
      startDid.get() +
      ", endDid " +
      endDid.get() +
      ", startCid " +
      startCid.get() +
      ", endCid " +
      endCid.get() +
      ", rVid " +
      rVid.get() +
      ", rid " +
      rid +
      "]"
    );
  }
}
