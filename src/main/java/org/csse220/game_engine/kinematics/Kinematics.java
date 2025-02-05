package org.csse220.game_engine.kinematics;

import org.csse220.game_engine.*;
import org.csse220.game_engine.graphics.Camera;
import org.csse220.game_engine.math_utils.Pose3d;
import org.csse220.game_engine.math_utils.Vector2d;
import org.csse220.game_engine.math_utils.Vector3d;

import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Kinematics extends KillableThread {
    private final static double MOVE_VEL = 0.025;
    private final static double TURN_VEL = 0.003;

    private final Set<GameElement> gameElements;
    private final ElapsedTime timer;
    private final GameObject player;
    private final Set<Collideable> collideables;
    private final GameKeyListener gameKeyListener;
    private final Pose3d[] elementMoveSteps = {
            new Pose3d(1, 0, 0, 0, 0, 0),
            new Pose3d(0, 1, 0, 0, 0, 0),
            new Pose3d(0, 0, 1, 0, 0, 0),
            new Pose3d(0, 0, 0, 1, 0, 0),
            new Pose3d(0, 0, 0, 0, 1, 0),
            new Pose3d(0, 0, 0, 0, 0, 1),
    };

    public Kinematics(GameObject player, GameKeyListener gameKeyListener) {
        gameElements = ConcurrentHashMap.newKeySet();
        timer = new ElapsedTime();
        this.player = player;
        collideables = ConcurrentHashMap.newKeySet();
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
            for (Pose3d elementMoveStep : elementMoveSteps) {
                for (GameElement gameElement : gameElements) {
                    gameElement.move(elementMoveStep, dt);
                }
                for (Collideable collideable : collideables) {
                    for (Collideable toCheck : collideables) {
                        if (collideable != toCheck && collideable.hasCollided(toCheck)) {
                            while (collideable.onCollide(elementMoveStep) && collideable.hasCollided(toCheck)) ;
                        }
                    }
                }
            }


            Camera.getInstance().setXYZ(player.getPose()); // Set camera pose after everything else is done
        }
    }

    private void setPlayerVelocity(double dt) {
        Vector2d velocityVector = new Vector2d();
        Camera camera = Camera.getInstance();
        if (gameKeyListener.isKeyPressed(KeyEvent.VK_UP)) {
            camera.setPitch(camera.getPose().pitch() + TURN_VEL * dt);
        } else if (gameKeyListener.isKeyPressed(KeyEvent.VK_DOWN)) {
            camera.setPitch(camera.getPose().pitch() - TURN_VEL * dt);
        }
        if (gameKeyListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            camera.setYaw(camera.getPose().yaw() - TURN_VEL * dt);
        } else if (gameKeyListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            camera.setYaw(camera.getPose().yaw() + TURN_VEL * dt);
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
        velocityVector = velocityVector.normalize().multiply(MOVE_VEL).rotate(camera.getPose().yaw());
        player.setXVel(velocityVector.x);
        player.setYVel(velocityVector.y);
    }

    private void collide(GameObject player, Collideable collideable, Vector3d moveDirection, int yawDirection) {
        // TODO: finish
        if (collideable.hasCollided(player.getCollideable())) {
            synchronized (player) {
                while (collideable.hasCollided(player.getCollideable())) {
                    //player.incrementPosition(moveDirection);
                    player.incrementYaw(yawDirection);
                }
            }
        }
    }

    public boolean keyPressed(int keycode) {
        return gameKeyListener.isKeyPressed(keycode);
    }

    public void addGameElement(GameElement element) {
        gameElements.add(element);
        if (element instanceof GameObject) {
            collideables.add(((GameObject) element).getCollideable());
        }
    }

    public void removeGameElement(GameElement element) {
        gameElements.remove(element);
        if (element instanceof GameObject) {
            collideables.remove(((GameObject) element).getCollideable());
        }
    }

    public void addCollideable(Collideable collideable) {
        collideables.add(collideable);
    }

    public void removeCollideable(Collideable collideable) {
        collideables.remove(collideable);
    }
}
