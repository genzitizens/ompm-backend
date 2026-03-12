package com.example.ompm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, length = 10, unique = true)
    private String userId;

    @Column(name = "service_account_uid", nullable = false, length = 55, unique = true)
    private String serviceAccountUid;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    public Account(
            String userId,
            String serviceAccountUid,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt
    ) {
        this.userId = userId;
        this.serviceAccountUid = serviceAccountUid;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
