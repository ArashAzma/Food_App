package clientFx;

public class Bank2 extends Bank{
    //doesnt allow

    public Bank2(String accountNumber, String passWord, String cvv2, int day, int year) {
        super(accountNumber, passWord, cvv2, day, year);
    }
    @Override
    public void deposit(double amount) {
        if (amount<50){
            super.cost=-1;
        }
        else{
            super.cost = amount*super.tax+amount;
        }
    }
}
