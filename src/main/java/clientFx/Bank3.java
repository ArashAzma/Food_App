package clientFx;

public class Bank3 extends Bank{

    public Bank3(String accountNumber, String passWord, String cvv2, int day, int year) {
        super(accountNumber, passWord, cvv2, day, year);
    }
    @Override
    public void deposit(double amount) {
        super.cost = amount*super.tax+amount;
    }
}
