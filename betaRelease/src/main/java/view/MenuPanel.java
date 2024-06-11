package view;

import javax.swing.*;

import controller.MainController;
import model.Fonts;
import model.ImageManager;
import model.MenuState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuPanel extends JPanel 
{
    private BufferedImage background;
    private MainController controller;
    private MenuState menuState;
    private static MenuPanel instance = null;
    
    private MenuPanel() {
        background = ImageManager.getBackgroundImage();
        controller = new MainController();
        this.menuState = new MenuState();
        setLayout(new BorderLayout());
        addTitle();
        addButtonsPanel();
    }

    public void addTitle() {
        JLabel label = new JLabel("Briscoliamo!", SwingConstants.CENTER);
        label.setFont(Fonts.getLogo());
        label.setForeground(Color.WHITE);
        label.setOpaque(false);
        add(label, BorderLayout.NORTH);
    }

    public void addButtonsPanel() {
        JPanel jPanel = new JPanel(new GridLayout(5,1));
        jPanel.setOpaque(false);

        String[] nameButtons = {"Play Now!", "Statistics", "Rules", "Settings", "Exit"};
        for (String names : nameButtons) {
            JButton jButton = new JButton(names);
            jButton.setOpaque(false);
            jButton.setContentAreaFilled(false);
            jButton.setBorderPainted(false);
            jButton.setFocusPainted(false);
            jButton.setForeground(Color.WHITE);
            jButton.setFont(Fonts.getMenuPanel());
            jButton.addMouseListener(controller);
            jPanel.add(jButton);
        }
        add(jPanel, BorderLayout.CENTER);
    }

    public <T extends Component> void updateView(T panelType) {
        removeAll();
        add(panelType, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public MenuState getMenuState() {
        return menuState;
    }

    public static MenuPanel getInstance() {
        if ( instance == null ) { instance = new MenuPanel(); }
        return instance;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) { 
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

}