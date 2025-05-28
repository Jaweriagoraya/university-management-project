package universitymanagement;
import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("University Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500); // Increased size
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 1, 10, 10));

        // Set background color to black
        getContentPane().setBackground(Color.BLACK);

        // Define a larger font
        Font largerFont = new Font("Arial", Font.BOLD, 16);

        // Add buttons for each form with white foreground and larger font
        add(createButton("Student Form", () -> new StudentForm(), largerFont));
        add(createButton("Teacher Form", () -> new TeacherForm(), largerFont));
        add(createButton("Apply Leave", () -> new ApplyLeaveForm(), largerFont));
        add(createButton("Enter Marks", () -> new universitymanagement.EnterMarks(), largerFont));
        add(createButton("Check Result", () -> new CheckResult(), largerFont));
        add(createButton("Fee Structure", () -> {
            FeeStructureFrame frame = new FeeStructureFrame();
            frame.setVisible(true);
        }, largerFont));
        add(createButton("Fee Payment Form", () -> new FeePaymentForm().setVisible(true), largerFont));
    }

    private JButton createButton(String title, Runnable action, Font font) {
        JButton button = new JButton(title);
        button.setForeground(Color.WHITE); // Set text color to white
        button.setBackground(Color.BLACK); // Set button background color to black
        button.setFont(font); // Apply larger font
        button.addActionListener(e -> action.run());
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu();
            menu.setVisible(true);
        });
    }
}



