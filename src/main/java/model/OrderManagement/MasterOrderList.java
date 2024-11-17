package model.OrderManagement;

import java.util.ArrayList;
import model.CustomerManagement.CustomerProfile;
import model.SalesManagement.SalesPersonProfile;

public class MasterOrderList {
    ArrayList<Order> orders;
    
    public MasterOrderList() {
        orders = new ArrayList<Order>();
    }
    
    public Order newOrder(CustomerProfile cp, SalesPersonProfile spp) {
        Order order = new Order(cp, spp);
        orders.add(order);
        return order;
    }
    
    public ArrayList<Order> getOrders() {
        return orders;
    }
}
