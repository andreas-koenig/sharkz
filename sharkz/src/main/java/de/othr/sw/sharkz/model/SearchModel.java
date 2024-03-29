package de.othr.sw.sharkz.model;

import de.othr.sw.sharkz.entity.Order;
import de.othr.sw.sharkz.entity.type.HeatingType;
import de.othr.sw.sharkz.entity.type.HouseType;
import de.othr.sw.sharkz.entity.type.OfferType;
import de.othr.sw.sharkz.entity.type.UsageType;
import de.othr.sw.sharkz.service.SearchService;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named(value="search")
public class SearchModel {
    
    // GET parameters (used to search)
    private UsageType usage;
    private String location;
    private OfferType offer;
    
    // List of search results
    private List<Order> results;
    private boolean hasNoResults = false;
    
    // Models & Services
    @Inject private SearchService searchService;
    
    public void search() {

        if (offer == null && location == null && usage != null) {
            results = searchService.search(usage);
        } else if (location != null && usage == null && offer == null) {
            results = searchService.search(location);
        } else {
            results = searchService.search(offer, usage, location);
        }
        
        if (results == null || results.isEmpty())
            hasNoResults = true;
            
    }
    
    // Enum Values to Array -> SelectOneMenu
    public OfferType[] getOfferTypeValues() {
        return OfferType.values();
    }
    
    public HouseType[] getHouseTypeValues() {
        return HouseType.values();
    }
    
    public HeatingType[] getHeatingTypeValues() {
        return HeatingType.values();
    }
    
    public UsageType[] getUsageTypeValues() {
        return UsageType.values();
    }
    
    // Getter & Setter
    public UsageType getUsage() {
        return usage;
    }

    public void setUsage(UsageType usage) {
        this.usage = usage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public OfferType getOffer() {
        return offer;
    }

    public void setOffer(OfferType offer) {
        this.offer = offer;
    }

    public List<Order> getResults() {
        return results;
    }

    public void setResults(List<Order> results) {
        this.results = results;
    }

    public boolean isHasNoResults() {
        return hasNoResults;
    }

    public void setHasNoResults(boolean hasNoResults) {
        this.hasNoResults = hasNoResults;
    }
    
    
}
