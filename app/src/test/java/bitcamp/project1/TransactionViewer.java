package bitcamp.project1;

import java.time.LocalDate;
import java.util.Scanner;

public class TransactionViewer {
    private final Scanner scanner;
    private final AccountBook accountBook;

    public TransactionViewer(Scanner scanner, AccountBook accountBook) {
        this.scanner = scanner;
        this.accountBook = accountBook;
    }

    public void viewTransactions() {
        System.out.println("********************************");
        System.out.println("              \033[1m\033[31m내역\033[0m               ");
        System.out.println("********************************");
        System.out.println("1. 전체 거래 보기");
        System.out.println("2. 특정 날짜 범위의 거래 보기");
        System.out.println("3. 수입만 보기");
        System.out.println("4. 지출만 보기");
        System.out.println("5. 총 수입/지출 보기");
        System.out.println("6. 이전");
        System.out.println("********************************");
        System.out.print("입력: ");

        int viewChoice = getUserChoice();
        if (viewChoice == 6) return;

        switch (viewChoice) {
            case 1 -> printAllTransactions(accountBook);
            case 2 -> printTransactionsByDateRange();
            case 3 -> printTransactionsByType("수입");
            case 4 -> printTransactionsByType("지출");
            case 5 -> printTotalIncomeAndExpense();
            default -> System.out.println("잘못된 선택입니다. 다시 시도하세요.");
        }
    }

    public static void printAllTransactions(AccountBook accountBook) {
        System.out.println("거래 목록:");
        for (int i = 0; i < accountBook.getAllTransactions().size(); i++) {
            System.out.println((i + 1) + ": " + accountBook.getAllTransactions().get(i));
        }
    }

    private void printTransactionsByDateRange() {
        try {
            System.out.print("시작 날짜 (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());
            System.out.print("끝 날짜 (YYYY-MM-DD): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine());
            System.out.println("거래 목록:");
            for (Transaction t : accountBook.getTransactionsByDateRange(startDate, endDate)) {
                System.out.println(t);
            }
            double totalIncome = accountBook.getTotalIncomeByDateRange(startDate, endDate);
            double totalExpense = accountBook.getTotalExpenseByDateRange(startDate, endDate);
            System.out.println("특정 기간 내 총 수입: " + String.format("%.0f원", totalIncome));
            System.out.println("특정 기간 내 총 지출: " + String.format("%.0f원", totalExpense));
            System.out.println("특정 기간 내 합계: " + String.format("%.0f원", (totalIncome - totalExpense)));
        } catch (Exception e) {
            System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
        }
    }

    private void printTransactionsByType(String type) {
        System.out.println(type + " 목록:");
        for (Transaction t : accountBook.getTransactionsByType(type)) {
            System.out.println(t);
        }
    }

    private void printTotalIncomeAndExpense() {
        double totalIncome = accountBook.getTotalIncome();
        double totalExpense = accountBook.getTotalExpense();
        System.out.println("총 수입: " + String.format("%.0f원", totalIncome));
        System.out.println("총 지출: " + String.format("%.0f원", totalExpense));
        System.out.println("합계: " + String.format("%.0f원", (totalIncome - totalExpense)));
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            return -1;
        }
    }
}
