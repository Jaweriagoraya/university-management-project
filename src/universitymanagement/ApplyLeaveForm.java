package universitymanagement;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ApplyLeaveForm extends JFrame {
    private JTextField empIdField, dateField;
    private JComboBox<String> durationCombo;

    public ApplyLeaveForm() {
        setTitle("Apply Leave (Teacher)");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Set background color to black
        getContentPane().setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Define larger font
        Font largerFont = new Font("Arial", Font.BOLD, 18);

        // Components
        JLabel empIdLabel = createLabel("Employee ID:", largerFont);
        empIdField = createTextField(largerFont);

        JLabel dateLabel = createLabel("Leave Date (YYYY-MM-DD):", largerFont);
        dateField = createTextField(largerFont);

        JLabel durationLabel = createLabel("Duration:", largerFont);
        durationCombo = createComboBox(largerFont, new String[]{
                "Full Day", "Half Day - Morning", "Half Day - Afternoon"
        });

        JButton submitButton = createButton("Submit", largerFont);
        JButton cancelButton = createButton("Cancel", largerFont);

        // Layout adjustments
        gbc.gridx = 0; gbc.gridy = 0;
        add(empIdLabel, gbc);
        gbc.gridx = 1;
        add(empIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(dateLabel, gbc);
        gbc.gridx = 1;
        add(dateField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(durationLabel, gbc);
        gbc.gridx = 1;
        add(durationCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(submitButton, gbc);
        gbc.gridx = 1;
        add(cancelButton, gbc);

        submitButton.addActionListener(e -> saveLeaveRequest());
        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField createTextField(Font font) {
        JTextField textField = new JTextField(10);
        textField.setFont(font);
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        return textField;
    }

    private JComboBox<String> createComboBox(Font font, String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(font);
        return comboBox;
    }

    private JButton createButton(String title, Font font) {
        JButton button = new JButton(title);
        button.setFont(font);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        return button;
    }

    private void saveLeaveRequest() {
        String empId = empIdField.getText();
        String leaveDate = dateField.getText();
        String duration = durationCombo.getSelectedItem().toString();

        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                String query = "INSERT INTO leave_requests (employee_id, leave_date, duration) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);

                pstmt.setString(1, empId);
                pstmt.setString(2, leaveDate);
                pstmt.setString(3, duration);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Leave request submitted successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to submit leave request.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Database connection error!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ApplyLeaveForm());
    }
}
