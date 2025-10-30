package com.NoBrokerage.NoBroDirectAI.service.implementation;
import com.NoBrokerage.NoBroDirectAI.searchFilters.FurnishingType;
import com.NoBrokerage.NoBroDirectAI.searchFilters.PropertyType;
import com.NoBrokerage.NoBroDirectAI.searchFilters.ReadinessStatus;
import com.NoBrokerage.NoBroDirectAI.searchFilters.SearchFilter;
import com.NoBrokerage.NoBroDirectAI.dto.SearchRequest;
import com.NoBrokerage.NoBroDirectAI.service.interfaces.FilterExtractorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class FilterExtractorServiceImpl implements FilterExtractorService {

    private static final Pattern BHK_PATTERN = Pattern.compile(
            "\\b(\\d{1,2})\\s*-?\\s*bhk\\b|\\b(\\d{1,2})\\s*(bedroom|bed room|bed rooms|beds|bed flat|bed apt|bed apartment|unit)\\b|\\b(single|double|triple)\\s*(bhk|room|unit)\\b",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern OFFICE_PATTERN = Pattern.compile(
            "\\boffice\\b|\\bcorporate\\s*space\\b|\\bworkspace\\b|\\bwork\\s*cabin\\b|\\bIT\\s*office\\b|\\bcommercial\\s*office\\b|\\bprivate\\s*office\\b|\\bshared\\s*workspace\\b|\\bco[- ]?working\\b",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern STUDIO_PATTERN = Pattern.compile(
            "\\bstudio\\b|\\bstudio\\s*apartment\\b|\\bstudio\\s*flat\\b|\\bstudio\\s*unit\\b|\\bstudio\\s*suite\\b|\\b1rk\\b|\\broom\\s*kitchen\\b",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern BATH_PATTERN = Pattern.compile(
            "\\b(\\d{1,2})\\s*-?\\s*bath(?:room)?s?\\b|\\b(\\d{1,2})\\s*(washroom|toilet|restroom|wc)\\b|\\battached\\s*bath\\b|\\bcommon\\s*bath\\b",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern BALCONY_PATTERN = Pattern.compile(
            "\\b(\\d{1,3})\\s*-?\\s*balcon(?:y|ies)\\b|\\bwith\\s*balcony\\b|\\bwithout\\s*balcony\\b|\\bprivate\\s*terrace\\b|\\bdeck\\b|\\bsit[- ]out\\b",
            Pattern.CASE_INSENSITIVE);

    // budget patterns: e.g., under ₹1.2 crore | below 90 lakhs | within ₹1 crore | around 2 cr | ₹80 Lakh
    private static final Pattern BUDGET_PATTERN = Pattern.compile(
            "(?:under|below|less than|within|upto|up to|under approx|under around|around|about|approx(?:\\.)?|roughly|near|nearly|close to|approximately|in range of|under budget|within budget|budget upto|price upto|price range|starting from|from|costing|priced at|expected price|between|around about|upwards of|downwards of|range around|not more than|not above|not exceeding|under limit|within limit|rough estimate|close estimate|starting|maximum|minimum|from around|from under|around nearly|approx range|up till|up under|expected around|rate under|rate upto|deal under|offer under|offer upto|offer around|near about|in vicinity of|approximate to|almost|price near|pricing under|pricing upto|pricing around|lesser than|value under|value upto|expected under|expected upto|expected around|offer price|final price|total cost|worth upto|worth around|worth under|within amount|in amount|total amount|budget under|budget around|around price|price near to|close price to|inr under|rs under|rs upto|rs around|value near|deal value under|deal value around|price tag under|price tag around|price tag upto)?\\s*(?:₹|rs\\.?|inr\\s*)?\\s*([\\d.,]+)\\s*(crore|cr|crores|lakh|lakhs|lac|lacs|k|thousand|thousands|million|millions|billion|billions)?",
            Pattern.CASE_INSENSITIVE);

    // property types
    private static final Pattern RESIDENTIAL_KEYWORDS = Pattern.compile(
            "\\b(flat|apartment|3bhk|2bhk|1bhk|villa|house|residential|bungalow|duplex|row house|independent house|penthouse|studio apartment|residential property|home|residence|villa community|townhouse|builder floor|floor apartment|society flat|gated flat|farmhouse|cottage|bungalow unit|villa type|residential land|plot|plot land|residential plot|housing unit|family flat|family home|family apartment|modern flat|modern apartment|ready flat|affordable home|mid income flat|premium flat|luxury home|deluxe flat|ultra luxury flat|eco home|compact flat|serviced apartment|studio flat|guest house|serviced home)\\b",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern COMMERCIAL_KEYWORDS = Pattern.compile(
            "\\b(office|commercial|shop|showroom|warehouse|factory|industrial|godown|restaurant|clinic|coworking|cowork space|corporate space|IT space|business center|startup office|workspace|commercial complex|mall shop|retail store|retail shop|commercial land|commercial property|bank space|training center|educational space|office space|office unit|office cabin|office suite|shared office|startup space|business property|corporate tower|commercial floor|business hub|retail unit|retail mall|warehouse unit|logistics warehouse|cold storage|storage space|workshop|industrial shed|factory unit)\\b",
            Pattern.CASE_INSENSITIVE);

    // readiness
    private static final Pattern READY_PATTERN = Pattern.compile(
            "\\b(ready[- ]?to[- ]?move|ready possession|ready|immediate possession|move in ready|possession available|handover ready|key ready|ready home|move[- ]?in property|ready apartment|ready flat|ready villa|ready shop|ready space|ready built|ready to occupy|available now|immediate move in|possession now|ready structure|ready to sell|ready to lease|hand over ready)\\b",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern UNDER_CONSTRUCTION_PATTERN = Pattern.compile(
            "\\b(under construction|ongoing project|new launch|pre launch|upcoming project|possession soon|possession in|under development|under process|launching soon|under progress|work in progress|newly developing|under building|building phase|structure stage|almost ready|nearing completion|construction stage|project phase|development stage|site work|foundation work|ongoing work|currently building|construction ongoing|possession after|possession expected|handover soon|under design|new tower|under approval)\\b",
            Pattern.CASE_INSENSITIVE);

    // location patterns
    private static final Pattern IN_LOC_PATTERN = Pattern.compile(
            "\\bin\\s+([a-zA-Z0-9\\s\\-]+?)(?:\\s*(?:under|below|within|around|near|,|for sale|for rent|for lease|available|at|location|area|region|zone|place|city|district|locality|town|village|$))",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern NEAR_PATTERN = Pattern.compile(
            "\\bnear\\s+([a-zA-Z0-9\\s\\-]+?)(?:\\s|,|in vicinity of|close to|adjacent to|beside|next to|opposite|facing|surrounding|nearby|around|within reach of|closeby|$)",
            Pattern.CASE_INSENSITIVE);

    // parking & furnishing
    private static final Pattern FURNISHED_PATTERN = Pattern.compile(
            "\\b(fully furnished|well furnished|furnished|completely furnished|move[- ]in ready|modern furnished|luxury furnished|premium furnished|designer furnished|decorated|fitted out|ready furnished|turnkey furnished)\\b",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern SEMI_FURNISHED_PATTERN = Pattern.compile(
            "\\b(semi[- ]furnished|partially furnished|half furnished|basic furnished|basic furnishing|moderately furnished|light furnished|simple furnished|mid furnished|half setup|half ready)\\b",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern UNFURNISHED_PATTERN = Pattern.compile(
            "\\b(unfurnished|bare shell|empty flat|without furniture|non furnished|shell property|unready|vacant|raw space|empty unit|bare flat|plain unit|without furnishing|plain setup|raw condition|no furniture)\\b",
            Pattern.CASE_INSENSITIVE);

    // helper to remove punctuation corners
    private String normalize(String s) {
        return s == null ? "" : s.trim();
    }

    @Override
    public SearchFilter extract(SearchRequest request) {
        String q = normalize(request.getQuery());
        String qLower = q.toLowerCase(Locale.ROOT);

        SearchFilter.SearchFilterBuilder builder = SearchFilter.builder().rawQuery(q);

        PropertyType(q, builder);

        BHK(q, builder);

        Bathrooms(q, builder);

        Balcony(q, builder);

        Readiness(builder, q);

        Landmark(q, builder);

        FullAddress(q, builder, qLower);

        BudgetExtraction(q, qLower, builder);

        Furnishing(q, qLower, builder);

        return builder.build();
    }

    //------------------------ Individual feature extractors ----------------------------
    private static void BHK(String q, SearchFilter.SearchFilterBuilder builder) {
        String bhkValue = null;

        Matcher m = BHK_PATTERN.matcher(q);
        if (m.find()) {
            bhkValue = m.group(1).trim() + "BHK";  // e.g. "3" → "3BHK"
        } else if (OFFICE_PATTERN.matcher(q).find()) {
            bhkValue = "Office";
        } else if (STUDIO_PATTERN.matcher(q).find()) {
            bhkValue = "Studio";
        }

        if (bhkValue != null) {
            builder.bhk(bhkValue);  // <-- store as String field in your SearchFilter
        }
    }

    private static void PropertyType(String q, SearchFilter.SearchFilterBuilder builder) {
        // Property type
        if (COMMERCIAL_KEYWORDS.matcher(q).find()) {
            builder.propertyType(PropertyType.COMMERCIAL);
        } else if (RESIDENTIAL_KEYWORDS.matcher(q).find()) {
            builder.propertyType(PropertyType.RESIDENTIAL);
        } else {
            builder.propertyType(PropertyType.ANY);
        }
    }

    private static void Bathrooms(String q, SearchFilter.SearchFilterBuilder builder) {
        Matcher m;
        // Bathrooms
        m = BATH_PATTERN.matcher(q);
        if (m.find()) {
            try { builder.bathrooms(Integer.valueOf(m.group(1))); } catch (NumberFormatException ignored) {}
        }
    }

    private static void Balcony(String q, SearchFilter.SearchFilterBuilder builder) {
        Matcher m;
        // Balcony
        m = BALCONY_PATTERN.matcher(q);
        if (m.find()) {
            try { builder.balconies(Integer.valueOf(m.group(1))); } catch (NumberFormatException ignored) {}
        }
    }

    private static void Readiness(SearchFilter.SearchFilterBuilder builder, String q) {

        if (UNDER_CONSTRUCTION_PATTERN.matcher(q).find()) {
            builder.status(ReadinessStatus.UNDER_CONSTRUCTION);
        } else if (READY_PATTERN.matcher(q).find()) {
            builder.status(ReadinessStatus.READY_TO_MOVE);
        }
    }

    private static void Landmark(String q, SearchFilter.SearchFilterBuilder builder) {
        Matcher m;
        // Landmark (near)
        m = NEAR_PATTERN.matcher(q);
        if (m.find()) {
            String lm = m.group(1).trim();
            builder.landmark(lm);
        }
    }

    private static void FullAddress(String q, SearchFilter.SearchFilterBuilder builder, String qLower) {
        Matcher m;
        // City / locality: try "in <...>" first
        m = IN_LOC_PATTERN.matcher(q);
        if (m.find()) {
            String loc = m.group(1).trim();
            // if contains common prepositions or words, try to split them
            loc = loc.replaceAll("\\b(near|close to|nearby)\\b", "").trim();
            // Heuristic: if contains a comma or ends with city name, attempt to split.
            builder.fullAddress(loc);
        } else {
            // fallback: look for common city names in the query (you can extend this list)
            List<String> wellKnown = Arrays.asList("pune", "mumbai", "bangalore", "bangalore", "wakad", "hinjewadi", "baner", "kharadi", "hadapsar", "viman nagar", "magarpatta");
            for (String c : wellKnown) {
                if (qLower.contains(c)) {
                    builder.fullAddress(c);
                    break;
                }
            }
        }
    }

    private static void BudgetExtraction(String q, String qLower, SearchFilter.SearchFilterBuilder builder) {
        Matcher budgetMatcher = BUDGET_PATTERN.matcher(q);
        List<Long> foundBudgets = new ArrayList<>();

        while (budgetMatcher.find()) {
            String rawNum = budgetMatcher.group(1);
            String unit = budgetMatcher.group(2);
            if (rawNum == null || rawNum.isBlank()) continue;

            Double number = parseNumber(rawNum);
            if (number == null) continue;

            long valueRupees = convertToRupees(number, unit);
            foundBudgets.add(valueRupees);
        }

        // Pick the last meaningful one (most recent in text)
        if (!foundBudgets.isEmpty()) {
            Long budget = foundBudgets.get(foundBudgets.size() - 1);
            builder.budget(budget);
        }
    }

    private static void Furnishing(String q, String qLower, SearchFilter.SearchFilterBuilder builder) {
        FurnishingType furnishingType = FurnishingType.NONE;

        if (UNFURNISHED_PATTERN.matcher(q).find()) {
            furnishingType = FurnishingType.UNFURNISHED;
        } else if (SEMI_FURNISHED_PATTERN.matcher(q).find()) {
            furnishingType = FurnishingType.SEMI_FURNISHED;
        } else if (FURNISHED_PATTERN.matcher(q).find()) {
            furnishingType = FurnishingType.FURNISHED;
        }

        builder.furnishingTypes(furnishingType);
    }

    //------------------------ Budget parsing helpers ----------------------------
    private static Double parseNumber(String raw) {
        try {
            String cleaned = raw.replaceAll(",", "").trim();
            return Double.parseDouble(cleaned);
        } catch (Exception ex) {
            return null;
        }
    }

    private static long convertToRupees(Double number, String unit) {
        if (unit == null) unit = "";
        unit = unit.toLowerCase();
        if (unit.contains("crore") || unit.equals("cr")) {
            // 1 crore = 1e7 rupees
            return Math.round(number * 10_000_000L);
        } else if (unit.contains("lakh") || unit.contains("lac") || unit.equals("k") && number >= 1) {
            // 1 lakh = 1e5
            return Math.round(number * 100_000L);
        } else {
            // no unit: assume rupees directly if large, otherwise treat as rupees (fallback)
            // If number < 1000 treat as lakhs? Not safe. We'll assume raw rupees.
            return Math.round(number);
        }
    }

    // helper classes
    private enum BudgetQual { MAX, APPROX, EXACT, UNKNOWN }
    private static class BudgetMatch {
        long value;
        BudgetQual qual;
        BudgetMatch(long v, BudgetQual q) { value = v; qual = q; }
    }
}
