package restbar.reports.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface for the database connection
 * @author amarenco
 *
 */
public interface DBConnection {
	/**
	 * Creates a new connection to the database
	 * @return Open {@link Connection}
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException;
}
