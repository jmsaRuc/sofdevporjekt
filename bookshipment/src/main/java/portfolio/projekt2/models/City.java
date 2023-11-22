/*
 * As most of the model class are the same but with diffrent values,
 * there wil only in detb comments in the first one:
 * @City
 *
 * This is the model class for the City table in the database
 *
 * The City class is used to create City objects, which are used in the,
 * CityDAO class.
 *
 */

package portfolio.projekt2.models;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;

/* This class is used to store the information about the cities. 
It contains the city name, the vessel date and the cid. */

public class City {

  private final ReadOnlyStringProperty cityV;

  private final ReadOnlyStringProperty vesselDateWithCidIndex;

  private final int cid;

  public City(String cityV, String vesselDateWithCidIndex, int cid) {
    // This initializes a new City object. The constructor takes three
    // arguments: cityV, vesselDateWithCidIndex, and cid. Each of these
    // arguments are assigned to the corresponding member variables of the
    // same name.
    this.cityV = new SimpleStringProperty(cityV);
    this.vesselDateWithCidIndex =
      new SimpleStringProperty(vesselDateWithCidIndex);
    this.cid = cid;
  }
  // Create a getter method for the cid.
  public int getCid() {
    return cid;
  }

  // Return the property value as a string
  public String getCityV() {
    return cityV.get();
  }

  // Create a getter method for the StringProperty.
  public ReadOnlyStringProperty getCityVProperty() {
    return cityV;
  }

  // Create a getter method for the StringProperty.
  public String getVesselDateWithCidIndex() {
    return vesselDateWithCidIndex.get();
  }

  // Create a getter method for the StringProperty.
  public ReadOnlyStringProperty getVesselDatewithCidindexProperty() {
    return vesselDateWithCidIndex;
  }

  // Override the toString() method so that it returns the city name, the
  // vessel date and the cid.
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
