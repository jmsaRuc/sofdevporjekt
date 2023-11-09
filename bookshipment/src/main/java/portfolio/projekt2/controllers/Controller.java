package portfolio.projekt2.controllers;

import java.util.Optional;
import java.util.function.UnaryOperator;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
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

  public Button bookButton;
  public Button searchButton;

  public String[] savedSSearcParams = new String[4];
  public int savedISearchParams;

  public void initialize() {
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

    bookButton
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
    SearchFunctions searchFunctions = new SearchFunctions();
    Dialog<String> dialog = createSearchDialog();
    Optional<String> result = dialog.showAndWait();

    if (result.isPresent()) {
      String[] a = result.get().split(",");
      exampleTable.setItems(
        searchFunctions.Search(a[0], a[1], a[2], a[3], Integer.parseInt(a[4]))
      );

      savedSSearcParams = new String[] { a[0], a[1], a[2], a[3] };
      savedISearchParams = Integer.parseInt(a[4]);
    }

    exampleTable.refresh();
    event.consume();
  }

  public void book(ActionEvent event) {
    if (exampleTable.getSelectionModel().getSelectedItems().size() != 1) {
      System.err.println("Erro when booking: more than one row selected");
    } else {
      Route route = exampleTable.getSelectionModel().getSelectedItem();

      Vessel vessel = VesselsDAO.getVessel(route.getRVid()).get();

      Dialog<Vessel> dialog = createVesselDialog(vessel);
      Optional<Vessel> result = dialog.showAndWait();
      result.ifPresent(newVessel -> {
        if (newVessel.getCityDateWithVidIndex().equals("s")) {
          Vessel newVessel1 = new Vessel(
            newVessel.getVesselName(),
            newVessel.getUsedCapacity(),
            newVessel.getMaxCapacity(),
            newVessel.getAvailableCapacity(),
            newVessel.getCityDateWithVidIndex(),
            vessel.getVid()
          );
          VesselsDAO.update(newVessel1);
        } else {
          int id = 1 + VesselsDAO.getVessels().size();
          Vessel newVessel2 = new Vessel(
            newVessel.getVesselName(),
            newVessel.getUsedCapacity(),
            newVessel.getMaxCapacity(),
            newVessel.getAvailableCapacity(),
            "s",
            id
          );
          VesselsDAO.insertVessel(
            newVessel2.getVesselName(),
            newVessel2.getUsedCapacity(),
            newVessel2.getMaxCapacity(),
            newVessel2.getAvailableCapacity(),
            newVessel2.getCityDateWithVidIndex()
          );

          Route newRoute = new Route(
            route.getStartDid(),
            route.getEndDid(),
            route.getStartCid(),
            route.getEndCid(),
            id,
            route.getRid()
          );
          RouteDAO.update(newRoute);
        }
      });
    }

    exampleTable.setItems(
      new SearchFunctions()
        .Search(
          savedSSearcParams[0],
          savedSSearcParams[1],
          savedSSearcParams[2],
          savedSSearcParams[3],
          savedISearchParams
        )
    );

    exampleTable.refresh();
    event.consume();
  }

  private Dialog<String> createSearchDialog() {
    //create the dialog itself

    Dialog<String> dialog = new Dialog<>();
    dialog.setTitle("Add Dialog");
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

    TextField AmountOfGargoTo = new TextField();
    AmountOfGargoTo.setPromptText("Amount Of Gargo");

    grid.add(new Label("Departure Date:"), 0, 0);
    grid.add(DepartureDate, 1, 0);
    grid.add(new Label("Arrival Date:"), 0, 1);
    grid.add(ArrivalDate, 1, 1);
    grid.add(new Label("Departure City:"), 0, 2);
    grid.add(DepartureCity, 1, 2);
    grid.add(new Label("Arrival City:"), 0, 3);
    grid.add(ArrivalCity, 1, 3);
    grid.add(new Label("Amount Of Gargo:"), 0, 4);
    grid.add(AmountOfGargoTo, 1, 4);
    dialog.getDialogPane().setContent(grid);

    //disable the OK button if the fields haven't been filled in
    dialog
      .getDialogPane()
      .lookupButton(ButtonType.OK)
      .disableProperty()
      .bind(
        Bindings.createBooleanBinding(
          () -> AmountOfGargoTo.getText().isEmpty(),
          AmountOfGargoTo.textProperty()
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
    AmountOfGargoTo.setTextFormatter(
      new TextFormatter<Object>(numberValidationFormatter)
    );

    //make sure the dialog returns a Route if it's available
    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == ButtonType.OK) {
        return new String(
          DepartureDate.getText() +
          "," +
          ArrivalDate.getText() +
          "," +
          DepartureCity.getText() +
          "," +
          ArrivalCity.getText() +
          "," +
          AmountOfGargoTo.getText()
        );
      }
      return null;
    });

    //if a record is supplied, use it to fill in the fields automatically

    return dialog;
  }

  private Dialog<Vessel> createVesselDialog(Vessel vessel) {
    //create the dialog itself
    Dialog<Vessel> dialog = new Dialog<>();
    dialog.setTitle("Add Dialog");
    dialog.setHeaderText("Book Selected Voyage, write amount of cargo");
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
    TextField AmountOfGargoTo = new TextField();
    AmountOfGargoTo.setPromptText("Amount of cargo to book");

    grid.add(new Label("Amount of cargo to book:"), 0, 0);
    grid.add(AmountOfGargoTo, 1, 0);
    dialog.getDialogPane().setContent(grid);
    //disable the OK button if the fields haven't been filled in

    dialog
      .getDialogPane()
      .lookupButton(ButtonType.OK)
      .disableProperty()
      .bind(
        Bindings
          .createBooleanBinding(
            () -> AmountOfGargoTo.getText().trim().isEmpty(),
            AmountOfGargoTo.textProperty()
          )
          .or(
            Bindings.createBooleanBinding(
              () ->
                Integer.parseInt(AmountOfGargoTo.getText().trim() + "0") >
                vessel.getAvailableCapacity() *
                10,
              AmountOfGargoTo.textProperty()
            )
          )
      );

    // make s

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
    AmountOfGargoTo.setTextFormatter(
      new TextFormatter<Object>(numberValidationFormatter)
    );
    //make sure the dialog returns a Vessel if it's available

    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == ButtonType.OK) {
        int id = -1;
        if (
          vessel != null &&
          Integer.parseInt(AmountOfGargoTo.getText()) >
          vessel.getAvailableCapacity()
        ) id = vessel.getVid();

        return new Vessel(
          vessel.getVesselName(),
          vessel.getUsedCapacity() +
          Integer.parseInt(AmountOfGargoTo.getText()),
          vessel.getMaxCapacity(),
          vessel.getAvailableCapacity() -
          Integer.parseInt(AmountOfGargoTo.getText()),
          vessel.getCityDateWithVidIndex(),
          id
        );
      }
      return null;
    });

    //if a record is supplied, use it to fill in the fields automatically

    return dialog;
  }
}
