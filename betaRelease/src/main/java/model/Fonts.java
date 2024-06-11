package model;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Fonts 
{
    private static Font logo;
    private static Font gamepanel;
    private static Font menupanel;
    private static Font endpanel;
    private static Font endpanelbuttons;
    private static Font settingsLeftPanel;
    private static Font settingsRightPanel;
    private static Font statisticsPanel;
    private static Font endpanelscores;
    
    static{
        try{
            logo = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Playfair_Display/static/PlayfairDisplay-BlackItalic.ttf")).deriveFont(120f);
            menupanel = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Montserrat/static/Montserrat-ExtraBoldItalic.ttf")).deriveFont(28f);
            gamepanel = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Montserrat/static/Montserrat-ExtraBold.ttf")).deriveFont(17f);
            endpanel = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Montserrat/static/Montserrat-MediumItalic.ttf")).deriveFont(24f);
            endpanelbuttons = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Montserrat/static/Montserrat-ExtraBoldItalic.ttf")).deriveFont(24f);
            endpanelscores = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Montserrat/static/Montserrat-ExtraBoldItalic.ttf")).deriveFont(60f);
            settingsLeftPanel = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Montserrat/static/Montserrat-MediumItalic.ttf")).deriveFont(24f);
            settingsRightPanel = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Montserrat/static/Montserrat-ExtraBold.ttf")).deriveFont(24f);
            statisticsPanel = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Playfair_Display/static/PlayfairDisplay-BlackItalic.ttf")).deriveFont(48f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(logo);
            ge.registerFont(gamepanel);
            ge.registerFont(menupanel);
            ge.registerFont(endpanel);
            ge.registerFont(endpanelscores);
            ge.registerFont(endpanelbuttons);
            ge.registerFont(settingsLeftPanel);
            ge.registerFont(settingsRightPanel);
            ge.registerFont(statisticsPanel);
        } catch (FontFormatException | IOException e) {}
    }

    public static Font getLogo(){
        return logo; 
    }

    public static Font getGamePanel(){
        return gamepanel;
    }
    
    public static Font getMenuPanel(){
        return menupanel;
    }

    public static Font getEndPanel(){
        return endpanel;
    }

    public static Font getSettingsLeftPanel(){
        return settingsLeftPanel;
    }

    public static Font getSettingsRightPanel(){
        return settingsRightPanel;
    }

    public static Font getStatisticsPanel(){
        return statisticsPanel;
    }

    public static Font getEndPanelButtons(){
        return endpanelbuttons;
    }

    public static Font getEndPanelFonts(){
        return endpanelscores;
    }
}
