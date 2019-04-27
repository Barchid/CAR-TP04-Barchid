package car.tp4.managers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

/**
 * @author Sami BARCHID
 *
 *         Service used retrieve the configuration properties of the application
 *         that are stored in the config.properties file.
 */
@Singleton
@Local
public class PropertiesManager {
	private static final String PATH = "";
	private Properties properties;

	public PropertiesManager() {
		try (InputStream input = new FileInputStream(PATH)) {
			this.properties = new Properties();
			// load a properties file
			this.properties.load(input);
		} catch (IOException ex) {
			// TODO remove for prod
			ex.printStackTrace();
		}
	}

	/**
	 * Gets the client ID in the config properties file (used for Oauth login)
	 * 
	 * @return the client ID
	 */
	public String getClientId() {
		return this.properties.getProperty("ClientId");
	}

	/**
	 * Gets the client secret in the config properties file (used for Oauth login)
	 * 
	 * @return the oauth2 client secret in the properties file
	 */
	public String getClientSecret() {
		return this.properties.getProperty("ClientSecret");
	}
}
