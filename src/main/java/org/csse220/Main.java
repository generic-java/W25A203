package org.csse220;


import org.csse220.game_engine.Engine;
import org.csse220.game_engine.game_objects.CuboidTerrain;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.levels.Level;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static int FPS = 0;

    public void start() {
        ArrayList<Level> levels = Level.loadAll();

        JFrame window = new JFrame("Demo");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);

        Engine engine = Engine.getInstance();
        engine.init(new Player(), window);
        engine.addGameObject(new CuboidTerrain(new Cuboid(new Point3d(0, 0, -15), 100, 10, 100, Color.BLUE)));

//
//        for (Level level : levels) {
//            engine.addLevel(level);
//        }
//        engine.setLevel(0);

    }

    public static void main(String[] args) {
        new Main().start();
    }
}