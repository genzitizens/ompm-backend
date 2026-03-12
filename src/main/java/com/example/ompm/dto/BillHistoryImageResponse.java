package com.example.ompm.dto;

import java.time.OffsetDateTime;

public record BillHistoryImageResponse(
        Long id,
        String fileName,
        String storageKey,
        String mimeType,
        Long fileSizeBytes,
        OffsetDateTime createdAt
) {
}
