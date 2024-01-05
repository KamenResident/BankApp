import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LoginWindow extends JFrame {

    private Bank bank;

    public LoginWindow(Bank bank) {
        this.bank = bank;
        init();
        createComponents();
    }

    private void init() {
        setTitle("Login");
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(1000, 1000);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createComponents() {
        JPanel loginPanel = new JPanel();
        GroupLayout loginLayout = new GroupLayout(loginPanel);
        loginPanel.setSize(500, 500);
        loginPanel.setLayout(loginLayout);
        loginPanel.setOpaque(false);
        loginLayout.setAutoCreateGaps(true);
        loginLayout.setAutoCreateContainerGaps(true);

        JTextField usernameField = new JTextField("Username");
        JTextField passwordField = new JTextField("Password");
        JTextArea failMessage = new JTextArea();

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.GREEN);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean loginSuccess = bank.login(usernameField.getText(),
                                                passwordField.getText());
                if (loginSuccess) {
                    setVisible(false);
                    bank.setVisible(true);
                } else {
                    failMessage.setText("Wrong credentials. Try again");
                }
            }
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(Color.ORANGE);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                bank.manageWindows(0);
            }
        });

        loginLayout.setHorizontalGroup(
            loginLayout.createSequentialGroup()
            .addGroup(loginLayout.createParallelGroup(GroupLayout.Alignment.LEADING))
                .addComponent(failMessage)
                .addComponent(usernameField)
                .addComponent(passwordField)
                .addComponent(loginButton)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createSequentialGroup()
            .addGroup(loginLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(failMessage))
            .addComponent(usernameField)
            .addComponent(passwordField)
            .addComponent(loginButton)
        );

        add(loginPanel);
    }
    
}
