package portfolio.projekt2.models;

import java.util.HashSet;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import portfolio.projekt2.boatshipmentApp;
import portfolio.projekt2.dao.*;

public class DataLoader {

  private final String[][] csvArray;
  private final HashSet<String> vesSpicific;
  private final HashSet<String> daSpicific;
  private final HashSet<String> cySpicific;
  //private final HashSet<String> daEndSpicific;
  //private final HashSet<String> cidEndSpicific;

  private final ObservableList<Route> routes;

  public DataLoader() {
    this.csvArray = getCsvArray();
    this.vesSpicific = getCsvSpicific(2);
    this.daSpicific = getCsvSpicific(0);
    //this.daEndSpicific = getCsvSpicific(1);
    this.cySpicific = getCsvSpicific(3);
    //this.cidEndSpicific = getCsvSpicific(4);
    this.routes = RouteDAO.getRoutes();
  }

  private static String[][] getCsvArray() {
    String[][] routes = CsvReader.ReadRoutes();
    return routes;
  }

  private static HashSet<String> getCsvSpicific(int n) {
    HashSet<String> hashSet = CsvReader.ReadSpecific(n);
    return hashSet;
  }

  private static int random_int(int Min, int Max) {
    return (int) (Math.random() * (Max - Min)) + Min;
  }

  public void loadCSV() {
    
    checkCSV();
    int Couldamout = 5;

    Integer vesselMaxCapacity = 0;
    Integer vesselMaxCapacityTemp = 0;
    Integer vesselUsedCapacity = 0;
    Integer vesselAvailableCapacity = 0;

    for (String ve : this.vesSpicific) {
      vesselMaxCapacityTemp = random_int(100, 1000);
      vesselMaxCapacity = vesselMaxCapacityTemp;
      vesselUsedCapacity = random_int(0, vesselMaxCapacityTemp);
      vesselAvailableCapacity = vesselMaxCapacityTemp - vesselUsedCapacity;
      Vessel newVessel = new Vessel(
        ve,
        vesselUsedCapacity,
        vesselMaxCapacity,
        vesselAvailableCapacity,
        "",
        0
      );

      if (checkifVesselAllreadyEx(newVessel)) {
        VesselsDAO.insertVessel(
          newVessel.getVesselName(),
          newVessel.getUsedCapacity(),
          newVessel.getMaxCapacity(),
          newVessel.getAvailableCapacity(),
          newVessel.getCityDateWithVidIndex()
        );
      } 
    }

    for (String da : this.daSpicific) {
      
      Date newDate = new Date(da, "", 0);

      if (checkifDateAllredyEx(newDate)) {
        DateDAO.insertDate(
          newDate.getDateV(),
          newDate.getCityVesselWithDidIndex()
        );
      }
    }
    /* 
    for (String da : this.daEndSpicific){
      Date newDate = new Date(da, "", 0);

      if (checkifDateAllredyEx(newDate)) {
        System.out.println(da);
        DateDAO.insertDate(
          newDate.getDateV(),
          newDate.getCityVesselWithDidIndex()
        );
      }
    }
*/
    for (String ci : this.cySpicific) {
      City newCity = new City(ci, "", 0);
      if (checkifCityAllredyEx(newCity)) {
        CityDAO.insertCity(
          newCity.getCityV(),
          newCity.getVesselDateWithCidIndex()
        );
      } 
    }
    /* 
    for (String ci : this.cidEndSpicific) {
      City newCity = new City(ci, "", 0);
      if (checkifCityAllredyEx(newCity)) {
        System.out.println(ci);
        CityDAO.insertCity(
          newCity.getCityV(),
          newCity.getVesselDateWithCidIndex()
        );
      } 
    }
*/
    for (int i = 0; i < this.csvArray.length; i++) {
      int[][] tempArray = new int[this.csvArray.length][Couldamout];

      for (Date Date : DateDAO.getDates()) {
        if (Date.getDateV().equals(this.csvArray[i][0])) {
          tempArray[i][0] = Date.getDid();
        }

        if (Date.getDateV().equals(this.csvArray[i][1])) {
          tempArray[i][1] = Date.getDid();
        }
      }

      for (City City : CityDAO.getCitys()) {
        if (City.getCityV().equals(this.csvArray[i][3])) {
          tempArray[i][2] = City.getCid();
        }
        if (City.getCityV().equals(this.csvArray[i][4])) {
          tempArray[i][3] = City.getCid();
        }
      }

      for (Vessel Vessel : VesselsDAO.getVessels()) {
        if (Vessel.getVesselName().equals(this.csvArray[i][2])) {
          tempArray[i][4] = Vessel.getVid();
        }
      }

      Route newRoute = new Route(
        tempArray[i][0],
        tempArray[i][1],
        tempArray[i][2],
        tempArray[i][3],
        tempArray[i][4],
        0
      );

      if (checkCSVDub(tempArray[i])) {
        RouteDAO.insertRoute(
          newRoute.getStartDid(),
          newRoute.getEndDid(),
          newRoute.getStartCid(),
          newRoute.getEndCid(),
          newRoute.getRVid()
        );
      }else{
        System.out.println(tempArray[i][0] + " " + tempArray[i][1] + " " + tempArray[i][2] + " " + tempArray[i][3] + " " + tempArray[i][4] );
      } 
    }
  }

  private Boolean checkCSVDub(int[] tempArray) {
    for (Route route : this.routes) {
      if (
        route.getStartDid() == tempArray[0] &&
        route.getEndDid() == tempArray[1] &&
        route.getStartCid() == tempArray[2] &&
        route.getEndCid() == tempArray[3] &&
        route.getRVid() == tempArray[4]
      ) {
        return false;
      }
    }
    return true;
  }

