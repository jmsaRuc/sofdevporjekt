package portfolio.projekt2.controllers;

import portfolio.projekt2.boatshipmentApp;
import portfolio.projekt2.dao.VesselsDAO;
import portfolio.projekt2.models.Vessel;
import javafx.scene.control.*;

public class Controller {
    public TableView<Vessel> exampleTable;
    public TableColumn<Vessel, Integer> vidColumn;
    public TableColumn<Vessel, String> firstNameColumn;
    public TableColumn<Vessel, String> lastNameColumn;
    public TableColumn<Vessel, Integer> ageColumn;
}
