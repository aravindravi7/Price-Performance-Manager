/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.OrderManagement;

import model.ProductManagement.Product;

/**
 *
 * @author kal bugrara
 */
public class OrderItem {

    private Product selectedProduct;
    private double actualPrice;
    private int quantity;

    public OrderItem(Product product, double price, int quantity) {
        this.selectedProduct = product;
        this.actualPrice = price;
        this.quantity = quantity;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getOrderItemTotal() {
        return actualPrice * quantity;
    }
}
