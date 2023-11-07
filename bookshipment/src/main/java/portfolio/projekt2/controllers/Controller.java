package portfolio.projekt2.controllers;

import java.util.Optional;
import java.util.function.UnaryOperator;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import portfolio.projekt2.boatshipmentApp;
import portfolio.projekt2.dao.*;
import portfolio.projekt2.models.*;

public class Controller {

  public TableView<Route> exampleTable;
  public TableColumn<Route, Integer> ridColumn;
  public TableColumn<Route, String> startDidColumn;
  public TableColumn<Route, String> endDidColumn;
  public TableColumn<Route, String> startCidColumn;
  public TableColumn<Route, String> endCidColumn;
  public TableColumn<Route, String> rVidColumn;

  public Button editButton;
  public Button deleteButton;

  public void initialize(String startDid, String endDid, String startCid, String endCid, int cargoAmount) {
    SearchFunctions searchFunctions = new SearchFunctions();
    exampleTable.setItems(
      searchFunctions.Search(startDid, endDid, startCid, endCid, cargoAmount)
    );

    ridColumn.setCellValueFactory(new PropertyValueFactory<>("rid"));
    startDidColumn.setCellValueFactory(cellData -> {
      Route route = cellData.getValue();
      Optional<Date> dateOptional = DateDAO.getDate(route.getStartDid());

      if (dateOptional.isPresent()) {
        Date date = dateOptional.get();
        return new SimpleStringProperty(date.getDateV());
      } else {
        return new SimpleStringProperty("");
      }
    });
    endDidColumn.setCellValueFactory(cellData -> {
      Route route = cellData.getValue();
      Optional<Date> dateOptional = DateDAO.getDate(route.getEndDid());

      if (dateOptional.isPresent()) {
        Date date = dateOptional.get();
        return new SimpleStringProperty(date.getDateV());
      } else {
        return new SimpleStringProperty("");
      }
    });
    startCidColumn.setCellValueFactory(cellData -> {
      Route route = cellData.getValue();
      Optional<City> cityOptional = CityDAO.getCity(route.getStartCid());

      if (cityOptional.isPresent()) {
        City city = cityOptional.get();
        return new SimpleStringProperty(city.getCityV());
      } else {
        return new SimpleStringProperty("");
      }
    });
    endCidColumn.setCellValueFactory(cellData -> {
      Route route = cellData.getValue();
      Optional<City> cityOptional = CityDAO.getCity(route.getEndCid());

      if (cityOptional.isPresent()) {
        City city = cityOptional.get();
        return new SimpleStringProperty(city.getCityV());
      } else {
        return new SimpleStringProperty("");
      }
    });
    rVidColumn.setCellValueFactory(cellData -> {
      Route route = cellData.getValue();
      Optional<Vessel> vesselOptional = VesselsDAO.getVessel(route.getRVid());

      if (vesselOptional.isPresent()) {
        Vessel vessel = vesselOptional.get();
        return new SimpleStringProperty(
          vessel.getVesselName() +
          ", Avail Cap: " +
          vessel.getAvailableCapacity()
        );
      } else {
        return new SimpleStringProperty("");
      }
    });

    editButton
      .disableProperty()
      .bind(
        Bindings.isEmpty(exampleTable.getSelectionModel().getSelectedItems())
      );
    deleteButton
      .disableProperty()
      .bind(
        Bindings.isEmpty(exampleTable.getSelectionModel().getSelectedItems())
      );
  }

  public void handleExitButtonClicked(ActionEvent event) {
    Platform.exit();
    event.consume();
  }

  public void Search(ActionEvent event) {
    
    Dialog<Route> searchDialog = createSearchDialog(null);
    Optional<Route> res = searchDialog.showAndWait();
    res.ifPresent(
      route -> {
        initialize(null, null, null, null, 0);
      }
    );
      
    event.consume();
  }

  

  private Dialog<Route> createSearchDialog(Route route) {
    //create the dialog itself
    Dialog<Route> dialog = new Dialog<>();
    dialog.setTitle("Add Dialog");
    if (route == null) {
      dialog.setHeaderText("Write in the filds to search for a voyage with aviabel space"); 
    } else {
      dialog.setHeaderText("Edit a database record");
    }
    dialog
      .getDialogPane()
      .getButtonTypes()
      .addAll(ButtonType.OK, ButtonType.CANCEL);
    Stage dialogWindow = (Stage) dialog.getDialogPane().getScene().getWindow();

    //create the form for the user to fill in
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));
    TextField DepartureDate = new TextField();
    DepartureDate.setPromptText("Departure Date");
    TextField ArrivalDate = new TextField();
    ArrivalDate.setPromptText("Arrival Date");
    TextField DepartureCity = new TextField();
    DepartureCity.setPromptText("Departure City");
    TextField ArrivalCity = new TextField();
    ArrivalCity.setPromptText("Arrival City");

    TextField AmountOfGargo = new TextField();
    AmountOfGargo.setPromptText("Amount Of Gargo");

    grid.add(new Label("Departure Date:"), 0, 0);
    grid.add(DepartureDate, 1, 0);
    grid.add(new Label("Arrival Date:"), 0, 1);
    grid.add(ArrivalDate, 1, 1);
    grid.add(new Label("Departure City:"), 0, 2);
    grid.add(DepartureCity, 1, 2);
    grid.add(new Label("Arrival City:"), 0, 3);
    grid.add(ArrivalCity, 1, 3);
    grid.add(new Label("Amount Of Gargo:"), 0, 4);
    grid.add(AmountOfGargo, 1, 4);
    dialog.getDialogPane().setContent(grid);

    //disable the OK button if the fields haven't been filled in
    dialog
      .getDialogPane()
      .lookupButton(ButtonType.OK)
      .disableProperty()
      .bind(
        Bindings
          .createBooleanBinding(
            () -> AmountOfGargo.getText().isEmpty(),
            AmountOfGargo.textProperty()
          )
      );

    //ensure only numeric input (integers) in age text field
    UnaryOperator<TextFormatter.Change> numberValidationFormatter = change -> {
      if (change.getText().matches("\\d+") || change.getText().equals("")) {
        return change; //if change is a number or if a deletion is being made
      } else {
        change.setText(""); //else make no change
        change.setRange( //don't remove any selected text either.
          change.getRangeStart(),
          change.getRangeStart()
        );
        return change;
      }
    };
    AmountOfGargo.setTextFormatter(
      new TextFormatter<Object>(numberValidationFormatter)
    );

    //make sure the dialog returns a Route if it's available
    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == ButtonType.OK) {
        int id = -1;
        if (route != null) id = route.getRid();
        return new Route(
          route.getStartDid(),
          route.getEndDid(),
          route.getStartCid(),
          route.getEndCid(),
          route.getRVid(),
          id
        );
      }
      return null;
    });

    //if a record is supplied, use it to fill in the fields automatically
    if (route != null) {
      DepartureDate.setText(
        DateDAO.getDate(route.getStartDid()).get().getDateV()
      );
      ArrivalDate.setText(DateDAO.getDate(route.getEndDid()).get().getDateV());
      DepartureCity.setText(
        CityDAO.getCity(route.getStartCid()).get().getCityV()
      );
      ArrivalCity.setText(CityDAO.getCity(route.getEndCid()).get().getCityV());
      AmountOfGargo.setText(
        VesselsDAO.getVessel(route.getRVid()).get().getAvailableCapacity() + ""
      );
    }

    return dialog;
  }
}
