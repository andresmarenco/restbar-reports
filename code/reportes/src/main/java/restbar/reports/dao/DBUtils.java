package restbar.reports.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Database Utils
 * @author amarenco
 *
 */
public class DBUtils {
	
	private static Log log = LogFactory.getLog(DBUtils.class);
	
	/**
	 * Closes the current {@link Connection}
	 * @param connection Connection instance
	 */
	public static void close(Connection connection) {
		if(connection != null) {
			try
			{
				connection.close();
			}
			catch(SQLException ex) {
				log.error(ex);
			}
		}
	}
	
	
	
	/**
	 * Closes the current {@link Statement}
	 * @param stmt Statement instance
	 */
	public static void close(Statement stmt) {
		if(stmt != null) {
			try
			{
				stmt.close();
			}
			catch(SQLException ex) {
				log.error(ex);
			}
		}
	}
	
	
	
	/**
	 * Closes the current {@link ResultSet}
	 * @param rs ResultSet instance
	 */
	public static void close(ResultSet rs) {
		if(rs != null) {
			try
			{
				rs.close();
			}
			catch(SQLException ex) {
				log.error(ex);
			}
		}
	}

}
