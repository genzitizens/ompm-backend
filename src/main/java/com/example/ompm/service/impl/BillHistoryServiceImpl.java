package com.example.ompm.service.impl;

import com.example.ompm.dto.BillHistoryDetailResponse;
import com.example.ompm.dto.BillHistoryImageResponse;
import com.example.ompm.dto.BillHistoryPayeeResponse;
import com.example.ompm.dto.BillHistoryResponse;
import com.example.ompm.dto.CreateBillHistoryDetailRequest;
import com.example.ompm.dto.CreateBillHistoryImageRequest;
import com.example.ompm.dto.CreateBillHistoryPayeeRequest;
import com.example.ompm.dto.CreateBillHistoryRequest;
import com.example.ompm.dto.GetBillHistoriesQuery;
import com.example.ompm.dto.PaginationResponse;
import com.example.ompm.entity.Account;
import com.example.ompm.entity.BillHistory;
import com.example.ompm.entity.BillHistoryDetail;
import com.example.ompm.entity.BillHistoryImage;
import com.example.ompm.entity.BillHistoryPayee;
import com.example.ompm.repository.AccountRepository;
import com.example.ompm.repository.BillHistoryRepository;
import com.example.ompm.service.BillHistoryService;
import jakarta.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BillHistoryServiceImpl implements BillHistoryService {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;

    private final AccountRepository accountRepository;
    private final BillHistoryRepository billHistoryRepository;

    public BillHistoryServiceImpl(
            AccountRepository accountRepository,
            BillHistoryRepository billHistoryRepository
    ) {
        this.accountRepository = accountRepository;
        this.billHistoryRepository = billHistoryRepository;
    }

    @Override
    public BillHistoryResponse createBillHistory(CreateBillHistoryRequest request) {
        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + request.accountId()));

        OffsetDateTime now = OffsetDateTime.now();
        BillHistory billHistory = BillHistory.create(
                account,
                request.billCode(),
                request.totalAmountCents(),
                now,
                now
        );

        mapImage(request.image(), billHistory, now);
        mapDetails(request.details(), billHistory, now);
        mapPayees(request.payees(), billHistory, now);

        BillHistory savedBillHistory = billHistoryRepository.save(billHistory);
        return toResponse(savedBillHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<BillHistoryResponse> getBillHistories(GetBillHistoriesQuery query) {
        Long accountId = query.accountId();
        if (accountId == null) {
            throw new IllegalArgumentException("accountId is required");
        }

        int page = query.page() == null ? DEFAULT_PAGE : Math.max(query.page(), 0);
        int size = query.size() == null ? DEFAULT_SIZE : Math.max(query.size(), 1);

        Page<BillHistory> result = billHistoryRepository.findByAccountId(
                accountId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        return new PaginationResponse<>(
                result.stream().map(this::toResponse).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );
    }

    private void mapImage(CreateBillHistoryImageRequest request, BillHistory billHistory, OffsetDateTime now) {
        if (request == null) {
            return;
        }

        BillHistoryImage image = BillHistoryImage.create(
                billHistory,
                request.fileName(),
                request.storageKey(),
                request.mimeType(),
                request.fileSizeBytes(),
                now
        );
        billHistory.assignImage(image);
    }

    private void mapDetails(List<CreateBillHistoryDetailRequest> requests, BillHistory billHistory, OffsetDateTime now) {
        if (requests == null || requests.isEmpty()) {
            return;
        }

        requests.forEach(request -> {
            BillHistoryDetail detail = BillHistoryDetail.create(
                    billHistory,
                    request.amountCents(),
                    now
            );
            billHistory.addDetail(detail);
        });
    }

    private void mapPayees(List<CreateBillHistoryPayeeRequest> requests, BillHistory billHistory, OffsetDateTime now) {
        if (requests == null || requests.isEmpty()) {
            return;
        }

        requests.forEach(request -> {
            BillHistoryPayee payee = BillHistoryPayee.create(
                    billHistory,
                    request.payeeName(),
                    request.payeeMobileNo(),
                    request.payeeTelegramChatId(),
                    request.amountCents(),
                    now,
                    now
            );
            billHistory.addPayee(payee);
        });
    }

    private BillHistoryResponse toResponse(BillHistory billHistory) {
        return new BillHistoryResponse(
                billHistory.getId(),
                billHistory.getAccount().getId(),
                billHistory.getBillCode(),
                billHistory.getTotalAmountCents(),
                billHistory.getCreatedAt(),
                billHistory.getUpdatedAt(),
                toImageResponse(billHistory.getImage()),
                toDetailResponses(billHistory.getDetails()),
                toPayeeResponses(billHistory.getPayees())
        );
    }

    private BillHistoryImageResponse toImageResponse(BillHistoryImage image) {
        if (image == null) {
            return null;
        }

        return new BillHistoryImageResponse(
                image.getId(),
                image.getFileName(),
                image.getStorageKey(),
                image.getMimeType(),
                image.getFileSizeBytes(),
                image.getCreatedAt()
        );
    }

    private List<BillHistoryDetailResponse> toDetailResponses(List<BillHistoryDetail> details) {
        if (details == null || details.isEmpty()) {
            return Collections.emptyList();
        }

        return details.stream()
                .map(detail -> new BillHistoryDetailResponse(
                        detail.getId(),
                        detail.getAmountCents(),
                        detail.getCreatedAt()
                ))
                .toList();
    }

    private List<BillHistoryPayeeResponse> toPayeeResponses(List<BillHistoryPayee> payees) {
        if (payees == null || payees.isEmpty()) {
            return Collections.emptyList();
        }

        return payees.stream()
                .map(payee -> new BillHistoryPayeeResponse(
                        payee.getId(),
                        payee.getPayeeName(),
                        payee.getPayeeMobileNo(),
                        payee.getPayeeTelegramChatId(),
                        payee.getAmountCents(),
                        payee.getCreatedAt(),
                        payee.getUpdatedAt()
                ))
                .toList();
    }
}
