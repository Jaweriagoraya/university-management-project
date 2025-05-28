package universitymanagement;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class FeePaymentForm extends JFrame {
    private JTextField nameField, rollNoField, semesterField, totalPayableField, statusField;
    private JButton payFeeButton, updateButton, backButton;

    public FeePaymentForm() {
        setTitle("Fee Payment");
        setSize(900, 500); // Increased frame size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Set background color to black
        getContentPane().setBackground(Color.BLACK);

        // Define font size
        Font largerFont = new Font("Arial", Font.BOLD, 18);

        createLabel("Enter Name", 30, 30);
        nameField = createTextField(200, 30);

        createLabel("Enter Roll No", 30, 70);
        rollNoField = createTextField(200, 70);

        createLabel("Enter Semester", 30, 110);
        semesterField = createTextField(200, 110);

        createLabel("Total Payable", 30, 150);
        totalPayableField = createTextField(200, 150);

        createLabel("Payment Status", 30, 190);
        statusField = createTextField(200, 190);

        payFeeButton = createButton("Pay Fee", 30, 250);
        updateButton = createButton("Update", 150, 250);
        backButton = createButton("Back", 270, 250);

        payFeeButton.addActionListener(e -> processFeePayment());
        updateButton.addActionListener(e -> updateFeeDetails());
        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setBounds(x, y, 150, 30);
        add(label);
        return label;
    }

    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        textField.setBounds(x, y, 200, 30);
        add(textField);
        return textField;
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setBounds(x, y, 100, 40);
        add(button);
        return button;
    }

    private void processFeePayment() {
        String rollNo = rollNoField.getText().trim();
        String semester = semesterField.getText().trim();

        if (rollNo.isEmpty() || semester.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Roll No and Semester.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/college", "root", "JAW_0909_")) {
            String checkQuery = "SELECT * FROM fee WHERE rollno=? AND semester=?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, rollNo);
            checkStmt.setString(2, semester);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String query = "UPDATE fee SET status='Paid' WHERE rollno=? AND semester=?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, rollNo);
                pstmt.setString(2, semester);
                pstmt.executeUpdate();

                statusField.setText("Paid");
                JOptionPane.showMessageDialog(this, "Fee Paid Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No fee record found! Please check Roll No and Semester.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "SQL Error: " + ex.getMessage());
        }
    }

    private void updateFeeDetails() {
        String name = nameField.getText().trim();
        String rollNo = rollNoField.getText().trim();
        String semester = semesterField.getText().trim();
        String totalPayable = totalPayableField.getText().trim();
        String status = statusField.getText().trim();

        if (name.isEmpty() || rollNo.isEmpty() || semester.isEmpty() || totalPayable.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/college", "root", "JAW_0909_")) {
            String query = "UPDATE fee SET name=?, total_payable=?, status=? WHERE rollno=? AND semester=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setFloat(2, Float.parseFloat(totalPayable));
            pstmt.setString(3, status);
            pstmt.setString(4, rollNo);
            pstmt.setString(5, semester);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Fee details updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.");
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FeePaymentForm::new);
    }
}
