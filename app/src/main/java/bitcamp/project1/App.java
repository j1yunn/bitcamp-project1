package bitcamp.project1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        AccountBook accountBook = AccountBook.loadFromFile();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("옵션을 선택하세요: ");
            System.out.println("1. 수입 추가");
            System.out.println("2. 지출 추가");
            System.out.println("3. 거래 내역 보기");
            System.out.println("4. 요약 보기");
            System.out.println("5. 종료");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();  // 개행 문자 소비

                switch (choice) {
                    case 1:
                        System.out.println("날짜 입력 (YYYY-MM-DD): ");
                        String dateIncome = scanner.nextLine();
                        System.out.println("금액 입력: ");
                        double amountIncome = scanner.nextDouble();
                        scanner.nextLine();  // 개행 문자 소비
                        System.out.println("설명 입력: ");
                        String descriptionIncome = scanner.nextLine();
                        accountBook.addTransaction(new Transaction(dateIncome, "income", amountIncome, descriptionIncome));
                        break;
                    case 2:
                        System.out.println("날짜 입력 (YYYY-MM-DD): ");
                        String dateExpense = scanner.nextLine();
                        System.out.println("금액 입력: ");
                        double amountExpense = scanner.nextDouble();
                        scanner.nextLine();  // 개행 문자 소비
                        System.out.println("설명 입력: ");
                        String descriptionExpense = scanner.nextLine();
                        accountBook.addTransaction(new Transaction(dateExpense, "expense", amountExpense, descriptionExpense));
                        break;
                    case 3:
                        accountBook.printTransactions();
                        break;
                    case 4:
                        accountBook.printSummary();
                        break;
                    case 5:
                        scanner.close();
                        accountBook.saveToFile();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("잘못된 옵션입니다. 다시 시도하세요.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                scanner.nextLine();  // 잘못된 입력을 소비하고 버퍼를 비웁니다.
            }
        }
    }
}
