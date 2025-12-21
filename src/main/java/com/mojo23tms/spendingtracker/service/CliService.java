package com.mojo23tms.spendingtracker.service;

import com.mojo23tms.spendingtracker.model.Expense;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class CliService {

    ExpenseService es;

    public CliService(ExpenseService es) {
        this.es = es;
    }

    public int readMenuChoice(Scanner sc) {
        String choice = sc.nextLine();
        try {
            int parsedChoice = Integer.parseInt(choice);
            if (parsedChoice > 6 || parsedChoice < 0) {
                throw new IllegalArgumentException("Wrong choice! Specify number from 0 to 6 depending on menu option!");
            }
            return Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Wrong input! Specify number from 0 to 6 depending on menu option!");
        }
    }

    public String optionAddExpense(int amount, String category, String description) {
        es.addExpense(amount, category, description);
        List<Expense> allExpense = es.getAllExpenses();
        return "Expense is added: \n" + allExpense.get(allExpense.size() - 1);
    }

    public void optionShowAllExpense() {
        for (Expense expense : es.getAllExpenses()) {
            System.out.println(expense);
        }
    }

    public String optionShowTotalSpent() {
        return "Total amount spent: $" + es.getTotalSpent();
    }

    public String optionShowSpentByCategory(String category) {
        return "Total amount by category \"" + category + "\": $" + es.getTotalSpent(category) + "\n";
    }

    public String optionUpdateExpense(long id, int amount, String category, String description) {
        es.updateExpenseById(id, amount, category, description);
        return "Expense updated!";
    }

    public String optionDeleteExpense(long id) {
        es.deleteExpense(id);
        return "Expense removed!";
    }


}
