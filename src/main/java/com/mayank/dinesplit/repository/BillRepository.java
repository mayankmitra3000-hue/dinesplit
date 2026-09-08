package com.mayank.dinesplit.repository;

import com.mayank.dinesplit.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
