package restbar.reports.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Visual FoxPro Connection
 * Needs the Visual FoxPro ODBC Driver installed:
 * http://www.topol.eu/articles/download%20odbc
 *
 */
public class VFPConnection implements DBConnection {
	private static Log log = LogFactory.getLog(VFPConnection.class);
	private final String connectionString;
	
	/**
	 * Default Constructor
	 * @throws ClassNotFoundException
	 */
	public VFPConnection() throws ClassNotFoundException {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		String driver = System.getProperty("database.driver");
		String type = System.getProperty("database.type");
		String path = System.getProperty("database.path");
		
        this.connectionString ="jdbc:odbc:Driver={" + driver + "};SourceType=" + type + ";SourceDB=" + path;
        
        log.info(MessageFormat.format("Connection string: {0}", connectionString));
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(this.connectionString);
	}

}
