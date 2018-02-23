package Model;

import javafx.scene.paint.Color;

/**
 * Created by lefou on 20/02/2018.
 * @author Matthieu Le Boucher
 */
public class Ramier extends Pigeon {
    private static final Color COLOR = Color.BLUE;

    public Ramier(int x, int y) {
        super(x, y);
    }

    @Override
    public Color getColor() {
        return COLOR;
    }

    @Override
    public void run() {
        System.out.println("LOL RAMIER");
    }
}
