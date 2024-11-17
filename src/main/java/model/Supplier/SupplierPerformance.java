package model.Supplier;

import java.util.Map;
import model.ProductManagement.Product;
import model.ProductManagement.ProductCatalog;
import model.ProductManagement.PriceProfile;

public class SupplierPerformance {
    private Supplier supplier;
    private double overallPerformance;
    
    public SupplierPerformance(Supplier supplier) {
        this.supplier = supplier;
        this.overallPerformance = 0.0;
    }
    
    public void updateMetrics(ProductCatalog catalog, Map<Product, PriceProfile> priceProfiles) {
        // Implementation for updating metrics
        double totalPerformance = 0.0;
        int productCount = 0;
        
        for (Map.Entry<Product, PriceProfile> entry : priceProfiles.entrySet()) {
            totalPerformance += entry.getValue().calculatePerformanceMetric();
            productCount++;
        }
        
        if (productCount > 0) {
            overallPerformance = totalPerformance / productCount;
        }
    }
    
    public double getOverallPerformance() {
        return overallPerformance;
    }
} 