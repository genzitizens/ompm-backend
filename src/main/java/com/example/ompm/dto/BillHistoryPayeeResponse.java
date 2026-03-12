package com.example.ompm.dto;

import java.time.OffsetDateTime;

public record BillHistoryPayeeResponse(
        Long id,
        String payeeName,
        String payeeMobileNo,
        String payeeTelegramChatId,
        Long amountCents,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
