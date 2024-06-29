package bitcamp.project1;

import java.util.Scanner;

public class App {
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
        // Print alternating colors for the stars
        printAlternatingStars();

        System.out.println("\u001B[33m┌─┐┌─┐┌─┐┌─┐┬ ┬┌┐┌┌┬┐  ┌┐ ┌─┐┌─┐┬┌─\u001B[0m");
        System.out.println("\u001B[33m├─┤│  │  │ ││ ││││ │   ├┴┐│ ││ │├┴┐\u001B[0m");
        System.out.println("\u001B[33m┴ ┴└─┘└─┘└─┘└─┘┘└┘ ┴   └─┘└─┘└─┘┴ ┴\u001B[0m");

        // Print alternating colors for the stars again
        printAlternatingStars();

        // Print menu options with specified colors
        System.out.println("\u001B[33m1.\u001B[0m 추가");
        System.out.println("\u001B[33m2.\u001B[0m 내역");
        System.out.println("\u001B[33m3.\u001B[0m 수정");
        System.out.println("\u001B[34m4. 종료\u001B[0m");

        // Print alternating colors for the stars once more
        printAlternatingStars();

        // Print the input prompt in yellow
        System.out.print("\u001B[33m입력: \u001B[0m");
    }

    private static void printAlternatingStars() {
        String stars = "***********************************";
        boolean isYellow = true;

        for (int i = 0; i < stars.length(); i++) {
            if (isYellow) {
                System.out.print("\u001B[33m" + stars.charAt(i) + "\u001B[0m");  // 노란색
            } else {
                System.out.print("\u001B[34m" + stars.charAt(i) + "\u001B[0m");  // 파란색
            }
            isYellow = !isYellow;
        }
        System.out.println();
    }

    private static int getUserChoice() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                System.out.print("\u001B[33m입력: \u001B[0m");
            }
        }
    }
}
