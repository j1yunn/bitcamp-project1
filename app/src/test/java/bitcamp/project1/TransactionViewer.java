package bitcamp.project1;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class TransactionViewer {
    private final Scanner scanner;
    private final AccountBook accountBook;

    public TransactionViewer(Scanner scanner, AccountBook accountBook) {
        this.scanner = scanner;
        this.accountBook = accountBook;
    }

    public void viewTransactions() {
        while (true) {
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
                case 3 -> viewTransactionsByType("수입");
                case 4 -> viewTransactionsByType("지출");
                case 5 -> printTotalIncomeAndExpense();
                default -> System.out.println("잘못된 선택입니다. 다시 시도하세요.");
            }
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
            LocalDate startDate = getDateFromUser("시작");
            LocalDate endDate = getDateFromUser("끝");
            System.out.println("거래 목록:");
            for (Transaction t : accountBook.getTransactionsByDateRange(startDate, endDate)) {
                System.out.println(t);
            }
            double totalIncome = accountBook.getTotalIncomeByDateRange(startDate, endDate);
            double totalExpense = accountBook.getTotalExpenseByDateRange(startDate, endDate);
            System.out.println("특정 기간 내 총 수입: " + String.format("%.0f원", totalIncome));
            System.out.println("특정 기간 내 총 지출: " + String.format("%.0f원", totalExpense));
            System.out.println("특정 기간 내 합계: " + String.format("%.0f원", (totalIncome - totalExpense)));
        } catch (DateTimeParseException e) {
            System.out.println("잘못된 입력입니다. 날짜를 YYYY-MM-DD 형식으로 입력해주세요.");
        }
    }

    private void viewTransactionsByType(String type) {
        while (true) {
            System.out.println("********************************");
            System.out.println("          \033[1m\033[31m" + type + " 보기\033[0m           ");
            System.out.println("********************************");
            System.out.println("1. 전체 " + type + " 보기");
            System.out.println("2. 특정 날짜 범위의 " + type + " 보기");
            System.out.println("3. 이전");
            System.out.println("********************************");
            System.out.print("입력: ");

            int choice = getUserChoice();
            if (choice == 3) return;

            switch (choice) {
                case 1 -> printTransactionsByType(type, null, null);
                case 2 -> printTransactionsByType(type, getDateFromUser("시작"), getDateFromUser("끝"));
                default -> System.out.println("잘못된 선택입니다. 다시 시도하세요.");
            }
        }
    }

    private void printTransactionsByType(String type, LocalDate startDate, LocalDate endDate) {
        System.out.println(type + " 목록:");
        if (startDate == null || endDate == null) {
            for (Transaction t : accountBook.getTransactionsByType(type)) {
                System.out.println(t);
            }
        } else {
            for (Transaction t : accountBook.getTransactionsByTypeAndDateRange(type, startDate, endDate)) {
                System.out.println(t);
            }
            double total = type.equals("수입") ?
                    accountBook.getTotalIncomeByDateRange(startDate, endDate) :
                    accountBook.getTotalExpenseByDateRange(startDate, endDate);
            System.out.println("특정 기간 내 총 " + type + ": " + String.format("%.0f원", total));
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
            return -1;
        }
    }

    private LocalDate getDateFromUser(String prefix) {
        while (true) {
            try {
                System.out.print(prefix + " 날짜 (YYYY-MM-DD): ");
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("잘못된 입력입니다. 날짜를 YYYY-MM-DD 형식으로 입력해주세요.");
            }
        }
    }
}
