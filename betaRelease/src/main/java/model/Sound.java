package model;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound
{    
    private Clip menuOST;
    private Clip gameOST;
    private static Sound instance = null;

    private Sound(){
        try {
            menuOST = loadClip("menuOST.wav");
            gameOST = loadClip("gameOST.wav");
        } catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            menuOST = null;
            gameOST = null;
        }
    }

    private Clip loadClip(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sounds/" + filename));
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        return clip;
    }

    public void sfx(String fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Clip clip = loadClip(fileName);
        if (clip != null) {
            if (clip.getFramePosition() == clip.getFrameLength()) {
                clip.setFramePosition(0);
            }
            clip.start();
        }
    }

    public void menuOST() {
        if (menuOST != null) {
            menuOST.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void gameOST() {
        if (gameOST != null) {
            gameOST.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void pauseMenuOST() {
        if (menuOST != null) {
            menuOST.stop();
        }
    }

    public void pauseGameOST() {
        if (gameOST != null) {
            gameOST.stop();
        }
    }

    public void resetMenuOST(){
        if (menuOST != null) {
            menuOST.stop();
            menuOST.setFramePosition(0);
            menuOST.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void resetGameOST(){
        if (gameOST != null) {
            gameOST.stop();
            gameOST.setFramePosition(0);
            gameOST.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public int ostHandler(int sliderValue) {
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

    public static Sound getInstance(){
        if (instance == null) {
            instance = new Sound();
        }
        return instance;
    }
}