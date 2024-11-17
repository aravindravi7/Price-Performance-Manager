/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import java.util.ArrayList;

import model.MarketModel.MarketChannelAssignment;

/**
 *
 * @author kal bugrara
 */
public class SolutionOffer {
    ArrayList<Product> products;
    int price;//floor, ceiling, and target ideas
    MarketChannelAssignment marketchannelcomb;
    public SolutionOffer(MarketChannelAssignment m){
        marketchannelcomb = m;
        products = new ArrayList();
    } 
    
    public void addProduct(Product p){
        products.add(p);
    }
    public void setPrice(int p){
        price = p;
        
    }
    // Getter for products
    public ArrayList<Product> getProducts() {
        return products;
    }

    // Getter for price
    public int getPrice() {
        return price;
    }

    // Getter for market channel assignment
    public MarketChannelAssignment getMarketChannelAssignment() {
        return marketchannelcomb;
    }
    
}
