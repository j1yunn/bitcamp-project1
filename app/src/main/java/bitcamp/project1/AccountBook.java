package bitcamp.project1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountBook implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Transaction> transactions;
    private double balance;

    public AccountBook() {
        transactions = new ArrayList<>();
        balance = 0.0;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        if (transaction.getType().equals("수입")) {
            balance += transaction.getAmount();
        } else if (transaction.getType().equals("지출")) {
            balance -= transaction.getAmount();
        }
        saveToFile();
    }

    public double getBalance() {
        return balance;
    }

    public void printTransactions() {
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public void printSummary() {
        double totalIncome = 0.0;
        double totalExpense = 0.0;

        for (Transaction transaction : transactions) {
            if (transaction.getType().equals("수입")) {
                totalIncome += transaction.getAmount();
            } else if (transaction.getType().equals("지출")) {
                totalExpense += transaction.getAmount();
            }
        }

        System.out.println("총 수입: " + totalIncome);
        System.out.println("총 지출: " + totalExpense);
        System.out.println("잔액: " + balance);
    }

    public void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("accountbook.dat"))) {
            out.writeObject(this);
        } catch (IOException e) {
            System.out.println("데이터 저장 중 오류 발생: " + e.getMessage());
        }
    }

    public static AccountBook loadFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("accountbook.dat"))) {
            return (AccountBook) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("데이터 불러오기 중 오류 발생: " + e.getMessage());
            return new AccountBook();
        }
    }
}
