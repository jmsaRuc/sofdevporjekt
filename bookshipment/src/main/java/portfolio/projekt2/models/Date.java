package portfolio.projekt2.models;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Date {

  private final ReadOnlyStringProperty dateV;
  private final ReadOnlyIntegerProperty cityVesselWithDidIndex;
  private final int did;

  public Date(String dateV, Integer cityVesselWithDidIndex, int did) {
    this.dateV = new SimpleStringProperty(dateV);
    this.cityVesselWithDidIndex =
      new SimpleIntegerProperty(cityVesselWithDidIndex);
    this.did = did;
  }

  public int getDid() {
    return did;
  }

  public String getDateV() {
    return dateV.get();
  }

  public ReadOnlyStringProperty getDateVProperty() {
    return dateV;
  }

  public int getCityVesselWithDidIndex() {
    return cityVesselWithDidIndex.get();
  }

  public ReadOnlyIntegerProperty CityVesselWithDidIndexProperty() {
    return cityVesselWithDidIndex;
  }

  @Override
  public String toString() {
    return (
      "Date [" +
      dateV.get() +
      ", cityVesselWithDidIndex " +
      cityVesselWithDidIndex.get() +
      ", did " +
      did +
      "]"
    );
  }
}
