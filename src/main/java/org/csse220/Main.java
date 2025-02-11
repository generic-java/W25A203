package org.csse220;


import org.csse220.game_engine.Engine;
import org.csse220.game_engine.characters.Drone;
import org.csse220.game_engine.game_objects.CuboidTerrain;
import org.csse220.game_engine.graphics.CompoundDrawable;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Face;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.GamePose;
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

        Player player = new Player();
        Engine engine = Engine.getInstance();
        engine.init(player, window);
        engine.addGameObject(new CuboidTerrain(new Cuboid(new Point3d(0, 0, -15), 100, 10, 100, Color.PINK)));


        engine.addGameObject(new CuboidTerrain(new Cuboid(new Point3d(0, 120, 0), 100, 10, 100, Color.GREEN)));
        player.setPose(new GamePose(5, 0, 0, 0));
        player.setZVel(0);

        CompoundDrawable elephant = new CompoundDrawable(new GamePose(0, 0, 0, 0),
                new Cuboid(new GamePose(), new Point3d(0, 0, 5.5), 5, 5.5, 3.75, Color.GRAY),
                new Cuboid(new GamePose(), new Point3d(-1.5, 0, 0), 1.5, 5, 1.5, Color.GRAY),
                new Cuboid(new GamePose(), new Point3d(1.5, 0, 0), 1.5, 5, 1.5, Color.GRAY),
                new Face(new GamePose(),
                        new Point3d(-2.5, 0, 4.75),
                        new Point3d(-2.5, -1, 4),
                        new Point3d(-2.5, 3, 3), Color.GRAY),
                new Face(new GamePose(),
                        new Point3d(-3.5, 0, 4.75),
                        new Point3d(-3.5, -1, 4),
                        new Point3d(-3.5, 3, 3), Color.GRAY),
                new Cuboid(new GamePose(), new Point3d(0, 0, 8.75), 4, 1, 2, Color.GRAY),
                new Cuboid(new GamePose(), new Point3d(0, 0, 11.25), 5.5, 6.5, 5.5, Color.GRAY),
                new Face(new GamePose(), // bottom part of left ear
                        new Point3d(-2.75, -1, 11.25),
                        new Point3d(-2.75, 0, 8),
                        new Point3d(-6.75, 0.5, 11.25),
                        Color.DARK_GRAY
                ),
                new Face(new GamePose(),
                        new Point3d(-2.75, -1, 11.25),
                        new Point3d(-2.75, 0, 15),
                        new Point3d(-6.75, 0.5, 11.25),
                        Color.DARK_GRAY
                ),

                new Face(new GamePose(), // bottom part of right ear
                        new Point3d(2.75, -1, 11.25),
                        new Point3d(2.75, 0, 8),
                        new Point3d(6.75, 0.5, 11.25),
                        Color.DARK_GRAY
                ),
                new Face(new GamePose(),
                        new Point3d(2.75, -1, 11.25),
                        new Point3d(2.75, 0, 15),
                        new Point3d(6.75, 0.5, 11.25),
                        Color.DARK_GRAY
                ), // eyes
                new Cuboid(new GamePose(), new Point3d(-2, 2.875, 11.25), 2, 2, 0.25, Color.WHITE),
                new Cuboid(new GamePose(), new Point3d(2, 2.875, 11.25), 2, 2, 0.25, Color.WHITE),

                new Cuboid(new GamePose(), new Point3d(-2, 3, 11.25), 1, 1, 0.25, Color.BLACK),
                new Cuboid(new GamePose(), new Point3d(2, 3, 11.25), 1, 1, 0.25, Color.BLACK)
        );
        //engine.addGameObject(new GameObject(new GamePose(), null, elephant));
        //engine.addGameObject(new Drone(new GamePose(0, 15, 15, 0)));


//
        engine.addGameObject(new Drone(new GamePose(15, 15, -10, 0)));
//        for (Level level : levels) {
//            engine.addLevel(level);
//        }
//        engine.setLevel(0);

    }

    public static void main(String[] args) {
        new Main().start();
    }
}
