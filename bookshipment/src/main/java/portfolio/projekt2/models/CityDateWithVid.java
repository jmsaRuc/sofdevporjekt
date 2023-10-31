package portfolio.projekt2.models;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CityDateWithVid {

  private final ReadOnlyIntegerProperty cityWithVid;
  private final ReadOnlyIntegerProperty dateWithVid;
  private final ReadOnlyIntegerProperty vid;

  private final int cDid;

  public CityDateWithVid(
    Integer cityWithVid,
    Integer dateWithVid,
    Integer vid,
    int cDid
  ) {
    this.cityWithVid = new SimpleIntegerProperty(cityWithVid);
    this.dateWithVid = new SimpleIntegerProperty(dateWithVid);
    this.vid = new SimpleIntegerProperty(vid);
    this.cDid = cDid;
  }

  public int getcDid() {
    return cDid;
  }

  public int getCityWithVid() {
    return cityWithVid.get();
  }

  public ReadOnlyIntegerProperty cityWithVidProperty() {
    return cityWithVid;
  }

  public int getDateWithVid() {
    return dateWithVid.get();
  }

  public ReadOnlyIntegerProperty dateWithVidProperty() {
    return dateWithVid;
  }

  public int getVidIn() {
    return vid.get();
  }

  public ReadOnlyIntegerProperty vidInProperty() {
    return vid;
  }

  @Override
  public String toString() {
    return (
      "CityDateWithVids [" +
      cityWithVid.get() +
      ", dateWithVid " +
      dateWithVid.get() +
      ", vid " +
      vid.get() +
      ", cDid " +
      cDid +
      "]"
    );
  }
}
