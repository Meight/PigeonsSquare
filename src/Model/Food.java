package Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 * @author Matthieu Le Boucher
 */
public class Food implements Drawable {
    /**
     * The x coordinate of the food's position.
     */
    public int x;

    /**
     * The y coordinate of the food's position.
     */
    public int y;

    /**
     * Whether or not this food can be eaten by a pigeon.
     */
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

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(isFresh ? Color.GREEN : Color.RED);
    }
}
