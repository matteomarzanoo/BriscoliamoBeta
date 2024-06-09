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
    public int currentSFXVolume = 0;

    public SettingsPanel() {
        controller = new MainController();
        background = ImageManager.getBackgroundImage();
        blurredBackground = ImageManager.getBlurred();
        sound = new Sound();
        player = new Player();
        setLayout(new BorderLayout());
        back();
        addSettings();
    }

    private void back() {
        JButton back = new JButton("←");
        back.setFont(Fonts.getMenuPanel());
        back.setOpaque(false);
        back.setContentAreaFilled(false); 
        back.setBorderPainted(false); 
        back.setFocusPainted(false); 
        back.setForeground(Color.WHITE);
        back.setHorizontalAlignment(SwingConstants.LEFT);
        back.addMouseListener(controller);
        add(back, BorderLayout.NORTH);
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

        JPanel leftIntermediate = new JPanel(new GridLayout(7,1));
        JPanel rightIntermediate = new JPanel(new GridLayout(7,1));
        leftIntermediate.setOpaque(false);
        rightIntermediate.setOpaque(false);
        addSettingsName(leftIntermediate);
        addButtonsSetting(rightIntermediate);
        
        intermediate.add(leftIntermediate);
        intermediate.add(rightIntermediate);
        add(intermediate);
    }

    private void addSettingsName(JPanel leftPanel) {
        String[] settings = {"Music", "SFX", "Username", "Table", "Server"};
        for (String names : settings){
            JButton jButton = new JButton(names);
            jButton.setForeground(new Color(229,184,11));
            jButton.setFont(Fonts.getSettingsLeftPanel());
            jButton.setOpaque(false);
            jButton.setContentAreaFilled(false);
            jButton.setBorderPainted(false);
            jButton.setFocusPainted(false);
            jButton.setHorizontalAlignment(SwingConstants.CENTER);
            leftPanel.add(jButton);
        }
    }

    private void addButtonsSetting(JPanel rightPanel) {
        JSlider musicSlider = new JSlider(JSlider.HORIZONTAL, -80, 6, currentOSTVolume);
        musicSlider.setOpaque(false);
        musicSlider.setBorder(null);
        musicSlider.addChangeListener(e -> currentOSTVolume = Sound.ostHandler(musicSlider.getValue()));
        rightPanel.add(musicSlider);

        JSlider sfxSlider = new JSlider(JSlider.HORIZONTAL, -80, 6, currentSFXVolume);
        sfxSlider.setOpaque(false);
        sfxSlider.setBorder(null);
        sfxSlider.addChangeListener(e -> currentSFXVolume = sound.sfxHandler(sfxSlider.getValue()));
        rightPanel.add(sfxSlider);

        JButton user = new JButton("Change Username");
        user.setFont(Fonts.getSettingsRightPanel());
        user.setOpaque(false); 
        user.setContentAreaFilled(false);
        user.setBorderPainted(false);
        user.setFocusPainted(false);
        user.setForeground(Color.WHITE);
        user.addMouseListener(controller);
        rightPanel.add(user);

        JButton colorT = new JButton("Set Color");
        colorT.setFont(Fonts.getSettingsRightPanel());
        colorT.setOpaque(false); 
        colorT.setContentAreaFilled(false);
        colorT.setBorderPainted(false); 
        colorT.setFocusPainted(false); 
        colorT.setForeground(Color.WHITE);
        colorT.addMouseListener(controller);
        rightPanel.add(colorT);

        JButton serverButton = new JButton("Change Settings");
        serverButton.setFont(Fonts.getSettingsRightPanel());
        serverButton.setOpaque(false); 
        serverButton.setContentAreaFilled(false); 
        serverButton.setBorderPainted(false); 
        serverButton.setFocusPainted(false); 
        serverButton.setForeground(Color.WHITE);
        serverButton.addMouseListener(controller);
        rightPanel.add(serverButton);
    }

    public void UsernameDialog() {
        String name = JOptionPane.showInputDialog( "Please enter a username");
        if (name != null && name.trim().isEmpty()){
            name = "defaultPlayer"; 
        }
        
        if (name != null) {
            int res = JOptionPane.showConfirmDialog(null, "Are you sure you want to use " + name + " as username?", "Enter username", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if ( res == JOptionPane.NO_OPTION ) { 
                UsernameDialog(); 
            }
        else { 
            if (res == JOptionPane.YES_OPTION) {
                player.setName(name); 
            } 
        }
        }
    }

    public void tableColor() {
        Color color = JColorChooser.showDialog(this, "Choose a color", Color.WHITE);
        if (color != null) {
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
