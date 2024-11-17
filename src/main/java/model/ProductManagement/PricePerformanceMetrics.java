public class PricePerformanceMetrics {
    private double targetPrice;
    private double actualPrice;
    private List<Order> salesHistory;
    private int salesFrequency;
    
    public double calculatePerformanceIndex() {
        double actualSales = salesHistory.size();
        double targetSales = salesFrequency;
        return (actualSales / targetSales) * (actualPrice / targetPrice);
    }
} 