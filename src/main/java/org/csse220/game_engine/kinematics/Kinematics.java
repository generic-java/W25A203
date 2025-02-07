package org.csse220.game_engine.kinematics;

import org.csse220.game_engine.*;
import org.csse220.game_engine.graphics.Camera;
import org.csse220.game_engine.math_utils.GamePose;
import org.csse220.game_engine.math_utils.Vector2d;

import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Kinematics extends KillableThread {
    private final static double MOVE_VEL = 0.025;
    private final static double TURN_VEL = 0.003;
    private final GamePose CAMERA_OFFSET = new GamePose(0, -15, 0, 0);

    private final Set<GameObject> gameObjects;
    private final ElapsedTime timer;
    private final GameObject player;
    private final Set<Collideable> collideables;
    private final GameKeyListener gameKeyListener;
    private final GamePose[] elementMoveSteps = {
            new GamePose(1, 0, 0, 0),
            new GamePose(0, 1, 0, 0),
            new GamePose(0, 0, 1, 0),
            new GamePose(0, 0, 0, 1),
    };

    public Kinematics(GameObject player, GameKeyListener gameKeyListener) {
        collideables = ConcurrentHashMap.newKeySet();
        gameObjects = ConcurrentHashMap.newKeySet();
        timer = new ElapsedTime();
        this.player = player;
        gameObjects.add(player);
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
            setPlayerVelocity(dt);
            for (GamePose elementMoveStep : elementMoveSteps) {
                for (GameObject gameObject : gameObjects) {
                    gameObject.move(elementMoveStep, dt);
                    if (gameObject == player) {

                    }
                }
                for (Collideable collideable : collideables) {
                    for (Collideable toCheck : collideables) {
                        if (collideable != toCheck && collideable.hasCollided(toCheck)) {
                            //while (collideable.onCollide(elementMoveStep) && collideable.hasCollided(toCheck)) ;
                        }
                    }
                }
            }
            updateCameraPosition();
        }
    }

    private void updateCameraPosition() {
        Camera camera = Camera.getInstance();
        GamePose targetPose = player.getPose().addTo(CAMERA_OFFSET);
        camera.setPose(targetPose);
    }

    private void setPlayerVelocity(double dt) {
        Vector2d velocityVector = new Vector2d();
        double zVel = 0;
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
        if (gameKeyListener.isKeyPressed(KeyEvent.VK_E)) {
            zVel = MOVE_VEL;
        } else if (gameKeyListener.isKeyPressed(KeyEvent.VK_Q)) {
            zVel = -MOVE_VEL;
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
        if(gameKeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            zVel += MOVE_VEL;
        }
        velocityVector = velocityVector.normalize().multiply(MOVE_VEL).rotate(camera.getPose().yaw());
        player.setXVel(velocityVector.x);
        player.setYVel(velocityVector.y);
        player.setZVel(zVel);
        player.setYawVel(yawVel);
    }

    public boolean keyPressed(int keycode) {
        return gameKeyListener.isKeyPressed(keycode);
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public void clearAllGameObjects() {
        gameObjects.removeIf((gameObject) -> gameObject != player);
    }
}
