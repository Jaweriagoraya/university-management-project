package universitymanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class FeeStructureFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public FeeStructureFrame() {
        setTitle("Fee Structure");
        setSize(1100, 600); // Increased frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Set background color to black
        getContentPane().setBackground(Color.BLACK);

        // Define fonts
        Font titleFont = new Font("Arial", Font.BOLD, 28);
        Font tableFont = new Font("Arial", Font.PLAIN, 18);
        Font buttonFont = new Font("Arial", Font.BOLD, 20);

        // Title Label
        JLabel titleLabel = new JLabel("Fee Structure");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Table Setup
        String[] columns = {"Course", "Semester 1", "Semester 2", "Semester 3", "Semester 4", "Semester 5", "Semester 6", "Semester 7", "Semester 8"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setFont(tableFont);
        table.setRowHeight(30);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(table);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        JButton backButton = createButton("Back", buttonFont);
        JButton okButton = createButton("OK", buttonFont);

        backButton.addActionListener(e -> dispose()); // Close window
        okButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fee Structure Loaded Successfully!"));

        buttonPanel.add(backButton);
        buttonPanel.add(okButton);

        // Adding Components
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        fetchFeeStructureFromDatabase();
    }

    private JButton createButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        return button;
    }

    private void fetchFeeStructureFromDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/college", "root", "JAW_0909_")) {
            if (conn != null) {
                String query = "SELECT * FROM fee_structure";
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();

                model.setRowCount(0); // Clear existing data

                while (rs.next()) {
                    Object[] row = {
                            rs.getString("course"),
                            rs.getFloat("semester1"),
                            rs.getFloat("semester2"),
                            rs.getFloat("semester3"),
                            rs.getFloat("semester4"),
                            rs.getFloat("semester5"),
                            rs.getFloat("semester6"),
                            rs.getFloat("semester7"),
                            rs.getFloat("semester8")
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
        SwingUtilities.invokeLater(FeeStructureFrame::new);
    }
}
