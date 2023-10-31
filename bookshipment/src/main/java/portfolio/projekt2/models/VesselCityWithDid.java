package portfolio.projekt2.models;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import portfolio.projekt2.dao.VesselsDAO;

public class VesselCityWithDid {

  private final ReadOnlyIntegerProperty cityWithDid;
  private final ReadOnlyIntegerProperty vesselWithDid;
  private final ReadOnlyIntegerProperty did;

  private final int vCid;
  

  public VesselCityWithDid(
    Integer cityWithDid,
    Integer vesselWithDid,
    Integer did,
    int vCid
  ) {
    this.cityWithDid = new SimpleIntegerProperty(cityWithDid);
    this.vesselWithDid = new SimpleIntegerProperty(vesselWithDid);
    this.did = new SimpleIntegerProperty(did);
    this.vCid = vCid;
    
  }

  public int getVCid() {
    return vCid;
  }

  public int getCityWithDid() {
    return cityWithDid.get();
  }

  public ReadOnlyIntegerProperty cityWithDidProperty() {
    return cityWithDid;
  }

  public int getVesselWithDid() {
    return vesselWithDid.get();
  }

  public ReadOnlyIntegerProperty vesselWithDidProperty() {
    return vesselWithDid;
  }

  public int getDidIn() {
    return did.get();
  }

  public ReadOnlyIntegerProperty didProperty() {
    return did;
  }

  @Override
  public String toString() {
    return (
      "VesselCityWithDids [" +
      cityWithDid.get() +
      ", vesselWithDid " +
      vesselWithDid.get() + ", did " + did.get() +
      ", vCid " +
      vCid +
      "]"
    );
  }
}
