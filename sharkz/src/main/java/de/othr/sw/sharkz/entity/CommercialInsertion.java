package de.othr.sw.sharkz.entity;

import de.othr.sw.sharkz.entity.type.OfferType;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.persistence.Entity;

@Entity
public class CommercialInsertion extends Insertion {
    private int area;
    private boolean aircon;
    private boolean heavyCurrent;

    @Override
    public Map<String, String> getImportantAttributes() {
        Map<String, String> attrs = new LinkedHashMap<>();
        
        String priceLabel;
        if (this.getOfferType() == OfferType.PURCHASE)
            priceLabel = "Kaufpreis";
        else
            priceLabel = "Kaltmiete";
            
        String price = NumberFormat.getNumberInstance(
                Locale.GERMAN).format(this.getPrice());

        attrs.put(priceLabel, price + " &euro;");
        attrs.put("Fläche", String.valueOf(this.getArea()) + " m&sup2;");
        
        return attrs;
    }
    
    @Override
    public Map<String, String> getFurtherAttributes() {
        Map<String, String> attrs = getAttributes();
        
        // Strings
        attrs.put("Fläche", String.valueOf(this.area));
        
        // Booleans
        String s;
        String tr = "<i class=\"fa fa-check\" aria-hidden=\"true\"/>";
        String fa = "<i class=\"fa fa-times\" aria-hidden=\"true\"/>";
        
        attrs.put("Klimaanlage", s = this.aircon ? tr : fa);
        attrs.put("Starkstromanschluss", s = this.heavyCurrent ? tr : fa);
                
        return attrs;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public boolean isAircon() {
        return aircon;
    }

    public void setAircon(boolean aircon) {
        this.aircon = aircon;
    }

    public boolean isHeavyCurrent() {
        return heavyCurrent;
    }

    public void setHeavyCurrent(boolean heavyCurrent) {
        this.heavyCurrent = heavyCurrent;
    }
    
    
}
