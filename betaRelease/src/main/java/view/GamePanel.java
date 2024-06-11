package view;

import model.Card;
import model.ImageManager;
import model.Player;
import org.example.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public abstract class GamePanel extends JPanel {
    protected Player player;
    protected BufferedImage background;

    public GamePanel(Player player) {
        background = ImageManager.getGamePanelBackgroundScreen();
        this.player = player;
        setVisible(true);
        setFocusable(true);
        updatePlayerCardPositions();
    }

    protected abstract void draw(Graphics2D g2d);

    protected void drawArea(Graphics2D g2d) {
        int x = Settings.PLAYING_AREA_LEFT_BORDER;
        int y = Settings.PLAYING_AREA_UP_BORDER;

        int width = Settings.PLAYING_AREA_RIGHT_BORDER - Settings.PLAYING_AREA_LEFT_BORDER;
        int height = Settings.PLAYING_AREA_DOWN_BORDER - Settings.PLAYING_AREA_UP_BORDER;
        g2d.setColor(Color.WHITE);
        g2d.drawRect(x, y, width, height);
    }

    public void updatePlayerCardPositions() {
        int yPosition = Settings.FRAME_HEIGHT - Settings.CARD_HEIGHT - 60; //386
        for (int i = 0; i < player.getHandPlayer().size(); i++) {
            Card card = player.getHandPlayer().get(i);
            int xPosition = 150 + 125 * i;
            card.setX(xPosition);
            card.setY(yPosition);
        }
    }

    protected Image loadCardImage(String path) {
        try {
            return new ImageIcon(Objects.requireNonNull(getClass().getResource(path))).getImage();
        } catch (Exception e) {
            return null;
        }
    }

    protected abstract void checkPause();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Color color1 = new Color(0x00, 0x64, 0x00);
        Color color2 = new Color(0x32, 0xCD, 0x32);

        Point2D center = new Point2D.Float(Settings.FRAME_WIDTH / 2.0f - 80, Settings.FRAME_HEIGHT / 2.0f);
        float radius = Math.min(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT) / 2.0f;
        RadialGradientPaint paint = new RadialGradientPaint(center, radius, new float[]{0f, 1f}, new Color[]{color2, color1});
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT);

        draw(g2d);
    }
}