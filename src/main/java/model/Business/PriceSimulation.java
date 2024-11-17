public class PriceSimulation {
    private Business business;
    private List<SimulationResult> results;
    
    public SimulationResult runSimulation(double priceAdjustmentFactor) {
        Map<Product, Double> originalPrices = new HashMap<>();
        Map<Product, Double> revenueImpact = new HashMap<>();
        
        // Store original prices
        for(Supplier supplier : business.getSupplierDirectory().getSupplierList()) {
            for(Product product : supplier.getProductCatalog().getProductList()) {
                originalPrices.put(product, product.getTargetPrice());
                // Simulate price adjustment
                product.setTargetPrice(product.getTargetPrice() * priceAdjustmentFactor);
                // Calculate revenue impact
                revenueImpact.put(product, calculateRevenueImpact(product));
            }
        }
        
        // Restore original prices
        originalPrices.forEach(Product::setTargetPrice);
        
        return new SimulationResult(revenueImpact);
    }
} 