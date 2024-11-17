/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Business;

import java.util.ArrayList;
import java.util.List;

import model.CustomerManagement.ChannelCatalog;
import model.CustomerManagement.CustomerDirectory;
import model.CustomerManagement.MarketCatalog;
import model.MarketingManagement.MarketingPersonDirectory;
import model.OrderManagement.MasterOrderList;
import model.OrderManagement.Order;
import model.OrderManagement.OrderItem;
import model.Personnel.EmployeeDirectory;
import model.Personnel.PersonDirectory;
import model.ProductManagement.Product;
import model.ProductManagement.ProductSummary;
import model.ProductManagement.ProductsReport;
import model.ProductManagement.SolutionOfferCatalog;
import model.SalesManagement.SalesPersonDirectory;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;
import model.UserAccountManagement.UserAccountDirectory;
import model.CustomerManagement.Customer;
import model.MarketModel.Channel;

/**
 *
 * @author kal bugrara
 */
public class Business {

    private String name;
    private SupplierDirectory supplierDirectory;
    private MarketingPersonDirectory marketingPersonDirectory;
    private UserAccountDirectory userAccountDirectory;
    private EmployeeDirectory employeeDirectory;
    private SalesPersonDirectory salesPersonDirectory;
    private PersonDirectory personDirectory;
    private CustomerDirectory customerDirectory;
    private MasterOrderList masterOrderList;

    public Business(String n) {
        name = n;
        supplierDirectory = new SupplierDirectory();
        marketingPersonDirectory = new MarketingPersonDirectory();
        userAccountDirectory = new UserAccountDirectory();
        employeeDirectory = new EmployeeDirectory();
        salesPersonDirectory = new SalesPersonDirectory();
        personDirectory = new PersonDirectory();
        customerDirectory = new CustomerDirectory();
        masterOrderList = new MasterOrderList();
    }

    public int getSalesVolume() {
        return masterOrderList.getSalesVolume();
    }

    public PersonDirectory getPersonDirectory() {
        return personDirectory;
    }

    public UserAccountDirectory getUserAccountDirectory() {
        return userAccountDirectory;
    }
    public MarketingPersonDirectory getMarketingPersonDirectory() {
        return marketingPersonDirectory;
    }

    public SupplierDirectory getSupplierDirectory() {
        return supplierDirectory;
    }

    public ProductsReport getSupplierPerformanceReport(String n) {
        Supplier supplier = supplierDirectory.findSupplier(n);
        if (supplier == null) {
            return null;
        }
        return supplier.prepareProductsReport();
    }

    public ArrayList<ProductSummary> getSupplierProductsAlwaysAboveTarget(String n) {
        ProductsReport productsreport = getSupplierPerformanceReport(n);
        return productsreport.getProductsAlwaysAboveTarget();
    }

    public int getHowManySupplierProductsAlwaysAboveTarget(String n) {
        ProductsReport productsreport = getSupplierPerformanceReport(n);
        int i = productsreport.getProductsAlwaysAboveTarget().size();
        return i;
    }

    public CustomerDirectory getCustomerDirectory() {
        return customerDirectory;
    }

    public SalesPersonDirectory getSalesPersonDirectory() {
        return salesPersonDirectory;
    }

    public MasterOrderList getMasterOrderList() {
        return masterOrderList;
    }

    public EmployeeDirectory getEmployeeDirectory() {
        return employeeDirectory;
    }

    public Order processOrder(Customer customer, Channel channel, List<OrderItem> items) {
        Order order = masterOrderList.newOrder(customer);
        for(OrderItem item: items) {
            double channelPrice = channel.adjustPrice(item.getProduct().getPrice());
            item.setActualPrice(channelPrice);
            order.addOrderItem(item);
        }
        return order;
    }

    public ProductsReport generatePricePerformanceReport() {
        ProductsReport report = new ProductsReport();
        for(Supplier supplier : supplierDirectory.getSupplierList()) {
            for(Product product : supplier.getProductCatalog().getProductList()) {
                ProductSummary summary = new ProductSummary(product);
                summary.calculatePricePerformance(masterOrderList);
                report.addProductSummary(summary);
            }
        }
        return report;
    }
}
