package com.mojo23tms.spendingtracker.dto;

public record CreateExpenseRequest(int amount, String category, String description) { }
