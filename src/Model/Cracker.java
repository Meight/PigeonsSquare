package Model;

import Controller.SquareController;

/**
 * @author Matthieu Le Boucher
 */
public class Cracker {
    public int x;

    public int y;

    float radius;

    double timer;

    double timeSinceExplosion = 0;

    Square square;

    public Cracker(int x, int y, float radius, double timer, Square square) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.timer = timer * 10;
        this.square = square;
    }

    public void tick(double dt) {
        timer -= dt;
        System.out.println("Tick, timer = " + timer);

        if(timer < 0)
            explode(dt);
    }

    private void explode(double dt) {
        timeSinceExplosion += dt;

        System.out.println("BOOM");

        if(timeSinceExplosion > SquareController.CRACKER_THREAT_TIME)
            square.removeCracker(this);
    }
}
