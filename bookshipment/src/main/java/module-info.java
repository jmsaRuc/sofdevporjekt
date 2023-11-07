module portfolio.projekt2 {
    requires javafx.controls;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    
    opens portfolio.projekt2.models to javafx.base;
    opens portfolio.projekt2.controllers to javafx.base;
    exports portfolio.projekt2;  
}
