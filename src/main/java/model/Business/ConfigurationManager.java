public class ConfigurationManager {
    private static final String CONFIG_FILE = "config/business_config.properties";
    private Properties config;
    
    public ConfigurationManager() {
        config = new Properties();
        loadConfiguration();
    }
    
    private void loadConfiguration() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            config.load(input);
        } catch (IOException e) {
            createDefaultConfiguration();
        }
    }
    
    private void createDefaultConfiguration() {
        // Default business settings
        config.setProperty("suppliers.count", "5");
        config.setProperty("products.per.supplier", "10");
        config.setProperty("customers.count", "10");
        config.setProperty("orders.per.product", "10");
        config.setProperty("price.adjustment.min", "0.8");
        config.setProperty("price.adjustment.max", "1.2");
        config.setProperty("simulation.iterations", "1000");
        
        saveConfiguration();
    }
    
    private void saveConfiguration() {
        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            config.store(output, "Business Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int getIntProperty(String key) {
        return Integer.parseInt(config.getProperty(key));
    }
    
    public double getDoubleProperty(String key) {
        return Double.parseDouble(config.getProperty(key));
    }
} 