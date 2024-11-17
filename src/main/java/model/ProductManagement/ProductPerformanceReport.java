public class ProductPerformanceReport {
    private List<ProductPerformanceEntry> entries;
    
    public void generateReport(Business business) {
        entries = new ArrayList<>();
        
        for(Supplier supplier : business.getSupplierDirectory().getSupplierList()) {
            for(Product product : supplier.getProductCatalog().getProductList()) {
                ProductPerformanceEntry entry = new ProductPerformanceEntry(
                    product.getName(),
                    product.getOriginalTargetPrice(),
                    product.getCurrentTargetPrice(),
                    product.getRevenue(),
                    product.getSalesBelowTarget(),
                    product.getSalesAboveTarget()
                );
                entries.add(entry);
            }
        }
    }
} 