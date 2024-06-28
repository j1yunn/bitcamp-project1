package bitcamp.project1;

import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private double amount;
    private String type; // "수입" 또는 "지출"
    private String description;

    public Transaction(LocalDate date, double amount, String type, String description) {
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", amount=" + String.format("%.0f원", amount) +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
