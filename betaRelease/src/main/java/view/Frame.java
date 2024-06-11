package view;

import org.example.Settings;

import javax.swing.*;

import java.awt.*;

public class Frame extends JFrame 
{
    public Frame()
    {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e){}
        setTitle("Briscoliamo!");
        setSize(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT);
        setIconImage(loadFrameIcon("src/main/resources/images/gameIcon.png"));

        add(MenuPanel.getInstance());

        setFocusable(true);
        requestFocus();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private Image loadFrameIcon(String path) {
        try {
            return new ImageIcon(path).getImage();
        } catch (Exception e) {
            return null;
        }
    }
}
