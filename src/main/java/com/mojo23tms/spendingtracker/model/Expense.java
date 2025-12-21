package com.mojo23tms.spendingtracker.model;

import java.time.LocalDate;

public class Expense{

    private final int amount;
    private final String category;
    private final String description;
    private final LocalDate date;

    private final long id;

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getAmount() {
        return this.amount;
    }

    public long getId() {
        return this.id;
    }

    public Expense(int amount, String category, String description, long id) {
        this(amount, category, description, id, LocalDate.now());
    }

    public Expense(int amount, String category, String description, long id, LocalDate updatedDate) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = updatedDate;
        this.id = id;
    }

    @Override
    public String toString() {
        return "- ID: " + this.id + "\n" +
                "- Date: " + this.date + "\n" +
                "- Amount spent: $" + this.amount + "\n" +
                "- Category of expense: " + this.category + "\n" +
                "- Description: " + this.description + "\n" +
                "==========";

    }
}
