package com.mojo23tms.spendingtracker.controller;

import com.mojo23tms.spendingtracker.dto.CreateExpenseRequest;
import com.mojo23tms.spendingtracker.model.Expense;
import com.mojo23tms.spendingtracker.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Expense postExpense(@RequestBody CreateExpenseRequest request) {
        try {
            return service.addExpense(request.amount(), request.category(), request.description());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
