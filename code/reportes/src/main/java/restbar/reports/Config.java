package restbar.reports;

import java.io.IOException;
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
	 */
	public static void loadConfig() {
    	try
    	{
			ClassLoader classLoader = App.class.getClassLoader();
	    	InputStream stream = classLoader.getResourceAsStream("config.properties");
			System.getProperties().load(stream);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		
	}
}
