/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Personnel;

import java.util.ArrayList;
import model.MarketModel.Market;
import model.MarketModel.Channel;
import model.OrderManagement.Order;

/**
 *
 * @author kal bugrara
 */
public class EmployeeProfile extends Profile {
    private ArrayList<Market> assignedMarkets;
    private ArrayList<Channel> managedChannels;
    private PerformanceMetrics performanceMetrics;
    
    public EmployeeProfile(Person p) {
        super(p);
        assignedMarkets = new ArrayList<Market>();
        managedChannels = new ArrayList<Channel>();
        performanceMetrics = new PerformanceMetrics();
    }
    
    public void assignMarket(Market m) {
        assignedMarkets.add(m);
    }
    
    public void assignChannel(Channel c) {
        managedChannels.add(c);
    }
    
    public boolean isResponsibleForOrder(Order order) {
        return managedChannels.contains(order.getChannel()) ||
               assignedMarkets.contains(order.getMarket());
    }
    
    public void updatePerformance(Order order) {
        if (isResponsibleForOrder(order)) {
            performanceMetrics.recordOrder(order);
        }
    }
    
    public ArrayList<Market> getAssignedMarkets() {
        return assignedMarkets;
    }
    
    public ArrayList<Channel> getManagedChannels() {
        return managedChannels;
    }
    
    public PerformanceMetrics getPerformanceMetrics() {
        return performanceMetrics;
    }
    
    @Override
    public String getRole(){
        return  "Admin";
    }

}