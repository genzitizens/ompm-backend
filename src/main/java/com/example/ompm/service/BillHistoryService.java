package com.example.ompm.service;

import com.example.ompm.dto.BillHistoryResponse;
import com.example.ompm.dto.CreateBillHistoryRequest;
import com.example.ompm.dto.GetBillHistoriesQuery;
import com.example.ompm.dto.PaginationResponse;

public interface BillHistoryService {

    BillHistoryResponse createBillHistory(CreateBillHistoryRequest request);

    PaginationResponse<BillHistoryResponse> getBillHistories(GetBillHistoriesQuery query);
}
