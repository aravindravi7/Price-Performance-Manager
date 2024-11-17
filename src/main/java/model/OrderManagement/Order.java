package model.OrderManagement;

import java.util.ArrayList;
import model.CustomerManagement.CustomerProfile;
import model.SalesManagement.SalesPersonProfile;
import model.MarketModel.Channel;
import model.ProductManagement.Product;

public class Order {
    private ArrayList<OrderItem> orderItems;
    private CustomerProfile customer;
    private SalesPersonProfile salesperson;
    private String status;
    private Channel channel;
    
    public Order(CustomerProfile cp, SalesPersonProfile spp) {
        this.customer = cp;
        this.salesperson = spp;
        this.orderItems = new ArrayList<OrderItem>();
        this.status = "New";
    }
    
    public OrderItem newOrderItem(Product p, double price, int q) {
        OrderItem oi = new OrderItem(p, price, q);
        orderItems.add(oi);
        return oi;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }
    
    public CustomerProfile getCustomer() {
        return customer;
    }
    
    public SalesPersonProfile getSalesperson() {
        return salesperson;
    }
    
    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    public Channel getChannel() {
        return channel;
    }
    
    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
