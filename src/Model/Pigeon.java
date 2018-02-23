package Model;

import javafx.scene.paint.Color;

/**
 * Created by lefou on 20/02/2018.
 * @author Matthieu Le Boucher
 */
public abstract class Pigeon extends Thread {
    /**
     * The x coordinate of the pigeon on the screen.
     */
    public int x;

    /**
     * The y coordinate of the pigeon on the screen.
     */
    public int y;

    /**
     * The movement speed of the pigeon.
     */
    private double speed;

    /**
     * The square within which the pigeon is living.
     */
    private Square square;

    /**
     * The food being currently targeted by the pigeon.
     */
    private Food targetFood;

    public Pigeon(int x, int y, double speed, Square square) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.square = square;
    }

    protected void refreshTargetFood() {
        targetFood = square.getClosestFood(x, y);
    }

    /**
     * @return The color of the individual, based on its species.
     */
    public abstract Color getColor();

    /**
     * @return The size of the individual, based on its species.
     */
    public abstract int getSize();
}