package universitymanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EnterMarks extends JFrame implements ActionListener {

    private JComboBox<String> rollNumberCombo;
    private JComboBox<String> semesterCombo;
    private JTextField[] subjectFields;
    private JTextField[] marksFields;
    private JButton submitButton, backButton;

    public EnterMarks() {
        setTitle("Enter Marks of Student");
        setLayout(null);
        setSize(800, 500);
        setLocation(250, 150);

        // Set background color to black
        getContentPane().setBackground(Color.BLACK);

        // Define font
        Font largerFont = new Font("Arial", Font.BOLD, 18);

        JLabel heading = new JLabel("Enter Marks of Student");
        heading.setFont(largerFont);
        heading.setForeground(Color.WHITE);
        heading.setBounds(50, 20, 400, 30);
        add(heading);

        JLabel rollLabel = new JLabel("Select Roll Number");
        rollLabel.setFont(largerFont);
        rollLabel.setForeground(Color.WHITE);
        rollLabel.setBounds(50, 70, 200, 30);
        add(rollLabel);

        rollNumberCombo = new JComboBox<>(new String[]{"101", "102", "103"}); // Sample roll numbers
        rollNumberCombo.setFont(largerFont);
        rollNumberCombo.setBounds(250, 70, 200, 30);
        add(rollNumberCombo);

        JLabel semesterLabel = new JLabel("Select Semester");
        semesterLabel.setFont(largerFont);
        semesterLabel.setForeground(Color.WHITE);
        semesterLabel.setBounds(50, 110, 200, 30);
        add(semesterLabel);

        semesterCombo = new JComboBox<>(new String[]{
                "1st Semester", "2nd Semester", "3rd Semester",
                "4th Semester", "5th Semester", "6th Semester",
                "7th Semester", "8th Semester"
        });
        semesterCombo.setFont(largerFont);
        semesterCombo.setBounds(250, 110, 200, 30);
        add(semesterCombo);

        JLabel subjectLabel = new JLabel("Enter Subject");
        subjectLabel.setFont(largerFont);
        subjectLabel.setForeground(Color.WHITE);
        subjectLabel.setBounds(50, 160, 200, 30);
        add(subjectLabel);

        JLabel marksLabel = new JLabel("Enter Marks");
        marksLabel.setFont(largerFont);
        marksLabel.setForeground(Color.WHITE);
        marksLabel.setBounds(300, 160, 200, 30);
        add(marksLabel);

        subjectFields = new JTextField[5];
        marksFields = new JTextField[5];

        for (int i = 0; i < 5; i++) {
            subjectFields[i] = createTextField();
            subjectFields[i].setBounds(50, 200 + i * 40, 200, 30);
            add(subjectFields[i]);

            marksFields[i] = createTextField();
            marksFields[i].setBounds(300, 200 + i * 40, 100, 30);
            add(marksFields[i]);
        }

        submitButton = createButton("Submit");
        submitButton.setBounds(50, 400, 120, 40);
        submitButton.addActionListener(this);
        add(submitButton);

        backButton = createButton("Back");
        backButton.setBounds(200, 400, 120, 40);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        return textField;
    }

    private JButton createButton(String title) {
        JButton button = new JButton(title);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            saveMarksToDatabase();
        } else if (e.getSource() == backButton) {
            dispose();
        }
    }

    private void saveMarksToDatabase() {
        String roll = (String) rollNumberCombo.getSelectedItem();
        String semester = (String) semesterCombo.getSelectedItem();

        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                String query = "INSERT INTO marks (roll_number, semester, subject, marks) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);

                for (int i = 0; i < 5; i++) {
                    String subject = subjectFields[i].getText();
                    String marksText = marksFields[i].getText();

                    if (!subject.isEmpty() && !marksText.isEmpty()) {
                        try {
                            float marks = Float.parseFloat(marksText);
                            pstmt.setString(1, roll);
                            pstmt.setString(2, semester);
                            pstmt.setString(3, subject);
                            pstmt.setFloat(4, marks);

                            pstmt.executeUpdate();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Invalid Marks Input for " + subject);
                        }
                    }
                }
                JOptionPane.showMessageDialog(this, "Marks Submitted Successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Database connection error!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EnterMarks());
    }
}
