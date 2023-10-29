module portfolio.projekt2 {
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires javafx.controls;
    
    opens portfolio.projekt2.models to javafx.base;
    //opens portfolio.projekt2.controllers to javafx.fxml;
    exports portfolio.projekt2;  
}
