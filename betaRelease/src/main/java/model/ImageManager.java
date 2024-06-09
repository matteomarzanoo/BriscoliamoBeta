package model;

import com.jhlabs.image.GaussianFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageManager  {
    private static BufferedImage backgroundImage;
    private static BufferedImage endPanelImage;
    private static BufferedImage loadingScreenImage;
    private static BufferedImage blurred;

    static {
        try {
            backgroundImage = ImageIO.read(new File("src/main/resources/images/menu.jpg"));
            backgroundImage = createBlurredImage(backgroundImage, 50);
            blurred = createBlurredImage(backgroundImage, 100);

            loadingScreenImage = ImageIO.read(new File("src/main/resources/images/menu.jpg"));
            loadingScreenImage = createBlurredImage(loadingScreenImage, 50);

            endPanelImage = ImageIO.read(new File("src/main/resources/images/t1bis.jpg"));
            endPanelImage = createBlurredImage(endPanelImage, 2);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static BufferedImage getBackgroundImage() {
        return backgroundImage; 
    }
    public static BufferedImage getBlurred() {
        return blurred; 
    }
    public static BufferedImage getLoadingScreen() {
        return loadingScreenImage;
    }
    public static BufferedImage getEndPanelBackground() {
        return endPanelImage;
    }

    private static BufferedImage createBlurredImage(BufferedImage src, int radius) {
        GaussianFilter filter = new GaussianFilter(radius);
        BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        filter.filter(src, dst);
        return dst;
    }
}