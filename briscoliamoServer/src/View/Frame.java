package View;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame() {
        setTitle("Briscoliamo Server");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Aggiunta del pannello principale con BorderLayout
        setLayout(new BorderLayout());

        // Pannello di stato in alto
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        add(statusPanel, BorderLayout.NORTH);

        // Aggiunta del ServerPanel al centro
        add(new ServerPanel(), BorderLayout.CENTER);

        // Visualizzazione della finestra
        setVisible(true);
    }
}
