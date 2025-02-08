package org.csse220;


import org.csse220.game_engine.Engine;
import org.csse220.game_engine.game_objects.CuboidTerrain;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.game_engine.kinematics.LineSegment;
import org.csse220.game_engine.kinematics.Point2d;
import org.csse220.levels.Level;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static int FPS = 0;

    public void start() {
        System.out.println(Double.isFinite(Double.NaN));

        JFrame window = new JFrame("Demo");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);

        Engine engine = Engine.getInstance();
        engine.init(new Player(), window);
        for (Level level : Level.loadAll()) {
            engine.addLevel(level);
        }
        engine.setLevel(0);
        engine.addGameObject(new CuboidTerrain(new Cuboid(new Point3d(0, 0, -15), 100, 10, 100, Color.BLUE)));

        System.out.println(new LineSegment(new Point2d(-1, -1), new Point2d(1, 1)).intersectsWith(new LineSegment(new Point2d(1, -1), new Point2d(-1, 1))));


//        for (int i = 0; i < 300; i++) {
//            engine.addGameObject(new GameObject(new GamePose(), null, new Cuboid(new Point3d(1000 * (Math.random() - 0.5), 1000 * (Math.random() - 0.5), 0), 8, 5, 15, Color.GREEN)));
//        }
//
//        engine.addGameObject(new GameObject(new GamePose(), null, new Cuboid(new Point3d(0, 15, 0), 5, 5, 5, Color.BLUE)));
//
    }

    public static void main(String[] args) {
        new Main().start();
    }
}