package com.mojo23tms.spendingtracker.repository;

import com.mojo23tms.spendingtracker.model.Expense;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ExpenseRepository {
    private final List<Expense> expenseList = new ArrayList<>();

    public void saveExpense(Expense exp) {
        expenseList.add(exp);
    }

    public List<Expense> getExpenseList() {
        return List.copyOf(expenseList);
    }

    public Optional<Expense> findById(long id) {
        return getExpenseList().stream()
                .filter(expense -> expense.getId() == id)
                .findAny();
    }

    public boolean deleteExpense(long id) {
        Optional<Expense> expense = findById(id);
        return expense.map(expenseList::remove).orElse(false);
    }

    public boolean updateExpense(long id, Expense updated) {
        Optional<Expense> originalExpense = findById(id);
        if (originalExpense.isEmpty()) {
            return false;
        }

        expenseList.remove(originalExpense.get());
        expenseList.add(updated);
        return true;
    }

}
