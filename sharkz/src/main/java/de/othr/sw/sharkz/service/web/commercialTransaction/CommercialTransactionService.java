package de.othr.sw.sharkz.service.web.commercialTransaction;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.service.SearchService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;

@WebService
@RequestScoped
public class CommercialTransactionService implements Serializable {
    @Inject private SearchService searchService;
    
    /**
     * Retrieve a list of commercial insertions from Sharkz.
     * 
     * Cast insertion via:
     * CommercialInsertion ins = (CommercialInsertion) insertion;
     * @param amount the amount of insertions wanted
     * @return a list with the most recent insertions
     */
    public List<Insertion> getCommercialInsertions(int amount) {
        List<Insertion> list = searchService.fetchBusiness(amount);
        
        for (Insertion in : list)
            in.setVendor(null);
        
        return list;
    }
}