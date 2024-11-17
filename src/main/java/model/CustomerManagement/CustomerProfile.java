package model.CustomerManagement;

import model.Personnel.Person;
import model.OrderManagement.Order;
import java.util.ArrayList;

public class CustomerProfile {
    Person person;
    ArrayList<Order> orders;
    
    public CustomerProfile(Person p) {
        person = p;
        orders = new ArrayList<Order>();
    }
    
    public Person getPerson() {
        return person;
    }
    
    public void addOrder(Order o) {
        orders.add(o);
    }
    
    public ArrayList<Order> getOrders() {
        return orders;
    }
}
