package com.example.ompm.dto;

import java.util.List;

public record PaginationResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {
}
