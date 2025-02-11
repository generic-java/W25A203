package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math_utils.GamePose;
import org.csse220.game_engine.math_utils.Vector3d;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CompoundFace extends Drawable {

    private final Set<Face> faces = new HashSet<>();

    public CompoundFace(GamePose pose, Color color, Face... faces) {
        super(pose, color);
        this.faces.addAll(Arrays.asList(faces));
    }

    @Override
    public void draw(Vector3d camPose, double pitch, double yaw, boolean shade) {
        faces.forEach((face) -> face.draw(camPose, pitch, yaw, shade));
    }
}
