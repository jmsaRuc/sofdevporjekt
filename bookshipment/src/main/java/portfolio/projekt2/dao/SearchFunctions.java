/*
 * This is class resposibell for the search function
 * 
 * 
 */

package portfolio.projekt2.dao;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import portfolio.projekt2.models.*;

public class SearchFunctions {

  private final ObservableList<Vessel> vessels;
  private final ObservableList<Date> dates;
  private final ObservableList<City> citys;
  private final ObservableList<Route> routes;

  private final ObservableList<CityDateWithVid> cityDateWithVids;
  private final ObservableList<DateVesselWithCid> dateVesselWithCitys;
  private final ObservableList<VesselCityWithDid> vesselCityWithDates;

  public SearchFunctions() {
    this.vessels = VesselsDAO.getVessels();
    this.dates = DateDAO.getDates();
    this.citys = CityDAO.getCitys();
    this.routes = RouteDAO.getRoutes();

    this.cityDateWithVids = CityDateWithVidDAO.getCityDateWithVids();
    this.dateVesselWithCitys = DateVesselWithCidDAO.getDateVesselWithCids();
    this.vesselCityWithDates = VesselCityWithDidDAO.getVesselCityWithDids();
  }

  public Optional<City> searchForCity(String cityName) {
    // Iterate through the list of cities
    for (City city : this.citys) {
      // If the name of the city matches the one we are looking for
      if (city.getCityV().equals(cityName)) {
        // Return the city
        return Optional.of(city);
      }
    }
    // If we didn't find a match, return an empty Optional
    return Optional.empty();
  }

  public Optional<Date> searchForDate(String date) {
    // loop through the dates list
    for (Date date1 : this.dates) {
      // if the date is found return the date
      if (date1.getDateV().equals(date)) {
        return Optional.of(date1);
      }
    }
    // if the date is not found return an empty optional
    return Optional.empty();
  }

  public ObservableList<Vessel> searchForVesssel_withAvailableCapacity(
    int amountOFcontainers
  ) {
    // create an empty list to hold the result
    ObservableList<Vessel> resultV = FXCollections.observableArrayList();
    // clear the list from any previous results
    resultV.clear();
    // loop over all vessels in the list
    for (Vessel vessel : this.vessels) {
      // check if the vessel has enough available capacity
      if (vessel.getAvailableCapacity() >= amountOFcontainers) {
        // if yes, add it to the result list
        resultV.add(vessel);
      }
    }
    // return the result
    return FXCollections.unmodifiableObservableList(resultV);
  }

