package bitcamp.project1;

import java.util.ArrayList;
import java.util.List;

public class AccountBook {
    private final List<Transaction> transactions;

    public AccountBook() {
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void editTransaction(int index, Transaction newTransaction) {
        if (index >= 0 && index < transactions.size()) {
            transactions.set(index, newTransaction);
        } else {
            System.out.println("잘못된 인덱스입니다.");
        }
    }
}
