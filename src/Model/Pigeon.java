package Model;

/**
 * Created by lefou on 20/02/2018.
 */
public abstract class Pigeon extends Thread {
    /*
     * The x coordinate of the pigeon on the screen.
     */
    public int x;

    /*
     * The y coordinate of the pigeon on the screen.
     */
    public int y;

    private double _vitesse;

    public Pigeon(int x, int y) {
        this.x = x;
        this.y = y;
    }
}