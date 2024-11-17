/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.MarketModel;

import java.util.ArrayList;

import model.ProductManagement.SolutionOffer;

/**
 *
 * @author kal bugrara
 */
public class Market {
    ArrayList<SolutionOffer> so;
    ArrayList<MarketChannelAssignment> channels;
    ArrayList<String> characteristics;
    ArrayList<Market> submarkets;
    int size;
    String name;

    public Market(String s) {
        characteristics = new ArrayList<>();
        characteristics.add(s);
        channels = new ArrayList<>();
        so = new ArrayList<>();
        submarkets = new ArrayList<>();
        this.name = s;
    }
    
    public String getName() {
        return name;
    }
}
