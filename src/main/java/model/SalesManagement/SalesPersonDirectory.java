/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.SalesManagement;

import java.util.ArrayList;
import model.Personnel.Person;

/**
 *
 * @author kal bugrara
 */
public class SalesPersonDirectory {

    ArrayList<SalesPersonProfile> salesPersonList;

    public SalesPersonDirectory() {
        salesPersonList = new ArrayList<SalesPersonProfile>();
    }

    public SalesPersonProfile newSalesPersonProfile(Person p) {
        SalesPersonProfile sp = new SalesPersonProfile(p);
        salesPersonList.add(sp);
        return sp;
    }

    public SalesPersonProfile findSalesPerson(String id) {

        for (SalesPersonProfile sp : salesPersonList) {

            if (sp.isMatch(id)) {
                return sp;
            }
        }
            return null; //not found after going through the whole list
         }

}
