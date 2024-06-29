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
        System.out.println("             \033[1m\033[31m가계부\033[0m              ");
        System.out.println("********************************");
        System.out.println("1. 추가");
        System.out.println("2. 내역");
        System.out.println("3. 수정");
        System.out.println("4. 종료");
        System.out.println("********************************");
        System.out.print("입력: ");
    }

    private static int getUserChoice() {
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
