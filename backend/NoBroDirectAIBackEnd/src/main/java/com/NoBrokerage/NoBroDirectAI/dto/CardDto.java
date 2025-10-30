package com.NoBrokerage.NoBroDirectAI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    private String projectName;
    private String bhk;
    private Integer bathrooms;
    private Integer balcony;
    private String propertyType;
    private String furnishedType;
    private String status;
    private String fullAddress;
    private String landmark;
    private Long price;
    private String ctaSlug;
}
