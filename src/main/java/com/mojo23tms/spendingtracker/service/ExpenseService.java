package com.mojo23tms.spendingtracker.service;

import com.mojo23tms.spendingtracker.model.Expense;
import com.mojo23tms.spendingtracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExpenseService {

    private long id = 0;

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public Expense addExpense(int amount, String category, String description) throws IllegalArgumentException {
        verifyAmount(amount);
        verifyCategory(category);
        verifyDescription(description);
        id++;
        Expense exp = new Expense(amount, category, description, id);
        repository.saveExpense(exp);
        return exp;

    }

    public int getTotalSpent() {
        int sum = 0;
        for (Expense expense : repository.getExpenseList()) {
            sum += expense.getAmount();
        }
        return sum;
    }

    public int getTotalSpent(String category) {
        verifyCategory(category);
        int sum = 0;
        for (Expense expense : repository.getExpenseList()) {
            if (expense.getCategory().equals(category)) {
                sum += expense.getAmount();
            }
        }
        return sum;
    }

    public List<Expense> getAllExpenses() {
        return repository.getExpenseList();
    }

    public void updateExpenseById(long id, int amount, String category, String description) {
        verifyAmount(amount);
        verifyCategory(category);
        verifyDescription(description);
        Expense updated = new Expense(amount, category, description, id);
        boolean isUpdated = repository.updateExpense(id, updated);
        if (!isUpdated) {
            throw new NoSuchElementException("No expense with such ID");
        }
    }

    public void deleteExpense(long id) {
        boolean isDeleted = repository.deleteExpense(id);
        if (!isDeleted) {
            throw new NoSuchElementException("Expense with ID: " + id + " doesn't exist");
        }
    }

    public void checkIfEmpty() {
        if (repository.getExpenseList().isEmpty()) {
            throw new IllegalStateException("Expense list is empty!");
        }
    }

    void verifyAmount(int amount) {
        if (!(amount > 0)) {
            throw new IllegalArgumentException("Amount should be greater than 0!");
        }
    }

    void verifyCategory(String category) {
        if (category.isBlank()) {
            throw new IllegalArgumentException("Category can't be empty!");
        }
    }

    void verifyDescription(String description) {
        if (description.isBlank()) {
            throw new IllegalArgumentException("Description can't be empty!");
        }
    }


}
