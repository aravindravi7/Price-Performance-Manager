package model.ProductManagement;

public class PriceProfile {
    private double targetPrice;
    private double floorPrice;
    private double ceilingPrice;
    
    public PriceProfile(double target, double floor, double ceiling) {
        this.targetPrice = target;
        this.floorPrice = floor;
        this.ceilingPrice = ceiling;
    }
    
    public double calculatePerformanceMetric() {
        return 0.0;  // Implementation for performance calculation
    }
} 