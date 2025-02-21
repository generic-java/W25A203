package org.csse220.game_engine;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class SoundPlayer {
    private static final String PATH_PREFIX = FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/main/java/org/csse220/sounds/";
    private MiniPlayer deathSound;
    private MiniPlayer gainFuelSound;
    private MiniPlayer boingSound;
    private MiniPlayer backgroundSong;


    public SoundPlayer() {

        deathSound = new MiniPlayer(PATH_PREFIX + "death_sound.wav");
        boingSound = new MiniPlayer(PATH_PREFIX + "boing.wav");
        gainFuelSound = new MiniPlayer(PATH_PREFIX + "gain_fuel.wav");
        backgroundSong = new MiniPlayer(PATH_PREFIX + "background.wav");

    }

    public void startBackground() {
        backgroundSong.play();
    }


    public void deathSound() {
        deathSound.play();
    }

    public void gainFuelSound() {
        gainFuelSound.play();
    }

    public void trampolineSound() {
        boingSound.play();
    }

    private static class MiniPlayer {
        private final Clip clip;

        private MiniPlayer(String fileName) {
            try {
                AudioInputStream sound = AudioSystem.getAudioInputStream(new File(fileName));
                clip = AudioSystem.getClip();
                clip.open(sound);
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            }
        }

        public void play() {
            clip.setFramePosition(0);
            clip.start();
        }
    }


}
