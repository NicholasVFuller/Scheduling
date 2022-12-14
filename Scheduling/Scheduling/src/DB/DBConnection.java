package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class provides a connection to the database for this application.
 */
public class DBConnection
{
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ06DTc";

    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName + "?connectionTimeZone=SERVER";

    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";

    private static final String userName = "U06DTc";

    private static Connection connection = null;

    /**
     * Starts and returns the active connection to the database.
     *
     * @return the active connection to the database.
     */
    public static Connection startConnection()
    {
        try
        {
            Class.forName(MYSQLJDBCDriver);
            connection = DriverManager.getConnection(jdbcURL, userName, "53688732687");

            System.out.println("Connection Successful!");
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Gets and returns the connection to the database.
     *
     * @return the connection to the database.
     */
    public static Connection getConnection()
    {
        return connection;
    }

    /**
     * Closes the connection to the database.
     */
    public static void closeConnection()
    {
        try
        {
            connection.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
