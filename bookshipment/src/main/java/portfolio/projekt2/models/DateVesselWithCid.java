package portfolio.projekt2.models;

import java.util.Optional;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DateVesselWithCid {

  private final ReadOnlyIntegerProperty dateWithCid;
  private final ReadOnlyIntegerProperty vesselWithCid;
  private final int dVcid;

  public DateVesselWithCid(
    Integer dateWithCid,
    Integer vesselWithCid,
    int dVcid
  ) {
    this.dateWithCid = new SimpleIntegerProperty(dateWithCid);
    this.vesselWithCid = new SimpleIntegerProperty(vesselWithCid);
    this.dVcid = dVcid;
  }

  public int getdVcid() {
    return dVcid;
  }

  public int getDateWithCid() {
    return dateWithCid.get();
  }

  public ReadOnlyIntegerProperty dateWithCidProperty() {
    return dateWithCid;
  }

  public int getVesselWithCid() {
    return vesselWithCid.get();
  }

  public ReadOnlyIntegerProperty vesselWithCidProperty() {
    return vesselWithCid;
  }

  @Override
  public String toString() {
    return (
      "DateVesselWithCids [" +
      dateWithCid.get() +
      ", vesselWithCid " +
      vesselWithCid.get() +
      ", dVcid " +
      dVcid +
      "]"
    );
  }
}
