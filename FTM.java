import java.sql.*;

public class FTM{
  private static final String DROP_FUNDS = "DROP TABLE IF EXISTS funds";

  private static final String CREATE_FUNDS = "CREATE TABLE funds AS\n" +
      "SELECT DISTINCT csrc.name AS from, ctgt.name AS to\n" +
      "FROM transfer, depositor dsrc, depositor dtgt, customer csrc, customer ctgt\n" +
      "WHERE transfer.src = dsrc.ano\n" +
      "AND transfer.tgt = dtgt.ano\n" +
      "AND dsrc.cname = csrc.name\n" +
      "AND dtgt.cname = ctgt.name";

  private static final String DROP_INFLUENCE = "DROP TABLE IF EXISTS influence";

  private static final String CREATE_INFLUENCE = "CREATE TABLE influence\n" +
  "(\n" +
    "\"from\"   TEXT,\n" +
    "\"to\"    TEXT,\n" +
    "FOREIGN KEY   (\"from\") REFERENCES customer,\n" +
    "FOREIGN KEY   (\"to\") REFERENCES customer\n" +
  ")";

  private static final String DROP_DELTA = "DROP TABLE IF EXISTS delta";

  private static final String CREATE_DELTA = "CREATE TABLE delta\n" +
  "(\n" +
    "\"from\"   TEXT,\n" +
    "\"to\"    TEXT,\n" +
    "FOREIGN KEY   (\"from\") REFERENCES customer,\n" +
    "FOREIGN KEY   (\"to\") REFERENCES customer\n" +
  ")";

  private static final String DROP_OLD_INFLUENCE = "DROP TABLE IF EXISTS old_influence";

  private static final String CREATE_OLD_INFLUENCE = "CREATE TABLE old_influence\n" +
  "(\n" +
    "\"from\"   TEXT,\n" +
    "\"to\"    TEXT,\n" +
    "FOREIGN KEY   (\"from\") REFERENCES customer,\n" +
    "FOREIGN KEY   (\"to\") REFERENCES customer\n" +
  ")";

  private static final String DELETE_INFLUENCE = "DELETE FROM influence";

  private static final String INITIALIZE_INFLUENCE = "INSERT INTO influence\n" +
  "SELECT DISTINCT * FROM funds";

  private static final String DELETE_DELTA = "DELETE FROM delta";

  private static final String INITIALIZE_DELTA = "INSERT INTO delta\n" +
  "SELECT DISTINCT * FROM funds";

  private static final String COUNT_DELTA = "SELECT COUNT(*) FROM delta";

  private static final String DELETE_OLD_INFLUENCE = "DELETE FROM old_influence";

  private static final String INSERT_OLD_INFLUENCE = "INSERT INTO old_influence\n" +
  "SELECT DISTINCT * FROM influence";

  private static final String UPDATE_INFLUENCE = "INSERT INTO influence\n" +
  "SELECT DISTINCT * FROM old_influence\n" +
  "UNION(\n" +
    "SELECT DISTINCT x.from, y.to\n" +
    "FROM delta x, old_influence y\n" +
    "WHERE x.to = y.from)\n" +
  "UNION(" +
    "SELECT DISTINCT x.from, y.to\n" +
    "FROM old_influence x, delta y\n" +
    "WHERE x.to = y.from\n" +
  ")";

  private static final String UPDATE_DELTA = "INSERT INTO delta\n" +
  "(SELECT DISTINCT * FROM influence)\n" +
  "EXCEPT\n" +
  "(SELECT DISTINCT * FROM old_influence)";



  public static void main (String[] args){

    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    String url = "jdbc:postgresql://localhost:5432/PA2Bank";
    Connection con = null;
    try {
      con = DriverManager.getConnection(url, args[0] ,args[1]);
      System.out.println("Connecting Database...");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    Statement stmt = null;
    ResultSet rtst = null;
    try{
      stmt = con.createStatement();
      stmt.executeUpdate(DROP_FUNDS);
      stmt.executeUpdate(CREATE_FUNDS);
      stmt.executeUpdate(DROP_INFLUENCE);
      stmt.executeUpdate(CREATE_INFLUENCE);
      stmt.executeUpdate(DROP_DELTA);
      stmt.executeUpdate(CREATE_DELTA);
      stmt.executeUpdate(DROP_OLD_INFLUENCE);
      stmt.executeUpdate(CREATE_OLD_INFLUENCE);
      stmt.executeUpdate(DELETE_INFLUENCE);
      stmt.executeUpdate(INITIALIZE_INFLUENCE);
      stmt.executeUpdate(DELETE_DELTA);
      stmt.executeUpdate(INITIALIZE_DELTA);
      rtst = stmt.executeQuery(COUNT_DELTA);
      int count = 0;
      if(rtst.next()){
        count = rtst.getInt(1);
        System.out.println("count: " + count);
      }
      while(count > 0){
        stmt.executeUpdate(DELETE_OLD_INFLUENCE);
        stmt.executeUpdate(INSERT_OLD_INFLUENCE);
        System.out.println("Computing...");
        stmt.executeUpdate(DELETE_INFLUENCE);
        stmt.executeUpdate(UPDATE_INFLUENCE);
        stmt.executeUpdate(DELETE_DELTA);
        stmt.executeUpdate(UPDATE_DELTA);
        rtst = stmt.executeQuery(COUNT_DELTA);
        if(rtst.next()){
          count = rtst.getInt(1);
          System.out.println("count: " + count);
        }
      }

      stmt.executeUpdate(DROP_FUNDS);
      stmt.executeUpdate(DROP_DELTA);
      stmt.executeUpdate(DROP_OLD_INFLUENCE);
    }catch (SQLException e) {
      e.printStackTrace();
    }



    try{
      if(rtst != null)
        rtst.close();
      if(stmt != null)
        stmt.close();
      if(con != null)
        con.close();
      System.out.println("Close Database connection");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
