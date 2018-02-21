package Model;

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

    public Pigeon(int x, int y) {
        this.x = x;
        this.y = y;
    }
}