package com.example.ompm.dto;

public record CreateBillHistoryImageRequest(
        String fileName,
        String storageKey,
        String mimeType,
        Long fileSizeBytes
) {
}
