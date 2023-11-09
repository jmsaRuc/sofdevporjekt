package portfolio.projekt2.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import portfolio.projekt2.boatshipmentApp;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

  private static final String Location =
    boatshipmentApp.class.getResource("/portfolio/projekt2/routes/routes1.csv")
      .getPath();

  public static String[][] ReadRoutes(){
    List <String> routesList = new ArrayList<String>();

    String delimiter = ",";

    String currentLine = "";

    int amoutOfFilds = 0;

    try{
      FileReader fr = new FileReader(Location);
      BufferedReader br = new BufferedReader(fr);

      while ((currentLine = br.readLine()) != null){
        routesList.add(currentLine.replaceAll("\'", ""));
        String[] a = currentLine.split(delimiter);
        amoutOfFilds = a.length;
      }
      
      int amountOfRoutes = routesList.size();

      String arrayToReturn[][] = new String[amountOfRoutes][amoutOfFilds];
      String[] tempArray;

      for (int i = 0; i < amountOfRoutes; i++){
        tempArray = routesList.get(i).split(delimiter);
        for (int j = 0; j < tempArray.length; j++){
          arrayToReturn[i][j] = tempArray[j];
        }
      }
      br.close();
      return arrayToReturn;

    }catch(Exception e){
      System.out.println(e);
      
      return null;
    }
    
  }

  public static HashSet<String> ReadSpecific(int n){
    HashSet<String> hashSet = new HashSet<String>();
    try{
      BufferedReader in = new BufferedReader(new FileReader(Location));
      while(true){
        String s = in.readLine();
        if(s == null) break;
        String[] a = s.split(",");
        a[n] = a[n].replaceAll("\'", "");
        hashSet.add(a[n]);
      }
      in.close();
    }catch(Exception e){
      System.out.println(e);
    }
    return hashSet;
  }

 
    
}
