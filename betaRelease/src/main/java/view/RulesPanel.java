package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import controller.MainController;
import model.Fonts;
import model.ImageManager;

public class RulesPanel extends JPanel 
{
    private BufferedImage background;
    private BufferedImage blurredBackground;
    private MainController controller;
    
    public RulesPanel() {
        controller = new MainController();
        background = ImageManager.getBackgroundImage();
        blurredBackground = ImageManager.getBlurred();
        setLayout(new BorderLayout());
        addRules();
    }

    private void addRules() {
        addButtonPanel();
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);
        editorPane.setOpaque(false);
        HTMLRules(editorPane);

        JScrollPane scrollPane = new JScrollPane(editorPane) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (blurredBackground != null) {
                    g.drawImage(blurredBackground, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane, BorderLayout.CENTER);
    }

    private void addButtonPanel() {
        JButton jButton = new JButton("←");
        jButton.setFont(Fonts.getMenuPanel());
        jButton.setOpaque(false); 
        jButton.setContentAreaFilled(false); 
        jButton.setBorderPainted(false); 
        jButton.setFocusPainted(false);
        jButton.setForeground(Color.WHITE);
        jButton.setHorizontalAlignment(SwingConstants.LEFT);
        jButton.addMouseListener(controller);
        add(jButton, BorderLayout.NORTH);
    }

    private void HTMLRules(JEditorPane editorPane) {
        try {
            File file = new File("src/main/resources/rules.html");
            editorPane.setPage(file.toURI().toURL());
        } catch (IOException e) {}
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
