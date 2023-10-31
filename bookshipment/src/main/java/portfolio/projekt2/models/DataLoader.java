package portfolio.projekt2.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import javafx.collections.ObservableList;
import portfolio.projekt2.boatshipmentApp;
import portfolio.projekt2.dao.*;
import portfolio.projekt2.models.*;

public class DataLoader {

  private final ObservableList<Vessel> vessels;
  private final ObservableList<Date> dates;
  private final ObservableList<City> citys;
  private final ObservableList<Route> routes;

  private final int[] vids;
  private final int[] dids;
  private final int[] cids;
  private final int[] rids;
  private final String[][] csvArray;
  private final HashSet<String> vesSpicific;
  private final HashSet<String> daSpicific;
  private final HashSet<String> cySpicific;
  public DataLoader() {
    this.vessels = VesselsDAO.getVessels();
    this.dates = DateDAO.getDates();
    this.citys = CityDAO.getCitys();
    this.routes = RouteDAO.getRoutes();
    this.vids = getVids();
    this.dids = getDids();
    this.cids = getCids();
    this.rids = getRids();
    this.csvArray = getCsvArray();
    this.vesSpicific = getCsvSpicific(2);
    this.daSpicific = getCsvSpicific(0);
    this.cySpicific = getCsvSpicific(3);
  }

  private int[] getVids() {
    int[] vids = new int[vessels.size()];
    for (int i = 0; i < vessels.size(); i++) {
      vids[i] = vessels.get(i).getVid();
    }
    return vids;
  }

  private int[] getDids() {
    int[] dids = new int[dates.size()];
    for (int i = 0; i < dates.size(); i++) {
      dids[i] = dates.get(i).getDid();
    }
    return dids;
  }

  private int[] getCids() {
    int[] cids = new int[citys.size()];
    for (int i = 0; i < citys.size(); i++) {
      cids[i] = citys.get(i).getCid();
    }
    return cids;
  }

  private int[] getRids() {
    int[] rids = new int[routes.size()];
    for (int i = 0; i < routes.size(); i++) {
      rids[i] = routes.get(i).getRid();
    }
    return rids;
  }

  private String[][] getCsvArray() {
    String[][] routes = CsvReader.ReadRoutes();
    return routes;
  }

  private HashSet<String> getCsvSpicific(int n) {
    HashSet<String> hashSet = CsvReader.ReadSpecific(n);
    return hashSet;
  }


