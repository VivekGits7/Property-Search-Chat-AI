package com.NoBrokerage.NoBroDirectAI.service;

import com.NoBrokerage.NoBroDirectAI.dto.CardDto;
import com.NoBrokerage.NoBroDirectAI.dto.SearchResponse;
import com.NoBrokerage.NoBroDirectAI.model.Property;
import com.NoBrokerage.NoBroDirectAI.repository.CsvPropertyRepository;
import com.NoBrokerage.NoBroDirectAI.searchFilters.SearchFilter;
import com.NoBrokerage.NoBroDirectAI.utils.MatchingMethod;
import com.NoBrokerage.NoBroDirectAI.utils.PropertySummaryGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CvsSearchService {

    private final CsvPropertyRepository repo;
    private final MatchingMethod matchingMethod;

    /**
     * Searches for properties based on flexible, case-insensitive filters,
     * and also finds similar properties with close attributes.
     */
    public SearchResponse search(SearchFilter filters) {
        log.info("Starting property search with filters: {}", filters);

        List<Property> allProperties = repo.findAll();
        List<CardDto> matched = new ArrayList<>();

        if (filters == null) {
            log.warn("No filters provided!");
            return SearchResponse.builder()
                    .query(null)
                    .extractedFilters(Collections.emptyMap())
                    .summary("No filters applied.")
                    .cards(Collections.emptyList())
                    .build();
        }

        // 1️⃣ Find main matched properties
        for (Property p : allProperties) {
            boolean matches = true;

            if (!isNullOrEmpty(filters.getBhk()))
                matches &= matchingMethod.softContains(p.getBhk(), filters.getBhk());

            if (!isNullOrEmpty(filters.getFullAddress()))
                matches &= matchingMethod.softContains(p.getFullAddress(), filters.getFullAddress());

            if (filters.getBudget() != null && p.getPrice() != null)
                matches &= matchingMethod.softPriceMatch(p.getPrice(), filters.getBudget());

            if (!isNullOrEmpty(filters.getLandmark()))
                matches &= matchingMethod.softContains(p.getLandmark(), filters.getLandmark());

            if (!isNullOrEmpty(filters.getStatus()))
                matches &= matchingMethod.softContains(p.getStatus(), String.valueOf(filters.getStatus()));

            if (!isNullOrEmpty(filters.getPropertyType()))
                matches &= matchingMethod.softContains(p.getPropertyType(), String.valueOf(filters.getPropertyType()));

            if (!isNullOrEmpty(filters.getFurnishingTypes()))
                matches &= matchingMethod.softContains(p.getFurnishingType(), String.valueOf(filters.getFurnishingTypes()));

            if (matches) {
                matched.add(toCardDto(p));
            }
        }

        // 2️⃣ Find similar properties if less than 4 results
        if (matched.size() < 4 && !matched.isEmpty()) {
            Property base = allProperties.stream()
                    .filter(p -> !isNullOrEmpty(p.getBhk()))
                    .filter(p -> p.getBhk().equalsIgnoreCase(matched.get(0).getBhk()))
                    .findFirst().orElse(null);

            if (base != null) {
                List<CardDto> similar = allProperties.stream()
                        .filter(p ->
                                p != base &&
                                        isSimilar(p, base)
                        )
                        .limit(4)
                        .map(this::toCardDto)
                        .collect(Collectors.toList());

                matched.addAll(similar);
            }
        }

        log.info("Matched {} properties (including similar)", matched.size());

        Map<String, Object> extracted = new HashMap<>();
        extracted.put("bhk", filters.getBhk());
        extracted.put("status", filters.getStatus());
        extracted.put("furnishingTypes", filters.getFurnishingTypes());
        extracted.put("budget", filters.getBudget());
        extracted.put("landmark", filters.getLandmark());
        extracted.put("address", filters.getFullAddress());
        extracted.put("propertyType", filters.getPropertyType());


        return SearchResponse.builder()
                .query(filters.getRawQuery())
                .extractedFilters(extracted)
                .summary(PropertySummaryGenerator.generateCsvSummary(matched))
                .cards(matched)
                .build();
    }

    /**
     * Checks if two properties are similar (not exact but close in attributes)
     */
    private boolean isSimilar(Property p, Property base) {
        boolean similar = true;

        // Match on bhk (partial ok)
        similar &= matchingMethod.softContains(p.getBhk(), base.getBhk());

        // Status match
        similar &= matchingMethod.softContains(p.getStatus(), base.getStatus());

        // Furnishing match
        similar &= matchingMethod.softContains(p.getFurnishingType(), base.getFurnishingType());

        similar &= matchingMethod.softContains(p.getPropertyType(), base.getPropertyType());

        // Price within ±30%
        if (p.getPrice() != null && base.getPrice() != null) {
            double ratio = (double) Math.abs(p.getPrice() - base.getPrice()) / base.getPrice();
            similar &= ratio <= 0.3; // within 30% range
        }

        return similar;
    }

    private CardDto toCardDto(Property p) {
        return CardDto.builder()
                .projectName(p.getProjectName())
                .bhk(p.getBhk())
                .bathrooms(p.getBathrooms())
                .balcony(p.getBalconies())
                .propertyType(p.getPropertyType())
                .furnishedType(p.getFurnishingType())
                .status(p.getStatus())
                .fullAddress(p.getFullAddress())
                .landmark(p.getLandmark())
                .price(p.getPrice())
                .ctaSlug(p.getCtaSlug())
                .build();
    }

    private boolean isNullOrEmpty(Object obj) {
        if (obj == null) return true;
        String str = String.valueOf(obj).trim();
        return str.isEmpty() || str.equalsIgnoreCase("null");
    }
}
