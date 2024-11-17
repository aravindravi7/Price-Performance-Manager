/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.ProductManagement.Product;
import model.ProductManagement.ProductCatalog;
import model.ProductManagement.ProductSummary;
import model.ProductManagement.ProductsReport;
import model.ProductManagement.PriceProfile;
import model.MarketModel.Market;
import model.MarketModel.Channel;
import model.MarketModel.MarketChannelAssignment;

/**
 *
 * @author kal bugrara
 */
public class Supplier {
    private String name;
    private String id;
    private ProductCatalog productcatalog;
    private List<MarketChannelAssignment> channelAssignments;
    private Map<Product, PriceProfile> priceProfiles;
    private SupplierPerformance performance;
    private ProductsReport productsreport;
    
    public Supplier(String name, String id) {
        this.name = name;
        this.id = id;
        this.productcatalog = new ProductCatalog();
        this.channelAssignments = new ArrayList<>();
        this.priceProfiles = new HashMap<Product, PriceProfile>();
        this.performance = new SupplierPerformance(this);
    }
    
    public void addProduct(Product product, PriceProfile priceProfile) {
        productcatalog.addProduct(product);
        priceProfiles.put(product, priceProfile);
    }
    
    public void assignToChannel(Market market, Channel channel) {
        MarketChannelAssignment assignment = new MarketChannelAssignment(market, channel);
        channelAssignments.add(assignment);
    }
    
    public double calculateProductPerformance(Product product) {
        PriceProfile profile = priceProfiles.get(product);
        return profile.calculatePerformanceMetric();
    }
    
    public SupplierPerformance generatePerformanceReport() {
        performance.updateMetrics(productcatalog, priceProfiles);
        return performance;
    }
    
    public ProductsReport prepareProductsReport(){
        
        productsreport = productcatalog.generatProductPerformanceReport();
        return productsreport;
    }
    
    public ArrayList<ProductSummary> getProductsAlwaysAboveTarget(){
       
        if(productsreport==null) productsreport = prepareProductsReport();
       return productsreport.getProductsAlwaysAboveTarget();
       
    }
    
    public String getName(){
        return name;
    }
        public ProductCatalog getProductCatalog(){
        return productcatalog;
    }
    //add supplier product ..
    
    //update supplier product ...
    @Override
   public String toString(){
       return name;
       
   }
}
