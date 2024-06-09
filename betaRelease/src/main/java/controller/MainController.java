package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import model.ColorAnimation;
import model.Sound;
import view.MenuPanel;

public class MainController extends MouseAdapter {
    private ColorAnimation colorAnimations;
    private Sound sound;

    public MainController() {
        colorAnimations = new ColorAnimation();
        sound = new Sound();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JButton jButton) {
            
            sound.playClickedButton();
            colorAnimations.exited(jButton);
            String buttonText = jButton.getText();
            MenuPanel.getInstance().getSelectedPanel(buttonText);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof JButton jButton) {
            sound.playEnteredButton();
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
