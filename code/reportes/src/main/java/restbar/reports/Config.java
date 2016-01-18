package restbar.reports;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Configuration loader
 * @author amarenco
 *
 */
public class Config {
	private static Log log = LogFactory.getLog(Config.class);
	
	/**
	 * Loads the configuration file
	 * @throws ReportsException 
	 */
	public static void loadConfig() throws ReportsException {
    	try
    	{
			ClassLoader classLoader = Config.class.getClassLoader();
	    	InputStream stream = classLoader.getResourceAsStream("config.properties");
			System.getProperties().load(stream);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ReportsException(ReportsException.NO_CONFIG, "Could not read configuration file");
		}
		
	}
}
