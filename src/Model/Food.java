package Model;

/**
 * @author Matthieu Le Boucher
 */
public class Food {
    public int x;

    public int y;

    private boolean isFresh = true;

    public Food(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Food(int x, int y, boolean isFresh) {
        this.x = x;
        this.y = y;
        this.isFresh = isFresh;
    }

    public boolean isFresh() {
        return isFresh;
    }

    public void setFresh(boolean fresh) {
        isFresh = fresh;
    }
}
