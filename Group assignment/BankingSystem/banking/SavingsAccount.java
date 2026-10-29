package banking;

//savings account inherits common features from bank account and implements loanables
public class SavingsAccount extends BankAccount implements Loanable 
{

    private static final double MINIMUM_BALANCE = 100.00;
    private static final double INTEREST_RATE = 0.05;
    private static final double MAX_LOAN = 5000.00;

    public SavingsAccount(String accountNumber, String accountHolderName, double balance) 
    {
        super(accountNumber, accountHolderName, balance);
    }

    //methods are overiden to apply savings account specific rules
    @Override
    public void deposit(double amount) 
    {
        if (amount <= 0) 
            {
            throw new IllegalArgumentException("Deposit must be greater than zero.");
        }

        setBalance(getBalance() + amount);
    }

    //override method applied
    @Override
    public void withdraw(double amount) 
    {
        if (amount <= 0) 
            {
            throw new IllegalArgumentException("Withdrawal must be greater than zero.");
        }

        if (getBalance() - amount < MINIMUM_BALANCE) 
            {
            throw new IllegalArgumentException(
                "Savings account must maintain a minimum balance of M100.00."
            );
        }

        setBalance(getBalance() - amount);
    }

    //override method applied
    @Override
    public void calculateInterest() 
    {
        double interest = getBalance() * INTEREST_RATE;
        setBalance(getBalance() + interest);

        System.out.println("Interest added: M" + interest);
    }

    //override method applied
    @Override
    public void requestLoan(double amount) 
    {
        if (amount <= 0) 
            {
            throw new IllegalArgumentException("Loan amount must be greater than zero.");
        }

        if (amount > MAX_LOAN) 
            {
            throw new IllegalArgumentException(
                "Savings account loan limit is M5000.00."
            );
        }

        System.out.println("Loan request of M" + amount + " approved for processing.");
    }
}