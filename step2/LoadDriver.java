import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

// assume that conn is an already created JDBC connection (see previous examples)
public class LoadDriver {
    public static void main(String[] args) {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = null;
            try {
                conn =
                DriverManager.getConnection("jdbc:mysql://localhost/my_db?" +
                "user=root&password=88888888");

                // Do something with the Connection

                Statement stmt = null;
                ResultSet rs = null;

                try {
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM Account");

                    // or alternatively, if you don't know ahead of time that
                    // the query will be a SELECT...

                    if (stmt.execute("SELECT * FROM Account")) {
                        rs = stmt.getResultSet();
                    }

                    // Now do something with the ResultSet ....

                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnsNumber = rsmd.getColumnCount();
                    while (rs.next()) {
                        for (int i = 1; i <= columnsNumber; i++) {
                            if (i > 1) System.out.print(",  ");
                            String columnValue = rs.getString(i);
                            System.out.print(columnValue + " " + rsmd.getColumnName(i));
                        }
                        System.out.println("");
                    }
                }
                catch (SQLException ex){
                    // handle any errors
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                }
                finally {
                    // it is a good idea to release
                    // resources in a finally{} block
                    // in reverse-order of their creation
                    // if they are no-longer needed

                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException sqlEx) { } // ignore

                        rs = null;
                    }

                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (SQLException sqlEx) { } // ignore

                        stmt = null;
                    }
                }

            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        } catch (Exception ex) {
            // handle the error
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}