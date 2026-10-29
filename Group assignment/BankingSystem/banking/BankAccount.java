package banking;

//abstract superclass that provides common structure for all bank accounts
public abstract class BankAccount 
{
    //private fields demonstartes encapsulationby restricting direct access
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public BankAccount(String accountNumber, String accountHolderName, double balance) {

        if (balance < 0) 
            {
            throw new IllegalArgumentException("Opening balance cannot be negative.");
        }

        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    public String getAccountNumber() 
    {
        return accountNumber;
    }

    public String getAccountHolderName() 
    {
        return accountHolderName;
    }

    public double getBalance() 
    {
        return balance;
    }

    protected void setBalance(double balance) 
    {
        this.balance = balance;
    }
    
    //abstarct methods require subclasses to provide their own implementations
    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
    public abstract void calculateInterest();

    public void displayAccountDetails() 
    {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: M" + balance);
    }
}
