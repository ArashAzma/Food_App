package clientFx;

public class Bank1 extends Bank{
    //more tax

    public Bank1(String accountNumber, String passWord, String cvv2, int day, int year) {
        super(accountNumber, passWord, cvv2, day, year);
        super.tax=0.1;
    }
    @Override
    public void deposit(double amount) {
        super.cost = amount*tax+amount;
    }
}
