import banking.BankAccount;
import banking.CurrentAccount;
import banking.Loanable;
import banking.SavingsAccount;
import banking.TransactionLogger;
import java.util.ArrayList;
import java.util.Scanner;

public class BankingSystemMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //bank account references allow both savings and current account objects to be handled by using polymorphism
        ArrayList<BankAccount> accounts = new ArrayList<>();

        BankAccount savings = new SavingsAccount(
                "9080009302332",
                "Qhoakeli Sello",
                1000.00
        );

        BankAccount current = new CurrentAccount(
                "62875007463",
                "Tlotliso Nkhetse",
                2000.00
        );

        accounts.add(savings);
        accounts.add(current);

        int choice = 0;

        do {
            System.out.println("\n===== BANKING SYSTEM =====");
            System.out.println("1. Display all accounts");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. Transfer money");
            System.out.println("5. Calculate interest");
            System.out.println("6. Request loan");
            System.out.println("7. View account details");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            
            if (!scanner.hasNextInt()) {
                String invalidChoice = scanner.next();
                System.out.println("Invalid menu choice. Please enter a number.");
                
                TransactionLogger.logTransaction(
                    "SYSTEM",
                    "Menu",
                    0,
                    0,
                    "Failed - Invalid menu input: " + invalidChoice
                );
                continue;
            }
            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\n===== ALL ACCOUNTS =====");

                    for (BankAccount account : accounts) {
                        account.displayAccountDetails();
                        System.out.println("------------------------");
                    }
                    break;

                case 2:
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.next();

                    BankAccount account = findAccount(accounts, accountNumber);

                    if (account == null) {
                        System.out.println("Account not found.");

                        TransactionLogger.logTransaction(
                                accountNumber,
                                "Deposit",
                                0,
                                0,
                                "Failed - Account not found"
                        );

                        break;
                    }
                    
                    System.out.print("Enter deposit amount: ");
                    
                    if (!scanner.hasNextDouble()) {
                        String invalidInput = scanner.next();
                        System.out.println("Invalid input. Please enter a number.");
                        
                        TransactionLogger.logTransaction(
                            account.getAccountNumber(),
                            "Deposit",
                            0,
                            account.getBalance(),
                            "Failed - Invalid numeric input: " + invalidInput
                        );
                        break;
                    }
                    double amount = scanner.nextDouble();

                    try {
                        account.deposit(amount);

                        System.out.println("Deposit successful.");
                        System.out.println("New balance: M" + account.getBalance());

                        TransactionLogger.logTransaction(
                                account.getAccountNumber(),
                                "Deposit",
                                amount,
                                account.getBalance(),
                                "Successful"
                        );

                    } catch (IllegalArgumentException e) {

                        System.out.println("Deposit failed: " + e.getMessage());

                        TransactionLogger.logTransaction(
                                account.getAccountNumber(),
                                "Deposit",
                                amount,
                                account.getBalance(),
                                "Failed - " + e.getMessage()
                        );
                    }

                    break;

                case 3:
                    System.out.print("Enter account number: ");
                    String withdrawalAccountNumber = scanner.next();
                        
                    BankAccount withdrawalAccount =
                    findAccount(accounts, withdrawalAccountNumber);
                        
                        if (withdrawalAccount == null) 
                            {
                            System.out.println("Account not found.");
                            
                            TransactionLogger.logTransaction(
                                withdrawalAccountNumber,
                                "Withdrawal",
                                0,
                                0,
                                "Failed - Account not found"
                            );
                            break;
                        }
                        
                        System.out.print("Enter withdrawal amount: ");
                        
                        if (!scanner.hasNextDouble()) {
                            String invalidInput = scanner.next();
                            System.out.println("Invalid amount. Please enter a number.");
                            
                            TransactionLogger.logTransaction(
                                withdrawalAccount.getAccountNumber(),
                                "Withdrawal",
                                0,
                                withdrawalAccount.getBalance(),
                                "Failed - Invalid numeric input: " + invalidInput
                            );
                            break;
                        }
                        double withdrawalAmount = scanner.nextDouble();
                        
                        try {
                            withdrawalAccount.withdraw(withdrawalAmount);
                            
                            System.out.println("Withdrawal successful.");
                            
                            System.out.println(
                                "New balance: M" + withdrawalAccount.getBalance()
                            );
                            
                            TransactionLogger.logTransaction(
                                withdrawalAccount.getAccountNumber(),
                                "Withdrawal",
                                withdrawalAmount,
                                withdrawalAccount.getBalance(),
                                "Successful"
                            );
                        
                        } 
                        catch (IllegalArgumentException e) {
                            
                            System.out.println(
                                "Withdrawal failed: " + e.getMessage()
                            );
                            
                            TransactionLogger.logTransaction(
                                withdrawalAccount.getAccountNumber(),
                                "Withdrawal",
                                withdrawalAmount,
                                withdrawalAccount.getBalance(),
                                "Failed - " + e.getMessage()
                            );
                        }
                        break;

                case 4:
                    System.out.print("Enter sender account number: ");
                    String senderNumber = scanner.next();

                    BankAccount sender = findAccount(accounts, senderNumber);

                    System.out.print("Enter receiver account number: ");
                    String receiverNumber = scanner.next();

                    BankAccount receiver = findAccount(accounts, receiverNumber);
                    
                    System.out.print("Enter transfer amount: ");
                    
                    if (!scanner.hasNextDouble()) {
                        String invalidInput = scanner.next();
                        
                        System.out.println("Invalid input. Please enter a number.");
                        
                        TransactionLogger.logTransaction(
                            senderNumber,
                            "Transfer",
                            0,
                            sender == null ? 0 : sender.getBalance(),
                            "Failed - Invalid numeric input: " + invalidInput
                        );
                        break;
                    }
                    
                    double transferAmount = scanner.nextDouble();

                    if (sender == null || receiver == null) {

                    System.out.println("Transfer failed: Account not found.");

                    TransactionLogger.logTransaction(
                        senderNumber,
                        "Transfer",
                        transferAmount,
                        sender == null ? 0 : sender.getBalance(),
                        "Failed - Account not found"
                    );
                    
                    break;
                }
                
                if (sender == receiver) {
                    System.out.println("Transfer failed: Cannot transfer to the same account.");
                    
                    TransactionLogger.logTransaction(
                        senderNumber,
                        "Transfer",
                        transferAmount,
                        sender.getBalance(),
                        "Failed - Same account"
                    );
                    
                    break;
                }
                
                if (transferAmount > sender.getBalance()) {
                    System.out.println("Transfer failed: Insufficient funds.");
                    
                    TransactionLogger.logTransaction(
                        sender.getAccountNumber(),
                        "Transfer",
                        transferAmount,
                        sender.getBalance(),
                        "Failed - Insufficient funds"
                    );
                    break;
                }
                
                try {
                    sender.withdraw(transferAmount);
                    receiver.deposit(transferAmount);
                    
                    System.out.println("Transfer successful.");
                    System.out.println("Sender new balance: M" + sender.getBalance());
                    System.out.println("Receiver new balance: M" + receiver.getBalance());
                    
                    TransactionLogger.logTransaction(
                        sender.getAccountNumber(),
                        "Transfer Sent",
                        transferAmount,
                        sender.getBalance(),
                        "Successful"
                    );
                    
                    TransactionLogger.logTransaction(
                        receiver.getAccountNumber(),
                        "Transfer Received",
                        transferAmount,
                        receiver.getBalance(),
                        "Successful"
                    );
                
                } catch (IllegalArgumentException e) {
                    
                    System.out.println("Transfer failed: " + e.getMessage());
                    
                    TransactionLogger.logTransaction(
                        sender.getAccountNumber(),
                        "Transfer",
                        transferAmount,
                        sender.getBalance(),
                        "Failed - " + e.getMessage()
                    );
                }
                
                break;

                case 5:
                    System.out.print("Enter account number: ");
                    String interestAccountNumber = scanner.next();

                    BankAccount interestAccount =
                    findAccount(accounts, interestAccountNumber);
                    
                    if (interestAccount == null) {
                        System.out.println("Account not found.");
                        
                        TransactionLogger.logTransaction(
                            interestAccountNumber,
                            "Interest Calculation",
                            0,
                            0,
                            "Failed - Account not found"
                        );
                        break;
                    }
                    
                    double previousBalance = interestAccount.getBalance();
                    
                    interestAccount.calculateInterest();
                    
                    System.out.println(
                        "Updated balance: M" + interestAccount.getBalance()
                    );
                    
                    TransactionLogger.logTransaction(
                        interestAccount.getAccountNumber(),
                        "Interest Calculation",
                        interestAccount.getBalance() - previousBalance,
                        interestAccount.getBalance(),
                        "Successful"
                    );
                    
                    break;

                case 6:
                    System.out.print("Enter account number: ");
                    String loanAccountNumber = scanner.next();
                    
                    BankAccount loanAccount = findAccount(accounts, loanAccountNumber);
                    
                    if (loanAccount == null) {
                        System.out.println("Account not found.");
                        
                        break;
                    }
                    
                    System.out.print("Enter loan amount: M");
                    
                    if (!scanner.hasNextDouble()) {
                        String invalidInput = scanner.next();
                        
                        System.out.println("Invalid input. Please enter a number.");
                        
                        TransactionLogger.logTransaction(
                            loanAccount.getAccountNumber(),
                            "Loan Request",
                            0,
                            loanAccount.getBalance(),
                            "Failed - Invalid numeric input: " + invalidInput
                        );
                        break;
                    }
                    double loanAmount = scanner.nextDouble();
                    
                    try {
                        Loanable loanableAccount = (Loanable) loanAccount;
                        loanableAccount.requestLoan(loanAmount);
                        
                        TransactionLogger.logTransaction(
                            loanAccount.getAccountNumber(),
                            "Loan Request",
                            loanAmount,
                            loanAccount.getBalance(),
                            "Successful"
                        );
                    
                    } catch (IllegalArgumentException e) {
                        System.out.println("Loan request failed: " + e.getMessage());
                        
                        TransactionLogger.logTransaction(
                            loanAccount.getAccountNumber(),
                            "Loan Request",
                            loanAmount,
                            loanAccount.getBalance(),
                            "Failed - " + e.getMessage()
                        );
                    }
                    
                    break;

                case 7:
                    System.out.print("Enter account number: ");
                    String detailsAccountNumber = scanner.next();
                    
                    BankAccount detailsAccount = findAccount(accounts, detailsAccountNumber);
                    
                    if (detailsAccount == null) {
                        System.out.println("Account not found.");
                    } else {
                        detailsAccount.displayAccountDetails();
                    }
                    
                    break;

                case 8:
                    System.out.println("Thank you for using the Banking System.");
                    break;

                default:
                    System.out.println("Invalid menu choice.");
            }

        } while (choice != 8);

        scanner.close();
    }

    public static BankAccount findAccount(
            ArrayList<BankAccount> accounts,
            String accountNumber) {

        for (BankAccount account : accounts) {

            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }

        return null;
    }
}