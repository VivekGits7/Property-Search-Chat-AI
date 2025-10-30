package com.NoBrokerage.NoBroDirectAI.utils;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.*;


@Service
public class MatchingMethod {

        // ---------------- Soft Contains ----------------
        public boolean softContains(String text, String keyword) {
            // ✅ If filter (keyword) is null or empty, treat as "no filter" → always match
            if (keyword == null || keyword.trim().isEmpty()) return true;
            if (text == null) return false;

            // Normalize to lowercase and remove accents
            text = normalize(text);
            keyword = normalize(keyword);

            // Replace special chars (-, _, ., etc.) with space
            text = text.replaceAll("[^a-z0-9\\s]", " ");
            keyword = keyword.replaceAll("[^a-z0-9\\s]", " ");

            // Tokenize
            List<String> textTokens = Arrays.asList(text.split("\\s+"));
            List<String> keywordTokens = Arrays.asList(keyword.split("\\s+"));

            // Soft matching logic
            for (String kw : keywordTokens) {
                if (kw.isEmpty()) continue;

                for (String t : textTokens) {
                    // Exact or substring match
                    if (t.contains(kw) || kw.contains(t)) return true;

                    // Prefix/suffix match (like pune vs pune-123)
                    if (t.startsWith(kw) || t.endsWith(kw)) return true;

                    // Fuzzy match based on character overlap
                    if (similarity(t, kw) > 0.7) return true;
                }
            }

            return false;
        }

        // ---------------- Normalize Helper ----------------
        private String normalize(String s) {
            return Normalizer.normalize(s, Normalizer.Form.NFD)
                    .replaceAll("\\p{M}", "")
                    .toLowerCase()
                    .trim();
        }

        // ---------------- Similarity Helper ----------------
        private double similarity(String a, String b) {
            if (a.length() < 2 || b.length() < 2) return 0;

            Set<Character> setA = new HashSet<>();
            Set<Character> setB = new HashSet<>();

            for (char c : a.toCharArray()) if (Character.isLetterOrDigit(c)) setA.add(c);
            for (char c : b.toCharArray()) if (Character.isLetterOrDigit(c)) setB.add(c);

            Set<Character> intersection = new HashSet<>(setA);
            intersection.retainAll(setB);

            double jaccard = (double) intersection.size() /
                    (setA.size() + setB.size() - intersection.size());
            return jaccard;
        }

        // ---------------- Soft Price Match ----------------
        public boolean softPriceMatch(long propertyPrice, long userBudget) {
            // ✅ No budget filter → treat as match
            if (userBudget <= 0) return true;

            // ❌ Invalid property price → no match
            if (propertyPrice <= 0) return false;

            // ⚙️ Dynamic tolerance based on user budget
            double tolerancePercent = getDynamicTolerance(userBudget);
            double tolerance = tolerancePercent / 100.0;

            long lowerBound = (long) (userBudget * (1 - tolerance));
            long upperBound = (long) (userBudget * (1 + tolerance));

            return propertyPrice >= lowerBound && propertyPrice <= upperBound;
        }

    // 📊 Adjust tolerance automatically based on budget size
    private double getDynamicTolerance(long budget) {
        if (budget < 2_000_000) return 30.0;   // < 20L → ±30%
        if (budget < 5_000_000) return 25.0;   // < 50L → ±25%
        if (budget < 10_000_000) return 20.0;  // < 1Cr → ±20%
        if (budget < 20_000_000) return 15.0;  // < 2Cr → ±15%
        return 10.0;                           // ≥ 2Cr → ±10%
    }

}

