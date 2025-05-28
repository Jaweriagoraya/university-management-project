package universitymanagement;

import javax.swing.*;
import javax.swing.border.LineBorder; // Import for border
import java.awt.*;

public class Splash extends JFrame implements Runnable {

    Thread t;

    Splash() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/first.png"));
        Image i2 = i1.getImage().getScaledInstance(1200, 700, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);

        // Adding black and white border
        img.setBorder(new LineBorder(Color.WHITE, 5)); // White inner border
        img.setBorder(new LineBorder(Color.BLACK, 10)); // Black outer border

        add(img);

        t = new Thread(this);
        t.start();

        setVisible(true);
        int x = 1;
        for (int i = 2; i <= 600; i += 4, x += 1) {
            setLocation(600 - ((i + x) / 2), 350 - (i / 2));
            setSize(5 * x, i + x / 2);

            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        try {
            Thread.sleep(5000); // Display for 5 seconds
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Splash();
    }
}
