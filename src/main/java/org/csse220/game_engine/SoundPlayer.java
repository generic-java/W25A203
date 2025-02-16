package org.csse220.game_engine;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class SoundPlayer {
    private static final String PATH_PREFIX = FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/main/java/org/csse220/sounds/";
    private AudioInputStream deathSound;
    private AudioInputStream loseHealthSound;
    private AudioInputStream gainFuelSound;
    private AudioInputStream winSound;
    private AudioInputStream backgroundSong;

    public SoundPlayer() {
        try {
            deathSound = AudioSystem.getAudioInputStream(new File(PATH_PREFIX + "death_sound.wav"));
//            loseHealthSound = AudioSystem.getAudioInputStream(new File(PATH_PREFIX + "lose_health.wav"));
//            gainFuelSound = AudioSystem.getAudioInputStream(new File(PATH_PREFIX + "gain_fuel.wav"));
//            winSound = AudioSystem.getAudioInputStream(new File(PATH_PREFIX + "win_sound.wav"));
            backgroundSong = AudioSystem.getAudioInputStream(new File(PATH_PREFIX + "background.wav"));
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public void startBackground() {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(backgroundSong);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void playSound(AudioInputStream sound) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(sound);
                clip.start();
            } catch (LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void deathSound() {
        playSound(deathSound);
    }
}
