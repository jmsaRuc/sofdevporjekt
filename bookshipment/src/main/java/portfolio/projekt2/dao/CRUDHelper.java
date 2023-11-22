/*
 * CRUDHelper.java
 * Author: Eden-Rump
 * Purpose: Helper class for CRUD operation
 * 
 * I got this code from this articel: https://edencoding.com/connect-javafx-with-sqlite/
 */


package portfolio.projekt2.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDHelper {//

  public static Object read(
    String tableName,
    String fieldName,
    int fieldDataType,
    String indexFieldName,
    int indexDataType,
    Object index
  ) {
    // Build the query string
    StringBuilder queryBuilder = new StringBuilder("Select ");
    queryBuilder.append(fieldName);
    queryBuilder.append(" from ");
    queryBuilder.append(tableName);
    queryBuilder.append(" where ");
    queryBuilder.append(indexFieldName);
    queryBuilder.append(" = ");
    queryBuilder.append(convertObjectToSQLField(index, indexDataType));
    // Connect to the database
    try (Connection connection = Database.connect()) {
      // Prepare the statement
      PreparedStatement statement = connection.prepareStatement(
        queryBuilder.toString()
      );
      // Execute the query
      try (ResultSet rs = statement.executeQuery()) {
        // Get the first result
        rs.next();
        // Return the field
        switch (fieldDataType) {
          case Types.INTEGER:
            return rs.getInt(fieldName);
          case Types.VARCHAR:
            return rs.getString(fieldName);
          default:
            throw new IllegalArgumentException(
              "Index type " +
              indexDataType +
              " from sql.Types is not yet supported."
            );
        }
      }
    } catch (SQLException exception) {
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() +
          ": Could not fetch from " +
          tableName +
          " by index " +
          index +
          " and column " +
          fieldName
        );
      return null;
    }
  }

  public static long update(
    String tableName,
    String[] columns,
    Object[] values,
    int[] types,
    String indexFieldName,
    int indexDataType,
    Object index
  ) {
    //this is the update method for the database 
    int number = Math.min(
      Math.min(columns.length, values.length),
      types.length
    );
    StringBuilder queryBuilder = new StringBuilder(
      "UPDATE " + tableName + " SET "
    );
    for (int i = 0; i < number; i++) {
      queryBuilder.append(columns[i]);
      queryBuilder.append(" = ");
      queryBuilder.append(convertObjectToSQLField(values[i], types[i]));
      if (i < number - 1) queryBuilder.append(", ");
    }
    queryBuilder.append(" WHERE ");
    queryBuilder.append(indexFieldName);
    queryBuilder.append(" = ");
    queryBuilder.append(convertObjectToSQLField(index, indexDataType));
    try (Connection conn = Database.connect()) {
      PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString());
      return pstmt.executeUpdate(); //number of affected rows
    } catch (SQLException ex) {
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() + ": Could not add to database update"
        );
        System.out.println("wup wup");
      return -1;
    }
  }

  public static long create(
    String tableName,
    String[] columns,
    Object[] values,
    int[] types
  ) { 
    //this is the create method for the database 
    
    int number = Math.min(
      Math.min(columns.length, values.length),
      types.length
    );
    StringBuilder queryBuilder = new StringBuilder(
      "INSERT INTO " + tableName + " ("
    );
    for (int i = 0; i < number; i++) {
      queryBuilder.append(columns[i]);
      if (i < number - 1) queryBuilder.append(", ");
    }
    queryBuilder.append(") ");
    queryBuilder.append(" VALUES (");
    for (int i = 0; i < number; i++) {
      switch (types[i]) {
        case (Types.VARCHAR):
          queryBuilder.append("'");
          queryBuilder.append((String) values[i]);
          queryBuilder.append("'");
          break;
        case (Types.INTEGER):
          queryBuilder.append((int)values[i]);
          break;
      }
      if (i < number - 1) queryBuilder.append(", ");
    }
    queryBuilder.append(");");
    try (Connection conn = Database.connect()) {
      PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString());
      int affectedRows = pstmt.executeUpdate();
      
      // check the affected rows
      if (affectedRows > 0) {
        // get the ID back
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
          if (rs.next()) {
            return rs.getLong(1);
          }
        }
      }
    } catch (SQLException ex) {
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() + ": Could not add vessel to database (create)"
          
        );
        
      return -1;
    }
    return -1;
  }

  public static int delete(String tableName, int id, String idName) {
    //this is the delete method for the database
    String sql = "DELETE FROM " + tableName + " WHERE "+ idName +" = ?";
    try (Connection conn = Database.connect()) {
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, id);
      return pstmt.executeUpdate();
    } catch (SQLException e) {
      Logger
        .getAnonymousLogger()
        .log(
          Level.SEVERE,
          LocalDateTime.now() +
          ": Could not delete from " +
          tableName +
          " by "+idName+" " +
          id +
          " because " +
          e.getCause()
        );
      return -1;
    }
  }

  private static String convertObjectToSQLField(Object value, int type) {
    //this is the method to convert the object to a sql field
    StringBuilder queryBuilder = new StringBuilder();
    switch (type) {
      case Types.VARCHAR:
        queryBuilder.append("'");
        queryBuilder.append(value);
        queryBuilder.append("'");
        break;
      case Types.INTEGER:
        queryBuilder.append(value);
        break;
      default:
        throw new IllegalArgumentException(
          "Index type " + type + " from sql.Types is not yet supported."
        );
    }
    return queryBuilder.toString();
  }
}
