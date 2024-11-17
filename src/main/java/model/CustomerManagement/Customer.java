package model.CustomerManagement;

import model.Personnel.Person;

public class Customer {
    private Person person;
    private CustomerProfile customerProfile;
    
    public Customer(Person person) {
        this.person = person;
        this.customerProfile = new CustomerProfile(person);
    }
    
    public CustomerProfile getCustomerProfile() {
        return customerProfile;
    }
    
    public Person getPerson() {
        return person;
    }
} 