package universitymanagement;
import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private CollegeLogin loginSystem;

    public LoginGUI(CollegeLogin loginSystem) {
        this.loginSystem = loginSystem;

        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300); // Increased size
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        // Set background color to black
        getContentPane().setBackground(Color.BLACK);

        // Define a larger font
        Font largerFont = new Font("Arial", Font.BOLD, 16);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(largerFont);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setFont(largerFont);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(largerFont);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(largerFont);
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(largerFont);
        add(loginButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (loginSystem.authenticateUser(username, password)) {
                dispose(); // Close login window
                MainMenu mainMenu = new MainMenu();
                mainMenu.getContentPane().setBackground(Color.LIGHT_GRAY); // MainMenu background
                mainMenu.setVisible(true); // Open Dashboard
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials!");
            }
        });

        setVisible(true);
    }
}
