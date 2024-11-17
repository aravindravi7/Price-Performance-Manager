public class ProfitOptimizer {
    private Business business;
    private PriceSimulation simulation;
    
    public OptimizationResult optimizeProfitMargins() {
        double bestProfitMargin = 0;
        double optimalAdjustment = 1.0;
        
        // Try different adjustment factors
        for(double factor = 0.5; factor <= 1.5; factor += 0.1) {
            SimulationResult result = simulation.runSimulation(factor);
            double profitMargin = calculateProfitMargin(result);
            
            if(profitMargin > bestProfitMargin) {
                bestProfitMargin = profitMargin;
                optimalAdjustment = factor;
            }
        }
        
        return new OptimizationResult(optimalAdjustment, bestProfitMargin);
    }
} 