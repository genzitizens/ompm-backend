package com.example.ompm.dto;

public record GetBillHistoriesQuery(
        Long accountId,
        Integer page,
        Integer size
) {
}
