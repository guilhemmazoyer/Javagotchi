package app.util;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {

    public static final String SOUNDS_MENU_CLICK = "app/assets/sounds/click.wav";
	public static final String SOUNDS_DOOR = "app/assets/sounds/door.wav";
	public static final String SOUNDS_SAVE = "app/assets/sounds/save.wav";

    private SoundManager() { }
    
    	/**
	 * method for starting the sound clip
	  */
	public static void playsound(String path) {
		try {
			Clip clip = AudioSystem.getClip();

			clip.open(AudioSystem.getAudioInputStream(new File(path)));
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-20.0f);
            clip.setFramePosition(0);
            clip.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
}
