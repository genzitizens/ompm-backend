package com.example.ompm.repository;

import com.example.ompm.entity.BillHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillHistoryRepository extends JpaRepository<BillHistory, Long> {

    @EntityGraph(attributePaths = {"account", "image", "details", "payees"})
    Page<BillHistory> findByAccountId(Long accountId, Pageable pageable);
}
