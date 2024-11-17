package model.Business;

import java.io.InputStream;
import java.util.Properties;

public class ConfigurationLoader {
    private static Properties properties;
    
    static {
        loadProperties();
    }
    
    private static void loadProperties() {
        properties = new Properties();
        try (InputStream input = ConfigurationLoader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static double getDoubleProperty(String key) {
        return Double.parseDouble(properties.getProperty(key));
    }
    
    public static int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }
} 