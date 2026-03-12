package com.example.ompm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bill_history_payees")
public class BillHistoryPayee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bill_history_id", nullable = false)
    private BillHistory billHistory;

    @Column(name = "payee_name", nullable = false, length = 100)
    private String payeeName;

    @Column(name = "payee_mobile_no", length = 20)
    private String payeeMobileNo;

    @Column(name = "payee_telegram_chat_id", length = 100)
    private String payeeTelegramChatId;

    @Column(name = "amount_cents", nullable = false)
    private Long amountCents;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    private BillHistoryPayee(
            BillHistory billHistory,
            String payeeName,
            String payeeMobileNo,
            String payeeTelegramChatId,
            Long amountCents,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt
    ) {
        this.billHistory = billHistory;
        this.payeeName = payeeName;
        this.payeeMobileNo = payeeMobileNo;
        this.payeeTelegramChatId = payeeTelegramChatId;
        this.amountCents = amountCents;

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static BillHistoryPayee create(
            BillHistory billHistory,
            String payeeName,
            String payeeMobileNo,
            String payeeTelegramChatId,
            Long amountCents,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt
    ) {
        return new BillHistoryPayee(
                billHistory,
                payeeName,
                payeeMobileNo,
                payeeTelegramChatId,
                amountCents,
                createdAt,
                updatedAt
        );
    }
}
