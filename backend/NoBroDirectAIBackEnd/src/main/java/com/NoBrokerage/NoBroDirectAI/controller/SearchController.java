package com.NoBrokerage.NoBroDirectAI.controller;

import com.NoBrokerage.NoBroDirectAI.dto.CardDto;
import com.NoBrokerage.NoBroDirectAI.dto.SearchResponse;
import com.NoBrokerage.NoBroDirectAI.dto.SearchRequest;
import com.NoBrokerage.NoBroDirectAI.searchFilters.SearchFilter;
import com.NoBrokerage.NoBroDirectAI.service.interfaces.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // http://localhost:8083/api
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/filter") // http://localhost:8083/api/filter
    public ResponseEntity<SearchFilter> searchFilter(@RequestBody SearchRequest request) {
        log.info("Received search request: {}", request == null ? "null" : request.getQuery());
        SearchFilter search = searchService.searchFilters(request);
        log.info("Returning search results for query '{}'", request.getQuery());
        return ResponseEntity.ok(search);
    }
    @PostMapping("/search") // http://localhost:8083/api/search
    public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest request) {
        log.info("Received search request: {}", request == null ? "null" : request.getQuery());
        SearchResponse search = searchService.searchResults(request);
        log.info("Returning search results for query '{}'", request.getQuery());
        return ResponseEntity.ok(search);
    }

    @GetMapping("/allProperties") // http://localhost:8083/api/allProperties
    public List<CardDto> allProperties() {
        log.info("Received request for all properties");
        List<CardDto> cardDtos = searchService.allProperties();
        log.info("Returning all properties");
        return cardDtos;
    }
}
