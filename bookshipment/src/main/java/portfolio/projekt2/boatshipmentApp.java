package portfolio.projekt2;

import java.util.Comparator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import portfolio.projekt2.controllers.Controller;
import portfolio.projekt2.dao.CityDAO;
import portfolio.projekt2.dao.CityDateWithVidDAO;
import portfolio.projekt2.dao.Database;
import portfolio.projekt2.dao.DateDAO;
import portfolio.projekt2.dao.DateVesselWithCidDAO;
import portfolio.projekt2.dao.RouteDAO;
import portfolio.projekt2.dao.VesselCityWithDidDAO;
import portfolio.projekt2.dao.VesselsDAO;
import portfolio.projekt2.models.City;
import portfolio.projekt2.models.CityDateWithVid;
import portfolio.projekt2.models.CsvReader;
import portfolio.projekt2.models.DataLoader;
import portfolio.projekt2.models.Date;
import portfolio.projekt2.models.DateVesselWithCid;
import portfolio.projekt2.models.Route;
import portfolio.projekt2.models.Vessel;
import portfolio.projekt2.models.VesselCityWithDid;

public class boatshipmentApp extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  public BorderPane createInterface(Controller controller) {
    BorderPane borderPane = new BorderPane();

    VBox vbox = new VBox();
    vbox.setId("dragTarget");
    vbox.setSpacing(10);

    HBox hbox = new HBox();
    hbox.setAlignment(Pos.CENTER);

    Label label = new Label("Boat Shipment Aplication");

    Button exitButton = new Button("X");
    exitButton.setOnAction(e -> controller.handleExitButtonClicked(e));

    HBox rightIcons = new HBox(exitButton);
    rightIcons.setId("right-icons");
    rightIcons.setAlignment(Pos.CENTER_RIGHT);
    rightIcons.setSpacing(10);
    HBox.setHgrow(rightIcons, Priority.ALWAYS);

    hbox.getChildren().addAll(label, rightIcons);
    hbox.setPadding(new Insets(2, 5, 2, 5));

    // Initialize the TableView with type Route
    TableView<Route> exampleTable = new TableView<>();

    // Create columns
    TableColumn<Route, Integer> ridColumn = new TableColumn<>("ID");
    TableColumn<Route, String> startDidColumn = new TableColumn<>("Departure Date");
    TableColumn<Route, String> endDidColumn = new TableColumn<>("Arrival Date");
    TableColumn<Route, String> startCidColumn = new TableColumn<>("Departure City");
    TableColumn<Route, String> endCidColumn = new TableColumn<>("Arrival City");
    TableColumn<Route, String> rVidColumn = new TableColumn<>("Vessel");

    // Add columns to the table
    exampleTable
      .getColumns()
      .addAll(
        ridColumn,
        startDidColumn,
        endDidColumn,
        startCidColumn,
        endCidColumn,
        rVidColumn
      );

    // Set the column resize policy
    exampleTable.setColumnResizePolicy(
      TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
    );

    Button searchButton = new Button("Search");
    searchButton.setOnAction(e -> controller.Search(e));
   

    Button bookButton = new Button("Book Shipment");
    bookButton.setOnAction(e -> controller.book(e));
   


    

    // Pass the TableView and its columns to the controller
    controller.exampleTable = exampleTable;
    controller.ridColumn = ridColumn;
    controller.startDidColumn = startDidColumn;
    controller.endDidColumn = endDidColumn;
    controller.startCidColumn = startCidColumn;
    controller.endCidColumn = endCidColumn;
    controller.rVidColumn = rVidColumn;
    controller.bookButton = bookButton;

    // Now that the controller has references to the table and columns, call initialize

    HBox buttonBox = new HBox(searchButton, bookButton);
    buttonBox.setAlignment(Pos.CENTER_RIGHT);
    buttonBox.setPrefHeight(40);
    buttonBox.setSpacing(20);

