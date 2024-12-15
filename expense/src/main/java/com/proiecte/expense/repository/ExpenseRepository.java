package com.proiecte.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proiecte.expense.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
