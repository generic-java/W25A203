package org.csse220;

import org.csse220.game_engine.Engine;
import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.game_objects.CuboidTerrain;
import org.csse220.game_engine.graphics.CompoundDrawable;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Face;
import org.csse220.game_engine.graphics.Point3d;
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
        window.setSize(1500, 900);
        window.requestFocus();

        Player player = new Player();
        Engine engine = Engine.getInstance();
        engine.init(player, window);
        levels.forEach((level) -> engine.addLevel(level));


        //engine.addGameObject(new CuboidTerrain(new Cuboid(new Point3d(0, 120, 0), 100, 10, 100, Color.GREEN)));

        CompoundDrawable elephant = new CompoundDrawable(new GamePose(0, 0, 0, 0),
                new Cuboid(new GamePose(0, 0, 5.5, 0), 5, 5.5, 3.75, Color.GRAY),
                new Cuboid(new GamePose(-1.5, 0, 0, 0), 1.5, 5, 1.5, Color.GRAY),
                new Cuboid(new GamePose(1.5, 0, 0, 0), 1.5, 5, 1.5, Color.GRAY),
                new Face(
                        new GamePose(),
                        new Point3d(-2.5, 0, 4.75),
                        new Point3d(-2.5, -1, 4),
                        new Point3d(-2.5, 3, 3), Color.GRAY),
                new Face(
                        new GamePose(),
                        new Point3d(-3.5, 0, 4.75),
                        new Point3d(-3.5, -1, 4),
                        new Point3d(-3.5, 3, 3), Color.GRAY),
                new Cuboid(new GamePose(0, 0, 8.75, 0), 4, 1, 2, Color.GRAY),
                new Cuboid(new GamePose(0, 0, 11.25, 0), 5.5, 6.5, 5.5, Color.GRAY), // head
                new Face(
                        new GamePose(), // bottom part of left ear
                        new Point3d(-2.75, -1, 11.25),
                        new Point3d(-2.75, 0, 8),
                        new Point3d(-6.75, 0.5, 11.25),
                        Color.DARK_GRAY
                ),
                new Face(
                        new GamePose(),
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
                new Face(
                        new GamePose(),
                        new Point3d(2.75, -1, 11.25),
                        new Point3d(2.75, 0, 15),
                        new Point3d(6.75, 0.5, 11.25),
                        Color.DARK_GRAY
                ), // eyes
                new Cuboid(new GamePose(-2, 2.875, 11.25, 0), 2, 2, 0.25, Color.WHITE),
                new Cuboid(new GamePose(2, 2.875, 11.25, 0), 2, 2, 0.25, Color.WHITE),

                new Cuboid(new GamePose(-2, 3, 11.25, 0), 1, 1, 0.25, Color.BLACK),
                new Cuboid(new GamePose(2, 3, 11.25, 0), 1, 1, 0.25, Color.BLACK),

                new Face(
                        new GamePose(),
                        new Point3d(-0.75, 2.75, 8),
                        new Point3d(-0.75, 2.75, 10),
                        new Point3d(-0.75, 5, 6),
                        Color.DARK_GRAY
                ),
                new Face(
                        new GamePose(),
                        new Point3d(0.75, 2.75, 8),
                        new Point3d(0.75, 2.75, 10),
                        new Point3d(0.75, 5, 6),
                        Color.DARK_GRAY
                ),
                new Face(
                        new GamePose(),
                        new Point3d(-0.75, 2.75, 10),
                        new Point3d(-0.75, 5, 7),
                        new Point3d(-0.75, 5, 6),
                        Color.DARK_GRAY
                ),
                new Face(
                        new GamePose(),
                        new Point3d(0.75, 2.75, 10),
                        new Point3d(0.75, 5, 7),
                        new Point3d(0.75, 5, 6),
                        Color.DARK_GRAY
                ),
                new Face(
                        new GamePose(),
                        new Point3d(-0.75, 2.75, 10),
                        new Point3d(-0.75, 5, 7),
                        new Point3d(0.75, 5, 7),
                        Color.DARK_GRAY
                ),
                new Face(
                        new GamePose(),
                        new Point3d(0.75, 2.75, 10),
                        new Point3d(-0.75, 2.75, 10),
                        new Point3d(0.75, 5, 7),
                        Color.DARK_GRAY
                ),
                new Face(
                        new GamePose(),
                        new Point3d(-0.75, 5, 7),
                        new Point3d(-0.75, 5, 4),
                        new Point3d(-0.75, 6, 4),
                        Color.DARK_GRAY
                ),
                new Face(
                        new GamePose(),
                        new Point3d(0.75, 5, 7),
                        new Point3d(0.75, 5, 4),
                        new Point3d(0.75, 6, 4),
                        Color.DARK_GRAY
                ),
                new Face(
                        new GamePose(),
                        new Point3d(-0.75, 5, 7),
                        new Point3d(-0.75, 6, 4),
                        new Point3d(0.75, 6, 4),
                        Color.DARK_GRAY
                ),
                new Face(
                        new GamePose(),
                        new Point3d(0.75, 5, 7),
                        new Point3d(-0.75, 5, 7),
                        new Point3d(0.75, 6, 4),
                        Color.DARK_GRAY
                ),
                new Face(
                        new GamePose(),
                        new Point3d(-0.75, 5, 4),
                        new Point3d(-0.75, 5, 7),
                        new Point3d(-0.75, 3.5, 8),
                        Color.DARK_GRAY
                ),
                new Face(
                        new GamePose(),
                        new Point3d(0.75, 5, 4),
                        new Point3d(0.75, 5, 7),
                        new Point3d(0.75, 3.5, 8),
                        Color.DARK_GRAY
                ),
                new Face(
                        new GamePose(),
                        new Point3d(-0.75, 5, 4),
                        new Point3d(0.75, 5, 4),
                        new Point3d(-0.75, 3.5, 8),
                        Color.DARK_GRAY
                ),
                new Face(
                        new GamePose(),
                        new Point3d(-0.75, 3.5, 8),
                        new Point3d(0.75, 3.5, 8),
                        new Point3d(0.75, 5, 4),
                        Color.DARK_GRAY
                )

        );
        //engine.addGameObject(new GameObject(new GamePose(), null, elephant));
//        engine.addGameObject(new Drone(new GamePose(0, 0, 15, 0)));
//        engine.addGameObject(new GameObject(new GamePose(), null, elephant));
//        engine.addGameObject(new CuboidTerrain(new Cuboid(new Point3d(50, 0, 0), 10, 100, 100, Color.GREEN)));


//
        //engine.addGameObject(new Drone(new GamePose(), new Hitbox(new GamePose(15, 15, 15, 0), 5, 5, 5), new Cuboid(new Point3d(0, 0, 0), 5, 5, 5, Color.GRAY)));
//        for (Level level : levels) {
//            engine.addLevel(level);
//        }
        //player.setZVel(-0.0001);
        engine.setLevel(0);
        //engine.addGameObject(new Drone(new GamePose(15, 0, 15, 0)));
        //engine.setBackground(new Color(160, 230, 255));
        //engine.addGameObject(new CuboidTerrain(new Cuboid(new GamePose(10, 10, -5, 0), 20, 500, 500, Color.PINK)));

        //engine.addGameObject(new CuboidTerrain(new Cuboid(new GamePose(50, 0, 0, 0.5), 25, 100, 100, Color.GREEN)));

//        engine.addGameObject(new CuboidTerrain(new Cuboid(new GamePose(150, 25, -10, 0), 30, 5, 5, Color.ORANGE)));
//        engine.addGameObject(new CuboidTerrain(new Cuboid(new GamePose(150, 40, -10, 0), 30, 5, 5, Color.ORANGE)));
//
//        engine.addGameObject(new CuboidTerrain(new Cuboid(new GamePose(140, 32, -5, 1.55), 30, 2.5, 5, Color.ORANGE)));
//        engine.addGameObject(new CuboidTerrain(new Cuboid(new GamePose(160, 32, -5, 1.55), 30, 2.5, 5, Color.ORANGE)));
//
//        engine.addGameObject(new CuboidTerrain(new Cuboid(new GamePose(150, 25, -2.5, 0), 30, 2.5, 5, Color.ORANGE)));
//        engine.addGameObject(new CuboidTerrain(new Cuboid(new GamePose(150, 40, -2.5, 0), 30, 2.5, 5, Color.ORANGE)));
//
//        engine.addGameObject(new CuboidTerrain(new Cuboid(new GamePose(140, 32, 0, 1.55), 30, 2.5, 5, Color.ORANGE)));
//        engine.addGameObject(new CuboidTerrain(new Cuboid(new GamePose(160, 32, 0, 1.55), 30, 2.5, 5, Color.ORANGE)));
//
//        engine.addGameObject(new CuboidTerrain(new Cuboid(new GamePose(150, 25, 2.5, 0), 30, 2.5, 5, Color.ORANGE)));
//        engine.addGameObject(new CuboidTerrain(new Cuboid(new GamePose(150, 40, 2.5, 0), 30, 2.5, 5, Color.ORANGE)));
//
//        engine.addGameObject(new CuboidTerrain(new Cuboid(new GamePose(140, 32, 5, 1.55), 30, 2.5, 5, Color.ORANGE)));
//        engine.addGameObject(new CuboidTerrain(new Cuboid(new GamePose(160, 32, 5, 1.55), 30, 2.5, 5, Color.ORANGE)));
        //engine.addGameObject(new CuboidTerrain(new Cuboid(new GamePose(150, 25, -5, 1.5), 30, 5, 5, Color.ORANGE)));
        //engine.addGameObject(new CuboidTerrain(new Cuboid(new Point3d(0, 0, 50), 100, 10, 100, Color.GREEN)));
        //player.setPose(new GamePose(0, 0, 25, 0));
//        GamePose standardPose = new GamePose(150, 25, -10, 0);
//        CompoundDrawable bonfire = new CompoundDrawable(new GamePose(0, 0, 0, 0),
//                new Cuboid(standardPose, 30, 5, 5, Color.ORANGE),
//                new Cuboid(standardPose.translateY(15), 30, 5, 5, Color.ORANGE),
//
//                new Cuboid(new GamePose(standardPose.x-10, standardPose.y+12, standardPose.z+5, 1.55), 30, 2.5, 5, Color.ORANGE),
//                new Cuboid(new GamePose(standardPose.x-10, standardPose.y+12, standardPose.z+5, 1.55), 30, 2.5, 5, Color.ORANGE),
//                new Cuboid(new GamePose(150, 25, -2.5, 0), 30, 2.5, 5, Color.ORANGE),
//                new Cuboid(new GamePose(150, 40, -2.5, 0), 30, 2.5, 5, Color.ORANGE),
//                new Cuboid(new GamePose(140, 32, 0, 1.55), 30, 2.5, 5, Color.ORANGE),
//                new Cuboid(new GamePose(160, 32, 0, 1.55), 30, 2.5, 5, Color.ORANGE),
//                new Cuboid(new GamePose(150, 25, 2.5, 0), 30, 2.5, 5, Color.ORANGE),
//                new Cuboid(new GamePose(150, 40, 2.5, 0), 30, 2.5, 5, Color.ORANGE),
//                new Cuboid(new GamePose(140, 32, 5, 1.55), 30, 2.5, 5, Color.ORANGE),
//                new Cuboid(new GamePose(160, 32, 5, 1.55), 30, 2.5, 5, Color.ORANGE)
//        );
//
//        engine.addGameObject(new GameObject(new GamePose(), null, bonfire));
        engine.addGameObject(new GameObject(new GamePose(), null, elephant));
        engine.startKinematics();


    }

    public static void main(String[] args) {
        new Main().start();
    }
}