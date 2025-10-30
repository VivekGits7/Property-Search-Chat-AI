package com.NoBrokerage.NoBroDirectAI.utils;

import com.NoBrokerage.NoBroDirectAI.dto.CardDto;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class PropertySummaryGenerator {

    public static String generateCsvSummary(List<CardDto> matched) {
        if (matched == null || matched.isEmpty()) {
            return "❌ No matching properties found in the CSV.";
        }

        StringBuilder summary = new StringBuilder();
        summary.append("✅ Found ").append(matched.size()).append(" matching propert")
                .append(matched.size() > 1 ? "ies" : "y").append(". ");

        // Extract common features
        Set<String> bhks = matched.stream()
                .map(CardDto::getBhk)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Set<String> statuses = matched.stream()
                .map(CardDto::getStatus)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Set<String> furnishTypes = matched.stream()
                .map(CardDto::getFurnishedType)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Find average or range price
        List<Long> prices = matched.stream()
                .map(CardDto::getPrice)
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());

        String priceRange = "";
        if (!prices.isEmpty()) {
            Long min = prices.get(0);
            Long max = prices.get(prices.size() - 1);
            if (Objects.equals(min, max)) {
                priceRange = "around " + formatPrice(min);
            } else {
                priceRange = "ranging from " + formatPrice(min) + " to " + formatPrice(max);
            }
        }

        // Find most common location landmark
        String location = matched.stream()
                .map(CardDto::getLandmark)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(l -> l, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("various prime locations");

        // Build concise description
        summary.append("These ")
                .append(bhks.isEmpty() ? "" : String.join("/", bhks))
                .append(" apartments are ")
                .append(furnishTypes.isEmpty() ? "" : furnishTypes.iterator().next().toLowerCase())
                .append(", ")
                .append(statuses.isEmpty() ? "" : statuses.iterator().next().toLowerCase().replace("_", " "))
                .append(", located near ")
                .append(location)
                .append(", ")
                .append("with prices ")
                .append(priceRange)
                .append(". ");

        // Add short list of project names
        List<String> names = matched.stream()
                .map(CardDto::getProjectName)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        summary.append("Projects include: ")
                .append(String.join(", ", names))
                .append(".");

        return summary.toString().trim();
    }

    private static String formatPrice(Long price) {
        if (price == null) return "N/A";

        if (price >= 10000000) {
            double crore = price / 10000000.0;
            return "₹" + new DecimalFormat("#.#").format(crore) + " Crore";
        } else if (price >= 100000) {
            double lakh = price / 100000.0;
            return "₹" + new DecimalFormat("#.#").format(lakh) + " Lakh";
        } else {
            return "₹" + new DecimalFormat("#,##,###").format(price);
        }
    }
}
