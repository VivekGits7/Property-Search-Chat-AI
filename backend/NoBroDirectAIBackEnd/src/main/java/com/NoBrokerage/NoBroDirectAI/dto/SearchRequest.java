package com.NoBrokerage.NoBroDirectAI.dto;

import jakarta.annotation.Nonnull;
import lombok.Data;

@Data
public class SearchRequest {
    @Nonnull
    private String query;
}
