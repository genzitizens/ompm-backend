package com.example.ompm.controller;

import com.example.ompm.dto.BillHistoryResponse;
import com.example.ompm.dto.CreateBillHistoryRequest;
import com.example.ompm.dto.GetBillHistoriesQuery;
import com.example.ompm.dto.PaginationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/bill-histories")
public interface BillHistoryController {

    @PostMapping
    ResponseEntity<BillHistoryResponse> createBillHistory(@RequestBody CreateBillHistoryRequest request);

    @GetMapping
    ResponseEntity<PaginationResponse<BillHistoryResponse>> getBillHistories(@ModelAttribute GetBillHistoriesQuery query);

    default ResponseEntity<BillHistoryResponse> created(BillHistoryResponse response) {
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    default ResponseEntity<PaginationResponse<BillHistoryResponse>> ok(
            PaginationResponse<BillHistoryResponse> response
    ) {
        return ResponseEntity.ok(response);
    }
}
