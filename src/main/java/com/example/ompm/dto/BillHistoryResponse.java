package com.example.ompm.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record BillHistoryResponse(
        Long id,
        Long accountId,
        String billCode,
        Long totalAmountCents,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        BillHistoryImageResponse image,
        List<BillHistoryDetailResponse> details,
        List<BillHistoryPayeeResponse> payees
) {
}
