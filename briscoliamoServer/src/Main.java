import javax.swing.UIManager;

import View.Frame;

public class Main {
    public static void main(String[] args) {
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (Exception e){}
        Frame myFrame = new Frame();
    }
}
