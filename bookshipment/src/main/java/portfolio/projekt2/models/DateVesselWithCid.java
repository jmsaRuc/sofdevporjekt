/*
 * As most of the model class are the same but with diffrent values,
 * there wil only in detb comments in the first one:
 * @City
 * 
 * This is the model class for the DateVesselWithCid table in the database.
 * 
 * The DateVesselWithCid class is used to create DateVesselWithCid objects, which are used in the,
 * DateVesselWithCidDAO class.
 * 
 */

package portfolio.projekt2.models;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class DateVesselWithCid {

  private final ReadOnlyIntegerProperty dateWithCid;
  private final ReadOnlyIntegerProperty vesselWithCid;
  private final ReadOnlyIntegerProperty cid;
  private final int dVid;

  public DateVesselWithCid(
    Integer dateWithCid,
    Integer vesselWithCid,
    Integer cid,
    int dVid
  ) {
    this.dateWithCid = new SimpleIntegerProperty(dateWithCid);
    this.vesselWithCid = new SimpleIntegerProperty(vesselWithCid);
    this.cid = new SimpleIntegerProperty(cid);
    this.dVid = dVid;
  }

  public int getdVid() {
    return dVid;
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

  public int getCidIn() {
    return cid.get();
  }

  public ReadOnlyIntegerProperty cidProperty() {
    return cid;
  }

  @Override
  public String toString() {
    return (
      "DateVesselWithCids [" +
      dateWithCid.get() +
      ", vesselWithCid " +
      vesselWithCid.get() +
      ", cid " +
      cid.get() +
      ", dVid " +
      dVid +
      "]"
    );
  }
}
