package bitcamp.project1.vo;

import bitcamp.project1.util.AccountBook;
import bitcamp.project1.util.Transaction;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        AccountBook accountBook = AccountBook.loadFromFile();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMainMenu();
            try {
                int mainChoice = scanner.nextInt();
                scanner.nextLine();  // 개행 문자 소비

                switch (mainChoice) {
                    case 1:
                        inputMenu(accountBook, scanner);
                        break;
                    case 2:
                        historyMenu(accountBook, scanner);
                        break;
                    case 3:
                        summaryMenu(accountBook, scanner);
                        break;
                    case 4:
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

    private static void printMainMenu() {
        System.out.println("********************************");
        System.out.println("             가계부             ");
        System.out.println("********************************");
        System.out.println("1. 입력");
        System.out.println("2. 내역");
        System.out.println("3. 합계");
        System.out.println("4. 종료");
        System.out.println("********************************");
    }

    private static void inputMenu(AccountBook accountBook, Scanner scanner) {
        while (true) {
            System.out.println("********************************");
            System.out.println("              입력              ");
            System.out.println("********************************");
            System.out.println("1. 수입 추가");
            System.out.println("2. 지출 추가");
            System.out.println("3. 이전");
            System.out.println("********************************");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();  // 개행 문자 소비

                switch (choice) {
                    case 1:
                        addTransaction(accountBook, scanner, "수입");
                        break;
                    case 2:
                        addTransaction(accountBook, scanner, "지출");
                        break;
                    case 3:
                        return;  // 이전 메뉴로 돌아감
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

    private static void historyMenu(AccountBook accountBook, Scanner scanner) {
        while (true) {
            System.out.println("********************************");
            System.out.println("              내역              ");
            System.out.println("********************************");
            System.out.println("1. 내역 보기");
            System.out.println("2. 내역 수정");
            System.out.println("3. 내역 삭제");
            System.out.println("4. 이전");
            System.out.println("********************************");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();  // 개행 문자 소비

                switch (choice) {
                    case 1:
                        accountBook.printTransactions();
                        break;
                    case 2:
                        modifyTransaction(accountBook, scanner);
                        break;
                    case 3:
                        deleteTransaction(accountBook, scanner);
                        break;
                    case 4:
                        return;  // 이전 메뉴로 돌아감
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

    private static void summaryMenu(AccountBook accountBook, Scanner scanner) {
        while (true) {
            System.out.println("********************************");
            System.out.println("              합계              ");
            System.out.println("********************************");
            System.out.println("1. 합계 보기");
            System.out.println("2. 초기화");
            System.out.println("3. 이전");
            System.out.println("********************************");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();  // 개행 문자 소비

                switch (choice) {
                    case 1:
                        accountBook.printSummary();
                        break;
                    case 2:
                        accountBook.resetSummary();
                        System.out.println("합계가 초기화되었습니다.");
                        break;
                    case 3:
                        return;  // 이전 메뉴로 돌아감
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

    private static void addTransaction(AccountBook accountBook, Scanner scanner, String type) {
        System.out.println("날짜 입력 (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.println("금액 입력: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // 개행 문자 소비
        System.out.println("설명 입력: ");
        String description = scanner.nextLine();
        accountBook.addTransaction(new Transaction(date, type, amount, description));
    }

    private static void modifyTransaction(AccountBook accountBook, Scanner scanner) {
        accountBook.printTransactions();
        System.out.println("수정할 내역의 번호를 입력하세요: ");
        try {
            int index = scanner.nextInt();
            scanner.nextLine();  // 개행 문자 소비
            Transaction transaction = accountBook.getTransaction(index - 1);
            if (transaction != null) {
                System.out.println("날짜 입력 (현재: " + transaction.getDate() + "): ");
                String newDate = scanner.nextLine();
                System.out.println("금액 입력 (현재: " + transaction.getAmount() + "): ");
                double newAmount = scanner.nextDouble();
                scanner.nextLine();  // 개행 문자 소비
                System.out.println("설명 입력 (현재: " + transaction.getDescription() + "): ");
                String newDescription = scanner.nextLine();

                double oldAmount = transaction.getAmount();
                transaction.setDate(newDate);
                transaction.setAmount(newAmount);
                transaction.setDescription(newDescription);
                accountBook.updateTransaction(index - 1, transaction);

                // 수정된 거래의 종류에 따라 합계를 업데이트합니다.
                if (transaction.getType().equals("수입")) {
                    accountBook.updateSummary(transaction);
                    accountBook.updateSummary(new Transaction(transaction.getDate(), "수입", -oldAmount, ""));
                } else if (transaction.getType().equals("지출")) {
                    accountBook.updateSummary(transaction);
                    accountBook.updateSummary(new Transaction(transaction.getDate(), "지출", -oldAmount, ""));
                }

                System.out.println("내역이 수정되었습니다.");
            } else {
                System.out.println("유효하지 않은 번호입니다.");
            }
        } catch (InputMismatchException | IndexOutOfBoundsException e) {
            System.out.println("잘못된 입력입니다.");
            scanner.nextLine();  // 잘못된 입력을 소비하고 버퍼를 비웁니다.
        }
    }

    private static void deleteTransaction(AccountBook accountBook, Scanner scanner) {
        accountBook.printTransactions();
        System.out.println("삭제할 내역의 번호를 입력하세요: ");
        try {
            int index = scanner.nextInt();
            scanner.nextLine();  // 개행 문자 소비

            Transaction transaction = accountBook.getTransaction(index - 1);
            if (transaction != null) {
                double amount = transaction.getAmount();
                if (transaction.getType().equals("수입")) {
                    accountBook.updateSummary(new Transaction(transaction.getDate(), "수입", -amount, ""));
                } else if (transaction.getType().equals("지출")) {
                    accountBook.updateSummary(new Transaction(transaction.getDate(), "지출", -amount, ""));
                }
                accountBook.deleteTransaction(index - 1);
                System.out.println("내역이 삭제되었습니다.");
            } else {
                System.out.println("유효하지 않은 번호입니다.");
            }
        } catch (InputMismatchException | IndexOutOfBoundsException e) {
            System.out.println("잘못된 입력입니다.");
            scanner.nextLine();  // 잘못된 입력을 소비하고 버퍼를 비웁니다.
        }
    }
}