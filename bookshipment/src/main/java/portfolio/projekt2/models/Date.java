/*
 * As most of the model class are the same but with diffrent values,
 * there wil only in detb comments in the first one:
 * @City
 * 
 * This is the model class for the Date table in the database.
 * 
 * The Date class is used to create Date objects, which are used in the,
 * DateDAO class.
 * 
 */

package portfolio.projekt2.models;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Date {

  private final ReadOnlyStringProperty dateV;
  private final ReadOnlyStringProperty cityVesselWithDidIndex;
  private final int did;

  public Date(String dateV, String cityVesselWithDidIndex, int did) {
    this.dateV = new SimpleStringProperty(dateV);
    this.cityVesselWithDidIndex =
      new SimpleStringProperty(cityVesselWithDidIndex);
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

  public String getCityVesselWithDidIndex() {
    return cityVesselWithDidIndex.get();
  }

  public ReadOnlyStringProperty CityVesselWithDidIndexProperty() {
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
