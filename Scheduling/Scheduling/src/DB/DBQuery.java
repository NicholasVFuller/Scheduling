package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A Class for setting and getting SQL statements.
 */
public class DBQuery
{
    private static PreparedStatement statement;

    /**
     * Sets the Prepared Statement for this query.
     *
     * @param connection the connection to the database.
     * @param sqlStatement the SQL Statement to execute.
     * @throws SQLException
     */
    public static void setPreparedStatement(Connection connection, String sqlStatement) throws SQLException
    {
        statement = connection.prepareStatement(sqlStatement);
    }

    /**
     * Gets and returns the statement of this query.
     *
     * @return the statement of this query.
     */
    public static PreparedStatement getPreparedStatement()
    {
        return statement;
    }

}
