package bitcamp.project1;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
            System.out.println("          \033[1m\033[31m거래 내역\033[0m           ");
            System.out.println("********************************");
            System.out.println("1. 전체 거래 보기");
            System.out.println("2. 특정 날짜 범위의 거래 보기");
            System.out.println("3. 수입만 보기");
            System.out.println("4. 지출만 보기");
            System.out.println("5. 총 수입/지출 보기");
            System.out.println("6. 이전");
            System.out.println("********************************");
            System.out.print("입력: ");

            int choice = getUserChoice();
            if (choice == -1) {
                continue;
            }

            switch (choice) {
                case 1 -> viewAllTransactions();
                case 2 -> viewTransactionsByDateRange();
                case 3 -> viewTransactionsByType("수입", false);
                case 4 -> viewTransactionsByType("지출", false);
                case 5 -> viewTotalIncomeAndExpense();
                case 6 -> {
                    return;
                }
                default -> System.out.println("잘못된 선택입니다. 다시 시도하세요.");
            }
        }
    }

    public void viewAllTransactions() {
        List<Transaction> transactions = accountBook.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("거래 내역이 없습니다.");
        } else {
            transactions.forEach(System.out::println);
        }
    }

    private void viewTransactionsByDateRange() {
        LocalDate startDate = getDateInput("시작 날짜(YYYY-MM-DD): ");
        LocalDate endDate = getDateInput("종료 날짜(YYYY-MM-DD): ");

        List<Transaction> transactions = accountBook.getTransactions().stream()
                .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
                .collect(Collectors.toList());

        if (transactions.isEmpty()) {
            System.out.println("해당 날짜 범위에 거래 내역이 없습니다.");
        } else {
            transactions.forEach(System.out::println);
        }
    }

    private void viewTransactionsByType(String type, boolean dateRange) {
        List<Transaction> transactions = accountBook.getTransactions().stream()
                .filter(t -> t.getType().equals(type))
                .collect(Collectors.toList());

        if (dateRange) {
            LocalDate startDate = getDateInput("시작 날짜(YYYY-MM-DD): ");
            LocalDate endDate = getDateInput("종료 날짜(YYYY-MM-DD): ");
            transactions = transactions.stream()
                    .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
                    .collect(Collectors.toList());
        }

        if (transactions.isEmpty()) {
            System.out.println("해당 유형의 거래 내역이 없습니다.");
        } else {
            transactions.forEach(System.out::println);
        }
    }

    private void viewTotalIncomeAndExpense() {
        int totalIncome = accountBook.getTransactions().stream()
                .filter(t -> t.getType().equals("수입"))
                .mapToInt(Transaction::getAmount)
                .sum();

        int totalExpense = accountBook.getTransactions().stream()
                .filter(t -> t.getType().equals("지출"))
                .mapToInt(Transaction::getAmount)
                .sum();

        System.out.println("총 수입: " + totalIncome + "원");
        System.out.println("총 지출: " + totalExpense + "원");
    }

    private int getUserChoice() {
        System.out.print("입력: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            return -1;
        }
    }

    private LocalDate getDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("잘못된 입력입니다. 날짜를 YYYY-MM-DD 형식으로 입력해주세요.");
            }
        }
    }
}
