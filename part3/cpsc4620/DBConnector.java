package cpsc4620;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnector {

    // enter your user name here
    private static String user = "admin";
    // enter your password here
    private static String password = "password";
    // enter your database name here
    private static String database_name = "Pizzeria";
    // Do not change the port. 3306 is the default MySQL port
    private static String url = "first-database-sp.cfcapmvvkhye.us-east-1.rds.amazonaws.com";
    private static Connection conn;


    /**
     * This function will handle the connection to the database
     *
     * @return true if the connection was successfully made
     * @throws SQLException
     * @throws IOException
     */
    public static Connection make_connection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load the driver");

            System.out.println("Message     : " + e.getMessage());

            return null;
        }

        conn = DriverManager.getConnection("" + "/" + database_name, user, password);
        return conn;
    }
}
