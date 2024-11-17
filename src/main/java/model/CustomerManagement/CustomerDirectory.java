/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.CustomerManagement;

import java.util.ArrayList;
import java.util.List;
import model.Personnel.Person;

/**
 *
 * @author kal bugrara
 */
public class CustomerDirectory {

    private List<CustomerProfile> customerlist;
    
    public CustomerDirectory() {
        customerlist = new ArrayList<>();
    }
    
    public CustomerProfile newCustomerProfile(Person p) {
        CustomerProfile cp = new CustomerProfile(p);
        customerlist.add(cp);
        return cp;
    }
    
    public List<CustomerProfile> getCustomerList() {
        return customerlist;
    }
    
    public CustomerProfile findCustomer(String name) {
        for (CustomerProfile cp : customerlist) {
            if (cp.getPerson().getName().equals(name)) {
                return cp;
            }
        }
        return null;
    }
    
    public int getCustomerCount() {
        return customerlist.size();
    }
}
