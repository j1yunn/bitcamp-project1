package bitcamp.project1;

import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private int amount;
    private String type; // "수입" 또는 "지출"
    private String description;

    public Transaction(LocalDate date, int amount, String type, String description) {
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getAmount() {
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
        return "날짜: " + date +
                ", 금액: " + amount + "원" +
                ", 유형: " + type +
                ", 설명: " + description;
    }
}
