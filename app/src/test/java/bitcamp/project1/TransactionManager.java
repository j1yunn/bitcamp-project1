package bitcamp.project1;

import java.time.LocalDate;
import java.util.Scanner;

public class TransactionManager {
    private final Scanner scanner;
    private final AccountBook accountBook;
    private final TransactionViewer transactionViewer;

    public TransactionManager(Scanner scanner, AccountBook accountBook) {
        this.scanner = scanner;
        this.accountBook = accountBook;
        this.transactionViewer = new TransactionViewer(scanner, accountBook);
    }

    public void addTransaction() {
        System.out.println("********************************");
        System.out.println("              추가               ");
        System.out.println("********************************");
        System.out.println("1. 수입 추가");
        System.out.println("2. 지출 추가");
        System.out.println("3. 이전");
        System.out.println("********************************");
        int addChoice = getValidChoice();
        if (addChoice == 3) return;

        Transaction transaction = createTransaction(addChoice);
        accountBook.addTransaction(transaction);
        System.out.println("거래가 추가되었습니다.");
    }

    public void editTransaction() {
        transactionViewer.viewAllTransactions();

        int index = -1;
        while (true) {
            System.out.print("수정할 거래의 인덱스 (0번: 이전으로 돌아가기): ");
            try {
                index = Integer.parseInt(scanner.nextLine());
                if (index == 0) return;

                if (index > 0 && index <= accountBook.getTransactions().size()) {
                    break;
                } else {
                    System.out.println("잘못된 인덱스입니다. 다시 시도해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            }
        }

        Transaction newTransaction = createTransaction(accountBook.getTransactions().get(index - 1).getType().equals("수입") ? 1 : 2);
        accountBook.editTransaction(index - 1, newTransaction);
    }

    private int getValidChoice() {
        System.out.print("입력: ");
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1 || choice == 2 || choice == 3) {
                    return choice;
                } else {
                    System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            }
        }
    }

    private Transaction createTransaction(int addChoice) {
        LocalDate date;
        int amount;
        String description;

        while (true) {
            try {
                System.out.print("날짜(YYYY-MM-DD): ");
                date = LocalDate.parse(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 날짜를 YYYY-MM-DD 형식으로 입력해주세요.");
            }
        }

        while (true) {
            try {
                System.out.print("금액: ");
                amount = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            }
        }

        String type = (addChoice == 1) ? "수입" : "지출";

        System.out.print("설명: ");
        description = scanner.nextLine();

        return new Transaction(date, amount, type, description);
    }
}
