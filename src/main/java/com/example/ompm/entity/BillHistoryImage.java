package com.example.ompm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bill_history_images")
public class BillHistoryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_history_id")
    private BillHistory billHistory;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "storage_key", nullable = false, length = 500)
    private String storageKey;

    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;

    @Column(name = "file_size_bytes", nullable = false)
    private Long fileSizeBytes;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    private BillHistoryImage(
            BillHistory billHistory,
            String fileName,
            String storageKey,
            String mimeType,
            Long fileSizeBytes,
            OffsetDateTime createdAt
    ) {
        this.billHistory = billHistory;
        this.fileName = fileName;
        this.storageKey = storageKey;
        this.mimeType = mimeType;
        this.fileSizeBytes = fileSizeBytes;
        this.createdAt = createdAt;
    }

    public static BillHistoryImage create(
            BillHistory billHistory,
            String fileName,
            String storageKey,
            String mimeType,
            Long fileSizeBytes,
            OffsetDateTime createdAt
    ) {
        return new BillHistoryImage(billHistory, fileName, storageKey, mimeType, fileSizeBytes, createdAt);
    }
}
