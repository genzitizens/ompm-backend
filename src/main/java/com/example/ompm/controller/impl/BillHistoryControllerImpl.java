package com.example.ompm.controller.impl;

import com.example.ompm.controller.BillHistoryController;
import com.example.ompm.dto.BillHistoryResponse;
import com.example.ompm.dto.CreateBillHistoryRequest;
import com.example.ompm.dto.GetBillHistoriesQuery;
import com.example.ompm.dto.PaginationResponse;
import com.example.ompm.service.BillHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillHistoryControllerImpl implements BillHistoryController {

    private final BillHistoryService billHistoryService;

    public BillHistoryControllerImpl(BillHistoryService billHistoryService) {
        this.billHistoryService = billHistoryService;
    }

    @Override
    public ResponseEntity<BillHistoryResponse> createBillHistory(CreateBillHistoryRequest request) {
        return created(billHistoryService.createBillHistory(request));
    }

    @Override
    public ResponseEntity<PaginationResponse<BillHistoryResponse>> getBillHistories(GetBillHistoriesQuery query) {
        return ok(billHistoryService.getBillHistories(query));
    }
}
