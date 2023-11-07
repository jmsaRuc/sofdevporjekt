package portfolio.projekt2.dao;

import java.util.HashSet;
import java.util.Optional;
import java.util.Optional;
import java.util.Random;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableList;
import portfolio.projekt2.boatshipmentApp;
import portfolio.projekt2.dao.*;
import portfolio.projekt2.models.*;

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


    ObservableList<Vessel> resultV = FXCollections.observableArrayList();
    for (Vessel vessel : this.vessels) {
      if (vessel.getAvailableCapacity() >= amountOFcontainers) {
        resultV.add(vessel);
      }
    }
    return FXCollections.unmodifiableObservableList(resultV);
  }

  public ObservableList<Route> Search(
    String startDate,
    String endDate,
    String startCity,
    String endCity,
    int amountOFcontainers
  ) {
    ObservableList<Vessel> vesselsWithAvailableCapacity = searchForVesssel_withAvailableCapacity(
      amountOFcontainers
    );
    ObservableList<Route> result = FXCollections.observableArrayList();

    for (Vessel vessel : vesselsWithAvailableCapacity) {
      try {
        for (String s : vessel.getCityDateWithVidIndex().split("-")) {
          String sDidtemp = DateDAO
            .getDate(
              CityDateWithVidDAO
                .getCityDateWithVid(Integer.parseInt(s))
                .get()
                .getDateWithVid()
            )
            .get()
            .getDateV();

          String eDidtemp = DateDAO
            .getDate(
              CityDateWithVidDAO
                .getCityDateWithVid(Integer.parseInt(s) + 1)
                .get()
                .getDateWithVid()
            )
            .get()
            .getDateV();

          String sCidTemp = CityDAO
            .getCity(
              CityDateWithVidDAO
                .getCityDateWithVid(Integer.parseInt(s))
                .get()
                .getCityWithVid()
            )
            .get()
            .getCityV();

          String eCidTemp = CityDAO
            .getCity(
              CityDateWithVidDAO
                .getCityDateWithVid(Integer.parseInt(s) + 1)
                .get()
                .getCityWithVid()
            )
            .get()
            .getCityV();
          if (startDate != "" && endDate != "" && startCity != "" && endCity != "") {
            if (
              sDidtemp.equals(startDate) &&
              eDidtemp.equals(endDate) &&
              sCidTemp.equals(startCity) &&
              eCidTemp.equals(endCity)
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }

          }

          if (startDate != "" && endDate != "" && startCity != "" && endCity == "") {
            if (
              sDidtemp.equals(startDate) &&
              eDidtemp.equals(endDate) &&
              sCidTemp.equals(startCity)
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }

          }

          if (startDate != "" && endDate != "" && startCity == "" && endCity != "") {
            if (
              sDidtemp.equals(startDate) &&
              eDidtemp.equals(endDate) &&
              eCidTemp.equals(endCity)
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }

          }

          if (startDate != "" && endDate == "" && startCity != "" && endCity != "") {
            if (
              sDidtemp.equals(startDate) &&
              sCidTemp.equals(startCity) &&
              eCidTemp.equals(endCity)
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }

          }

          if (startDate == "" && endDate != "" && startCity != "" && endCity != "") {
            if (
              eDidtemp.equals(endDate) &&
              sCidTemp.equals(startCity) &&
              eCidTemp.equals(endCity)
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }

          }

          if (startDate != "" && endDate == "" && startCity == "" && endCity != "") {
            if (
              sDidtemp.equals(startDate) &&
              eCidTemp.equals(endCity)
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }

          }

          if (startDate != "" && endDate == "" && startCity != "" && endCity == "") {
            if (
              sDidtemp.equals(startDate) &&
              sCidTemp.equals(startCity)
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }

          }

          if (startDate == "" && endDate != "" && startCity != "" && endCity == "") {
            if (
              eDidtemp.equals(endDate) &&
              sCidTemp.equals(startCity)
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }

          }

          if (startDate == "" && endDate != "" && startCity == "" && endCity != "") {
            if (
              eDidtemp.equals(endDate) &&
              eCidTemp.equals(endCity)
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }

          }

          if (startDate == "" && endDate == "" && startCity != "" && endCity != "") {
            if (
              sCidTemp.equals(startCity) &&
              eCidTemp.equals(endCity)
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }

          }

          if (startDate != "" && endDate != "" && startCity == "" && endCity == "") {
            if (
              sDidtemp.equals(startDate) &&
              eDidtemp.equals(endDate)
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }

          }

          if (startDate == "" && endDate == "" && startCity == "" && endCity != "") {
            if (
              eCidTemp.equals(endCity)
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }

          }

          if (startDate == "" && endDate == "" && startCity != "" && endCity == "") {
            if (
              sCidTemp.equals(startCity)
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }

          }

          if (startDate == "" && endDate != "" && startCity == "" && endCity == "") {
            if (
              eDidtemp.equals(endDate)
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }

          }

          if (startDate != "" && endDate == "" && startCity == "" && endCity == "") {
            if (
              sDidtemp.equals(startDate)
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }

          }

          if (startDate == "" && endDate == "" && startCity == "" && endCity == "") {
            result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
          }
          

        }
      } catch (Exception e) {}
    }
    return FXCollections.unmodifiableObservableList(result);
  }
}
