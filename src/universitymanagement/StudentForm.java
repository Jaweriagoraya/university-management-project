package universitymanagement;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentForm extends JFrame {

    private JTextField nameField, rollNoField, dobField, addressField, phoneField, emailField, class10Field, class12Field, aadharField;
    private JComboBox<String> courseBox, branchBox;

    public StudentForm() {
        setTitle("New Student Details");
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

        // Labels and input fields
        String[] labels = {"Name", "Roll Number", "Date of Birth", "Address",
                "Phone", "Email", "Class X (%)", "Class XII (%)",
                "Aadhar Number", "Course", "Branch"};

        nameField = createTextField(largerFont);
        rollNoField = createTextField(largerFont);
        dobField = createTextField(largerFont);
        addressField = createTextField(largerFont);
        phoneField = createTextField(largerFont);
        emailField = createTextField(largerFont);
        class10Field = createTextField(largerFont);
        class12Field = createTextField(largerFont);
        aadharField = createTextField(largerFont);

        courseBox = createComboBox(largerFont, new String[]{"OOP", "Database", "Software Engineering", "Calculus"});
        branchBox = createComboBox(largerFont, new String[]{"Computer Science", "Mechanical", "Electrical", "Civil"});

        addRow(gbc, 0, labels[0], nameField, labels[1], rollNoField, largerFont);
        addRow(gbc, 1, labels[2], dobField, labels[3], addressField, largerFont);
        addRow(gbc, 2, labels[4], phoneField, labels[5], emailField, largerFont);
        addRow(gbc, 3, labels[6], class10Field, labels[7], class12Field, largerFont);
        addRow(gbc, 4, labels[8], aadharField, labels[9], courseBox, largerFont);
        addRow(gbc, 5, labels[10], branchBox, "", new JLabel(), largerFont);

        // Buttons
        JButton submitBtn = createButton("Submit", largerFont);
        JButton cancelBtn = createButton("Cancel", largerFont);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(submitBtn, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 2;
        add(cancelBtn, gbc);

        submitBtn.addActionListener(e -> saveStudentData());
        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void saveStudentData() {
        String name = nameField.getText().trim();
        String rollNo = rollNoField.getText().trim();
        String dob = dobField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || rollNo.isEmpty() || dob.isEmpty() || address.isEmpty() ||
                phone.isEmpty() || email.isEmpty() || class10Field.getText().trim().isEmpty() ||
                class12Field.getText().trim().isEmpty() || aadharField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled!");
            return;
        }

        try {
            float classX = Float.parseFloat(class10Field.getText().trim());
            float classXII = Float.parseFloat(class12Field.getText().trim());
            String aadhar = aadharField.getText().trim();
            String course = courseBox.getSelectedItem().toString();
            String branch = branchBox.getSelectedItem().toString();

            try (Connection conn = DBConnection.getConnection()) {
                if (conn != null) {
                    String query = "INSERT INTO students (name, rollno, dob, address, phone, email, classX, classXII, aadhar, course, branch) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(query);

                    pstmt.setString(1, name);
                    pstmt.setString(2, rollNo);
                    pstmt.setString(3, dob);
                    pstmt.setString(4, address);
                    pstmt.setString(5, phone);
                    pstmt.setString(6, email);
                    pstmt.setFloat(7, classX);
                    pstmt.setFloat(8, classXII);
                    pstmt.setString(9, aadhar);
                    pstmt.setString(10, course);
                    pstmt.setString(11, branch);

                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Student details saved successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to save details.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Database connection error!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid numeric input! Please enter valid numbers for Class X and Class XII percentages.");
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
        SwingUtilities.invokeLater(() -> new StudentForm());
    }
}
