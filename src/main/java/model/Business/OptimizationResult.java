public class OptimizationResult {
    private double optimizedPrice;
    private double projectedRevenue;
    private double confidenceScore;
    private List<String> recommendations;
    
    public OptimizationResult(double price, double revenue) {
        this.optimizedPrice = price;
        this.projectedRevenue = revenue;
        this.recommendations = new ArrayList<>();
        calculateConfidence();
    }
    
    private void calculateConfidence() {
        // Implement confidence calculation based on data quality
        // and market conditions
    }
    
    public void addRecommendation(String recommendation) {
        recommendations.add(recommendation);
    }
} 