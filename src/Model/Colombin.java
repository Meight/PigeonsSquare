package Model;

import javafx.scene.paint.Color;

/**
 * Created by lefou on 20/02/2018.
 * @author Matthieu Le Boucher
 */
public class Colombin extends Pigeon {
    private static final Color COLOR = Color.PURPLE;
    private static final int SIZE = 12;

    public Colombin(int x, int y, double speed, Square square) {
        super(x, y, speed, square);
    }

    @Override
    public Color getColor() {
        return COLOR;
    }

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    public void run() {
        super.run();
        System.out.println("LOL COLOMBIN");
    }
}
