package com.mojo23tms.spendingtracker.controller;

import com.mojo23tms.spendingtracker.dto.CreateExpenseRequest;
import com.mojo23tms.spendingtracker.model.Expense;
import com.mojo23tms.spendingtracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @GetMapping
    public List<Expense> getAll() {
        return service.getAllExpenses();
    }

    @PostMapping
    public Expense postExpense(@RequestBody CreateExpenseRequest request) {
        return service.addExpense(request.amount(), request.category(), request.description());
    }
}
