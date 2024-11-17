package model.ProductManagement;

public class Product {
    private String name;
    private double targetPrice;
    private double currentPrice;
    private double floorPrice;
    private double ceilingPrice;
    private int salesVolume;
    
    public Product(String name, double targetPrice, double floorPrice, double ceilingPrice) {
        this.name = name;
        this.targetPrice = targetPrice;
        this.currentPrice = targetPrice;
        this.floorPrice = floorPrice;
        this.ceilingPrice = ceilingPrice;
    }
    
    public String getName() {
        return name;
    }
    
    public double getTargetPrice() {
        return targetPrice;
    }
    
    public double getCurrentPrice() {
        return currentPrice;
    }
    
    public double getFloorPrice() {
        return floorPrice;
    }
    
    public double getCeilingPrice() {
        return ceilingPrice;
    }
    
    public String getProductName() {
        return name;
    }
    
    public int getSalesVolume() {
        return salesVolume;
    }
    
    public void updateSalesVolume(int quantity) {
        this.salesVolume += quantity;
    }
    
    public void setTargetPrice(double newPrice) {
        if (newPrice >= this.floorPrice && newPrice <= this.ceilingPrice) {
            this.targetPrice = newPrice;
        } else {
            throw new IllegalArgumentException("Target price must be between floor price (" + 
                formatPrice(this.floorPrice) + ") and ceiling price (" + 
                formatPrice(this.ceilingPrice) + ")");
        }
    }
    
    public void setCurrentPrice(double newPrice) {
        if (newPrice >= this.floorPrice && newPrice <= this.ceilingPrice) {
            this.currentPrice = newPrice;
        } else {
            throw new IllegalArgumentException("Current price must be between floor price (" + 
                formatPrice(this.floorPrice) + ") and ceiling price (" + 
                formatPrice(this.ceilingPrice) + ")");
        }
    }
    
    private String formatPrice(double price) {
        return String.format("$%.2f", price);
    }
}
