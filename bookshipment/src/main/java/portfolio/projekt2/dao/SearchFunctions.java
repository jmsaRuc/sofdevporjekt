package portfolio.projekt2.dao;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import javafx.collections.ObservableList;
import portfolio.projekt2.boatshipmentApp;
import portfolio.projekt2.dao.*;
import portfolio.projekt2.models.*;
import javafx.collections.ObservableList;
public class SearchFunctions {

  private final ObservableList<Vessel> vessels;
  private final ObservableList<Date> dates;
  private final ObservableList<City> citys;
  private final ObservableList<Route> routes;

  private final ObservableList<CityDateWithVid> cityDateWithVids;
  private final ObservableList<DateVesselWithCid> dateVesselWithCitys;
  private final ObservableList<VesselCityWithDid> vesselCityWithDates;

  private final int[] vids;
  private final int[] dids;
  private final int[] cids;
  private final int[] rids;

  private final int[] cDids;
  private final int[] vCids;
  private final int[] dVid;

  public SearchFunctions() {
    this.vessels = VesselsDAO.getVessels();
    this.dates = DateDAO.getDates();
    this.citys = CityDAO.getCitys();
    this.routes = RouteDAO.getRoutes();

    this.cityDateWithVids = CityDateWithVidDAO.getCityDateWithVids();
    this.dateVesselWithCitys = DateVesselWithCidDAO.getDateVesselWithCids();
    this.vesselCityWithDates = VesselCityWithDidDAO.getVesselCityWithDids();

    this.vids = getVids();
    this.dids = getDids();
    this.cids = getCids();
    this.rids = getRids();

    this.cDids = getCDids();
    this.vCids = getVCids();
    this.dVid = getDVids();
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

  private int[] getCDids() {
    int[] cDids = new int[cityDateWithVids.size()];
    for (int i = 0; i < cityDateWithVids.size(); i++) {
      cDids[i] = cityDateWithVids.get(i).getcDid();
    }
    return cDids;
  }

  private int[] getVCids() {
    int[] vCids = new int[vesselCityWithDates.size()];
    for (int i = 0; i < vesselCityWithDates.size(); i++) {
      vCids[i] = vesselCityWithDates.get(i).getVCid();
    }
    return vCids;
  }

  private int[] getDVids() {
    int[] dVid = new int[dateVesselWithCitys.size()];
    for (int i = 0; i < dateVesselWithCitys.size(); i++) {
      dVid[i] = dateVesselWithCitys.get(i).getdVid();
    }
    return dVid;
  }

  public Optional<City> searchForCity(String cityName) {
    for (City city : this.citys) {
      if (city.getCityV().equals(cityName)) {
        return Optional.of(city);
      }
    }
    return Optional.empty();
  }

  public Optional<Date> searchForDate(String date) {
    for (Date date1 : this.dates) {
      if (date1.getDateV().equals(date)) {
        return Optional.of(date1);
      }
    }
    return Optional.empty();
  }

  public ObservableList<Vessel> searchForVesssel_withAvailableCapacity(
    int amountOFcontainers
  ) {
    ObservableList<Vessel> vesselsWithAvailableCapacity = this.vessels;
    for (Vessel vessel : vesselsWithAvailableCapacity) {
      if (vessel.getAvailableCapacity() < amountOFcontainers) {
        vesselsWithAvailableCapacity.remove(vessel);
      }
    }
    return vesselsWithAvailableCapacity;
    
  }
  public ObservableList<Route> Search(String startDate, String endDate, String startCity, String endCity, int amountOFcontainers){
    
    ObservableList<Vessel> vesselsWithAvailableCapacity = searchForVesssel_withAvailableCapacity(amountOFcontainers);
    ObservableList<Route> routesResult = routes;
    for (Vessel vessel : vesselsWithAvailableCapacity) {
      
      for(String s : vessel.getCityDateWithVidIndex().split(",")){
         int temp = CityDateWithVidDAO.getCityDateWithVid(Integer.parseInt(s)).get().getDateWithVid();
       
        

      }
    }
    return routesResult;
  }
}
