package com.example.ompm.dto;

import java.util.List;

public record CreateBillHistoryRequest(
        Long accountId,
        String billCode,
        Long totalAmountCents,
        CreateBillHistoryImageRequest image,
        List<CreateBillHistoryDetailRequest> details,
        List<CreateBillHistoryPayeeRequest> payees
) {
}
