package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
        init();
    }
    

    private void init() {
        JPanel panel = new JPanel(new GridLayout(1,2)){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                if (blurredBackground != null) { 
                    g.drawImage(blurredBackground, 0, 0, getWidth(), getHeight(), this); 
                }
            }
        };

        String[] names = {"←", "Play Offline", "Play Online"};
        for (String name : names) {
            buttons(name, panel);
        }
        add(panel, BorderLayout.CENTER);
    }

    private void buttons(String name, JPanel panel) {
        JButton jButton = new JButton(name);
        jButton.setFont(Fonts.getMenuPanel());
        jButton.setOpaque(false); 
        jButton.setContentAreaFilled(false); 
        jButton.setBorderPainted(false); 
        jButton.setFocusPainted(false);
        jButton.setForeground(Color.WHITE);
        jButton.addMouseListener(controller);
        panel.add(jButton);

        if (jButton.getText().equals("←")) {
            jButton.setHorizontalAlignment(SwingConstants.LEFT);
            add(jButton, BorderLayout.NORTH);
        } else {
            panel.add(jButton);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null){
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
