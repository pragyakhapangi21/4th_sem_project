package gui;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;
import util.Validation;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField firstNameField;
    private JTextField middleNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;

    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JComboBox<String> roleBox;

    public RegisterFrame() {

        // Window title
        setTitle("Local Guide - Registration");

        // Window size
        setSize(500, 550);

        // Put window in center
        setLocationRelativeTo(null);

        // Close only this window
        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        // Main panel
        JPanel panel = new JPanel();

        panel.setLayout(
                new GridLayout(9, 2, 10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );


        // First Name
        panel.add(
                new JLabel("First Name:")
        );

        firstNameField = new JTextField();

        panel.add(firstNameField);


        // Middle Name
        panel.add(
                new JLabel("Middle Name:")
        );

        middleNameField = new JTextField();

        panel.add(middleNameField);


        // Last Name
        panel.add(
                new JLabel("Last Name:")
        );

        lastNameField = new JTextField();

        panel.add(lastNameField);


        // Email
        panel.add(
                new JLabel("Email:")
        );

        emailField = new JTextField();

        panel.add(emailField);


        // Phone
        panel.add(
                new JLabel("Phone:")
        );

        phoneField = new JTextField();

        panel.add(phoneField);


        // Password
        panel.add(
                new JLabel("Password:")
        );

        passwordField =
                new JPasswordField();

        // Show password as *
        passwordField.setEchoChar('*');

        panel.add(passwordField);


        // Confirm Password
        panel.add(
                new JLabel("Confirm Password:")
        );

        confirmPasswordField =
                new JPasswordField();

        // Show password as *
        confirmPasswordField.setEchoChar('*');

        panel.add(confirmPasswordField);


        // Role
        panel.add(
                new JLabel("Register As:")
        );

        roleBox =
                new JComboBox<>(
                        new String[]{
                                "CUSTOMER",
                                "GUIDE"
                        }
                );

        panel.add(roleBox);


        // Register button
        JButton registerButton =
                new JButton("Register");

        panel.add(registerButton);


        // Cancel button
        JButton cancelButton =
                new JButton("Cancel");

        panel.add(cancelButton);


        // Add panel to frame
        add(panel);


        // Register button action
        registerButton.addActionListener(
                e -> registerUser()
        );


        // Cancel button action
        cancelButton.addActionListener(
                e -> dispose()
        );
    }


    private void registerUser() {

        // Get values from text fields

        String firstName =
                firstNameField
                        .getText()
                        .trim();

        String middleName =
                middleNameField
                        .getText()
                        .trim();

        String lastName =
                lastNameField
                        .getText()
                        .trim();

        String email =
                emailField
                        .getText()
                        .trim();

        String phone =
                phoneField
                        .getText()
                        .trim();


        // Get password from password field

        String password =
                new String(
                        passwordField
                                .getPassword()
                );


        // Get confirm password

        String confirmPassword =
                new String(
                        confirmPasswordField
                                .getPassword()
                );


        // Get selected role

        String role =
                roleBox
                        .getSelectedItem()
                        .toString();


        // -----------------------------
        // VALIDATION
        // -----------------------------


        // First name

        if (!Validation.validNameWord(firstName)) {

            JOptionPane.showMessageDialog(
                    this,
                    "First name must contain letters only."
            );

            return;
        }


        // Middle name

        if (!middleName.isEmpty()
                && !Validation.validNameWord(
                        middleName
                )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Middle name must contain letters only."
            );

            return;
        }


        // Last name

        if (!Validation.validNameWord(lastName)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Last name must contain letters only."
            );

            return;
        }


        // Email

        if (!Validation.validEmail(email)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email."
            );

            return;
        }


        // Phone

        if (!Validation.validPhone(phone)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Phone must be exactly 10 digits and start with 9."
            );

            return;
        }


        // Password

        if (!Validation.validPassword(password)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain:\n"
                    + "• 8-72 characters\n"
                    + "• Uppercase letter\n"
                    + "• Lowercase letter\n"
                    + "• Number\n"
                    + "• Special character"
            );

            return;
        }


        // Confirm password

        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match."
            );

            return;
        }


        // -----------------------------
        // PASSWORD HASHING
        // -----------------------------


        // Generate random salt

        String salt =
                PasswordUtil.generateSalt();


        // Hash password

        String passwordHash =
                PasswordUtil.hashPassword(
                        password,
                        salt
                );


        // -----------------------------
        // CREATE USER OBJECT
        // -----------------------------

        User user = new User();


        user.setFirstName(firstName);


        if (middleName.isEmpty()) {

            user.setMiddleName(null);

        } else {

            user.setMiddleName(middleName);
        }


        user.setLastName(lastName);

        user.setEmail(email);

        user.setPhone(phone);

        user.setPasswordHash(passwordHash);

        user.setPasswordSalt(salt);

        user.setRole(role);


        // -----------------------------
        // SAVE TO DATABASE
        // -----------------------------

        UserDAO userDAO =
                new UserDAO();


        boolean success =
                userDAO.registerUser(user);


        // -----------------------------
        // RESULT
        // -----------------------------

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration successful!"
            );

            // Close registration window
            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed!\n"
                    + "Email or phone may already exist."
            );
        }
    }
}
