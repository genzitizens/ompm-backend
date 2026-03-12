package com.example.ompm.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bill_history")
public class BillHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "bill_code", nullable = false, length = 30)
    private String billCode;

    @Column(name = "total_amount_cents", nullable = false)
    private Long totalAmountCents;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @OneToOne(mappedBy = "billHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private BillHistoryImage image;

    @OneToMany(mappedBy = "billHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillHistoryDetail> details = new ArrayList<>();

    @OneToMany(mappedBy = "billHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillHistoryPayee> payees = new ArrayList<>();

    private BillHistory(
            Account account,
            String billCode,
            Long totalAmountCents,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt
    ) {
        this.account = account;
        this.billCode = billCode;
        this.totalAmountCents = totalAmountCents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static BillHistory create(
            Account account,
            String billCode,
            Long totalAmountCents,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt
    ) {
        return new BillHistory(account, billCode, totalAmountCents, createdAt, updatedAt);
    }

    public void assignImage(BillHistoryImage image) {
        this.image = image;
    }

    public void addDetail(BillHistoryDetail detail) {
        details.add(detail);
    }

    public void addPayee(BillHistoryPayee payee) {
        payees.add(payee);
    }

    public List<BillHistoryDetail> getDetails() {
        return Collections.unmodifiableList(details);
    }

    public List<BillHistoryPayee> getPayees() {
        return Collections.unmodifiableList(payees);
    }
}