  public Boolean checkifVesselAllreadyEx(Vessel vesselToCheck) {
    for (Vessel vesselTemp : VesselsDAO.getVessels()) {
      if (vesselTemp.getVesselName().equals(vesselToCheck.getVesselName())) {
        return false;
      }
    }
    return true;
  }

  public Boolean checkifDateAllredyEx(Date dateToCheck) {
    for (Date dateTemp : DateDAO.getDates()) {
      if (dateTemp.getDateV().equals(dateToCheck.getDateV())) {
        return false;
      }
    }
    return true;
  }

  public Boolean checkifCityAllredyEx(City cityToCheck) {
    for (City cityTemp : CityDAO.getCitys()) {
      if (cityTemp.getCityV().equals(cityToCheck.getCityV())) {
        return false;
      }
    }
    return true;
  }

  public void clearWithid() {
    ObservableList<Vessel> toUpdateV = FXCollections.observableArrayList();

    for (Vessel vessel : VesselsDAO.getVessels()) {
      toUpdateV.add(
        new Vessel(
          vessel.getVesselName(),
          vessel.getUsedCapacity(),
          vessel.getMaxCapacity(),
          vessel.getAvailableCapacity(),
          "",
          vessel.getVid()
        )
      );
    }

    for (Vessel vessel : toUpdateV) {
      VesselsDAO.update(vessel);
    }

    ObservableList<Date> toUpdateD = FXCollections.observableArrayList();

    for (Date date : DateDAO.getDates()) {
      toUpdateD.add(new Date(date.getDateV(), "", date.getDid()));
    }

    for (Date date : toUpdateD) {
      DateDAO.update(date);
    }

    ObservableList<City> toUpdateC = FXCollections.observableArrayList();

    for (City city : CityDAO.getCitys()) {
      toUpdateC.add(new City(city.getCityV(), "", city.getCid()));
    }

    for (City city : toUpdateC) {
      CityDAO.update(city);
    }
  }

  public void Updatepaires() {
    boatshipmentApp.deleteAllDateVesselWithCid();

    boatshipmentApp.deleteAllVesselCityWithDid();

    boatshipmentApp.deleteAllCityDateWithVid();

    clearWithid();

    for (Route route : RouteDAO.getRoutes()) {
      CityDateWithVid cityDateWithVid = new CityDateWithVid(
        route.getStartCid(),
        route.getStartDid(),
        route.getRVid(),
        0
      );
      CityDateWithVidDAO.instertCityDateWithVid(
        cityDateWithVid.getCityWithVid(),
        cityDateWithVid.getDateWithVid(),
        cityDateWithVid.getVidIn()
      );

      DateVesselWithCid dateVesselWithCid = new DateVesselWithCid(
        route.getStartDid(),
        route.getRVid(),
        route.getStartCid(),
        0
      );
      DateVesselWithCidDAO.instertDateVesselWithCid(
        dateVesselWithCid.getDateWithCid(),
        dateVesselWithCid.getVesselWithCid(),
        dateVesselWithCid.getCidIn()
      );

      VesselCityWithDid vesselCityWithDid = new VesselCityWithDid(
        route.getStartCid(),
        route.getRVid(),
        route.getStartDid(),
        0
      );
      VesselCityWithDidDAO.instertVesselCityWithDid(
        vesselCityWithDid.getCityWithDid(),
        vesselCityWithDid.getVesselWithDid(),
        vesselCityWithDid.getDidIn()
      );
    }

    for (CityDateWithVid cityDateWithVid : CityDateWithVidDAO.getCityDateWithVids()) {
      Optional<Vessel> vessel = VesselsDAO.getVessel(
        cityDateWithVid.getVidIn()
      );

      String tempC = "";
      tempC =
        vessel.get().getCityDateWithVidIndex() +
        cityDateWithVid.getcDid() +
        "-";
      Vessel newVessel = new Vessel(
        vessel.get().getVesselName(),
        vessel.get().getUsedCapacity(),
        vessel.get().getMaxCapacity(),
        vessel.get().getAvailableCapacity(),
        tempC,
        vessel.get().getVid()
      );
      VesselsDAO.update(newVessel);
    }

    for (DateVesselWithCid dateVesselWithCid : DateVesselWithCidDAO.getDateVesselWithCids()) {
      Optional<City> city = CityDAO.getCity(dateVesselWithCid.getCidIn());
      String tempC = "";
      tempC =
        city.get().getVesselDateWithCidIndex() +
        dateVesselWithCid.getdVid() +
        "-";
      City newCity = new City(
        city.get().getCityV(),
        tempC,
        city.get().getCid()
      );
      CityDAO.update(newCity);
    }

    for (VesselCityWithDid vesselCityWithDid : VesselCityWithDidDAO.getVesselCityWithDids()) {
      Optional<Date> date = DateDAO.getDate(vesselCityWithDid.getDidIn());
      String tempC = "";
      tempC =
        date.get().getCityVesselWithDidIndex() +
        vesselCityWithDid.getVCid() +
        "-";
      Date newDate = new Date(
        date.get().getDateV(),
        tempC,
        date.get().getDid()
      );
      DateDAO.update(newDate);
    }
  }

  public void checkCSV() {
    for (String[] row : this.csvArray) {
      if (row.length != 5) throw new IllegalStateException(
        "CSV file is not in correct format"
      );
    }
  }
}
