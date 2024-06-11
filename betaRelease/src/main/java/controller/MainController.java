package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import model.ColorAnimation;
import model.Sound;
import view.MenuPanel;

public class MainController extends MouseAdapter {
    private ColorAnimation colorAnimations;
    private Sound sound;

    public MainController() {
        colorAnimations = new ColorAnimation();
        sound = Sound.getInstance();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JButton jButton) {
            try {
                sound.sfx("hitted_button.wav");
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1 ) {}
            colorAnimations.exited(jButton);
            String buttonText = jButton.getText();
            MenuPanel.getInstance().getMenuState().getSelectedPanel(buttonText);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof JButton jButton) {
            try {
                sound.sfx("button.wav");
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1 ) {}
            colorAnimations.entered(jButton);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) { 
        if (e.getSource() instanceof JButton jButton) { 
            colorAnimations.exited(jButton);
        }
    }
}
