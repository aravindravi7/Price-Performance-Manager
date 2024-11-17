package model.Personnel;

import model.OrderManagement.Order;

public class PerformanceMetrics {
    private double salesTarget;
    private double actualSales;
    private int ordersProcessed;
    
    public PerformanceMetrics() {
        this.salesTarget = 0;
        this.actualSales = 0;
        this.ordersProcessed = 0;
    }
    
    public void recordOrder(Order order) {
        ordersProcessed++;
        actualSales += order.getOrderTotal();
    }
    
    public void setSalesTarget(double target) {
        this.salesTarget = target;
    }
    
    public double getPerformanceRatio() {
        return salesTarget > 0 ? actualSales / salesTarget : 0;
    }
} 