  public void loadCSV() {

    int Couldamout=5;
    Random random = new Random();
    Integer vesselMaxCapacity;
    Integer vesselUsedCapacity;
    Integer vesselAvailableCapacity;

    for (String ve : vesSpicific) {
      
      vesselMaxCapacity = random.nextInt(1000 + 1 - 100) + 100;
      vesselUsedCapacity = random.nextInt(vesselMaxCapacity + 1 - 1) + 1;
      vesselAvailableCapacity = vesselMaxCapacity - vesselUsedCapacity;
      Vessel newVessel = new Vessel(
        ve,
        vesselUsedCapacity,
        vesselMaxCapacity,
        vesselAvailableCapacity,
        "",
        0
      );
      VesselsDAO.insertVessel(
        newVessel.getVesselName(),
        newVessel.getUsedCapacity(),
        newVessel.getMaxCapacity(),
        newVessel.getAvailableCapacity(),
        newVessel.getCityDateWithVidIndex()
      );
    }

    for (String da : daSpicific) {
      
      Date newDate = new Date(da, "", 0);
      DateDAO.insertDate(newDate.getDateV(),newDate.getCityVesselWithDidIndex());
    }

    

    for (String ci : cySpicific) {  
      
      City newCity = new City(ci,"",0);
      CityDAO.insertCity(newCity.getCityV(),newCity.getVesselDateWithCidIndex());
    }

    for (int i = 0; i < csvArray.length; i++) {
       
      int[][] tempArray = new int[csvArray.length][Couldamout]; 

      for ( Date Date : DateDAO.getDates()) {
        if(Date.getDateV().equals(csvArray[i][0])){
          tempArray[i][0] = Date.getDid();
        }
        if(Date.getDateV().equals(csvArray[i][1])){
          tempArray[i][1] = Date.getDid();
        }
      }

      for ( City City : CityDAO.getCitys()) {
        if(City.getCityV().equals(csvArray[i][3])){
          tempArray[i][2] = City.getCid();
        }
        if(City.getCityV().equals(csvArray[i][4])){
          tempArray[i][3] = City.getCid();
        }
      }

      for ( Vessel Vessel : VesselsDAO.getVessels()) {
        if(Vessel.getVesselName().equals(csvArray[i][2])){
          tempArray[i][4] = Vessel.getVid();
        }
      }
      Route routeT = new Route(
        tempArray[i][0],
        tempArray[i][1],
        tempArray[i][2],
        tempArray[i][3],
        tempArray[i][4],
        0
      );

      RouteDAO.insertRoute(routeT.getStartDid(),routeT.getEndDid(), routeT.getStartCid(), routeT.getEndCid(), routeT.getRVid());
    }
    
  }
  public void Updatepaires(){
    boatshipmentApp.deleteAllDateVesselWithCid();
    boatshipmentApp.deleteAllVesselCityWithDid();
    boatshipmentApp.deleteAllCityDateWithVid();
    for (Route route : RouteDAO.getRoutes()){
      CityDateWithVid cityDateWithVid = new CityDateWithVid(route.getStartCid(),route.getStartDid(),route.getRVid(),0);
      CityDateWithVidDAO.instertCityDateWithVid(cityDateWithVid.getCityWithVid(),cityDateWithVid.getDateWithVid(),cityDateWithVid.getVidIn());
      DateVesselWithCid dateVesselWithCid = new DateVesselWithCid(route.getStartDid(),route.getRVid(),route.getStartCid(),0);
      DateVesselWithCidDAO.instertDateVesselWithCid(dateVesselWithCid.getDateWithCid(),dateVesselWithCid.getVesselWithCid(),dateVesselWithCid.getCidIn());
      VesselCityWithDid vesselCityWithDid = new VesselCityWithDid(route.getStartCid(),route.getRVid(),route.getStartDid(),0);
      VesselCityWithDidDAO.instertVesselCityWithDid(vesselCityWithDid.getCityWithDid(),vesselCityWithDid.getVesselWithDid(),vesselCityWithDid.getDidIn());
    }
    
    
    for (CityDateWithVid cityDateWithVid : CityDateWithVidDAO.getCityDateWithVids()){
      
      Optional<City> city = CityDAO.getCity(cityDateWithVid.getCityWithVid());
      String tempC = city.get().getVesselDateWithCidIndex() + cityDateWithVid.getDateWithVid() + ",";
      City newCity = new City(city.get().getCityV(),tempC,city.get().getCid());
      CityDAO.update(newCity);  
    }

    for (DateVesselWithCid dateVesselWithCid : DateVesselWithCidDAO.getDateVesselWithCids()){
      
      Optional<Date> date = DateDAO.getDate(dateVesselWithCid.getDateWithCid());
      String tempC = date.get().getCityVesselWithDidIndex() + dateVesselWithCid.getVesselWithCid() + ",";
      Date newDate = new Date(date.get().getDateV(),tempC,date.get().getDid());
      DateDAO.update(newDate);  
    }

    for (VesselCityWithDid vesselCityWithDid : VesselCityWithDidDAO.getVesselCityWithDids()){
      
      Optional<Vessel> vessel = VesselsDAO.getVessel(vesselCityWithDid.getVesselWithDid());
      String tempC = vessel.get().getCityDateWithVidIndex() + vesselCityWithDid.getCityWithDid() + ",";
      Vessel newVessel = new Vessel(vessel.get().getVesselName(),vessel.get().getUsedCapacity(),vessel.get().getMaxCapacity(),vessel.get().getAvailableCapacity(),tempC,vessel.get().getVid());
      VesselsDAO.update(newVessel);  
    }
  }
}
