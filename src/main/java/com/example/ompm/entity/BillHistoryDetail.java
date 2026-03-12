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
@Table(name = "bill_history_details")
public class BillHistoryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bill_history_id", nullable = false)
    private BillHistory billHistory;

    @Column(name = "amount_cents", nullable = false)
    private Long amountCents;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    private BillHistoryDetail(
            BillHistory billHistory,
            Long amountCents,
            OffsetDateTime createdAt
    ) {
        this.billHistory = billHistory;
        this.amountCents = amountCents;
        this.createdAt = createdAt;
    }

    public static BillHistoryDetail create(
            BillHistory billHistory,
            Long amountCents,
            OffsetDateTime createdAt
    ) {
        return new BillHistoryDetail(billHistory, amountCents, createdAt);
    }
}
