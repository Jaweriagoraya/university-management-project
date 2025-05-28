package universitymanagement;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeacherForm extends JFrame {

    private JTextField nameField, fatherField, dobField, addressField, phoneField, emailField, aadharField;
    private JComboBox<String> qualificationBox, departmentBox;

    public TeacherForm() {
        setTitle("New Teacher Details");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Set background color to black
        getContentPane().setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Define a larger font
        Font largerFont = new Font("Arial", Font.BOLD, 18);

        // Labels and fields
        String[] labels = {"Name", "Father's Name", "Date of Birth", "Address",
                "Phone", "Email", "Aadhar Number", "Qualification", "Department"};

        nameField = createTextField(largerFont);
        fatherField = createTextField(largerFont);
        dobField = createTextField(largerFont);
        addressField = createTextField(largerFont);
        phoneField = createTextField(largerFont);
        emailField = createTextField(largerFont);
        aadharField = createTextField(largerFont);

        qualificationBox = createComboBox(largerFont, new String[]{"M.Tech", "Ph.D", "B.Ed"});
        departmentBox = createComboBox(largerFont, new String[]{"Computer Science", "Mathematics", "Physics"});

        addRow(gbc, 0, labels[0], nameField, labels[1], fatherField, largerFont);
        addRow(gbc, 1, labels[2], dobField, labels[3], addressField, largerFont);
        addRow(gbc, 2, labels[4], phoneField, labels[5], emailField, largerFont);
        addRow(gbc, 3, labels[6], aadharField, labels[7], qualificationBox, largerFont);
        addRow(gbc, 4, labels[8], departmentBox, "", new JLabel(), largerFont);

        // Buttons
        JButton submitBtn = createButton("Submit", largerFont);
        JButton cancelBtn = createButton("Cancel", largerFont);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(submitBtn, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 2;
        add(cancelBtn, gbc);

        submitBtn.addActionListener(e -> saveTeacherData());
        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void saveTeacherData() {
        String name = nameField.getText().trim();
        String fatherName = fatherField.getText().trim();
        String dob = dobField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String aadhar = aadharField.getText().trim();
        String qualification = qualificationBox.getSelectedItem().toString();
        String department = departmentBox.getSelectedItem().toString();

        if (name.isEmpty() || fatherName.isEmpty() || dob.isEmpty() || address.isEmpty() ||
                phone.isEmpty() || email.isEmpty() || aadhar.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                String query = "INSERT INTO teachers (name, father_name, dob, address, phone, email, aadhar_number, qualification, department) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);

                pstmt.setString(1, name);
                pstmt.setString(2, fatherName);
                pstmt.setString(3, dob);
                pstmt.setString(4, address);
                pstmt.setString(5, phone);
                pstmt.setString(6, email);
                pstmt.setString(7, aadhar);
                pstmt.setString(8, qualification);
                pstmt.setString(9, department);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Teacher details saved successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to save details.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Database connection error!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
        }
    }

    private JTextField createTextField(Font font) {
        JTextField textField = new JTextField();
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

    private void addRow(GridBagConstraints gbc, int row, String label1, Component comp1, String label2, Component comp2, Font font) {
        JLabel lbl1 = new JLabel(label1);
        lbl1.setFont(font);
        lbl1.setForeground(Color.WHITE);

        JLabel lbl2 = new JLabel(label2);
        lbl2.setFont(font);
        lbl2.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = row;
        add(lbl1, gbc);

        gbc.gridx = 1;
        add(comp1, gbc);

        gbc.gridx = 2;
        add(lbl2, gbc);

        gbc.gridx = 3;
        add(comp2, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TeacherForm());
    }
}
