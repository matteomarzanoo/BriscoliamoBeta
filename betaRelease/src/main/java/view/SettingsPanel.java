package view;

import controller.MainController;
import model.Fonts;
import model.ImageManager;
import model.Player;
import model.Sound;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SettingsPanel extends JPanel 
{
    private BufferedImage background;
    private BufferedImage blurredBackground;
    private MainController controller;
    private Sound sound;
    private Player player;
    public int currentOSTVolume = 0;

    public SettingsPanel() {
        controller = new MainController();
        background = ImageManager.getBackgroundImage();
        blurredBackground = ImageManager.getBlurred();
        sound = Sound.getInstance();
        player = new Player();
        setLayout(new BorderLayout());
        addSettings();
    }

    private void addSettings() {
        JPanel intermediate = new JPanel() {
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                if (blurredBackground != null) { 
                    g.drawImage(blurredBackground, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        intermediate.setLayout(new BoxLayout(intermediate, BoxLayout.X_AXIS));

        JPanel left = new JPanel(new GridLayout(3,1));
        left.setOpaque(false);
        addSettingsName(left);

        JPanel right = new JPanel(new GridLayout(3,1));
        right.setOpaque(false);
        addButtonsSetting(right);

        intermediate.add(left);
        intermediate.add(right);
        add(intermediate);
    }

    private void addSettingsName(JPanel panel) {
        String[] settings = {"Music", "Username", "Server"};
        for (String names : settings){
            JLabel label = new JLabel();
            label.setText(names);
            label.setForeground(new Color(229,184,11));
            label.setFont(Fonts.getSettingsLeftPanel());
            label.setOpaque(false);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(label);
        }
    }

    private void addButtonsSetting(JPanel rightPanel) {
        JSlider musicSlider = new JSlider(JSlider.HORIZONTAL, -80, 6, currentOSTVolume);
        musicSlider.setOpaque(false);
        musicSlider.setBorder(null);
        musicSlider.setFocusable(false);
        musicSlider.addChangeListener(e -> currentOSTVolume = sound.ostHandler(musicSlider.getValue()));
        rightPanel.add(musicSlider);

        String[] text = {"←", "Change Username", "Change Settings"};
        for (String texts : text) {
            buttons(texts, rightPanel);
        }
    }

    private void buttons(String name, JPanel panel) {
        JButton button = new JButton(name);
        button.setFont(Fonts.getSettingsRightPanel());
        button.setOpaque(false); 
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.addMouseListener(controller);

        if (name.equals("←")) {
            button.setHorizontalAlignment(SwingConstants.LEFT);
            add(button, BorderLayout.NORTH);
        } else {
            panel.add(button);
        }
    }

    public void UsernameDialog() {
        String name = JOptionPane.showInputDialog( "Please enter a username");
        if (name != null && name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter a valid username", "Warning", JOptionPane.WARNING_MESSAGE); 
            UsernameDialog();
        } else if (name != null) {
            int res = JOptionPane.showConfirmDialog(null, "Are you sure you want to use " + name + " as username?", "Enter username", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if ( res == JOptionPane.NO_OPTION ) { 
                UsernameDialog(); 
            } else { 
            if (res == JOptionPane.YES_OPTION) {
                player.setNickname(name);
            } 
        }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) { 
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
