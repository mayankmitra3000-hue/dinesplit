package com.mayank.dinesplit.repository;

import com.mayank.dinesplit.entity.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillItemRepository extends JpaRepository<BillItem, Long> {
}