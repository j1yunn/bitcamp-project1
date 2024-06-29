package bitcamp.project1;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class TransactionManager {
    private final Scanner scanner;
    private final AccountBook accountBook;

    public TransactionManager(Scanner scanner, AccountBook accountBook) {
        this.scanner = scanner;
        this.accountBook = accountBook;
    }

    public void addTransaction() {
        while (true) {
            System.out.println("********************************");
            System.out.println("              \033[1m\033[31m추가\033[0m               ");
            System.out.println("********************************");
            System.out.println("1. 수입 추가");
            System.out.println("2. 지출 추가");
            System.out.println("3. 이전");
            System.out.println("********************************");
            System.out.print("입력: ");

            int addChoice = getUserChoice();
            if (addChoice == 3) return;

            try {
                Transaction transaction = createTransaction(addChoice);
                accountBook.addTransaction(transaction);
                break;
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }
    }

    private Transaction createTransaction(int addChoice) {
        LocalDate date = getDateFromUser();
        System.out.print("금액: ");
        double amount = Double.parseDouble(scanner.nextLine());
        String type = (addChoice == 1) ? "수입" : "지출";
        System.out.print("설명: ");
        String description = scanner.nextLine();
        return new Transaction(date, amount, type, description);
    }

    private LocalDate getDateFromUser() {
        while (true) {
            try {
                System.out.print("날짜 (YYYY-MM-DD): ");
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("잘못된 입력입니다. 날짜를 YYYY-MM-DD 형식으로 입력해주세요.");
            }
        }
    }

    public void editTransaction() {
        TransactionViewer.printAllTransactions(accountBook);
        System.out.print("수정할 거래의 인덱스 (0번: 이전으로 돌아가기): ");
        int index = getUserChoice() - 1;
        if (index == -1) return;

        if (index < 0 || index >= accountBook.getAllTransactions().size()) {
            System.out.println("잘못된 인덱스입니다. 다시 시도해주세요.");
            return;
        }

        Transaction originalTransaction = accountBook.getAllTransactions().get(index);
        System.out.println("현재 거래: " + originalTransaction);

        Transaction newTransaction = getUpdatedTransaction(originalTransaction);
        if (newTransaction != null) {
            System.out.print("수정된 정보를 저장하시겠습니까? (y/n): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                accountBook.updateTransaction(index, newTransaction);
                System.out.println("거래가 수정되었습니다.");
            } else {
                System.out.println("수정이 취소되었습니다.");
            }
        }
    }

    private Transaction getUpdatedTransaction(Transaction originalTransaction) {
        LocalDate date = originalTransaction.getDate();
        double amount = originalTransaction.getAmount();
        String type = originalTransaction.getType();
        String description = originalTransaction.getDescription();

        while (true) {
            System.out.println("********************************");
            System.out.println("    수정할 항목을 선택하세요    ");
            System.out.println("********************************");
            System.out.println("1. 날짜");
            System.out.println("2. 금액");
            System.out.println("3. 유형");
            System.out.println("4. 설명");
            System.out.println("5. 이전");
            System.out.println("********************************");
            System.out.print("입력: ");
            int editChoice = getUserChoice();
            if (editChoice == 5) return null;

            switch (editChoice) {
                case 1 -> date = getUpdatedDate(date);
                case 2 -> amount = getUpdatedAmount(amount);
                case 3 -> type = getUpdatedType(type);
                case 4 -> description = getUpdatedDescription(description);
                default -> {
                    System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                    continue;
                }
            }

            return new Transaction(date, amount, type, description);
        }
    }

    private LocalDate getUpdatedDate(LocalDate originalDate) {
        while (true) {
            try {
                System.out.print("새 날짜 (YYYY-MM-DD, 이전 값: " + originalDate + "): ");
                String dateInput = scanner.nextLine();
                return dateInput.isEmpty() ? originalDate : LocalDate.parse(dateInput);
            } catch (DateTimeParseException e) {
                System.out.println("잘못된 입력입니다. 날짜를 YYYY-MM-DD 형식으로 입력해주세요.");
            }
        }
    }

    private double getUpdatedAmount(double originalAmount) {
        System.out.print("새 금액 (이전 값: " + String.format("%.0f", originalAmount) + "원): ");
        String amountInput = scanner.nextLine();
        return amountInput.isEmpty() ? originalAmount : Double.parseDouble(amountInput);
    }

    private String getUpdatedType(String originalType) {
        System.out.print("새 유형 (수입/지출, 이전 값: " + originalType + "): ");
        String typeInput = scanner.nextLine();
        return typeInput.isEmpty() ? originalType : typeInput;
    }

    private String getUpdatedDescription(String originalDescription) {
        System.out.print("새 설명 (이전 값: " + originalDescription + "): ");
        String descriptionInput = scanner.nextLine();
        return descriptionInput.isEmpty() ? originalDescription : descriptionInput;
    }

    private int getUserChoice() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                System.out.print("입력: ");
            }
        }
    }
}
