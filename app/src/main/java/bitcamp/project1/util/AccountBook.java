package bitcamp.project1.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * AccountBook 클래스는 거래 내역을 관리하고 합계를 계산하는 기능을 제공합니다.
 */
public class AccountBook implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Transaction> transactions; // 거래 내역을 저장할 리스트
    private double totalIncome; // 총 수입
    private double totalExpense; // 총 지출

    /**
     * AccountBook 객체를 생성하면서 초기화합니다.
     */
    public AccountBook() {
        this.transactions = new ArrayList<>();
        this.totalIncome = 0;
        this.totalExpense = 0;
    }

    /**
     * 새로운 거래를 추가합니다.
     *
     * @param transaction 추가할 거래 객체
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        updateSummary(transaction); // 거래를 추가하면서 합계를 업데이트합니다.
    }

    /**
     * 지정된 인덱스의 거래를 수정합니다.
     *
     * @param index        수정할 거래의 인덱스
     * @param transaction  수정할 거래 객체
     */
    public void updateTransaction(int index, Transaction transaction) {
        if (index >= 0 && index < transactions.size()) {
            Transaction oldTransaction = transactions.get(index);
            // 이전 거래의 종류에 따라 총 수입 또는 총 지출에서 이전 금액을 차감합니다.
            if (oldTransaction.getType().equals("수입")) {
                totalIncome -= oldTransaction.getAmount();
            } else if (oldTransaction.getType().equals("지출")) {
                totalExpense -= oldTransaction.getAmount();
            }

            transactions.set(index, transaction); // 거래를 수정합니다.
            updateSummary(transaction); // 수정된 거래를 반영하여 합계를 업데이트합니다.
        }
    }

    /**
     * 지정된 인덱스의 거래를 삭제합니다.
     *
     * @param index 삭제할 거래의 인덱스
     */
    public void deleteTransaction(int index) {
        if (index >= 0 && index < transactions.size()) {
            Transaction transaction = transactions.get(index);
            // 삭제할 거래의 종류에 따라 총 수입 또는 총 지출에서 금액을 차감합니다.
            if (transaction.getType().equals("수입")) {
                totalIncome -= transaction.getAmount();
            } else if (transaction.getType().equals("지출")) {
                totalExpense -= transaction.getAmount();
            }
            transactions.remove(index); // 거래를 삭제합니다.
        }
    }

    /**
     * 지정된 인덱스의 거래를 반환합니다.
     *
     * @param index 가져올 거래의 인덱스
     * @return 거래 객체
     */
    public Transaction getTransaction(int index) {
        if (index >= 0 && index < transactions.size()) {
            return transactions.get(index);
        }
        return null;
    }

    /**
     * 저장된 모든 거래 내역을 화면에 출력합니다.
     */
    public void printTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("내역이 없습니다.");
            return;
        }
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println((i + 1) + ". " + transactions.get(i));
        }
    }

    /**
     * 총 수입, 총 지출, 잔액을 숫자+원 조합으로 화면에 출력합니다.
     */
    public void printSummary() {
        System.out.println("총 수입: " + String.format("%.0f원", totalIncome));
        System.out.println("총 지출: " + String.format("%.0f원", totalExpense));
        System.out.println("잔액: " + String.format("%.0f원", totalIncome - totalExpense));
    }

    /**
     * 총 수입과 총 지출을 초기화합니다.
     */
    public void resetSummary() {
        totalIncome = 0;
        totalExpense = 0;
    }

    /**
     * 파일로부터 AccountBook 객체를 불러옵니다.
     *
     * @return 불러온 AccountBook 객체
     */
    public static AccountBook loadFromFile() {
        AccountBook accountBook = new AccountBook();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("accountbook.dat"))) {
            accountBook = (AccountBook) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("파일을 불러오는 중 오류가 발생했습니다.");
        }
        return accountBook;
    }

    /**
     * 현재 객체를 파일에 저장합니다.
     */
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("accountbook.dat"))) {
            oos.writeObject(this);
            System.out.println("저장되었습니다.");
        } catch (IOException e) {
            System.out.println("파일을 저장하는 중 오류가 발생했습니다.");
        }
    }

    /**
     * 거래를 추가하거나 수정할 때 합계를 업데이트합니다.
     *
     * @param transaction 업데이트할 거래 객체
     */
    public void updateSummary(Transaction transaction) {
        if (transaction.getType().equals("수입")) {
            totalIncome += transaction.getAmount();
        } else if (transaction.getType().equals("지출")) {
            totalExpense += transaction.getAmount();
        }
    }
}