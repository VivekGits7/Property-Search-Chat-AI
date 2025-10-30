package com.NoBrokerage.NoBroDirectAI.service.interfaces;

import com.NoBrokerage.NoBroDirectAI.searchFilters.SearchFilter;
import com.NoBrokerage.NoBroDirectAI.dto.SearchRequest;
import org.springframework.stereotype.Service;

@Service
public interface FilterExtractorService {
    public SearchFilter extract(SearchRequest request);
}
