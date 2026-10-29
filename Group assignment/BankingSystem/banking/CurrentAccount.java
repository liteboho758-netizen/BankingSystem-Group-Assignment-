package banking;

//current account inherits from bank account and implements the loanable interface
public class CurrentAccount extends BankAccount implements Loanable {

    private static final double OVERDRAFT_LIMIT = 2000.00;
    private static final double MAX_LOAN = 10000.00;

    public CurrentAccount(String accountNumber, String accountHolderName, double balance) {
        super(accountNumber, accountHolderName, balance);
    }

    //methods are overridden to apply current account specific rules
    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit must be greater than zero.");
        }

        setBalance(getBalance() + amount);
    }

    //override methods applied
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal must be greater than zero.");
        }

        if (getBalance() - amount < -OVERDRAFT_LIMIT) {
            throw new IllegalArgumentException(
                "Withdrawal exceeds the M2000.00 overdraft limit."
            );
        }

        setBalance(getBalance() - amount);
    }

    //override methods applied
    @Override
    public void calculateInterest() {
        System.out.println("Current accounts do not earn interest.");
    }

    @Override
    public void requestLoan(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Loan amount must be greater than zero.");
        }

        if (amount > MAX_LOAN) {
            throw new IllegalArgumentException(
                "Current account loan limit is M10000.00."
            );
        }

        System.out.println("Loan request of M" + amount + " approved for processing.");
    }
}