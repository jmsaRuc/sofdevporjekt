package portfolio.projekt2.controllers;


import portfolio.projekt2.boatshipmentApp;
import portfolio.projekt2.models.*;
import portfolio.projekt2.dao.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class gui {
  
  public static void showAddBoatDialog() {


    class Gui {
      public static void main(String[] args) {
        boatshipmentApp.main(args);
      }

      public static void showAddBoatDialog() {
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label typeLabel = new Label("Type:");
        TextField typeField = new TextField();
        Label lengthLabel = new Label("Length:");
        TextField lengthField = new TextField();
        Label widthLabel = new Label("Width:");
        TextField widthField = new TextField();
        Label draftLabel = new Label("Draft:");
        TextField draftField = new TextField();
        Label weightLabel = new Label("Weight:");
        TextField weightField = new TextField();
        Label capacityLabel = new Label("Capacity:");
        TextField capacityField = new TextField();
        Label ownerLabel = new Label("Owner:");
        TextField ownerField = new TextField();

        GridPane grid = new GridPane();
        grid.add(nameLabel, 1, 1);
        grid.add(nameField, 2, 1);
        grid.add(typeLabel, 1, 2);
        grid.add(typeField, 2, 2);
        grid.add(lengthLabel, 1, 3);
        grid.add(lengthField, 2, 3);
        grid.add(widthLabel, 1, 4);
        grid.add(widthField, 2, 4);
        grid.add(draftLabel, 1, 5);
        grid.add(draftField, 2, 5);
        grid.add(weightLabel, 1, 6);
        grid.add(weightField, 2, 6);
        grid.add(capacityLabel, 1, 7);
        grid.add(capacityField, 2, 7);
        grid.add(ownerLabel, 1, 8);
        grid.add(ownerField, 2, 8);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Boat");
        dialog.getDialogPane().setContent(grid);

        ButtonType addButton = new ButtonType("Search", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton);

        dialog.setResultConverter(dialogButton -> {
          if (dialogButton == addButton) {
            String name = nameField.getText();
            String type = typeField.getText();
            double length = Double.parseDouble(lengthField.getText());
            double width = Double.parseDouble(widthField.getText());
            double draft = Double.parseDouble(draftField.getText());
            double weight = Double.parseDouble(weightField.getText());
            int capacity = Integer.parseInt(capacityField.getText());
            String owner = ownerField.getText();

          
          }
          return null;
        });

        dialog.showAndWait();
      }
    }
    boatshipmentApp.main(new String[0]);
  }
  
// https://www.maersk.com/schedules/pointToPoint

// type in city name from



//type in city name to 

//date type in departing or arraiveing

//container type amount

//press search 


//when press search tjek at date is a date, and show a tabel of vesses comming from a function, 
}
