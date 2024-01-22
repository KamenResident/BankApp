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
 * Window for facilitating the interactions with the user
 * for signing up new accounts.
 */
public class SignUpWindow extends JFrame {

    private Bank bank;
    private JLabel[] labels;
    private JTextField[] fields;

    /**
     * Constructor for the window.
     * 
     * @param bank is the bank application using this window.
     */
    protected SignUpWindow(Bank bank, String title) {
        super(title);
        labels = new JLabel[9];
        fields = new JTextField[9];
        this.bank = bank;
        init();
        createComponents();
    }

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

    private void createComponents() {
        // Create first panel to house all of the fields for signing up.
        JPanel signUpPanel = new JPanel(new GridBagLayout());
        Border panelBorder = BorderFactory.createLineBorder(Color.BLACK);
        signUpPanel.setMaximumSize(new Dimension(350,  350));
        signUpPanel.setBackground(Color.YELLOW);
        signUpPanel.setBorder(panelBorder);
        GridBagConstraints gbc = new GridBagConstraints();

        String[] names = new String[] { "First Name:", "Last Name:", "Username:", 
                                                                    "Password:", 
                                                                    "Phone Number:", 
                                                                    "Email:", 
                                                                    "State:", 
                                                                    "City:", 
                                                                    "ZIP:" };
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

        // Create separate panel to house the buttons on the bottom.
        JPanel buttonPanel = new JPanel();
        FlowLayout buttonLayout = new FlowLayout(FlowLayout.CENTER);
        buttonPanel.setLayout(buttonLayout);
        buttonPanel.setMaximumSize(new Dimension(350, 50));
        buttonPanel.setBackground(Color.ORANGE);
        buttonPanel.setBorder(panelBorder);

        JButton checkingsButton = new JButton("Checking");
        JButton savingsButton = new JButton("Savings");

        addButtonListener(checkingsButton, 0, Color.GREEN);
        addButtonListener(savingsButton, 1, Color.YELLOW);

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
     * @param choice determines if the user wants to make a checkings or savings account.
     * @return true if sign up with valid credentials is successful, false otherwise.
     */
    private boolean signUp(int choice, JButton button) {
        boolean signedIn = false;
        boolean[] validCredentials = new boolean[9];
        validCredentials[0] = fields[0].getText().length() > 1;
        validCredentials[1] = fields[1].getText().length() > 1;        
        validCredentials[2] = bank.getBankUtils().findAccount(fields[2].getText()) == null;
        validCredentials[3] = bank.getBankUtils().checkPassword(fields[3].getText());
        validCredentials[4] = fields[4].getText().length() == 10;
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
        Pattern emailPattern = Pattern.compile(emailRegex);
        validCredentials[5] = !fields[5].getText().isEmpty() 
                                && emailPattern.matcher(fields[5].getText()).matches();
        validCredentials[6] = fields[6].getText().matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
        validCredentials[7] = fields[7].getText().matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
        validCredentials[8] = fields[8].getText().length() == 5;

        // Validation check on fields starting from first name.
        int index = 0;
        boolean signedUp = true;
        while (index < 9 && signedUp) {
            if (!validCredentials[index]) {
                signedUp = false;     
                JOptionPane.showMessageDialog(button, 
                                                String.format("The following field is invalid: %s", 
                                                                labels[index].getText()));       
            } else {
                index++;
            }
        }
   
        String address = String.format("%s, %s %s", fields[7].getText(), 
                                                            fields[6].getText(), 
                                                            fields[8].getText());
        if (signedUp) {
            if (choice == 0) {
                bank.addNewAccount(fields[0].getText(),
                                            fields[1].getText(),
                                            fields[2].getText(), 
                                            fields[3].getText(), 
                                            fields[4].getText(), 
                                            fields[5].getText(), 
                                            address, 
                                            0);
            } else {
                bank.addNewAccount(fields[0].getText(),
                                            fields[1].getText(),
                                            fields[2].getText(), 
                                            fields[3].getText(), 
                                            fields[4].getText(), 
                                            fields[5].getText(), 
                                            address, 
                                            1);
            }
            JOptionPane.showMessageDialog(button, "New account created!");
            for (int i = 0; i < fields.length; i++) {
                fields[i].setText("");
            }
            setVisible(false);
            bank.manageWindows(1);
        }

        return signedIn;
    }

    /**
     * Helper method to add functionality to the account creation buttons
     * for checking accounts and savings accounts.
     * 
     * @param button is the button whose functionality is being added.
     * @param userCredentials is the collection of fields to be inspected.
     * @param choice determines if the button is meant for checking or savings accounts.
     * @param color is the color of the button.
     * @param labels is the collection of labels for identifying each field.
     */
    private void addButtonListener(JButton button, int choice, Color color) {
        button.setBackground(color);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean signUpSuccess = signUp(choice, button);
                if (signUpSuccess) {
                    for (int i = 0; i < fields.length; i++) {
                        fields[i].setText("");
                    }
                    setVisible(false);
                    bank.manageWindows(1);
                }
            }

        });
    }
    
}
