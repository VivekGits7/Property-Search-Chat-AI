package com.NoBrokerage.NoBroDirectAI.searchFilters;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Builder
public class SearchFilter {
    private String fullAddress;               // in <city>
    private String landmark;           // near <landmark>
    private Long budget;            // nullable
    private String bhk;               // e.g., "2 BHK", "3 BHK", "Studio", "1 RK"
    private PropertyType propertyType; // RESIDENTIAL / COMMERCIAL / ANY
    private FurnishingType furnishingTypes; // FURNISHED / SEMI-FURNISHED / UNFURNISHED / ANY
    private ReadinessStatus status;  // READY_TO_MOVE / UNDER_CONSTRUCTION / ANY
    private Integer bathrooms; // number of bathrooms
    private Integer balconies; // number of balconies
    private String rawQuery;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("fullAddress", fullAddress);
        map.put("landmark", landmark);
        map.put("budget", budget);
        map.put("bhk", bhk);
        map.put("propertyType", propertyType);
        map.put("furnishingTypes", furnishingTypes);
        map.put("status", status);
        map.put("bathrooms", bathrooms);
        map.put("balconies", balconies);
        map.put("rawQuery", rawQuery);
        return map;
    }
}

