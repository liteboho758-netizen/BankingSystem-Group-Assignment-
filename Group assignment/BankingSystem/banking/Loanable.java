package banking;

//interface defining the loan operation that the right accoount types must provide
public interface Loanable
{
    void requestLoan(double amount);
}