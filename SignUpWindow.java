import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SignUpWindow extends JFrame {

    private Bank bank;

    public SignUpWindow(Bank bank) {
        this.bank = bank;
        init();
        createComponents();
    }

    private void init() {
        setTitle("Sign Up");
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(1000, 1000);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createComponents() {
        JPanel signUpPanel = new JPanel();
        GroupLayout signUpLayout = new GroupLayout(signUpPanel);
        signUpPanel.setSize(500, 500);
        signUpPanel.setLayout(signUpLayout);
        signUpPanel.setOpaque(false);
        signUpLayout.setAutoCreateGaps(true);
        signUpLayout.setAutoCreateContainerGaps(true);

        JTextArea failMessage = new JTextArea();
        JTextField firstNameField = new JTextField("First Name");
        JTextField lastNameField = new JTextField("Last Name");
        JTextField usernameField = new JTextField("Username");
        JTextField passwordField = new JTextField("Password");
        JTextField numberField = new JTextField("Phone Number");
        JButton checkingsButton = new JButton("Checking");
        JButton savingsButton = new JButton("Savings");

        String[] credentials = new String[] { firstNameField.getText(),
                                            lastNameField.getText(),
                                            usernameField.getText(),
                                            passwordField.getText(),
                                            numberField.getText() };
        addButtonListener(checkingsButton, credentials, 0,
                                            Color.GREEN,
                                            failMessage);
        addButtonListener(savingsButton, credentials, 1,
                                            Color.YELLOW,
                                            failMessage);

        add(signUpPanel);
    }

    private void addButtonListener(JButton button, 
                                    String[] userCredentials, 
                                    int choice, 
                                    Color color, 
                                    JTextArea textArea) {
        button.setBackground(color);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean signUpSuccess = bank.signUp(userCredentials[0], 
                                        userCredentials[1], 
                                        userCredentials[2], 
                                        userCredentials[3], 
                                        userCredentials[4], choice);
                if (signUpSuccess) {
                    setVisible(false);
                    bank.manageWindows(1);
                } else {
                    textArea.setText("Incorrect Credentials");
                }
            }

        });
    }
    
}
