/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Business;

import model.Business.Business;
import model.CustomerManagement.CustomerDirectory;
import model.CustomerManagement.CustomerProfile;
import model.MarketingManagement.MarketingPersonDirectory;
import model.MarketingManagement.MarketingPersonProfile;
import model.OrderManagement.MasterOrderList;
import model.OrderManagement.Order;
import model.OrderManagement.OrderItem;
import model.Personnel.EmployeeDirectory;
import model.Personnel.EmployeeProfile;
import model.Personnel.Person;
import model.Personnel.PersonDirectory;
import model.ProductManagement.Product;
import model.ProductManagement.ProductCatalog;
import model.ProductManagement.ProductSummary;
import model.SalesManagement.SalesPersonDirectory;
import model.SalesManagement.SalesPersonProfile;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;
import model.UserAccountManagement.Role;
import model.UserAccountManagement.UserAccount;
import model.UserAccountManagement.UserAccountDirectory;
import model.MarketModel.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Calendar;

/**
 *
 * @author kal bugrara
 */
public class ConfigureABusiness {

  public static Business initialize() {
    Business business = new Business("Pricing Analytics Company");
    
    // Initialize directories
    SupplierDirectory supplierDirectory = business.getSupplierDirectory();
    CustomerDirectory customerDirectory = business.getCustomerDirectory();
    MasterOrderList masterOrderList = business.getMasterOrderList();
    
    // Create sales persons
    ArrayList<SalesPersonProfile> salesPersons = createSalesPersons(business);
    
    // Create 5 suppliers
    String[] supplierNames = {"Lenovo", "Dell", "HP", "Acer", "Asus"};
    for (String supplierName : supplierNames) {
      Supplier supplier = supplierDirectory.newSupplier(supplierName);
      generateProductsForSupplier(supplier);
    }
    
    // Create 10 customers with person details
    String[][] customerData = {
      {"John", "Smith", "Best Buy"},
      {"Sarah", "Johnson", "Amazon"},
      {"Michael", "Brown", "Walmart"},
      {"Emily", "Davis", "Target"},
      {"David", "Wilson", "Costco"},
      {"Lisa", "Anderson", "Newegg"},
      {"James", "Taylor", "B&H Photo"},
      {"Jennifer", "Thomas", "Micro Center"},
      {"Robert", "Martinez", "Office Depot"},
      {"Maria", "Garcia", "Staples"}
    };
    
    for (String[] data : customerData) {
      Person person = new Person();
      person.setFirstName(data[0]);
      person.setLastName(data[1]);
      person.setName(data[2]); // Company name
      customerDirectory.newCustomerProfile(person);
    }
    
    // Generate orders
    generateOrders(business, salesPersons);
    
    return business;
  }
  
  private static ArrayList<SalesPersonProfile> createSalesPersons(Business business) {
    ArrayList<SalesPersonProfile> salesPersons = new ArrayList<>();
    String[][] salesPersonData = {
      {"Mike", "Johnson", "Sales Manager"},
      {"Emma", "Williams", "Senior Sales Rep"},
      {"Alex", "Davis", "Sales Rep"},
      {"Sophie", "Miller", "Sales Rep"},
      {"Chris", "Wilson", "Sales Rep"}
    };
    
    for (String[] data : salesPersonData) {
      Person person = new Person();
      person.setFirstName(data[0]);
      person.setLastName(data[1]);
      person.setName(data[0] + " " + data[1]);
      SalesPersonProfile salesPerson = business.getSalesPersonDirectory().newSalesPersonProfile(person);
      salesPersons.add(salesPerson);
    }
    
    return salesPersons;
  }
  
  private static void generateProductsForSupplier(Supplier supplier) {
    // Generate 10 products for each supplier with more realistic data
    String[][] productData = {
        {"Laptop", "Basic", "Entry-level laptop for everyday use"},
        {"Laptop", "Pro", "High-performance laptop for professionals"},
        {"Desktop", "Basic", "Standard desktop computer"},
        {"Desktop", "Pro", "Workstation for heavy tasks"},
        {"Tablet", "Basic", "Compact tablet for entertainment"},
        {"Tablet", "Pro", "Premium tablet for productivity"},
        {"Monitor", "Basic", "24-inch HD display"},
        {"Monitor", "Pro", "32-inch 4K display"},
        {"Printer", "Basic", "Home office printer"},
        {"Printer", "Pro", "Enterprise-grade printer"}
    };
    
    for (String[] data : productData) {
        String type = data[0];
        String tier = data[1];
        String description = data[2];
        
        String productName = String.format("%s %s %s", supplier.getName(), type, tier);
        double basePrice = getBasePrice(type, tier);
        double floorPrice = basePrice * 0.8;
        double ceilingPrice = basePrice * 1.2;
        
        Product product = new Product(productName, basePrice, floorPrice, ceilingPrice);
        supplier.getProductCatalog().addProduct(product);
        
        System.out.println("Created product: " + productName + " for supplier: " + supplier.getName()); // Debug line
    }
  }
  
  private static double getBasePrice(String type, String tier) {
    // Base prices for different product types
    Map<String, Double> basePrice = new HashMap<>();
    basePrice.put("Laptop", 800.0);
    basePrice.put("Desktop", 600.0);
    basePrice.put("Tablet", 300.0);
    basePrice.put("Monitor", 200.0);
    basePrice.put("Printer", 150.0);
    
    // Adjust for tier
    double multiplier = tier.equals("Pro") ? 1.5 : 1.0;
    return basePrice.get(type) * multiplier;
  }
  
  private static void generateOrders(Business business, ArrayList<SalesPersonProfile> salesPersons) {
    Random random = new Random();
    
    // Get all suppliers and their products
    for (Supplier supplier : business.getSupplierDirectory().getSupplierList()) {
      for (Product product : supplier.getProductCatalog().getProductList()) {
        // Generate 10 orders for each product
        for (int i = 0; i < 10; i++) {
          // Randomly select a customer and sales person
          CustomerProfile customer = getRandomCustomer(business.getCustomerDirectory());
          SalesPersonProfile salesPerson = getRandomSalesPerson(salesPersons);
          
          // Create order with random quantity and price within bounds
          double actualPrice = generateRandomPrice(product);
          int quantity = random.nextInt(5) + 1; // 1-5 items
          
          Order order = business.getMasterOrderList().newOrder(customer, salesPerson);
          order.newOrderItem(product, actualPrice, quantity);
          
          // Set random date within last 6 months
          // Note: You'll need to add date field to Order class
          Calendar cal = Calendar.getInstance();
          cal.add(Calendar.MONTH, -random.nextInt(6));
          // order.setDate(cal.getTime());
        }
      }
    }
  }
  
  private static CustomerProfile getRandomCustomer(CustomerDirectory directory) {
    List<CustomerProfile> customers = directory.getCustomerList();
    return customers.get(new Random().nextInt(customers.size()));
  }
  
  private static SalesPersonProfile getRandomSalesPerson(ArrayList<SalesPersonProfile> salesPersons) {
    return salesPersons.get(new Random().nextInt(salesPersons.size()));
  }
  
  private static double generateRandomPrice(Product product) {
    Random random = new Random();
    double range = product.getCeilingPrice() - product.getFloorPrice();
    return product.getFloorPrice() + (range * random.nextDouble());
  }
}

