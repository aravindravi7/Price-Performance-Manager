package model.MarketingManagement;

import java.util.ArrayList;
import model.Personnel.Person;

public class MarketingPersonDirectory {
    ArrayList<MarketingPersonProfile> marketingPersonList;

    public MarketingPersonDirectory() {
        marketingPersonList = new ArrayList<MarketingPersonProfile>();
    }

    public MarketingPersonProfile newMarketingPersonProfile(Person p) {
        MarketingPersonProfile mp = new MarketingPersonProfile(p);
        marketingPersonList.add(mp);
        return mp;
    }
}
