package com.example.ompm.dto;

public record CreateBillHistoryPayeeRequest(
        String payeeName,
        String payeeMobileNo,
        String payeeTelegramChatId,
        Long amountCents
) {
}
