package org.csse220.game_engine;

public abstract class KillableThread extends Thread {
    private boolean active;

    protected final void activate() {
        active = true;
    }

    protected final void kill() {
        active = false;
    }

    protected final boolean isActive() {
        return active;
    }
}