  /*
   * This is the main search funtion of the program
   * is uses the the difrent "A""B"with"C" to find the routes
   * 
   * this function only need 
   * @amountOFcontainers 
   * to work. 
   * 
   * It can search with any combinatin of the other parameters 
   * 
   */

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
    result.clear();
    for (Vessel vessel : vesselsWithAvailableCapacity) {
      if (!vessel.getCityDateWithVidIndex().equals("s")) {
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

          String eDidtemp = "";
          try {
            eDidtemp =
              DateDAO
                .getDate(
                  CityDateWithVidDAO
                    .getCityDateWithVid(Integer.parseInt(s) + 1)
                    .get()
                    .getDateWithVid()
                )
                .get()
                .getDateV();
          } catch (Exception e) {}
          String sCidTemp = CityDAO
            .getCity(
              CityDateWithVidDAO
                .getCityDateWithVid(Integer.parseInt(s))
                .get()
                .getCityWithVid()
            )
            .get()
            .getCityV();

          String eCidTemp = "";
          try {
            eCidTemp =
              CityDAO
                .getCity(
                  CityDateWithVidDAO
                    .getCityDateWithVid(Integer.parseInt(s) + 1)
                    .get()
                    .getCityWithVid()
                )
                .get()
                .getCityV();
          } catch (Exception e) {}
          if (
            startDate != "" && endDate != "" && startCity != "" && endCity != ""
          ) {
            if (
              sDidtemp.equals(startDate) &&
              eDidtemp.equals(endDate) &&
              sCidTemp.equals(startCity) &&
              eCidTemp.equals(endCity)
            ) {
              if (
                VesselsDAO
                  .getVessel(
                    RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                  )
                  .get()
                  .getAvailableCapacity() >=
                amountOFcontainers
              ) {
                result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
              }
            }
          }

          if (
            startDate != "" && endDate != "" && startCity != "" && endCity == ""
          ) {
            if (
              sDidtemp.equals(startDate) &&
              eDidtemp.equals(endDate) &&
              sCidTemp.equals(startCity)
            ) {
              if (
                VesselsDAO
                  .getVessel(
                    RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                  )
                  .get()
                  .getAvailableCapacity() >=
                amountOFcontainers
              ) {
                result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
              }
            }
          }

          if (
            startDate != "" && endDate != "" && startCity == "" && endCity != ""
          ) {
            if (
              sDidtemp.equals(startDate) &&
              eDidtemp.equals(endDate) &&
              eCidTemp.equals(endCity)
            ) {
              if (
                VesselsDAO
                  .getVessel(
                    RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                  )
                  .get()
                  .getAvailableCapacity() >=
                amountOFcontainers
              ) {
                result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
              }
            }
          }

          if (
            startDate != "" && endDate == "" && startCity != "" && endCity != ""
          ) {
            if (
              sDidtemp.equals(startDate) &&
              sCidTemp.equals(startCity) &&
              eCidTemp.equals(endCity)
            ) {
              if (
                VesselsDAO
                  .getVessel(
                    RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                  )
                  .get()
                  .getAvailableCapacity() >=
                amountOFcontainers
              ) {
                result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
              }
            }
          }

          if (
            startDate == "" && endDate != "" && startCity != "" && endCity != ""
          ) {
            if (
              eDidtemp.equals(endDate) &&
              sCidTemp.equals(startCity) &&
              eCidTemp.equals(endCity)
            ) {
              if (
                VesselsDAO
                  .getVessel(
                    RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                  )
                  .get()
                  .getAvailableCapacity() >=
                amountOFcontainers
              ) {
                result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
              }
            }
          }

          if (
            startDate != "" && endDate == "" && startCity == "" && endCity != ""
          ) {
            if (sDidtemp.equals(startDate) && eCidTemp.equals(endCity)) {
              if (
                VesselsDAO
                  .getVessel(
                    RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                  )
                  .get()
                  .getAvailableCapacity() >=
                amountOFcontainers
              ) {
                result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
              }
            }
          }

          if (
            startDate != "" && endDate == "" && startCity != "" && endCity == ""
          ) {
            if (sDidtemp.equals(startDate) && sCidTemp.equals(startCity)) {
              if (
                VesselsDAO
                  .getVessel(
                    RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                  )
                  .get()
                  .getAvailableCapacity() >=
                amountOFcontainers
              ) {
                result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
              }
            }
          }

          if (
            startDate == "" && endDate != "" && startCity != "" && endCity == ""
          ) {
            if (eDidtemp.equals(endDate) && sCidTemp.equals(startCity)) {
              if (
                VesselsDAO
                  .getVessel(
                    RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                  )
                  .get()
                  .getAvailableCapacity() >=
                amountOFcontainers
              ) {
                result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
              }
            }
          }

          if (
            startDate == "" && endDate != "" && startCity == "" && endCity != ""
          ) {
            if (eDidtemp.equals(endDate) && eCidTemp.equals(endCity)) {
              if (
                VesselsDAO
                  .getVessel(
                    RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                  )
                  .get()
                  .getAvailableCapacity() >=
                amountOFcontainers
              ) {
                result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
              }
            }
          }

          if (
            startDate == "" && endDate == "" && startCity != "" && endCity != ""
          ) {
            if (sCidTemp.equals(startCity) && eCidTemp.equals(endCity)) {
              if (
                VesselsDAO
                  .getVessel(
                    RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                  )
                  .get()
                  .getAvailableCapacity() >=
                amountOFcontainers
              ) {
                result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
              }
            }
          }

          if (
            startDate != "" && endDate != "" && startCity == "" && endCity == ""
          ) {
            if (sDidtemp.equals(startDate) && eDidtemp.equals(endDate)) {
              if (
                VesselsDAO
                  .getVessel(
                    RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                  )
                  .get()
                  .getAvailableCapacity() >=
                amountOFcontainers
              ) {
                result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
              }
            }
          }

          if (
            startDate == "" && endDate == "" && startCity == "" && endCity != ""
          ) {
            if (eCidTemp.equals(endCity)) {
              if (
                VesselsDAO
                  .getVessel(
                    RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                  )
                  .get()
                  .getAvailableCapacity() >=
                amountOFcontainers
              ) {
                result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
              }
            }
          }

          if (
            startDate == "" && endDate == "" && startCity != "" && endCity == ""
          ) {
            if (sCidTemp.equals(startCity)) {
              if (
                VesselsDAO
                  .getVessel(
                    RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                  )
                  .get()
                  .getAvailableCapacity() >=
                amountOFcontainers
              ) {
                result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
              }
            }
          }

          if (
            startDate == "" && endDate != "" && startCity == "" && endCity == ""
          ) {
            if (eDidtemp.equals(endDate)) {
              if (
                VesselsDAO
                  .getVessel(
                    RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                  )
                  .get()
                  .getAvailableCapacity() >=
                amountOFcontainers
              ) {
                result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
              }
            }
          }

          if (
            startDate != "" && endDate == "" && startCity == "" && endCity == ""
          ) {
            if (sDidtemp.equals(startDate)) {
              if (
                VesselsDAO
                  .getVessel(
                    RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                  )
                  .get()
                  .getAvailableCapacity() >=
                amountOFcontainers
              ) {
                result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
              }
            }
          }

          if (
            startDate == "" && endDate == "" && startCity == "" && endCity == ""
          ) {
            if (
              VesselsDAO
                .getVessel(
                  RouteDAO.getRoute(Integer.parseInt(s)).get().getRVid()
                )
                .get()
                .getAvailableCapacity() >=
              amountOFcontainers
            ) {
              result.add(RouteDAO.getRoute(Integer.parseInt(s)).get());
            }
          }
        }
      }
    }

    return FXCollections.unmodifiableObservableList(result);
  }
}
