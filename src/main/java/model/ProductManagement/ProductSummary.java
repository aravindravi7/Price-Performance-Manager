/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import model.OrderManagement.MasterOrderList;

/**
 *
 * @author kal bugrara
 */

//this class will extract summary data from the product
public class ProductSummary {
    private Product product;
    
    public ProductSummary(Product p) {
        product = p;
    }
    
    public void calculatePricePerformance(MasterOrderList masterOrderList) {
        // Implementation for calculating price performance
        for(Order order : masterOrderList.getOrders()) {
            // Calculate performance metrics
        }
    }
}
