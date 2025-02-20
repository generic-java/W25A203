package org.csse220.game_engine.characters;

import org.csse220.game_engine.graphics.*;
import org.csse220.game_engine.math.GamePose;
import org.csse220.game_engine.math.Vector3d;

import java.awt.*;

public class Elephant extends Drawable {
    private final Drawable drawable;

    public Elephant(GamePose pose) {
        super(pose, Color.GRAY);
        drawable = new CompositeDrawable(new GamePose(0, 0, 0, 0),
                new Cuboid(new GamePose(), new GamePose(0, 0, 5.5, 0), 5, 5.5, 3.75, Color.GRAY),
                new Cuboid(new GamePose(), new GamePose(-1.5, 0, 0, 0), 1.5, 5, 1.5, Color.GRAY),
                new Cuboid(new GamePose(), new GamePose(1.5, 0, 0, 0), 1.5, 5, 1.5, Color.GRAY),
                new Face(
                        new GamePose(),
                        new Point3d(-2.5, 0, 4.75),
                        new Point3d(-2.5, -1, 4),
                        new Point3d(-2.5, 3, 3), Color.GRAY),
//                new Face(
//                        new GamePose(),
//                        new Point3d(-3.5, 0, 4.75),
//                        new Point3d(-3.5, -1, 4),
//                        new Point3d(-3.5, 3, 3), Color.RED),
//                new Face(
//                        new GamePose(),
//                        new Point3d(-3.5, 1, 4.75),
//                        new Point3d(-3.5, 0, 4.75),
//                        new Point3d(-3.5, 3, 3), Color.YELLOW),
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
                new Cuboid(new GamePose(), new GamePose(-2, 2.875, 11.25, 0), 2, 2, 0.25, Color.WHITE),
                new Cuboid(new GamePose(), new GamePose(2, 2.875, 11.25, 0), 2, 2, 0.25, Color.WHITE),

                new Cuboid(new GamePose(), new GamePose(-2, 3, 11.25, 0), 1, 1, 0.25, Color.BLACK),
                new Cuboid(new GamePose(), new GamePose(2, 3, 11.25, 0), 1, 1, 0.25, Color.BLACK),

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
    }

    @Override
    public void setPose(GamePose pose) {
        drawable.setPose(pose);
    }

    @Override
    public void hide() {
        drawable.hide();
    }

    @Override
    public void show() {
        drawable.show();
    }

    @Override
    public void draw(Vector3d camPose, double pitch, double yaw, boolean shade) {
        drawable.draw(camPose, pitch, yaw, shade);
    }
}
