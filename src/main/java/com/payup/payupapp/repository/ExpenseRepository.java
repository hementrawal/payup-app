package com.payup.payupapp.repository;

import com.payup.payupapp.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    Expense findByDescription(String description);
    List<Expense> findByGroupId(Long groupId);
}
