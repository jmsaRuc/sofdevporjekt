package portfolio.projekt2.models;

import java.util.Optional;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import portfolio.projekt2.dao.VesselsDAO;
public class CityDateWithVid {

  private final ReadOnlyIntegerProperty cityWithVid;
  private final ReadOnlyIntegerProperty dateWithVid;
 
  private final int cDvid;
  private final Optional<Vessel> vessel;  


  public CityDateWithVid(Integer cityWithVid, Integer dateWithVid, int cDvid) {
    this.cityWithVid = new SimpleIntegerProperty(cityWithVid);
    this.dateWithVid = new SimpleIntegerProperty(dateWithVid);
    this.cDvid = cDvid;
    this.vessel = VesselsDAO.getVessel(cDvid);
  }

  public int getCDvid() {
    return cDvid;
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

  

  @Override
  public String toString() {
    return (
      "CityDateWithVids [" +
      cityWithVid.get() +
      ", dateWithVid " +
      dateWithVid.get() +
      ", cDvid " +
      cDvid +
      "]"
    );
  }
}
