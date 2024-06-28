package guis;

import constants.CommonConstants;
import db.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterFormGUI extends Form {
    private Runnable onRegisterSuccess;
    private Runnable onSwitchToLogin;

    public RegisterFormGUI() {
        super("Register");
        addGuiComponents();
    }

    private void addGuiComponents() {
        JLabel registerLabel = new JLabel("Register");

        registerLabel.setBounds(0, 25, 520, 100);

        registerLabel.setForeground(CommonConstants.TEXT_COLOR);
        registerLabel.setFont(new Font("Dialog", Font.BOLD, 40));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(registerLabel);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(30, 150, 400, 25);
        usernameLabel.setForeground(CommonConstants.TEXT_COLOR);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        JTextField usernameField = new JTextField();
        usernameField.setBounds(30, 185, 450, 55);
        usernameField.setBackground(CommonConstants.SECONDARY_COLOR);
        usernameField.setForeground(CommonConstants.TEXT_COLOR);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 24));

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(30, 255, 400, 25);
        passwordLabel.setForeground(CommonConstants.TEXT_COLOR);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(30, 285, 450, 55);
        passwordField.setBackground(CommonConstants.SECONDARY_COLOR);
        passwordField.setForeground(CommonConstants.TEXT_COLOR);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 24));

        JLabel rePasswordLabel = new JLabel("Re-enter Password: ");
        rePasswordLabel.setBounds(30, 365, 400, 25);
        rePasswordLabel.setForeground(CommonConstants.TEXT_COLOR);
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        JPasswordField rePasswordField = new JPasswordField();
        rePasswordField.setBounds(30, 395, 450, 55);
        rePasswordField.setBackground(CommonConstants.SECONDARY_COLOR);
        rePasswordField.setForeground(CommonConstants.TEXT_COLOR);
        rePasswordField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(passwordLabel);
        add(passwordField);

        add(usernameLabel);
        add(usernameField);

        add(rePasswordLabel);
        add(rePasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Dialog", Font.BOLD, 18));

        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.setBackground(CommonConstants.TEXT_COLOR);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get username
                String username = usernameField.getText();

                //get password
                String password = new String(passwordField.getPassword());

                // get repassword
                String rePassword = new String(rePasswordField.getPassword());

                // validate user input
                if (validateUserInput(username, password, rePassword)) {
                    if (MyJDBC.register(username, password)) {
                        // dispose the GUI
                        RegisterFormGUI.this.dispose();

                        // get login GUI back
                        LoginFormGUI loginFormGUI = new LoginFormGUI();
                        loginFormGUI.setVisible(true);

                        // create result dialog
                        JOptionPane.showMessageDialog(loginFormGUI,
                                "Registered Account Successfully!");

                        if (onRegisterSuccess != null) {
                            onRegisterSuccess.run();
                        }

                    } else {
                        // regis failed
                        JOptionPane.showMessageDialog(RegisterFormGUI.this,
                                "Error: Username already taken.");
                    }
                } else {
                    // invalid user input
                    JOptionPane.showMessageDialog(RegisterFormGUI.this,
                            "Error: username must be at least 6 characters \n" +
                            "and/or Passwords must match!");
                }
            }
        });
        registerButton.setBounds(125, 520, 250, 50);
        add(registerButton);

        JLabel loginLabel = new JLabel("Have an account? Login Here");
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.setForeground(CommonConstants.TEXT_COLOR);

        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterFormGUI.this.dispose();
                new LoginFormGUI().setVisible(true);
            }
        });
        loginLabel.setBounds(125, 600, 250, 30);
        add(loginLabel);
    }

    private boolean validateUserInput(String username, String password, String rePasswrod) {
        // all should have value
        if (username.length() == 0 || password.length() == 0 || rePasswrod.length() == 0) return false;

        if (username.length() < 6) return false;

        if (!password.equals(rePasswrod)) return false;

        // passes validation
        return true;
    }

    public void setOnRegisterSuccess(Runnable onRegisterSuccess) {
        this.onRegisterSuccess = onRegisterSuccess;
    }

    public void setOnSwitchToLogin(Runnable onSwitchToLogin) {
        this.onSwitchToLogin = onSwitchToLogin;
    }
}
