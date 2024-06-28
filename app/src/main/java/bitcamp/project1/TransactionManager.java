package bitcamp.project1;

import java.time.LocalDate;
import java.util.Scanner;

public class TransactionManager {
    private final Scanner scanner;
    private final AccountBook accountBook;

    public TransactionManager(Scanner scanner, AccountBook accountBook) {
        this.scanner = scanner;
        this.accountBook = accountBook;
    }

    public void addTransaction() {
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

        while (addChoice < 1 || addChoice > 3) {
            System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            System.out.print("입력: ");
            addChoice = getUserChoice();
        }

        try {
            Transaction transaction = createTransaction(addChoice);
            accountBook.addTransaction(transaction);
        } catch (Exception e) {
            System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
        }
    }

    private Transaction createTransaction(int addChoice) {
        LocalDate date = null;
        while (date == null) {
            try {
                System.out.print("날짜 (YYYY-MM-DD): ");
                date = LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }

        Double amount = null;
        while (amount == null) {
            try {
                System.out.print("금액: ");
                amount = Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }

        String type = (addChoice == 1) ? "수입" : "지출";
        System.out.print("설명: ");
        String description = scanner.nextLine();
        return new Transaction(date, amount, type, description);
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
                return null;
            }
        }

        return new Transaction(date, amount, type, description);
    }

    private LocalDate getUpdatedDate(LocalDate originalDate) {
        LocalDate date = null;
        while (date == null) {
            try {
                System.out.print("새 날짜 (YYYY-MM-DD, 이전 값: " + originalDate + "): ");
                String dateInput = scanner.nextLine();
                date = dateInput.isEmpty() ? originalDate : LocalDate.parse(dateInput);
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }
        return date;
    }

    private double getUpdatedAmount(double originalAmount) {
        Double amount = null;
        while (amount == null) {
            try {
                System.out.print("새 금액 (이전 값: " + String.format("%.0f", originalAmount) + "원): ");
                String amountInput = scanner.nextLine();
                amount = amountInput.isEmpty() ? originalAmount : Double.parseDouble(amountInput);
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }
        return amount;
    }

    private String getUpdatedType(String originalType) {
        String type = null;
        while (type == null) {
            try {
                System.out.print("새 유형 (수입/지출, 이전 값: " + originalType + "): ");
                String typeInput = scanner.nextLine();
                type = typeInput.isEmpty() ? originalType : typeInput;
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }
        return type;
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
