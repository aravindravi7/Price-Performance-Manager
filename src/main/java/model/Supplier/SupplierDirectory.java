/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Supplier;

import java.util.ArrayList;
import java.util.List;
import model.MarketModel.Market;

/**
 *
 * @author kal bugrara
 */
public class SupplierDirectory {
    private ArrayList<Supplier> supplierlist;
    
    public SupplierDirectory() {
        supplierlist = new ArrayList<>();
    }
    
    public Supplier newSupplier(String name) {
        String supplierId = "SUP" + (supplierlist.size() + 1);
        Supplier supplier = new Supplier(name, supplierId);
        supplierlist.add(supplier);
        return supplier;
    }
    
    public ArrayList<Supplier> getSupplierList() {
        return supplierlist;
    }
    
    public Supplier findSupplier(String name) {
        for (Supplier supplier : supplierlist) {
            if (supplier.getName().equals(name)) {
                return supplier;
            }
        }
        return null;
    }
}