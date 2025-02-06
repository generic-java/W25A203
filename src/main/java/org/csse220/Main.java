package org.csse220;


import org.csse220.game_engine.Engine;
import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.game_engine.math_utils.GamePose;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static int FPS = 0;

    public void start() {
        JFrame window = new JFrame("Demo");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);

        Engine engine = Engine.getInstance();

        engine.init(new Player(), window);

        for (int i = 0; i < 300; i++) {
            engine.addGameObject(new GameObject(new GamePose(), null, new Cuboid(new Point3d(1000 * (Math.random() - 0.5), 1000 * (Math.random() - 0.5), 0), 8, 5, 15, Color.GREEN)));
        }

        engine.addGameObject(new GameObject(new GamePose(), null, new Cuboid(new Point3d(0, 15, 0), 5, 5, 5, Color.BLUE)));


    }

    public static void main(String[] args) {
        new Main().start();
    }
}