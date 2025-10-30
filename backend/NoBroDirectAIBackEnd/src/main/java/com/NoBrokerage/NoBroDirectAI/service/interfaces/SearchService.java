package com.NoBrokerage.NoBroDirectAI.service.interfaces;

import com.NoBrokerage.NoBroDirectAI.dto.CardDto;
import com.NoBrokerage.NoBroDirectAI.dto.SearchResponse;
import com.NoBrokerage.NoBroDirectAI.searchFilters.SearchFilter;
import com.NoBrokerage.NoBroDirectAI.dto.SearchRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {
    public SearchFilter searchFilters(SearchRequest request);
    public SearchResponse searchResults(SearchRequest request);
    public List<CardDto> allProperties();
}
