package portfolio.projekt2.models;

import java.util.Optional;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import portfolio.projekt2.dao.VesselsDAO;

public class VesselCityWithDid {

  private final ReadOnlyIntegerProperty cityWithDid;
  private final ReadOnlyIntegerProperty vesselWithDid;

  private final int vCdid;
  private final Optional<Vessel> vessel;

  public VesselCityWithDid(
    Integer cityWithDid,
    Integer vesselWithDid,
    int vCdid
  ) {
    this.cityWithDid = new SimpleIntegerProperty(cityWithDid);
    this.vesselWithDid = new SimpleIntegerProperty(vesselWithDid);
    this.vCdid = vCdid;
    this.vessel = VesselsDAO.getVessel(vCdid);
  }

  public int getVCdid() {
    return vCdid;
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

  public Optional<Vessel> getVessel() {
    return vessel;
  }

  @Override
  public String toString() {
    return (
      "VesselCityWithDids [" +
      cityWithDid.get() +
      ", vesselWithDid " +
      vesselWithDid.get() +
      ", vCdid " +
      vCdid +
      "]"
    );
  }
}
