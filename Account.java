public class Account {

    private String firstName;
    private String lastName;
    private double balance;
    private String phoneNumber;
    private int id;

    public Account(String firstName, String lastName, String phoneNumber, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.balance = 0.0;
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public double getBalance() {
        return this.balance;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public int getID() {
        return this.id;
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    public void depositMoney(double depositAmount) {
        this.balance += depositAmount;
        System.out.println("Deposited " + depositAmount);
    }

    public void withdrawMoney(double withdrawalAmount) {
        this.balance -= withdrawalAmount;
        System.out.println("Withdrew " + withdrawalAmount);
    }

    
}
