package me.manzhos.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    private File environment = new File("environment.properties").getCanonicalFile();
    private Properties properties = new Properties();

    public PropertiesReader() throws IOException {
    }

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
