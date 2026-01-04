package com.mojo23tms.spendingtracker.controller;

import com.mojo23tms.spendingtracker.dto.CreateExpenseRequest;
import com.mojo23tms.spendingtracker.model.Expense;
import com.mojo23tms.spendingtracker.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Expense> getAll() {
        return service.getAllExpenses();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Expense postExpense(@RequestBody CreateExpenseRequest request) {
        return service.addExpense(request.amount(), request.category(), request.description());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void deleteExpense(@PathVariable long id) {
        service.deleteExpense(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/{id}")
    public Expense putExpense(@PathVariable long id, @RequestBody CreateExpenseRequest request) {
        return service.updateExpenseById(id, request.amount(), request.category(), request.description());
    }

}
