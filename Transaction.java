import java.time.LocalDateTime;

public class Transaction {

    private LocalDateTime dateOfTransaction;

    private String transactionType;

    private double transactionAmount;

    public Transaction(String transactionType, double transactionAmount, LocalDateTime dateOfTransaction) {
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.dateOfTransaction = dateOfTransaction;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public LocalDateTime getDateOfTransaction() {
        return dateOfTransaction;
    }

    @Override
    public String toString() {
        return transactionType + " : " + transactionAmount + " : " + dateOfTransaction;
    }
    
}
