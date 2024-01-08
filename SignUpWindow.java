import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * 
 */
public class SignUpWindow extends JFrame {

    /**
     * 
     */
    private Bank bank;

    /**
     * 
     * 
     * @param bank
     */
    public SignUpWindow(Bank bank) {
        this.bank = bank;
        init();
        createComponents();
    }

    /**
     * 
     */
    private void init() {
        setTitle("Sign Up");
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(500, 500);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
    }

    /**
     * 
     */
    private void createComponents() {
        JPanel signUpPanel = new JPanel();
        GridBagLayout signUpLayout = new GridBagLayout();
        Border panelBorder = BorderFactory.createLineBorder(Color.BLACK);
        signUpPanel.setLayout(signUpLayout);
        signUpPanel.setMaximumSize(new Dimension(350,  350));
        signUpPanel.setBackground(Color.YELLOW);
        signUpPanel.setBorder(panelBorder);
        GridBagConstraints gbc = new GridBagConstraints();

        String[] names = new String[] { "First Name", "Last Name", "Username", 
                                                                    "Password", 
                                                                    "Phone Number", 
                                                                    "Email", 
                                                                    "State", 
                                                                    "City", 
                                                                    "ZIP" };
        JLabel[] labels = new JLabel[9];
        JTextField[] fields = new JTextField[9];
        gbc.fill = GridBagConstraints.HORIZONTAL;   
        for (int i = 0; i < labels.length; i++) {
            JLabel newLabel = new JLabel(names[i], JLabel.CENTER);
            newLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            labels[i] = newLabel;
            gbc.gridx = 0;
            gbc.gridy = i;
            signUpPanel.add(labels[i], gbc);
            JTextField newField = new JTextField();
            newField.setPreferredSize(new Dimension(200, 30));
            fields[i] = newField;
            gbc.gridx = 1;
            gbc.gridy = i;
            signUpPanel.add(fields[i], gbc);
        }

        JPanel buttonPanel = new JPanel();
        FlowLayout buttonLayout = new FlowLayout(FlowLayout.CENTER);
        buttonPanel.setLayout(buttonLayout);
        buttonPanel.setMaximumSize(new Dimension(350, 50));
        buttonPanel.setBackground(Color.ORANGE);
        buttonPanel.setBorder(panelBorder);

        JButton checkingsButton = new JButton("Checking");
        JButton savingsButton = new JButton("Savings");

        addButtonListener(checkingsButton, fields, 0,
                                            Color.GREEN,
                                            names);
        addButtonListener(savingsButton, fields, 1,
                                            Color.YELLOW,
                                            names);

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.RED);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                bank.manageWindows(1);
            }
        });

        buttonPanel.add(checkingsButton);
        buttonPanel.add(savingsButton);
        buttonPanel.add(backButton);
        JLabel signUpLabel = new JLabel("Sign up as a new user.", JLabel.CENTER);
        signUpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font header = new Font("serif", Font.PLAIN, 24);
        signUpLabel.setFont(header);

        add(signUpLabel);
        add(signUpPanel);
        add(buttonPanel);
    }

    /**
     * Used to allow users to sign up for the application.
     * 
     * @param credentials is an array of credentials entered by the user.
     * @param choice determines if the user wants to make a checkings or savings account.
     * @return true if sign up with valid credentials is successful, false otherwise.
     */
    private boolean signUp(JTextField[] credentials, String[] categories, int choice, JButton button) {
        boolean signedIn = false;
        boolean[] validCredentials = new boolean[9];
        validCredentials[0] = credentials[0].getText().length() > 1;
        validCredentials[1] = credentials[1].getText().length() > 1;        
        validCredentials[2] = !bank.checkUsername(credentials[2].getText());
        validCredentials[3] = bank.checkPassword(credentials[3].getText());
        validCredentials[4] = credentials[4].getText().length() == 10;
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
        Pattern emailPattern = Pattern.compile(emailRegex);
        validCredentials[5] = !credentials[5].getText().isEmpty() 
                                && emailPattern.matcher(credentials[5].getText()).matches();
        validCredentials[6] = credentials[6].getText().matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
        validCredentials[7] = credentials[7].getText().matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
        validCredentials[8] = credentials[8].getText().length() == 5;

        int index = 0;
        boolean signedUp = true;
        while (index < 9 && signedUp) {
            if (!validCredentials[index]) {
                signedUp = false;     
                JOptionPane.showMessageDialog(button, 
                                                String.format("The following field is invalid: %s", 
                                                                categories[index]));       
            } else {
                index++;
            }
        }
   
        String address = String.format("%s, %s %s", credentials[6].getText(), 
                                                            credentials[7].getText(), 
                                                            credentials[8].getText());
        if (signedUp) {
            Account newAccount;
            if (choice == 0) {
                newAccount = new CheckingAccount(credentials[0].getText(),
                                            credentials[1].getText(),
                                            credentials[2].getText(), 
                                            credentials[3].getText(), 
                                            credentials[4].getText(), 
                                            credentials[5].getText(), 
                                            address, 
                                            bank.getUID());
            } else {
                newAccount = new SavingsAccount(credentials[0].getText(),
                                            credentials[1].getText(),
                                            credentials[2].getText(), 
                                            credentials[3].getText(), 
                                            credentials[4].getText(), 
                                            credentials[5].getText(), 
                                            address, 
                                            bank.getUID());
            }
            bank.addNewAccount(newAccount);
            JOptionPane.showMessageDialog(button, "New account created!");
        }
        return signedIn;
    }

    /**
     * 
     * 
     * @param button
     * @param userCredentials
     * @param choice
     * @param color
     * @param labels
     */
    private void addButtonListener(JButton button, 
                                    JTextField[] userCredentials, 
                                    int choice, 
                                    Color color,
                                    String[] labels) {
        button.setBackground(color);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean signUpSuccess = signUp(userCredentials, labels, choice, button);
                if (signUpSuccess) {
                    setVisible(false);
                    bank.manageWindows(1);
                }
            }

        });
    }
    
}
