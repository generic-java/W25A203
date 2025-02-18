package org.csse220.game_engine.kinematics;

import org.csse220.game_engine.*;
import org.csse220.game_engine.graphics.Camera;
import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.graphics.Screen;
import org.csse220.game_engine.graphics.ZBuffer;
import org.csse220.game_engine.math_utils.CameraPose;
import org.csse220.game_engine.math_utils.GamePose;
import org.csse220.game_engine.math_utils.Vector2d;
import org.csse220.game_engine.math_utils.Vector3d;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Kinematics extends KillableThread {
    private final static double MOVE_VEL = 0.075;
    private final static double TURN_VEL = 0.003;
    private static final Vector3d CAMERA_KP = new Vector3d(0.005, 0.005, 1);
    private final GamePose CAMERA_OFFSET = new GamePose(0, -20, 35, 0);
    private static final GamePose MOVE_X = new GamePose(1, 0, 0, 0);
    private static final GamePose MOVE_Y = new GamePose(0, 1, 0, 0);
    private static final GamePose MOVE_Z = new GamePose(0, 0, 1, 0);
    private static final GamePose MOVE_YAW = new GamePose(0, 0, 0, 1);
    private static final GamePose ROUNDING_PRECISION = new GamePose(1, 1, 1, 3);
    private Color backgroundColor = Color.WHITE;

    private final Set<GameObject> gameObjects;
    private final ElapsedTime timer;
    private final SolidGameObject player;
    private final Set<GameObject> collideables;
    private final Set<Drawable> drawables;
    private final Set<Runnable> eventManagers;
    private final GameKeyListener gameKeyListener;
    private final GamePose[] elementMoveSteps = {
            MOVE_X,
            MOVE_Y,
            MOVE_Z,
            MOVE_YAW,
    };

    public Kinematics(SolidGameObject player, GameKeyListener gameKeyListener) {
        collideables = ConcurrentHashMap.newKeySet();
        drawables = ConcurrentHashMap.newKeySet();
        gameObjects = ConcurrentHashMap.newKeySet();
        eventManagers = ConcurrentHashMap.newKeySet();
        timer = new ElapsedTime();
        this.player = player;
        addGameObject(player);
        this.gameKeyListener = gameKeyListener;
    }

    @Override
    public void start() {
        super.start();
        activate();
        timer.reset();
    }

    @Override
    public void run() {
        while (isActive()) {
            double dt = timer.getAndReset();
            for (Runnable runnable : eventManagers) {
                runnable.run();
            }
            for (GamePose elementMoveStep : elementMoveSteps) {
                for (GameObject gameObject : gameObjects) {
                    gameObject.update();
                    gameObject.move(elementMoveStep, dt);
                }
                for (GameObject gameObject : collideables) {
                    for (GameObject toCheck : collideables) { // The below if statement \/ is the problematic child
                        if (gameObject != toCheck && gameObject.getCollideable().hasCollided(toCheck.getCollideable())) {
                            if (gameObject.blocksMovement() && toCheck.blocksMovement()) {
                                gameObject.onSolidCollision(toCheck, elementMoveStep);
                                toCheck.onSolidCollision(gameObject, elementMoveStep);
                            } else {
                                gameObject.onSoftCollision(toCheck, elementMoveStep);
                                toCheck.onSoftCollision(gameObject, elementMoveStep);
                            }
                        }
                    }
                }
            }
            render(dt);
            setPlayerVelocity(dt);

        }
    }

    public void render(double dt) {
        updateCameraPosition(dt);
        Camera camera = Camera.getInstance();
        CameraPose camPose = camera.getPose();
        Vector3d.updatePitchYaw(camPose.pitch(), camPose.yaw());
        Screen.getInstance().fill(backgroundColor);
        for (Drawable drawable : drawables) {
            drawable.draw(camera.getPose(), camPose.pitch(), camPose.yaw(), true);
        }
        ZBuffer.getInstance().wipe();
        Screen.getInstance().refresh();
    }

    private void updateCameraPosition(double dt) {
        Camera camera = Camera.getInstance();
        GamePose targetPose = player.getPose().addTo(CAMERA_OFFSET.rotateYaw(player.getPose().yaw()));
        Vector3d error = targetPose.relativeTo(camera.getPose());
        Vector3d cameraVelocity = new Vector3d(error.x() * CAMERA_KP.x(), error.y() * CAMERA_KP.y(), error.z() * CAMERA_KP.z()).scale(dt);
        Vector3d newCameraPose = camera.getPose().addTo(cameraVelocity);
        camera.setPose(new GamePose(newCameraPose.x(), newCameraPose.y(), targetPose.z(), targetPose.yaw()));
        camera.setPose(camera.getPose().round(ROUNDING_PRECISION));
    }

    private void setPlayerVelocity(double dt) {
        Vector2d velocityVector = new Vector2d();
        double yawVel = 0;
        Camera camera = Camera.getInstance();
        if (gameKeyListener.isKeyPressed(KeyEvent.VK_UP)) {
            camera.setPitch(camera.getPose().pitch() + TURN_VEL * dt);
        } else if (gameKeyListener.isKeyPressed(KeyEvent.VK_DOWN)) {
            camera.setPitch(camera.getPose().pitch() - TURN_VEL * dt);
        }
        if (gameKeyListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            yawVel = -TURN_VEL;
        } else if (gameKeyListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            yawVel = TURN_VEL;
        }
        if (gameKeyListener.isKeyPressed(KeyEvent.VK_D)) {
            velocityVector = velocityVector.translate(1, 0);
        } else if (gameKeyListener.isKeyPressed(KeyEvent.VK_A)) {
            velocityVector = velocityVector.translate(-1, 0);
        }
        if (gameKeyListener.isKeyPressed(KeyEvent.VK_W)) {
            velocityVector = velocityVector.translate(0, 1);
        } else if (gameKeyListener.isKeyPressed(KeyEvent.VK_S)) {
            velocityVector = velocityVector.translate(0, -1);
        }
        if (gameKeyListener.isKeyPressed(KeyEvent.VK_U)) {
            Engine.getInstance().setLevel(0);
        } else if (gameKeyListener.isKeyPressed(KeyEvent.VK_I)) {
            Engine.getInstance().setLevel(1);
        }
        if (gameKeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            player.jump();
        }
        velocityVector = velocityVector.normalize().multiply(MOVE_VEL).rotate(camera.getPose().yaw());
        player.setXVel(velocityVector.x);
        player.setYVel(velocityVector.y);
        player.setYawVel(yawVel);
    }

    public boolean keyPressed(int keycode) {
        return gameKeyListener.isKeyPressed(keycode);
    }

    public void addGameObject(GameObject gameObject) {
        if (gameObject == null) {
            return;
        }
        gameObjects.add(gameObject);
        if (gameObject.hasCollideable()) {
            collideables.add(gameObject);
        }
        if (gameObject.hasDrawable()) {
            drawables.add(gameObject.getDrawable());
        }
    }

    public void removeGameObject(GameObject gameObject) {
        if (gameObject == null) {
            return;
        }
        gameObjects.remove(gameObject);
        if (gameObject.hasCollideable()) {
            collideables.remove(gameObject);
        }
        if (gameObject.hasDrawable()) {
            drawables.remove(gameObject.getDrawable());
        }
    }

    public void clearAllGameObjects() {
        gameObjects.clear();
        collideables.clear();
        drawables.clear();
        addGameObject(player);
    }

    public void setBackground(Color color) {
        backgroundColor = color;
    }

    public void addEventManager(Runnable runnable) {
        eventManagers.add(runnable);
    }
}
