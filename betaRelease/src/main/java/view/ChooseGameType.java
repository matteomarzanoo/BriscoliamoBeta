package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.MainController;
import model.Fonts;
import model.ImageManager;

public class ChooseGameType extends JPanel
{
    private BufferedImage background;
    private BufferedImage blurredBackground;
    private MainController controller;
    
    public ChooseGameType(){
        controller = new MainController();
        background = ImageManager.getBackgroundImage();
        blurredBackground = ImageManager.getBlurred();
        setLayout(new BorderLayout());
        addButtonPanel();
        selectedTypeGame();
    }

    private void addButtonPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        JButton jButton = new JButton("←");
        jButton.setFont(Fonts.getMenuPanel());
        jButton.setOpaque(false);
        jButton.setContentAreaFilled(false);
        jButton.setBorderPainted(false);
        jButton.setFocusPainted(false);
        jButton.setForeground(Color.WHITE);
        jButton.addMouseListener(controller);
        add(jButton, BorderLayout.NORTH);
    }

    private void selectedTypeGame() {
        JPanel panel = new JPanel(new GridLayout(1,2)){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                if (blurredBackground != null) { 
                    g.drawImage(blurredBackground, 0, 0, getWidth(), getHeight(), this); 
                }
            }
        };

        String[] names = {"Play Offline", "Play Online"};
        for (String name : names) {
            JButton buttons = new JButton(name);
            buttons.setOpaque(false);
            buttons.setContentAreaFilled(false);
            buttons.setBorderPainted(false);
            buttons.setFocusPainted(false);
            buttons.setForeground(Color.WHITE);
            buttons.setFont(Fonts.getMenuPanel());
            buttons.addMouseListener(controller);
            panel.add(buttons);
        }
        
        add(panel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null){
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
