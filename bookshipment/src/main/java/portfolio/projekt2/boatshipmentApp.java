/*
 * This is the main class of the application. It creates the interface and starts the application.
 * 
 * The whole aplication, and comments was made with the help of githup copilot
 * 
 * The arkitecture as well as well as the CRUDHelper, and Database comes from this articel: https://edencoding.com/connect-javafx-with-sqlite/
 * 
 * 
 * 
 */

/////////////////////////////////////////


/*
 * The datebase should be populated, if its not, uncomment the 
 * @load() 
 * method in the start method, but remeber to uncoment it after the first run,
 * or the database will be populated every time the application is started
 * 
 * 
 */

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
import portfolio.projekt2.models.DataLoader;
import portfolio.projekt2.models.Route;

public class boatshipmentApp extends Application {

  

public static void main(String[] args) { // main method
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
  public void start(Stage primaryStage) throws Exception {//start method of the application 
    System.out.println("Starting Boatshipment");
   //deletALl();
    //System.out.println("Database cleared");
    //load();
    //System.out.println("CSV loaded");
   // printAll();
    if (Database.isOK()) {//check if the database is OK
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

  
  public static void load() {//load the csv file
    DataLoader dataLoader = new DataLoader();
    dataLoader.loadCSV();
    dataLoader.Updatepaires();
  }

  public static void deletALl() {//delete all the data from the database
    deleteAllDates();
    deleteAllCitys();
    deleteAllRouts();
    deleteAllCityDateWithVid();
    deleteAllVesselCityWithDid();
    deleteAllDateVesselWithCid();
    deletAllVessesls();
  }

  public static void printAll() {//print all the data from the database
    VesselsDAO.getVessels().forEach(System.out::println);
    DateDAO.getDates().forEach(System.out::println);
    CityDAO.getCitys().forEach(System.out::println);
    RouteDAO.getRoutes().forEach(System.out::println);
    CityDateWithVidDAO.getCityDateWithVids().forEach(System.out::println);
    VesselCityWithDidDAO.getVesselCityWithDids().forEach(System.out::println);
    DateVesselWithCidDAO.getDateVesselWithCids().forEach(System.out::println);
  }

  
  public static void deletAllVessesls() {//delete all the vessels from the database
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


  public static void deleteAllDates() {//delete all the dates from the database
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

  public static void deleteAllCitys() {//delete all the citys from the database
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

  public static void deleteAllRouts() {//delete all the routes from the database
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

  
  public static void deleteAllCityDateWithVid() {//delete all the city date with vid from the database
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

  public static void deleteAllVesselCityWithDid() {//delete all the vessel city with did from the database
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

  public static void deleteAllDateVesselWithCid() {//delete all the date vessel with cid from the database
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
}
