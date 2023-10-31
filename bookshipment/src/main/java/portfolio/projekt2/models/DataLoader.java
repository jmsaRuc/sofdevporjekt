package portfolio.projekt2.models;


import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import javafx.collections.ObservableList;
import portfolio.projekt2.boatshipmentApp;
import portfolio.projekt2.dao.*;
import portfolio.projekt2.models.*;

public class DataLoader {

 


  private final String[][] csvArray;
  private final HashSet<String> vesSpicific;
  private final HashSet<String> daSpicific;
  private final HashSet<String> cySpicific;
  public DataLoader() {
    this.csvArray = getCsvArray();
    this.vesSpicific = getCsvSpicific(2);
    this.daSpicific = getCsvSpicific(0);
    this.cySpicific = getCsvSpicific(3);
  }

  private String[][] getCsvArray() {
    String[][] routes = CsvReader.ReadRoutes();
    return routes;
  }

  private HashSet<String> getCsvSpicific(int n) {
    HashSet<String> hashSet = CsvReader.ReadSpecific(n);
    return hashSet;
  }

  public static int random_int(int Min, int Max)
{   
     return (int) (Math.random()*(Max-Min))+Min;
}


  public void loadCSV() {

    int Couldamout=5;
    
    Integer vesselMaxCapacity;
    Integer vesselMaxCapacityTemp;
    Integer vesselUsedCapacity;
    Integer vesselAvailableCapacity;
    int n =1;
    for (String ve : vesSpicific) {
      
     

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
        n
      );
      VesselsDAO.insertVessel(
        newVessel.getVesselName(),
        newVessel.getUsedCapacity(),
        newVessel.getMaxCapacity(),
        newVessel.getAvailableCapacity(),
        newVessel.getCityDateWithVidIndex()
      );
      n++;
    }
    n = 1;
    for (String da : daSpicific) {
      
      Date newDate = new Date(da, "", n);
      DateDAO.insertDate(newDate.getDateV(),newDate.getCityVesselWithDidIndex());
      n++;
    }

    
    n = 1;
    for (String ci : cySpicific) {  
      
      City newCity = new City(ci,"",n);
      CityDAO.insertCity(newCity.getCityV(),newCity.getVesselDateWithCidIndex());
      n++;
    }
    n = 1;
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
        n
      );

      RouteDAO.insertRoute(routeT.getStartDid(),routeT.getEndDid(), routeT.getStartCid(), routeT.getEndCid(), routeT.getRVid());
      n++;
    }
    
  }
  public void Updatepaires(){
    boatshipmentApp.deleteAllDateVesselWithCid();

    boatshipmentApp.deleteAllVesselCityWithDid();

    boatshipmentApp.deleteAllCityDateWithVid();

    int n = 1;
    for (Route route : RouteDAO.getRoutes()){

      CityDateWithVid cityDateWithVid = new CityDateWithVid(route.getStartCid(),route.getStartDid(),route.getRVid(),n);
      CityDateWithVidDAO.instertCityDateWithVid(cityDateWithVid.getCityWithVid(),cityDateWithVid.getDateWithVid(),cityDateWithVid.getVidIn());
    
      DateVesselWithCid dateVesselWithCid = new DateVesselWithCid(route.getStartDid(),route.getRVid(),route.getStartCid(),n);
      DateVesselWithCidDAO.instertDateVesselWithCid(dateVesselWithCid.getDateWithCid(),dateVesselWithCid.getVesselWithCid(),dateVesselWithCid.getCidIn());

      VesselCityWithDid vesselCityWithDid = new VesselCityWithDid(route.getStartCid(),route.getRVid(),route.getStartDid(),n);
      VesselCityWithDidDAO.instertVesselCityWithDid(vesselCityWithDid.getCityWithDid(),vesselCityWithDid.getVesselWithDid(),vesselCityWithDid.getDidIn());

      n++;
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
