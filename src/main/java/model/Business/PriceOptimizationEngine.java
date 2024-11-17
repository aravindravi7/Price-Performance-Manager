public class PriceOptimizationEngine {
    private ConfigurationManager config;
    private Business business;
    private Map<Product, OptimizationResult> optimizationResults;
    
    public PriceOptimizationEngine(Business business) {
        this.business = business;
        this.config = new ConfigurationManager();
        this.optimizationResults = new HashMap<>();
    }
    
    public void optimizeProductPrices() {
        for (Supplier supplier : business.getSupplierDirectory().getSupplierList()) {
            for (Product product : supplier.getProductCatalog().getProductList()) {
                OptimizationResult result = optimizeProductPrice(product);
                optimizationResults.put(product, result);
            }
        }
    }
    
    private OptimizationResult optimizeProductPrice(Product product) {
        double currentPrice = product.getTargetPrice();
        double bestPrice = currentPrice;
        double bestRevenue = calculateRevenue(product, currentPrice);
        
        double minAdjustment = config.getDoubleProperty("price.adjustment.min");
        double maxAdjustment = config.getDoubleProperty("price.adjustment.max");
        
        for (double adjustment = minAdjustment; adjustment <= maxAdjustment; adjustment += 0.1) {
            double testPrice = currentPrice * adjustment;
            double projectedRevenue = calculateRevenue(product, testPrice);
            
            if (projectedRevenue > bestRevenue) {
                bestRevenue = projectedRevenue;
                bestPrice = testPrice;
            }
        }
        
        return new OptimizationResult(bestPrice, bestRevenue);
    }
    
    private double calculateRevenue(Product product, double price) {
        // Implement price elasticity model
        double elasticity = calculatePriceElasticity(product);
        double currentDemand = getCurrentDemand(product);
        double projectedDemand = currentDemand * Math.pow(price / product.getTargetPrice(), elasticity);
        
        return price * projectedDemand;
    }
} 