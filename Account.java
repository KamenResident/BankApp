import java.util.Date;

public class Account {

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private double balance;

    private String phoneNumber;

    private int id;

    private double annualInterestRate;

    private Date dateOfCreation;

    public Account() {
        firstName = "";
        lastName = "";
        username = "";
        password = "";
        phoneNumber = "";
        balance = 0;
        id = 0;
        annualInterestRate = 0;
        dateOfCreation = new Date();
    }

    public Account(String firstName, String lastName, String username, String password, String phoneNumber, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.balance = 0;
        this.id = id;
        annualInterestRate = 0;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int newId) {
        this.id = newId;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(double newRate) {
        annualInterestRate = newRate;
    }

    public double getMonthlyInterestRate() {
        return annualInterestRate / 12;
    }

    public double getMonthlyInterest() {
        return balance * (getMonthlyInterestRate() / 100);
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void deposit(double depositAmount) {
        this.balance += depositAmount;
        System.out.println("Deposited " + depositAmount);
    }

    public void withdraw(double withdrawalAmount) {
        this.balance -= withdrawalAmount;
        System.out.println("Withdrew " + withdrawalAmount);
    }

}
