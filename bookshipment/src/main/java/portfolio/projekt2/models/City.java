package portfolio.projekt2.models;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;

public class City {

  private final ReadOnlyStringProperty cityV;
  private final ReadOnlyStringProperty vesselDateWithCidIndex;
  private final int cid;

  public City(String cityV, String vesselDateWithCidIndex, int cid) {
    this.cityV = new SimpleStringProperty(cityV);
    this.vesselDateWithCidIndex =
      new SimpleStringProperty(vesselDateWithCidIndex);
    this.cid = cid;
  }

  public int getCid() {
    return cid;
  }

  public String getCityV() {
    return cityV.get();
  }

  public ReadOnlyStringProperty getCityVProperty() {
    return cityV;
  }

  public String getVesselDateWithCidIndex() {
    return vesselDateWithCidIndex.get();
  }

  public ReadOnlyStringProperty getVesselDatewithCidindexProperty() {
    return vesselDateWithCidIndex;
  }

 
  

  @Override
  public String toString() {
    return (
      "City [" +
      cityV.get() +
      ", vesselDateWithCidIndex " +
      vesselDateWithCidIndex.get() +
      ", cid " +
      cid +
      "]"
    );
  }
}