package bitcamp.project1.util;

import bitcamp.project1.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class AccountBook {
    private List<bitcamp.project1.Transaction> transactions;

    public AccountBook() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(bitcamp.project1.Transaction transaction) {
        transactions.add(transaction);
    }

    public List<bitcamp.project1.Transaction> getAllTransactions() {
        return transactions;
    }

    public List<bitcamp.project1.Transaction> getTransactionsByType(String type) {
        return transactions.stream()
                .filter(t -> t.getType().equals(type))
                .collect(Collectors.toList());
    }

    public List<bitcamp.project1.Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
                .collect(Collectors.toList());
    }

    public List<bitcamp.project1.Transaction> getTransactionsByTypeAndDateRange(String type, LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(t -> t.getType().equals(type))
                .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
                .collect(Collectors.toList());
    }

    public double getTotalIncome() {
        return transactions.stream()
                .filter(t -> t.getType().equals("수입"))
                .mapToDouble(bitcamp.project1.Transaction::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        return transactions.stream()
                .filter(t -> t.getType().equals("지출"))
                .mapToDouble(bitcamp.project1.Transaction::getAmount)
                .sum();
    }

    public double getTotalIncomeByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(t -> t.getType().equals("수입"))
                .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
                .mapToDouble(bitcamp.project1.Transaction::getAmount)
                .sum();
    }

    public double getTotalExpenseByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(t -> t.getType().equals("지출"))
                .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
                .mapToDouble(bitcamp.project1.Transaction::getAmount)
                .sum();
    }

    public void updateTransaction(int index, Transaction newTransaction) {
        if (index >= 0 && index < transactions.size()) {
            transactions.set(index, newTransaction);
        } else {
            System.out.println("잘못된 인덱스입니다.");
        }
    }
}
