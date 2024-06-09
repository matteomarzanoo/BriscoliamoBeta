package model;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound
{

    public Clip enteredButton;
    public Clip clickedButton;
    
    public static Clip menuOST;
    public static Clip gameOST;

    static
    {
        try {
            menuOST = loadMusic("src/main/resources/sounds/menuOST.wav");
            gameOST = loadMusic("src/main/resources/sounds/gameOST.wav");
        } catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            menuOST = null;
            gameOST = null;
        }
    }

    private static Clip loadMusic(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(path));
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        return clip;
    }

    public static void menuOSTLoop(){ if (menuOST != null) {menuOST.loop(Clip.LOOP_CONTINUOUSLY);}}
    public static void gameOSTLoop(){ if (gameOST != null) {gameOST.loop(Clip.LOOP_CONTINUOUSLY);}}

    public void playEnteredButton()
    {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("src/main/resources/sounds/button.wav"));
            enteredButton = AudioSystem.getClip();
            enteredButton.open(audioIn);
            if (enteredButton != null)
            {
                if (enteredButton.getFramePosition() == enteredButton.getFrameLength()) { enteredButton.setFramePosition(0); }
                enteredButton.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();}

    }

    public void playClickedButton()
    {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("src/main/resources/sounds/hitted_button.wav"));
            clickedButton = AudioSystem.getClip();
            clickedButton.open(audioIn);
            if (clickedButton != null)
            {
                if (clickedButton.getFramePosition() == clickedButton.getFrameLength()) { clickedButton.setFramePosition(0); }
                clickedButton.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();}
    }

    public static void pause(Clip clip)
    {
        if (clip != null){clip.stop();}
    }

    public static void restart(Clip clip)
    {
        if (clip != null){
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public static int ostHandler(int sliderValue)
    {
        FloatControl menuOSTControl = (FloatControl) menuOST.getControl(FloatControl.Type.MASTER_GAIN);
        FloatControl gameOSTControl = (FloatControl) gameOST.getControl(FloatControl.Type.MASTER_GAIN);
        float value = sliderValue;
        if (value >= -80.0f && value <= 6.0f)
        {
            menuOSTControl.setValue(value);
            gameOSTControl.setValue(value);
        }
        return (int) value;
    }

    public int sfxHandler(int sliderValue)
    {
        FloatControl enteredButtonControl = (FloatControl) enteredButton.getControl(FloatControl.Type.MASTER_GAIN);
        FloatControl clickedButtonControl = (FloatControl) clickedButton.getControl(FloatControl.Type.MASTER_GAIN);
        float value = sliderValue;
        if (value >= -80.0f && value <= 6.0f)
        {
            enteredButtonControl.setValue(value);
            clickedButtonControl.setValue(value);
        }
        return (int) value;
    }
}