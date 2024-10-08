package fr.um3.agar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                displayGUI();
            }
        });
    }

    private static void displayGUI() {
        JFrame frame = new JFrame("Agar Window");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // Pour utiliser la position absolue des éléments

        JLabel test_label = new JLabel("Test");
        test_label.setBounds(50, 50, 100, 30); // (x, y, largeur, hauteur)
        frame.add(test_label);

        JButton test_button = new JButton("Pause");
        test_button.setBounds(50, 100, 100, 30);
        frame.add(test_button);

        frame.setVisible(true);

        // UpdateData = classe pour calculer les positions etc.
        // updated = instance de UpdateData placée dans un thread séparé
        UpdateData updated = new UpdateData(test_label, test_button);
        new Thread(updated).start();
    }
}
