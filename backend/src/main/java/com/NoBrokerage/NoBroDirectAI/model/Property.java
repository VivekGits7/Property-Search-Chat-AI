package com.NoBrokerage.NoBroDirectAI.model;


import com.NoBrokerage.NoBroDirectAI.searchFilters.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    private String projectName; // Apartment / Villa / Office Space Name
    private String fullAddress; // complete address
    private String landmark; // near <landmark>
    private Long price; // price in INR
    private String bhk; // 1RK, 1BHK, 2BHK, 3BHK etc.
    private String propertyType; // RESIDENTIAL / COMMERCIAL / ANY
    private String furnishingType; // FURNISHED / SEMI-FURNISHED / UNFURNISHED
    private String status; // READY_TO_MOVE / UNDER_CONSTRUCTION / ANY
    private Integer bathrooms; // number of bathrooms
    private Integer balconies; // number of balconies
    private String ctaSlug; // Call to action slug (URL)
}

