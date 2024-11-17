/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class SolutionOfferCatalog {
    ArrayList<SolutionOffer> solutionoffers;
    
public SolutionOfferCatalog() {
        solutionoffers = new ArrayList<>();
    }

    // Method to add a new SolutionOffer
    public void addSolutionOffer(SolutionOffer offer) {
        solutionoffers.add(offer);
    }

    // Method to get the list of all SolutionOffers
    public ArrayList<SolutionOffer> getSolutionOffers() {
        return solutionoffers;
    }

    // Optional: Method to find a SolutionOffer by price or other attributes
    public SolutionOffer findSolutionOfferByPrice(int price) {
        for (SolutionOffer offer : solutionoffers) {
            if (offer.getPrice() == price) {
                return offer;
            }
        }
        return null; // Return null if no match is found
    }
}