package me.manzhos.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The {@code PropertiesReader} class provides methods to read values from a properties file.
 * This class is designed to read configuration values from an "environment.properties" file.
 *
    * <p>Example usage:</p>
    * <pre>{@code
 * PropertiesReader reader = new PropertiesReader();
 * String value = reader.getValueFromConfig("key");
 * }</pre>
        */
public class PropertiesReader {

    private File environment = new File("environment.properties").getCanonicalFile();
    private Properties properties = new Properties();

    /**
     * Constructs a {@code PropertiesReader} object and initializes the properties file.
     *
     * @throws IOException if an I/O error occurs while getting the canonical file
     */
    public PropertiesReader() throws IOException {
    }

    /**
     * Retrieves the value associated with the specified key from the configuration file.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or an empty string if the key is not found or an error occurs
     */
    public String getValueFromConfig(String key) {
        String value = "";
        try {
            FileInputStream fileInput = new FileInputStream(environment);
            properties.load(fileInput);
            value = properties.getProperty(key);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return value;
    }
}
