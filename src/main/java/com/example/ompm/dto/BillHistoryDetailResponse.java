package com.example.ompm.dto;

import java.time.OffsetDateTime;

public record BillHistoryDetailResponse(
        Long id,
        Long amountCents,
        OffsetDateTime createdAt
) {
}
