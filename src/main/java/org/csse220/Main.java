package org.csse220;

import org.csse220.game_engine.Engine;
import org.csse220.game_engine.characters.PaperAirplaneManager;
import org.csse220.levels.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Main {
    public static int FPS = 0;

    public void start() {
        ArrayList<Level> levels = Level.loadAll();

        JFrame window = new JFrame("Arcade Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setUndecorated(true);

        window.addKeyListener(new KeyListener() {
            boolean isFullScreen = true;

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_H || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !isFullScreen) {
                        return;
                    }
                    isFullScreen = !isFullScreen;
                    if (isFullScreen) {
                        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        window.dispose();
                        window.setUndecorated(true);
                        window.setUndecorated(true);
                        window.setVisible(true);
                    } else {
                        window.setSize(1500, 900);
                        window.dispose();
                        window.setUndecorated(false);
                        window.setVisible(true);
                    }
                }
            }
        });
        window.requestFocus();

        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_U) {
                    Engine.getInstance().downOneLevel();
                } else if (e.getKeyCode() == KeyEvent.VK_I) {
                    Engine.getInstance().upOneLevel();
                }
            }
        });

        Player player = new Player();
        Engine engine = Engine.getInstance();
        engine.init(player, window);
        engine.setBackground(new Color(110, 220, 255));
        levels.forEach(engine::addLevel);


        engine.setLevel(0);
        engine.addEventManager(new PaperAirplaneManager());
        engine.startKinematics();


    }

    public static void main(String[] args) {
        new Main().start();
    }
}