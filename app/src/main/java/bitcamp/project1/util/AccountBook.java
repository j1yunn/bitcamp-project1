package bitcamp.project1.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountBook implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Transaction> transactions;
    private double totalIncome;
    private double totalExpense;

    public AccountBook() {
        this.transactions = new ArrayList<>();
        this.totalIncome = 0;
        this.totalExpense = 0;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        updateSummary(transaction);
    }

    public void updateTransaction(int index, Transaction transaction) {
        if (index >= 0 && index < transactions.size()) {
            Transaction oldTransaction = transactions.get(index);
            if (oldTransaction.getType().equals("수입")) {
                totalIncome -= oldTransaction.getAmount();
            } else if (oldTransaction.getType().equals("지출")) {
                totalExpense -= oldTransaction.getAmount();
            }

            transactions.set(index, transaction);
            updateSummary(transaction);
        }
    }

    public void deleteTransaction(int index) {
        if (index >= 0 && index < transactions.size()) {
            Transaction transaction = transactions.get(index);
            if (transaction.getType().equals("수입")) {
                totalIncome -= transaction.getAmount();
            } else if (transaction.getType().equals("지출")) {
                totalExpense -= transaction.getAmount();
            }
            transactions.remove(index);
        }
    }

    public Transaction getTransaction(int index) {
        if (index >= 0 && index < transactions.size()) {
            return transactions.get(index);
        }
        return null;
    }

    public void printTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("내역이 없습니다.");
            return;
        }
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println((i + 1) + ". " + transactions.get(i));
        }
    }

    public void printSummary() {
        System.out.println("총 수입: " + String.format("%.0f원", totalIncome));
        System.out.println("총 지출: " + String.format("%.0f원", totalExpense));
        System.out.println("잔액: " + String.format("%.0f원", totalIncome - totalExpense));
    }

    public void resetSummary() {
        totalIncome = 0;
        totalExpense = 0;
    }

    public static AccountBook loadFromFile() {
        AccountBook accountBook = new AccountBook();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("accountbook.dat"))) {
            accountBook = (AccountBook) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("파일을 불러오는 중 오류가 발생했습니다.");
        }
        return accountBook;
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("accountbook.dat"))) {
            oos.writeObject(this);
            System.out.println("저장되었습니다.");
        } catch (IOException e) {
            System.out.println("파일을 저장하는 중 오류가 발생했습니다.");
        }
    }

    public void updateSummary(Transaction transaction) {
        if (transaction.getType().equals("수입")) {
            totalIncome += transaction.getAmount();
        } else if (transaction.getType().equals("지출")) {
            totalExpense += transaction.getAmount();
        }
    }
}