package bitcamp.project1;

import java.util.Scanner;

public class AppTest {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AccountBook accountBook = new AccountBook();
    private static final TransactionManager transactionManager = new TransactionManager(scanner, accountBook);
    private static final TransactionViewer transactionViewer = new TransactionViewer(scanner, accountBook);

    public static void main(String[] args) {
        while (true) {
            printMainMenu();
            int choice = getUserChoice();

            if (choice == -1) {
                continue; // 잘못된 입력이면 루프의 처음으로 돌아감
            }

            switch (choice) {
                case 1 -> transactionManager.addTransaction();
                case 2 -> transactionViewer.viewTransactions();
                case 3 -> transactionManager.editTransaction();
                case 4 -> {
                    System.out.println("종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 선택입니다. 다시 시도하세요.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("********************************");
        System.out.println("             가계부              ");
        System.out.println("********************************");
        System.out.println("1. 추가");
        System.out.println("2. 내역");
        System.out.println("3. 수정");
        System.out.println("4. 종료");
        System.out.println("********************************");
    }

    private static int getUserChoice() {
        System.out.print("입력: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            return -1;
        }
    }
}
