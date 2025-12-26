package com.mojo23tms.spendingtracker;

import com.mojo23tms.spendingtracker.model.Expense;
import com.mojo23tms.spendingtracker.repository.ExpenseRepository;
import com.mojo23tms.spendingtracker.service.ExpenseService;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExpenseServiceTest {
    private ExpenseService es;

    private final int amount = 25;
    private final String category = "food";
    private final String description = "lunch";

    @BeforeEach
    public void setup() {
        es = new ExpenseService(new ExpenseRepository());
        es.addExpense(amount, category, description);
    }

    @Test
    public void test_addValidExpense() {
        List<Expense> expenseList = es.getAllExpenses();
        assertThat(expenseList)
                .as("Expense list isn't valid!")
                .hasSize(1);
        assertThat(expenseList.get(0))
                .satisfies(expense -> {
                    assertThat(expense.getAmount())
                            .as("Amount of added expense is wrong!")
                            .isEqualTo(amount);
                    assertThat(expense.getCategory())
                            .as("Category of added expense is wrong!")
                            .isEqualTo(category);
                    assertThat(expense.getDescription())
                            .as("Description of added expense is wrong!")
                            .isEqualTo(description);
                });

    }

    @Test
    public void test_invalidAmountThrowsException() {
        int invalidAmount = -25;
        assertThatThrownBy(() ->
                es.addExpense(invalidAmount, category, description)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("greater than 0");
    }

    @Test
    public void test_validUpdateExpense() {
        long expenseId = es.getAllExpenses().get(0).getId();

        int updAmount = 45;
        String updCategory = "clothes";
        String updDescription = "pants";

        es.updateExpenseById(expenseId, updAmount, updCategory, updDescription);
        assertThat(es.getAllExpenses().get(0))
                .satisfies(expense -> {
                    assertThat(expense.getId())
                            .as("Amount of added expense is wrong!")
                            .isEqualTo(expenseId);
                    assertThat(expense.getAmount())
                            .as("Amount of added expense is wrong!")
                            .isEqualTo(updAmount);
                    assertThat(expense.getCategory())
                            .as("Category of added expense is wrong!")
                            .isEqualTo(updCategory);
                    assertThat(expense.getDescription())
                            .as("Description of added expense is wrong!")
                            .isEqualTo(updDescription);
                });
    }

    @Test
    public void test_invalidIdUpdateExpense() {
        long expenseId = es.getAllExpenses().get(0).getId();
        assertThatThrownBy(() ->
                es.updateExpenseById(expenseId + 120, amount, category, description)
        )
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No expense with such ID");
    }

    @Test
    public void test_invalidIdDeleteExpense() {
        long expenseId = es.getAllExpenses().get(0).getId();
        long invalidId = expenseId + 120;
        assertThatThrownBy(() ->
                es.deleteExpense(invalidId)
        )
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Expense with ID: " + invalidId + " doesn't exist");
    }

    @Test
    public void test_validEmptyListResponse() {
        ExpenseService es_empty = new ExpenseService(new ExpenseRepository());
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(es_empty.getAllExpenses())
                    .as("List is not empty!")
                    .isEmpty();
            softly.assertThat(es_empty.getTotalSpent())
                    .as("Total amount should be 0!")
                    .isZero();
            softly.assertThat(es_empty.getTotalSpent(category))
                    .as("Total amount should be 0!")
                    .isZero();
        });
    }

}
