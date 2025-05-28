package universitymanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckResult extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public CheckResult() {
        setTitle("Check Result");
        setSize(1100, 500); // Increased frame size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color to black
        getContentPane().setBackground(Color.BLACK);

        // Define a larger font
        Font largerFont = new Font("Arial", Font.BOLD, 18);

        JButton btnResult = createButton("Fetch Results", largerFont);
        JButton btnBack = createButton("Back", largerFont);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK); // Set button panel background
        buttonPanel.add(btnResult);
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.NORTH);

        String[] columns = {"Name", "Roll No", "DOB", "Address", "Phone", "Email", "Class X", "Class XII", "Aadhar", "Course", "Branch"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setFont(largerFont);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnResult.addActionListener(e -> fetchResultsFromDatabase());
        btnBack.addActionListener(e -> dispose());

        setVisible(true);
    }

    private JButton createButton(String title, Font font) {
        JButton button = new JButton(title);
        button.setFont(font);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        return button;
    }

    private void fetchResultsFromDatabase() {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                String query = "SELECT name, rollno, dob, address, phone, email, classX, classXII, aadhar, course, branch FROM students";
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();

                model.setRowCount(0); // Clear old data before adding new rows

                while (rs.next()) {
                    Object[] row = {
                            rs.getString("name"),
                            rs.getString("rollno"),
                            rs.getString("dob"),
                            rs.getString("address"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getFloat("classX"),
                            rs.getFloat("classXII"),
                            rs.getString("aadhar"),
                            rs.getString("course"),
                            rs.getString("branch")
                    };
                    model.addRow(row);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Database connection failed!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CheckResult());
    }
}
