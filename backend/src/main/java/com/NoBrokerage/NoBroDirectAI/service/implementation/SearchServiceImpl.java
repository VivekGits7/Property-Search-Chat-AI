package com.NoBrokerage.NoBroDirectAI.service.implementation;

import com.NoBrokerage.NoBroDirectAI.dto.CardDto;
import com.NoBrokerage.NoBroDirectAI.dto.SearchResponse;
import com.NoBrokerage.NoBroDirectAI.model.Property;
import com.NoBrokerage.NoBroDirectAI.repository.CsvPropertyRepository;
import com.NoBrokerage.NoBroDirectAI.searchFilters.SearchFilter;
import com.NoBrokerage.NoBroDirectAI.dto.SearchRequest;
import com.NoBrokerage.NoBroDirectAI.service.CvsSearchService;
import com.NoBrokerage.NoBroDirectAI.service.interfaces.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final FilterExtractorServiceImpl filterExtractorServiceImpl;
    private final CvsSearchService cvsSearchService;
    private final CsvPropertyRepository repo;

    @Override
    public SearchFilter searchFilters(SearchRequest request) {
        SearchFilter extricatedFilters = filterExtractorServiceImpl.extract(request);
        return extricatedFilters;
    }

    @Override
    public SearchResponse searchResults(SearchRequest request) {
        SearchFilter extricatedFilters = filterExtractorServiceImpl.extract(request);
        SearchResponse finalResponce = cvsSearchService.search(extricatedFilters);
        return finalResponce;
    }

    @Override
    public List<CardDto> allProperties() {
        List<Property> allProperties = repo.findAll();
        List<CardDto> cards = new ArrayList<>();
        for (Property p : allProperties) {
            CardDto card = CardDto.builder()
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
            cards.add(card);
        }
        return cards;
    }


}
