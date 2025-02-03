package org.csse220;


import org.csse220.game_engine.EngineMain;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Point3d;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static int FPS = 0;

    public void start() {
        JFrame window = new JFrame("Demo");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);

        EngineMain engine = new EngineMain(new Player(), window);

        for (int i = 0; i < 300; i++) {
            engine.addDrawable(new Cuboid(new Point3d(1000 * (Math.random() - 0.5), 1000 * (Math.random() - 0.5), 0), 8, 5, 15, Color.GREEN));
        }

        engine.addDrawable(new Cuboid(new Point3d(0, 15, 0), 5, 5, 5, Color.BLUE));


        engine.start();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}