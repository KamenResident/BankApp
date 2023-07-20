public class Bank {

    public Bank() {
        
    }

    public void moneyTransfer(Account sender, Account recipient, double transferAmount) {
        if (sender.getBalance() > 0) {
            recipient.setBalance(recipient.getBalance() + transferAmount);
            sender.setBalance(sender.getBalance() - transferAmount);
        } else {
            System.out.println("Insufficient funds");
        }
    }
}