    vbox.getChildren().addAll(hbox, exampleTable, buttonBox);

    borderPane.setCenter(vbox);
    controller.initialize();
    //set the exampleTable to sort from earlyest date to latest date
    controller.exampleTable.setSortPolicy( tv -> {
      Comparator<Route> c = (r1, r2) -> {
        return r1.getStartDid() - r2.getStartDid();
      };
      FXCollections.sort(controller.exampleTable.getItems(), c);
      return true;
    });
    return borderPane;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    System.out.println("Starting Boatshipment");
    deletALl();
    System.out.println("Database cleared");
    load();
    System.out.println("CSV loaded");
    printAll();
    if (Database.isOK()) {
      Controller controller = new Controller();
      primaryStage.setTitle("Boatshipment");
      primaryStage.setScene(new Scene(createInterface(controller), 1400, 500));
      primaryStage.initStyle(StageStyle.DECORATED);
      primaryStage.show();
    } else {
      System.out.println("Database is not OK");
      Platform.exit();
    }
  }

  /*  
    String startdate= "";

    String endDate= "";

    String startcity = "Yokohama";

    String endcity = "";
    SearchFunctions sc = new SearchFunctions();
    for(Route route : sc.Search(startdate, endDate, startcity, endcity, 10)){
      System.out.println(route);
      System.out.print(" Startdate: "+ DateDAO.getDate(route.getStartDid()).get().getDateV()); 
      System.out.print(" Enddate: "+ DateDAO.getDate(route.getEndDid()).get().getDateV());
      System.out.print(" Startcity: "+ CityDAO.getCity(route.getStartCid()).get().getCityV());
      System.out.print(" Endcity: "+ CityDAO.getCity(route.getEndCid()).get().getCityV());
      System.out.println(" Vessel: "+ VesselsDAO.getVessel(route.getRVid()).get().getVesselName()+" "+ VesselsDAO.getVessel(route.getRVid()).get().getAvailableCapacity());
    }
      
     */

  /* 
    Vessel vessel = VesselsDAO.getVessel(5).get();
      System.out.println(vessel);
      for(String s : vessel.getCityDateWithVidIndex().split(".")){
        
        System.out.println(CityDateWithVidDAO.getCityDateWithVid(Integer.parseInt(s)));
         int temp = CityDateWithVidDAO.getCityDateWithVid(Integer.parseInt(s)).get().getDateWithVid();
         int temp2 = CityDateWithVidDAO.getCityDateWithVid(Integer.parseInt(s)).get().getCityWithVid();
         System.out.println(DateDAO.getDate(temp)); 
         System.out.println(CityDAO.getCity(temp2));
         
       
        

      }
      
      */

  //DataLoader dataLoader = new DataLoader();
  //dataLoader.loadCSV();

  //dataLoader.Updatepaires();

  //DateDAO.getDates().forEach(System.out::println);
  //CityDAO.getCitys().forEach(System.out::println);

  //printAll();

  //deletALl();
  /*  
    testVessels1(10);
    deletAllVessesls();
    testVessels2(10);
    deletAllVessesls();
     */
  //testVessels2(10);
  //testDates2(10);
  //testCitys2(10);
  //testRouts2(10);
  /* 
    testDates1(10);
    deleteAllDates();
    testDates2(10);
    deleteAllDates();
    */
  /* 
    testCitys1(10);
    deleteAllCitys();
    testCitys2(10);
    deleteAllCitys();
     */
  /* 
    testRouts1(10);
    deleteAllRouts();
    testRouts2(10);
    deleteAllRouts();
    */

  /*
    testCityDateWithVid1(10);
    deleteAllCityDateWithVid();
    testCityDateWithVid2(10);
    deleteAllCityDateWithVid();
    */
  /* 
    tetsVesselCityWithDid1(10);
    deleteAllVesselCityWithDid();
    tetsVesselCityWithDid2(10);
    deleteAllVesselCityWithDid();
    */

  /*
    testDateVesselWithCid1(10);
    deleteAllDateVesselWithCid();
    testDateVesselWithCid2(10);
    deleteAllDateVesselWithCid();
    */

  //testCSVreader();

  
  public static void load() {
    DataLoader dataLoader = new DataLoader();
    dataLoader.loadCSV();
    dataLoader.Updatepaires();
  }

  public static void deletALl() {
    deleteAllDates();
    deleteAllCitys();
    deleteAllRouts();
    deleteAllCityDateWithVid();
    deleteAllVesselCityWithDid();
    deleteAllDateVesselWithCid();
    deletAllVessesls();
  }

  public static void printAll() {
    VesselsDAO.getVessels().forEach(System.out::println);
    DateDAO.getDates().forEach(System.out::println);
    CityDAO.getCitys().forEach(System.out::println);
    RouteDAO.getRoutes().forEach(System.out::println);
    CityDateWithVidDAO.getCityDateWithVids().forEach(System.out::println);
    VesselCityWithDidDAO.getVesselCityWithDids().forEach(System.out::println);
    DateVesselWithCidDAO.getDateVesselWithCids().forEach(System.out::println);
  }

  public static void testVessels1(int amount) {
    if (!Database.isOK()) {
      System.exit(1);
    }
    int n = 1;
    for (int i = 0; i < amount; i++) {
      Vessel vessel = new Vessel(
        "titanic" + i,
        100 + i,
        200 + 2 + i,
        100 + i,
        "" + i,
        n + i
      );
      VesselsDAO.insertVessel(
        vessel.getVesselName(),
        vessel.getUsedCapacity(),
        vessel.getMaxCapacity(),
        vessel.getAvailableCapacity(),
        vessel.getCityDateWithVidIndex()
      );
      System.out.println(VesselsDAO.getVessel(n));
      if (!Database.isOK()) {
        System.out.println(
          "error at testvessel1:" + " " + i + " " + "after inserted"
        );
        System.exit(1);
      }
      Vessel vessel2 = new Vessel(
        "titanicUP" + i,
        100 + i,
        200 + 2 + i,
        100 + i,
        "" + i,
        n
      );
      VesselsDAO.update(vessel2);
      System.out.println(VesselsDAO.getVessel(n));
      VesselsDAO.delete(n);
      if (!Database.isOK()) {
        System.out.println(
          "error at testvessel1:" + " " + i + " " + "after getand delet"
        );
        System.exit(1);
      }
    }
    VesselsDAO.getVessels().forEach(System.out::println);
  }

  public static void testVessels2(int amount) {
    if (!Database.isOK()) {
      System.exit(1);
    }
    int n = 1;
    for (int i = 0; i < amount; i++) {
      Vessel vessel = new Vessel(
        "titanic" + i,
        100 + i,
        200 + 2 + i,
        100 + i,
        "" + i,
        n + i
      );
      VesselsDAO.insertVessel(
        vessel.getVesselName(),
        vessel.getUsedCapacity(),
        vessel.getMaxCapacity(),
        vessel.getAvailableCapacity(),
        vessel.getCityDateWithVidIndex()
      );
      System.out.println(VesselsDAO.getVessel(n + i));
      if (!Database.isOK()) {
        System.out.println("error at testvessel2:" + " " + i + " " + "insert");
        System.exit(1);
      }
    }
    if (!Database.isOK()) {
      System.exit(1);
    }
    for (int i = 0; i < amount; i++) {
      Vessel vessel2 = new Vessel(
        "titanicUP" + i,
        100 + i,
        200 + 2 + i,
        100 + i,
        "" + i,
        n + i
      );
      VesselsDAO.update(vessel2);

      System.out.println(VesselsDAO.getVessel(n + i));

      if (!Database.isOK()) {
        System.out.println("error at testvessel2:" + " " + i + " " + "update");
        System.exit(1);
      }
    }
  }

  public static void deletAllVessesls() {
    while (
      VesselsDAO.getVessels() == null || VesselsDAO.getVessels().size() > 0
    ) {
      if (!Database.isOK()) {
        System.out.println("error at testvessel delete all:");
        System.exit(1);
      }
      try {
        VesselsDAO
          .getVessels()
          .forEach(vessel -> VesselsDAO.delete(vessel.getVid()));
      } catch (Exception ex) {}
    }

    VesselsDAO.getVessels().forEach(System.out::println);
  }

  public static void testDates1(int amount) {
    if (!Database.isOK()) {
      System.exit(1);
    }
    int n = 1;
    for (int i = 0; i < amount; i++) {
      Date date = new Date("2021-01-01" + " " + i, "" + i, n + i);
      DateDAO.insertDate(date.getDateV(), date.getCityVesselWithDidIndex());
      System.out.println(DateDAO.getDate(n));
      if (!Database.isOK()) {
        System.out.println(
          "error at test date1:" + " " + i + " " + "after inserted"
        );
        System.exit(1);
      }
      Date date2 = new Date("2021-01-01" + "UP" + i, "" + i, n);
      DateDAO.update(date2);
      System.out.println(DateDAO.getDate(n));
      DateDAO.delete(n);
      if (!Database.isOK()) {
        System.out.println(
          "error at test date :" + " " + i + " " + "after getand delet"
        );
        System.exit(1);
      }
    }
    DateDAO.getDates().forEach(System.out::println);
  }

  public static void testDates2(int amount) {
    if (!Database.isOK()) {
      System.exit(1);
    }
    int n = 1;
    for (int i = 0; i < amount; i++) {
      Date date = new Date("2021-01-01" + " " + i, "" + i, n + i);
      DateDAO.insertDate(date.getDateV(), date.getCityVesselWithDidIndex());
      System.out.println(DateDAO.getDate(n + i));
      if (!Database.isOK()) {
        System.out.println(
          "error at test date2:" + " " + i + " " + "after inserted"
        );
        System.exit(1);
      }
    }
    if (!Database.isOK()) {
      System.exit(1);
    }
    for (int i = 0; i < amount; i++) {
      Date date2 = new Date("2021-01-01" + "UP" + i, "" + i, n + i);
      DateDAO.update(date2);

      System.out.println(DateDAO.getDate(n + i));

      if (!Database.isOK()) {
        System.out.println("error at test date2:" + " " + i + " " + "update");
        System.exit(1);
      }
    }
  }

  public static void deleteAllDates() {
    while (DateDAO.getDates() == null || DateDAO.getDates().size() > 0) {
      if (!Database.isOK()) {
        System.out.println("error at test vdate delete all:");
        System.exit(1);
      }
      try {
        DateDAO.getDates().forEach(date -> DateDAO.delete(date.getDid()));
      } catch (Exception ex) {}
    }

    DateDAO.getDates().forEach(System.out::println);
  }

  public static void testCitys1(int amount) {
    if (!Database.isOK()) {
      System.exit(1);
    }
    int n = 1;
    for (int i = 0; i < amount; i++) {
      City city = new City("city" + i, "" + i, n + i);
      CityDAO.insertCity(city.getCityV(), city.getVesselDateWithCidIndex());
      System.out.println(CityDAO.getCity(n));
      if (!Database.isOK()) {
        System.out.println(
          "error at test city1:" + " " + i + " " + "after inserted"
        );
        System.exit(1);
      }
      City city2 = new City("cityUP" + i, "" + i, n);
      CityDAO.update(city2);
      System.out.println(CityDAO.getCity(n));
      CityDAO.delete(n);
      if (!Database.isOK()) {
        System.out.println(
          "error at test city1:" + " " + i + " " + "after getand delet"
        );
        System.exit(1);
      }
    }
    CityDAO.getCitys().forEach(System.out::println);
  }

  public static void testCitys2(int amount) {
    if (!Database.isOK()) {
      System.exit(1);
    }
    int n = 1;
    for (int i = 0; i < amount; i++) {
      City city = new City("city" + i, "" + i, n + i);
      CityDAO.insertCity(city.getCityV(), city.getVesselDateWithCidIndex());
      System.out.println(CityDAO.getCity(n + i));
      if (!Database.isOK()) {
        System.out.println(
          "error at test city2:" + " " + i + " " + "after inserted"
        );
        System.exit(1);
      }
    }
    if (!Database.isOK()) {
      System.exit(1);
    }
    for (int i = 0; i < amount; i++) {
      City city2 = new City("cityUP" + i, "" + i, n + i);
      CityDAO.update(city2);

      System.out.println(CityDAO.getCity(n + i));

      if (!Database.isOK()) {
        System.out.println("error at test city2:" + " " + i + " " + "update");
        System.exit(1);
      }
    }
  }

  public static void deleteAllCitys() {
    while (CityDAO.getCitys() == null || CityDAO.getCitys().size() > 0) {
      if (!Database.isOK()) {
        System.out.println("error at test city delete all:");
        System.exit(1);
      }
      try {
        CityDAO.getCitys().forEach(city -> CityDAO.delete(city.getCid()));
      } catch (Exception ex) {}
    }

    CityDAO.getCitys().forEach(System.out::println);
  }

  public static void testRouts1(int amount) {
    if (!Database.isOK()) {
      System.exit(1);
    }
    int n = 1;
    for (int i = 0; i < amount; i++) {
      Route route = new Route(20 + i, 3 + i, 3 + i, 4 + i, 3 + i, n + i);
      RouteDAO.insertRoute(
        route.getStartDid(),
        route.getEndDid(),
        route.getStartCid(),
        route.getEndCid(),
        route.getRVid()
      );
      System.out.println(RouteDAO.getRoute(n));
      if (!Database.isOK()) {
        System.out.println(
          "error at test reoute1:" + " " + i + " " + "after inserted"
        );
        System.exit(1);
      }
      Route route2 = new Route(2077 + i, 3 + i, 3 + i, 4 + i, 3 + i, n);
      RouteDAO.update(route2);
      System.out.println(RouteDAO.getRoute(n));
      RouteDAO.delete(n);
      if (!Database.isOK()) {
        System.out.println(
          "error at test reoute1:" + " " + i + " " + "after getand delet"
        );
        System.exit(1);
      }
    }
    RouteDAO.getRoutes().forEach(System.out::println);
  }

  public static void testRouts2(int amount) {
    if (!Database.isOK()) {
      System.exit(1);
    }
    int n = 1;
    for (int i = 0; i < amount; i++) {
      Route route = new Route(20 + i, 3 + i, 3 + i, 4 + i, 3 + i, n + i);
      RouteDAO.insertRoute(
        route.getStartDid(),
        route.getEndDid(),
        route.getStartCid(),
        route.getEndCid(),
        route.getRVid()
      );
      System.out.println(RouteDAO.getRoute(n + i));
      if (!Database.isOK()) {
        System.out.println(
          "error at test route2:" + " " + i + " " + "after inserted"
        );
        System.exit(1);
      }
    }
    if (!Database.isOK()) {
      System.exit(1);
    }
    for (int i = 0; i < amount; i++) {
      Route route2 = new Route(2077 + i, 3 + i, 3 + i, 4 + i, 3 + i, n + i);
      RouteDAO.update(route2);

      System.out.println(RouteDAO.getRoute(n + i));

      if (!Database.isOK()) {
        System.out.println("error at test route 2:" + " " + i + " " + "update");
        System.exit(1);
      }
    }
  }

  public static void deleteAllRouts() {
    while (RouteDAO.getRoutes() == null || RouteDAO.getRoutes().size() > 0) {
      if (!Database.isOK()) {
        System.out.println("error at test route delete all:");
        System.exit(1);
      }
      try {
        RouteDAO.getRoutes().forEach(route -> RouteDAO.delete(route.getRid()));
      } catch (Exception ex) {}
    }
    RouteDAO.getRoutes().forEach(System.out::println);
  }

  // create testCityDateWithVid1 and testCityDateWithVid2 and deleteAllCityDateWithVid same as above
  public static void testCityDateWithVid1(int amount) {
    if (!Database.isOK()) {
      System.exit(1);
    }
    int n = 1;
    for (int i = 0; i < amount; i++) {
      CityDateWithVid cityDateWithVid = new CityDateWithVid(
        1 + i,
        1 + i,
        1 + i,
        n + i
      );
      CityDateWithVidDAO.instertCityDateWithVid(
        cityDateWithVid.getCityWithVid(),
        cityDateWithVid.getDateWithVid(),
        cityDateWithVid.getVidIn()
      );
      System.out.println(CityDateWithVidDAO.getCityDateWithVid(n));
      if (!Database.isOK()) {
        System.out.println(
          "error at test CityDateWithVi:" + " " + i + " " + "after inserted"
        );
        System.exit(1);
      }

      CityDateWithVid cityDateWithVid2 = new CityDateWithVid(
        1 + 200 + i,
        1 + i,
        1 + i,
        n
      );

      CityDateWithVidDAO.udpate(cityDateWithVid2);

      System.out.println(CityDateWithVidDAO.getCityDateWithVid(n));

      CityDateWithVidDAO.delete(n);

      if (!Database.isOK()) {
        System.out.println(
          "error at test CityDateWithVid:" +
          " " +
          i +
          " " +
          "after getand delet"
        );
        System.exit(1);
      }
    }
    CityDateWithVidDAO.getCityDateWithVids().forEach(System.out::println);
  }

  public static void testCityDateWithVid2(int amount) {
    if (!Database.isOK()) {
      System.exit(1);
    }
    int n = 1;
    for (int i = 0; i < amount; i++) {
      CityDateWithVid cityDateWithVid = new CityDateWithVid(
        1 + i,
        1 + i,
        1 + i,
        n + i
      );
      CityDateWithVidDAO.instertCityDateWithVid(
        cityDateWithVid.getCityWithVid(),
        cityDateWithVid.getDateWithVid(),
        cityDateWithVid.getVidIn()
      );
      System.out.println(CityDateWithVidDAO.getCityDateWithVid(n + i));
      if (!Database.isOK()) {
        System.out.println(
          "error at test CityDateWithVi:" + " " + i + " " + "after inserted"
        );
        System.exit(1);
      }
    }
    if (!Database.isOK()) {
      System.exit(1);
    }
    for (int i = 0; i < amount; i++) {
      CityDateWithVid cityDateWithVid2 = new CityDateWithVid(
        1 + 200 + i,
        1 + i,
        1 + i,
        n + i
      );
      CityDateWithVidDAO.udpate(cityDateWithVid2);

      System.out.println(CityDateWithVidDAO.getCityDateWithVid(n + i));

      if (!Database.isOK()) {
        System.out.println(
          "error at test CityDateWithVid:" + " " + i + " " + "after update"
        );
        System.exit(1);
      }
    }
  }

  public static void deleteAllCityDateWithVid() {
    while (
      CityDateWithVidDAO.getCityDateWithVids() == null ||
      CityDateWithVidDAO.getCityDateWithVids().size() > 0
    ) {
      if (!Database.isOK()) {
        System.out.println("error at test CityDateWithVid delete all:");
        System.exit(1);
      }
      try {
        CityDateWithVidDAO
          .getCityDateWithVids()
          .forEach(cityDateWithVid ->
            CityDateWithVidDAO.delete(cityDateWithVid.getcDid())
          );
      } catch (Exception ex) {}
    }

    CityDateWithVidDAO.getCityDateWithVids().forEach(System.out::println);
  }

  // create tetsVesselCityWithDid1 and tetsVesselCityWithDid2 and deleteAllVesselCityWithDid same as above
  public static void tetsVesselCityWithDid1(int amount) {
    if (!Database.isOK()) {
      System.exit(1);
    }
    int n = 1;

    for (int i = 0; i < amount; i++) {
      VesselCityWithDid vesselCityWithDid = new VesselCityWithDid(
        1 + i,
        1 + i,
        1 + i,
        n + i
      );
      VesselCityWithDidDAO.instertVesselCityWithDid(
        vesselCityWithDid.getCityWithDid(),
        vesselCityWithDid.getVesselWithDid(),
        vesselCityWithDid.getDidIn()
      );
      System.out.println(VesselCityWithDidDAO.getVesselCityWithDid(n));
      if (!Database.isOK()) {
        System.out.println(
          "error at test VesselCityWithDid:" + " " + i + " " + "after inserted"
        );
        System.exit(1);
      }

      VesselCityWithDid vesselCityWithDid2 = new VesselCityWithDid(
        1 + 200 + i,
        1 + i,
        1 + i,
        n
      );

      VesselCityWithDidDAO.udpate(vesselCityWithDid2);

      System.out.println(VesselCityWithDidDAO.getVesselCityWithDid(n));

      VesselCityWithDidDAO.delete(n);

      if (!Database.isOK()) {
        System.out.println(
          "error at test VesselCityWithDid:" +
          " " +
          i +
          " " +
          "after getand delet"
        );
        System.exit(1);
      }
    }
    VesselCityWithDidDAO.getVesselCityWithDids().forEach(System.out::println);
  }

  public static void tetsVesselCityWithDid2(int amount) {
    if (!Database.isOK()) {
      System.exit(1);
    }
    int n = 1;

    for (int i = 0; i < amount; i++) {
      VesselCityWithDid vesselCityWithDid = new VesselCityWithDid(
        1 + i,
        1 + i,
        1 + i,
        n + i
      );
      VesselCityWithDidDAO.instertVesselCityWithDid(
        vesselCityWithDid.getCityWithDid(),
        vesselCityWithDid.getVesselWithDid(),
        vesselCityWithDid.getDidIn()
      );
      System.out.println(VesselCityWithDidDAO.getVesselCityWithDid(n + i));
      if (!Database.isOK()) {
        System.out.println(
          "error at test VesselCityWithDid:" + " " + i + " " + "after inserted"
        );
        System.exit(1);
      }
    }
    if (!Database.isOK()) {
      System.exit(1);
    }
    for (int i = 0; i < amount; i++) {
      VesselCityWithDid vesselCityWithDid2 = new VesselCityWithDid(
        1 + 200 + i,
        1 + i,
        1 + i,
        n + i
      );
      VesselCityWithDidDAO.udpate(vesselCityWithDid2);

      System.out.println(VesselCityWithDidDAO.getVesselCityWithDid(n + i));

      if (!Database.isOK()) {
        System.out.println(
          "error at test VesselCityWithDid:" + " " + i + " " + "after update"
        );
        System.exit(1);
      }
    }
  }

  public static void deleteAllVesselCityWithDid() {
    while (
      VesselCityWithDidDAO.getVesselCityWithDids() == null ||
      VesselCityWithDidDAO.getVesselCityWithDids().size() > 0
    ) {
      if (!Database.isOK()) {
        System.out.println("error at test VesselCityWithDid delete all:");
        System.exit(1);
      }
      try {
        VesselCityWithDidDAO
          .getVesselCityWithDids()
          .forEach(vesselCityWithDid ->
            VesselCityWithDidDAO.delete(vesselCityWithDid.getVCid())
          );
      } catch (Exception ex) {}
    }

    VesselCityWithDidDAO.getVesselCityWithDids().forEach(System.out::println);
  }

  // create testDateVesselWithCid1 and testDateVesselWithCid2 and deleteAllDateVesselWithCid same as above

  public static void testDateVesselWithCid1(int amount) {
    if (!Database.isOK()) {
      System.exit(1);
    }
    int n = 1;

    for (int i = 0; i < amount; i++) {
      DateVesselWithCid dateVesselWithCid = new DateVesselWithCid(
        1 + i,
        1 + i,
        1 + i,
        n + i
      );
      DateVesselWithCidDAO.instertDateVesselWithCid(
        dateVesselWithCid.getDateWithCid(),
        dateVesselWithCid.getVesselWithCid(),
        dateVesselWithCid.getCidIn()
      );
      System.out.println(DateVesselWithCidDAO.getDateVesselWithCid(n));
      if (!Database.isOK()) {
        System.out.println(
          "error at test DateVesselWithCid:" + " " + i + " " + "after inserted"
        );
        System.exit(1);
      }

      DateVesselWithCid dateVesselWithCid2 = new DateVesselWithCid(
        1 + 200 + i,
        1 + i,
        1 + i,
        n
      );

      DateVesselWithCidDAO.udpate(dateVesselWithCid2);

      System.out.println(DateVesselWithCidDAO.getDateVesselWithCid(n));

      DateVesselWithCidDAO.delete(n);

      if (!Database.isOK()) {
        System.out.println(
          "error at test DateVesselWithCid:" +
          " " +
          i +
          " " +
          "after getand delet"
        );
        System.exit(1);
      }
    }
    DateVesselWithCidDAO.getDateVesselWithCids().forEach(System.out::println);
  }

  public static void testDateVesselWithCid2(int amount) {
    if (!Database.isOK()) {
      System.exit(1);
    }
    int n = 1;

    for (int i = 0; i < amount; i++) {
      DateVesselWithCid dateVesselWithCid = new DateVesselWithCid(
        1 + i,
        1 + i,
        1 + i,
        n + i
      );
      DateVesselWithCidDAO.instertDateVesselWithCid(
        dateVesselWithCid.getDateWithCid(),
        dateVesselWithCid.getVesselWithCid(),
        dateVesselWithCid.getCidIn()
      );
      System.out.println(DateVesselWithCidDAO.getDateVesselWithCid(n + i));
      if (!Database.isOK()) {
        System.out.println(
          "error at test DateVesselWithCid:" + " " + i + " " + "after inserted"
        );
        System.exit(1);
      }
    }
    if (!Database.isOK()) {
      System.exit(1);
    }
    for (int i = 0; i < amount; i++) {
      DateVesselWithCid dateVesselWithCid2 = new DateVesselWithCid(
        1 + 200 + i,
        1 + i,
        1 + i,
        n + i
      );
      DateVesselWithCidDAO.udpate(dateVesselWithCid2);

      System.out.println(DateVesselWithCidDAO.getDateVesselWithCid(n + i));

      if (!Database.isOK()) {
        System.out.println(
          "error at test DateVesselWithCid:" + " " + i + " " + "after update"
        );
        System.exit(1);
      }
    }
  }

  public static void deleteAllDateVesselWithCid() {
    while (
      DateVesselWithCidDAO.getDateVesselWithCids() == null ||
      DateVesselWithCidDAO.getDateVesselWithCids().size() > 0
    ) {
      if (!Database.isOK()) {
        System.out.println("error at test DateVesselWithCid delete all:");
        System.exit(1);
      }
      try {
        DateVesselWithCidDAO
          .getDateVesselWithCids()
          .forEach(dateVesselWithCid ->
            DateVesselWithCidDAO.delete(dateVesselWithCid.getdVid())
          );
      } catch (Exception ex) {}
    }

    DateVesselWithCidDAO.getDateVesselWithCids().forEach(System.out::println);
  }

  public static void testCSVreader() {
    String[][] routes = CsvReader.ReadRoutes();

    for (int i = 0; i < routes.length; i++) {
      for (int j = 0; j < routes[i].length; j++) {
        System.out.print(routes[i][j] + " ");
      }
      System.out.println();
    }
  }
  
}
