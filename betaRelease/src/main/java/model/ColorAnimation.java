package model;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ColorAnimation 
{
    private Timer timer;
    private float brightness = 0.0f;

    public void entered(JButton jButton) {
        brightness = 0.0f;
        timer = new javax.swing.Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                brightness += 0.05f;
                if (brightness > 1.0f) {
                    brightness = 1.0f;
                    timer.stop();
                }
                jButton.setForeground(interpolateColor(Color.WHITE, Color.BLACK, brightness));
            }
        });
        timer.start();
    }

    public void exited(JButton jButton) {
        if (timer != null && timer.isRunning()) { 
            timer.stop(); 
        }
        jButton.setForeground(Color.WHITE);
    }
    
    private static Color interpolateColor(Color start, Color end, float ratio) {
        int red = (int) (start.getRed() * (1 - ratio) + end.getRed() * ratio);
        int green = (int) (start.getGreen() * (1 - ratio) + end.getGreen() * ratio);
        int blue = (int) (start.getBlue() * (1 - ratio) + end.getBlue() * ratio);
        return new Color(red, green, blue);
    }
}
