package portfolio.projekt2;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import portfolio.projekt2.dao.Database;
import portfolio.projekt2.dao.VesselsDAO;



public class boatshipmentApp {

  
 
  public static void main(String[] args) {
    
    System.out.println(Database.isOK());
    VesselsDAO.insertVessel("titanic", 100, 200, 100, 0);
    System.out.println(Database.isOK());
  }

  

  /*public static void testVessels() {
    Vessel vessel = new Vessel("titanic", 100, 200, 100, 0, 1);
    VesselsDAO.insertVessel(
      vessel.getVesselName(),
      vessel.getUsedCapacity(),
      vessel.getMaxCapacity(),
      vessel.getAvailableCapacity(),
      vessel.getCityDateWithVid()
    );
    VesselsDAO.update(vessel);
    if (!Database.isOK()) {
      System.out.println("Database is not OK2");
      System.exit(1);
    }
    System.out.println("Database is OK2");

    VesselsDAO.getVessels().forEach(System.out::println);

    VesselsDAO.delete("Vessels", 1);

    if (!Database.isOK()) {
      System.out.println("Database is not OK3");
      System.exit(1);
    }
    System.out.println("Database is OK3");
  }
  */
}
