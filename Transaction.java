import java.time.LocalDateTime;

/**
 * Represents a transaction that a user has made.
 * This transaction can either be a withdrawal, deposit, or transfer of money.
 */
public class Transaction {

    private LocalDateTime dateOfTransaction;
    private String transactionType;
    private double transactionAmount;

    /**
     * Constructor for a transaction.
     * 
     * @param transactionType
     * @param transactionAmount
     * @param dateOfTransaction
     */
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
