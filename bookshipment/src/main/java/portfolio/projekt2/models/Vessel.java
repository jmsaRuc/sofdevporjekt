package portfolio.projekt2.models;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Vessel {

  private final ReadOnlyStringProperty vesselName;
  private final ReadOnlyIntegerProperty usedCapacity;
  private final ReadOnlyIntegerProperty maxCapacity;
  private final ReadOnlyIntegerProperty availableCapacity;
  private final ReadOnlyIntegerProperty cityDateWithVid;
  private final int vid;

  public Vessel(
    String vesselName,
    Integer usedCapacity,
    Integer maxCapacity,
    Integer availableCapacity,
    Integer cityDateWithVid,
    int vid
  ) {
    this.vesselName = new SimpleStringProperty(vesselName);
    this.usedCapacity = new SimpleIntegerProperty(usedCapacity);
    this.maxCapacity = new SimpleIntegerProperty(maxCapacity);
    this.availableCapacity = new SimpleIntegerProperty(availableCapacity);
    this.cityDateWithVid = new SimpleIntegerProperty(cityDateWithVid);
    this.vid = vid;
  }

  public int getVid() {
    return vid;
  }

  public String getVesselName() {
    return vesselName.get();
  }

  public ReadOnlyStringProperty vesselNameProperty() {
    return vesselName;
  }

  public int getUsedCapacity() {
    return usedCapacity.get();
  }

  public ReadOnlyIntegerProperty usedCapacityProperty() {
    return usedCapacity;
  }


  public int getMaxCapacity() {
    return maxCapacity.get();
  }

  public ReadOnlyIntegerProperty maxCapacityProperty() {
    return maxCapacity;
  }

  public int getAvailableCapacity() {
    return availableCapacity.get();
  }

  public ReadOnlyIntegerProperty availableCapacityProperty() {
    return availableCapacity;
  }

  public int getCityDateWithVidIndex() {
    return cityDateWithVid.get();
  }

  public ReadOnlyIntegerProperty cityDateWithVidProperty() {
    return cityDateWithVid;
  }

  @Override
  public String toString() {
    return (
      "Vessel [" +
      vesselName.get() +
      ", usedCapacit " + usedCapacity.get() + ", maxCapacity " + maxCapacity.get() + ", availableCapacity " + availableCapacity.get() + ", cityDateWithVid " + cityDateWithVid.get() + ", vid " + vid + "]"
    );
  }
}
